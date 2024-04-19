package com.minesweeper.minesweeperserver.socket;

import com.minesweeper.minesweeperserver.logic.BoardGenerator;
import com.minesweeper.minesweeperserver.logic.Game;
import com.minesweeper.minesweeperserver.logic.dto.GameSettings;
import com.minesweeper.minesweeperserver.logic.dto.PlayerAction;
import org.springframework.stereotype.Component;

@Component
public class GameManager {
    private Game game;
    private final GameSettings defaultGameSettings = new GameSettings(16, 30, 99);

    public void playerAction(PlayerAction action){
        if (game == null) {
            game = new Game(defaultGameSettings, new BoardGenerator());
        }

        if (!game.isGameOver()) {
            game.playerAction(action);
        } else {
            game = new Game(defaultGameSettings, new BoardGenerator());
        }
    }

    public GameUpdate getGameUpdate(){
        return game.getGameUpdate();
    }
}
