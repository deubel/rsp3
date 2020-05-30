package org.rspeer.injector.adapt;

import org.rspeer.injector.script.types.RemoveCode;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

/**
 * Removes code
 */
//TODO not currently implemented (or used so not bothered to fix yet)
public class RemoveCodeVisitor extends ClassVisitor {

    private final RemoveCode code;

    public RemoveCodeVisitor(ClassVisitor delegate, RemoveCode code) {
        super(ASM7, delegate);
        this.code = code;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor delegate = super.visitMethod(access, name, desc, signature, exceptions);
        if (!name.equals(code.getMethodName()) || !desc.equals(code.getDescriptor())) {
            return delegate;
        }

        return new OverrideCodeVisitor(delegate);
    }

    private class OverrideCodeVisitor extends MethodVisitor {

        private int offset = 0;

        private OverrideCodeVisitor(MethodVisitor delegate) {
            super(ASM7, delegate);
        }

        private boolean delete(int offset) {
            if (offset >= code.getFrom() && offset <= code.getTo()) {
                super.visitInsn(NOP);
                return true;
            }
            return false;
        }

        @Override
        public void visitInsn(int opcode) {
            if (delete(offset++)) {
                return;
            }
            super.visitInsn(opcode);
        }

        public void visitIntInsn(int opcode, int operand) {
            if (delete(offset++)) {
                return;
            }
            super.visitIntInsn(opcode, operand);
        }

        public void visitVarInsn(int opcode, int var) {
            if (delete(offset++)) {
                return;
            }
            super.visitVarInsn(opcode, var);
        }

        public void visitTypeInsn(int opcode, String type) {
            if (delete(offset++)) {
                return;
            }
            super.visitTypeInsn(opcode, type);
        }

        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
            if (delete(offset++)) {
                return;
            }
            super.visitFieldInsn(opcode, owner, name, descriptor);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if (delete(offset++)) {
                return;
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }

        public void visitJumpInsn(int opcode, Label label) {
            if (delete(offset++)) {
                return;
            }
            super.visitJumpInsn(opcode, label);
        }

        public void visitLabel(Label label) {
            if (delete(offset++)) {
                return;
            }
            super.visitLabel(label);
        }

        public void visitLdcInsn(Object value) {
            if (delete(offset++)) {
                return;
            }
            super.visitLdcInsn(value);
        }

        public void visitIincInsn(int var, int increment) {
            if (delete(offset++)) {
                return;
            }
            super.visitIincInsn(var, increment);
        }

        public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
            if (delete(offset++)) {
                return;
            }
            super.visitTableSwitchInsn(min, max, dflt, labels);
        }

        public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
            if (delete(offset++)) {
                return;
            }
            super.visitLookupSwitchInsn(dflt, keys, labels);
        }

        public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
            if (delete(offset++)) {
                return;
            }
            super.visitMultiANewArrayInsn(descriptor, numDimensions);
        }
    }
}
