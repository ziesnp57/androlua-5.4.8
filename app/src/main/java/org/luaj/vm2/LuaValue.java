


package org.luaj.vm2;

import android.view.KeyEvent;

import org.luaj.vm2.Varargs;


abstract
public class LuaValue extends Varargs {


    public static final int TINT = (-2);


    public static final int TNONE = (-1);


    public static final int TNIL = 0;


    public static final int TBOOLEAN = 1;


    public static final int TLIGHTUSERDATA = 2;


    public static final int TNUMBER = 3;


    public static final int TSTRING = 4;


    public static final int TTABLE = 5;


    public static final int TFUNCTION = 6;


    public static final int TUSERDATA = 7;


    public static final int TTHREAD = 8;


    public static final int TVALUE = 9;


    public static final String[] TYPE_NAMES = {
            "nil",
            "boolean",
            "lightuserdata",
            "number",
            "string",
            "table",
            "function",
            "userdata",
            "thread",
            "value",
    };


    public static final LuaValue NIL = LuaNil._NIL;


    public static final LuaBoolean TRUE = LuaBoolean._TRUE;


    public static final LuaBoolean FALSE = LuaBoolean._FALSE;


    public static final LuaValue NONE = None._NONE;


    public static final LuaNumber ZERO = LuaInteger.valueOf(0);


    public static final LuaNumber ONE = LuaInteger.valueOf(1);


    public static final LuaNumber MINUSONE = LuaInteger.valueOf(-1);


    public static final LuaValue[] NOVALS = {};


    public static LuaString ENV = valueOf("_ENV");


    public static final LuaString INDEX = valueOf("__index");


    public static final LuaString NEWINDEX = valueOf("__newindex");


    public static final LuaString CALL = valueOf("__call");


    public static final LuaString MODE = valueOf("__mode");


    public static final LuaString METATABLE = valueOf("__metatable");


    public static final LuaString ADD = valueOf("__add");


    public static final LuaString SUB = valueOf("__sub");


    public static final LuaString DIV = valueOf("__div");


    public static final LuaString MUL = valueOf("__mul");


    public static final LuaString POW = valueOf("__pow");


    public static final LuaString MOD = valueOf("__mod");


    public static final LuaString UNM = valueOf("__unm");


    public static final LuaString LEN = valueOf("__len");


    public static final LuaString EQ = valueOf("__eq");


    public static final LuaString LT = valueOf("__lt");


    public static final LuaString LE = valueOf("__le");


    public static final LuaString TOSTRING = valueOf("__tostring");


    public static final LuaString CONCAT = valueOf("__concat");


    public static final LuaString EMPTYSTRING = valueOf("");


    private static int MAXSTACK = 250;


    public static final LuaValue[] NILS = new LuaValue[MAXSTACK];

    static {
        for (int i = 0; i < MAXSTACK; i++)
            NILS[i] = NIL;
    }

    public LuaValue uservalue;

    public static final LuaString IDIV = valueOf("__idiv");
    public static final LuaString BAND = valueOf("__band");
    public static final LuaString BOR = valueOf("__bor");
    public static final LuaString BXOR = valueOf("__bxor");
    public static final LuaString SHL = valueOf("__shl");
    public static final LuaString SHR = valueOf("__shr");
    public static final LuaString BNOT = valueOf("__bnot");


    abstract public int type();


    abstract public String typename();


    public boolean isboolean() {
        return false;
    }


    public boolean isclosure() {
        return false;
    }


    public boolean isfunction() {
        return false;
    }


    public boolean isint() {
        return false;
    }


    public boolean isinttype() {
        return false;
    }


    public boolean islong() {
        return false;
    }


    public boolean isnil() {
        return false;
    }


    public boolean isnumber() {
        return false;
    }


    public boolean isstring() {
        return false;
    }


    public boolean isthread() {
        return false;
    }


    public boolean istable() {
        return false;
    }


    public boolean isuserdata() {
        return false;
    }


    public boolean isuserdata(Class c) {
        return false;
    }


    public boolean toboolean() {
        return true;
    }


    public byte tobyte() {
        return 0;
    }


    public char tochar() {
        return 0;
    }


