package com.minesweeper.minesweeperserver.socket;

import com.minesweeper.minesweeperserver.logic.Board;
import com.minesweeper.minesweeperserver.logic.dto.Cell;
import com.minesweeper.minesweeperserver.logic.dto.GameSettings;
import com.minesweeper.minesweeperserver.logic.dto.PlayerAction;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    //endpoint pod który strzelamy z websocketa
    @MessageMapping("/hello")
    //ODPOWIEDŹ POLECI NA TEN TOPIC
    @SendTo("/topic/greetings")
    public Cell[][] greeting(PlayerAction message) {
        Board board = new Board(new GameSettings(7,7,7));
        return board.playerAction(message).getCells();
    }

}