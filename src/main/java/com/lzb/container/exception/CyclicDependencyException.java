package com.lzb.container.exception;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.lzb.container.Component;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-04 11:53
 * @author lizebin
 */
@Getter
public class CyclicDependencyException extends RuntimeException {

    private final Set<Component> components = new HashSet<>();
    public CyclicDependencyException(Collection<Component> componentTypes) {
        this.components.addAll(componentTypes);
    }

    public Set<Class<?>> getComponentTypes() {
        return components.stream().map(Component::type).collect(Collectors.toSet());
    }

}
