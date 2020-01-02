package com.rgenerator.excel;

import org.apache.poi.ss.usermodel.Workbook;

public interface WriteDataToExcelInterface {

	void createExcelFile(String fineName);
	
	void addSheetToFile(String nameSheet);
	
	void addRowToSheet();
	
	
	
}
