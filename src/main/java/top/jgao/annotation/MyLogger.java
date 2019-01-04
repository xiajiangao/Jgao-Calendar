package top.jgao.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyLogger {

    Type type() default Type.SEARCH;

    String value() default "";

    boolean isInsertTable() default false;

    // String level() default "INFO";
    // public enum LeveL {
    // OFF, FATAL, ERROR, WARM, INFO, DEBUG, TRACE, ALL;
    // }

    public enum Type {
        ADD, UPDATE, DELETE, SEARCH, INDEX;
    }
}
