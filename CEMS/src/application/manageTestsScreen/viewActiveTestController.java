package application.manageTestsScreen;

import client.Client;
import client.ClientUI;
import common.MsgHandler;
import common.TypeMsg;
import entity.ActiveTest;
import entity.StateManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import util.ExitButton;
import util.MinimizeButton;
import util.PathConstants;
import util.ScreenManager;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class viewActiveTestController {
    @FXML
    public TextField testIdTextField;
    public Label courseNameLabel;
    public Label subjecteNameLabel;
    public TextField numOfQuestionsTextField;
    public TextField testCodeTextField;
    public Label timeLeftLabel;
    public TextArea testCommentsTextArea;
    public TextField testDateTextField;
    public TextField testDurationTextField;
    public TextField startingTimeTextField;

    public Button lockBtn;
    public Label lockTestLabel;

    public Button unlockBtn;
    public Label unlockTestLabel;
    @FXML
    private AnchorPane header;

    private Timeline timer;
    private int testDurationMinutes;
    private int remainingSeconds;

    public StateManagement stateManagement;

    public void initialize() {
        ScreenManager.dragAndDrop(header);
        stateManagement = StateManagement.getInstance();

        //sets the relevant field values
        testIdTextField.setText(stateManagement.getTestID());
        courseNameLabel.setText(stateManagement.course.getCourseName());
        subjecteNameLabel.setText(stateManagement.course.getSubjectName());
        testCodeTextField.setText(stateManagement.getTestCode());
        numOfQuestionsTextField.setText(String.valueOf(stateManagement.getCurrentActivetest().getNumOfQuestions()));
        testDateTextField.setText(stateManagement.getCurrentActivetest().getTestDate());
        testDurationTextField.setText(stateManagement.getTestDuration());
        startingTimeTextField.setText(stateManagement.getCurrentActivetest().getStartingTime());

        //updateRemainingTestTime();
        //startTimer();
    }

    /**
     * calculates the remaining time for the test based on the test's start time and duration
     */
    private void updateRemainingTestTime() {
        int testDurationInSeconds = Integer.parseInt(stateManagement.getTestDuration()) * 60;

        // Calculate the remaining seconds based on the current time and the test's starting time
        LocalTime  startTime = LocalTime.parse(stateManagement.getCurrentActivetest().getStartingTime());
        LocalTime endTime = startTime.plus(testDurationInSeconds,ChronoUnit.SECONDS);
        LocalTime currentTime = LocalTime.now();
        remainingSeconds = (int) ChronoUnit.SECONDS.between(currentTime,endTime);
        if (remainingSeconds < 0) {
            remainingSeconds = 0;
        }
    }

    /**
     * updates the timer object to correctly display a test's remaining time
     */
    private void startTimer() {
        int totalSeconds = remainingSeconds;
        final int[] seconds = { totalSeconds };  // Create a final array to hold the remaining seconds

        timer = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    seconds[0]--;  // Decrement the remaining seconds
                    if (seconds[0] <= 0) {
                        // Timer has ended, perform necessary actions
                        timer.stop();
                        // Additional logic...
                    } else {
                        // Update the timer display on the screen
                        timeLeftLabel.setText(formatTime(seconds[0]));
                    }
                })
        );
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();

        // Update the timer label immediately
        timeLeftLabel.setText(formatTime(seconds[0]));
    }

    /**
     * formats the time  as a hh:mm:ss string
     * @param seconds time in seconds
     * @return the formatted string
     */
    private String formatTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    /**
     * locks the test for all clients - the timer is stopped and the timeLeft value
     * in the activeTest table in the DB is updated
     * @param actionEvent the event that triggered the method
     */
    public void lockTest(ActionEvent actionEvent) {
        timer.pause();

        //switches the visible buttons
        lockBtn.setVisible(false);
        lockTestLabel.setVisible(false);
        unlockBtn.setVisible(true);
        unlockTestLabel.setVisible(true);

        //gets the updated number of remaining minutes at the time of the test being locked
        updateRemainingTestTime();
        String updatedTimeLeft = Integer.toString(remainingSeconds/60);

        MsgHandler updateRemainingTime = new MsgHandler(TypeMsg.UpdateRemainingTime, stateManagement.getCurrentActivetest());
        ClientUI.chat.accept(updateRemainingTime);

    }

    public void unlockTest(ActionEvent actionEvent) {
        timer.play();

        //switches the visible buttons
        lockBtn.setVisible(true);
        lockTestLabel.setVisible(true);
        unlockBtn.setVisible(false);
        unlockTestLabel.setVisible(false);

        //TODO: unlock the active test for all clients through the DB
    }

    public void sendExtraTimeRequest(ActionEvent actionEvent) {

    }
    public void LogOut(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.loginPath);
    }

    public void back(ActionEvent event) {
        stateManagement.resetInstance();
        ExitButton.closePopUp(event);
    }
    @FXML
    private void closeClient(ActionEvent event) {
        ExitButton.closeClient(event);
    }

    @FXML
    public void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }


}
