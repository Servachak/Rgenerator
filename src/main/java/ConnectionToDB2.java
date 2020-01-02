import java.sql.DriverManager;
import java.sql.ResultSetMetaData;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ibm.db2.jcc.am.Connection;
import com.ibm.db2.jcc.am.DatabaseMetaData;
import com.ibm.db2.jcc.am.ResultSet;
import com.rgenerator.excel.WriteDataToExcel;

public class ConnectionToDB2 {

	final static String URL = "jdbc:db2://10.254.187.216:50000/ACCTDB";
	final static String USER = "root";
	final static String PASSWORD = "15Asennu51";

	public static void main(String[] args) {

		Connection connection;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet colums = null;
		WriteDataToExcel dataToExcel = new WriteDataToExcel();

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

				// Get meta data
//				DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
//				colums = (ResultSet) metaData.getColumns(null, null, "ACCT", null);

				// Find all column in DB
//				ResultSetMetaData resultSetMetaData = colums.getMetaData();
//				int columCount = resultSetMetaData.getColumnCount();
//				System.out.println(columCount);

				// Create query
				String query = "SELECT * FROM ACCT.ACCOUNT_RESULT_DETAILS ";
//						+ "WHERE ACC_ID IN (SELECT ACC_ID FROM ACCT.ACCOUNT a WHERE ACC_NUMBER LIKE '%LT542140030002190972%')\r\n"
//						+ "  AND CALCU_TYPE = 'INT'\r\n" + "  ORDER BY CALCU_REG_TIMESTAMP DESC";

				// Execute a query and generate a ResultSet instance
				rs = (ResultSet) stmt.executeQuery(query);
				System.out.println("**** Created JDBC ResultSet object");

				// Get column counter
				int columCount = rs.getMetaData().getColumnCount();
				
				List<String> columnNames = new ArrayList<String>();
				for (int i = 1; i <= columCount; i++) {
					String columnName =  rs.getMetaData().getColumnName(i);
					columnNames.add(columnName);
				}

				System.out.println(columnNames.toString());

//				while (rs.next()) {
//
//					String ACC_ID = rs.getString("ACC_ID");
//					String FUNCTION_ID = rs.getString("FUNCTION_ID");
//
//					System.out.printf("%s  %s\n", ACC_ID, FUNCTION_ID);
//
//				}
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
