package com.minesweeper.minesweeperserver.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConnectionHandler implements Runnable {
    private final Consumer<ConnectionHandler> onDisconnect;
    private final BiConsumer<String, ConnectionHandler> onClientMessageReceived;
    public final String clientId;
    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;

    public ConnectionHandler(Socket socket, Consumer<ConnectionHandler> onDisconnect, BiConsumer<String, ConnectionHandler> onClientMessageReceived) throws IOException {
        this.clientSocket = socket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.onDisconnect = onDisconnect;
        this.onClientMessageReceived = onClientMessageReceived;
        this.clientId = UUID.randomUUID().toString();
    }

    public void sendMessageToClient(String message){
        out.println(message);
    }
    public void run() {
        try {
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                onClientMessageReceived.accept(clientMessage, this);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to interact with a client");
            System.out.println(e.getMessage());
        } finally {
            try {
                onDisconnect.accept(this);
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Exception caught when trying to close resources");
                System.out.println(e.getMessage());
            }
        }
    }
}
