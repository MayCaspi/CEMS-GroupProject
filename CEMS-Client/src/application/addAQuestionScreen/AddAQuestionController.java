package application.addAQuestionScreen;


import Client.Client;
import Client.ClientUI;
import Client.ExitButton;
import Client.LogOut;
import entity.Course;
import entity.Question;
import entity.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
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

 The AddAQuestionController class manages the user interface and functionality of the "Add a Question" screen.
 It allows users to enter question details, validate the data, and save the new question to the database.
 */

public class AddAQuestionController {
    public static ObservableList<Course> coursesList;
    public ComboBox<String> subjectComboBox;
    public String newQuestionNumber;
    public String QuestionID;

    @FXML
    private ComboBox<String> CourseComboBox;
    @FXML
    private AnchorPane header;
    @FXML
    private TextField questionID;
    @FXML
    private TextArea answer1;
    @FXML
    private TextArea answer2;
    @FXML
    private TextArea answer3;
    @FXML
    private TextArea answer4;
    @FXML
    private TextField questionNumber;
    @FXML
    private TextArea questionTextField;
    @FXML
    private TextField CorrectAnswer;
    @FXML
    private Text usernameText;

    /**
     * This method is called when the FXML file is loaded.
     * It initializes the necessary components, sets event listeners,
     * and retrieves subject and course information from the server.
     */
    public void initialize() {
        ScreenManager.dragAndDrop(header);

        formatField(CorrectAnswer, true, 1);

        usernameText.setText(Client.user.getName());
        createSubjectCombo(Client.user.getUserName());
        createCourseCombo(Client.user.getUserName());

        questionNumber.setVisible(false);
        questionID.setVisible(false);

        subjectComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> checkFieldsAndExecuteActions());
        CourseComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> checkFieldsAndExecuteActions());

        checkFieldsAndExecuteActions();
    }

    /**
     * Checks if both subject and course combo-boxes are selected,
     * and executes actions accordingly.
     */
    private void checkFieldsAndExecuteActions() {
        if (!subjectComboBox.getSelectionModel().isEmpty() && !CourseComboBox.getSelectionModel().isEmpty()) {
            setQuestionID();
            questionNumber.setVisible(true);
            questionID.setVisible(true);
        }
    }

    /**
     * Creates Subject combobox according to the data in the DB
     *
     * @param username The username of the user.
     */
    private void createSubjectCombo(String username) {
        MsgHandler<String> getSubject = new MsgHandler<>(TypeMsg.ImportSubjects, username);
        ClientUI.chat.accept(getSubject);

        List<Object> subjectObjectsList = ClientUI.chat.getSubjects();
        ObservableList<Subject> subjectsList = FXCollections.observableArrayList((List) subjectObjectsList);
        ObservableList<String> subjectNames = FXCollections.observableArrayList();

        for (Subject subject : subjectsList) {
            String subjectNameAndID = subject.getSubjectName() + " - " + subject.getSubjectID();
            subjectNames.add(subjectNameAndID);
        }
        subjectComboBox.setItems(subjectNames);
    }

    /**
     * Creates Course combobox according to the data in the DB
     *
     * @param username The username of the user.
     */
    private void createCourseCombo(String username) {
        MsgHandler<String> getCourses = new MsgHandler<>(TypeMsg.ImportCourses, username);
        ClientUI.chat.accept(getCourses);

        List<Object> courseObjectsList = ClientUI.chat.getCourses();
        coursesList = FXCollections.observableArrayList((List) courseObjectsList);
        ObservableList<String> courseNames = FXCollections.observableArrayList();

        for (Course course : coursesList) {
            String courseName = course.getCourseName();
            courseNames.add(courseName);
        }
        CourseComboBox.setItems(courseNames);
    }

    /**
     * Checks if all required fields are filled.
     *
     * @return true if all fields are filled, false otherwise.
     */
    public boolean areAllFieldsFilled() {
        return !CourseComboBox.getSelectionModel().isEmpty() &&
                !subjectComboBox.getSelectionModel().isEmpty() &&
                !questionNumber.getText().isEmpty() &&
                !questionTextField.getText().isEmpty() &&
                !answer1.getText().isEmpty() &&
                !answer2.getText().isEmpty() &&
                !answer3.getText().isEmpty() &&
                !answer4.getText().isEmpty() &&
                !CorrectAnswer.getText().isEmpty();
    }

    /**
     * Checks the validity of the data entered in the fields.
     * Displays error messages if the data is invalid.
     *
     * @return true if the data is valid, false otherwise.
     */
    private boolean checkValidData() {
        if (!(areAllFieldsFilled())) {
            ShowMessage.showErrorPopup("Not all fields are filled!");
            return false;
        }
        if (!(CorrectAnswer.getText().matches("[1-4]"))) {
            ShowMessage.showErrorPopup("Choose values from 1 to 4 in Correct Answer field");
            return false;
        }
        if (!(questionNumber.getText().matches("\\d+"))) { //checks if only digits
            ShowMessage.showErrorPopup("Question number contains NUMBERS only");
            return false;
        }
        return true;
    }

    /**
     * Saves the entered question data and adds a new question.
     * Displays a success message and navigates back to the main menu.
     *
     * @param event The event triggered by the save button click.
     */
    @FXML
    private void saveData(ActionEvent event) {
        String Subject = subjectComboBox.getSelectionModel().getSelectedItem();
        String Course = CourseComboBox.getSelectionModel().getSelectedItem();
        if (!checkValidData()) {
            return;
        }
        Question newQuestion = new Question(
                questionNumber.getText(),
                QuestionID,
                questionTextField.getText(),
                Client.user.getFullName(),
                Subject.substring(Subject.indexOf("-") + 2),
                Course,
                answer1.getText(),
                answer2.getText(),
                answer3.getText(),
                answer4.getText(),
                CorrectAnswer.getText()
        );
        MsgHandler<Question> addNewQuestion = new MsgHandler<>(TypeMsg.AddNewQuestion, newQuestion);
        ClientUI.chat.accept(addNewQuestion);
        ShowMessage.showInfoPopup("Added question successfully");
        ScreenManager.goToNewScreen(event, PathConstants.mainMenuPath);
    }

    /**
     * Sets the question ID based on the selected subject and course.
     */
    public void setQuestionID() {
        String Subject = subjectComboBox.getSelectionModel().getSelectedItem();
        MsgHandler<String> getQuestionTable = new MsgHandler(TypeMsg.GetAllQuestions, null);
        ClientUI.chat.accept(getQuestionTable);
        List<Question> questions = ((List) ClientUI.chat.getAllQuestions());
        String selectedCourse = getSelectedID(CourseComboBox, coursesList);
        newQuestionNumber = correctQuestionNumber(findFirstFreeIndex(questions));
        QuestionID = ((Subject.substring(Subject.indexOf("-") + 1)) + selectedCourse + newQuestionNumber);
        questionNumber.setText(newQuestionNumber);
        questionID.setText(QuestionID);
        questionID.setDisable(true);
        questionNumber.setDisable(true);
    }

    /**
     * Finds the first free index in the question table list.
     *
     * @param questionTableList The list of questions.
     * @return The first free index.
     */
    public int findFirstFreeIndex(List<Question> questionTableList) {
        if (questionTableList.isEmpty()) {
            return 1;
        }

        Set<Integer> indexSet = new HashSet<>();
        for (Question question : questionTableList) {
            indexSet.add(Integer.parseInt(question.getQuestionNumber()));
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
     * Retrieves the ID of the selected item from the combo box.
     *
     * @param comboBox    The combo box containing the items.
     * @param listToCheck The list to check for the item.
     * @return The ID of the selected item.
     */
    public String getSelectedID(ComboBox comboBox, ObservableList<Course> listToCheck) {
        String choice = comboBox.getSelectionModel().getSelectedItem().toString();
        for (Course item : listToCheck) {
            String course = item.getCourseName();
            if (choice.equals(course)) {
                return item.getCourseID();
            }
        }
        return null;
    }

    /**
     * Corrects the question number by adding leading zeros if necessary.
     *
     * @param newQuestionIndex The new question index.
     * @return The corrected question number.
     */
    public String correctQuestionNumber(int newQuestionIndex) {
        if (newQuestionIndex >= 0 && newQuestionIndex < 100) {
            if (newQuestionIndex < 10) {
                return "0" + newQuestionIndex;
            }
            return String.valueOf(newQuestionIndex);
        }
        throw new IllegalArgumentException("Question number is out of bounds: " + newQuestionIndex);
    }

    /**
     * Navigates back to the main menu.
     *
     * @param event The event triggered by the back button click.
     */
    public void BackToMenu(ActionEvent event) {
        ScreenManager.goToNewScreen(event, PathConstants.mainMenuPath);
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
     * Minimizes the application window.
     *
     * @param event The event triggered by the minimize button click.
     */
    @FXML
    public void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }

    /**
     * Closes the application.
     */
    @FXML
    public void closeClient() {
        ExitButton.closeClient();
    }

}
