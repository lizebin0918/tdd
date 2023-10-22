package com.lzb.container;

import java.util.List;

/**
 * <br/>
 * Created on : 2023-10-05 09:09
 * @author lizebin
 */
public interface ComponentProvider<T> {

    T get(Context context);

    List<ComponentRef> getDependencies();

}
