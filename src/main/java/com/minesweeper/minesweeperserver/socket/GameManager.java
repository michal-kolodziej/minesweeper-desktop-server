package com.minesweeper.minesweeperserver.socket;

import com.minesweeper.minesweeperserver.logic.*;
import org.springframework.stereotype.Component;

@Component
public class GameManager {
    private final GameEventHandler gameEventHandler;
    private Game game;
    private final GameSettings defaultGameSettings = new GameSettings(16, 30, 99);

    GameManager(GameEventHandler gameEventHandler) {
        this.gameEventHandler = gameEventHandler;
    }

    public void playerAction(PlayerAction action){
        if (game == null) {
            game = new Game(defaultGameSettings, new BoardGenerator(), gameEventHandler);
        }

        if (!game.isGameOver()) {
            game.playerAction(action);
        } else {
            game = new Game(defaultGameSettings, new BoardGenerator(), gameEventHandler);
        }
    }

    public GameUpdate getGameUpdate(){
        return game.getGameUpdate();
    }
}
