package org.Game;
public class Minimax {



    public static int[] minimax(int[] board, int depth, int alpha, int beta, boolean isMaximising) {
        int BestMove=-1;

        if (depth == 0 || Board.WinCheck(board.clone()) != 0) {
            return new int[]{Evaluator.evaluateBoard(board.clone()), };
        }


        if (isMaximising) {
            int MaxValue = Integer.MIN_VALUE;
            for (int i = 0; i < 7; i++) {
                if (Board.move(1, i, board.clone()).length != 0) {// возможный ход?
                    int value = minimax(Board.move(1, i, board.clone()), depth - 1, alpha, beta, false)[0];

                    if (MaxValue < value) {
                        MaxValue = value;
                        BestMove = i;
                    }
                    alpha = Math.max(alpha, value);
                    if (beta <= alpha){
                        break;
                    }



                }
            }
            return new int[]{MaxValue, BestMove};
        } else {
            int MinValue = Integer.MAX_VALUE;
            for (int i = 0; i < 7; i++) {
                if (Board.move(2, i, board.clone()).length != 0) { // возможный ход?
                    int value = minimax(Board.move(2, i, board.clone()), depth - 1, alpha, beta, true)[0];

                    if (MinValue > value) {
                        MinValue = value;
                        BestMove = i;
                    }
                    beta = Math.min(beta, value);
                    if (beta <= alpha){
                        break;
                    }


                }
            }
            return new int[]{MinValue, BestMove};
        }
    }


}
