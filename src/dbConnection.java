

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Class.forName("com.mysql.jdbc.Driver");
//Connection con = DriverManager.getConnection(
		//jdbc:mysql://alon.cd7u1ow7e0k3.eu-central-1.rds.amazonaws.com","admin","a1234567");
public class dbConnection {
	//***Old Amazon RDS ***
//	private String dbUrl = "alon.cd7u1ow7e0k3.eu-central-1.rds.amazonaws.com";
//	private String dbUsername = "admin";
//	private String dbPassword = "a1234567";
	private String dbUrl = "localhost";
	private String dbUsername = "root";
	private String dbPassword = "root";
	private String driver = "com.mysql.cj.jdbc.Driver";
	private Connection conn = null;
	private static dbConnection instance = null;

	/* Private constructor to prevent more than one instance 
	 * to create instance of dbConnection use instance method */
	private dbConnection() throws ClassNotFoundException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("jdbc");
		}
		
		String url = "jdbc:mysql://" + dbUrl+"";
		String username = dbUsername;
		String password = dbPassword;

		System.out.println("Connecting database...");
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
		} catch (SQLException e) {
			System.out.println("Database dead!");
			System.out.println(e);

		}

	}
	
	/* this method return an instance of dbConnection 
	 * and making sure there is only one connection instance of this class */
	public static dbConnection instance() throws ClassNotFoundException {
		if (instance == null) 
			instance = new dbConnection();
		return instance;
	}
	
	/* return conn */
	public Connection getConn() {
		return this.conn;
	}
}
