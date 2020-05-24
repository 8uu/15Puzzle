package com.italankin.fifteen.game;

import com.italankin.fifteen.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class AbstractGame implements Game {

    protected static final Random RANDOM = new Random();

    protected final int width;
    protected final int height;
    protected final Mode mode;

    protected final List<Integer> field;
    protected State state = State.INITIAL;
    protected int moves = 0;
    protected long time = 0;

    public AbstractGame(int width, int height, Mode mode) {
        this.width = width;
        this.height = height;
        this.mode = mode;
        this.field = new ArrayList<>(width * height);
    }

    protected abstract boolean isSolved();

    @Override
    public int move(int x, int y) {
        return move(x, y, y * width + x);
    }

    @Override
    public int move(int index) {
        return move(index % width, index / width, index);
    }

    @Override
    public int numberAt(int index) {
        return field.get(index);
    }

    @Override
    public int numberAt(int x, int y) {
        return field.get(y * width + x);
    }

    @Override
    public State state() {
        return state;
    }

    @Override
    public int moves() {
        return moves;
    }

    @Override
    public long time() {
        return time;
    }

    @Override
    public void incrementTime(long elapsed) {
        time += elapsed;
    }

    @Override
    public void setPaused(boolean paused) {
        if (state == State.STARTED) {
            state = State.PAUSED;
        }
    }

    @Override
    public List<Integer> slideMoves(int index, MoveDirection direction) {
        return slideMoves(index % width, index / width, index, direction);
    }

    @Override
    public List<Integer> slideMoves(int x, int y, MoveDirection direction) {
        return slideMoves(x, y, y * width + x, direction);
    }

    private int move(int x, int y, int index) {
        if (field.get(index) == 0) {
            return index;
        }
        int zeroIndex = field.indexOf(0);
        int zx = zeroIndex % width;
        int zy = zeroIndex / width;
        // if distance to zero more than 1, we cant move
        if (Tools.manhattan(zx, zy, x, y) > 1) {
            return index;
        }
        Collections.swap(field, index, zeroIndex);
        moves++;
        if (isSolved()) {
            state = State.SOLVED;
        }
        return zeroIndex;
    }

    private List<Integer> slideMoves(int x, int y, int index, MoveDirection direction) {
        if (index < 0 || index >= field.size()) {
            return Collections.emptyList();
        }
        int zeroIndex = field.indexOf(0);
        int zx = zeroIndex % width;
        int zy = zeroIndex / width;
        ArrayList<Integer> result = new ArrayList<>();
        if (direction == null) {
            direction = getMoveDirection(x, y);
        }
        switch (direction) {
            case UP:
                // check we're moving tiles in the same column
                if (x != zx) {
                    break;
                }
                for (int i = zy + 1; i < Math.min(height, y + 1); i++) {
                    result.add(i * width + zx);
                }
                break;

            case RIGHT:
                // check we're moving tiles in the same row
                if (y != zy) {
                    break;
                }
                for (int i = zx - 1; i >= Math.max(0, i); i--) {
                    result.add(zy * width + i);
                }
                break;

            case DOWN:
                // check we're moving tiles in the same column
                if (x != zx) {
                    break;
                }
                for (int i = zy - 1; i >= Math.max(0, y); i--) {
                    result.add(i * width + zx);
                }
                break;

            case LEFT:
                // check we're moving tiles in the same row
                if (y != zy) {
                    break;
                }
                for (int i = zx + 1; i < Math.min(width, i + 1); i++) {
                    result.add(zy * width + i);
                }
                break;
        }
        return result;
    }

    private MoveDirection getMoveDirection(int x, int y) {
        int zeroPos = field.indexOf(0);
        int dx = zeroPos % width - x;
        int dy = zeroPos / width - y;
        if (Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? MoveDirection.RIGHT : MoveDirection.LEFT;
        } else {
            return dy > 0 ? MoveDirection.DOWN : MoveDirection.UP;
        }
    }
}
