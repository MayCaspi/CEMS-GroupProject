package application.viewReportsScreen;

import client.ClientUI;
import common.MsgHandler;
import common.TypeMsg;
import entity.LoggedInUser;
import entity.Report;
import entity.Subject;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.*;

import java.util.ArrayList;
import java.util.List;

public class ViewReportsController {

    @FXML
    private AnchorPane header;

    @FXML
    private ComboBox<Integer> yearComboBox;
    @FXML
    private ComboBox<String> semesterComboBox;
    @FXML
    private ComboBox<String> subjectComboBox;

    @FXML
    private TableView<Report> reportsTableView;
    @FXML
    private Text usernameText;
    @FXML
    public void initialize() {
        ScreenManager.dragAndDrop(header);
        User authenticatedUser = LoggedInUser.getAuthenticatedUser();
        if (authenticatedUser != null) {
            // Set the text in the usernameText element
            usernameText.setText(authenticatedUser.getUserName());
        }
        List<String> username = new ArrayList<>();
        username.add(usernameText.getText());
        // Temporary lists, for presentation
        ObservableList<Integer> yearList = FXCollections.observableArrayList(2023, 2022, 2021, 2020, 2019, 2018, 2017, 2016, 2015);
        ObservableList<String> semesterList = FXCollections.observableArrayList("A", "B", "Summer");
        createSubjectCombo(username);
        // Binds the data into the correct dropdown lists
        yearComboBox.setItems(yearList);
        semesterComboBox.setItems(semesterList);

        // Temporary lists, for presentation
        ObservableList<Report> reportList = FXCollections.observableArrayList(
                new Report(2023, "A", "Computer Science", "Automatons", 42069),
                new Report(2021, "B", "Mathematics", "Statistics", 13096),
                new Report(2021, "Summer", "Statistics", "Bananas n' monkeys", 13099)
        );

        // Sets the column name
        ObservableList<String> columnList = FXCollections.observableArrayList();
        columnList.addAll("Year", "Semester", "Subject", "Course", "Test ID");
        // Creates the report table with the correct data
        TableManager.createTable(reportsTableView, columnList);
        TableManager.importData(reportsTableView, reportList);

        TableManager.addDoubleClickFunctionality(reportsTableView, PathConstants.viewGraphPath, this::setFunctions);

        double[] multipliers = {0.07, 0.1, 0.2, 0.525, 0.1};
        TableManager.resizeColumns(reportsTableView, multipliers);
    }
    private void createSubjectCombo(List<String> username) {
        MsgHandler getSubject = new MsgHandler(TypeMsg.importSubjects, username);
        ClientUI.chat.accept(getSubject);
        List<Object> subjectObjectsList = ClientUI.chat.getSubjects();
        ObservableList<Subject> subjectsList = FXCollections.observableArrayList((List) subjectObjectsList);
        ObservableList<String> subjectNames = FXCollections.observableArrayList();

        for (Subject subject : subjectsList) {
            String subjectNameandID = subject.getSubjectName() + " -" + subject.getSubjectID();
            subjectNames.add(subjectNameandID);
        }
        subjectComboBox.setItems(subjectNames);
    }

    public void setFunctions(String relativePath) {
        ScreenElements<Stage, FXMLLoader> screenElements = ScreenManager.popUpScreen(relativePath);

//        FXMLLoader loader = screenElements.getFXMLLoader();
    }

    public void goBackToPreviousScreen(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.mainMenuPath);
    }

    public void showReports() {
        ScreenManager.popUpScreen(PathConstants.viewGraphPath);
    }

    public void LogOut(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.loginPath);
    }

    @FXML
    public void closeClient(ActionEvent event) {
        ExitButton.closeClient(event);
    }

    @FXML
    public void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }
}