    public double todouble() {
        return 0;
    }


    public float tofloat() {
        return 0;
    }


    public int toint() {
        return 0;
    }


    public long tolong() {
        return 0;
    }


    public short toshort() {
        return 0;
    }


    public String tojstring() {
        return typename() + ": " + Integer.toHexString(hashCode());
    }


    public Object touserdata() {
        return null;
    }


    public <T> T touserdata(Class<T> c) {
        return null;
    }


    public String toString() {
        return tojstring();
    }


    public LuaValue tonumber() {
        return NIL;
    }


    public LuaValue tostring() {
        return NIL;
    }


    public boolean optboolean(boolean defval) {
        argerror("boolean");
        return false;
    }


    public double optdouble(double defval) {
        argerror("double");
        return 0;
    }


    public int optint(int defval) {
        argerror("int");
        return 0;
    }


    public LuaInteger optinteger(LuaInteger defval) {
        argerror("integer");
        return null;
    }


    public long optlong(long defval) {
        argerror("long");
        return 0;
    }


    public LuaNumber optnumber(LuaNumber defval) {
        argerror("number");
        return null;
    }


    public String optjstring(String defval) {
        argerror("String");
        return null;
    }


    public LuaString optstring(LuaString defval) {
        argerror("string");
        return null;
    }


    public Object optuserdata(Object defval) {
        argerror("object");
        return null;
    }


    public Object optuserdata(Class c, Object defval) {
        argerror(c.getName());
        return null;
    }


    public LuaValue optvalue(LuaValue defval) {
        return this;
    }


    public boolean checkboolean() {
        argerror("boolean");
        return false;
    }



    public double checkdouble() {
        argerror("double");
        return 0;
    }

    public int checkint() {
        argerror("int");
        return 0;
    }


    public LuaInteger checkinteger() {
        argerror("integer");
        return null;
    }


    public long checklong() {
        argerror("long");
        return 0;
    }


    public LuaNumber checknumber() {
        argerror("number");
        return null;
    }


    public LuaNumber checknumber(String msg) {
        throw new LuaError(msg);
    }


    public String checkjstring() {
        argerror("string");
        return null;
    }


    public LuaString checkstring() {
        argerror("string");
        return null;
    }


    public Object checkuserdata() {
        argerror("userdata");
        return null;
    }


    public Object checkuserdata(Class c) {
        argerror("userdata");
        return null;
    }


    public LuaValue checknotnil() {
        return this;
    }


    public boolean isvalidkey() {
        return true;
    }


    public static LuaValue error(String message) {
        throw new LuaError(message);
    }


    public static void assert_(boolean b, String msg) {
        if (!b) throw new LuaError(msg);
    }


    protected LuaValue argerror(String expected) {
        throw new LuaError("bad argument: " + expected + " expected, got " + typename());
    }


    public static LuaValue argerror(int iarg, String msg) {
        throw new LuaError("bad argument #" + iarg + ": " + msg);
    }


    protected LuaValue typerror(String expected) {
        throw new LuaError(expected + " expected, got " + typename());
    }


    protected LuaValue unimplemented(String fun) {
        throw new LuaError("'" + fun + "' not implemented for " + typename());
    }


    protected LuaValue illegal(String op, String typename) {
        throw new LuaError("illegal operation '" + op + "' for " + typename);
    }


    protected LuaValue lenerror() {
        throw new LuaError("attempt to get length of " + typename());
    }


    protected LuaValue aritherror() {
        throw new LuaError("attempt to perform arithmetic on " + typename());
    }


    protected LuaValue aritherror(String fun) {
        throw new LuaError("attempt to perform arithmetic '" + fun + "' on " + typename());
    }


    protected LuaValue compareerror(String rhs) {
        throw new LuaError("attempt to compare " + typename() + " with " + rhs);
    }


    protected LuaValue compareerror(LuaValue rhs) {
        throw new LuaError("attempt to compare " + typename() + " with " + rhs.typename());
    }


    public LuaValue get(LuaValue key) {
        return gettable(this, key);
    }


    public LuaValue get(int key) {
        return get(LuaInteger.valueOf(key));
    }


