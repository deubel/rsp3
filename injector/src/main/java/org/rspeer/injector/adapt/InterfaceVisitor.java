package org.rspeer.injector.adapt;

import org.rspeer.injector.script.types.Interface;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Makes the target class implement an interface
 */
public class InterfaceVisitor extends ClassVisitor {

    private final String name;

    public InterfaceVisitor(ClassVisitor delegate, Interface itf) {
        super(Opcodes.ASM7, delegate);
        this.name = itf.getDefinedPackage() + "RS" + itf.getDefinedName();
    }

    @Override
    public void visit(int version, int access, String name, String signature,
            String superName, String[] interfaces) {
        String[] itfs = new String[interfaces.length + 1];
        System.arraycopy(interfaces, 0, itfs, 0, interfaces.length);
        itfs[interfaces.length] = this.name;
        super.visit(version, access, name, signature, superName, itfs);
    }

    @Override
    public String toString() {
        return "- Injected interface " + name;
    }
}