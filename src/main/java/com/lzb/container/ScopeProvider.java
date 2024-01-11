package com.lzb.container;

/**
 * <br/>
 * Created on : 2023-11-05 14:08
 * @author mac
 */
@FunctionalInterface
public interface ScopeProvider {

    ComponentProvider<?> create(ComponentProvider<?> provider);

}
