package com.italankin.fifteen.game;

import java.util.Collections;

public class SnakeGame extends AbstractGame {

    public SnakeGame(int width, int height, Mode mode) {
        super(width, height, mode);
        init();
    }

    @Override
    protected boolean isSolved() {
        for (int i = 0, size = width * height; i < size - 1; i++) {
            int v;
            if ((i / width) % 2 == 0) {
                v = i + 1;
                if (field.get(i) != v) {
                    return false;
                }
            } else {
                v = (width * (1 + i / width) - i % width);
                if (v == size) {
                    v = 0;
                }
                if (field.get(i) != v) {
                    return false;
                }
            }
        }
        return true;
    }

    private void init() {
        int size = width * height;
        for (int i = 0; i < size; i++) {
            field.add(i);
        }
        Collections.shuffle(field, RANDOM);
        if (!isSolvable()) {
            Collections.swap(field, field.indexOf(size - 1), field.indexOf(size - 2));
        }
        if (isSolved()) {
            init();
        }
    }

    private boolean isSolvable() {
        int size = width * height;
        int sum = 0;
        for (int i = 0; i < size; i++) {
            int n;
            if ((i / width) % 2 == 0) {
                n = field.get(i);
            } else {
                n = field.get(width * (1 + i / width) - i % width - 1);
            }
            int s = 0;
            for (int j = i + 1; j < size; j++) {
                int m;
                if ((j / width) % 2 == 0) {
                    m = field.get(j);
                } else {
                    m = field.get(width * (1 + j / width) - j % width - 1);
                }
                if (n > m && m > 0) {
                    s++;
                }
            }
            sum += s;
        }
        return sum % 2 == 0;
    }
}
