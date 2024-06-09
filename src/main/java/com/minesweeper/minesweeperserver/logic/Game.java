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
            handleFlagClicked(cell);
        } else if (playerAction.action() == PlayerActionType.CLICK) {
            handleClick(cell, playerAction);
        }
        // check all bomb cells are flagged, if so - game won
        if (board.allBombsCorrectlyFlagged()) {
            onGameWon.accept(playerAction);
            gameState.setCurrentStatus(WON);
            System.out.println("!!! !!! !!! GAME WON !!! !!! !!!");
        }
    }

    private void handleClick(Cell cell, PlayerAction playerAction) {
        if (cell.isFlagged()) return;
        //if clicked field is bomb - game over.
        uncoverCell(playerAction.row(), playerAction.col(), playerAction);
    }

    private void handleFlagClicked(Cell cell) {
        if (cell.isVisible()) return;
        boolean toggleResult = cell.toggleFlag();
        // decrease remainingBombs counter
        gameState.updateRemainingMines(toggleResult ? -1 : 1);
    }

    public GameUpdate getGameUpdate() {
        return new GameUpdate(board.getCells(), gameState.getRemainingMines());
    }

    private void uncoverCell(int row, int col, PlayerAction playerAction) {
        Cell cell = board.getCells()[row][col];
        // set clicked non-bomb cell visible
        if (!cell.isMine()) {
            cell.setVisible(true);
        } else {
            //attempt to uncover a cell which is a mine
            gameState.setCurrentStatus(LOST);
            onGameLost.accept(playerAction, board.getMineLocations());
            return;
        }
        // if no bombs around - uncover fields around
        if (cell.getSurroundingMines() == 0) {
            board.forEachCellAround(row, col, (currentRow, currentCol) -> {
                if (!board.getCells()[currentRow][currentCol].isVisible()) {
                    uncoverCell(currentRow, currentCol, playerAction);
                }
            });
        }

        // if amount of flags around is equal to surrounding bombs, uncover covered fields
        if (board.getSurroundingFlags(row, col) == cell.getSurroundingMines()) {
            board.forEachCellAround(row, col, (currentRow, currentCol) -> {
                if (!board.getCells()[currentRow][currentCol].isVisible() && !board.getCells()[currentRow][currentCol].isFlagged()) {
                    uncoverCell(currentRow, currentCol, playerAction);
                }
            });
        }
    }
}
