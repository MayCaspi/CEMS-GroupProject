package application.createNewTestScreen;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import util.ExitButton;
import util.MinimizeButton;
import util.PathConstants;
import util.ScreenManager;
public class CreateTestController {

    @FXML
    private AnchorPane header;

    public void initialize() {

        ScreenManager.dragAndDrop(header);
    }


    public void BackToMenu(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.mainMenuPath);
    }

    public void pickQuestion(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.pickQuestionsPath);
    }

    public void openPreview() {
        ScreenManager.popUpScreen(PathConstants.testPreviewPath);
    }

    public void LogOut(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.loginPath);
    }

    public void closeClient(ActionEvent event) {
        ExitButton.closeClient(event);
    }

    @FXML
    public void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }
}