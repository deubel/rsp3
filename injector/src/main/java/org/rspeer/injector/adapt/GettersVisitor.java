package org.rspeer.injector.adapt;

import org.rspeer.injector.script.types.Getter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

/**
 * Injects getter methods returning fields
 */
public class GettersVisitor extends ClassVisitor {

    private final Getter[] getters;

    public GettersVisitor(ClassVisitor delegate, Getter[] getters) {
        super(ASM7, delegate);
        this.getters = getters;
    }

    @Override
    public void visitEnd() {
        for (Getter g : getters) {
            MethodVisitor mv = super.visitMethod(ACC_PUBLIC, g.getDefinition(), g.getReturnDescriptor(), null, null);
            mv.visitCode();
            load(mv, g);
            multiply(mv, g);
            terminate(mv, g);
        }
        super.visitEnd();
    }

    private void load(MethodVisitor mv, Getter g) {
        if (g.isVirtual()) {
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, g.getOwner(), g.getName(), g.getDescriptor());
        } else {
            mv.visitFieldInsn(GETSTATIC, g.getOwner(), g.getName(), g.getDescriptor());
        }
    }

    private void multiply(MethodVisitor mv, Getter g) {
        if (g.getDecoder() != 0) {
            if (g.isWide()) {
                mv.visitLdcInsn(g.getDecoder());
                mv.visitInsn(LMUL);
            } else {
                mv.visitLdcInsn((int) g.getDecoder());
                mv.visitInsn(IMUL);
            }
        }
    }

    private void terminate(MethodVisitor mv, Getter g) {
        mv.visitInsn(Type.getReturnType(g.getReturnDescriptor()).getOpcode(IRETURN));
        mv.visitEnd();
        mv.visitMaxs(2, 2);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        String postfix = "";
        for (Getter getter : getters) {
            builder.append(postfix);
            builder.append("-- Inserted getter ");
            builder.append(getter.getDefinition());
            postfix = "\n";
        }
        return builder.toString();
    }
}