    public LuaValue get(String key) {
        return get(valueOf(key));
    }


    public void set(LuaValue key, LuaValue value) {
        settable(this, key, value);
    }


    public void set(int key, LuaValue value) {
        set(LuaInteger.valueOf(key), value);
    }


    public void set(int key, String value) {
        set(key, valueOf(value));
    }


    public void set(String key, LuaValue value) {
        set(valueOf(key), value);
    }


    public void set(String key, double value) {
        set(valueOf(key), valueOf(value));
    }


    public void set(String key, int value) {
        set(valueOf(key), valueOf(value));
    }


    public void set(String key, String value) {
        set(valueOf(key), valueOf(value));
    }


    public LuaValue rawget(LuaValue key) {
        return unimplemented("rawget");
    }


    public LuaValue rawget(int key) {
        return rawget(valueOf(key));
    }


    public LuaValue rawget(String key) {
        return rawget(valueOf(key));
    }


    public void rawset(LuaValue key, LuaValue value) {
        unimplemented("rawset");
    }


    public void rawset(int key, LuaValue value) {
        rawset(valueOf(key), value);
    }


    public void rawset(int key, String value) {
        rawset(key, valueOf(value));
    }


    public void rawset(String key, LuaValue value) {
        rawset(valueOf(key), value);
    }


    public void rawset(String key, double value) {
        rawset(valueOf(key), valueOf(value));
    }


    public void rawset(String key, int value) {
        rawset(valueOf(key), valueOf(value));
    }


    public void rawset(String key, String value) {
        rawset(valueOf(key), valueOf(value));
    }


    public void rawsetlist(int key0, Varargs values) {
        for (int i = 0, n = values.narg(); i < n; i++) rawset(key0 + i, values.arg(i + 1));
    }


    public void presize(int i) {
        typerror("table");
    }


    public Varargs next(LuaValue index) {
        return typerror("table");
    }


    public Varargs inext(LuaValue index) {
        return typerror("table");
    }


    public LuaValue load(LuaValue library) {
        return library.call(EMPTYSTRING, this);
    }


    public LuaValue arg(int index) {
        return index == 1 ? this : NIL;
    }

    public int narg() {
        return 1;
    }

    ;

    public LuaValue arg1() {
        return this;
    }


    public LuaValue getmetatable() {
        return null;
    }


    public LuaValue setmetatable(LuaValue metatable) {
        return argerror("table");
    }


    public LuaValue call() {
        return callmt().call(this);
    }


    public LuaValue call(LuaValue arg) {
        return callmt().call(this, arg);
    }


    public LuaValue call(String arg) {
        return call(valueOf(arg));
    }


    public LuaValue call(LuaValue arg1, LuaValue arg2) {
        return callmt().call(this, arg1, arg2);
    }


    public LuaValue call(LuaValue arg1, LuaValue arg2, LuaValue arg3) {
        return callmt().invoke(new LuaValue[]{this, arg1, arg2, arg3}).arg1();
    }


    public LuaValue method(String name) {
        return this.get(name).call(this);
    }


    public LuaValue method(LuaValue name) {
        return this.get(name).call(this);
    }


    public LuaValue method(String name, LuaValue arg) {
        return this.get(name).call(this, arg);
    }


    public LuaValue method(LuaValue name, LuaValue arg) {
        return this.get(name).call(this, arg);
    }


    public LuaValue method(String name, LuaValue arg1, LuaValue arg2) {
        return this.get(name).call(this, arg1, arg2);
    }


    public LuaValue method(LuaValue name, LuaValue arg1, LuaValue arg2) {
        return this.get(name).call(this, arg1, arg2);
    }


    public Varargs invoke() {
        return invoke(NONE);
    }


    public Varargs invoke(Varargs args) {
        return callmt().invoke(this, args);
    }


    public Varargs invoke(LuaValue arg, Varargs varargs) {
        return invoke(varargsOf(arg, varargs));
    }


    public Varargs invoke(LuaValue arg1, LuaValue arg2, Varargs varargs) {
        return invoke(varargsOf(arg1, arg2, varargs));
    }


