package com.lzb.di;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static java.util.Arrays.stream;

/**
 * <br/>
 * Created on : 2022-07-31 22:20
 *
 * @author mac
 */
public class Context {

    private Map<Class<?>, Provider<?>> providers = new HashMap<>();

    public <T> void bind(Class<T> type, T instance) {
        providers.put(type, () -> instance);
    }

    public <T, I extends T> void bind(Class<T> componentType, Class<I> implementation) {
        Constructor<I> injectConstructor = getInjectConstructor(implementation);
        providers.put(componentType, getTypeProvider(injectConstructor));
    }

    private <T> Provider<T> getTypeProvider(Constructor<T> injectConstructor) {
        return new ConstructorInjectionProvider<>(injectConstructor);
    }

    class ConstructorInjectionProvider<T> implements Provider<T> {

        private final Constructor<T> injectConstructor;
        private boolean constructing = false;

        public ConstructorInjectionProvider(Constructor<T> injectConstructor) {
            this.injectConstructor = injectConstructor;
        }

        @Override
        public T get() {
            if (constructing) throw new CyclicDependenciesFoundException();
            try {
                constructing = true;
                Object[] dependencies = stream(injectConstructor.getParameters())
                        .map(p -> Context.this.get(p.getType()).orElseThrow(DependencyNotFoundException::new))
                        .toArray(Object[]::new);
                return injectConstructor.newInstance(dependencies);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                constructing = false;
            }
        }
    }

    private <T> Constructor<T> getInjectConstructor(Class<T> implementation) {
        List<Constructor<?>> injectConstructors = stream(implementation.getConstructors())
                .filter(c -> c.isAnnotationPresent(Inject.class)).toList();

        if (injectConstructors.size() > 1) throw new IllegalComponentException();

        return (Constructor<T>) injectConstructors.stream().findFirst().orElseGet(() -> {
            try {
                return implementation.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new IllegalComponentException();
            }
        });
    }

    public <T> Optional<T> get(Class<T> componentClass) {
        return Optional.ofNullable(providers.get(componentClass)).map(provider -> (T) provider.get());
    }
}
