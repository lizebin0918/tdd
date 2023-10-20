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
import lombok.EqualsAndHashCode;
import lombok.Getter;
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

            @Override
            public Optional getType(Type type) {
                return getObject(Ref.of(type));
            }

            private Optional<?> getObject(Ref ref) {
                if (ref.isContainerType()) {
                    if (ref.getContainerType() != Provider.class) return Optional.empty();
                    return Optional.ofNullable(providers.get(ref.getComponentType()))
                            .map(provider -> (Provider<Object>) () -> provider.get(this));
                }
                return Optional.ofNullable(providers.get(ref.getComponentType())).map(provider -> provider.get(this));
            }

        };
    }

    @Getter
    @EqualsAndHashCode
    static class Ref {
        private Type containerType;
        private Class<?> componentType;

        public Ref(ParameterizedType containerType) {
            this.containerType = containerType.getRawType();
            this.componentType = (Class<?>) containerType.getActualTypeArguments()[0];
        }

        public Ref(Class<?> componentType) {
            this.componentType = componentType;
        }

        static Ref of(Type type) {
            if (type instanceof ParameterizedType container) {
                return new Ref(container);
            }
            return new Ref((Class<?>) type);
        }

        public boolean isContainerType() {
            return containerType != null;
        }

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

    private void checkContainerTypeDependency(Class<?> component, ParameterizedType pt) {
        Class<?> componentType = getComponentType(pt);
        if (!providers.containsKey(componentType)) {
            throw new DependencyNotBindException(component, componentType);
        }
    }

    private void checkComponentTypeDependency(Class<?> component, Deque<Class<?>> visiting, Class<?> dependency) {

        Class<?> componentType = dependency;

        // 检查依赖是否存在
        if (!providers.containsKey(componentType)) {
            throw new DependencyNotBindException(component, componentType);
        }

        // 检查循环依赖
        if (visiting.contains(componentType)) {
            throw new CyclicDependencyException(visiting);
        }

        visiting.push(componentType);
        checkDependency(componentType, visiting);
        visiting.pop();
    }

}
