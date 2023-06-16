package util;

public enum TypeMsg {

    Disconnected("Disconnected"),
    Connected("Disconnected"),
    GetQuestionsBySubject("User asked for table questions of specific Subject"),
    GetQuestionsBySubjectResponse("Server imported all questions of specific subject"),
    GetAllQuestions("User asked for all questions"),
    GetAllQuestionsResponse("Server imported all questions"),
    TryLogin("User tried to login"),
    LoginResponse("User has been logged in"),
    ImportSubjects("import Subjects list for user"),
    ImportSubjectsResponse("Server imported subjects successfully"),
    ImportCourses("Import courses list for user"),
    ImportCoursesResponse("Server imported courses successfully"),
    EditQuestion("User asked to edit question"),
    EditQuestionResponse("Server updated the question"),
    AddNewQuestion("User asked to add a question"),
    AddNewQuestionResponse("Server added the question"),
    DeleteQuestion("User asked to delete a question"),
    DeleteQuestionResponse("Server deleted the question"),
    GetCourseTable("User asked for a list of courses"),
    CourseTableResponse("Server returned the course list"),
    GetAllTestsTable("User requested the table of every test in the DB"),
    GetAllTestsTableResponse("Server returned the table of every test in the DB"),
    GetTestsBySubject("User requested the tests table filtered by subject"),
    GetTestsBySubjectResponse("Server returned the table of tests filtered by subject"),
    AddNewTestQuestion("Lecturer asked to save the questions of test"),
    AddNewTestQuestionsResponse("Server saved the test questions"),
    DeleteRequest("Deleting request for time change"),
    DeleteRequestResponse("Server deleted the time change request"),
    AddNewTest("Lecturer asked to save test"),
    AddNewTestResponse("Server saved the test"),
    GetRequestsBySubject("Head Of Department wants to import table of requests"),
    GetRequestsBySubjectResponse("Server returned the request table"),
    ApproveRequestByHeadOfDepartment ("Head Of Department want to approve a requests"),
    RequestIsApproved ("The request has been approved"),
    DeclineRequestByHeadOfDepartment ("Head Of Department want to decline a requests"),
    RequestIsDeclined ("Time Request Declined"),
    RequestIsDeclinedToLecturer ("Time Request Declined"),
    GetStudentReport ("Head of department want to get a report of a student"),
    StudentReportImported ("Student's report was imported"),
    GetUser ("Get list of users"),
    UserImported ("Imported list of users"),
    GetTestsByLecutrer ("Get list tests made by lecutrer"),
    ImportedTestsByLecturer ("Imported list of test made by lecturer"),
    GetTestsByCourse ("Get list tests made by course"),
    ImportedTestsByCourse ("Imported list of tests by course"),
    GetTestsByLecturerForLecturerReport("Imported list of tests by lecturer"),
    ImportedTestsByLecturerForLecturerReport ("Imported list of test made by lecturer"),
    DeleteTest ("User asked to delete a test"),
    DeleteTestResponse ("Server deleted the test"),
    GetTestQuestions ("User asked for the questions of the chosen test"),
    GetTestQuestionsResponse ("Server returned the list of test questions"),
    GetActiveTests ("User asked for the active tests table"),
    GetActiveTestsResponse ("Server returned the active tests table"),
    GetCorrectAnswer ("Get correct Answer From the data base"),
    importedCorrectAnswer ("Imported correct answer"),
    UpdateRemainingTime ("User wants to update the test's remaining time"),
    UpdateRemainingTimeResponse ("Server updated the test's remaining time"),
    GetRemainingTime ("User wants to get the test's remaining time"),
    GetRemainingTimeResponse ("Server returned the test's remaining time"),
    getQuestionAndAnswerFromTest ("Get Question and it's answers"),
    importedQuestionAndAnswerFromTest ("Imported Question and it's answers"),
    GetTestQuestionsById ("User asked for the questions of the chosen test"),
    AddStudentAnswer ("Student submitted an answer in the test"),
    StudentAnswerAdded ("Student final answer is saved"),
    GetTestByID ("Get Test information by it's id"),
    ImportedTestByID ("Test information imported"),
    AddNewTestOfStudent ("Student finished his test"),
    TestOfStudentSaved ("Test of student is saved"),
    CheckStudentRegisteredCourse ("Check if student is in this course"),
    StudentVerified ("Student id's is verified"),
    IcreaseStudentsEnteringTest ("Student is taking a test"),
    TotalStudentsInTestIncreased ("Student is taking a test"),
    IcreaseStudentsFinishedTest ("Student finished test"),
    StudentsFinishedTestIncreased ("Student is taking a test"),
    changeTestDuration ("Head Of Department want to change test duration"),
    changeTestDurationAnswer ("Change of test duration succeeded"),
    TestDurationChanged ("test's duration was changed message sent"),
    TestDurationChangedComputerized ("test's duration was changed"),
    TestDurationChangedManual ("test's duration was changed"),
    GetSubjectNameToID("get subject id"),
    ImportedSubjectIDFromName("subject ID imported"),
    RequestExtraTime ("Lecturer requested extra time in test"),
    ExtraTimeRequested ("Lecturer requested extra time in test"),
    TestDurationApprovedPopLecturer ("Approved time change popup"),
    TestDurationDeclinedPopLecturer ("Declined time change popup"),
    GetTestForApproval ("User Asked All Test To Do Approve"),
    GetTestForApprovalResponse ("Server returned the list of tests needed approval"),
    UpdateTheApprovalOfLecturer("User asked to update the approve"),
    UpdateTheApprovalOfLecturerResponse("Server update your approval"),
    AddNewActiveTest ("Lecturer wants to activate a test"),
    AddNewActiveTestResponse ("Server activated the test"),
    AddNewAfterTestInfo ("Lecturer wants to add initial information a test"),
    AddNewAfterTestInfoResponse ("Server added initial test information"),
    NumberOfAttendedCounter ("Request to get number of students who entered a test"),
    ImportedNumberOfAttendedCounter ("imported number of students who entered a test"),
    CountRegisteredStudents ("Request to get number of students who are assigned to a test (the course)"),
    ImportedRegisteredStudents ("Imported number of students who are registered to the test"),
    FinishAfterTestInfo ("Test is over, need to save afterTestInfo"),
    AfterTestRowCompleted ("After test info saved"),
    DeactivateTest("Test is over, remove from activetest"),
    DeactivatingTestResponse("Test is no longer active"),
    CountNumberOfFinished ("Count number of students who submitted the test"),
    ImportedNumberOfFinished ("Imported number of students who submitted the test"),
    DetectedCheating ("User Asked to update student and test have cheating"),
    DetectedCheatingResponse("the test have cheating updating"),
    GetActiveTestsByLecturer("User asked for the active tests table filtered by user's name"),
    GetActiveTestsByLecturerResponse("Server returned the active tests table filtered by user's name"),
    ChangeIsLoggedValue("Change is Logged in value"),
    IsLoggedValueChanged("Logged in value changed"),
    StudentsTestIsApproved("Lecturer approved a test of a student"),
    StudentsTestIsApprovedToAllClients("find Students to Send SMS about approved test"),
    PopupTestApprove("Student received an sms of approved test"),
    StudentsTestIsApprovedResponse("Test Approved"),
    LecturerClickedLockTest("Lecturer wants to lock a test"),
    LecturerClickedLockTestResponse("Test is locked"),
    LockTestForStudentByLecturer("Test of student has stopped by lecturer"),
    TestIsForcedLocked("Locking test succeeded"),
    TestIsForcedLockedComputerized("Locking test succeeded"),
    TestIsForcedLockedManual("Locking test succeeded"),
    GetQuestionsByLecturer("User asked for the list of questions written by him"),
    GetQuestionsByLecturerResponse("Server returned a list of questions written by the user"),
    GetTestsByLecturer("User asked for the list of tests written by him"),
    GetTestsByLecturerResponse("Server returned a list of tests written by the user"),
    GetStudentsTests("Student requested his tests"),
    ImportedStudentTests("Server returned student's tests"),
    GetStudentCourses("Student requested his courses"),
    ImportedStudentCourses("Server returned student's courses"),
    SetTestAverage("Request test's average"),
    ImportedTestsAverage("Server returned test's average"),
    GetTestAverage("Get test's average"),
    ImportedTestAverage("Server returned test's average"),
    ServerStopped("Server stopped listening"),
    TerminateClient("Terminating client"),
    TestDurationChangedComputerizedSendToAll("Time of Computerized test increased"),
    TestDurationChangedManualSendToAll("Time of Manual test increased"),
    getTestAfterTestInfo("get after test info"),
    ImportedAfterTestInfo("Imported after test info");

    private final String message;

    TypeMsg(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}