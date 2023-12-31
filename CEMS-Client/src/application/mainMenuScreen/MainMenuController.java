package application.mainMenuScreen;

import Client.ClientUI;
import Client.ExitButton;
import Client.LogOut;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import util.*;

import static Client.Client.user;
/**
 * Main menu screen for lecturers
 */
public class MainMenuController {
    @FXML
    private Text usernameText;
    @FXML
    private AnchorPane header;
    @FXML
    private Pane backPane;


    /**
     * Initializes the main menu screen.
     * Gets the authenticated user from the Client and sets the username.
     * Enables dragging and dropping of the application window using the header pane.
     */
    public void initialize() {
        usernameText.setText(user.getFullName());
        ScreenManager.dragAndDrop(header);
        backPane.setVisible(false);
        if (user.getRole().equals("Head of Department/Lecturer")) {
            backPane.setVisible(true);
        }
    }
    /**

     Navigates back to the pick a role screen. Appears only to users who have two roles - Head Of Department and Lecturers.
     @param event the ActionEvent triggering the navigation
     */
    public void backToPickaRole(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.PickRolePath);
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
     * Navigates to the manage questions screen.
     *
     * @param event The event triggered by the manage questions button click.
     */
    public void manageQuestions(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.manageQuestions);
    }

    /**
     * Navigates to the view reports screen.
     *
     * @param event The event triggered by the view reports button click.
     */
    public void ViewReports(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.viewReportsPath);
    }

    /**
     * Navigates to the create new test screen.
     *
     * @param event The event triggered by the create new test button click.
     */
    public void CreateNewTest(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.createNewTestPath);
    }
    /**

     Initiates the process of managing tests and runs cheating detector on finished tests
     @param event the ActionEvent triggering the management of tests
     */

    public void manageTests(ActionEvent event) {
        MsgHandler<String> cheatingTest = new MsgHandler<>(TypeMsg.DetectedCheating,null);
        ClientUI.chat.accept(cheatingTest);
        ScreenManager.goToNewScreen(event, PathConstants.manageTestsPath);
    }
    /**

     Initiates the process of adding a question
     @param event the ActionEvent triggering the management of tests
     */

    public void addQuestion(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.addQuestionPath);
    }

    /**
     * Closes the application.
     */
    @FXML
    private void closeClient() {
        ExitButton.closeClient();
    }

    /*
      Minimizes the application window.
      @param event The event triggered by the minimize button click.
     */
    @FXML
    public void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }
}