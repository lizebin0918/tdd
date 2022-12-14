package com.lzb.game;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * <br/>
 * Created on : 2022-12-14 09:41
 * @author mac
 */
class TestGameIsGameWin {

	public static final char ANY_CONSONANT = 'b';
	public static final char LAST_CONSONANT = 'm';
	GameForTest game = new GameForTest("am");
	Runnable afterGameWin = mock(Runnable.class);

	@Test
	void game_win_when_game_start() {
		Judge judge = game.type(ANY_CONSONANT);
		judge.checkGameWin(afterGameWin);
		verify(afterGameWin, never()).run();
	}

	@Test
	void game_win_when_type_last_consonant() {
		Judge judge = game.type(LAST_CONSONANT);
		judge.checkGameWin(afterGameWin);
		verify(afterGameWin).run();
	}

}