    public Varargs invoke(LuaValue[] args) {
        return invoke(varargsOf(args));
    }


    public Varargs invoke(LuaValue[] args, Varargs varargs) {
        return invoke(varargsOf(args, varargs));
    }


    public Varargs invokemethod(String name) {
        return get(name).invoke(this);
    }


    public Varargs invokemethod(LuaValue name) {
        return get(name).invoke(this);
    }


    public Varargs invokemethod(String name, Varargs args) {
        return get(name).invoke(varargsOf(this, args));
    }


    public Varargs invokemethod(LuaValue name, Varargs args) {
        return get(name).invoke(varargsOf(this, args));
    }


    public Varargs invokemethod(String name, LuaValue[] args) {
        return get(name).invoke(varargsOf(this, varargsOf(args)));
    }


    public Varargs invokemethod(LuaValue name, LuaValue[] args) {
        return get(name).invoke(varargsOf(this, varargsOf(args)));
    }


    protected LuaValue callmt() {
        return checkmetatag(CALL, "attempt to call ");
    }


    public LuaValue not() {
        return FALSE;
    }


    public LuaValue neg() {
        return checkmetatag(UNM, "attempt to perform arithmetic on ").call(this);
    }


    public LuaValue len() {
        return checkmetatag(LEN, "attempt to get length of ").call(this);
    }


    public int length() {
        return len().toint();
    }


    public int rawlen() {
        typerror("table or string");
        return 0;
    }


    public boolean equals(Object obj) {
        return this == obj;
    }


    public LuaValue eq(LuaValue val) {
        return this == val ? TRUE : FALSE;
    }


    public boolean eq_b(LuaValue val) {
        return this == val;
    }


    public LuaValue neq(LuaValue val) {
        return eq_b(val) ? FALSE : TRUE;
    }


    public boolean neq_b(LuaValue val) {
        return !eq_b(val);
    }


    public boolean raweq(LuaValue val) {
        return this == val;
    }


    public boolean raweq(LuaString val) {
        return false;
    }


    public boolean raweq(double val) {
        return false;
    }


    public boolean raweq(long val) {
        return false;
    }


    public static final boolean eqmtcall(LuaValue lhs, LuaValue lhsmt, LuaValue rhs, LuaValue rhsmt) {
        LuaValue h = lhsmt.rawget(EQ);
        return h.isnil() || h != rhsmt.rawget(EQ) ? false : h.call(lhs, rhs).toboolean();
    }


    public LuaValue add(LuaValue rhs) {
        return arithmt(ADD, rhs);
    }


    public LuaValue add(double rhs) {
        return arithmtwith(ADD, rhs);
    }


    public LuaValue add(long rhs) {
        return add((double) rhs);
    }


    public LuaValue sub(LuaValue rhs) {
        return arithmt(SUB, rhs);
    }


    public LuaValue sub(double rhs) {
        return aritherror("sub");
    }


    public LuaValue sub(long rhs) {
        return aritherror("sub");
    }


    public LuaValue subFrom(double lhs) {
        return arithmtwith(SUB, lhs);
    }


    public LuaValue subFrom(long lhs) {
        return subFrom((double) lhs);
    }


    public LuaValue mul(LuaValue rhs) {
        return arithmt(MUL, rhs);
    }


    public LuaValue mul(double rhs) {
        return arithmtwith(MUL, rhs);
    }


    public LuaValue mul(long rhs) {
        return arithmtwith(MUL, rhs);
    }


    public LuaValue pow(LuaValue rhs) {
        return arithmt(POW, rhs);
    }


    public LuaValue pow(double rhs) {
        return aritherror("pow");
    }


    public LuaValue pow(long rhs) {
        return aritherror("pow");
    }


    public LuaValue powWith(double lhs) {
        return arithmtwith(POW, lhs);
    }


    public LuaValue powWith(long lhs) {
        return powWith((double) lhs);
    }


    public LuaValue div(LuaValue rhs) {
        return arithmt(DIV, rhs);
    }


    public LuaValue div(double rhs) {
        return aritherror("div");
    }


    public LuaValue div(long rhs) {
        return aritherror("div");
    }


