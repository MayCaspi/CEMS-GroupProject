package application.mainMenuScreen;

import Client.Client;
import application.Simulation.SmsEmailPopUpController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import util.*;
import Client.ExitButton;
import Client.LogOut;
/**
 * Main menu screen for students
 */
public class MainMenuStudentController {
    final SmsEmailPopUpController smsEmailPopUpController = new SmsEmailPopUpController();
    @FXML
    private AnchorPane header;
    @FXML
    private Text fullNameText;

    /**
     * Initializes the main menu screen for the student.
     * Enables dragging and dropping of the application window using the header pane.
     * Sets the full name of the authenticated user.
     */
    public void initialize() {
        ScreenManager.dragAndDrop(header);
        fullNameText.setText(Client.user.getFullName());
    }

    /**

     Displays a pop-up notification indicating that a test has been approved.
     */
    public void showTestApprovedPopUp() {
        Platform.runLater(() -> {

            smsEmailPopUpController.SetInfoField("Your test was approved by the lecturer!\nEnter View Tests to see more information", Client.user.getFullName(), Client.user.getEmail(), Client.user.getPhone());
            ScreenManager.popUpScreen(PathConstants.SmsEmailPopUp);
        });
    }

    /**
     * Navigates to the screen where the student can enter the code for a test.
     *
     * @param event The event triggered by the enter test button click.
     */
    public void EnterTest(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.EnterCodeForTestStudent);
    }

    /**
     * Navigates to the view tests screen for the student.
     *
     * @param event The event triggered by the view tests button click.
     */
    public void ViewTests(ActionEvent event) {
        // TODO: Implement the logic to navigate to the view tests screen
        ScreenManager.goToNewScreen(event, PathConstants.ViewTestsStudent);
    }

    /**
     * Logs out the user and navigates back to the login screen.
     *
     * @param event The event triggered by the logout button click.
     */
    public void LogOut(ActionEvent event) {
        LogOut.logOutToLoginScreen(event);
    }


    /**
     * Closes the application.*
     */
    @FXML
    private void closeClient() {
        ExitButton.closeClient();
    }


    /**
     * Minimizes the application window.
     *
     * @param event The event triggered by the minimize button click.
     */
    @FXML
    public void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }
}
