import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import util.ConnectToClients;
import util.MinimizeButton;
import util.ScreenManager;
import util.TableManager;

import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;


public class ServerController {

    @FXML
    private AnchorPane header;

    @FXML
    private TextArea Console;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnDisconnect;

    @FXML
    private TableView<ConnectToClients> clientsTable;

    @FXML
    private TextField txtDBname;

    @FXML
    private TextField txtIP;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPort;

    @FXML
    private TextField txtUsername;

    /**
     * updates the text in the console text area in real time
     * based on client-server related events or errors
     */
    @FXML
    void consoleStreamIntoGUI() {
        PrintStream switchConsole = new PrintStream(new Console(Console));
        System.setOut(switchConsole);
        System.setErr(switchConsole);
    }

    /**
     * gets the server's IP address
     *
     * @return a string of the IP
     */
    public String getIp() {
        String ip = null;
        try {
            ip = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

            e.printStackTrace();
        }
        return ip;
    }

    /**
     * calls the server's runServer method
     * (used by the connect button in the server GUI)
     *
     */
    @FXML
    void Connection() {

        ServerGUI.runServer(txtIP.getText(), txtPort.getText(), txtDBname.getText(), txtUsername.getText(), txtPassword.getText());
        btnConnect.setDisable(true);
        btnDisconnect.setDisable(false);
        disableDataInput(true);

    }

    /**
     * calls the server's disconnect method
     * (used by the disconnect button in the server GUI)
     *
     */
    @FXML
    void Disconnection() {
        ServerGUI.disconnect();
        btnDisconnect.setDisable(true);
        btnConnect.setDisable(false);
        disableDataInput(false);
    }

    /**
     * Turns off the option to pass data in the TextArea
     */
    void disableDataInput(boolean Condition) {
        txtIP.setDisable(Condition);
        txtPort.setDisable(Condition);
        txtDBname.setDisable(Condition);
        txtUsername.setDisable(Condition);
        txtPassword.setDisable(Condition);
    }
    /**
     * Initializes the controller and sets up the initial state of the GUI.
     * This method is automatically called when the FXML file is loaded.
     */

    @FXML
    public void initialize() {
        ScreenManager.dragAndDrop(header);

        ObservableList<String> columnList = FXCollections.observableArrayList();
        columnList.addAll("IP", "Host", "Status");
        double[] multipliers = {0.33, 0.33, 0.33};

        TableManager.createTable(clientsTable, columnList);
        TableManager.resizeColumns(clientsTable, multipliers);

        consoleStreamIntoGUI();

        this.clientsTable.setItems(CemsServer.getClientList());
        //Default data input for server
        this.txtIP.setText(getIp());
        this.txtPort.setText("5555");
        this.txtDBname.setText("jdbc:mysql://localhost/Cems?serverTimezone=IST");
        this.txtUsername.setText("root");
        this.txtPassword.setText("");
        this.btnDisconnect.setDisable(true);
    }
    /**
     * Closes the server application.
     * This method is called when the close button is clicked.
     */
    public void Close() { //close button
        ServerGUI.disconnect();
        System.exit(0);
    }
    /**
     * Minimizes the window.
     * This method is called when the minimize button is clicked.
     *
     * @param event the ActionEvent triggered by the minimize button.
     */
    public void minimizeWindow(ActionEvent event) {
        MinimizeButton.minimizeWindow(event);
    }
}
