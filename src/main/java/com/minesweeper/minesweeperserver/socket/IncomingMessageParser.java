package com.minesweeper.minesweeperserver.socket;

import org.apache.commons.lang3.tuple.Pair;

public class IncomingMessageParser {
    public static Pair<Integer, Integer> mousePos(String msg){
        String[] split = msg.split(",");
        int x = Integer.parseInt(split[2]);
        int y = Integer.parseInt(split[4]);
        return Pair.of(x,y);
    }

    public static Pair<Integer, Integer> click(String message){
        String[] split = message.split(",");
        int x = Integer.parseInt(split[2]);
        int y = Integer.parseInt(split[4]);
        return Pair.of(x,y);
    }

    public static Pair<Integer, Integer> flag(String message){
        String[] split = message.split(",");
        int x = Integer.parseInt(split[2]);
        int y = Integer.parseInt(split[4]);
        return Pair.of(x,y);
    }
}
