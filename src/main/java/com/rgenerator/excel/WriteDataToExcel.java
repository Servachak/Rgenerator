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
			FileOutputStream stream = new FileOutputStream(nameFile);

			workbook.write(stream);
			stream.close();

		} catch (FileNotFoundException e) {
			System.out.println("File is not created");
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
