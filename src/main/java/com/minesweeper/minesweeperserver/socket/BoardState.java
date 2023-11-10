package com.minesweeper.minesweeperserver.socket;

import com.minesweeper.minesweeperserver.socket.playeraction.Field;

public class BoardState {
    String[][] board;

    BoardState(String[][] board) {
        this.board = board;
    }

    String[][] getBoard() {
        return board;
    }

    public void updateField(Field field, String newValue){
        board[field.getXCoord()][field.getYCoord()] = newValue;
    }
}
