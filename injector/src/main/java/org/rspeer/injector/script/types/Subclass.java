package org.rspeer.injector.script.types;

import org.rspeer.injector.script.Buffer;

public class Subclass {

    private final String name;
    private final String definition;

    private Subclass(String name, String definition) {
        this.name = name;
        this.definition = definition;
    }

    public static Subclass readFrom(Buffer buffer) {
        return new Subclass(buffer.readUTF(), buffer.readUTF());
    }

    public String getName() {
        return name;
    }

    public String getDefinition() {
        return definition;
    }
}
