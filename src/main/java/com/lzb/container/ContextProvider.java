package com.lzb.container;

/**
 * <br/>
 * Created on : 2023-10-05 09:09
 * @author lizebin
 */
public interface ContextProvider<T> {

    T get(Context context);

}
