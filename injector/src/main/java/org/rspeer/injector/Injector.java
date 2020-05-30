package org.rspeer.injector;

import org.rspeer.injector.script.Modscript;

import java.util.function.Function;

public class Injector implements Function<byte[], byte[]> {

    private final Modscript modscript;

    public Injector(Modscript modscript) {
        this.modscript = modscript;
    }

    @Override
    public byte[] apply(byte[] bytes) {
        return modscript.accept(bytes);
    }
}
