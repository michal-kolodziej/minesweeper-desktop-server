package com.minesweeper.minesweeperserver.socket;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

// This factory produces messages sent FROM SERVER TO CLIENT
public class OutgoingMessageFactory {
    public static String mousePos(int x, int y, String clientId){
        return "MOUSEPOS,X," + x + ",Y,"+y + ",CLIENTID," + clientId;
    }

    public static String gameUpdate(String[][] board){
        StringBuilder sb = new StringBuilder();
        sb.append("GAMEUPDATE,");
        sb.append("ROWS,").append(board.length).append(",");
        sb.append("COLS,").append(board[0].length).append(",");
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                sb.append(board[row][col]).append(",");
            }
        }

        return sb.toString();
    }

    public static String gameOver(String username, Pair<Integer, Integer> clickedMineCoords, List<Pair<Integer, Integer>> mineLocations){
        StringBuilder sb = new StringBuilder();
        sb
                .append("GAMEOVER,").append("BYUSER,").append(username)
                .append(",XCOORD,").append(clickedMineCoords.getLeft())
                .append(",YCOORD,").append(clickedMineCoords.getRight());

        sb.append(",MINECOORDS,");
        mineLocations.forEach(location -> sb.append(location.getLeft()).append(",").append(location.getRight()).append(","));
        return sb.toString();
    }

    public static String gameWon() {
        return "GAMEWON";
    }
}
