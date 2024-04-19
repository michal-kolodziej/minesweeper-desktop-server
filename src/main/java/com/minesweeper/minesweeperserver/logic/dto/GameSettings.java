package com.minesweeper.minesweeperserver.logic.dto;

import lombok.Data;

@Data
public class GameSettings {
    private int rows;
    private int cols;
    private int mines;

    public GameSettings(int rows, int cols, int mines) {
        if (rows < 1 || cols < 1 || mines > rows * cols)
            throw new IllegalArgumentException(String.format("Cannot create board with: %d rows, %d cols, %d mines", rows, cols, mines));
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }
}