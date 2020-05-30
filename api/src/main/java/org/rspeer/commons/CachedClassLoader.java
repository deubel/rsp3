package org.rspeer.commons;

import java.util.HashMap;
import java.util.Map;

public class CachedClassLoader extends ClassLoader {

    private final Map<String, byte[]> classes;
    private final Map<String, Class<?>> defined;

    public CachedClassLoader(Map<String, byte[]> classes) {
        super(CachedClassLoader.class.getClassLoader());
        this.classes = classes;
        this.defined = new HashMap<>();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (defined.containsKey(name)) {
            return defined.get(name);
        } else if (!classes.containsKey(name)) {
            return super.loadClass(name);
        }

        byte[] def = classes.get(name);
        Class<?> cls = super.defineClass(name, def, 0, def.length);
        defined.put(name, cls);
        return cls;
    }
}