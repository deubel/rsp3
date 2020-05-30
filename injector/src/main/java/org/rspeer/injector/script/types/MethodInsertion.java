package org.rspeer.injector.script.types;

import org.rspeer.injector.script.Buffer;
import org.rspeer.injector.script.CodeReader;

import java.util.Arrays;

public class MethodInsertion extends MemberInsertion {

    private final int maxStack, maxLocals;
    private final CodeReader code;

    public MethodInsertion(int access, String owner, String name, String desc,
            int maxStack, int maxLocals, CodeReader code) {
        super(access, owner, name, desc);
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.code = code;
    }

    public static MethodInsertion readFrom(Buffer buffer) {
        return new MethodInsertion(
                buffer.readInt(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readInt(),
                buffer.readInt(),
                new CodeReader(buffer)
        );
    }

    public static MethodInsertion[] readAll(Buffer buffer) {
        MethodInsertion[] methods = new MethodInsertion[buffer.readInt()];
        Arrays.setAll(methods, i -> readFrom(buffer));
        return methods;
    }

    public CodeReader getCode() {
        return code;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }
}
