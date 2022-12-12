package com.lzb.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 测试长度<br/>
 *
 * length
 * tries
 * used
 * discovered
 * game over 依赖于 tries
 * game win 依赖于 discovered
 *
 * Created on : 2022-12-10 21:42
 * @author mac
 */
class TestGameLength {

	@Test
	void length_of_word() {
		Game game = new Game("world");
		Assertions.assertEquals(5, game.length());
	}

}
