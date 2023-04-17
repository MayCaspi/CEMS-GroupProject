package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginController {
    public void logIN(ActionEvent e) {

        try {
            Parent mainMenuRoot = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Scene mainMenuScene = new Scene(mainMenuRoot);
            Stage mainMenuStage = new Stage();
            // mainMenuStage.setTitle("CEMS Main Menu");
            mainMenuStage.getIcons().add(new Image("application/images/icons8-title-icon.png"));

            mainMenuStage.setScene(mainMenuScene);
            mainMenuStage.show();

            Node source = (Node) e.getSource();

            // Get the Stage object that contains the source node
            Stage stage = (Stage) source.getScene().getWindow();

            // Close the stage
            stage.close();

            // Close the LoginWindow

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
