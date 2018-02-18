package com.jetruby.simple.mapper.processor;

import com.jetruby.simple.mapper.ObjMapper;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by anton_azaryan on 22.10.2017.
 */

class MapperFactoryRenderer {

    private String packageName;
    private final List<IfBlock> ifBlocks;
    private final TypeSpec factoryClassSpec;

    MapperFactoryRenderer(String factoryClassName, MapperFactorySpec mapperFactorySpec) {
        TypeElement factory = mapperFactorySpec.annotatedElement();
        List<MapperSpec> mapperSpecs = mapperFactorySpec.mappersSpecs();
        ifBlocks = new ArrayList<>(mapperSpecs.size());
        for (MapperSpec spec : mapperSpecs) {
            if (packageName == null) {
                packageName = spec.packageName();
            }
            ifBlocks.add(new IfBlock(spec));
        }

        TypeVariableName typeS = TypeVariableName.get("S");
        TypeVariableName typeT = TypeVariableName.get("T");
        ClassName entityMapper = ClassName.get(ObjMapper.class);
        ClassName clazz = ClassName.get(Class.class);
        ParameterizedTypeName genericEntityMapper = ParameterizedTypeName.get(entityMapper, typeS, typeT);
        ParameterizedTypeName from = ParameterizedTypeName.get(clazz, typeS);
        ParameterizedTypeName to = ParameterizedTypeName.get(clazz, typeT);

        MethodSpec.Builder createMethodBuilder = MethodSpec.methodBuilder("create")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(typeS)
                .addTypeVariable(typeT)
                .addParameter(from, "from")
                .addParameter(to, "to")
                .returns(genericEntityMapper)
                .addCode("\n")
                .addStatement("ObjMapper<S, T> mapper = super.create(from, to)")
                .beginControlFlow("if (mapper != null)")
                .addStatement("return mapper")
                .endControlFlow()
                .addCode("\n");

        for (IfBlock block : ifBlocks) {
            createMethodBuilder.addCode(block.getCodeBlock())
                    .addCode("\n");
        }

        createMethodBuilder.addStatement("throw new IllegalArgumentException(" +
                "String.format(\"Cannot find mapper class %s.class -> %s.class!\",\n" +
                "from.getSimpleName(),\n" +
                "to.getSimpleName()))");

        factoryClassSpec = TypeSpec.classBuilder(factoryClassName)
                .addModifiers(Modifier.PUBLIC)
                .superclass(TypeName.get(factory.asType()))
                .addMethod(createMethodBuilder.build())
                .build();
    }

    public JavaFile generateMapperFactoryFile() {
        return JavaFile.builder(packageName, factoryClassSpec)
                .build();
    }

    private class IfBlock {

        final CodeBlock codeBlock;

        IfBlock(MapperSpec mapperSpec) {
            ClassName mapper = ClassName.get(
                    mapperSpec.packageName(),
                    mapperSpec.mapperName());

            codeBlock = CodeBlock.builder()
                    .beginControlFlow("if(from == $T.class && to == $T.class)",
                            mapperSpec.sourceType(), mapperSpec.targetType())
                    .addStatement("return (ObjMapper<S, T>) $T.INSTANCE", mapper)
                    .endControlFlow()
                    .build();
        }

        CodeBlock getCodeBlock() {
            return codeBlock;
        }
    }

}
