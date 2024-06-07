package com.minesweeper.minesweeperserver.socket;

import com.minesweeper.minesweeperserver.logic.PlayerAction;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameManager gameManager;

    //endpoint pod który strzelamy z websocketa
    @MessageMapping("/player-action")
    //ODPOWIEDŹ POLECI NA TEN TOPIC
    @SendTo("/topic/game-update")
    public GameUpdate playerAction(PlayerAction message) {
        gameManager.playerActionWithReinitialize(message);
        return gameManager.getGameUpdate();
    }
}