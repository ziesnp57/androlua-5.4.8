package com.myopicmobile.textwarrior.common;

import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;

import com.androlua.LuaLexer;
import com.androlua.LuaTokenTypes;

import org.luaj.vm2.compiler.LexState;

import java.io.IOException;
import java.util.ArrayList;

import static com.androlua.LuaTokenTypes.NAME;
import static com.androlua.LuaTokenTypes.NEW_LINE;
import static com.androlua.LuaTokenTypes.WHITE_SPACE;

public class AutoIndent {
    public static int createAutoIndent(CharSequence text) {
        LuaLexer lexer = new LuaLexer(text);
        int idt = 0;
        try {
            while (true) {
                LuaTokenTypes type = lexer.advance();
                if (type == null) {
                    break;
                }
                if (lexer.yytext().equals("switch"))
                    idt += 1;
                else
                    idt += indent(type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return idt;
    }


    private static int indent(LuaTokenTypes t) {
        switch (t) {
            case FOR:
            case WHILE:
            case FUNCTION:
            case IF:
            case REPEAT:
            case SWITCH:
            case WHEN:
            //case TRY:
                return 1;
            case UNTIL:
            case END:
            case RCURLY:
                return -1;
            case LCURLY:
                return 1;
            default:
                return 0;
        }
    }

    public static CharSequence format(CharSequence text, int width) {
        StringBuilder builder = new StringBuilder();
        boolean isNewLine = true;
        boolean isNewLine2 = true;
        LuaLexer lexer = new LuaLexer(text);
        ArrayList<Rect> lines = new ArrayList<>(LexState.lines);
        if(lines.isEmpty()||!TextUtils.isEmpty(LexState.errormsg)) {
            lines.clear();
            lines.addAll(Lexer.getLines());
        }
        SparseIntArray idts = new SparseIntArray();
        for (Rect rect : lines) {
            if(rect==null)
                continue;
            idts.put(rect.top, idts.get(rect.top) + 1);
            idts.put(rect.bottom, idts.get(rect.bottom) - 1);
        }
        int line = 0;
        int lastLine = 0;
        try {
            int idt = 0;
            LuaTokenTypes last = WHITE_SPACE;
            while (true) {
                LuaTokenTypes type = lexer.advance();

                if (type == null)
                    break;
                line = lexer.yyline();
                if(lastLine!=line)
                    isNewLine2=true;

                lastLine=line;
                if (type == LuaTokenTypes.NEW_LINE) {
                    if (builder.length() > 0 && builder.charAt(builder.length() - 1) == ' ')
                        builder.deleteCharAt(builder.length() - 1);
                    isNewLine = true;
                    builder.append('\n');
                    idt = Math.max(0, idt);
                } else if (isNewLine||isNewLine2) {
                    switch (type) {
                        case WHITE_SPACE:
                            //builder.append(createIndent(idt * width));
                            break;
                        case ELSE:
                        case ELSEIF:
                        case CASE:
                        case DEFAULT:
                        //case CATCH:
                        //case FINALLY:
                            //idt--;
                            if(isNewLine)
                                builder.append(createIndent(idt * width - width / 2));
                            builder.append(lexer.yytext());
                            //idt++;
                            isNewLine = false;
                            isNewLine2=false;
                            break;
                        case DOUBLE_COLON:
                        case AT:
                            builder.append(lexer.yytext());
                            isNewLine = false;
                            isNewLine2=false;
                            break;
                        case END:
                        case UNTIL:
                        case RCURLY:
                             //idt--;
                            idt += idts.get(line);
                            if(isNewLine)
                                builder.append(createIndent(idt * width));
                            builder.append(lexer.yytext());
                            isNewLine = false;
                            isNewLine2=false;
                            break;
                        default:
                            if(isNewLine)
                            builder.append(createIndent(idt * width));
                            builder.append(lexer.yytext());
                            //idt += indent(type);
                            idt += idts.get(line);
                            isNewLine = false;
                            isNewLine2=false;
                            break;
                    }
                } else if (type == WHITE_SPACE) {
                    builder.append(' ');
                } else {
                    builder.append(lexer.yytext());
                    /*if(type == LCURLY){
                        switch (last){
                            case TRY:
                            case CATCH:
                            case FINALLY:
                            case RPAREN:
                                idt += 0;
                                break;
                            default:
                                idt += indent(type);
                        }
                    }*/
                }
                if (type != WHITE_SPACE && type != NEW_LINE)
                    last = type;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder;
    }

    private static char[] createIndent(int n) {
        if (n < 0)
            return new char[0];
        char[] idts = new char[n];
        for (int i = 0; i < n; i++)
            idts[i] = ' ';
        return idts;
    }

    public static String[] fix(CharSequence text) {
        ArrayList<String> ret = new ArrayList<>();
        LuaLexer lexer = new LuaLexer(text);
        while (true) {
            try {
                LuaTokenTypes  type = lexer.advance();
                if (type == null)
                    break;
                if(type==NAME){
                    ArrayList<String> cs = PackageUtil.fix(lexer.yytext());
                    if(cs!=null){
                        for (String c : cs) {
                            if(!ret.contains(c))
                                ret.add(c);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[] arr = new String[ret.size()];
        ret.toArray(arr);
        return arr;
    }
}
