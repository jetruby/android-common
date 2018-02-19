package com.jetruby.simple.mapper.processor;

import javax.lang.model.element.TypeElement;

/**
 * Created by anton_azaryan on 22.10.2017.
 */

class MapperSpec {

    private final String mapperName;
    private final String packageName;
    private final TypeElement mapperAnnotation;
    private final TypeElement sourceType;
    private final TypeElement targetType;

    public MapperSpec(String packageName,
                      TypeElement mapperAnnotation,
                      TypeElement sourceType,
                      TypeElement targetType) {
        this.packageName = packageName;
        this.mapperAnnotation = mapperAnnotation;
        this.sourceType = sourceType;
        this.targetType = targetType;

        mapperName = sourceType.getSimpleName()
                + "To"
                + targetType.getSimpleName()
                + "Mapper";
    }

    public String packageName() {
        return packageName;
    }

    public TypeElement mapperAnnotation() {
        return mapperAnnotation;
    }

    public TypeElement sourceType() {
        return sourceType;
    }

    public TypeElement targetType() {
        return targetType;
    }

    public String mapperName() {
        return mapperName;
    }
}
