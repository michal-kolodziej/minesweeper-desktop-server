package com.minesweeper.minesweeperserver.socket;

public class BoardState {
    String[][] board;

    BoardState(String[][] board) {
        this.board = board;
    }

    String[][] getBoard() {
        return board;
    }

    public void updateField(int row, int col, String newValue){
        board[row][col] = newValue;
    }
}
