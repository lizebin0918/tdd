package com.lzb.container;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-20 10:15
 * @author lizebin
 */
@Getter
@EqualsAndHashCode
public class Ref {
    private Type containerType;
    private Class<?> componentType;

    public Ref(ParameterizedType containerType) {
        this.containerType = containerType.getRawType();
        this.componentType = (Class<?>) containerType.getActualTypeArguments()[0];
    }

    public Ref(Class<?> componentType) {
        this.componentType = componentType;
    }

    static Ref of(Type type) {
        if (type instanceof ParameterizedType container) {
            return new Ref(container);
        }
        return new Ref((Class<?>) type);
    }

    public boolean isContainerType() {
        return containerType != null;
    }

}
