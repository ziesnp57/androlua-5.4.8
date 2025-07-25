


package org.luaj.vm2;


public abstract class Varargs {


    abstract public LuaValue arg(int i);


    abstract public int narg();


    abstract public LuaValue arg1();


    public Varargs eval() {
        return this;
    }


    public boolean isTailcall() {
        return false;
    }


    public int type(int i) {
        return arg(i).type();
    }


    public boolean isnil(int i) {
        return arg(i).isnil();
    }


    public boolean isfunction(int i) {
        return arg(i).isfunction();
    }


    public boolean isnumber(int i) {
        return arg(i).isnumber();
    }


    public boolean isstring(int i) {
        return arg(i).isstring();
    }


    public boolean istable(int i) {
        return arg(i).istable();
    }


    public boolean isthread(int i) {
        return arg(i).isthread();
    }


    public boolean isuserdata(int i) {
        return arg(i).isuserdata();
    }


    public boolean isvalue(int i) {
        return i > 0 && i <= narg();
    }


    public boolean optboolean(int i, boolean defval) {
        return arg(i).optboolean(defval);
    }


    public double optdouble(int i, double defval) {
        return arg(i).optdouble(defval);
    }


    public int optint(int i, int defval) {
        return arg(i).optint(defval);
    }


    public LuaInteger optinteger(int i, LuaInteger defval) {
        return arg(i).optinteger(defval);
    }


    public long optlong(int i, long defval) {
        return arg(i).optlong(defval);
    }


    public LuaNumber optnumber(int i, LuaNumber defval) {
        return arg(i).optnumber(defval);
    }


    public String optjstring(int i, String defval) {
        return arg(i).optjstring(defval);
    }


    public LuaString optstring(int i, LuaString defval) {
        return arg(i).optstring(defval);
    }


    public Object optuserdata(int i, Object defval) {
        return arg(i).optuserdata(defval);
    }


    public Object optuserdata(int i, Class c, Object defval) {
        return arg(i).optuserdata(c, defval);
    }


    public LuaValue optvalue(int i, LuaValue defval) {
        return i > 0 && i <= narg() ? arg(i) : defval;
    }


    public boolean checkboolean(int i) {
        return arg(i).checkboolean();
    }

    public double checkdouble(int i) {
        return arg(i).checknumber().todouble();
    }


    public int checkint(int i) {
        return arg(i).checknumber().toint();
    }


    public LuaInteger checkinteger(int i) {
        return arg(i).checkinteger();
    }


    public long checklong(int i) {
        return arg(i).checknumber().tolong();
    }


    public LuaNumber checknumber(int i) {
        return arg(i).checknumber();
    }


    public String checkjstring(int i) {
        return arg(i).checkjstring();
    }


    public LuaString checkstring(int i) {
        return arg(i).checkstring();
    }


    public Object checkuserdata(int i) {
        return arg(i).checkuserdata();
    }


    public Object checkuserdata(int i, Class c) {
        return arg(i).checkuserdata(c);
    }


    public LuaValue checkvalue(int i) {
        return i <= narg() ? arg(i) : LuaValue.argerror(i, "value expected");
    }


    public LuaValue checknotnil(int i) {
        return arg(i).checknotnil();
    }


    public void argcheck(boolean test, int i, String msg) {
        if (!test) LuaValue.argerror(i, msg);
    }


    public boolean isnoneornil(int i) {
        return i > narg() || arg(i).isnil();
    }


    public boolean toboolean(int i) {
        return arg(i).toboolean();
    }


    public byte tobyte(int i) {
        return arg(i).tobyte();
    }


    public char tochar(int i) {
        return arg(i).tochar();
    }


    public double todouble(int i) {
        return arg(i).todouble();
    }


    public float tofloat(int i) {
        return arg(i).tofloat();
    }


    public int toint(int i) {
        return arg(i).toint();
    }


    public long tolong(int i) {
        return arg(i).tolong();
    }


    public String tojstring(int i) {
        return arg(i).tojstring();
    }


    public short toshort(int i) {
        return arg(i).toshort();
    }


    public Object touserdata(int i) {
        return arg(i).touserdata();
    }


    public Object touserdata(int i, Class c) {
        return arg(i).touserdata(c);
    }


    public String tojstring() {
        Buffer sb = new Buffer();
        sb.append("(");
        for (int i = 1, n = narg(); i <= n; i++) {
            if (i > 1) sb.append(",");
            sb.append(arg(i).tojstring());
        }
        sb.append(")");
        return sb.tojstring();
    }


    public String toString() {
        return tojstring();
    }


    abstract public Varargs subargs(final int start);


