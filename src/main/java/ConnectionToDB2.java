import java.sql.DriverManager;
import java.sql.ResultSetMetaData;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

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
		WriteDataToExcel writeDataToExcel;
		Cell cell;
		Row row;

		LocalDate date = LocalDate.now();
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

				// Create query
				String query = "SELECT * FROM ACCT.ACCOUNT_RESULT_DETAILS "
						+ "WHERE ACC_ID IN (SELECT ACC_ID FROM ACCT.ACCOUNT a WHERE ACC_NUMBER LIKE '%LT542140030002190972%')\r\n"
						+ "  AND CALCU_TYPE = 'INT'\r\n" + "  ORDER BY CALCU_REG_TIMESTAMP DESC";

				// Execute a query and generate a ResultSet instance
				rs = (ResultSet) stmt.executeQuery(query);
				System.out.println("**** Created JDBC ResultSet object");

				// Get columns counter
				ResultSetMetaData rsMetadata = rs.getMetaData();
				int columCount = rsMetadata.getColumnCount();

				// Get columns name
				List<String> columnNames = new ArrayList<String>();
				for (int i = 1; i <= columCount; i++) {
					String columnName = rsMetadata.getColumnName(i);
					columnNames.add(columnName);
				}

				// create Excel file
				Workbook workbook = new HSSFWorkbook();

				// create new Sheet
				String sheetName = rsMetadata.getColumnName(1);
				Sheet sheet = workbook.createSheet(sheetName);

				// write data to Excel
				writeDataToExcel = new WriteDataToExcel();
				String fileName = rsMetadata.getColumnLabel(1) + " " + date;

				int currentRow = 0;

				while (rs.next()) {

					for (int i = 1; i < 2; i++) {
						row = sheet.createRow(currentRow);
						if (currentRow == 0) {

							for (int k = 1; k <= columCount; k++) {
								cell = row.createCell(k - 1);
								cell.setCellValue(rsMetadata.getColumnName(k));

							}
							currentRow++;
						}
						row = sheet.createRow(currentRow);
						for (int j = 1; j <= columCount; j++) {

							// get data from DB
							System.out.print(rs.getString(j) + " ");

							cell = row.createCell(j - 1);
							cell.setCellValue(rs.getString(j));
						}
						System.out.print(" ");
					}
					currentRow++;
				}
				System.out.println();
				for (int i = 0; i < columCount; i++) {
					sheet.autoSizeColumn(i);
				}
				writeDataToExcel.createExcelFile(fileName, sheet, workbook);
			}

//			}
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

		} catch (

		ClassNotFoundException e) {
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
