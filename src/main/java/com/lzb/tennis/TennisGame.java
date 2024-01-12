package com.lzb.tennis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
    private Optional<String> winner = Optional.empty();

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

    private static final String[] SCORE_DISPLAY_TEXT = {"love", "fifteen", "thirty", "forty"};

    private static final Map<String, Score> playerToScore = new HashMap<>();

    public String wonPoint(String playerName) {
        int currentScore = playerToScore.get(playerName).increment();
        int otherScore = getOtherPlayerScore(playerName).getValue();
        if (currentScore >= 4 && currentScore - otherScore >= 2) {
            winner = Optional.of(playerName);
        }
        return getPlayerScore(playerName);
    }

    public String getPlayerScore(String playerName) {
        if (winner.isPresent() && winner.get().equals(playerName)) {
            return "win";
        }
        if (isDeuce()) {
            return "deuce";
        }
        if (isAdvantage(playerName)) {
            return "advantage";
        }
        Score score = playerToScore.get(playerName);
        return SCORE_DISPLAY_TEXT[score.getValue()];
    }

    private static boolean isDeuce() {
        List<Score> scores = new ArrayList<>(playerToScore.values());
        // 大于3分，而且比分相等
        return scores.stream().allMatch(s -> s.value >= 3)
                && (scores.get(0).getValue() - scores.get(1).getValue() == 0);
    }

    public boolean isAdvantage(String playerName) {
        // 领先的player需要返回advantage
        Map.Entry<String, Score> current = playerToScore.entrySet().stream()
                .filter(e -> e.getKey().equals(playerName)).findFirst().orElseThrow();
        Map.Entry<String, Score> other = playerToScore.entrySet().stream()
                .filter(e -> !e.getKey().equals(playerName)).findFirst().orElseThrow();
        if (current.getValue().getValue() >= 3 && other.getValue().getValue() >= 3
                && current.getValue().getValue() - other.getValue().getValue() == 1) {
            return true;
        }
        return false;
    }

    protected Score getOtherPlayerScore(String playerName) {
        Map.Entry<String, Score> other = playerToScore.entrySet().stream()
                .filter(e -> !e.getKey().equals(playerName)).findFirst().orElseThrow();
        return other.getValue();
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

        public Score(int value) {
            this.value = value;
        }

        public int increment() {
            value += 1;
            return value;
        }

        public int getValue() {
            return this.value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Score score)) return false;
            return value == score.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

    }

}
