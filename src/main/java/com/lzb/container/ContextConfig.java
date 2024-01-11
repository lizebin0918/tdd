package com.lzb.container;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import com.lzb.container.exception.CyclicDependencyException;
import com.lzb.container.exception.DependencyNotBindException;
import com.lzb.container.exception.IllegalComponentException;
import com.lzb.container.provider.InjectProvider;
import com.lzb.container.provider.InstanceProvider;
import com.lzb.container.provider.SingletonProvider;
import jakarta.inject.Provider;
import jakarta.inject.Qualifier;
import jakarta.inject.Scope;
import jakarta.inject.Singleton;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Context->ContextConfig 改造成 builder 模式，实现构造和初始化context分离<br/>
 * Created on : 2023-10-01 17:25
 * @author lizebin
 */
@Slf4j
public class ContextConfig {

    // private final Map<Class<?>, ComponentProvider<?>> providers = new HashMap<>();
    private final Map<Component, ComponentProvider<?>> componentProviders = new HashMap<>();

    // private final Map<Class<?>, Function<ComponentProvider<?>, ComponentProvider<?>>> scopes = new HashMap<>();
    // 如果出参和入参都是同一个类型，可以用UnaryOperator
    //private final Map<Class<?>, UnaryOperator<ComponentProvider<?>>> scopes = new HashMap<>();
    private final Map<Class<?>, ScopeProvider> scopes = new HashMap<>();

    public ContextConfig() {
        scope(Singleton.class, SingletonProvider::new);
    }

    public <ScopeType extends Annotation> void scope(Class<ScopeType> scope, ScopeProvider provider) {
        scopes.put(scope, provider);
    }

    public <T> void bind(Class<T> componentType, T instance, Annotation... annotations) {
        Map<Class<?>, List<Annotation>> annotationGroups = groupAnnotations(annotations);
        bind(componentType, createScopeProvider(instance.getClass(), annotationGroups,
                new InstanceProvider<>(instance)), annotationGroups.getOrDefault(Qualifier.class, Collections.emptyList()));
    }

    private Map<Class<?>, List<Annotation>> groupAnnotations(Annotation[] annotations) {
        Map<Class<?>, List<Annotation>> annotationGroups = Arrays.stream(annotations).collect(Collectors.groupingBy(this::typeOf));
        if (annotationGroups.containsKey(Illegal.class)) {
            throw new IllegalComponentException("annotation must be annotated by @Qualifier or @Singleton");
        }
        return annotationGroups;
    }

    /**
     * 通过重载的方式调用更好
     * @param componentType
     * @param implementationType
     * @param <T>
     * @param <I>
     */
    public <T, I extends T> void bind(Class<T> componentType, Class<I> implementationType) {
        bind(componentType, implementationType, implementationType.getAnnotations());
    }

    /**
     * 绑定
     * @param componentType
     * @param implementationType
     * @param annotations 实现Annotation的实例
     */
    public <T, I extends T> void bind(Class<T> componentType, Class<I> implementationType, @NonNull Annotation... annotations) {

        // 分组：scope/qualifier/illegal
        Map<Class<?>, List<Annotation>> annotationGroups = groupAnnotations(annotations);

        ComponentProvider<T> scopeProvider = createScopeProvider(implementationType, annotationGroups, new InjectProvider<>(implementationType));

        bind(componentType, scopeProvider, annotationGroups.getOrDefault(Qualifier.class, Collections.emptyList()));
    }

    private <T> void bind(Class<T> componentType, ComponentProvider<T> provider, List<Annotation> qualifiers) {
        if (qualifiers.isEmpty()) {
            componentProviders.put(new Component(componentType, null), provider);
        }
        for (Annotation qualifier : qualifiers) {
            Component component = new Component(componentType, qualifier);
            componentProviders.put(component, provider);
        }
    }

    private Class<?> typeOf(Annotation annotation) {
        Class<? extends Annotation> type = annotation.annotationType();
        return Stream.of(Qualifier.class, Scope.class).filter(type::isAnnotationPresent).findFirst().orElse(Illegal.class);
    }

    @NotNull
    private <T> ComponentProvider<T> createScopeProvider(Class<?> implementationType,
            @NonNull Map<Class<?>, List<Annotation>> annotationGroup, ComponentProvider<T> provider) {
        Optional<Annotation> scope = annotationGroup.getOrDefault(Scope.class, Collections.emptyList()).stream().findFirst()
                .or(() -> Arrays.stream(implementationType.getAnnotations()).filter(a -> a.annotationType().isAnnotationPresent(Scope.class)).findFirst());
        if (scope.isPresent()) {
            var providerFun = scopes.get(scope.get().annotationType());
            return (ComponentProvider<T>) providerFun.create(provider);
        }
        return provider;
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
