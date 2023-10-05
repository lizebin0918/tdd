package com.lzb.container;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.lzb.container.exception.CyclicDependencyException;
import com.lzb.container.exception.DependencyNotBindException;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

/**
 * Context->ContextConfig 改造成 builder 模式，实现构造和初始化context分离<br/>
 * Created on : 2023-10-01 17:25
 * @author lizebin
 */
@Slf4j
public class ContextConfig {

    private final Map<Class<?>, ContextProvider<?>> components = new HashMap<>();

    public <T> void bind(Class<T> componentClass, T instance) {
        components.put(componentClass, new ContextProvider<>() {
            @Override
            public Object get(Context context) {
                return instance;
            }

            @Override
            public List<Class<?>> getDependencies() {
                return Collections.emptyList();
            }
        });
    }

    public <T, I extends T> void bind(Class<T> componentClass, Class<I> implementationClass) {
        Constructor<?> constructor = getConstructor(implementationClass);
        components.put(componentClass, new ConstructorInjectProvider<>(constructor));
    }

    public Context getContext() {

        components.forEach((componentType, provider) -> {

            // 检查依赖是否存在
            provider.getDependencies().forEach(dependencyType -> {
                if (!components.containsKey(dependencyType)) {
                    throw new DependencyNotBindException(dependencyType, componentType);
                }
            });

            // 检查循环依赖
            checkCyclicDependency(componentType, new LinkedList<>());
        });

        return new Context() {
            @Override
            public <T> Optional<T> get(Class<T> componentClass) {
                return (Optional<T>) Optional.ofNullable(components.get(componentClass)).map(provider -> provider.get(this));
            }
        };
    }

    private void checkCyclicDependency(Class<?> componentType, Deque<Class<?>> visiting) {
        for (Class<?> dependency : components.get(componentType).getDependencies()) {
            if (visiting.contains(dependency)) {
                throw new CyclicDependencyException(visiting);
            }
            visiting.push(dependency);
            checkCyclicDependency(dependency, visiting);
            visiting.pop();
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
