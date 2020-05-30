package org.rspeer.injector.script.types;

import org.rspeer.injector.script.Buffer;

import java.util.Arrays;

public class Invoker {

    private final String definition;

    private final String owner;
    private final String name;
    private final String descriptor;

    private final String expectedDescriptor;
    private final String normalizedDescriptor;

    private final int predicate;

    private final boolean virtual;
    private final boolean itf;

    private Invoker(String definition, String owner, String name, String descriptor, String expectedDescriptor,
            String normalizedDescriptor, int predicate, boolean virtual, boolean itf) {
        this.definition = definition;
        this.owner = owner;
        this.name = name;
        this.descriptor = descriptor;
        this.expectedDescriptor = expectedDescriptor;
        this.normalizedDescriptor = normalizedDescriptor;
        this.predicate = predicate;
        this.virtual = virtual;
        this.itf = itf;
    }

    public static Invoker readFrom(Buffer buffer) {
        return new Invoker(
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readInt(),
                buffer.readBoolean(),
                buffer.readBoolean()
        );
    }

    public static Invoker[] readAll(Buffer buffer) {
        Invoker[] invokers = new Invoker[buffer.readInt()];
        Arrays.setAll(invokers, i -> readFrom(buffer));
        return invokers;
    }

    public String getDefinition() {
        return definition;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public String getExpectedDescriptor() {
        return expectedDescriptor;
    }

    public String getNormalizedDescriptor() {
        return normalizedDescriptor;
    }

    public int getPredicate() {
        return predicate;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public boolean isInterface() {
        return itf;
    }
}
