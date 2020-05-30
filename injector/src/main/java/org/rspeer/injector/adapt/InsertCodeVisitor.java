package org.rspeer.injector.adapt;

import org.rspeer.injector.script.types.Code;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

public class InsertCodeVisitor extends ClassVisitor {

    private final String ctx;
    private final Code code;

    public InsertCodeVisitor(ClassVisitor delegate, String ctx, Code code) {
        super(ASM7, delegate);
        this.ctx = ctx;
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

    @Override
    public String toString() {
        return "-- Inserted code into " + code.getDefinition();
    }

    private class OverrideCodeVisitor extends MethodVisitor {

        private int offset = 0;

        private OverrideCodeVisitor(MethodVisitor delegate) {
            super(ASM7, delegate);
        }

        private void visit(int offset) {
            if (offset == code.getIndex()) {
                accept();
            }
        }

        @Override
        public void visitInsn(int opcode) {
            if (code.getIndex() == Code.BEFORE_RETURNS && opcode >= IRETURN && opcode <= RETURN) {
                accept();
            }
            visit(offset++);
            super.visitInsn(opcode);
        }

        public void visitIntInsn(int opcode, int operand) {
            visit(offset++);
            super.visitIntInsn(opcode, operand);
        }

        public void visitVarInsn(int opcode, int var) {
            visit(offset++);
            super.visitVarInsn(opcode, var);
        }

        public void visitTypeInsn(int opcode, String type) {
            visit(offset++);
            super.visitTypeInsn(opcode, type);
        }

        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
            visit(offset++);
            super.visitFieldInsn(opcode, owner, name, descriptor);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            visit(offset++);
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }

        public void visitJumpInsn(int opcode, Label label) {
            visit(offset++);
            super.visitJumpInsn(opcode, label);
        }

        public void visitLabel(Label label) {
            visit(offset++);
            super.visitLabel(label);
        }

        public void visitLdcInsn(Object value) {
            visit(offset++);
            super.visitLdcInsn(value);
        }

        public void visitIincInsn(int var, int increment) {
            visit(offset++);
            super.visitIincInsn(var, increment);
        }

        public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
            visit(offset++);
            super.visitTableSwitchInsn(min, max, dflt, labels);
        }

        public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
            visit(offset++);
            super.visitLookupSwitchInsn(dflt, keys, labels);
        }

        public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
            visit(offset++);
            super.visitMultiANewArrayInsn(descriptor, numDimensions);
        }

        private void accept() {
            if (code.getType() == Code.TYPE_ARGS_CALLBACK) {
                visitCallback();
            } else if (code.getType() == Code.TYPE_CODE) {
                code.getReader().accept(mv);
            }
        }

        private void visitCallback() {
            String desc = code.getNormalizedDescriptor();

            visitFieldInsn(GETSTATIC, "client", "eventMediator", "L" + ctx + ";");
            loadArgs(desc);
            visitMethodInsn(INVOKEVIRTUAL, ctx, code.getDefinition(), desc, false);
        }

        private void loadArgs(String desc) {
            int load = 0;
            for (Type type : Type.getArgumentTypes(desc)) {
                visitVarInsn(type.getOpcode(ILOAD), load++);
            }
        }
    }
}
