package com.lzb.tennis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 You task is to implement a tennis scoring program.
 Summary of tennis scoring:

 1.
 A game is won by the first player to have won at least four points in total and at least two points more than the opponent.

 2.
 The running score of each game is described in a manner peculiar to tennis: scores from zero to three points are described as
 "love", "fifteen", "thirty", and "forty" respectively.

 3.
 If at least three points have been scored by each player, and the scores are equal, the score is "deuce".

 4.
 If at least three points have been scored by each side and a player has one more point than his opponent, the score of the game is "advantage" for the player in the lead.

 [source http://en.wikipedia.org/wiki/Tennis#Scoring]

 */
class TennisGameUnitTest {

    private TennisGame tennisGame;

    private final String player1Name = "A";
    private final String player2Name = "B";

    @BeforeEach
    public void setup() {
        tennisGame = new TennisGame(player1Name, player2Name);
    }

    @Test
    @DisplayName("Player1开始比赛")
    void should_love_when_player1_start_game() {
        assertEquals("love", tennisGame.displayPlayerScore(player1Name));
    }

    @Test
    @DisplayName("Player2开始比赛")
    void should_love_when_player2_start_game() {
        assertEquals("love", tennisGame.displayPlayerScore(player2Name));
    }

    @Test
    @DisplayName("Player1得分:love->fifteen")
    void should_love_to_fifteen_when_player_won() {
        assertEquals("fifteen", tennisGame.wonPoint(player1Name));
    }

    @Test
    @DisplayName("Player1得分:fifteen->thirty")
    void should_fifteen_to_thirty_when_player_won() {
        tennisGame = new TennisGame(player1Name, Score.fifteen(), player2Name, Score.love());
        assertEquals("thirty", tennisGame.wonPoint(player1Name));
    }

    @Test
    @DisplayName("Player1得分:thirty->forty")
    void should_thirty_to_forty_when_player_won() {
        tennisGame = new TennisGame(player1Name, Score.thirty(), player2Name, Score.love());
        assertEquals("forty", tennisGame.wonPoint(player1Name));
    }

    @Test
    @DisplayName("Player1得分:forty->win")
    void should_forty_to_win_when_player_won() {
        tennisGame = new TennisGame(player1Name, Score.forty(), player2Name, Score.love());
        assertEquals("win", tennisGame.wonPoint(player1Name));
    }

    @Test
    @DisplayName("Player1得分:deuce")
    void should_deuce_when_player_won() {
        tennisGame = new TennisGame(player1Name, Score.thirty(), player2Name, Score.forty());
        assertEquals("deuce", tennisGame.wonPoint(player1Name));
    }

    @Test
    @DisplayName("Player1:forty vs Player2:forty")
    void should_deuce_when_forty_tie() {
        tennisGame = new TennisGame(player1Name, Score.forty(), player2Name, Score.forty());
        assertEquals("deuce", tennisGame.displayPlayerScore(player1Name));
        assertEquals("deuce", tennisGame.displayPlayerScore(player2Name));
    }

    @Test
    @DisplayName("当前平局，player1赢1分变成advantage")
    void should_advantage_when_deuce() {
        tennisGame = new TennisGame(player1Name, Score.forty(), player2Name, Score.forty());
        assertEquals("advantage", tennisGame.wonPoint(player1Name));
    }


    @Test
    void test_get_other_player_score() {
        tennisGame = new TennisGame(player1Name, Score.forty(), player2Name, Score.thirty());
        Score otherPlayerScore = tennisGame.getOtherPlayerScore(player1Name);
        assertEquals(Score.thirty(), otherPlayerScore);
    }

    @Test
    @DisplayName("初始化advantage")
    void should_init_advantage() {
        tennisGame = new TennisGame(player1Name, new Score(7), player2Name, new Score(6));
        assertEquals("advantage", tennisGame.displayPlayerScore(player1Name));
    }

    @Test
    @DisplayName("初始化advantage，player1赢一分，比赛结束")
    void should_init_advantage_when_player1_won() {
        tennisGame = new TennisGame(player1Name, new Score(7), player2Name, new Score(6));
        assertEquals("win", tennisGame.wonPoint(player1Name));
    }

    @Test
    @DisplayName("初始化advantage，对方追回一分，educe")
    void should_init_advantage_when_other_player_won() {
        tennisGame = new TennisGame(player1Name, new Score(7), player2Name, new Score(6));
        assertEquals("deuce", tennisGame.wonPoint(player2Name));
    }

    @Test
    @DisplayName("初始化deuce，连续赢两分，比赛结束")
    void should_init_deuce_when_player_won_twice() {
        tennisGame = new TennisGame(player1Name, Score.forty(), player2Name, Score.forty());
        assertEquals("advantage", tennisGame.wonPoint(player2Name));
        assertEquals("win", tennisGame.wonPoint(player2Name));
    }
}