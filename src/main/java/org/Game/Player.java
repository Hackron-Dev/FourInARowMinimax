package org.Game;
import java.util.Scanner;

public class Player {

    String Nickname;
    String Color;
    String Timer;
    int Turn;
    boolean isMinimax;

    public Player(String Nickname, String Color, String Timer, int Turn, boolean isMinimax){
        this.Nickname = Nickname;
        this.Color = Color;
        this.Timer = Timer;
        this.Turn = Turn;
        this.isMinimax = isMinimax;
    }


    public String getNickname() {
        return Nickname;
    }
    public String getColor() {
        return Color;
    }
    public String getTimer() {
        return Timer;
    }
    public int getTurn() {
        return Turn;
    }
    public boolean isMinimax() {
        return isMinimax;
    }

//    public String getAllInfo(){
//
//    }
}

