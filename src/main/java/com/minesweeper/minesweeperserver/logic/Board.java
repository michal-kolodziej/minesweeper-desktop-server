package com.minesweeper.minesweeperserver.logic;

import com.minesweeper.minesweeperserver.logic.dto.GameSettings;
import com.minesweeper.minesweeperserver.logic.dto.PlayerAction;
import com.minesweeper.minesweeperserver.logic.interfaces.Operations;

public class Board implements Operations {

    private BoardState state;

    public Board(GameSettings gameSettings) {
        state = new BoardState(gameSettings);
    }

    @Override
    public BoardState playerAction(PlayerAction playerAction) {
//        Cell[][] cells = state.getCells();
//        Cell cell = cells[response.getCol()][response.getRow()];
        return null;
    }
}