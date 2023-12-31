package application.createNewTestScreen.notesScreen;

import Client.Client;
import Client.ClientUI;
import Client.ExitButton;
import application.loginWindowScreen.LoginWindowController;
import entity.IServerClientCommunication;
import entity.Test;
import entity.TestQuestion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import util.*;

import java.util.List;

/**
 * The NotesController class manages the notes screen for creating a new test.
 * It handles Lecturer's inputs and actions related to adding notes for the test.
 * The lecturer can leave notes for other lecturers and students
 */
public class NotesController {
    //public class NotesController {
    public StateManagement stateManagement = StateManagement.getInstance();
    @FXML
    private Text nameAuthor;
    @FXML
    private TextArea studentNote;
    @FXML
    private TextArea teacherNote;
    @FXML
    private AnchorPane header;
    private Test test;

    private TypeMsg serverResponseMsg;
    private InotesController notesController;
    private IServerClientCommunication iServerClientCommunication;


    public NotesController(InotesController INC) {
        this.notesController = INC;
    }

    public NotesController() {
        this.notesController = new testMethods();
    }


    public void initialize() {
        ScreenManager.dragAndDrop(header);

        nameAuthor.setText(Client.user.getName());

        if (stateManagement.getStudentComment() != null)
            studentNote.setText(stateManagement.studentComment);
        if (stateManagement.getTeacherComment() != null)
            teacherNote.setText(stateManagement.teacherComment);
    }

    public void setiServerClientCommunication(IServerClientCommunication iServerClientCommunication) {
        this.iServerClientCommunication = iServerClientCommunication;
    }

    /**
     * navigates back to the second stage of test creation
     *
     * @param event the event that triggered the method (clicking the back button)
     */
    @FXML
    void backToPickQuestions(ActionEvent event) {

        if (!studentNote.getText().isEmpty()) {
            stateManagement.setStudentComment(studentNote.getText());
        }
        if (!teacherNote.getText().isEmpty()) {
            stateManagement.setTeacherComment(teacherNote.getText());
        }
        ScreenManager.goToNewScreen(event, PathConstants.pickQuestionsPath);
    }

    /**
     * finalizes the test creation process:
     * - deletes a test with an identical id if one is found
     * - adds a test row to the tests table
     * - adds rows for each selected question to the testQuestion table
     * - calls the resetInstance method to reset all fields in the test creation screens
     *
     * @param event the event that triggered the method (clicking the submit button)
     */
    @FXML
    public String createTest(ActionEvent event) {
        if (stateManagement.semester.equals("") || stateManagement.year.equals("") || stateManagement.session.equals("")
                || stateManagement.testDuration.equals("") || stateManagement.testType == null) {
            return ("Go to page 1 and complete the data of test");
        }
        if (stateManagement.getTestQuestions().size() == 0) {
            return ("Select Questions for test from page 2");
        }
        if (stateManagement.getTotalRemainingPoints() > 0) {
            return ("Points for the questions do not add up to 100 in page 2");
        }
        notesController.checkNotes();
        notesController.deleteTestIfAlreadyExists();
        notesController.addTestToDB();
        notesController.addAllTestQuestionsToDB();

        setServerResponseMsg(TypeMsg.AddNewTestResponse);

        StateManagement.resetInstance();

        notesController.replaceScreen(event);

        return ("Test added successfully");
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Test getTest() {
        return test;
    }

    public TypeMsg getServerResponseMsg() {
        return serverResponseMsg;
    }

    public void setServerResponseMsg(TypeMsg serverResponseMsg) {
        this.serverResponseMsg = serverResponseMsg;
    }


    /**
     * Closes the application.
     */
    @FXML
    void closeClient() {
        ExitButton.closeClient();
    }

    /**
     * Minimizes the application window.
     *
     * @param event The event triggered by the minimize button click.
     */
    @FXML
    void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }

    class testMethods implements InotesController {
        /**
         * Checks if a test with the current testID already exists in the db
         * if it does, it gets deleted
         * Used for editing tests (Allows the lecturer to override an existing test's data)
         */
        //@Override
        public void deleteTestIfAlreadyExists() {
            MsgHandler getDbTestTable = new MsgHandler(TypeMsg.GetTestsBySubject, Client.user.getUserName());
            ClientUI.chat.accept(getDbTestTable);
            ObservableList<Test> dbTests = FXCollections.observableArrayList((List) ClientUI.chat.getTests());

            for (Test dbTest : dbTests) {
                if (stateManagement.getTestID().equals(dbTest.getId())) {
                    MsgHandler clearTestMsg = new MsgHandler(TypeMsg.DeleteTest, dbTest);
                    ClientUI.chat.accept(clearTestMsg);
                    break;
                }
            }
        }

        /**
         * adds all the test's questions to the DB
         */
        //@Override
        public void addAllTestQuestionsToDB() {
            ObservableList<TestQuestion> testQuestions = stateManagement.getTestQuestions();

            for (int i = 0; i < stateManagement.getTestQuestions().size(); i++) {
                MsgHandler addNewTestQuestion = new MsgHandler(TypeMsg.AddNewTestQuestion, testQuestions.get(i));
                ClientUI.chat.accept(addNewTestQuestion);
            }
        }
        /**
         * adds a test to the DB
         */
        //@Override
        public void addTestToDB() {

            Test newTest = new Test(
                    stateManagement.getTestNum(),
                    stateManagement.getTestID(),
                    Client.user.getFullName(),
                    stateManagement.getTestDuration(),
                    stateManagement.getCourse().getCourseName(),
                    stateManagement.getTeacherComment(),
                    stateManagement.getTestType(),
                    stateManagement.getStudentComment(),
                    stateManagement.getCourse().getSubjectName(),
                    stateManagement.getYear(),
                    stateManagement.getSession(),
                    stateManagement.getSemester(),
                    stateManagement.getSubjectID()
            );

            MsgHandler addNewTest = new MsgHandler(TypeMsg.AddNewTest, newTest);
            ClientUI.chat.accept(addNewTest);
        }

        public void replaceScreen(ActionEvent event) {
            ScreenManager.goToNewScreen(event, PathConstants.manageTestsPath);
        }
        @Override
        public void checkNotes() {
            if (!studentNote.getText().isEmpty()) {
                stateManagement.setStudentComment(studentNote.getText());
            }
            if (!teacherNote.getText().isEmpty()) {
                stateManagement.setTeacherComment(teacherNote.getText());
            }

        }
    }
}
