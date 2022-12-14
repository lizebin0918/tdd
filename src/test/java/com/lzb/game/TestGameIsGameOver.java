package com.lzb.game;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import static com.lzb.game.GameForTest.*;
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

	GameForTest game = new GameForTest("word");
	Runnable afterGameOver = mock(Runnable.class);

	@Test
	void game_not_over_when_game_start() {
		Judge judge = game.type(ANY_CHAR);
		judge.checkGameOver(afterGameOver);
		verify(afterGameOver, never()).run();
	}

	@Test
	void game_over_when_last_try_failed() {
		IntStream.range(0, MAX_TRIES - 1).forEach(i -> {
			game.type(ANY_CHAR);
		});
		Judge judge = game.type(ANY_CHAR);
		judge.checkGameOver(afterGameOver);
		verify(afterGameOver).run();
	}

}
