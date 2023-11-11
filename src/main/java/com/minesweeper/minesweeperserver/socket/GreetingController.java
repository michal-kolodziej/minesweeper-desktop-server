package com.minesweeper.minesweeperserver.socket;

import com.minesweeper.minesweeperserver.logic.BoardGenerator;
import com.minesweeper.minesweeperserver.logic.Game;
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

    private final Game game = new Game(new GameSettings(16, 30, 99), new BoardGenerator());
    //endpoint pod który strzelamy z websocketa
    @MessageMapping("/hello")
    //ODPOWIEDŹ POLECI NA TEN TOPIC
    @SendTo("/topic/greetings")
    public String[][] greeting(PlayerAction message) {
        return convertNaPale(game.playerAction(message));
    }

    private String[][] convertNaPale(Cell[][] cells) {
        String[][] strings = new String[cells.length][cells[0].length];
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                Cell cell = cells[row][col];
                if(cell.isVisible()) strings[row][col] = cell.getSurroundingMines() + "";
                if(!cell.isVisible()) strings[row][col] = "?";
                if(cell.isFlagged()) strings[row][col] = "F";
            }
        }
        return strings;
    }

}