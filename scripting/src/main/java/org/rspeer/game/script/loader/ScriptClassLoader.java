package org.rspeer.game.script.loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ScriptClassLoader extends ClassLoader {

    private final URL base;

    public ScriptClassLoader(URL base) {
        this.base = base;
    }

    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(name);

        if (clazz != null) {
            return clazz;
        }

        try {
            InputStream input = getResourceAsStream(name.replace('.', '/') + ".class");
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int read;
            while ((read = input.read(data, 0, data.length)) != -1) {
                output.write(data, 0, read);
            }
            output.flush();
            output.close();
            clazz = defineClass(name, data, 0, data.length);
            if (resolve) {
                resolveClass(clazz);
            }
        } catch (Exception e) {
            clazz = super.loadClass(name, resolve);
        }

        return clazz;
    }

    public URL getResource(String name) {
        try {
            return new URL(base, name);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public InputStream getResourceAsStream(String name) {
        try {
            return new URL(base, name).openStream();
        } catch (IOException e) {
            return null;
        }
    }
}
