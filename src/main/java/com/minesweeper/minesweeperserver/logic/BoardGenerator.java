package com.minesweeper.minesweeperserver.logic;

import com.minesweeper.minesweeperserver.logic.dto.Cell;
import com.minesweeper.minesweeperserver.logic.dto.GameSettings;
import com.minesweeper.minesweeperserver.logic.enums.CellContent;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BoardGenerator {

    public Board generate(GameSettings gameSettings) {
        Cell[][] cells = generateCells(gameSettings.getCols(), gameSettings.getRows(), gameSettings.getMines());
        return new Board(cells);
    }

    private Cell[][] generateCells(int cols, int rows, int mines) {
        Cell[][] cells = generateEmptyCells(cols, rows);
        int minesToGenerate = mines;
        while (minesToGenerate > 0) {
            if (generateMine(cols, rows, cells)) {
                minesToGenerate--;
            }
        }
        calculateSurroundingMines(cells, cols, rows);
        return cells;
    }

    private static boolean generateMine(int cols, int rows, Cell[][] cells) {
        Random random = new Random();
        int col = random.nextInt(cols);
        int row = random.nextInt(rows);
        if (cells[row][col].isMine()) {
            return false;
        } else {
            cells[row][col] = new Cell(CellContent.MINE);
            System.out.println("MINA " + row + " " + col);
            return true;
        }
    }

    private static Cell[][] generateEmptyCells(int cols, int rows) {
        Cell[][] cells = new Cell[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = new Cell(CellContent.EMPTY);
            }
        }
        return cells;
    }

    public void calculateSurroundingMines(Cell[][] cells, int cols, int rows) {
        int[][] mines = calculateSurroundingMinesMap(cells, cols, rows);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col].setSurroundingMines(mines[row][col]);
            }
        }
    }

    private int[][] calculateSurroundingMinesMap(Cell[][] cells, int cols, int rows) {
        int[][] mineField = new int[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (cells[row][col].isMine()) {
                    mineField[row][col] = 1; // If it's mine = 1
                } else {
                    for (int j = Math.max(0, row - 1); j <= Math.min(rows - 1, row + 1); j++) {
                        for (int i = Math.max(0, col - 1); i <= Math.min(cols - 1, col + 1); i++) {
                            mineField[j][i] += cells[row][col].isMine() ? 1 : 0; // Counting surrounding mines
                        }
                    }
                }
            }
        }
        return mineField;
    }
}
