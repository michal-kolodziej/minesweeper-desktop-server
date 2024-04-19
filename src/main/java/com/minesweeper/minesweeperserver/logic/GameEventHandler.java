package com.minesweeper.minesweeperserver.logic;

import com.minesweeper.minesweeperserver.socket.ServerLogMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameEventHandler {
    private final ServerLogMessagePublisher logMessagePublisher;
    public void onGameOver(GameStatus result, PlayerAction causingAction) {
        if (result == GameStatus.LOST){
            logMessagePublisher.log("GAME OVER, YOU LOST THANKS TO " + causingAction.username());
        } else {
            logMessagePublisher.log("BRAWO! ZWYCIESTWO!!!!");
        }
    }

    void onNewGame() {
        logMessagePublisher.log("New game started");
    }
}
