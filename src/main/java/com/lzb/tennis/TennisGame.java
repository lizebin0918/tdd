package com.lzb.tennis;

import java.util.HashMap;
import java.util.Map;

/**
 * 1.了解需求，举例具体比分场景<br/>
 * 2.定义类及api
 * 3.编写测试
 * Created on : 2024-01-11 13:46
 * @author lizebin
 */
public class TennisGame {


    private final String player1Name;
    private final String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        playerToScore.put(player1Name, new Score(0));
        playerToScore.put(player2Name, new Score(0));
    }

    protected TennisGame(String player1Name, Score player1Score, String player2Name, Score player2Score) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        playerToScore.put(player1Name, player1Score);
        playerToScore.put(player2Name, player2Score);
    }

    private static final String[] SCORE_DISPLAY_TEXT = {"love", "fifteen", "thirty", "forty"};

    private static final Map<String, Score> playerToScore = new HashMap<>();

    public String wonPoint(String playerName) {
        playerToScore.get(playerName).increment();
        return getPlayerScore(playerName);
    }

    public String getPlayerScore(String palyerName) {
        Score score = playerToScore.get(palyerName);
        return SCORE_DISPLAY_TEXT[score.getValue()];
    }

    static class Score {

        static final Score LOVE = new Score(0);
        static final Score FIFTEEN = new Score(1);
        static final Score THIRTY = new Score(2);
        static final Score FORTY = new Score(3);

        private int value;

        private Score(int value) {
            this.value = value;
        }

        public void increment() {
            value += 1;
        }

        public int getValue() {
            return this.value;
        }

    }

}
