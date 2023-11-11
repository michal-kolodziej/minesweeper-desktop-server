package com.minesweeper.minesweeperserver.logic.interfaces;

import com.minesweeper.minesweeperserver.logic.dto.Cell;
import com.minesweeper.minesweeperserver.logic.dto.PlayerAction;

public interface Operations {

    Cell[][] playerAction(PlayerAction playerAction);
}