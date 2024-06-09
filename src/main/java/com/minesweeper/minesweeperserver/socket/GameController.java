package com.minesweeper.minesweeperserver.socket;

import com.minesweeper.minesweeperserver.logic.PlayerAction;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class GameController {

    private final SimpMessagingTemplate template;
    private final GameManager gameManager;

    @MessageMapping("/player-action")
    public void playerAction(PlayerAction message) {
        gameManager.playerActionWithReinitialize(message);
        template.convertAndSend("/topic/game-update", gameManager.getGameUpdate());
    }
}