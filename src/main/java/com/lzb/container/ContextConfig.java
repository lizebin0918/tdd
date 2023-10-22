package com.lzb.container;

import java.lang.annotation.Annotation;
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
        for (Annotation qualifier : qualifiers) {
            Component component = new Component(componentType, qualifier);
            componentProviders.put(component, new InstanceProvider<>(instance));
        }
    }

    public <T, I extends T> void bind(Class<T> componentType, Class<I> implementationType, @NonNull Annotation... qualifiers) {
        if (ArrayUtils.isEmpty(qualifiers)) {
            componentProviders.put(new Component(componentType, null), new InjectProvider<>(implementationType));
            return;
        }
        for (Annotation qualifier : qualifiers) {
            Component component = new Component(componentType, qualifier);
            componentProviders.put(component, new InjectProvider<>(implementationType));
        }
    }

    public Context getContext() {

        componentProviders.keySet().forEach(componentType -> checkDependency(componentType, new LinkedList<>()));

        return new Context() {
            @Override
            public Optional<?> get(Ref ref) {
                if (ref.isContainerType()) {
                    if (ref.getContainerType() != Provider.class) return Optional.empty();
                    return getValue(ref).map(provider -> (Provider<Object>) () -> provider.get(this));
                }
                return getValue(ref).map(provider -> provider.get(this));
            }

        };
    }

    private Optional<? extends ComponentProvider<?>> getValue(Context.Ref ref) {
        return Optional.ofNullable(componentProviders.get(new Component(ref.getComponentType(), ref.getQualifier())));
    }

    private void checkDependency(Component componentType, Deque<Class<?>> visiting) {
        for (Context.Ref dependency : componentProviders.get(componentType).getDependencies()) {

            // 检查依赖是否存在
            if (!componentProviders.containsKey(new Component(dependency.getComponentType(), dependency.getQualifier()))) {
                throw new DependencyNotBindException(componentType.type(), dependency.getComponentType());
            }

            if (!dependency.isContainerType()) {

                // 检查循环依赖
                if (visiting.contains(dependency.getComponentType())) {
                    throw new CyclicDependencyException(visiting);
                }

                visiting.push(dependency.getComponentType());
                checkDependency(new Component(dependency.getComponentType(), dependency.getQualifier()), visiting);
                visiting.pop();
            }
        }
    }

}
