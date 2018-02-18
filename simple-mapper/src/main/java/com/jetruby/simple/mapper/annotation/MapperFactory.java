package com.jetruby.simple.mapper.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by anton_azaryan on 21.10.2017.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface MapperFactory {

    MapperDeclaration[] mappers();

    Class<? extends Annotation> mapperAnnotation() default Annotation.class;

}
