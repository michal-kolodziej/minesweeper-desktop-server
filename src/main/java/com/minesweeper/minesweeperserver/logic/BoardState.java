package com.minesweeper.minesweeperserver.logic;

import com.minesweeper.minesweeperserver.logic.dto.Cell;
import com.minesweeper.minesweeperserver.logic.dto.GameSettings;
import com.minesweeper.minesweeperserver.logic.dto.GameState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    }
}
