package com.minesweeper.minesweeperserver.socket;

import com.minesweeper.minesweeperserver.logic.dto.PlayerAction;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final GameManager gameManager;

    //endpoint pod który strzelamy z websocketa
    @MessageMapping("/hello")
    //ODPOWIEDŹ POLECI NA TEN TOPIC
    @SendTo("/topic/greetings")
    public GameUpdate greeting(PlayerAction message) {
        gameManager.playerAction(message);
        return gameManager.getGameUpdate();
    }
}