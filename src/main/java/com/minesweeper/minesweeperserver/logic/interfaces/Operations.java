package com.minesweeper.minesweeperserver.logic.interfaces;

import com.minesweeper.minesweeperserver.logic.BoardState;
import com.minesweeper.minesweeperserver.logic.dto.PlayerAction;

public interface Operations {

    BoardState playerAction(PlayerAction playerAction);
}