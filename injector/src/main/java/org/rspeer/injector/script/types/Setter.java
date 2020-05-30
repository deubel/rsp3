package org.rspeer.injector.script.types;

import org.rspeer.injector.script.Buffer;

import java.util.Arrays;

public class Setter {

    private final String definition;

    private final String owner;
    private final String name;
    private final String descriptor;
    private final String returnDescriptor;

    private final boolean virtual;
    private final boolean wide;
    private final boolean proxy;

    private final long multiplier;

    private Setter(String definition, String owner, String name, String descriptor, String returnDescriptor,
            boolean virtual, boolean wide,  boolean proxy, String multiplier) {
        this.definition = createDefinition(definition, descriptor);
        this.owner = owner;
        this.name = name;
        this.descriptor = descriptor;
        this.returnDescriptor = returnDescriptor;
        this.virtual = virtual;
        this.wide = wide;
        this.proxy = proxy;
        this.multiplier = wide ? Long.parseLong(multiplier) : Integer.parseInt(multiplier);
    }

    public static Setter readFrom(Buffer buffer) {
        return new Setter(
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readUTF()
        );
    }

    public static Setter[] readAll(Buffer buffer) {
        Setter[] setters = new Setter[buffer.readInt()];
        Arrays.setAll(setters, i -> readFrom(buffer));
        return setters;
    }

    private String createDefinition(String definition, String descriptor) {
        definition = Character.toUpperCase(definition.charAt(0)) + definition.substring(1);
        return "set" + definition;
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

    public String getReturnDescriptor() {
        return returnDescriptor;
    }

    public String getArgumentType() {
        String desc = returnDescriptor;
        return desc.substring(desc.indexOf('(') + 1, desc.indexOf(')'));
    }

    public boolean isVirtual() {
        return virtual;
    }

    public boolean isWide() {
        return wide;
    }

    public boolean isProxy() {
        return proxy;
    }

    public long getEncoder() {
        return multiplier;
    }
}
