package org.rspeer.injector.adapt;

import org.rspeer.injector.script.types.Setter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

/**
 * Injects setter methods for fields
 */
public class SettersVisitor extends ClassVisitor {

    private final String ctx;
    private final Setter[] setters;

    public SettersVisitor(ClassVisitor delegate, String ctx, Setter[] setters) {
        super(ASM7, delegate);
        this.ctx = ctx;
        this.setters = setters;
    }

    @Override
    public void visitEnd() {
        for (Setter s : setters) {
            if (!s.getDescriptor().startsWith("[")) {
                visitSetter(s);
            }
        }
        super.visitEnd();
    }

    private void visitSetter(Setter s) {
        MethodVisitor mv = super.visitMethod(ACC_PUBLIC, s.getDefinition(), s.getReturnDescriptor(), null, null);
        mv.visitCode();
        load(mv, s);
        multiply(mv, s);
        callback(mv, s);
        terminate(mv, s);
    }

    private void load(MethodVisitor mv, Setter s) {
        if (s.isVirtual()) {
            mv.visitVarInsn(ALOAD, 0);
        }
        mv.visitVarInsn(Type.getType(s.getDescriptor()).getOpcode(ILOAD), 1);
    }

    private void multiply(MethodVisitor mv, Setter s) {
        if (s.getEncoder() != 0) {
            if (s.isWide()) {
                mv.visitLdcInsn(s.getEncoder());
                mv.visitInsn(LMUL);
            } else {
                mv.visitLdcInsn((int) s.getEncoder());
                mv.visitInsn(IMUL);
            }
        }
        mv.visitFieldInsn(s.isVirtual() ? PUTFIELD : PUTSTATIC, s.getOwner(), s.getName(), s.getDescriptor());
    }

    private void callback(MethodVisitor mv, Setter s) {
        if (!s.isProxy()) {
            return;
        }

        String postfix = s.getDefinition().substring(3);
        String callDesc = "(" + s.getArgumentType() + s.getArgumentType() + ")V";

        mv.visitFieldInsn(GETSTATIC, "client", "eventMediator", "L" + ctx + ";");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, s.getOwner(), "get" + postfix, "()" + s.getArgumentType(), false);
        mv.visitVarInsn(Type.getType(s.getArgumentType()).getOpcode(ILOAD), 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, ctx, "notify" + postfix, callDesc, false);
    }

    private void terminate(MethodVisitor mv, Setter s) {
        mv.visitInsn(Type.getReturnType(s.getReturnDescriptor()).getOpcode(IRETURN));
        mv.visitEnd();
        mv.visitMaxs(3, 3);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        String postfix = "";
        for (Setter setter : setters) {
            builder.append(postfix);
            builder.append("-- Inserted setter ");
            builder.append(setter.getDefinition());
            postfix = "\n";
        }
        return builder.toString();
    }
}
