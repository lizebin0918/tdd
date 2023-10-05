package com.lzb.container;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.lzb.container.exception.CyclicDependencyException;
import com.lzb.container.exception.DependencyNotBindException;
import com.lzb.container.exception.DependencyNotFoundException;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

/**
 * Context->ContextConfig 改造成 builder 模式，实现构造和初始化context分离<br/>
 * Created on : 2023-10-01 17:25
 * @author lizebin
 */
@Slf4j
public class ContextConfig {

    private final Map<Class<?>, ContextProvider<?>> newComponents = new HashMap<>();
    private final Map<Class<?>, Set<Class<?>>> dependencies = new HashMap<>();

    public <T> void bind(Class<T> componentClass, T instance) {
        newComponents.put(componentClass, context -> instance);
        dependencies.put(componentClass, Set.of());
    }

    public <T, I extends T> void bind(Class<T> componentClass, Class<I> implementationClass) {
        Constructor<?> constructor = getConstructor(implementationClass);
        newComponents.put(componentClass, new ConstructorInjectProvider<>(constructor));
        dependencies.put(componentClass, Set.of(constructor.getParameterTypes()));
    }

    public Context getContext() {

        // 检查依赖是否存在
        dependencies.forEach((componentType, dependencyTypes) ->
                dependencyTypes.forEach(dependencyType -> {
                    if (!newComponents.containsKey(dependencyType)) {
                        throw new DependencyNotBindException(dependencyType, componentType);
                    }
                }));

        return new Context() {
            @Override
            public <T> Optional<T> get(Class<T> componentClass) {
                return (Optional<T>) Optional.ofNullable(newComponents.get(componentClass)).map(provider -> provider.get(this));
            }
        };
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
