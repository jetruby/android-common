package com.jetruby.simple.mapper.processor;

import com.jetruby.simple.mapper.ObjMapper;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * Created by anton_azaryan on 22.10.2017.
 */

class MapperFileRenderer {

    final MapperSpec mapperSpec;

    final TypeSpec.Builder typeSpecBuilder;
    final AnnotationSpec.Builder annotationBuilder;

    MapperFileRenderer(MapperSpec mapperSpec) {
        this.mapperSpec = mapperSpec;

        ClassName sourceObject = ClassName.get(mapperSpec.sourceType());
        ClassName targetObject = ClassName.get(mapperSpec.targetType());
        ClassName mappers = ClassName.get("org.mapstruct.factory", "Mappers");
        ClassName mapper = ClassName.get(
                mapperSpec.packageName(),
                mapperSpec.mapperName());

        //generate interface
        ClassName annotationName = ClassName.get(mapperSpec
                .mapperAnnotation());

        ClassName entityMapperClass = ClassName.get(ObjMapper.class);
        TypeName superInterface = ParameterizedTypeName.get(entityMapperClass, sourceObject, targetObject);

        FieldSpec instance = FieldSpec.builder(mapper, "INSTANCE")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$T.getMapper($T.class)", mappers, mapper)
                .build();

        annotationBuilder = AnnotationSpec.builder(annotationName);
        typeSpecBuilder = TypeSpec.interfaceBuilder(mapperSpec.mapperName())
                .addModifiers(Modifier.PUBLIC)
                .addField(instance)
                .addSuperinterface(superInterface);
    }

    public void addMapperDependency(String name, String format, Object... args) {
        annotationBuilder.addMember(name, format, args);
    }

    public JavaFile generateMapperFile() {
        typeSpecBuilder.addAnnotation(annotationBuilder.build());
        return JavaFile.builder(mapperSpec.packageName(), typeSpecBuilder.build())
                .build();
    }
}
