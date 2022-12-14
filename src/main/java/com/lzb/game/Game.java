package com.lzb.game;

import static java.util.stream.Collectors.joining;

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
	private final String PLACE_HOLDER = "_";

	private final Judge judge;

	public Game(String word) {
		this.word = word;
		this.judge = new Judge(this);
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
	 * 这里有个问题：回调函数会原来越多，不好扩展
	 *
	 * @param c
	 */
	Judge type(char c) {
		decreaseTries(c);
		appendToUsed(c);
		// checkGameOver(afterGameOver);
		// checkGameWin(afterGameWin);
		return judge;
	}

	private void decreaseTries(char c) {
		if (isUsed(c) || !isCharContained(c)) {
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
		return word.indexOf(c) != -1;
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

	/**
	 * 辅音替换成占位符，元音则显示
	 *
	 * @return
	 */
	String discovered() {
		// 我的实现版本
		/*
		StringBuilder sb = new StringBuilder();
		for (char c : word.toCharArray()) {
			if (isVowel(c)) {
				sb.append(c);
				continue;
			}
			sb.append(PLACE_HOLDER);
		}
		return sb.toString();
		*/
		return word.chars().mapToObj(i -> discoveredChar((char)i)).collect(joining());
	}

	private String discoveredChar(char i) {
		if (isUsed(i)) {
			return String.valueOf(i);
		}
		return PLACE_HOLDER;
	}

	public String getWord() {
		return word;
	}

}
