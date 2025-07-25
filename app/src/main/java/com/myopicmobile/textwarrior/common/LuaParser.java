package com.myopicmobile.textwarrior.common;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import org.luaj.vm2.LocVars;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.Upvaldesc;
import org.luaj.vm2.VarType;
import org.luaj.vm2.compiler.LexState;
import org.luaj.vm2.compiler.LuaC;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nirenr on 2019/11/23.
 */

public class LuaParser {
    private static HashMap<String, ArrayList<Pair>> localMap = new HashMap<>();
    public static HashMap<String, ArrayList<String>> javaMethodMap = new HashMap<>();
    public static HashMap<String, ArrayList<String>> javaFieldMap = new HashMap<>();
    public static HashMap<String, ArrayList<JavaVar>> javaVar = new HashMap<>();
    private static ArrayList<Var> varList = new ArrayList<>();
    private static ArrayList<LuaString> globalist = new ArrayList<>();
    private static HashMap<String, ArrayList<Pair>> valueMap =new HashMap<>();

    public static ArrayList<String> filterJava(String pkg, String keyword, int i) {
        //Log.i("luaj", "filterJava: " + pkg + ";" + keyword + ";" + i);
        ArrayList<String> ms = new ArrayList<>();
        ArrayList<JavaVar> js = javaVar.get(pkg);
        if (js == null)
            return ms;
        for (int i1 = js.size() - 1; i1 >= 0; i1--) {
            JavaVar j=js.get(i1);
            //Log.i("luaj", "filterJava: " + pkg + ";" + keyword + ";" + j.name);
            if (j.startidx <= i && j.endidx >= i) {
                if (j.name.toLowerCase().endsWith("." + pkg)) {
                    ArrayList<String> mm = javaFieldMap.get(j.name);
                    if (mm != null){
                        for (String s : mm) {
                            if (s.toLowerCase().startsWith(keyword))
                                ms.add(s);
                        }
                    }
                }
                ArrayList<String> mm = javaMethodMap.get(j.name);
                if (mm == null)
                    continue;
                for (String s : mm) {
                    if (s.toLowerCase().startsWith(keyword))
                        ms.add(s);
                }
                break;
            }
        }
        return ms;
    }

    public static ArrayList<String> filterJava(String pkg, int i) {
        i=i-pkg.length();
        //Log.i("luaj", "filterJava: " + pkg + ";" + javaVar);
        ArrayList<String> ms = new ArrayList<>();
        ArrayList<JavaVar> js = javaVar.get(pkg);
        //Log.i("luaj", "filterJava: " + pkg + ";" + js);
        if (js == null)
            return ms;
        for (int i1 = js.size() - 1; i1 >= 0; i1--) {
            JavaVar j=js.get(i1);
            //Log.i("luaj", "filterJava: " + pkg + ";" + j.name);
            if (j.startidx <= i && j.endidx >= i) {
                if (j.name.toLowerCase().endsWith("." + pkg)) {
                    ArrayList<String> mm = javaFieldMap.get(j.name);
                    if (mm != null)
                        ms.addAll(mm);
                }
                ArrayList<String> mm = javaMethodMap.get(j.name);
                if (mm != null)
                    ms.addAll(mm);
                break;
            }
        }
        return ms;
    }

    public static HashMap<String, ArrayList<Pair>> getValueMap() {
        return valueMap;
    }

    public static void reset() {
        if(varList.isEmpty())
            return;
        localMap.clear();
        varList.clear();
        javaVar.clear();
        globalist = new ArrayList<>();
        valueMap =new HashMap<>();
        LexState.errormsg=null;
        LexState.globals.clear();
        LexState.lines.clear();
        LexState.valueMap.clear();
    }

    public static class Var {
        public String name;
        public String type;
        public int startidx;
        public int endidx;

        public Var(String n, String t, int s, int e) {
            name = n;
            type = t;
            startidx = s;
            endidx = e;
        }

        @Override
        public String toString() {
            return String.format("Var (%s %s %s-%s)", name, type, startidx, endidx);
        }
    }

