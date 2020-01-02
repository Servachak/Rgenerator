package com.rgenerator.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WriteDataToExcel implements WriteDataToExcelInterface {

	String nameSheet = "";
	String nameFile = "";

	public void writeToFile(String nameSheet, String nameFile) {

		Workbook workbook = new HSSFWorkbook();

		Sheet sheet = workbook.createSheet(nameSheet);

		try {
			FileOutputStream stream = new FileOutputStream(nameFile + ".xls");

			workbook.write(stream);
			stream.close();

		} catch (FileNotFoundException e) {
			System.out.println("File is not created");
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void createExcelFile(String fineName) {

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(nameFile + ".xls");
			
			fileOutputStream.close();
		} catch (FileNotFoundException e) {

			System.out.println("File is not created");
			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void addSheetToFile(String nameSheet) {
		
		Workbook workbook = new HSSFWorkbook();
		
		Sheet sheet = workbook.createSheet(nameSheet);
		
		try {
			workbook.close();
		} catch (IOException e) {
			System.out.println("Workbook still be opened");
			e.printStackTrace();
		}

	}

	public void addRowToSheet() {

	}
}
