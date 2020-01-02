import java.sql.DriverManager;
import java.sql.Statement;

import com.ibm.db2.jcc.am.Connection;
import com.ibm.db2.jcc.am.ResultSet;

public class Db2test {
//	:50000/ACCTDB
	final static String URL = "jdbc:as400://10.254.187.216:50000/ACCTDB";
	final static String USER = "root";
	final static String PASSWORD = "15Asennu51";

	public static void main(String[] args) {

		try {
			System.out.println("traing to connect....");
			// loading DB2 driver
			String driver = "com.ibm.as400.access.AS400JDBCDriver";
			
			Class.forName(driver);
			// create connection
			Connection connection = (Connection) DriverManager.getConnection(URL, USER,"");
			System.out.println("Created a JDBC connection to the data source");
			
			if (!connection.isClosed()) {
				System.out.println("Connected");
				
				// Create the Statement
				Statement stmt = connection.createStatement();
				System.out.println("**** Created JDBC Statement object");
				
				ResultSet resultSet = (ResultSet) stmt.executeQuery("SELECT * FROM ACCOUNT_RESULT");
				while (resultSet.next()) {
					System.out.println(resultSet.getString(1));
					
				}
			}
			connection.close();
			if(connection.isClosed()) {
				System.out.println("Connection is closed");
			} 

		} catch (Exception e) {
			e.getMessage();
		}

	}

}