    public LuaValue divInto(double lhs) {
        return arithmtwith(DIV, lhs);
    }


    public LuaValue mod(LuaValue rhs) {
        return arithmt(MOD, rhs);
    }


    public LuaValue mod(double rhs) {
        return aritherror("mod");
    }


    public LuaValue mod(long rhs) {
        return aritherror("mod");
    }


    public LuaValue modFrom(double lhs) {
        return arithmtwith(MOD, lhs);
    }


    protected LuaValue arithmt(LuaValue tag, LuaValue op2) {
        LuaValue h = this.metatag(tag);
        if (h.isnil()) {
            h = op2.metatag(tag);
            if (h.isnil())
                error("attempt to perform arithmetic " + tag + " on " + typename() + " and " + op2.typename());
        }
        return h.call(this, op2);
    }


    protected LuaValue arithmtwith(LuaValue tag, double op1) {
        LuaValue h = metatag(tag);
        if (h.isnil())
            error("attempt to perform arithmetic " + tag + " on number and " + typename());
        return h.call(LuaValue.valueOf(op1), this);
    }


    public LuaValue lt(LuaValue rhs) {
        return comparemt(LT, rhs);
    }


    public LuaValue lt(double rhs) {
        return compareerror("number");
    }


    public LuaValue lt(long rhs) {
        return compareerror("number");
    }


    public boolean lt_b(LuaValue rhs) {
        return comparemt(LT, rhs).toboolean();
    }


    public boolean lt_b(long rhs) {
        compareerror("number");
        return false;
    }


    public boolean lt_b(double rhs) {
        compareerror("number");
        return false;
    }


    public LuaValue lteq(LuaValue rhs) {
        return comparemt(LE, rhs);
    }


    public LuaValue lteq(double rhs) {
        return compareerror("number");
    }


    public LuaValue lteq(long rhs) {
        return compareerror("number");
    }


    public boolean lteq_b(LuaValue rhs) {
        return comparemt(LE, rhs).toboolean();
    }


    public boolean lteq_b(long rhs) {
        compareerror("number");
        return false;
    }


    public boolean lteq_b(double rhs) {
        compareerror("number");
        return false;
    }


    public LuaValue gt(LuaValue rhs) {
        return rhs.comparemt(LE, this);
    }


    public LuaValue gt(double rhs) {
        return compareerror("number");
    }


    public LuaValue gt(long rhs) {
        return compareerror("number");
    }


    public boolean gt_b(LuaValue rhs) {
        return rhs.comparemt(LE, this).toboolean();
    }


    public boolean gt_b(long rhs) {
        compareerror("number");
        return false;
    }


    public boolean gt_b(double rhs) {
        compareerror("number");
        return false;
    }


    public LuaValue gteq(LuaValue rhs) {
        return rhs.comparemt(LT, this);
    }


    public LuaValue gteq(double rhs) {
        return compareerror("number");
    }


    public LuaValue gteq(long rhs) {
        return valueOf(todouble() >= rhs);
    }


    public boolean gteq_b(LuaValue rhs) {
        return rhs.comparemt(LT, this).toboolean();
    }


    public boolean gteq_b(long rhs) {
        compareerror("number");
        return false;
    }


    public boolean gteq_b(double rhs) {
        compareerror("number");
        return false;
    }


    public LuaValue comparemt(LuaValue tag, LuaValue op1) {
        LuaValue h;
        if (!(h = metatag(tag)).isnil() || !(h = op1.metatag(tag)).isnil())
            return h.call(this, op1);
        if (LuaValue.LE.raweq(tag) && (!(h = metatag(LT)).isnil() || !(h = op1.metatag(LT)).isnil()))
            return h.call(op1, this).not();
        return error("attempt to compare " + tag + " on " + typename() + " and " + op1.typename());
    }


    public int strcmp(LuaValue rhs) {
        error("attempt to compare " + typename());
        return 0;
    }


    public int strcmp(LuaString rhs) {
        error("attempt to compare " + typename());
        return 0;
    }


    public LuaValue concat(LuaValue rhs) {
        return this.concatmt(rhs);
    }


    public LuaValue concatTo(LuaValue lhs) {
        return lhs.concatmt(this);
    }


