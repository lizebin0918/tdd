package com.lzb.container;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import com.lzb.container.exception.CyclicDependencyException;
import com.lzb.container.exception.DependencyNotBindException;
import com.lzb.container.provider.InjectProvider;
import com.lzb.container.provider.InstanceProvider;
import jakarta.inject.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * Context->ContextConfig 改造成 builder 模式，实现构造和初始化context分离<br/>
 * Created on : 2023-10-01 17:25
 * @author lizebin
 */
@Slf4j
public class ContextConfig {

    private final Map<Class<?>, ComponentProvider<?>> providers = new HashMap<>();

    public <T> void bind(Class<T> componentClass, T instance) {
        providers.put(componentClass, new InstanceProvider<>(instance));
    }

    public <T, I extends T> void bind(Class<T> componentClass, Class<I> implementationClass) {
        providers.put(componentClass, new InjectProvider<>(implementationClass));
    }

    public Context getContext() {

        providers.keySet().forEach(componentType -> checkDependency(componentType, new LinkedList<>()));

        return new Context() {
            private <T> Optional<T> getComponent(Class<T> type) {
                return Optional.ofNullable(providers.get(type)).map(provider -> (T) provider.get(this));
            }

            @Override
            public Optional getType(Type type) {
                if (type instanceof ParameterizedType pt) {
                    return getContainer(pt);
                }
                return getComponent((Class<?>) type);
            }

            private Optional<Provider> getContainer(ParameterizedType type) {
                if (type.getRawType() != Provider.class) return Optional.empty();
                Class<?> componentType = getComponentType(type);
                return Optional.ofNullable(providers.get(componentType))
                        .map(provider -> (Provider<Object>) () -> provider.get(this));
            }
        };
    }

    private static Class<?> getComponentType(Type type) {
        return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    private void checkDependency(Class<?> componentType, Deque<Class<?>> visiting) {
        for (Type dependency : providers.get(componentType).getDependencies()) {
            if (dependency instanceof Class<?> c) {
                checkComponentTypeDependency(componentType, visiting, c);
            }
            if (dependency instanceof ParameterizedType pt) {
                checkContainerTypeDependency(componentType, pt);
                // 是否存在循环依赖？provider注入不存在
            }
        }
    }

    private void checkContainerTypeDependency(Class<?> componentType, ParameterizedType pt) {
        Class<?> type = getComponentType(pt);
        if (!providers.containsKey(type)) {
            throw new DependencyNotBindException(componentType, type);
        }
    }

    private void checkComponentTypeDependency(Class<?> componentType, Deque<Class<?>> visiting, Class<?> dependency) {
        // 检查依赖是否存在
        if (!providers.containsKey(dependency)) {
            throw new DependencyNotBindException(componentType, dependency);
        }

        // 检查循环依赖
        if (visiting.contains(dependency)) {
            throw new CyclicDependencyException(visiting);
        }

        visiting.push(dependency);
        checkDependency(dependency, visiting);
        visiting.pop();
    }

}
