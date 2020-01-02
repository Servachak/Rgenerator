import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.ibm.db2.jcc.am.Connection;
import com.ibm.db2.jcc.am.ResultSet;

public class ConnectionToDB2 {

	final static String URL = "jdbc:db2://10.254.187.216:50000/ACCTDB";
	final static String USER = "root";
	final static String PASSWORD = "15Asennu51";

	public static void main(String[] args) {

		Connection connection;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			// loading DB2 driver
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			System.out.println("**** Loaded the JDBC driver");

			// create connection
			connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);

			// Commit changes manually
			connection.setAutoCommit(false);
			System.out.println("**** Created a JDBC connection to the data source");

			if (!connection.isClosed()) {
				System.out.println("Connected");

				// Create the Statement
				stmt = connection.createStatement();
				System.out.println("**** Created JDBC Statement object");

				// Execute a query and generate a ResultSet instance
				rs = (ResultSet) stmt.executeQuery("SELECT * FROM ACCT.ACCOUNT_RESULT_DETAILS");
				System.out.println("**** Created JDBC ResultSet object");

				while (rs.next()) {
					String result = rs.getString(1);
					System.out.println(result);
				}
			}
			System.out.println("**** Fetched all rows from JDBC ResultSet");

			// Close the ResultSet
			rs.close();
			System.out.println("**** Closed JDBC ResultSet");

			// Close the Statement
			stmt.close();
			System.out.println("**** Closed JDBC Statement");

			// Connection must be on a unit-of-work boundary to allow close
			connection.commit();
			System.out.println("**** Transaction committed");

			// Connection must be on a unit-of-work boundary to allow close
			connection.close();

			if (connection.isClosed()) {
				System.out.println("Connection is closed");
			}

		} catch (ClassNotFoundException e) {
			System.err.println("Could not load JDBC driver");
			System.out.println("Exception: " + e);
			e.printStackTrace();

		} catch (SQLException ex) {
			System.err.println("SQLException information");
			while (ex != null) {
				System.err.println("Error msg: " + ex.getMessage());
				System.err.println("SQLSTATE: " + ex.getSQLState());
				System.err.println("Error code: " + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException(); // For drivers that support chained exceptions
			}

		}

	}
}
