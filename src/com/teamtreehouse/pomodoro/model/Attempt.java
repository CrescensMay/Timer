package com.teamtreehouse.pomodoro.model;

import com.teamtreehouse.pomodoro.controllers.Home;


public class Attempt {

    private String message;
    private int remainingSecond;
    private AttemptKind kind;
//    private static  int setSeconds;
    private Home home = new Home();

    public Attempt(String message, AttemptKind kind) {
        this.message = message;
        this.kind = kind;
        remainingSecond = kind.getTotalSecond();
    }

    public String getMessage() {
        return message;
    }

    public int getRemainingSecond() {
        return remainingSecond;
    }

    public int setSeconds(int seconds){
        this.remainingSecond = seconds;
        return remainingSecond;
    }
    public AttemptKind getKind() {
        return kind;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void tick() {
        remainingSecond--;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "message='" + message + '\'' +
                ", remainingSecond=" + remainingSecond +
                ", kind=" + kind +
                '}';
    }

    public void save() {
        System.out.printf("Saving: %s\n",this);
    }
}
