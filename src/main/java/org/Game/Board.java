package org.Game;
import java.util.*;

public class Board {
    static int SIZE = 42;


    public static int[][] FinalArr = boardInit();
      public int[] board = new int[SIZE];
//         static int[] board =   {   0,2,1,2,2,0,1, //test-board
//                                    0,1,1,2,1,0,2,
//                                    1,1,1,2,2,0,2,
//                                    2,2,2,1,1,0,2,
//                                    1,1,1,2,2,2,1,
//                                    2,1,2,1,1,1,2};


    public static int[][] boardInit() {
        ArrayList<ArrayList<Integer>> GlobalCheck = new ArrayList<>();
        GlobalCheck.addAll(XYCheck());
        GlobalCheck.addAll(DiogonalCheck());
        FinalArr = ArrConvertor(GlobalCheck);

        return FinalArr;

    }

    public static int WinCheck(int[] board) {
        int player;
        boolean Draw = true;
        outerLoop:
        for (int[] ints : FinalArr) {
            player = board[ints[0]];
            for (int el: ints){
                if (board[el] == 0){
                    Draw = false;
                    continue outerLoop;
                } else{
                    if (player != board[el]){
                         continue outerLoop;
                    }
                }

            }
            return player;
        }
        if (Draw){
            return -1;
        }
        return 0;
    }

    public static void boardPrint(int[] board) {
        System.out.println("0_1_2_3_4_5_6");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(board[getI(j, i)] + "|");
            }

            System.out.println();
        }
    }


    public static int getI(int x, int y) {
        return x + y * 7;
    }


    public static ArrayList<ArrayList<Integer>> XYCheck() {
        ArrayList<ArrayList<Integer>> verticalArr = new ArrayList<>();
        ArrayList<ArrayList<Integer>> horizontalArr = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            horizontalArr.add(new ArrayList<>());
            for (int j = 0; j < 7; j++) {
                verticalArr.add(new ArrayList<>());

                horizontalArr.get(i).add(getI(j, i));
                verticalArr.get(j).add(getI(j, i));
            }
        }
        verticalArr.addAll(horizontalArr);
        return verticalArr;
    }

    public static int[] move(int player, int x, int[] board) {
        boolean moved = false;
        while (!moved) {
            for (int i = 5; i >= 0; i--) {
                if (board[x + i * 7] == 0) {
                    board[x + i * 7] = player;
                    moved = true;
                    break;
                }
            }
            if (!moved) {
                //System.out.println("@!#");
                return new int[0];
            }

        }
        return board;

    }


    public static ArrayList<ArrayList<Integer>> DiogonalCheck() {
        ArrayList<ArrayList<Integer>> LeftDioArr = new ArrayList<>();
        ArrayList<ArrayList<Integer>> RightDioArr = new ArrayList<>();


        for (int j = 0; j < 3; j++) {

            LeftDio(LeftDioArr, 0, j);
        }
        for (int j = 0; j < 4; j++) {
            LeftDio(LeftDioArr, j, 0);

        }
        for (int j = 5; j >= 3; j--) {
            RightDio(RightDioArr, j, 0);
        }
        for (int j = 0; j < 3; j++) { // (j,0)
            RightDio(RightDioArr, 6, j);

        }


        LeftDioArr.addAll(RightDioArr);
        return LeftDioArr;

    }

    private static void LeftDio(ArrayList<ArrayList<Integer>> LeftDioArr, int row, int column) {
        ArrayList<Integer> SmallArr = new ArrayList<>();
        while (row < 7 && column < 6) {
            SmallArr.add(getI(row, column));
            row++;
            column++;
        }
        LeftDioArr.add(SmallArr);
    }

    private static void RightDio(ArrayList<ArrayList<Integer>> RightDioArr, int row, int column) {
        ArrayList<Integer> SmallArr = new ArrayList<>();
        while (row >= 0 && column < 6) {
            SmallArr.add(getI(row, column));
            row--;
            column++;

        }
        RightDioArr.add(SmallArr);
    }


  public static int[][] ArrConvertor(ArrayList<ArrayList<Integer>> Arr) {
        Arr = RemoveDublicates(Arr);

        Arr = ArrSplitter(Arr);



        int[][] result = new int[Arr.size()][];
        for (int i = 0; i < Arr.size(); i++) {
            result[i] = new int[Arr.get(i).size()];
            for (int j = 0; j < Arr.get(i).size(); j++) {
                result[i][j] = Arr.get(i).get(j);
            }

        }
        //System.out.println(Arrays.deepToString(result));
        return result;
    }

    public static ArrayList<ArrayList<Integer>> RemoveDublicates(ArrayList<ArrayList<Integer>> Arr) {
        Arr.removeIf(List::isEmpty);
        Arr.sort((list1, list2) -> Integer.compare(list1.get(0), list2.get(0)));
        ArrayList<Integer> prevElement = Arr.get(0);
        for (int i = 1; i < Arr.size(); i++) {
            if (Arr.get(i).equals(prevElement)) {
                Arr.remove(Arr.get(i));
            } else {
                prevElement = Arr.get(i);
            }
        }
        return Arr;
    }


    public static ArrayList<ArrayList<Integer>> ArrSplitter(ArrayList<ArrayList<Integer>> arr){
        //{{1,2,3,4,5},{1,2,3,4,5,6}}


        for( int i = 0; i < arr.size(); i++){
            if ( arr.get(i).size() > 4){
                arr.addAll(convertToFour(arr.get(i)));

            }
        }
        arr.removeIf(sublist -> sublist.size() > 4);


        return arr;
    }
    public static ArrayList<ArrayList<Integer>> convertToFour(ArrayList<Integer> arr) {
        //int[][] newArr = new int[arr.length - 3][4];
        ArrayList<ArrayList<Integer>> newArr = new ArrayList<>();


        for (int i = 0; i < arr.size() - 3; i++) {
            newArr.add(new ArrayList<>());

            for (int j = 0; j < 4; j++) {
                newArr.get(i).add(arr.get(i+j));
            }
        }
        return newArr;
    }

}
