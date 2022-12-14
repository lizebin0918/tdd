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
		game.typeWithoutCheckGameOver(ANY_CONSONANT, afterGameWin);
		verify(afterGameWin, never()).run();
	}

	@Test
	void game_win_when_type_last_consonant() {
		game.typeWithoutCheckGameOver(LAST_CONSONANT, afterGameWin);
		verify(afterGameWin).run();
	}

}
