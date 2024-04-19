package com.minesweeper.minesweeperserver.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServerLogMessagePublisher {

    private final SimpMessagingTemplate simpMessagingTemplate;
    public void log(String message){
        //send message to server log topic
        simpMessagingTemplate.convertAndSend("/topic/server-msg", message);
    }
}
