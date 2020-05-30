package org.rspeer.injector.script.types;

import org.rspeer.injector.script.Buffer;
import org.rspeer.injector.script.CodeReader;

import java.util.Arrays;

public class Code {

    public static final int METHOD_START = 0;
    public static final int BEFORE_RETURNS = Integer.MIN_VALUE;

    public static final int TYPE_CODE = 0;
    public static final int TYPE_ARGS_CALLBACK = 1;

    private final String definition;

    private final String owner;
    private final String name;
    private final String desc;
    private final String normalizedDesc;

    private final boolean virtual;

    private final int type;
    private final int index;

    private final CodeReader reader;

    private Code(String definition, String owner, String name, String desc,
            String normalizedDesc, boolean virtual, int type, int index, CodeReader reader) {
        this.definition = definition;
        this.owner = owner;
        this.name = name;
        this.desc = desc;
        this.normalizedDesc = normalizedDesc;
        this.virtual = virtual;
        this.type = type;
        this.index = index;
        this.reader = reader;
    }

    public static Code readFrom(Buffer buffer) {
        return new Code(
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readBoolean(),
                buffer.readInt(),
                buffer.readInt(),
                new CodeReader(buffer)
        );
    }

    public static Code[] readAll(Buffer buffer) {
        Code[] codes = new Code[buffer.readInt()];
        Arrays.setAll(codes, i -> readFrom(buffer));
        return codes;
    }

    public String getDefinition() {
        return definition;
    }

    public String getOwner() {
        return owner;
    }

    public String getMethodName() {
        return name;
    }

    public String getDescriptor() {
        return desc;
    }

    public String getNormalizedDescriptor() {
        return normalizedDesc;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public int getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public CodeReader getReader() {
        return reader;
    }
}