    static class SubVarargs extends Varargs {
        private final Varargs v;
        private final int start;
        private final int end;

        public SubVarargs(Varargs varargs, int start, int end) {
            this.v = varargs;
            this.start = start;
            this.end = end;
        }

        public LuaValue arg(int i) {
            i += start - 1;
            return i >= start && i <= end ? v.arg(i) : LuaValue.NIL;
        }

        public LuaValue arg1() {
            return v.arg(start);
        }

        public int narg() {
            return end + 1 - start;
        }

        public Varargs subargs(final int start) {
            if (start == 1)
                return this;
            final int newstart = this.start + start - 1;
            if (start > 0) {
                if (newstart >= this.end)
                    return LuaValue.NONE;
                if (newstart == this.end)
                    return v.arg(this.end);
                if (newstart == this.end - 1)
                    return new PairVarargs(v.arg(this.end - 1), v.arg(this.end));
                return new SubVarargs(v, newstart, this.end);
            }
            return new SubVarargs(v, newstart, this.end);
        }
    }


    static final class PairVarargs extends Varargs {
        private final LuaValue v1;
        private final Varargs v2;


        PairVarargs(LuaValue v1, Varargs v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        public LuaValue arg(int i) {
            return i == 1 ? v1 : v2.arg(i - 1);
        }

        public int narg() {
            return 1 + v2.narg();
        }

        public LuaValue arg1() {
            return v1;
        }

        public Varargs subargs(final int start) {
            if (start == 1)
                return this;
            if (start == 2)
                return v2;
            if (start > 2)
                return v2.subargs(start - 1);
            return LuaValue.argerror(1, "start must be > 0");
        }
    }


    static final class ArrayVarargs extends Varargs {
        private final LuaValue[] v;
        private final Varargs r;


        ArrayVarargs(LuaValue[] v, Varargs r) {
            this.v = v;
            this.r = r;
        }

        public LuaValue arg(int i) {
            return i < 1 ? LuaValue.NIL : i <= v.length ? v[i - 1] : r.arg(i - v.length);
        }

        public int narg() {
            return v.length + r.narg();
        }

        public LuaValue arg1() {
            return v.length > 0 ? v[0] : r.arg1();
        }

        public Varargs subargs(int start) {
            if (start <= 0)
                LuaValue.argerror(1, "start must be > 0");
            if (start == 1)
                return this;
            if (start > v.length)
                return r.subargs(start - v.length);
            return LuaValue.varargsOf(v, start - 1, v.length - (start - 1), r);
        }

        void copyto(LuaValue[] dest, int offset, int length) {
            int n = Math.min(v.length, length);
            System.arraycopy(v, 0, dest, offset, n);
            r.copyto(dest, offset + n, length - n);
        }
    }


    static final class ArrayPartVarargs extends Varargs {
        private final int offset;
        private final LuaValue[] v;
        private final int length;
        private final Varargs more;


        ArrayPartVarargs(LuaValue[] v, int offset, int length) {
            this.v = v;
            this.offset = offset;
            this.length = length;
            this.more = LuaValue.NONE;
        }


        public ArrayPartVarargs(LuaValue[] v, int offset, int length, Varargs more) {
            this.v = v;
            this.offset = offset;
            this.length = length;
            this.more = more;
        }

        public LuaValue arg(final int i) {
            return i < 1 ? LuaValue.NIL : i <= length ? v[offset + i - 1] : more.arg(i - length);
        }

        public int narg() {
            return length + more.narg();
        }

        public LuaValue arg1() {
            return length > 0 ? v[offset] : more.arg1();
        }

        public Varargs subargs(int start) {
            if (start <= 0)
                LuaValue.argerror(1, "start must be > 0");
            if (start == 1)
                return this;
            if (start > length)
                return more.subargs(start - length);
            return LuaValue.varargsOf(v, offset + start - 1, length - (start - 1), more);
        }

        void copyto(LuaValue[] dest, int offset, int length) {
            int n = Math.min(this.length, length);
            System.arraycopy(this.v, this.offset, dest, offset, n);
            more.copyto(dest, offset + n, length - n);
        }
    }


    void copyto(LuaValue[] dest, int offset, int length) {
        for (int i = 0; i < length; ++i)
            dest[offset + i] = arg(i + 1);
    }


    public Varargs dealias() {
        int n = narg();
        switch (n) {
            case 0:
                return LuaValue.NONE;
            case 1:
                return arg1();
            case 2:
                return new PairVarargs(arg1(), arg(2));
            default:
                LuaValue[] v = new LuaValue[n];
                copyto(v, 0, n);
                return new ArrayVarargs(v, LuaValue.NONE);
        }
    }
}
