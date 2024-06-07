package com.minesweeper.minesweeperserver.logic;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.BiConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Board {
    private final Cell[][] cells;

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

    int getSurroundingFlags(int row, int col) {
        return sumCellsAround(row, col, (currentRow, currentCol) -> {
            if (cells[currentRow][currentCol].isFlagged()) return 1;
            return 0;
        });
    }

    // utility method for doing for-each on each cell around given row and col
    void forEachCellAround(int row, int col, BiConsumer<Integer, Integer> cellProcessor) {
        for (int currentRow = Math.max(0, row - 1); currentRow <= Math.min(cells.length - 1, row + 1); currentRow++) {
            for (int currentCol = Math.max(0, col - 1); currentCol <= Math.min(cells[0].length - 1, col + 1); currentCol++) {
                if (currentRow != row || currentCol != col) {
                    cellProcessor.accept(currentRow, currentCol);
                }
            }
        }
    }

    // utility method for summing value for each cell around given row and col
    int sumCellsAround(int row, int col, BiFunction<Integer, Integer, Integer> cellProcessor) {
        int sum = 0;
        for (int currentRow = Math.max(0, row - 1); currentRow <= Math.min(cells.length - 1, row + 1); currentRow++) {
            for (int currentCol = Math.max(0, col - 1); currentCol <= Math.min(cells[0].length - 1, col + 1); currentCol++) {
                if (currentRow != row || currentCol != col) {
                    sum += cellProcessor.apply(currentRow, currentCol);
                }
            }
        }
        return sum;
    }

    public List<Pair<Integer, Integer>> getMineLocations() {
        List<Pair<Integer, Integer>> mineCoords = new ArrayList<>();
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                Cell checkedCell = cells[row][col];
                if (checkedCell.isMine()) mineCoords.add(new ImmutablePair<>(row,col));
            }
        }
        return mineCoords;
    }
}