package com.lzb.container;

import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.stream.Stream;

public class ConstructorInjectProvider<T> implements ContextProvider<T> {

        private final Constructor<T> constructor;
        public ConstructorInjectProvider(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        private Object[] getInjectDependencies(Context context) {
            return Stream.of(constructor.getParameterTypes()).map(context::get).filter(Optional::isPresent).map(Optional::get).toArray();
        }

        @Override
        public T get(Context context) {
            try {
                return constructor.newInstance(getInjectDependencies(context));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }