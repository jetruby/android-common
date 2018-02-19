package com.jetruby.simple.mapper.processor;


import com.jetruby.simple.mapper.annotation.MapperDeclaration;
import com.jetruby.simple.mapper.annotation.MapperFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by anton_azaryan on 22.10.2017.
 */

public class SimpleMapperProcessor extends AbstractProcessor {

    private static boolean codeGenerated;

    /* ProcessingEnvironment */
    private Elements elementUtils;
    private Types typeUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        if (codeGenerated)
            return false;

        try {
            Set<? extends Element> factorySet = env.getElementsAnnotatedWith(MapperFactory.class);

            // do nothing if there is no @MapperFactory annotation
            if(factorySet.isEmpty())
                return false;

            if (factorySet.size() > 1) {
                e("Only single class can be annotated with @%s", MapperFactory.class.getSimpleName());
                return false;
            }

            TypeElement annotatedElement = (TypeElement) factorySet.iterator().next();
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                e(annotatedElement, "Only classes can be annotated with @%s",
                        MapperFactory.class.getSimpleName());
                return false;
            }
            MapperFactorySpec generateMapStructSpec = new MapperFactorySpec(elementUtils, annotatedElement);

            // generate mapper and factory class files
            generateMapperClassFiles(generateMapStructSpec.mappersSpecs());
            generateMapperFactoryClassFile(generateMapStructSpec);

        } catch (Exception e) {
            e.printStackTrace();
            e(e.getMessage());
            return false;
        }

        codeGenerated = true;
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(MapperFactory.class);
        annotations.add(MapperDeclaration.class);
        return annotations;
    }

    private void generateMapperClassFiles(List<MapperSpec> mapperSpecs) throws IOException {
        List<MapperFileRenderer> mapperRenders = getMapperRenders(mapperSpecs);
        for (MapperFileRenderer renderer : mapperRenders) {
            renderer.generateMapperFile().writeTo(filer);
        }
    }

    private void generateMapperFactoryClassFile(MapperFactorySpec mapperFactorySpec) throws IOException {
        new MapperFactoryRenderer("ObjMapperFactory", mapperFactorySpec)
                .generateMapperFactoryFile().writeTo(filer);
    }

    private List<MapperFileRenderer> getMapperRenders(List<MapperSpec> mapperSpecs) {
        List<MapperFileRenderer> mapperRenders = new ArrayList<>();
        for (MapperSpec spec : mapperSpecs) {
            mapperRenders.add(new MapperFileRenderer(spec));
        }
        return mapperRenders;
    }

    /* LOG MESSAGING */

    public void e(String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args));
    }

    public void e(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }
}
