package com.minesweeper.minesweeperserver.logic;

import com.minesweeper.minesweeperserver.logic.dto.Cell;
import com.minesweeper.minesweeperserver.logic.dto.GameSettings;
import com.minesweeper.minesweeperserver.logic.dto.GameState;
import com.minesweeper.minesweeperserver.logic.dto.PlayerAction;
import com.minesweeper.minesweeperserver.logic.enums.Action;
import com.minesweeper.minesweeperserver.logic.interfaces.Operations;

import static com.minesweeper.minesweeperserver.logic.dto.GameState.Status.LOST;
import static com.minesweeper.minesweeperserver.logic.dto.GameState.Status.WON;

public class Game implements Operations {
    private final Board board;
    private final GameState gameState;

    public Game(GameSettings gameSettings, BoardGenerator boardGenerator) {
        this.board = boardGenerator.generate(gameSettings);
        this.gameState = new GameState(gameSettings.getMines());
    }

    @Override
    public Cell[][] playerAction(PlayerAction playerAction) {
        //if player makes an action after game is over - ignore
        // TODO: bring back after logic works fine
        //  if(gameState.isGameOver()) return board.getCells();

        Cell cell = board.getCells()[playerAction.row()][playerAction.col()];
        if (playerAction.action() == Action.FLAG) {
            // check all bomb cells are flagged, if so - game won
            if (board.allBombsCorrectlyFlagged()) {
                gameState.setCurrentStatus(WON);
                return board.getCells();
            }
            cell.toggleFlag();
            // decrease remainingBombs counter
            gameState.decreaseRemainingMines();
        } else if (playerAction.action() == Action.CLICK) {
            //if clicked field is bomb - game over.
            if (cell.isMine()) {
                gameState.setCurrentStatus(LOST);
                return board.getCells();
            } else {
               uncoverCell(playerAction.row(), playerAction.col());
            }
        }
        return board.getCells();
    }

    private void uncoverCell(int row, int col) {
        Cell cell = board.getCells()[row][col];
        // set clicked non-bomb cell visible
        cell.setVisible(true);
        // if no bombs around - uncover fields around
        if (cell.getSurroundingMines() == 0) {
            for (int currentRow = Math.max(0, row - 1); row <= Math.min(board.getCells().length - 1, row + 1); currentRow++) {
                for (int currentCol = Math.max(0, col - 1); col <= Math.min(board.getCells()[0].length - 1, col + 1); currentCol++) {
                    uncoverCell(currentRow, currentCol);
                }
            }
        }
    }

}
