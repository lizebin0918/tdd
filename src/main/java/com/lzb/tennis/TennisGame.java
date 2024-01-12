package com.lzb.tennis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        getPlayerScore(playerName).increment();
        return displayPlayerScore(playerName);
    }

    private boolean isWin(String playerName) {
        int currentScore = getPlayerScore(playerName).getValue();
        int otherScore = getOtherPlayerScore(playerName).getValue();
        return currentScore >= 4 && currentScore - otherScore >= 2;
    }

    public String displayPlayerScore(String playerName) {
        if (isWin(playerName)) {
            return "win";
        }
        if (isDeuce()) {
            return "deuce";
        }
        if (isAdvantage(playerName)) {
            return "advantage";
        }
        Score score = getPlayerScore(playerName);
        return SCORE_DISPLAY_TEXT[score.getValue()];
    }

    private static Score getPlayerScore(String playerName) {
        return playerToScore.get(playerName);
    }

    private static boolean isDeuce() {
        List<Score> scores = new ArrayList<>(playerToScore.values());
        // 大于3分，而且比分相等
        return scores.stream().allMatch(Score::isGreaterForty)
                && (scores.get(0).getValue() - scores.get(1).getValue() == 0);
    }

    public boolean isAdvantage(String playerName) {
        Score currentScore = getPlayerScore(playerName);
        Score otherScore = getOtherPlayerScore(playerName);
        if (currentScore.isGreaterForty() && otherScore.isGreaterForty()
                && currentScore.getValue() - otherScore.getValue() == 1) {
            return true;
        }
        return false;
    }

    protected Score getOtherPlayerScore(String playerName) {
        Map.Entry<String, Score> other = playerToScore.entrySet().stream()
                .filter(e -> !e.getKey().equals(playerName)).findFirst().orElseThrow();
        return other.getValue();
    }

}
