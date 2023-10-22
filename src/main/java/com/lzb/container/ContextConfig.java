package com.lzb.container;

import java.lang.annotation.Annotation;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
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
    private final Map<Component, ComponentProvider<?>> componentProviders = new HashMap<>();

    public <T> void bind(Class<T> componentType, T instance) {
        providers.put(componentType, new InstanceProvider<>(instance));
    }

    public <T> void bind(Class<T> componentType, T instance, Annotation... qualifiers) {
        if (Objects.isNull(qualifiers)) {
            return;
        }
        for (Annotation qualifier : qualifiers) {
            Component component = new Component(componentType, qualifier);
            componentProviders.put(component, new InstanceProvider<>(instance));
        }
    }

    public <T, I extends T> void bind(Class<T> componentType, Class<I> implementationType) {
        providers.put(componentType, new InjectProvider<>(implementationType));
    }

    public <T, I extends T> void bind(Class<T> componentType, Class<I> implementationType, Annotation... qualifiers) {
        if (Objects.isNull(qualifiers)) {
            return;
        }
        for (Annotation qualifier : qualifiers) {
            Component component = new Component(componentType, qualifier);
            componentProviders.put(component, new InjectProvider<>(implementationType));
        }
    }

    public Context getContext() {

        providers.keySet().forEach(componentType -> checkDependency(componentType, new LinkedList<>()));

        return new Context() {
            @Override
            public Optional<?> get(Ref ref) {
                if (Objects.nonNull(ref.getQualifierComponentType())) {
                    return Optional.ofNullable(componentProviders.get(ref.getQualifierComponentType())).map(provider -> provider.get(this));
                }
                if (ref.isContainerType()) {
                    if (ref.getContainerType() != Provider.class) return Optional.empty();
                    return Optional.ofNullable(providers.get(ref.getComponentType()))
                            .map(provider -> (Provider<Object>) () -> provider.get(this));
                }
                return Optional.ofNullable(providers.get(ref.getComponentType())).map(provider -> provider.get(this));
            }

        };
    }

    private void checkDependency(Class<?> componentType, Deque<Class<?>> visiting) {
        for (Context.Ref dependency : providers.get(componentType).getDependencies()) {

            // 检查依赖是否存在
            if (!providers.containsKey(dependency.getComponentType())) {
                throw new DependencyNotBindException(componentType, dependency.getComponentType());
            }

            if (!dependency.isContainerType()) {

                // 检查循环依赖
                if (visiting.contains(dependency.getComponentType())) {
                    throw new CyclicDependencyException(visiting);
                }

                visiting.push(dependency.getComponentType());
                checkDependency(dependency.getComponentType(), visiting);
                visiting.pop();
            }
        }
    }

}
