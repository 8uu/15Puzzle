package com.italankin.fifteen.game;

import java.util.List;

public interface Game {

    int move(int x, int y);

    int move(int index);

    int numberAt(int index);

    int numberAt(int x, int y);

    List<Integer> slideMoves(int index, MoveDirection direction);

    List<Integer> slideMoves(int x, int y, MoveDirection direction);

    State state();

    int moves();

    long time();

    void incrementTime(long elapsed);

    void setPaused(boolean paused);

    enum State {
        INITIAL,
        STARTED,
        PAUSED,
        SOLVED
    }

    enum Mode {
        NORMAL,
        HARD
    }

    enum MoveDirection {
        LEFT,
        UP,
        RIGHT,
        DOWN
    }
}
