package com.lzb.container;

import java.util.Collections;
import java.util.List;

/**
 * <br/>
 * Created on : 2023-10-05 09:09
 * @author lizebin
 */
public interface ContextProvider<T> {

    T get(Context context);

    default List<Class<?>> getDependencies() {
        return Collections.emptyList();
    }

}
