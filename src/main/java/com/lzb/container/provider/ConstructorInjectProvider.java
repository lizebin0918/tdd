package com.lzb.container.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.lzb.container.Context;
import com.lzb.container.ContextProvider;
import com.lzb.container.exception.IllegalComponentException;
import jakarta.inject.Inject;

public class ConstructorInjectProvider<T> implements ContextProvider<T> {

    private final Constructor<?> constructor;

    private final List<Field> injectFields;

    private final List<Method> injectMethods;

    public ConstructorInjectProvider(Class<?> component) {

        if (Modifier.isAbstract(component.getModifiers())) throw new IllegalComponentException();

        this.constructor = getConstructor(component);
        this.injectFields = getInjectFields(component);
        this.injectMethods = getInjectMethods(component);

        if (injectFields.stream().anyMatch(f -> Modifier.isFinal(f.getModifiers()))) {
            throw new IllegalComponentException();
        }
    }

    private static List<Method> getInjectMethods(Class<?> component) {
        List<Method> injectMethods = new ArrayList<>();
        Class<?> current = component;
        while (current != Object.class) {
            injectMethods.addAll(Arrays.stream(current.getDeclaredMethods())
                    .filter(f -> f.isAnnotationPresent(Inject.class))
                    // 父类声明@Inject，子类没有声明，不会注入
                    .filter(m -> injectMethods.stream().noneMatch(o -> o.getName()
                            .equals(m.getName()) && Arrays.equals(o.getParameterTypes(), m.getParameterTypes())))
                    .filter(m -> Stream.of(component.getDeclaredMethods())
                            .filter(m1 -> !m1.isAnnotationPresent(Inject.class))
                            .noneMatch(o -> o.getName()
                                    .equals(m.getName()) && Arrays.equals(o.getParameterTypes(), m.getParameterTypes())))
                    .toList());
            current = current.getSuperclass();
        }
        // 先实例化父类
        Collections.reverse(injectMethods);
        return injectMethods;
    }

    private static List<Field> getInjectFields(Class<?> component) {
        List<Field> injectFields = new ArrayList<>();
        Class<?> current = component;
        while (current != Object.class) {
            injectFields.addAll(Arrays.stream(current.getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(Inject.class)).toList());
            current = current.getSuperclass();
        }
        return injectFields;
    }

    static boolean byIsInjectConstructor(Constructor<?> constructor) {
        return constructor.isAnnotationPresent(Inject.class);
    }

    private static <T, I extends T> Optional<Constructor<?>> getDefaultConstructor(Class<I> implementationClass) {
        try {
            return Optional.of(implementationClass.getDeclaredConstructor());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    static <T, I extends T> Constructor<?> getConstructor(Class<I> implementationClass) {

        List<Constructor<?>> injectConstructors = getInjectConstructors(implementationClass).toList();
        long injectConstructorsCount = injectConstructors.size();
        if (injectConstructorsCount > 1) {
            throw new IllegalArgumentException("不支持多个构造函数");
        }
        return injectConstructors.stream().findFirst().or(() -> getDefaultConstructor(implementationClass))
                .orElseThrow();
    }

    private static <T, I extends T> Stream<Constructor<?>> getInjectConstructors(Class<I> implementationClass) {
        return Arrays.stream(implementationClass.getConstructors())
                .filter(ConstructorInjectProvider::byIsInjectConstructor);
    }

    private Object[] getInjectDependencies(Context context) {
        return Stream.of(constructor.getParameterTypes())
                .map(context::get)
                .filter(Optional::isPresent)
                .map(Optional::get).toArray();
    }

    @Override
    public T get(Context context) {
        try {
            T instance = (T) constructor.newInstance(getInjectDependencies(context));
            injectFields.forEach(setField(context, instance));
            injectMethods.forEach(invokeMethod(context, instance));
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Consumer<Method> invokeMethod(Context context, T instance) {
        return m -> {
            try {
                m.setAccessible(true);
                Object[] parameters = Arrays.stream(m.getParameterTypes()).map(context::get).filter(Optional::isPresent)
                        .map(Optional::get).toArray();
                m.invoke(instance, parameters);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private static <T> Consumer<Field> setField(Context context, T instance) {
        return f -> {
            try {
                f.setAccessible(true);
                f.set(instance, context.get(f.getType()).orElseThrow());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public List<Class<?>> getDependencies() {
        List<Class<?>> allDependencies = new ArrayList<>(List.of(constructor.getParameterTypes()));
        injectFields.forEach(f -> allDependencies.add(f.getType()));
        injectMethods.forEach(m -> allDependencies.addAll(Arrays.asList(m.getParameterTypes())));
        return allDependencies;
    }
}