    public LuaValue concatTo(LuaNumber lhs) {
        return lhs.concatmt(this);
    }


    public LuaValue concatTo(LuaString lhs) {
        return lhs.concatmt(this);
    }


    public LuaValue concatmt(LuaValue rhs) {
        LuaValue h = metatag(CONCAT);
        if (h.isnil() && (h = rhs.metatag(CONCAT)).isnil())
            error("attempt to concatenate " + typename() + " and " + rhs.typename());
        return h.call(this, rhs);
    }


    public LuaValue and(LuaValue rhs) {
        return this.toboolean() ? rhs : this;
    }


    public LuaValue or(LuaValue rhs) {
        return this.toboolean() ? this : rhs;
    }


    public boolean testfor_b(LuaValue limit, LuaValue step) {
        return step.gt_b(0) ? lteq_b(limit) : gteq_b(limit);
    }


    public LuaString strvalue() {
        typerror("strValue");
        return null;
    }


    public LuaValue strongvalue() {
        return this;
    }


    public static LuaBoolean valueOf(boolean b) {
        return b ? LuaValue.TRUE : FALSE;
    }

    ;


    public static LuaInteger valueOf(int i) {
        return LuaInteger.valueOf(i);
    }

    public static LuaNumber valueOf(long i) {
        return LuaInteger.valueOf(i);
    }


    public static LuaNumber valueOf(double d) {
        return LuaDouble.valueOf(d);
    }

    ;


    public static LuaString valueOf(String s) {
        return LuaString.valueOf(s);
    }


    public static LuaString valueOf(byte[] bytes) {
        return LuaString.valueOf(bytes);
    }


    public static LuaString valueOf(byte[] bytes, int off, int len) {
        return LuaString.valueOf(bytes, off, len);
    }


    private static final int MAXTAGLOOP = 100;


    protected static LuaValue gettable(LuaValue t, LuaValue key) {
        LuaValue tm;
        int loop = 0;
        do {
            if (t.istable()) {
                LuaValue res = t.rawget(key);
                if ((!res.isnil()) || (tm = t.metatag(INDEX)).isnil())
                    return res;
            } else if ((tm = t.metatag(INDEX)).isnil())
                t.indexerror();
            if (tm.isfunction())
                return tm.call(t, key);
            t = tm;
        }
        while (++loop < MAXTAGLOOP);
        error("loop in gettable");
        return NIL;
    }


    protected static boolean settable(LuaValue t, LuaValue key, LuaValue value) {
        LuaValue tm;
        int loop = 0;
        do {
            if (t.istable()) {
                if ((!t.rawget(key).isnil()) || (tm = t.metatag(NEWINDEX)).isnil()) {
                    t.rawset(key, value);
                    return true;
                }
            } else if ((tm = t.metatag(NEWINDEX)).isnil())
                t.typerror("index");
            if (tm.isfunction()) {
                tm.call(t, key, value);
                return true;
            }
            t = tm;
        }
        while (++loop < MAXTAGLOOP);
        error("loop in settable");
        return false;
    }


    public LuaValue metatag(LuaValue tag) {
        LuaValue mt = getmetatable();
        if (mt == null)
            return NIL;
        return mt.rawget(tag);
    }


    protected LuaValue checkmetatag(LuaValue tag, String reason) {
        LuaValue h = this.metatag(tag);
        if (h.isnil())
            throw new LuaError(reason + typename());
        return h;
    }





    private void indexerror() {
        error("attempt to index (a " + typename() + " value)");
    }


    public static Varargs varargsOf(final LuaValue[] v) {
        switch (v.length) {
            case 0:
                return NONE;
            case 1:
                return v[0];
            case 2:
                return new PairVarargs(v[0], v[1]);
            default:
                return new ArrayVarargs(v, NONE);
        }
    }


    public static Varargs varargsOf(final LuaValue[] v, Varargs r) {
        switch (v.length) {
            case 0:
                return r;
            case 1:
                return r.narg() > 0 ?
                        (Varargs) new PairVarargs(v[0], r) :
                        (Varargs) v[0];
            case 2:
                return r.narg() > 0 ?
                        (Varargs) new ArrayVarargs(v, r) :
                        (Varargs) new PairVarargs(v[0], v[1]);
            default:
                return new ArrayVarargs(v, r);
        }
    }


