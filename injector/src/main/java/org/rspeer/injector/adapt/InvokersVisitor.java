package org.rspeer.injector.adapt;

import org.rspeer.injector.script.types.Invoker;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

/**
 * Inserts methods that invoke other methods
 */
public class InvokersVisitor extends ClassVisitor {

    private final Invoker[] invokers;

    public InvokersVisitor(ClassVisitor delegate, Invoker[] invokers) {
        super(ASM7, delegate);
        this.invokers = invokers;
    }

    @Override
    public void visitEnd() {
        for (Invoker iv : invokers) {
            if (iv.isVirtual() && iv.getDefinition().equals(iv.getName())) {
                continue;
            }

            Type[] expectedArgs = Type.getArgumentTypes(iv.getExpectedDescriptor());
            Type[] normalizedArgs = Type.getArgumentTypes(iv.getNormalizedDescriptor());

            MethodVisitor mv = super.visitMethod(ACC_PUBLIC, iv.getDefinition(), iv.getNormalizedDescriptor(), null, null);
            mv.visitCode();

            if (iv.isVirtual()) {
                mv.visitVarInsn(ALOAD, 0);
            }

            int locals = args(mv, expectedArgs, normalizedArgs);
            call(mv, iv);
            terminate(mv, iv, locals);
        }
        super.visitEnd();
    }

    private int args(MethodVisitor mv, Type[] expectedArgs, Type[] normalizedArgs) {
        int local = 1;
        for (int i = 0; i < expectedArgs.length; i++) {
            Type expected = expectedArgs[i];
            Type normalized = normalizedArgs[i];
            mv.visitVarInsn(normalized.getOpcode(ILOAD), local);

            String expectedInternal = expected.getInternalName();
            if (!expectedInternal.equals(normalized.getInternalName())) {
                mv.visitTypeInsn(CHECKCAST, expectedInternal);
            }

            local += expected.getSize();
        }
        return local;
    }

    private void call(MethodVisitor mv, Invoker iv) {
        if (iv.getPredicate() != Integer.MAX_VALUE) {
            mv.visitLdcInsn(iv.getPredicate());
        }

        int acc = iv.isVirtual() ? INVOKEVIRTUAL : INVOKESTATIC;
        mv.visitMethodInsn(acc, iv.getOwner(), iv.getName(), iv.getDescriptor(), iv.isInterface());
    }

    private void terminate(MethodVisitor mv, Invoker iv, int locals) {
        Type expectedReturn = Type.getReturnType(iv.getExpectedDescriptor());
        mv.visitInsn(expectedReturn.getOpcode(IRETURN));
        mv.visitMaxs(1, locals);
        mv.visitEnd();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        String postfix = "";
        for (Invoker invoker : invokers) {
            builder.append(postfix);
            builder.append("-- Inserted invoker ");
            builder.append(invoker.getDefinition());
            postfix = "\n";
        }
        return builder.toString();
    }
}
