package org.rspeer.injector.script.types;

import org.rspeer.injector.script.Buffer;

import java.util.Arrays;

public class FieldInsertion extends MemberInsertion {

    private final boolean getter;
    private final boolean setter;

    private FieldInsertion(int access, String owner, String name, String desc, boolean getter, boolean setter) {
        super(access, owner, name, desc);
        this.getter = getter;
        this.setter = setter;
    }

    public static FieldInsertion readFrom(Buffer buffer) {
        return new FieldInsertion(
                buffer.readInt(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readBoolean(),
                buffer.readBoolean()
        );
    }

    public static FieldInsertion[] readAll(Buffer buffer) {
        FieldInsertion[] fields = new FieldInsertion[buffer.readInt()];
        Arrays.setAll(fields, i -> readFrom(buffer));
        return fields;
    }

    public boolean isInjectedWithGetter() {
        return getter;
    }

    public boolean isInjectedWithSetter() {
        return setter;
    }

    public String createGetterDefinition() {
        String name = super.name;
        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        return desc.equals("Z") ? "is" + name : "get" + name;
    }

    public String createSetterDefinition() {
        String name = super.name;
        return "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
