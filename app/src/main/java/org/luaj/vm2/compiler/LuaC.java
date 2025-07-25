/*******************************************************************************
* Copyright (c) 2009-2011 Luaj.org. All rights reserved.
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
******************************************************************************/
package org.luaj.vm2.compiler;

import android.util.Log;

import com.myopicmobile.textwarrior.common.Flag;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;


import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;


public class LuaC extends Constants {

	/** A sharable instance of the LuaC compiler. */
	public static final LuaC instance = new LuaC();


    public static Prototype lexer(InputStream stream, String chunkname, Flag _abort) throws IOException {
		return (new CompileState()).luaY_parser2(stream, chunkname,_abort);
	}

	public static Prototype lexer(CharSequence stream, String chunkname, Flag _abort) throws IOException {
		return (new CompileState()).luaY_parser2(new ByteArrayInputStream(stream.toString().getBytes()), chunkname, _abort);
	}

	protected LuaC() {}

	/** Compile lua source into a Prototype.
	 * @param stream InputStream representing the text source conforming to lua source syntax.
	 * @param chunkname String name of the chunk to use.
	 * @return Prototype representing the lua chunk for this source.
	 * @throws IOException
	 */
	public Prototype compile(InputStream stream, String chunkname) throws IOException {
		return (new CompileState()).luaY_parser(stream, chunkname);
	}


	static class CompileState {
		int nCcalls = 0;
		private Hashtable strings = new Hashtable();
		protected CompileState() {}
	
		/** Parse the input */
		private Prototype luaY_parser(InputStream z, String name) throws IOException{
			LexState lexstate = new LexState(this, z);
			FuncState funcstate = new FuncState();
			// lexstate.buff = buff;
			lexstate.fs = funcstate;
			lexstate.setinput(this, z.read(), z, (LuaString) LuaValue.valueOf(name) );
			/* main func. is always vararg */
			funcstate.f = new Prototype();
			funcstate.f.source = (LuaString) LuaValue.valueOf(name);
			lexstate.mainfunc(funcstate);
			LuaC._assert (funcstate.prev == null);
			/* all scopes should be correctly finished */
			LuaC._assert (lexstate.dyd == null 
					|| (lexstate.dyd.n_actvar == 0 && lexstate.dyd.n_gt == 0 && lexstate.dyd.n_label == 0));
			return funcstate.f;
		}

		private Prototype luaY_parser2(InputStream z, String name, Flag _abort) throws IOException{
			LexState lexstate = new LexState(this, z,true, _abort);
			FuncState funcstate = new FuncState();
			// lexstate.buff = buff;
			lexstate.fs = funcstate;
			lexstate.setinput(this, z.read(), z, (LuaString) LuaValue.valueOf(name) );
			/* main func. is always vararg */
			funcstate.f = new Prototype();
			funcstate.f.source = (LuaString) LuaValue.valueOf(name);
			lexstate.mainfunc(funcstate);
			LuaC._assert (funcstate.prev == null);
			/* all scopes should be correctly finished */
			LuaC._assert (lexstate.dyd == null
					|| (lexstate.dyd.n_actvar == 0 && lexstate.dyd.n_gt == 0 && lexstate.dyd.n_label == 0));
			return funcstate.f;
		}

		// look up and keep at most one copy of each string
		public LuaString newTString(String s) {
			return cachedLuaString(LuaString.valueOf(s));
		}
	
		// look up and keep at most one copy of each string
		public LuaString newTString(LuaString s) {
			return cachedLuaString(s);
		}
	
		public LuaString cachedLuaString(LuaString s) {
			LuaString c = (LuaString) strings.get(s);
			if (c != null) 
				return c;
			strings.put(s, s);
			return s;
		}
	
		public String pushfstring(String string) {
			return string;
		}
	}
}
