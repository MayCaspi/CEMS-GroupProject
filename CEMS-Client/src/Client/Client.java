package Client;

import application.enterTest.ManualTestController;
import application.enterTest.QuestionsComputerizedTestAnswerController;
import application.mainMenuScreen.MainMenuStudentController;
import application.manageTestsScreen.ViewActiveTestController;
import application.viewReportsScreen.ViewReportsController;
import application.viewReportsScreen.ViewSpecificReportHeadOfDepart.openRepoGraphs.OpenReportByCourseController;
import application.viewReportsScreen.ViewSpecificReportHeadOfDepart.openRepoGraphs.OpenReportByLecturerController;
import application.viewReportsScreen.ViewSpecificReportHeadOfDepart.openRepoGraphs.OpenReportByStudentController;
import application.viewReportsScreen.ViewSpecificReportHeadOfDepart.ShowReportByLecturerController;
import client.AbstractClient;
import entity.*;
import javafx.application.Platform;
import util.ChatIF;
import util.MsgHandler;
import util.showError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//test
public class Client extends AbstractClient {
    public List<Object> studentTests;
    private final ChatIF clientUI;
    private boolean waitResponse = false;
    public static MsgHandler<Object> messageFromServer;
    public List<Object> questions;
    public List<Object> subjects;
    public List<Object> courses;
    public List<Object> allQuestions;
    public List<Object> tests;
    public List<Object> testQuestions;
    public List<Object> activeTests;
    public List<Object> infoAboutTest;
    public Object singleQuestion;
    public Object singleTest;
    public Object singleSubject;
    public Object UserAndCourse;
    public List<Object> testsForApproval;
    public Object NumOfRegistered;
    public Object NumOfFinished;
    public Object NumOfAttended;

    public static User user;

    public List<Object> requests;
    public static final OpenReportByStudentController HODPstudentReportcontroller = new OpenReportByStudentController();
    public static final ShowReportByLecturerController HODPshowReportByLecturerController = new ShowReportByLecturerController();
    public static final OpenReportByLecturerController HODPopenReporLecturerController = new OpenReportByLecturerController();
    public static final OpenReportByCourseController HODPopenReportCourseController = new OpenReportByCourseController();
    public static final ViewReportsController LectureReportsController = new ViewReportsController();
    public static final QuestionsComputerizedTestAnswerController StudentInTest = new QuestionsComputerizedTestAnswerController();
    public static final ViewActiveTestController activeTestController = new ViewActiveTestController();
    public static final MainMenuStudentController menuStudentController = new MainMenuStudentController();
    public static final ManualTestController manualTest = new ManualTestController();


