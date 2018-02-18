package com.jetruby.simple.mapper.processor;

import com.jetruby.simple.mapper.annotation.MapperDeclaration;
import com.jetruby.simple.mapper.annotation.MapperFactory;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;

/**
 * Created by anton_azaryan on 22.10.2017.
 */

class MapperFactorySpec {

    private final Elements elementUtils;
    private TypeElement annotatedElement;
    private TypeElement mapperAnnotation;

    private final List<MapperSpec> mappersSpecs;
    private final List<TypeElement> allMappingClasses;

    MapperFactorySpec(Elements elementUtils, TypeElement element) {
        this.elementUtils = elementUtils;
        annotatedElement = element;
        String packageName = elementUtils.getPackageOf(element).toString();

        MapperFactory generateAnnotation = element.getAnnotation(MapperFactory.class);
        try { //hack, if class not compiled yet
            mapperAnnotation = elementUtils.getTypeElement(generateAnnotation.mapperAnnotation().getName());
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            mapperAnnotation = (TypeElement) classTypeMirror.asElement();
        }

        MapperDeclaration[] mapperAnnotations = generateAnnotation.mappers();

        final int annotationsCount = mapperAnnotations.length;
        allMappingClasses = new ArrayList<>(annotationsCount * 2);
        mappersSpecs = new ArrayList<>(annotationsCount);

        for (MapperDeclaration annotation : mapperAnnotations) {
            TypeElement fromClass = getMappingClassFromAnnotation("from", annotation);
            TypeElement toClass = getMappingClassFromAnnotation("to", annotation);
            allMappingClasses.add(validateMappingClass(fromClass));
            allMappingClasses.add(validateMappingClass(toClass));

            mappersSpecs.addAll(getMapperSpecsFromAnnotation(
                    packageName,
                    mapperAnnotation,
                    fromClass,
                    toClass,
                    annotation));
        }
    }

    private TypeElement getMappingClassFromAnnotation(String methodName, MapperDeclaration annotation) {
        try { //hack
            switch (methodName) {
                case "from":
                    return elementUtils.getTypeElement(annotation.from().getName());
                case "to":
                    return elementUtils.getTypeElement(annotation.to().getName());
            }
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            return (TypeElement) classTypeMirror.asElement();
        }
        throw new IllegalArgumentException("Failed to get mapping class from MapperDeclaration!");
    }

    private List<MapperSpec> getMapperSpecsFromAnnotation(String packageName,
                                                          TypeElement mapperAnnotation,
                                                          TypeElement fromClass,
                                                          TypeElement toClass,
                                                          MapperDeclaration annotation) {
        List<MapperSpec> specs = new ArrayList<>(2);

        specs.add(new MapperSpec(
                packageName,
                mapperAnnotation,
                fromClass,
                toClass));

        if (annotation.reverse()) {
            specs.add(new MapperSpec(
                    packageName,
                    mapperAnnotation,
                    toClass,
                    fromClass));
        }
        return specs;
    }

    private TypeElement validateMappingClass(TypeElement mappingClass) {
        if (mappingClass.getQualifiedName().toString().equals(void.class.getName()))
            throw new IllegalArgumentException("Void.class can not be mapped!" +
                    " You must specify \"from\" and \"to\" parameters in " +
                    "MapStructMapper.class annotation.");

        return mappingClass;
    }

    public TypeElement annotatedElement() {
        return annotatedElement;
    }

    public List<MapperSpec> mappersSpecs() {
        return mappersSpecs;
    }

    public List<TypeElement> allMappingClasses() {
        return allMappingClasses;
    }
}
