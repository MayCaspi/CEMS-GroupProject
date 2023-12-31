import javafx.application.Application;
import javafx.stage.Stage;
import util.PathConstants;
import util.ScreenManager;

import java.io.IOException;

public class ServerGUI extends Application {
    static CemsServer Cems;
    public static MysqlConnection mysqlConnection;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * disconnects from the server
     */
    public static void disconnect() {
        if (Cems == null)
            System.out.println("Server Already Stopped");
        else {
            try {
                Cems.serverStopped();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Server has stopped listening for connections.");
            System.out.println("Server Disconnected");
        }
    }

    /**
     * connects to the server
     *
     * @param IP       the IP address
     * @param port     the port number
     * @param DBName   the address of the database
     * @param username the DB username
     * @param password the DB password
     */
    public static void runServer(String IP, String port, String DBName, String username, String password) {
        int Port; // Port to listen on

        try {
            Port = Integer.parseInt(port); // Set port to 5555

        } catch (Throwable t) {
            System.out.println("ERROR - Could not connect!");
            return;
        }
        Cems = new CemsServer(Port, password);
        try {
            Cems.listen(); // Start listening for connections

        } catch (IOException e) {
            System.out.println("IO exception");
        }
    }

    /**
    Start gui application
     */
    @Override
    public void start(Stage primaryStage) {
        ScreenManager.showStage(PathConstants.serverPath, PathConstants.serverIcon);
    }
}