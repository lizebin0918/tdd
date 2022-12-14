package com.lzb.game;

/**
 * <br/>
 * Created on : 2022-12-14 12:10
 * @author mac
 */
public class Judge {

	private final Game game;

	public Judge(Game game) {
		this.game = game;
	}

	void checkGameWin(Runnable afterGameWin) {
		if (game.getWord().equals(game.discovered())) {
			afterGameWin.run();
		}
	}

	void checkGameOver(Runnable afterGameOver) {
		if (game.tries() == 0) {
			afterGameOver.run();
		}
	}
}
