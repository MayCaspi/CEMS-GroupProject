package server;

import entity.Question;

import java.sql.*;
import java.util.ArrayList;

/**
 * the class contains methods which connect to the CEMS database
 * and perform various queries on the data
 */
public class MysqlConnection {
    static Connection conn;
    static String url = "jdbc:mysql://localhost/cems?serverTimezone=IST&allowPublicKeyRetrieval=true&useSSL=false"; // replace
                                                                                                                    // "mydatabase"
    static String username = "root"; // replace with your username


    /**
     * This hook method sets up a connection between the Server and the Data base we
     * connect to the server by passing the DB address, DB username and password
     */
    public static void connectToDb(String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeed");
        } catch (Exception ex) {
            /* handle the error */
            System.out.println("Driver definition failed");
        }
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("SQL connection succeed");
        } catch (SQLException ex) {/* handle any errors */
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            System.out.println("\n");
        }
    }

	/**
	 * executes an UPDATE query on the CEMS database
	 * @param q the given update query
	 */
	public static void update(String q) {
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(q);
			stmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * gets an array list of the questions from the CEMS database
	 * @param q the given SELECT query
	 * @return ArrayList of the questions
	 */
	public static ArrayList<Question> getQuestionsTable(String q) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		ArrayList<Question> list = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(q);
			while (rs.next()) {

				int id = rs.getInt("id"); // assuming your table has a column named "id" with type INT
				String subject = rs.getString("subject");
				String course_name = rs.getString("course_name");
				String question_text = rs.getString("question_text");
				String question_number = rs.getString("question_number");
				String lecturer = rs.getString("lecturer");

				// public Question(String questionNumber,String questionId,String questionText,
				// String questionWrittenBy) {
				Question qeustion = new Question(question_number, Integer.toString(id), question_text, lecturer, subject,
						course_name);
				list.add(qeustion);
//                list.add(Integer.toString(id));
//                String course_name = rs.getString("course_name");
//                list.add(course_name);
////
////				String name = rs.getString("name"); // assuming your table has a column named "name" with type VARCHAR
////				int age = rs.getInt("age"); // assuming your table has a column named "age" with type INT
////
////				System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
//                System.out.println(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String authenticateUser(String username, String password) {
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT role FROM users WHERE username = ? AND password = ?");
			statement.setString(1, username);
			statement.setString(2, password);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString("role");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * this method closes the connection to the DB and the server
	 */
	public static void closeConnection() {
		if (conn == null)
			System.out.println("Server Connection has been closed");
		else {
			try {
				conn.close();
			} catch (SQLException e) {
				// ignore
			}
		}
	}
}
	
