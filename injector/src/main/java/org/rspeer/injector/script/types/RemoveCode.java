package org.rspeer.injector.script.types;

import org.rspeer.injector.script.Buffer;

import java.util.Arrays;

public class RemoveCode {

    private final String owner, method, desc;
    private final int from, to;

    private RemoveCode(String owner, String method, String desc, int from, int to) {
        this.owner = owner;
        this.method = method;
        this.desc = desc;
        this.from = from;
        this.to = to;
    }

    public static RemoveCode readFrom(Buffer buffer) {
        return new RemoveCode(
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readUTF(),
                buffer.readInt(),
                buffer.readInt()
        );
    }

    public static RemoveCode[] readAll(Buffer buffer) {
        RemoveCode[] deletes = new RemoveCode[buffer.readInt()];
        Arrays.setAll(deletes, i -> readFrom(buffer));
        return deletes;
    }

    public String getOwner() {
        return owner;
    }

    public String getMethodName() {
        return method;
    }

    public String getDescriptor() {
        return desc;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
