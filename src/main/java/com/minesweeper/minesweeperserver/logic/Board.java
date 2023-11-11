package com.minesweeper.minesweeperserver.logic;

import com.minesweeper.minesweeperserver.logic.dto.Cell;

public class Board {
    private Cell[][] cells;

    public Board(Cell[][] cells) {
        this.cells = cells;
    }

    Cell[][] getCells() {
        return cells;
    }

    public boolean allBombsCorrectlyFlagged() {
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                Cell checkedCell = cells[row][col];
                // non-bomb cell is flagged
                if (checkedCell.isFlagged() && !checkedCell.isMine()) return false;
                // bomb cell not flagged
                if (checkedCell.isMine() && !checkedCell.isFlagged()) return false;
            }
        }
        return true;
    }
}