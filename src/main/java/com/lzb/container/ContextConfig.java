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

    private static Class<?> getComponentType(Type type) {
        return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    private void checkDependency(Class<?> componentType, Deque<Class<?>> visiting) {
        for (Type dependency : providers.get(componentType).getDependencies()) {
            Ref ref = Ref.of(dependency);

            // 检查依赖是否存在
            if (!providers.containsKey(ref.getComponentType())) {
                throw new DependencyNotBindException(componentType, ref.getComponentType());
            }

            if (!ref.isContainerType()) {

                // 检查循环依赖
                if (visiting.contains(ref.getComponentType())) {
                    throw new CyclicDependencyException(visiting);
                }

                visiting.push(ref.getComponentType());
                checkDependency(ref.getComponentType(), visiting);
                visiting.pop();
            }
        }
    }

}
