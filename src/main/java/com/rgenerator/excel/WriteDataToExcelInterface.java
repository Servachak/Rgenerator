package com.rgenerator.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface WriteDataToExcelInterface {

	void createExcelFile(String fineName,Sheet sheet,Workbook workbook);
	
	Sheet createSheet(String sheetName, Workbook workbook);
	
	
	
}
