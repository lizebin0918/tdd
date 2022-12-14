package com.lzb.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>
 * Created on : 2022-12-13 21:09
 * @author mac
 */
class TestGameDiscovered {

	public static final String PLACE_HOLDER = "_";

	@Test
	void one_consonant_word_when_game_start() {
		GameForTest game = new GameForTest("b");
		assertEquals(PLACE_HOLDER, game.discovered());
	}

	@Test
	void two_consonants_word_when_game_start() {
		GameForTest game = new GameForTest("bt");
		assertEquals(PLACE_HOLDER + PLACE_HOLDER, game.discovered());
	}

	@Test
	void one_vowel_word_when_game_start() {
		Game game = new Game("a");
		assertEquals("a", game.discovered());
	}

	@Test
	void many_vowels_many_consonants_when_game_start() {
		Game game = new Game("abcde");
		assertEquals("a___e", game.discovered());
	}

	@Test
	void discovered_when_type_a_contained_consonant() {
		GameForTest game = new GameForTest("word");
		game.type('w');
		assertEquals("wo__", game.discovered());
	}
}
