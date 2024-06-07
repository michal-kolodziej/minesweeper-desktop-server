package com.minesweeper.minesweeperserver.socket;

import com.minesweeper.minesweeperserver.logic.Cell;

public class GameUpdate {
    final String[][] board;
    final int remainingMines;

    public GameUpdate(Cell[][] cells, int remainingMines) {
        this.board = convertCells(cells);
        this.remainingMines = remainingMines;
    }

    private String[][] convertCells(Cell[][] cells) {
        String[][] strings = new String[cells.length][cells[0].length];
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                Cell cell = cells[row][col];
                if (cell.isVisible()) strings[row][col] = cell.getSurroundingMines() + "";
                if (!cell.isVisible()) strings[row][col] = "E";
                if (cell.isFlagged()) strings[row][col] = "F";
            }
        }
        return strings;
    }

    public String[][] getBoard() {
        return board;
    }
}
