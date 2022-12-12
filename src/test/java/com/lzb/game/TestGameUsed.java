package com.lzb.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>
 * Created on : 2022-12-11 22:51
 * @author mac
 */
class TestGameUsed {

	private final String ALL_VOWELS = "aeiou";
	private final char consonant = 'b';
	private final char vowel = 'a';
	Game game = new Game("lizebin");

	@Test
	@DisplayName("默认元音")
	void used_when_game_start() {
		assertEquals(ALL_VOWELS, game.used());
	}

	@Test
	@DisplayName("输入元音字母")
	void used_when_type_a_vowel() {
		game.type(vowel);
		assertEquals(ALL_VOWELS, game.used());
	}

	@Test
	@DisplayName("输入辅音")
	void used_when_type_a_consonant() {
		game.type(consonant);
		assertEquals(ALL_VOWELS + consonant, game.used());
	}

	@Test
	@DisplayName("输入相同辅音")
	void used_when_type_a_repeat_consonant() {
		game.type(consonant);
		game.type(consonant);
		assertEquals(ALL_VOWELS + consonant, game.used());
	}

}
