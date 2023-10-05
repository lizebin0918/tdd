package com.lzb.container;

import java.lang.reflect.Constructor;
import java.util.stream.Stream;

import com.lzb.container.exception.CyclicDependencyException;
import com.lzb.container.exception.DependencyNotFoundException;

public class ConstructorInjectProvider<T> implements ContextProvider<T> {

        private final Constructor<T> constructor;
        private boolean constructing = false;
        private final Class<T> componentType;

        public ConstructorInjectProvider(Constructor<T> constructor) {
            this.constructor = constructor;
            this.componentType = constructor.getDeclaringClass();
        }

        private Object[] getInjectDependencies(Context context) {
            return Stream.of(constructor.getParameterTypes())
                    .map(p -> context.get(p)
                            .orElseThrow(() -> new DependencyNotFoundException(p, componentType)))
                    .toArray();
        }

        @Override
        public T get(Context context) {
            if (constructing) {
                throw new CyclicDependencyException(componentType);
            }
            try {
                constructing = true;
                return constructor.newInstance(getInjectDependencies(context));
            } catch (CyclicDependencyException e) {
                throw new CyclicDependencyException(componentType, e);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                constructing = false;
            }
        }
    }