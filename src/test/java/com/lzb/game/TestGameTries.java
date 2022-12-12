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

	/**
	 * 包含的辅音
	 */
	private final char CONTAINED_CONSONANT = 'w';

	Game game = new Game("word");
	private char NOT_CONTAINED_CONSONANT = 'z';

	@Test
	void tries_when_game_start() {
		assertEquals(MAX_TRIES, game.tries());
	}

	/**
	 * 输入元音之后，默认就要减1，因为元音在游戏中显示出来了
	 */
	@Test
	void tries_when_type_a_vowel() {
		game.type('a');
		assertEquals(MAX_TRIES - 1, game.tries());
	}

	/**
	 * 这里采用包含的辅音，为了构造一个失败的测试。如果构造一个不包含的辅音，这个测试默认就通过了
	 */
	@Test
	void tries_when_type_a_contained_consonant() {
		game.type(CONTAINED_CONSONANT);
		assertEquals(MAX_TRIES, game.tries());
	}

	@Test
	void tries_when_type_the_same_contained_consonant_again() {
		game.type(CONTAINED_CONSONANT);
		game.type(CONTAINED_CONSONANT);
		assertEquals(MAX_TRIES - 1, game.tries());
	}

	@Test
	void tries_when_type_a_not_contained_consonant() {
		game.type(NOT_CONTAINED_CONSONANT);
		assertEquals(MAX_TRIES - 1, game.tries());
	}

}
