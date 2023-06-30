package com.lzb.pratice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>
 * Created on : 2022-12-15 10:04
 * @author mac
 */
class ConcatNumberToMaxValueTest {

	@Test
	void max_value_is_self_when_input_a_number() {
		assertEquals("1", ConcatNumberToMaxValue.maxOf(new int[]{1}));
	}

}
