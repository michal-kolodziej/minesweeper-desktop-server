package com.minesweeper.minesweeperserver.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private BoardState boardState = new BoardState(new String[][]{
            {"T", "T"},
            {"T", "T"}
    });

    //endpoint pod który strzelamy z websocketa
    @MessageMapping("/hello")
    //ODPOWIEDŹ POLECI NA TEN TOPIC
    @SendTo("/topic/greetings")
    public String[][] greeting(PlayerActionDto message) {
        boardState.updateField(message.row(), message.col(), message.action());
        return boardState.getBoard();
    }

}