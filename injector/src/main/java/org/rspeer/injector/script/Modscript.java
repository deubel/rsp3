package org.rspeer.injector.script;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.rspeer.injector.adapt.*;
import org.rspeer.injector.script.types.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Client modification script parser & processor
 */
public class Modscript {

    private static final boolean DEBUG = false;

    private final Map<String, ClassVisitor> adapters;
    private final Map<String, ClassWriter> writers;

    private Modscript(byte[] payload) {
        adapters = new HashMap<>();
        writers = new HashMap<>();
        process(payload);
    }

    public static Modscript load(Path path) throws IOException {
        return new Modscript(Files.readAllBytes(path));
    }

    public byte[] accept(byte[] data) {
        ClassReader reader = new ClassReader(data);
        String name = reader.getClassName();
        ClassVisitor adapter = adapters.get(name);
        if (adapter != null) {
            reader.accept(adapter, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
            return writers.get(name).toByteArray();
        }
        return data;
    }

    private ClassVisitor delegate(String clazz) {
        ClassVisitor delegate = adapters.get(clazz);
        if (delegate == null) {
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            writers.put(clazz, writer);
            return writer;
        }
        return delegate;
    }

    private void process(byte[] payload) {
        try (Buffer buffer = new Buffer(payload)) {

            buffer.assertValidMagic(Constants.HEADER);

            Map<String, String> attrs = new HashMap<>();

            int count = buffer.readInt();
            while (count-- > 0) {
                attrs.put(buffer.readUTF(), buffer.readUTF());
            }

            String mdtr = attrs.get(Constants.EVENT_MEDIATOR_KEY);
            count = buffer.readInt();
            while (count-- > 0) {

                if (!buffer.readBoolean()) {
                    continue;
                }

                Interface itf = Interface.readFrom(buffer);
                String cls = itf.getClassName();
                accept(cls, new InterfaceVisitor(delegate(cls), itf));

                if (buffer.readBoolean()) {
                    Subclass sub = Subclass.readFrom(buffer);
                    accept(sub.getName(), new SuperVisitor(delegate(sub.getName()), sub, attrs.get(Constants.SUBCLASS_PKG_KEY)));
                }

                accept(cls, new FieldInsertionsVisitor(delegate(cls), FieldInsertion.readAll(buffer)));
                accept(cls, new MethodInsertionsVisitor(delegate(cls), MethodInsertion.readAll(buffer)));
                accept(cls, new GettersVisitor(delegate(cls), Getter.readAll(buffer)));
                accept(cls, new SettersVisitor(delegate(cls), mdtr, Setter.readAll(buffer)));
                accept(cls, new InvokersVisitor(delegate(cls), Invoker.readAll(buffer)));

                for (Code code : Code.readAll(buffer)) {
                    accept(code.getOwner(), new InsertCodeVisitor(delegate(code.getOwner()), mdtr, code));
                }

                for (RemoveCode delete : RemoveCode.readAll(buffer)) {
                    accept(delete.getOwner(), new RemoveCodeVisitor(delegate(delete.getOwner()), delete));
                }
            }
        }
    }

    private void accept(String name, ClassVisitor visitor) {
        adapters.put(name, visitor);
        if (DEBUG) {
            String debug = visitor.toString();
            if (!debug.isEmpty()) {
                System.out.println(debug);
            }
        }
    }

    public interface Constants {

        int HEADER = Arrays.hashCode("mouse collector moderator".getBytes());

        String ITF_PKG_KEY = "itf_pkg";
        String CALLBACK_PKG_KEY = "cbk_pkg";
        String EVENT_MEDIATOR_KEY = "evt_mtr";
        String SUBCLASS_PKG_KEY = "sub_pkg";
    }
}
