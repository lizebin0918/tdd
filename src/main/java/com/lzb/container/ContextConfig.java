package com.lzb.container;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import com.lzb.container.exception.CyclicDependencyException;
import com.lzb.container.exception.DependencyNotBindException;
import com.lzb.container.provider.ConstructorInjectProvider;
import com.lzb.container.provider.InstanceProvider;
import lombok.extern.slf4j.Slf4j;

/**
 * Context->ContextConfig 改造成 builder 模式，实现构造和初始化context分离<br/>
 * Created on : 2023-10-01 17:25
 * @author lizebin
 */
@Slf4j
public class ContextConfig {

    private final Map<Class<?>, ContextProvider<?>> providers = new HashMap<>();

    public <T> void bind(Class<T> componentClass, T instance) {
        providers.put(componentClass, new InstanceProvider<>(instance));
    }

    public <T, I extends T> void bind(Class<T> componentClass, Class<I> implementationClass) {
        providers.put(componentClass, new ConstructorInjectProvider<>(implementationClass));
    }

    public Context getContext() {

        providers.keySet().forEach(componentType -> checkDependency(componentType, new LinkedList<>()));

        return new Context() {
            @Override
            public <T> Optional<T> get(Class<T> componentClass) {
                return (Optional<T>) Optional.ofNullable(providers.get(componentClass)).map(provider -> provider.get(this));
            }
        };
    }

    private void checkDependency(Class<?> componentType, Deque<Class<?>> visiting) {
        for (Class<?> dependency : providers.get(componentType).getDependencies()) {

            // 检查依赖是否存在
            if (!providers.containsKey(dependency)) {
                throw new DependencyNotBindException(dependency, componentType);
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

}
