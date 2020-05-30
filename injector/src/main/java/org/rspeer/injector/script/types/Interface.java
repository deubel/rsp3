package org.rspeer.injector.script.types;

import org.rspeer.injector.script.Buffer;

public class Interface {

    private final String definedPackage;
    private final String className;
    private final String definedName;

    private Interface(String definedPackage, String className, String definedName) {
        this.definedPackage = definedPackage;
        this.className = className;
        this.definedName = definedName;
    }

    public static Interface readFrom(Buffer buffer) {
        return new Interface(buffer.readUTF(), buffer.readUTF(), buffer.readUTF());
    }

    public String getDefinedPackage() {
        return definedPackage;
    }

    public String getClassName() {
        return className;
    }

    public String getDefinedName() {
        return definedName;
    }
}
