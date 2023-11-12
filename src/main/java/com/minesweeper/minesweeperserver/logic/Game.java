package com.minesweeper.minesweeperserver.logic;

import com.minesweeper.minesweeperserver.logic.dto.Cell;
import com.minesweeper.minesweeperserver.logic.dto.GameSettings;
import com.minesweeper.minesweeperserver.logic.dto.GameState;
import com.minesweeper.minesweeperserver.logic.dto.PlayerAction;
import com.minesweeper.minesweeperserver.logic.enums.Action;
import com.minesweeper.minesweeperserver.logic.interfaces.Operations;
import com.minesweeper.minesweeperserver.socket.GameUpdate;

import static com.minesweeper.minesweeperserver.logic.dto.GameState.Status.LOST;
import static com.minesweeper.minesweeperserver.logic.dto.GameState.Status.WON;

public class Game implements Operations {
    private final Board board;
    private final GameState gameState;

    public Game(GameSettings gameSettings, BoardGenerator boardGenerator) {
        this.board = boardGenerator.generate(gameSettings);
        this.gameState = new GameState(gameSettings.getMines());
    }

    public boolean isGameOver() {
        return gameState.isGameOver();
    }

    @Override
    public void playerAction(PlayerAction playerAction) {
        //if player makes an action after game is over - ignore
        if (gameState.isGameOver()) return;

        Cell cell = board.getCells()[playerAction.row()][playerAction.col()];
        if (playerAction.action() == Action.FLAG) {
            boolean toggleResult = cell.toggleFlag();
            // decrease remainingBombs counter
            gameState.updateRemainingMines(toggleResult ? -1 : 1);
        } else if (playerAction.action() == Action.CLICK) {
            //if clicked field is bomb - game over.
            if (cell.isMine()) {
                gameState.setCurrentStatus(LOST);
                System.out.println("!!! !!! !!! PRZEGRALES XD !!! !!! !!!");
            } else {
                uncoverCell(playerAction.row(), playerAction.col());
            }
        }
        // check all bomb cells are flagged, if so - game won
        if (board.allBombsCorrectlyFlagged()) {
            gameState.setCurrentStatus(WON);
            System.out.println("!!! !!! !!! GAME WON !!! !!! !!!");
        }
    }

    @Override
    public GameUpdate getGameUpdate() {
        return new GameUpdate(convertCells(board.getCells()), gameState.getRemainingMines());
    }

    private String[][] convertCells(Cell[][] cells) {
        String[][] strings = new String[cells.length][cells[0].length];
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                Cell cell = cells[row][col];
                if (cell.isVisible()) strings[row][col] = cell.getSurroundingMines() + "";
                if (!cell.isVisible()) strings[row][col] = "";
                if (cell.isFlagged()) strings[row][col] = "⚑";
            }
        }
        return strings;
    }

    private void uncoverCell(int row, int col) {
        Cell cell = board.getCells()[row][col];
        // set clicked non-bomb cell visible
        cell.setVisible(true);
        // if no bombs around - uncover fields around
        if (cell.getSurroundingMines() == 0) {
            board.forEachCellAround(row, col, (currentRow, currentCol) -> {
                if (!board.getCells()[currentRow][currentCol].isVisible()) {
                    uncoverCell(currentRow, currentCol);
                }
            });
        }

        // if amount of flags around is equal to surrounding bombs, uncover covered fields
        if (board.getSurroundingFlags(row, col) == cell.getSurroundingMines()) {
            board.forEachCellAround(row, col, (currentRow, currentCol) -> {
                if (!board.getCells()[currentRow][currentCol].isVisible() && !board.getCells()[currentRow][currentCol].isFlagged()) {
                    uncoverCell(currentRow, currentCol);
                }
            });
        }
    }
}
