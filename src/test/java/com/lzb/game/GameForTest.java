package com.lzb.game;

/**
 * 用于测试的类<br/>
 * Created on : 2022-12-12 23:01
 * @author mac
 */
public class GameForTest extends Game {

	static final char VOWEL = 'a';

	static final int MAX_TRIES = 12;

	/**
	 * 包含的辅音
	 */
	static final char CONTAINED_CONSONANT = 'w';

	static final char NOT_CONTAINED_CONSONANT = 'z';

	/**
	 * 原音
	 */
	static final String ALL_VOWELS = "aeiou";

	/**
	 * 辅音
	 */
	static final char CONSONANT = 'b';

	public GameForTest(String word) {
		super(word);
	}

}
