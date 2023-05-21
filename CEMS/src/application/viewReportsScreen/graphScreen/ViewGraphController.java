package application.viewReportsScreen.graphScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import util.ExitButton;
import util.MinimizeButton;
import util.PathConstants;
import util.ScreenManager;

public class ViewGraphController {

    @FXML
    private AnchorPane header;
    @FXML
    private BarChart<String, Number> reportGraph;

    //todo: add the value of each bar above it
    public void initialize() {
        ScreenManager.dragAndDrop(header);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        int[] gradeRanges = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        int[] numOfStudentsPerGroup = {5, 16, 17, 14, 12, 1, 17, 13, 11, 7};

        // Add the data points to the series
        for (int i = 0; i < gradeRanges.length - 1; i++) {
            String group = gradeRanges[i] + " - " + gradeRanges[i + 1];
            series.getData().add(new XYChart.Data<>(group, numOfStudentsPerGroup[i]));
        }
        reportGraph.getData().add(series);
    }

    public void LogOut(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.loginPath);
    }

    public void closePopUp(ActionEvent event) {
        ExitButton.closePopUp(event);
    }

    @FXML
    public void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }
}
