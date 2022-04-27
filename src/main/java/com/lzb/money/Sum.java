package com.lzb.money;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2022-04-27 09:02
 *
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public class Sum implements Expression {

    private Money augend;
    private Money addend;

}
