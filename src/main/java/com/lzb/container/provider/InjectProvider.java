package com.lzb.container.provider;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.lzb.container.ComponentProvider;
import com.lzb.container.ComponentRef;
import com.lzb.container.Context;
import com.lzb.container.exception.IllegalComponentException;
import jakarta.inject.Inject;

import static java.util.Arrays.stream;

public class InjectProvider<T> implements ComponentProvider<T> {

    private final Constructor<?> injectConstructor;

    private final List<Field> injectFields;

    private final List<Method> injectMethods;

    public InjectProvider(Class<?> component) {

        if (Modifier.isAbstract(component.getModifiers())) throw new IllegalComponentException();

        this.injectConstructor = getConstructor(component);
        this.injectFields = getInjectFields(component);
        this.injectMethods = getInjectMethods(component);

        if (injectFields.stream().anyMatch(f -> Modifier.isFinal(f.getModifiers()))) {
            throw new IllegalComponentException();
        }

        if (injectMethods.stream().anyMatch(m -> m.getTypeParameters().length != 0)) {
            throw new IllegalComponentException();
        }
    }

    /**
     * 下面两个方法存在结构重复
     *
     * @param component
     * @return
     */
    private static List<Method> getInjectMethods(Class<?> component) {
        List<Method> injectMethods = traverse(component, (current, methods) -> injectable(current.getDeclaredMethods())
                // 父类声明@Inject，子类没有声明，不会注入
                .filter(m -> isOverrideByInjectMethod(m, methods))
                .filter(m -> isOverrideByNoInjectMethod(component, m))
                .toList());
        // 先实例化父类
        Collections.reverse(injectMethods);
        return injectMethods;
    }

    private static List<Field> getInjectFields(Class<?> component) {
        // 给定的component，通过给定的function向上找到所有的injectFields
        return traverse(component, (current, fields) -> injectable(current.getDeclaredFields()).toList());
    }

    private static <T> List<T> traverse(Class<?> component, BiFunction<Class<?>, List<T>, List<T>> finder) {
        List<T> members = new ArrayList<>();
        Class<?> current = component;
        while (current != Object.class) {
            members.addAll(finder.apply(current, members));
            current = current.getSuperclass();
        }
        return members;
    }

    private static <T extends AnnotatedElement> Stream<T> injectable(T[] declareds) {
        return stream(declareds).filter(f -> f.isAnnotationPresent(Inject.class));
    }

    private static <T, I extends T> Optional<Constructor<?>> getDefaultConstructor(Class<I> implementationClass) {
        try {
            return Optional.of(implementationClass.getDeclaredConstructor());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    static <T, I extends T> Constructor<?> getConstructor(Class<I> implementationClass) {

        List<Constructor<?>> injectConstructors = injectable(implementationClass.getConstructors()).toList();
        long injectConstructorsCount = injectConstructors.size();
        if (injectConstructorsCount > 1) {
            throw new IllegalArgumentException("不支持多个构造函数");
        }
        return injectConstructors.stream().findFirst().or(() -> getDefaultConstructor(implementationClass))
                .orElseThrow();
    }

    private static Object[] toDependencies(Context context, Executable executable) {
        return stream(executable.getParameters())
                .map(p -> toDependency(context, p.getParameterizedType()))
                .toArray(Object[]::new);
    }

    private static Object toDependency(Context context, Type type) {
        return context.get(ComponentRef.of(type))
                .orElseThrow();
    }

    @Override
    public T get(Context context) {
        try {
            T instance = (T) injectConstructor.newInstance(toDependencies(context, injectConstructor));
            injectFields.forEach(setField(context, instance));
            injectMethods.forEach(invokeMethod(context, instance));
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ComponentRef> getDependencies() {
        // 作者写法
        // return Arrays.stream(injectConstructor.getParameters()).map(p -> p.getParameterizedType()).toList();

        List<ComponentRef> types = new ArrayList<>();
        types.addAll(stream(this.injectConstructor.getGenericParameterTypes()).map(ComponentRef::of).toList());
        injectFields.forEach(f -> types.add(ComponentRef.of(f.getGenericType())));
        injectMethods.forEach(m -> types.addAll(Stream.of(m.getGenericParameterTypes()).map(ComponentRef::of).toList()));
        return types.stream().toList();
    }

    private static <T> Consumer<Method> invokeMethod(Context context, T instance) {
        return m -> {
            try {
                m.setAccessible(true);
                Object[] parameters = toDependencies(context, m);
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
                Object dependency = toDependency(context, f.getGenericType());
                f.set(instance, dependency);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private static boolean isOverride(Method m, Method o) {
        return o.getName()
                .equals(m.getName()) && Arrays.equals(o.getParameterTypes(), m.getParameterTypes());
    }

    private static boolean isOverrideByNoInjectMethod(Class<?> component, Method m) {
        return Stream.of(component.getDeclaredMethods())
                .filter(m1 -> !m1.isAnnotationPresent(Inject.class))
                .noneMatch(o -> isOverride(m, o));
    }

    private static boolean isOverrideByInjectMethod(Method m, List<Method> injectMethods) {
        return injectMethods.stream().noneMatch(o -> isOverride(m, o));
    }
}