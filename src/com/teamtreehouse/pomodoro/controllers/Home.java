package com.teamtreehouse.pomodoro.controllers;

import com.teamtreehouse.pomodoro.model.Attempt;
import com.teamtreehouse.pomodoro.model.AttemptKind;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.util.Optional;


public class Home {
    private final AudioClip applause;

    @FXML
    private VBox container;

    @FXML
    private Label title;

    @FXML
    private TextArea message;

    @FXML
    private ComboBox<String> timing;

    private Attempt currentAttemp;
    private StringProperty timerText;
    private Timeline timeline;
    private AttemptKind currentKind;

    public Home(){
        timerText = new SimpleStringProperty();
        setTimerText(0);
        applause = new AudioClip(getClass().getResource("/sounds/Applause.mp3").toExternalForm());
//        setTimerText(0);
    }

    public String getTimerText() {
        return timerText.get();
    }

    public StringProperty timerTextProperty() {
        return timerText;
    }

    public void setTimerText(String timerText) {
        this.timerText.set(timerText);
    }

    public void setTimerText(int remainingSeconds){
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        setTimerText(String.format("%02d:%02d",minutes,seconds));
    }

    private void prepareAttempt(AttemptKind kind){
        reset();
        currentAttemp = new Attempt("",kind);
        addAttempStyle(kind);
        title.setText(kind.getDisplayName());
        int time = Integer.parseInt(timing.getValue()) * 60;
        setTimerText(currentAttemp.setSeconds(time));
        timeline = new Timeline();
        timeline.setCycleCount(kind.setTotalSecond(time));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            currentAttemp.tick();
            setTimerText(currentAttemp.getRemainingSecond());
        }));
        timeline.setOnFinished(event -> {
            saveCurrentAttempt();
//            applause.play();
            prepareAttempt(currentAttemp.getKind() == AttemptKind.FOCUS ? AttemptKind.BREAK : AttemptKind.FOCUS);
        });

    }

    private void saveCurrentAttempt() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Time's Up");
        alert.setHeaderText(null);
        alert.setContentText(message.getText());
        alert.show();
        if(alert.isShowing()){
            for(int i = 0; i < 1; i++){
                applause.play();
            }
        }else{
            applause.stop();
        }
        currentAttemp.setMessage("You said this: " + message.getText());
        currentAttemp.save();
    }

    private void reset() {
        clearAttemptStyle();
        if(timeline != null && timeline.getStatus() == Animation.Status.RUNNING){
            timeline.stop();
        }
//        else{
//            System.out.println("Doit y avoir le time set");
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error Alert");
//            alert.setHeaderText(null);
//            alert.setContentText("Sorry you must set the time first");
//            alert.showAndWait();
//        }
    }

    public void playTimer(){
        container.getStyleClass().add("playing");
        timeline.play();
    }

    public void pauseTime(){
        container.getStyleClass().remove("playing");
        timeline.pause();
    }

    private void addAttempStyle(AttemptKind kind) {
        container.getStyleClass().add(kind.toString().toLowerCase());
    }

    private void clearAttemptStyle(){
        container.getStyleClass().remove("playing");
        for(AttemptKind kind: AttemptKind.values()){
            container.getStyleClass().remove(kind.toString().toLowerCase());
        }
    }

    public void handleResart(ActionEvent actionEvent) {

            prepareAttempt(AttemptKind.FOCUS);
            playTimer();
    }

    public void handlePlay(ActionEvent actionEvent) {

        if(currentAttemp == null){
            handleResart(actionEvent);
        }else{
            playTimer();
        }
    }

    public void handlePause(ActionEvent actionEvent) {
        pauseTime();
    }

//    public void setTime(ActionEvent actionEvent) {
//
//        System.out.println(""+timing.getValue());
//    }
}
