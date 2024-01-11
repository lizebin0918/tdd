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
        assertEquals("love", tennisGame.getPlayerScore(player1Name));
    }

    @Test
    @DisplayName("Player2开始比赛")
    void should_love_when_player2_start_game() {
        assertEquals("love", tennisGame.getPlayerScore(player2Name));
    }

    @Test
    @DisplayName("Player1得分:love->fifteen")
    void should_love_to_fifteen_when_player_won() {
        assertEquals("fifteen", tennisGame.wonPoint(player1Name));
    }

    @Test
    @DisplayName("Player1得分:fifteen->thirty")
    void should_fifteen_to_thirty_when_player_won() {
        tennisGame = new TennisGame(player1Name, TennisGame.Score.FIFTEEN, player2Name, TennisGame.Score.LOVE);
        assertEquals("thirty", tennisGame.wonPoint(player1Name));
    }

    @Test
    @DisplayName("Player1得分:thirty->forty")
    void should_thirty_to_forty_when_player_won() {
        tennisGame = new TennisGame(player1Name, TennisGame.Score.THIRTY, player2Name, TennisGame.Score.LOVE);
        assertEquals("forty", tennisGame.wonPoint(player1Name));
    }

}