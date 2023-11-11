package com.minesweeper.minesweeperserver.logic;

import com.minesweeper.minesweeperserver.logic.dto.Cell;
import com.minesweeper.minesweeperserver.logic.dto.GameSettings;
import com.minesweeper.minesweeperserver.logic.dto.GameState;
import com.minesweeper.minesweeperserver.logic.enums.CellContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
public class BoardState {
    private GameState state;
    private Cell[][] cells;
    private GameSettings gameSettings;

    public BoardState(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.state = new GameState(gameSettings.getMines());
        this.cells = generateCells(gameSettings.getCols(), getGameSettings().getRows(), gameSettings.getMines());
    }

    private Cell[][] generateCells(int cols, int rows, int mines) {
        Cell[][] cells = generateEmptyCells(cols, rows);
        for (int i = 0; i < mines; i++) {
            generateMine(cols, rows, cells);
        }
        calculateSurroundingMines(cols, rows);
        return cells;
    }

    private static void generateMine(int cols, int rows, Cell[][] cells) {
        int col = new Random().nextInt(cols);
        int row = new Random().nextInt(rows);
        if (cells[col][row].isMine()) {
            generateMine(cols, rows, cells);
        } else {
            cells[col][row] = new Cell(CellContent.MINE);
        }
    }

    private static Cell[][] generateEmptyCells(int cols, int rows) {
        Cell[][] cells = new Cell[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                cells[i][j] = new Cell(CellContent.EMPTY);
            }
        }
        return cells;
    }

    public void calculateSurroundingMines(int cols, int rows) {
        int[][] mines = calculateSurroundingMinesMap(cells, cols, rows);
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                cells[col][row].setSurroundingMines(mines[col][row]);
            }
        }
    }

    private int[][] calculateSurroundingMinesMap(Cell[][] cells, int cols, int rows) {
        int[][] mineField = new int[cols][rows];

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                if (cells[col][row].isMine()) {
                    mineField[col][row] = 1; // If it's mine = 1
                } else {
                    for (int i = Math.max(0, col - 1); i <= Math.min(cols - 1, col + 1); i++) {
                        for (int j = Math.max(0, row - 1); j <= Math.min(rows - 1, row + 1); j++) {
                            mineField[i][j] += cells[col][row].isMine() ? 1 : 0; // Counting surrounding mines
                        }
                    }
                }
            }
        }
        return mineField;
    }
}
