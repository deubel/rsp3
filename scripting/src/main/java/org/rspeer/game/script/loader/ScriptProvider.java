package org.rspeer.game.script.loader;

import org.rspeer.game.script.Script;
import org.rspeer.game.script.ScriptMeta;

import java.lang.reflect.Modifier;
import java.util.function.Predicate;

public interface ScriptProvider extends Predicate<Class<?>> {

    ScriptBundle load();

    default ScriptBundle predefined() {
        return new ScriptBundle();
    }

    Script define(ScriptSource source);

    @Override
    default boolean test(Class<?> clazz) {
        return !Modifier.isAbstract(clazz.getModifiers())
                && Script.class.isAssignableFrom(clazz)
                && clazz.isAnnotationPresent(ScriptMeta.class);
    }
}
