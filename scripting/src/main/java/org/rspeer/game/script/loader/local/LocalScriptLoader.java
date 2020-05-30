package org.rspeer.game.script.loader.local;

import org.rspeer.game.script.Script;
import org.rspeer.game.script.loader.ScriptBundle;
import org.rspeer.game.script.loader.ScriptProvider;
import org.rspeer.game.script.loader.ScriptSource;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LocalScriptLoader implements ScriptProvider {

    private final File root;

    public LocalScriptLoader(Path root) {
        this.root = root.toFile();
    }

    @Override
    public ScriptBundle predefined() {
        ScriptBundle bundle = new ScriptBundle();
        //bundle.add(new ScriptSource(TestScript.class));
        return bundle;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ScriptBundle load() {
        ScriptBundle bundle = new ScriptBundle();
        Deque<File> files = new ArrayDeque<>();
        Deque<File> visited = new ArrayDeque<>();
        try (URLClassLoader loader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()})) {
            files.push(root);
            if (root.isDirectory()) {
                for (File file : root.listFiles()) {
                    files.push(file);
                }
            }

            while (!files.isEmpty()) {
                File file = files.pop();
                visited.add(file);
                if (file.isDirectory()) {
                    File[] subFiles = file.listFiles();
                    if (subFiles != null) {
                        for (File sub : subFiles) {
                            if (!visited.contains(sub)) {
                                files.add(sub);
                            }
                        }
                    }
                } else if (file.getName().endsWith(".class")) {
                    String raw = file.getPath();
                    raw = raw.substring(root.getPath().length() + 1);
                    raw = raw.substring(0, raw.length() - ".class".length());
                    raw = raw.replace(File.separatorChar, '.');
                    Class<?> clazz = loader.loadClass(raw);
                    if (test(clazz)) {
                        bundle.add(new ScriptSource((Class<? extends Script>) clazz));
                    }
                } else if (file.getName().endsWith(".jar")) {
                    try (JarFile jar = new JarFile(file);
                         URLClassLoader ucl = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()})) {
                        Enumeration<JarEntry> elems = jar.entries();
                        while (elems.hasMoreElements()) {
                            try {
                                JarEntry entry = elems.nextElement();
                                if (entry.getName().endsWith(".class")) {
                                    String name = entry.getName();
                                    name = name.substring(0, name.length() - ".class".length());
                                    name = name.replace('/', '.');
                                    Class<?> clazz = ucl.loadClass(name);
                                    if (test(clazz)) {
                                        bundle.add(new ScriptSource((Class<? extends Script>) clazz));
                                    }
                                }
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bundle;
    }

    @Override
    public Script define(ScriptSource source) {
        try {
            return source.getTarget().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }
}
