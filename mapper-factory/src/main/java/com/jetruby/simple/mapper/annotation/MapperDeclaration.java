package com.jetruby.simple.mapper.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by anton_azaryan on 21.10.2017.
 */

@Retention(RetentionPolicy.SOURCE)
public @interface MapperDeclaration {

    Class<?> from() default void.class;

    Class<?> to() default void.class;

    boolean reverse() default false;
}
