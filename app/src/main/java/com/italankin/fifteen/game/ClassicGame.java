package com.italankin.fifteen.game;

import java.util.Collections;

public class ClassicGame extends AbstractGame {

    public ClassicGame(int width, int height, Mode mode) {
        super(width, height, mode);
        init();
    }

    private void init() {
        int size = width * height;
        for (int i = 0; i < size; i++) {
            field.add(i);
        }
        Collections.shuffle(field, RANDOM);
        if (!isSolvable()) {
            // if puzzle is not solvable
            // we swap last two digits (e.g. 14 and 15)
            Collections.swap(field, field.indexOf(size - 1), field.indexOf(size - 2));
        }
        // a rare case where we have solved puzzle, create another
        if (isSolved()) {
            init();
        }
    }

    @Override
    protected boolean isSolved() {
        for (int i = 0, size = field.size(); i < size - 1; i++) {
            if (field.get(i) != (i + 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSolvable() {
        int sum = 0, size = height * width;
        // for every number we need to count:
        // - numbers less than chosen
        // - follow chosen number (by rows)
        for (int i = 0; i < size; i++) {
            int n = field.get(i);
            int s = 0;
            for (int j = i + 1; j < size; j++) {
                int m = field.get(j);
                if (n > m && m > 0) {
                    s++;
                }
            }
            sum += s;
        }
        // if we got an even number of columns
        // we need to add row number (counting from down) where zero is located
        if (width % 2 == 0) {
            int z = height - field.indexOf(0) / width;
            if (z % 2 == 0) {
                return sum % 2 == 1;
            }
        }
        // sum should be even
        return sum % 2 == 0;
    }
}
