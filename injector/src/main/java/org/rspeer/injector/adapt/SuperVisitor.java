package org.rspeer.injector.adapt;

import org.rspeer.injector.script.types.Subclass;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

/**
 * Sets the superclass of a target class
 */
public class SuperVisitor extends ClassVisitor {

    private final String newSuperName;
    private String superName;

    public SuperVisitor(ClassVisitor delegate, Subclass subclass, String pkg) {
        super(ASM7, delegate);
        newSuperName = pkg + subclass.getDefinition();
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.superName = superName;
        super.visit(version, access, name, signature, newSuperName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.equals("<init>")) {
            return new MethodVisitor(ASM7, super.visitMethod(access, name, desc, signature, exceptions)) {
                @Override
                public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                    if (opcode == INVOKESPECIAL && owner.equals(superName)) {
                        owner = newSuperName;
                    }
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            };
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public String toString() {
        return "-- Replaced supertype reference of " + superName + " with " + newSuperName;
    }
}
