package com.lzb.container;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * <br/>
 * Created on : 2023-10-20 10:15
 * @author lizebin
 */
@Getter
@EqualsAndHashCode
public
class ComponentRef<T> {
    private Type containerType;
    private Component component;

    public ComponentRef(Type type) {
        init(type, null);
    }

    public ComponentRef(Type type, Annotation qualifier) {
        init(type, qualifier);
    }

    public ComponentRef(Class<T> componentType) {
        init(componentType, null);
    }

    private ComponentRef(@NonNull Class<T> componentType, Annotation qualifier) {
        if (Objects.isNull(qualifier)) {
            init(componentType, null);
            return;
        }
        this.component = new Component(componentType, qualifier);
    }

    public static ComponentRef of(Type type) {
        return new ComponentRef<>(type);
    }

    public static <T> ComponentRef<T> of(Class<T> componentClass) {
        return new ComponentRef<>(componentClass);
    }

    public static <T> ComponentRef<T> of(@NonNull Class<T> componentType, Annotation qualifier) {
        return new ComponentRef<>(componentType, qualifier);
    }

    public static ComponentRef<?> of(Type parameterizedType, Annotation qualifier) {
        return new ComponentRef<>(parameterizedType, qualifier);
    }

    public boolean isContainerType() {
        return containerType != null;
    }

    protected ComponentRef() {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        init(type, null);
    }

    private void init(Type type, Annotation qualifier) {
        Class<?> componentType;
        if (type instanceof ParameterizedType container) {
            componentType = (Class<?>) container.getActualTypeArguments()[0];
            this.containerType = container.getRawType();
        } else {
            componentType = (Class<?>) type;
        }
        this.component = new Component(componentType, qualifier);
    }

}
