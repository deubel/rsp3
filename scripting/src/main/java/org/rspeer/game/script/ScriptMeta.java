package org.rspeer.game.script;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptMeta {

    String name();

    double version() default 1.0;

    String developer() default "";

    String desc() default "";
}