    //constructor
    public Client(String host, int port, ChatIF clientUI) {
        super(host, port);
        this.clientUI = clientUI;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void handleMessageFromServer(Object msg) {
        waitResponse = false;
        messageFromServer = (MsgHandler<Object>) msg;
        System.out.println("handleMessageFromServer ");
        System.out.println(messageFromServer.getType().toString());
        switch (messageFromServer.getType()) {
            case Connected:
            case LecturerClickedLockTestResponse:
            case ImportedTestsAverage:
            case StudentsTestIsApprovedResponse:
            case IsLoggedValueChanged:
            case DetectedCheatingResponse:
            case DeleteRequestResponse:
            case DeactivatingTestResponse:
            case AfterTestRowCompleted:
            case AddNewAfterTestInfoResponse:
            case AddNewActiveTestResponse:
            case UpdateTheApprovalOfLecturerResponse:
            case ExtraTimeRequested:
            case changeTestDurationAnswer:
            case StudentsFinishedTestIncreased:
            case TotalStudentsInTestIncreased:
            case TestOfStudentSaved:
            case StudentAnswerAdded:
            case UpdateRemainingTimeResponse:
            case AddNewTestQuestionsResponse:
            case AddNewTestResponse:
            case QuestionAddedSuccessfuly:
            case QuestionUpdated:
            case QuestionDeleted:
                break;

            case Disconnected:
                System.exit(0);
                break;
            case ServerStopped:
                Platform.runLater(() -> {
                    showError.showErrorPopup("Server disconnected\nBye-bye");
                    System.exit(-1);
                });

                break;
            case allQuestionImported:
                this.allQuestions = (List<Object>) messageFromServer.getMsg();
                break;

            case LoginResponse:
                user = (User) messageFromServer.getMsg();
                break;

            case QuestionsBySubjectImported:
            case GetQuestionsByLecturerResponse:
                this.questions = (List<Object>) messageFromServer.getMsg();
                break;

            case CoursesimportSuccess:

            case ImportedStudentCourses:
                this.courses = (List<Object>) messageFromServer.getMsg();
                break;

            case SubjectsimportSuccess:
                this.subjects = (List<Object>) messageFromServer.getMsg();
                break;

            case CourseTableResponse:
                courses = (ArrayList<Object>) messageFromServer.getMsg();
                this.courses = (List<Object>) messageFromServer.getMsg();
                break;
            case GetAllTestsTableResponse:
            case GetTestsByLecturerResponse:
                this.tests = (List<Object>) messageFromServer.getMsg();
                break;
            case GetTestsBySubjectResponse:
                this.tests = (List<Object>) messageFromServer.getMsg();
                System.out.println(tests);
                break;

            case RequestImportedSuccessfully:
                requests = (ArrayList<Object>) messageFromServer.getMsg();
                this.requests = (List<Object>) messageFromServer.getMsg();
                break;
            case RequestIsApproved://check delete
            case RequestIsDeclined:
                break;
            case RequestIsDeclinedToLecturer://check delete
            case TestDurationDeclinedPopLecturer:
                activeTestController.showRequestDeclinedPopUp();
              break;
            case StudentReportImported:
                HODPstudentReportcontroller.reportCalc((ArrayList<Object>) messageFromServer.getMsg());
                break;
            case UserImported:
                HODPshowReportByLecturerController.setLecturerCombo((ArrayList<Object>) messageFromServer.getMsg());
                break;

            case ImportedTestsByLecturer:
                HODPopenReporLecturerController.reportCalc((ArrayList<Object>) messageFromServer.getMsg());
                break;

            case ImportedTestsByCourse:
                HODPopenReportCourseController.reportCalc((ArrayList<Object>) messageFromServer.getMsg());
                break;

            case ImportedTestsByLecturerForLecturerReport:
                LectureReportsController.setTestsTable((ArrayList<Object>) messageFromServer.getMsg());
                break;

            case GetTestQuestionsResponse:
                testQuestions = (List<Object>) messageFromServer.getMsg();
                break;

            case GetActiveTestsResponse:

            case GetActiveTestsByLecturerResponse:
                this.activeTests = (List<Object>) messageFromServer.getMsg();
                break;
            case importedQuestionAndAnswerFromTest:
                this.singleQuestion = messageFromServer.getMsg();
                break;
            case ImportedTestByID:
            case ImportedTestAverage:
                this.singleTest = messageFromServer.getMsg();
                break;
            case StudentVerified:
                this.UserAndCourse = messageFromServer.getMsg();
                break;
            case TestDurationChangedComputerized:
                StudentInTest.showNotificationAndChangeDuration((Integer) messageFromServer.getMsg());
                break;
            case TestDurationChangedManual:
                manualTest.showNotificationAndChangeDuration((Integer) messageFromServer.getMsg());

                break;
            case TestDurationApprovedPopLecturer:
                activeTestController.showRequestApprovedPopUp();
                break;
            case ImportedSubjectIDFromName:
                this.singleSubject = messageFromServer.getMsg();
                break;
            case GetTestForApprovalResponse:
                testsForApproval = (ArrayList<Object>) messageFromServer.getMsg();
                this.testsForApproval = (List<Object>) messageFromServer.getMsg();
                break;
            case ImportedNumberOfAttendedCounter:
                this.NumOfAttended = messageFromServer.getMsg();
                break;
            case ImportedRegisteredStudents:
                this.NumOfRegistered = messageFromServer.getMsg();
                break;
            case ImportedNumberOfFinished:
                this.NumOfFinished = messageFromServer.getMsg();
                break;
            case PopupTestApprove:
                menuStudentController.showTestApprovedPopUp();
                break;

            case ImportedStudentTests:
                this.studentTests = (List<Object>) messageFromServer.getMsg();
                break;

            case TestIsForcedLockedManual:
                manualTest.lockTest();
                break;
            case TestIsForcedLockedComputerized:
                StudentInTest.lockTest();
                break;
            case ImportedAfterTestInfo:
                this.infoAboutTest = (List<Object>) messageFromServer.getMsg();
                break;
        }
    }

    public void handleMessageFromClientUI(Object message) {
        try {
            openConnection();
            waitResponse = true;
            sendToServer(message);
            // wait for response
            while (waitResponse) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            clientUI.display("Could not send message to server.  Terminating client.");
            quit();
        }
    }

    public void quit() {
        try {
            closeConnection();
        } catch (IOException ignored) {
        }
        System.exit(0);
    }


}
