package org.rspeer.injector.script.types;

import org.objectweb.asm.Opcodes;

public abstract class MemberInsertion {

    private final int access;

    private final String owner;

    protected final String name;
    protected final String desc;

    protected MemberInsertion(int access, String owner, String name, String desc) {
        this.access = access;
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }

    public int getAccess() {
        return access;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return desc;
    }

    public boolean isVirtual() {
        return (access & Opcodes.ACC_STATIC) == 0;
    }
}
