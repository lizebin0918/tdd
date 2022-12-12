package com.lzb.game;

/**
 * <br/>
 * Created on : 2022-12-10 21:43
 * @author mac
 */
public class Game {

	/**
	 * 这个游戏默认显示元音，所以无需输入元音
	 */
	private static final String ALL_VOWELS = "aeiou";
	private static final int MAX_TRIES = 12;

	private int tries = MAX_TRIES;

	private final String word;

	private String used = ALL_VOWELS;

	public Game(String word) {
		this.word = word;
	}

	/**
	 * 返回单词长度
	 *
	 * @return
	 */
	int length() {
		return word.length();
	}

	/**
	 * 使用过的字母
	 *
	 * @return
	 */
	String used() {
		return used;
	}

	/**
	 * 输入字符
	 *
	 * @param c
	 */
	void type(char c) {
		decreaseTries(c);
		appendToUsed(c);
	}

	private void decreaseTries(char c) {
		if (isUsed(c) || isCharContained(c)) {
			--tries;
		}
	}

	/**
	 * todo:lizebin 这个方法名有误
	 *
	 * @param c
	 * @return
	 */
	private boolean isCharContained(char c) {
		return word.indexOf(c) == -1;
	}

	private void appendToUsed(char input) {
		// 是否为辅音
		if (!isUsed(input)) {
			used += input;
		}
	}

	/**
	 * 字符是否使用
	 *
	 * @param input
	 * @return
	 */
	private boolean isUsed(char input) {
		return used.indexOf(input) != -1;
	}

	/**
	 * 最大尝试次数
	 *
	 * @return
	 */
	int tries() {
		return tries;
	}
}
