package org.rspeer.injector.adapt;

import org.rspeer.injector.script.types.MethodInsertion;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Inserts new methods
 */
public class MethodInsertionsVisitor extends ClassVisitor {

    private final MethodInsertion[] methods;

    public MethodInsertionsVisitor(ClassVisitor delegate, MethodInsertion[] methods) {
        super(Opcodes.ASM7, delegate);
        this.methods = methods;
    }

    @Override
    public void visitEnd() {
        for (MethodInsertion m : methods) {
            MethodVisitor mv = super.visitMethod(m.getAccess(), m.getName(), m.getDescriptor(), null, null);
            mv.visitCode();
            m.getCode().accept(mv);
            mv.visitMaxs(m.getMaxStack(), m.getMaxLocals());
            mv.visitEnd();
        }
        super.visitEnd();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        String postfix = "";
        for (MethodInsertion method : methods) {
            builder.append(postfix);
            builder.append("-- Inserted method ");
            builder.append(method.getName());
            postfix = "\n";
        }
        return builder.toString();
    }
}
