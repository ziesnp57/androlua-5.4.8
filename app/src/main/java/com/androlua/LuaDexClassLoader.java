package com.androlua;

import dalvik.system.*;
import java.util.*;

public class LuaDexClassLoader extends DexClassLoader {
	private HashMap<String,Class<?>> classCache=new HashMap<String,Class<?>>();

	private String mDexPath;

	public LuaDexClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
		super(dexPath, optimizedDirectory, libraryPath, parent);
		mDexPath=dexPath;
	}

	public String getDexPath() {
		return mDexPath;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		// TODO: Implement this method
		Class<?> cls=classCache.get(name);
		if (cls == null) {
			cls = super.findClass(name);
			classCache.put(name, cls);
		}
		return cls;
	}
}
	
