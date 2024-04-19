package com.minesweeper.minesweeperserver.logic;

import org.apache.logging.log4j.util.BiConsumer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {
    @Test
    void test_forEachCellAround_should_iterateOverSurroundingCells(){
        Board testBoard = new Board(new Cell[][]{
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
        });
        RowColStoringBiConsumer testConsumer = new RowColStoringBiConsumer();

        testBoard.forEachCellAround(1, 1, testConsumer);

        assertThat(testConsumer.getRows()).containsExactly(0,0,0,1,1,2,2,2);
        assertThat(testConsumer.getCols()).containsExactly(0,1,2,0,2,0,1,2);
    }

    @Test
    void test_forEachCellAround_should_iterateOverSurroundingCells_topLeftCorner(){
        Board testBoard = new Board(new Cell[][]{
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
        });
        RowColStoringBiConsumer testConsumer = new RowColStoringBiConsumer();

        testBoard.forEachCellAround(0, 0, testConsumer);

        assertThat(testConsumer.getRows()).containsExactly(0,1,1);
        assertThat(testConsumer.getCols()).containsExactly(1,0,1);
    }

    @Test
    void test_forEachCellAround_should_iterateOverSurroundingCells_topRightCorner(){
        Board testBoard = new Board(new Cell[][]{
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
        });
        RowColStoringBiConsumer testConsumer = new RowColStoringBiConsumer();

        testBoard.forEachCellAround(0, 2, testConsumer);

        assertThat(testConsumer.getRows()).containsExactly(0,1,1);
        assertThat(testConsumer.getCols()).containsExactly(1,1,2);
    }

    @Test
    void test_forEachCellAround_should_iterateOverSurroundingCells_bottomLeftCorner(){
        Board testBoard = new Board(new Cell[][]{
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
        });
        RowColStoringBiConsumer testConsumer = new RowColStoringBiConsumer();

        testBoard.forEachCellAround(2, 0, testConsumer);

        assertThat(testConsumer.getRows()).containsExactly(1,1,2);
        assertThat(testConsumer.getCols()).containsExactly(0,1,1);
    }

    @Test
    void test_forEachCellAround_should_iterateOverSurroundingCells_bottomRightCorner(){
        Board testBoard = new Board(new Cell[][]{
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
                {new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY),new Cell(CellContent.EMPTY)},
        });
        RowColStoringBiConsumer testConsumer = new RowColStoringBiConsumer();

        testBoard.forEachCellAround(0, 2, testConsumer);

        assertThat(testConsumer.getRows()).containsExactly(0,1,1);
        assertThat(testConsumer.getCols()).containsExactly(1,1,2);
    }

    static class RowColStoringBiConsumer implements BiConsumer<Integer, Integer> {

        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();

        @Override
        public void accept(Integer currentRow, Integer currentCol) {
            rows.add(currentRow);
            cols.add(currentCol);
        }

        List<Integer> getRows() {
            return rows;
        }

        List<Integer> getCols() {
            return cols;
        }
    }
}