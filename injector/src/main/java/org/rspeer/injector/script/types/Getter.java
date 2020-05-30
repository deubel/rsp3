package org.rspeer.injector.script.types;

import org.rspeer.injector.script.Buffer;

import java.util.Arrays;

public class Getter {

    private final String definition;

    private final String owner;
    private final String name;
    private final String descriptor;
    private final String returnDescriptor;

    private final boolean virtual;
    private final boolean wide;

    private final long multiplier;

    private Getter(String definition, String owner, String name, String descriptor,
            String returnDescriptor, boolean virtual, boolean wide, String multiplier) {
        this.definition = createDefinition(definition, descriptor);
        this.owner = owner;
        this.name = name;
        this.descriptor = descriptor;
        this.returnDescriptor = returnDescriptor;
        this.virtual = virtual;
        this.wide = wide;
        this.multiplier = wide ? Long.parseLong(multiplier) : Integer.parseInt(multiplier);
    }

    public static Getter readFrom(Buffer buffer) {
        return new Getter(
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readBoolean(),
                buffer.readBoolean(),
                buffer.readUTF()
        );
    }

    public static Getter[] readAll(Buffer buffer) {
        Getter[] getters = new Getter[buffer.readInt()];
        Arrays.setAll(getters, i -> readFrom(buffer));
        return getters;
    }

    private String createDefinition(String definition, String descriptor) {
        definition = Character.toUpperCase(definition.charAt(0)) + definition.substring(1);
        return descriptor.equals("Z") ? "is" + definition : "get" + definition;
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

    public boolean isVirtual() {
        return virtual;
    }

    public boolean isWide() {
        return wide;
    }

    public long getDecoder() {
        return multiplier;
    }
}
