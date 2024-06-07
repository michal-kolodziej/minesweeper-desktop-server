package com.minesweeper.minesweeperserver.logic;

import lombok.Data;

@Data
public class GameSettings {
    private int rows;
    private int cols;
    private int mines;

    public static GameSettings EXPERT = new GameSettings(16, 30, 99);

    public GameSettings(int rows, int cols, int mines) {
        if (rows < 1 || cols < 1 || mines > rows * cols)
            throw new IllegalArgumentException(String.format("Cannot create board with: %d rows, %d cols, %d mines", rows, cols, mines));
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }
}