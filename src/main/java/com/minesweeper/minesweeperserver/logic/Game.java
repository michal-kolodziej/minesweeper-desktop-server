package com.minesweeper.minesweeperserver.logic;

import com.minesweeper.minesweeperserver.socket.GameUpdate;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.minesweeper.minesweeperserver.logic.GameStatus.LOST;
import static com.minesweeper.minesweeperserver.logic.GameStatus.WON;

public class Game {
    private final Board board;
    private final GameState gameState;
    private final BiConsumer<PlayerAction, List<Pair<Integer, Integer>>> onGameLost;
    private final Consumer<PlayerAction> onGameWon;

    public Game(GameSettings gameSettings, BoardGenerator boardGenerator, BiConsumer<PlayerAction, List<Pair<Integer, Integer>>> onGameLostCallback,
                Consumer<PlayerAction> onGameWonCallback) {
        this.board = boardGenerator.generate(gameSettings);
        this.gameState = new GameState(gameSettings.getMines());
        this.onGameLost = onGameLostCallback;
        this.onGameWon = onGameWonCallback;
    }

    public boolean isGameOver() {
        return gameState.isGameOver();
    }

    public void playerAction(PlayerAction playerAction) {
        //if player makes an action after game is over - ignore
        if (gameState.isGameOver()) return;

        Cell cell = board.getCells()[playerAction.row()][playerAction.col()];
        if (playerAction.action() == PlayerActionType.FLAG) {
            boolean toggleResult = cell.toggleFlag();
            // decrease remainingBombs counter
            gameState.updateRemainingMines(toggleResult ? -1 : 1);
        } else if (playerAction.action() == PlayerActionType.CLICK) {
            //if clicked field is bomb - game over.
            if (cell.isMine()) {
                onGameLost.accept(playerAction, board.getMineLocations());
                gameState.setCurrentStatus(LOST);
            } else {
                // this can also cause game over
                uncoverCell(playerAction.row(), playerAction.col());
            }
        }
        // check all bomb cells are flagged, if so - game won
        if (board.allBombsCorrectlyFlagged()) {
            onGameWon.accept(playerAction);
            gameState.setCurrentStatus(WON);
            System.out.println("!!! !!! !!! GAME WON !!! !!! !!!");
        }
    }
    public GameUpdate getGameUpdate() {
        return new GameUpdate(board.getCells(), gameState.getRemainingMines());
    }

    private void uncoverCell(int row, int col) {
        Cell cell = board.getCells()[row][col];
        // set clicked non-bomb cell visible
        if(!cell.isMine()){
            cell.setVisible(true);
        } else {
            gameState.setCurrentStatus(LOST);
            return;
        }
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
