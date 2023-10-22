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
        init(type);
    }

    public ComponentRef(Class<T> componentType) {
        init(componentType);
    }

    public ComponentRef(@NonNull Class<T> componentType, Annotation qualifier) {
        if (Objects.isNull(qualifier)) {
            init(componentType);
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

    public boolean isContainerType() {
        return containerType != null;
    }

    protected ComponentRef() {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        init(type);
    }

    private void init(Type type) {
        Class<?> componentType;
        if (type instanceof ParameterizedType container) {
            componentType = (Class<?>) container.getActualTypeArguments()[0];
            this.containerType = container.getRawType();
        } else {
            componentType = (Class<?>) type;
        }
        this.component = new Component(componentType, null);
    }

}
