package com.jetruby.simple.mapper;

import java.util.List;

import io.reactivex.functions.Function;


/**
 * Created by anton_azaryan on 21.10.2017.
 */

public interface ObjMapper<S, T> extends Function<S, T> {

    List<T> applyList(List<S> from);

    interface Factory {

        <S, T> ObjMapper<S, T> create(Class<S> from, Class<T> to);

        final class Instance {

            private static ObjMapper.Factory INSTANCE;

            private Instance() {
            }

            public static ObjMapper.Factory get(Class<? extends Factory> factoryClass) {
                ObjMapper.Factory factory = INSTANCE;
                if (factory == null) {
                    synchronized (Instance.class) {
                        factory = INSTANCE;
                        if (factory == null) {
                            factory = obtainFactory(factoryClass);
                            INSTANCE = factory;
                        }
                    }
                }
                return INSTANCE;
            }

            private static ObjMapper.Factory obtainFactory(Class<? extends Factory> factoryClass) {
                try {
                    String packageName = factoryClass.getPackage().getName();
                    return (ObjMapper.Factory) Class.forName(packageName + ".ObjMapperFactory")
                            .newInstance();
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException("ObjMapperFactory.class not found. " +
                            "\nMay be you forgot to annotate factory class with annotation: @MapperFactory." +
                            "\nOr try to add annotationProcessor dependency to your gradle file.");
                }
            }
        }
    }
}