    public static Varargs varargsOf(final LuaValue[] v, final int offset, final int length) {
        switch (length) {
            case 0:
                return NONE;
            case 1:
                return v[offset];
            case 2:
                return new PairVarargs(v[offset + 0], v[offset + 1]);
            default:
                return new ArrayPartVarargs(v, offset, length, NONE);
        }
    }


    public static Varargs varargsOf(final LuaValue[] v, final int offset, final int length, Varargs more) {
        switch (length) {
            case 0:
                return more;
            case 1:
                return more.narg() > 0 ?
                        (Varargs) new PairVarargs(v[offset], more) :
                        (Varargs) v[offset];
            case 2:
                return more.narg() > 0 ?
                        (Varargs) new ArrayPartVarargs(v, offset, length, more) :
                        (Varargs) new PairVarargs(v[offset], v[offset + 1]);
            default:
                return new ArrayPartVarargs(v, offset, length, more);
        }
    }


    public static Varargs varargsOf(LuaValue v, Varargs r) {
        switch (r.narg()) {
            case 0:
                return v;
            default:
                return new PairVarargs(v, r);
        }
    }


    public static Varargs varargsOf(LuaValue v1, LuaValue v2, Varargs v3) {
        switch (v3.narg()) {
            case 0:
                return new PairVarargs(v1, v2);
            default:
                return new ArrayPartVarargs(new LuaValue[]{v1, v2}, 0, 2, v3);
        }
    }




    public Varargs onInvoke(Varargs args) {
        return invoke(args);
    }


    public void initupvalue1(LuaValue env) {
    }

    public void setuservalue(LuaValue uservalue) {
        this.uservalue = uservalue;
    }

    public LuaValue getuservalue() {
        return uservalue;
    }

    public LuaValue idiv(LuaValue rhs) {
        return arithmt(IDIV, rhs);
    }

    public LuaValue idiv(long rhs) {
        return arithmtwith(IDIV, rhs);
    }

    public LuaValue band(LuaValue rhs) {
        return arithmt(BAND, rhs);
    }

    public LuaValue band(long rhs) {
        return arithmtwith(BAND, rhs);
    }

    public LuaValue bor(LuaValue rhs) {
        return arithmt(BOR, rhs);
    }

    public LuaValue bor(long rhs) {
        return arithmtwith(BOR, rhs);
    }

    public LuaValue bxor(LuaValue rhs) {
        return arithmt(BXOR, rhs);
    }

    public LuaValue bxor(long rhs) {
        return arithmtwith(BXOR, rhs);
    }

    public LuaValue shl(LuaValue rhs) {
        return arithmt(SHL, rhs);
    }

    public LuaValue shl(long rhs) {
        return arithmtwith(SHL, rhs);
    }

    public LuaValue shr(LuaValue rhs) {
        return arithmt(SHR, rhs);
    }

    public LuaValue shr(long rhs) {
        return arithmtwith(SHR, rhs);
    }

    public LuaValue bnot() {
        return checkmetatag(BNOT, "attempt to perform arithmetic on ").call(this);
    }

    public void setfenv(LuaValue env) {
        typerror("function");
    }

    public LuaValue getfenv() {
        typerror("function");
        return null;
    }


    private static final class None extends LuaNil {
        static None _NONE = new None();

        public LuaValue arg(int i) {
            return NIL;
        }

        public int narg() {
            return 0;
        }

        public LuaValue arg1() {
            return NIL;
        }

        public String tojstring() {
            return "none";
        }

        public Varargs subargs(final int start) {
            return start > 0 ? this : argerror(1, "start must be > 0");
        }

        void copyto(LuaValue[] dest, int offset, int length) {
            for (; length > 0; length--) dest[offset++] = NIL;
        }
    }


    public Varargs subargs(final int start) {
        if (start == 1)
            return this;
        if (start > 1)
            return NONE;
        return argerror(1, "start must be > 0");
    }

}
