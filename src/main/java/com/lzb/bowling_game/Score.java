package com.lzb.bowling_game;

import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2024-01-28 10:32
 *
 * @author lizebin
 */
@Getter
public enum Score {

    ZERO(0, '-'),
    ONE(1, '1'),
    TWO(2, '2'),
    THREE(3, '3'),
    FOUR(4, '4'),
    FIVE(5, '5'),
    SIX(6, '6'),
    SEVEN(7, '7'),
    EIGHT(8, '8'),
    NINE(9, '9'),
    STRIKE(10, 'X'),
    SPARE(10, '/'),
    ;

    private final int score;
    private final char symbol;

    Score(int score, char symbol) {
        this.score = score;
        this.symbol = symbol;
    }

    public int getScore() {
        return score;
    }

    public char getSymbol() {
        return symbol;
    }

    public static Optional<Score> of(Character symbol) {
        if (symbol == null) {
            return Optional.empty();
        }
        for (Score score : Score.values()) {
            if (score.getSymbol() == symbol) {
                return Optional.of(score);
            }
        }
        return Optional.empty();
    }

}
