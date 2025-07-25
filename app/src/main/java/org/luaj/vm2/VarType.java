package org.luaj.vm2;

public class VarType {
    public static final VarType TNUMBER = new VarType(LuaValue.TNUMBER);
    public static final VarType TFUNCTION = new VarType(LuaValue.TFUNCTION);
    public static final VarType TSTRING = new VarType(LuaValue.TSTRING);
    public static final VarType TTABLE = new VarType(LuaValue.TTABLE);
    public static final VarType TBOOLEAN = new VarType(LuaValue.TBOOLEAN);
    public static final VarType TNIL = new VarType(LuaValue.TNIL);
    public int type = LuaValue.TNONE;
    public String typename = "";

    public VarType(int t, String n) {
        type = t;
        typename = n;
    }

    public VarType(int t) {
        if (t < LuaValue.TYPE_NAMES.length&&t>0)
            typename = LuaValue.TYPE_NAMES[t];
        type = t;
    }

    public VarType(String n) {
        switch (n) {
            case "string":
                type = LuaValue.TSTRING;
                break;
            case "number":
                type = LuaValue.TNUMBER;
                break;
            case "table":
                type = LuaValue.TTABLE;
                break;
            case "function":
                type = LuaValue.TFUNCTION;
                break;
            case "boolean":
                type = LuaValue.TBOOLEAN;
                break;
        }
        typename = n;
    }
}
