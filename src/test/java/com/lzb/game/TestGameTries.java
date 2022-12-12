package com.lzb.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>
 * Created on : 2022-12-12 09:12
 * @author mac
 */
class TestGameTries {

	private static final int MAX_TRIES = 12;

	@Test
	void tries_when_game_start() {
		Game game = new Game("word");
		assertEquals(MAX_TRIES, game.tries());
	}

	/**
	 * 输入元音之后，默认就要减1，因为元音在游戏中显示出来了
	 */
	@Test
	void tries_when_type_a_vowel() {
		Game game = new Game("word");
		game.type('a');
		assertEquals(MAX_TRIES - 1, game.tries());
	}

}
