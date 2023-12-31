package application.createNewTestScreen;

import Client.Client;
import Client.ClientUI;
import Client.ExitButton;
import Client.LogOut;
import entity.Course;
import entity.Test;
import entity.TestTypeEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import util.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static util.TextFormatter.formatField;
/**
 * Controller for the "Create Test" screen.
 * Manages test creation by setting up test initial details.
 */
@SuppressWarnings("unchecked")
public class CreateTestController {
    public StateManagement stateManagement;
    Course rowData = null;
    @FXML
    private Text nameAuthor;
    @FXML
    private TextField semesterTextField;
    @FXML
    private TextField sessionTextField;
    @FXML
    private TextField yearField;
    @FXML
    private AnchorPane header;
    @FXML
    private TextField courseIDField;
    @FXML
    private TextField subjectIDField;
    @FXML
    private TextField testIDField;
    @FXML
    private TextField testNumberField;
    @FXML
    private TextField testDurationField;
    @FXML
    private TableView<Course> courseTableView;
    @FXML
    private ComboBox<TestTypeEnum> TypeTestCombo;

    /**
     * Initializes the CreateTestController.
     * Sets up the UI components, formats text fields,
     * displays courses in the table, and retrieves saved data.
     */
    public void initialize() {
        ScreenManager.dragAndDrop(header);

        nameAuthor.setText(Client.user.getName());
        ObservableList<TestTypeEnum> Types = FXCollections.observableArrayList(TestTypeEnum.C,TestTypeEnum.M);
        TypeTestCombo.setItems(Types);
        formatField(yearField, true, 4);
        formatField(testDurationField, true, 4);
        formatField(semesterTextField, false, 1);
        formatField(sessionTextField, false, 1);

        displayCourses();
        retrieveSavedData();
    }

    /**
     * Displays the list of courses in the table view.
     * Retrieves the courses from the server and populates the table.
     * Sets up event handling for selecting a course.
     */
    private void displayCourses() {
        MsgHandler getTableCourse = new MsgHandler(TypeMsg.ImportCourses, Client.user.getUserName());
        ClientUI.chat.accept(getTableCourse);
        List<Object> courseObjectsList = ClientUI.chat.getCourses();

        ObservableList<Course> courses = FXCollections.observableArrayList((List) courseObjectsList);

        ObservableList<String> columnList = FXCollections.observableArrayList();
        columnList.addAll("Course Name", "Subject Name");
        TableManager.createTable(courseTableView, columnList);
        TableManager.importData(courseTableView, courses);

        double[] multipliers = {0.7, 0.295};
        TableManager.resizeColumns(courseTableView, multipliers);

        courseTableView.setOnMouseClicked((e) -> {
            rowData = courseTableView.getSelectionModel().getSelectedItem();
            if (rowData != null) {
                stateManagement.setSubjectID(rowData.getSubjectID());
                setTestData();
                stateManagement.clearTestQuestions();
            }
        });
    }

    /**
     * Sets the initial values of the text fields based on the selected course.
     */
    public void setTestData() {
        courseIDField.setText(rowData.getCourseID());
        subjectIDField.setText(rowData.getSubjectID());

        MsgHandler getTestTable = new MsgHandler(TypeMsg.GetAllTestsTable, null);
        ClientUI.chat.accept(getTestTable);
        List<Object> testTableList = ClientUI.chat.getTests();

        String newTestNumber = correctTestNumber(findFirstFreeIndex(testTableList));
        String testID = rowData.getSubjectID() + rowData.getCourseID() + newTestNumber;

        testIDField.setText(testID);
        testNumberField.setText(newTestNumber);
    }

    /**
     * Finds the first free test number in the given table
     * by scanning the table from its beginning.
     *
     * @param testTableList the table that will be scanned
     * @return the free index
     */
    public int findFirstFreeIndex(List<Object> testTableList) {

        // If the test table is empty
        if (testTableList.isEmpty()) {
            return 1;
        }

        Set<Integer> indexSet = new HashSet<>();
        for (Object test : testTableList) {
            if (test instanceof Test) {
                Test testObject = (Test) test;
                indexSet.add(Integer.parseInt(testObject.getTestNumber()));
            }
        }

        int maxIndex = Collections.max(indexSet);
        for (int i = 1; i <= maxIndex; i++) {
            if (!indexSet.contains(i)) {
                return i;
            }
        }

        return maxIndex + 1;
    }

