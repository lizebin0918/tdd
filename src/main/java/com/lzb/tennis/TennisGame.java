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
        playerToScore.put(this.player1Name, new Score(0));
        playerToScore.put(this.player2Name, new Score(0));
    }

    protected TennisGame(String player1Name, Score player1Score, String player2Name, Score player2Score) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        playerToScore.put(this.player1Name, player1Score);
        playerToScore.put(this.player2Name, player2Score);
    }

    private static final String[] SCORE_DISPLAY_TEXT = {"love", "fifteen", "thirty", "forty", "win"};

    private static final Map<String, Score> playerToScore = new HashMap<>();

    public String wonPoint(String playerName) {
        playerToScore.get(playerName).increment();
        return getPlayerScore(playerName);
    }

    public String getPlayerScore(String playerName) {
        if (isDeuce()) {
            // 领先的player需要返回advantage
            Map.Entry<String, Score> current = playerToScore.entrySet().stream()
                    .filter(e -> e.getKey().equals(playerName)).findFirst().orElseThrow();
            Map.Entry<String, Score> other = playerToScore.entrySet().stream()
                    .filter(e -> !e.getKey().equals(playerName)).findFirst().orElseThrow();
            if (current.getValue().getValue() - other.getValue().getValue() == 1) {
                return "advantage";
            }
            return "deuce";
        }
        Score score = playerToScore.get(playerName);
        return SCORE_DISPLAY_TEXT[score.getValue()];
    }

    private static boolean isDeuce() {
        return playerToScore.values().stream().allMatch(s -> s.value == 3);
    }

    static class Score {

        public static Score love() {
            return new Score(0);
        }

        public static Score fifteen() {
            return new Score(1);
        }

        public static Score thirty() {
            return new Score(2);
        }

        public static Score forty() {
            return new Score(3);
        }

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
