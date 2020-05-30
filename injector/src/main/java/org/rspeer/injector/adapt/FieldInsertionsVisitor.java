package org.rspeer.injector.adapt;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.rspeer.injector.script.types.FieldInsertion;

import static org.objectweb.asm.Opcodes.*;

/**
 * Inserts new field members, with the option to inject getters or setters along with it
 */
public class FieldInsertionsVisitor extends ClassVisitor {

    private final FieldInsertion[] fields;

    public FieldInsertionsVisitor(ClassVisitor delegate, FieldInsertion[] fields) {
        super(Opcodes.ASM7, delegate);
        this.fields = fields;
    }

    @Override
    public void visitEnd() {
        for (FieldInsertion f : fields) {
            super.visitField(f.getAccess(), f.getName(), f.getDescriptor(), null, null);

            if (f.isInjectedWithGetter()) {
                injectGetter(f);
            }

            if (f.isInjectedWithSetter()) {
                injectSetter(f);
            }
        }

        super.visitEnd();
    }

    private void injectSetter(FieldInsertion f) {
        MethodVisitor mv = super.visitMethod(ACC_PUBLIC, f.createSetterDefinition(), "(" + f.getDescriptor() + ")V", null, null);
        mv.visitCode();

        if (f.isVirtual()) {
            mv.visitVarInsn(ALOAD, 0);
        }

        mv.visitVarInsn(Type.getType(f.getDescriptor()).getOpcode(ILOAD), 1);
        mv.visitFieldInsn(f.isVirtual() ? PUTFIELD : PUTSTATIC, f.getOwner(), f.getName(), f.getDescriptor());
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private void injectGetter(FieldInsertion f) {
        MethodVisitor mv = super.visitMethod(ACC_PUBLIC, f.createGetterDefinition(), "()" + f.getDescriptor(), null, null);
        mv.visitCode();

        if (f.isVirtual()) {
            mv.visitVarInsn(ALOAD, 0);
        }

        mv.visitFieldInsn(f.isVirtual() ? GETFIELD : GETSTATIC, f.getOwner(), f.getName(), f.getDescriptor());
        mv.visitInsn(Type.getType(f.getDescriptor()).getOpcode(IRETURN));
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        String postfix = "";
        for (FieldInsertion field : fields) {
            builder.append(postfix);
            builder.append("-- Inserted field ");
            builder.append(field.getName());
            postfix = "\n";
        }
        return builder.toString();
    }
}
