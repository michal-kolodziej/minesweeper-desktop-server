package com.minesweeper.minesweeperserver.socket;

import com.minesweeper.minesweeperserver.logic.BoardGenerator;
import com.minesweeper.minesweeperserver.logic.Game;
import com.minesweeper.minesweeperserver.logic.dto.GameSettings;
import com.minesweeper.minesweeperserver.logic.dto.PlayerAction;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final GameSettings gameSettings = new GameSettings(21, 37, 99);
    private Game game = new Game(gameSettings, new BoardGenerator());
    //endpoint pod który strzelamy z websocketa
    @MessageMapping("/hello")
    //ODPOWIEDŹ POLECI NA TEN TOPIC
    @SendTo("/topic/greetings")
    public GameUpdate greeting(PlayerAction message) {
        if(!game.isGameOver()){
            game.playerAction(message);
        } else {
            game = new Game(gameSettings, new BoardGenerator());
        }
        return game.getGameUpdate();
    }
}