package com.lzb.container;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.lzb.container.exception.CyclicDependencyException;
import com.lzb.container.exception.DependencyNotFoundException;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * Context->ContextConfig 改造成 builder 模式，实现构造和初始化context分离<br/>
 * Created on : 2023-10-01 17:25
 * @author lizebin
 */
@Slf4j
public class ContextConfig {

    private final Map<Class<?>, Provider<?>> newComponents = new HashMap<>();

    public <T> void bind(Class<T> componentClass, T instance) {
        newComponents.put(componentClass, () -> instance);
    }

    public <T> Optional<T> get(Class<T> componentClass) {
        return (Optional<T>) Optional.ofNullable(newComponents.get(componentClass)).map(Provider::get);
    }

    public <T, I extends T> void bind(Class<T> componentClass, Class<I> implementationClass) {
        Constructor<?> constructor = getConstructor(implementationClass);
        newComponents.put(componentClass, new CacheProvider<>(new ConstructorInjectProvider<>(constructor)));
    }

    class ConstructorInjectProvider<T> implements Provider<T> {

        private final Constructor<T> constructor;
        private boolean constructing = false;

        public ConstructorInjectProvider(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        @Override
        public T get() {
            if (constructing) {
                throw new CyclicDependencyException(constructor.getDeclaringClass());
            }
            try {
                constructing = true;
                Object[] dependencies = getInjectDependencies(constructor);
                return constructor.newInstance(dependencies);
            } catch (CyclicDependencyException e) {
                throw new CyclicDependencyException(constructor.getDeclaringClass(), e);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                constructing = false;
            }
        }

        private Object[] getInjectDependencies(Constructor<?> constructor) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            return Arrays.stream(parameterTypes)
                    .map(p -> ContextConfig.this.get(p)
                            .orElseThrow(() -> new DependencyNotFoundException(p, constructor.getDeclaringClass())))
                    .toArray();
        }

    }


    private <T, I extends T> Optional<Constructor<?>> getDefaultConstructor(Class<I> implementationClass) {
        return Arrays.stream(implementationClass.getConstructors()).filter(c -> c.getParameterCount() == 0).findFirst();
    }

    private <T, I extends T> Constructor<?> getConstructor(Class<I> implementationClass) {

        List<Constructor<?>> injectConstructors = getInjectConstructors(implementationClass).toList();
        long injectConstructorsCount = injectConstructors.size();
        if (injectConstructorsCount > 1) {
            throw new IllegalArgumentException("不支持多个构造函数");
        }
        Optional<Constructor<?>> defaultConstructor = getDefaultConstructor(implementationClass);
        if (injectConstructorsCount == 0 && defaultConstructor.isEmpty()) {
            throw new IllegalArgumentException("构造函数非法");
        }

        return injectConstructors.stream().findFirst().orElseGet(defaultConstructor::orElseThrow);
    }

    private <T, I extends T> Stream<Constructor<?>> getInjectConstructors(Class<I> implementationClass) {
        return Arrays.stream(implementationClass.getConstructors()).filter(this::byIsInjectConstructor);
    }

    private boolean byIsInjectConstructor(Constructor<?> constructor) {
        return constructor.isAnnotationPresent(Inject.class);
    }

}
