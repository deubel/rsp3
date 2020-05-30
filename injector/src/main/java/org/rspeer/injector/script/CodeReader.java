package org.rspeer.injector.script;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Serialized code reader
 */
public class CodeReader {

    private static final int INSN = 0;
    private static final int INT_INSN = 1;
    private static final int VAR_INSN = 2;
    private static final int TYPE_INSN = 3;
    private static final int FIELD_INSN = 4;
    private static final int METHOD_INSN = 5;
    private static final int INVOKE_DYNAMIC_INSN = 6;
    private static final int JUMP_INSN = 7;
    private static final int LABEL = 8;
    private static final int LDC_INSN = 9;
    private static final int IINC_INSN = 10;
    private static final int TABLESWITCH_INSN = 11;
    private static final int LOOKUPSWITCH_INSN = 12;
    private static final int MULTIANEWARRAY_INSN = 13;
    private static final int FRAME = 14;
    private static final int LINE = 15;

    private final Buffer code;
    private final List<Consumer<MethodVisitor>> visits;

    private short ptr;
    private boolean visited;

    public CodeReader(Buffer code) {
        this.code = code;
        visits = new ArrayList<>();
        if ((ptr = code.readShort()) > 0) {
            readCode();
        }
    }

    private void readCode() {
        Label[] labels = new Label[code.readShort()];
        for (short i = 0; i < labels.length; ++i) {
            labels[i] = new Label();
        }

        while (ptr-- > 0) {

            int type = code.readShort();
            int opcode = code.readShort();

            switch (type) {

                case INSN: {
                    visits.add(visitor -> visitor.visitInsn(opcode));
                    break;
                }

                case INT_INSN: {
                    short operand = code.readShort();
                    visits.add(visitor -> visitor.visitIntInsn(opcode, operand));
                    break;
                }

                case VAR_INSN: {
                    short var = code.readShort();
                    visits.add(visitor -> visitor.visitVarInsn(opcode, var));
                    break;
                }

                case TYPE_INSN: {
                    String cls = code.readUTF();
                    visits.add(visitor -> visitor.visitTypeInsn(opcode, cls));
                    break;
                }

                case FIELD_INSN: {
                    String owner = code.readUTF();
                    String name = code.readUTF();
                    String desc = code.readUTF();
                    visits.add(visitor -> visitor.visitFieldInsn(opcode, owner, name, desc));
                    break;
                }

                case METHOD_INSN: {
                    String owner = code.readUTF();
                    String name = code.readUTF();
                    String desc = code.readUTF();
                    boolean itf = code.readBoolean();
                    visits.add(visitor -> visitor.visitMethodInsn(opcode, owner, name, desc, itf));
                    break;
                }

                case JUMP_INSN: {
                    short index = code.readShort();
                    visits.add(visitor -> visitor.visitJumpInsn(opcode, labels[index]));
                    break;
                }

                case LDC_INSN: {
                    int def = code.readByte();
                    if (def == 1) {
                        int operand = code.readInt();
                        visits.add(visitor -> visitor.visitLdcInsn(operand));
                    } else if (def == 2) {
                        String operand = code.readUTF();
                        visits.add(visitor -> visitor.visitLdcInsn(Float.parseFloat(operand)));
                    } else if (def == 3) {
                        long operand = code.readLong();
                        visits.add(visitor -> visitor.visitLdcInsn(operand));
                    } else if (def == 4) {
                        String operand = code.readUTF();
                        visits.add(visitor -> visitor.visitLdcInsn(Double.parseDouble(operand)));
                    } else if (def == 5) {
                        String operand = code.readUTF();
                        visits.add(visitor -> visitor.visitLdcInsn(operand));
                    } else if (def == 6) {
                        String operand = code.readUTF();
                        visits.add(visitor -> visitor.visitLdcInsn(Type.getType(operand)));
                    }
                    break;
                }

                case IINC_INSN: {
                    short var = code.readShort();
                    int incr = code.readInt();
                    visits.add(visitor -> visitor.visitIincInsn(var, incr));
                    break;
                }

                case MULTIANEWARRAY_INSN: {
                    String desc = code.readUTF();
                    byte dims = code.readByte();
                    visits.add(visitor -> visitor.visitMultiANewArrayInsn(desc, dims));
                    break;
                }

                case LABEL: {
                    short index = code.readShort();
                    visits.add(visitor -> visitor.visitLabel(labels[index]));
                    break;
                }
            }
        }
    }

    public void accept(MethodVisitor visitor) {
        if (visited) {
            return;
        }
        Collections.reverse(visits);
        visits.forEach(task -> task.accept(visitor));
        visited = true;
    }
}