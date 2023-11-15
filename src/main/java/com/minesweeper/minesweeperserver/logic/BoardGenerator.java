package com.minesweeper.minesweeperserver.logic;

import com.minesweeper.minesweeperserver.logic.dto.Cell;
import com.minesweeper.minesweeperserver.logic.dto.GameSettings;
import com.minesweeper.minesweeperserver.logic.enums.CellContent;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BoardGenerator {

    private final Random random = new Random();

    private static Cell[][] generateEmptyCells(int rows, int cols) {
        Cell[][] cells = new Cell[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = new Cell(CellContent.EMPTY);
            }
        }
        return cells;
    }

    public Board generate(GameSettings gameSettings) {
        Cell[][] cells = generateEmptyCells(gameSettings.getRows(), gameSettings.getCols());
        Board board = new Board(cells);
        placeMines(cells, gameSettings.getMines());
        calculateSurroundingMines(board);
        return board;
    }

    private void placeMines(Cell[][] cells, int mines) {
        int minesToGenerate = mines;
        while (minesToGenerate > 0) {
            if (generateMine(cells)) {
                minesToGenerate--;
            }
        }
    }

    private boolean generateMine(Cell[][] cells) {
        int row = random.nextInt(cells.length);
        int col = random.nextInt(cells[0].length);
        if (cells[row][col].isMine()) {
            return false;
        } else {
            cells[row][col] = new Cell(CellContent.MINE);
            return true;
        }
    }


    // For mine-fields we include the mine on which we are currently.
    void calculateSurroundingMines(Board board) {
        Cell[][] cells = board.getCells();
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                cells[row][col].setSurroundingMines(getSurroundingMines(board, row, col));
            }
        }
    }

    private int getSurroundingMines(Board board, int row, int col) {
        return board.sumCellsAround(row, col, (currentRow, currentCol) -> {
            if (board.getCells()[currentRow][currentCol].isMine()) {
                return 1;
            }
            return 0;
        });
    }
}
