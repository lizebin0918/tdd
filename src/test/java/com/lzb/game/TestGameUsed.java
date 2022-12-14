package com.lzb.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.lzb.game.GameForTest.ALL_VOWELS;
import static com.lzb.game.GameForTest.VOWEL;
import static com.lzb.game.GameForTest.CONSONANT;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <br/>
 * Created on : 2022-12-11 22:51
 * @author mac
 */
class TestGameUsed {

	GameForTest game = new GameForTest("lizebin");

	@Test
	@DisplayName("默认元音")
	void used_when_game_start() {
		assertEquals(ALL_VOWELS, game.used());
	}

	@Test
	@DisplayName("输入元音字母")
	void used_when_type_a_vowel() {
		game.typeWithoutCheckGameOverAndGameWin(VOWEL);
		assertEquals(ALL_VOWELS, game.used());
	}

	@Test
	@DisplayName("输入辅音")
	void used_when_type_a_consonant() {
		game.typeWithoutCheckGameOverAndGameWin(CONSONANT);
		assertEquals(ALL_VOWELS + CONSONANT, game.used());
	}

	@Test
	@DisplayName("输入相同辅音")
	void used_when_type_a_repeat_consonant() {
		game.typeWithoutCheckGameOverAndGameWin(CONSONANT);
		game.typeWithoutCheckGameOverAndGameWin(CONSONANT);
		assertEquals(ALL_VOWELS + CONSONANT, game.used());
	}

}