    /**
     * Corrects the test number format by padding with leading zeros if necessary.
     *
     * @param newTestIndex the test index to format
     * @return the formatted test number
     * @throws IllegalArgumentException if the test number is out of bounds
     */
    public String correctTestNumber(int newTestIndex) {

        if (newTestIndex >= 0 && newTestIndex < 100) {
            if (newTestIndex < 10) {
                return "0" + newTestIndex;
            }
            return String.valueOf(newTestIndex);
        }
        throw new IllegalArgumentException("Test number is out of bounds: " + newTestIndex);
    }

    /**
     * Saves the exam details and performs validation checks.
     * Displays an error popup if any required field is empty.
     */
    @FXML
    public void SaveExam() {
        if (testDurationField.getText().isEmpty()) {
            ShowMessage.showErrorPopup("Please Enter The Duration Time of test!");
        }
        if (rowData == null) {
            ShowMessage.showErrorPopup("Please Press To Select Subject and Course!");
        }
    }

    /**
     * Retrieves the saved data from stateManagement and updates the fields on the screen.
     */
    public void retrieveSavedData() {
        stateManagement = StateManagement.getInstance();
        if (!stateManagement.testID.equals("-1")) {
            ObservableList<Course> list = courseTableView.getItems();
            int indexInTable = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCourseID().equals(stateManagement.getCourse().getCourseID()))
                    indexInTable = i;
            }
            rowData = stateManagement.course;
            courseTableView.getSelectionModel().select(indexInTable);
            courseIDField.setText(stateManagement.course.getCourseID());
            subjectIDField.setText(stateManagement.course.getSubjectID());
            testIDField.setText(stateManagement.getTestID());
            testNumberField.setText(String.valueOf(stateManagement.getTestNum()));
            testDurationField.setText(stateManagement.getTestDuration());
            yearField.setText(stateManagement.getYear());
            sessionTextField.setText(stateManagement.getSession());
            semesterTextField.setText(stateManagement.getSemester());
            TypeTestCombo.valueProperty().set(stateManagement.getTestType());
        }
    }

    /**
     * Navigates back to the previous screen.
     *
     * @param event the action event
     */
    public void BackToMenu(ActionEvent event) {
        StateManagement.resetInstance();
        ScreenManager.goToNewScreen(event, stateManagement.getPreviousScreenPath());
    }

    /**
     * Navigates to the pick questions screen.
     * Validates the input fields and sets the data in the stateManagement object.
     * Displays an error popup if any required field is empty.
     *
     * @param event the action event
     */
    public void goToPickQuestions(ActionEvent event) {
        stateManagement = StateManagement.getInstance();

        if (!sessionTextField.getText().isEmpty()) {
            stateManagement.setSession(sessionTextField.getText());
        } else
            stateManagement.setSession("");
        if (!TypeTestCombo.getSelectionModel().isEmpty()) {
            stateManagement.setTestType(TypeTestCombo.getValue());
        } else
            stateManagement.setTestType(null);

        if (!yearField.getText().isEmpty()) {
            stateManagement.setYear(yearField.getText());
        } else
            stateManagement.setYear("");

        if (!semesterTextField.getText().isEmpty()) {
            stateManagement.setSemester(semesterTextField.getText());
        } else
            stateManagement.setSemester("");

        if (!testDurationField.getText().isEmpty()) {
            stateManagement.setTestDuration(testDurationField.getText());
        } else
            stateManagement.setTestDuration("");

        stateManagement.setDataOfTest(rowData, testNumberField.getText(), testIDField.getText());
        if (rowData == null) {
            ShowMessage.showErrorPopup("Choose subject and course first");
        } else {
            ScreenManager.goToNewScreen(event, PathConstants.pickQuestionsPath);
        }
    }

    /**
     * Logs out the user and navigates to the login screen.
     *
     * @param event the action event
     */
    public void LogOut(ActionEvent event) {
        LogOut.logOutToLoginScreen(event);
    }

    /**
     * Closes the client application.
     */
    public void closeClient() {
        ExitButton.closeClient();
    }

    /**
     * Minimizes the application window.
     *
     * @param event the action event
     */
    @FXML
    public void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }
}
