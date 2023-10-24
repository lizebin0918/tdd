package com.lzb.container.exception;

import com.lzb.container.Component;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-04 10:05
 * @author lizebin
 */
@Getter
public class DependencyNotBindException extends RuntimeException {

    private final Component dependencyComponent;
    private final Component componentComponent;

    public DependencyNotBindException(Component componentComponent, Component dependencyComponent) {
        this.dependencyComponent = dependencyComponent;
        this.componentComponent = componentComponent;
    }

}
