package com.minesweeper.minesweeperserver.socket;

import com.minesweeper.minesweeperserver.logic.*;
import com.minesweeper.minesweeperserver.socket.desktop.ConnectionHandler;
import com.minesweeper.minesweeperserver.socket.desktop.GameServer;
import com.minesweeper.minesweeperserver.socket.desktop.IncomingMessageParser;
import com.minesweeper.minesweeperserver.socket.desktop.OutgoingMessageFactory;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class GameManager {
    private final GameSettings defaultGameSettings = GameSettings.EXPERT;
    private final SimpMessagingTemplate template;
    private final GameServer gameServer;
    private Game game;

    public GameManager(SimpMessagingTemplate template, GameServer gameServer) {
        this.template = template;
        game = new Game(defaultGameSettings, new BoardGenerator(), this::onGameLost, this::onGameWon);
        gameServer.setOnClientMessageReceived(this::onClientMessageReceived);
        gameServer.setOnClientConnected(this::onClientConnected);
        this.gameServer = gameServer;
        gameServer.start();
    }

    private void onGameWon(PlayerAction playerAction) {
        gameServer.sendToAll(OutgoingMessageFactory.gameWon());
    }

    private void onGameLost(PlayerAction playerAction, List<Pair<Integer, Integer>> pairs) {
        Pair<Integer, Integer> clickedMineCoords = new ImmutablePair<>(playerAction.row(), playerAction.col());
        String gameOverMessage = OutgoingMessageFactory.gameOver(playerAction.username(), clickedMineCoords, pairs);
        gameServer.sendToAll(gameOverMessage);
    }

    public GameUpdate getGameUpdate() {
        return game.getGameUpdate();
    }

    public void onClientMessageReceived(String receivedMessage, ConnectionHandler sender) {
        if (receivedMessage.startsWith("MOUSE")) {
            Pair<Integer, Integer> coords = IncomingMessageParser.mousePos(receivedMessage);
            String mousePos = OutgoingMessageFactory.mousePos(coords.getLeft(), coords.getRight(), sender.clientId);
            gameServer.sendToAllExcept(mousePos, Collections.singletonList(sender));
        } else if (receivedMessage.startsWith("CLICK")) {
            Pair<Integer, Integer> click = IncomingMessageParser.click(receivedMessage);
            playerActionWithReinitialize(new PlayerAction(click.getRight(), click.getLeft(), PlayerActionType.CLICK, sender.clientId));
        } else if (receivedMessage.startsWith("FLAG")) {
            Pair<Integer, Integer> click = IncomingMessageParser.flag(receivedMessage);
            playerActionWithReinitialize(new PlayerAction(click.getRight(), click.getLeft(), PlayerActionType.FLAG, sender.clientId));
        }
    }

    public void onClientConnected(ConnectionHandler connectionHandler) {
        connectionHandler.sendMessageToClient(OutgoingMessageFactory.gameUpdate(getGameUpdate().getBoard()));
    }

    void playerActionWithReinitialize(PlayerAction playerAction) {
        if(!game.isGameOver()){
            game.playerAction(playerAction);
        } else {
            game = new Game(defaultGameSettings, new BoardGenerator(), this::onGameLost, this::onGameWon);
        }
        gameServer.sendToAll(OutgoingMessageFactory.gameUpdate(getGameUpdate().getBoard()));
        template.convertAndSend("/topic/game-update", getGameUpdate());
    }
}