    public static class JavaVar {
        public String name;
        public ArrayList<String> method;
        public int startidx;
        public int endidx;

        public JavaVar(String n, int s, int e) {
            name = n;
            startidx = s;
            endidx = e;
        }
    }

    public static class CharInputSteam extends InputStream {

        private final CharSequence mSrc;
        private final int mLen;
        private int idx = 0;

        public CharInputSteam(CharSequence src) {
            mSrc = src;
            mLen = src.length();
        }

        @Override
        public int read() throws IOException {
            idx++;
            if (idx > mLen)
                return -1;
            return mSrc.charAt(idx - 1);
        }
    }

    public static boolean lexer(CharSequence src, Flag _abort) {
        //Log.i("luaj", "lexer: start");
        try {
            //Prototype lex = LuaC.lexer(new CharInputSteam(src), "luaj");
            Prototype lex = LuaC.lexer(src, "luaj",_abort);
            localMap.clear();
            varList.clear();
            javaVar.clear();
            lexer(lex);
            if (LexState.erroridx < 0)
                globalist = new ArrayList<>(LexState.globals);
            valueMap =new HashMap<>(LexState.valueMap);
            //Log.i("luaj", "lexer: "+valueMap);
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return false;
    }

    public static HashMap<String, ArrayList<Pair>> getLocalMap() {
        return localMap;
    }

    public static String typename(String n, LocVars l) {
        VarType type = l.type;
        if (type == null)
            return "";
        //Log.i("luaj", "typename: " + n + ';' + type.typename);
        int idx = type.typename.lastIndexOf(".");
        if (idx < 1)
            return type.typename;
        String p = type.typename.substring(0, idx);
        String c = type.typename.substring(idx + 1);
        getJavaMethods(c, type.typename);
        ArrayList<JavaVar> jv = javaVar.get(n);
        if (jv == null) {
            jv = new ArrayList<>();
            javaVar.put(n.toLowerCase(), jv);
        }
        jv.add(new JavaVar(type.typename, l.startidx, l.endidx));
        if (c.equals(n))
            return p;
        return c;
    }

    public static String typename(String n, Upvaldesc l) {
        VarType type = l.type;
        if (type == null)
            return "";
        //Log.i("luaj", "typename: " + n + ';' + type.typename);
        int idx = type.typename.lastIndexOf(".");
        if (idx < 1)
            return type.typename;
        String p = type.typename.substring(0, idx);
        String c = type.typename.substring(idx + 1);
        if (c.equals(n))
            return p;
        return c;
    }

    private static void getJavaMethods(String c, String typename) {
        ArrayList<String> ms = javaMethodMap.get(typename);
        if (ms != null)
            return;
        ms = new ArrayList<>();
        try {
            Class<?> clazz = Class.forName(typename);
            Method[] mm = clazz.getMethods();
            for (Method method : mm) {
                String name = method.getName();
                if (ms.contains(name))
                    continue;
                ms.add(name);
            }
            javaMethodMap.put(typename, ms);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        ArrayList<String> fs = javaFieldMap.get(typename);
        if (fs != null)
            return;
        fs = new ArrayList<>();
        try {
            Class<?> clazz = Class.forName(typename);
            Field[] mm = clazz.getFields();
            for (Field method : mm) {
                String name = method.getName();
                if (fs.contains(name))
                    continue;
                fs.add(name);
            }
            javaFieldMap.put(typename, fs);
        } catch (Exception e) {
           // e.printStackTrace();
        }


    }

    private static void lexer(Prototype p) {
        if (p == null)
            return;
        LocVars[] ls = p.locvars;
        int np = p.numparams;
        Upvaldesc[] us = p.upvalues;
        for (int i = 0; i < us.length; i++) {
            Upvaldesc l = us[i];
            String n = l.name.tojstring();
            typename(n, l);
            varList.add(new Var(n, " :upval" , p.startidx, p.endidx));
        }
        for (int i = 0; i < ls.length; i++) {
            LocVars l = ls[i];
            String n = l.varname.tojstring();
            if (i < np) {
                varList.add(new Var(n, " :arg", l.startidx, l.endidx));
            } else {
                typename(n, l);
                varList.add(new Var(n, " :local" , l.startidx, l.endidx));
            }
            ArrayList<Pair> a = localMap.get(n);
            if (a == null) {
                a = new ArrayList<>();
                localMap.put(n, a);
            }
            a.add(new Pair(l.startidx, l.endidx));
        }

        Prototype[] ps = p.p;
        for (Prototype l : ps) {
            lexer(l);
        }
    }

    private static ArrayList<String> userWord = new ArrayList<>();

    public static void clearUserWord() {
        userWord.clear();
    }

    public static ArrayList<String> getUserWord() {
        return userWord;
    }

    public static void addUserWord(String s) {
        userWord.add(s);
    }

    public static ArrayList<CharSequence> filterLocal(String name, int idx, ColorScheme colorScheme) {
        ArrayList<CharSequence> ret = new ArrayList<>();
        ArrayList<CharSequence> ca = new ArrayList<>();

        for (int i = varList.size() - 1; i >= 0; i--) {
            Var var = varList.get(i);
            if (var.startidx <= idx && var.endidx >= idx) {
                String n = var.name;
                if (ca.contains(n))
                    continue;
                ca.add(n);
                if (n.toLowerCase().startsWith(name))
                    ret.add(getColorText(n + var.type, colorScheme.getTokenColor(getType(var.type))));
                String p = getSpells(n);
                if (TextUtils.isEmpty(p))
                    continue;
                if (p.startsWith(name))
                    ret.add(getColorText(n + var.type, colorScheme.getTokenColor(getType(var.type))));
            }
        }
        ArrayList<LuaString> ks = globalist;
        for (LuaValue k : ks) {
            String n = k.tojstring();
            if (ca.contains(n))
                continue;
            ca.add(n);
            if (n.toLowerCase().startsWith(name))
                ret.add(getColorText(n + " :global", colorScheme.getTokenColor(Lexer.GLOBAL)));
            String p = getSpells(n);
            if (TextUtils.isEmpty(p))
                continue;
            if (p.startsWith(name))
                ret.add(getColorText(n + " :global", colorScheme.getTokenColor(Lexer.GLOBAL)));
        }
        return ret;
    }

    private static int getType(String type) {
        switch (type) {
            case " :upval":
                return Lexer.UPVAL;
            case " :arg":
            case " :local":
                return Lexer.LOCAL;
            case " :global":
                return Lexer.GLOBAL;
        }
        return 0;
    }

    private static CharSequence getColorText(String text, int color) {
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(color), 0, text.length(), 0);
        return ss;
    }

    private static final int GB_SP_DIFF = 160;
    private static final int[] secPosValueList = {1601, 1637, 1833, 2078, 2274, 2302,
            2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
            4086, 4390, 4558, 4684, 4925, 5249, 5600};
    private static final char[] firstLetter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
            'y', 'z'};

    private static String getSpells(String characters) {
        try {
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < characters.length(); i++) {
                char ch = characters.charAt(i);
                if (i == 0 && ch < 128) {
                    return null;
                }
                if ((ch >> 7) == 0) {
                    buffer.append(ch);
                } else {
                    char spell = getFirstLetter(ch);
                    if (spell == 0) {
                        continue;
                    }
                    buffer.append(String.valueOf(spell));
                }
            }
            return buffer.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static char getFirstLetter(char ch) {
        byte[] uniCode = null;
        try {
            uniCode = String.valueOf(ch).getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            return 0;
        }
        if (uniCode[0] < 128 && uniCode[0] > 0) {
            return 0;
        } else {
            return convert(uniCode);
        }
    }

    public static char convert(byte[] bytes) {
        char result = 0;
        int secPosValue = 0;
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= GB_SP_DIFF;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosValue >= secPosValueList[i]
                    && secPosValue < secPosValueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }


}
