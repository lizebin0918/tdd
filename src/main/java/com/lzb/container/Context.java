package com.lzb.container;

import java.util.Optional;

/**
 * 容器<br/>
 * Created on : 2023-10-05 08:50
 * @author lizebin
 */
public interface Context {

    <T> Optional<T> get(ComponentRef<T> componentRef);

}
