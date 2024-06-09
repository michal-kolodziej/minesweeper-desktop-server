package com.minesweeper.minesweeperserver.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardGeneratorTest {

    @Test
    public void testSurroundingMinesAreCalculatedCorrectly() {
        BoardGenerator boardGenerator = new BoardGenerator();
        Cell[][] cells = new Cell[][]{
                {new Cell(CellContent.EMPTY), new Cell(CellContent.MINE), new Cell(CellContent.EMPTY)},
                {new Cell(CellContent.MINE), new Cell(CellContent.EMPTY), new Cell(CellContent.MINE)},
                {new Cell(CellContent.EMPTY), new Cell(CellContent.MINE), new Cell(CellContent.EMPTY)}
        };

        // when
        boardGenerator.calculateSurroundingMines(new Board(cells));

        // then
        assertAll(
                () -> assertEquals(4, cells[1][1].getSurroundingMines()),
                () -> assertEquals(2, cells[0][0].getSurroundingMines()),
                () -> assertEquals(2, cells[1][0].getSurroundingMines())
        );
    }
}