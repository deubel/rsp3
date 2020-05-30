package org.rspeer.game.loader.gamepack;

import org.rspeer.commons.CachedClassLoader;
import org.rspeer.commons.Configuration;
import org.rspeer.game.loader.config.GameConfig;
import org.rspeer.injector.Injector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class GamePack {

    private final GameConfig config;

    public GamePack(GameConfig config) {
        this.config = config;
    }

    public CachedClassLoader getVanillaLoader() {
        return getWritableLoader(Function.identity());
    }

    public void downloadIfOutdated() {
        if (isOutdated()) {
            download();
        }
    }

    public ClassLoader getInjectedLoader(Injector injector) {
        return getWritableLoader(injector);
    }

    private int getHash(boolean local) {
        try (JarInputStream jis = getJarInputStream(local)) {
            if (jis == null) {
                return -1;
            }

            Manifest man = jis.getManifest();
            if (man != null) {
                return man.hashCode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private boolean isOutdated() {
        return getHash(true) != getHash(false);
    }

    private void download() {
        try (InputStream is = getURL(false).openStream()) {
            if (!Files.exists(Configuration.Paths.HOME)) {
                Files.createDirectories(Configuration.Paths.HOME);
            }

            Files.copy(is, Configuration.Paths.GAMEPACK_LOCATION, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CachedClassLoader getWritableLoader(Function<byte[], byte[]> transformer) {
        return getLoader(jis -> {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] data = new byte[4096];
                int read;
                while ((read = jis.read(data, 0, data.length)) != -1) {
                    out.write(data, 0, read);
                }
                out.flush();
                return transformer.apply(out.toByteArray());
            } catch (IOException e) {
                return null;
            }
        });
    }

    private CachedClassLoader getLoader(Function<JarInputStream, byte[]> mapEntryToBytes) {
        try {
            JarInputStream jis = getJarInputStream(true);
            if (jis == null) {
                return null;
            }

            Map<String, byte[]> entryToBytes = new HashMap<>();

            JarEntry entry;
            while ((entry = jis.getNextJarEntry()) != null) {
                if (!entry.getName().endsWith(".class")) {
                    continue;
                }

                byte[] bytes = mapEntryToBytes.apply(jis);
                if (bytes == null || bytes.length == 0) {
                    continue;
                }

                entryToBytes.put(entry.getName().replace(".class", ""), bytes);
            }

            return new CachedClassLoader(entryToBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private URL getURL(boolean local) throws MalformedURLException {
        if (local) {
            File pack = Configuration.Paths.GAMEPACK_LOCATION.toFile();
            if (!pack.exists()) {
                return null;
            }

            return pack.toURI().toURL();
        }

        return new URL(config.getGamePack());
    }

    private JarInputStream getJarInputStream(boolean local) throws IOException {
        URL url = getURL(local);
        if (url == null) {
            return null;
        }

        return new JarInputStream(url.openStream());
    }
}
