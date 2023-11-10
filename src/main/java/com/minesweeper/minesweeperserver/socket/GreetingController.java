package com.minesweeper.minesweeperserver.socket;

import com.minesweeper.minesweeperserver.socket.playeraction.Field;
import com.minesweeper.minesweeperserver.socket.playeraction.PlayerAction;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.user.SimpUserRegistry;
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
    public String[][] greeting(PlayerAction message) {
        Field field = message.getParsedField();
        boardState.updateField(field, message.getAction());
        return boardState.getBoard();
    }

}