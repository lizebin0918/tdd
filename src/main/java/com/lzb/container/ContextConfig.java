package com.lzb.container;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import com.lzb.container.exception.CyclicDependencyException;
import com.lzb.container.exception.DependencyNotBindException;
import com.lzb.container.exception.IllegalComponentException;
import com.lzb.container.provider.InjectProvider;
import com.lzb.container.provider.InstanceProvider;
import jakarta.inject.Provider;
import jakarta.inject.Qualifier;
import jakarta.inject.Scope;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Context->ContextConfig 改造成 builder 模式，实现构造和初始化context分离<br/>
 * Created on : 2023-10-01 17:25
 * @author lizebin
 */
@Slf4j
public class ContextConfig {

    // private final Map<Class<?>, ComponentProvider<?>> providers = new HashMap<>();
    private final Map<Component, ComponentProvider<?>> componentProviders = new HashMap<>();

    public <T> void bind(Class<T> componentType, T instance, Annotation... qualifiers) {
        if (ArrayUtils.isEmpty(qualifiers)) {
            componentProviders.put(new Component(componentType, null), new InstanceProvider<>(instance));
            return;
        }
        if (Arrays.stream(qualifiers).anyMatch(q -> !q.annotationType().isAnnotationPresent(Qualifier.class))) {
            throw new IllegalComponentException("qualifier must be annotated by @Qualifier");
        }
        for (Annotation qualifier : qualifiers) {
            Component component = new Component(componentType, qualifier);
            componentProviders.put(component, new InstanceProvider<>(instance));
        }
    }

    public <T, I extends T> void bind(Class<T> componentType, Class<I> implementationType, @NonNull Annotation... annotations) {
        if (ArrayUtils.isEmpty(annotations)) {
            componentProviders.put(new Component(componentType, null), new InjectProvider<>(implementationType));
            return;
        }
        if (Arrays.stream(annotations).map(Annotation::annotationType)
                .anyMatch(q -> !q.isAnnotationPresent(Qualifier.class) && !q.isAnnotationPresent(Scope.class))) {
                //.anyMatch(q -> !q.isAnnotationPresent(Qualifier.class) && !q.isAnnotationPresent(Singleton.class))) {
            throw new IllegalComponentException("annotation must be annotated by @Qualifier or @Singleton");
        }
        for (Annotation qualifier : annotations) {
            Component component = new Component(componentType, qualifier);
            componentProviders.put(component, new InjectProvider<>(implementationType));
        }
    }

    public Context getContext() {

        componentProviders.keySet().forEach(componentType -> checkDependency(componentType, new LinkedList<>()));

        return new Context() {
            @Override
            public Optional<?> get(ComponentRef componentRef) {
                if (componentRef.isContainerType()) {
                    if (componentRef.getContainerType() != Provider.class) return Optional.empty();
                    return getValue(componentRef).map(provider -> (Provider<Object>) () -> provider.get(this));
                }
                return getValue(componentRef).map(provider -> provider.get(this));
            }

        };
    }

    private Optional<? extends ComponentProvider<?>> getValue(ComponentRef componentRef) {
        return Optional.ofNullable(componentProviders.get(componentRef.getComponent()));
    }

    private void checkDependency(Component component, Deque<Component> visiting) {
        for (ComponentRef dependency : componentProviders.get(component).getDependencies()) {

            Component dependencyComponent = dependency.getComponent();

            // 检查依赖是否存在
            if (!componentProviders.containsKey(dependencyComponent)) {
                throw new DependencyNotBindException(component, dependencyComponent);
            }

            if (!dependency.isContainerType()) {

                // 检查循环依赖
                if (visiting.contains(dependencyComponent)) {
                    throw new CyclicDependencyException(visiting);
                }

                visiting.push(dependencyComponent);
                checkDependency(dependencyComponent, visiting);
                visiting.pop();
            }
        }
    }

}
