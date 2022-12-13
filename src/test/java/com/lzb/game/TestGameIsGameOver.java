package com.lzb.game;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * 测试游戏结束<br/>
 *
 * 什么情况下，游戏会game over？type的时候，但是type()要返回布尔值？或者抛一个gameOver的异常？
 * 这里采用的方式是回调函数
 *
 * Created on : 2022-12-13 17:27
 * @author mac
 */
class TestGameIsGameOver {

	private static final char ANY_CHAR = 'a';

	@Test
	void game_not_over_when_game_start() {
		Game game = new Game("word");
		Runnable mockAfterGameOver = mock(Runnable.class);
		game.type(ANY_CHAR, mockAfterGameOver);
		verify(mockAfterGameOver, never()).run();
	}

	@Test
	void game_over_when_last_try_failed() {
		Game game = new Game("word");
		Runnable mockAfterGameOver = mock(Runnable.class);
		IntStream.range(-0, 11).forEach(i -> {
			game.type(ANY_CHAR, () -> {});
		});
		game.type(ANY_CHAR, mockAfterGameOver);
		verify(mockAfterGameOver).run();
	}

}
