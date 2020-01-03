package com.rgenerator.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WriteDataToExcel implements WriteDataToExcelInterface {

	Sheet sheet;
	Workbook workbook;

	public void createExcelFile(String fileName, Sheet sheet, Workbook workbook) {

		this.workbook = workbook;
		this.sheet = sheet;

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".xls");

			workbook.write(fileOutputStream);
			System.out.println("Writing to Excel file");

			fileOutputStream.close();
			workbook.close();
		} catch (FileNotFoundException e) {

			System.out.println("File is not created");
			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public Sheet createSheet(String sheetName, Workbook workbook) {

		Sheet sheet = workbook.createSheet(sheetName);

		return sheet;
	}

}
