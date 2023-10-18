package com.lzb.container;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * <br/>
 * Created on : 2023-10-05 09:09
 * @author lizebin
 */
public interface ComponentProvider<T> {

    T get(Context context);

    default List<Class<?>> getDependencies() {
        return Collections.emptyList();
    }

    default List<Type> getDependencyTypes() {
        return Collections.emptyList();
    }

}
