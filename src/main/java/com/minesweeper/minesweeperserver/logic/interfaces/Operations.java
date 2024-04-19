package com.minesweeper.minesweeperserver.logic.interfaces;

import com.minesweeper.minesweeperserver.logic.dto.PlayerAction;
import com.minesweeper.minesweeperserver.socket.GameUpdate;

public interface Operations {

    void playerAction(PlayerAction playerAction);

    GameUpdate getGameUpdate();
}