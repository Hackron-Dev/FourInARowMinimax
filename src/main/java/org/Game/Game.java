package org.Game;

import org.WebAi.WebApi;

import java.sql.SQLOutput;
import java.util.Arrays;

public class Game {
    int turn = 1;
    Board board = new Board();
    WebApi webApi = new WebApi();

    Player MinimaxPlayer;
    Player WebPlayer;
    int Depth = 10;
    int start_depth = Depth;

//TODO 1: сделать динамическую глубину для минимакса
//TODO 2: сделать обнуление для новой игры


    public void setPlayers() {
        Player[] Players = webApi.getPlayersInfo().clone();
        if (Players[0].isMinimax) {
            MinimaxPlayer = Players[0];
            WebPlayer = Players[1];
        } else {
            MinimaxPlayer = Players[1];
            WebPlayer = Players[0];
        }
    }

    private int depthCalculator(int depth) {
        double countZero = Arrays.stream(board.board.clone()).filter(num -> num == 0).count();

        while (Math.pow(7, start_depth) - (Math.pow((int) (7.0 / (42.0 / countZero)), depth + 1) * 10) > 0 && depth <= 100) {

            System.out.println("Round:" + (42 - countZero+1) +" Depth:" + depth + " countZero:" + countZero);
            depth++;

        }
        return depth;
    }


    private void placeMove() {
        int move;
        Depth = depthCalculator(Depth);
        System.out.println("Real Depth: " + Depth);
        if (turn == 1) {
            if (MinimaxPlayer.Turn == 1) {
                move = Minimax.minimax(board.board, Depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true)[1];
                webApi.placeMove(move);
            } else {
                move = webApi.getLastMoveCircle(WebPlayer.Color);
            }

            Board.move(1, move, board.board);
            turn = 2;

        } else {
            if (MinimaxPlayer.Turn == 2) {
                move = Minimax.minimax(board.board, Depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false)[1];
                webApi.placeMove(move);
            } else {
                move = webApi.getLastMoveCircle(WebPlayer.Color);
            }

            Board.move(2, move, board.board);
            turn = 1;
        }


    }


    public void GameCycle() {
        while (Board.WinCheck(board.board) == 0) {
            placeMove();
            System.out.println("___________________");
            Board.boardPrint(board.board);
            System.out.println("___________________");
        }


        switch (Board.WinCheck(board.board)) {
            case 1 -> System.out.println("First Player WON!");
            case 2 -> System.out.println("Second Player WON!");
            case -1 -> System.out.println("Draw!");
        }

    }

}
