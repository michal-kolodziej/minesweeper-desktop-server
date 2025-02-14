package com.minesweeper.minesweeperserver.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
@Async
public class GameServer {
    private final List<ConnectionHandler> activeConnections = new ArrayList<>();
    private final ServerSocket serverSocket;
    private BiConsumer<String, ConnectionHandler> onClientMessageReceived = (s, connectionHandler) -> {
    };
    private Consumer<ConnectionHandler> onClientConnected = (connectionHandler) -> {
    };

    GameServer(@Value("${server.port:20667}") int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, this::onDisconnect, this::onClientMessageReceived);
                onClientConnected.accept(connectionHandler);
                activeConnections.add(connectionHandler);
                Executors.newSingleThreadExecutor().submit(connectionHandler);
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
    }

    private void onClientMessageReceived(String receivedMessage, ConnectionHandler sender) {
        System.out.println("got message from connection " + sender.clientId + ": " + receivedMessage);
        onClientMessageReceived.accept(receivedMessage, sender);
    }

    public void sendToAll(String message) {
        activeConnections.forEach(con -> con.sendMessageToClient(message));
    }

    public void sendToAllExcept(String message, List<ConnectionHandler> except) {
        activeConnections.stream()
                .filter(con -> !except.contains(con))
                .forEach(con -> con.sendMessageToClient(message));
    }

    void onDisconnect(ConnectionHandler connectionHandler) {
        activeConnections.remove(connectionHandler);
        System.out.println("active connection count: " + activeConnections.size());
    }

    public void setOnClientMessageReceived(BiConsumer<String, ConnectionHandler> callback) {
        this.onClientMessageReceived = callback;
    }

    public void setOnClientConnected(Consumer<ConnectionHandler> callback) {
        this.onClientConnected = callback;
    }
}
