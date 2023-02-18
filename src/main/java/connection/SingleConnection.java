package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {

	private static String url = "jdbc:firebirdsql:localhost/3050:D:\\.arquivobanco";
	private static String user = "userdb";
	private static String pass = "senhadb";
	private static Connection connection = null;

	static {
		Connect();
	}

	public SingleConnection() {
		Connect();
	}

	private static void Connect() {
		try {
			if (connection == null) {
				Class.forName("org.firebirdsql.jdbc.FBDriver");
				connection = DriverManager.getConnection(url, user, pass);
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}
