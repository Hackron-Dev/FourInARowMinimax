package org.Game;

import static java.lang.Math.pow;

public class Evaluator {


    public static int evaluateBoard(int[] board) {
        int finalMark = 0;
        for (int[] ints : Board.FinalArr) {
            finalMark += evaluateLine(ints, board);
        }

        return finalMark;
    }

    public static int evaluateLine(int[] ints, int[] board) {
        int player = 0;
        int counter = 0;
        for (int el : ints) {
            if (board[el] != 0 && player == 0) {
                player = board[el];
                counter++;
            } else if (player != board[el] && board[el] != 0 && player != 0) {
                return 0;
            } else if (player == board[el] && board[el] != 0) {
                counter++;
            }

        }
        if (player == 2) {
            player = -1;
        }
        switch (counter) {
            case 0:
                return 0;
            case 1:
                return 0;
            case 2:
                return player * 10;
            case 3:
                return player * 1000;
            case 4:
                return player * 100000;
        }
        return 0;

    }
}
