package com.teamtreehouse.pomodoro.model;


public enum AttemptKind {

    FOCUS(0, "Focus time"),
    BREAK(0,"Break time");


    private int totalSecond;
    private String dispalyName;
    private static AttemptKind kind = AttemptKind.FOCUS;

    AttemptKind(int totalSecond,String dispalyName) {
        this.totalSecond = totalSecond;
        this.dispalyName = dispalyName;
    }

    public int getTotalSecond() {
        return totalSecond;
    }

    public int setTotalSecond(int totalSecond) {
        this.totalSecond = totalSecond;
        return this.totalSecond;
    }

    public void setAttempts(AttemptKind kind){
        this.kind = kind;
    }

    public String getDisplayName() {
        return dispalyName;
    }
}
