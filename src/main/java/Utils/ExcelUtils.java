package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	public static Object[][] getData(String filePath, String sheetName){
		List<String[]> rows = new ArrayList<>();
		
		try(FileInputStream fis = new FileInputStream(filePath);
				Workbook workbook = new XSSFWorkbook(fis)){
			Sheet sheet = workbook.getSheet(sheetName);
			
			if(sheet == null) {
				throw new RuntimeException("Sheet"+sheetName+"Not found in"+filePath);
			}
			DataFormatter formatter = new DataFormatter();
			int lastRow = sheet.getLastRowNum();
			
			for(int i=1;i<lastRow;i++) {
				Row row=sheet.getRow(i);
				if(row==null) continue;
				
				int lastColumn =row.getLastCellNum();				
				String rowData[] = new String[lastColumn];
				
				for(int c=0;c<lastColumn;c++) {
					Cell cell=row.getCell(c);
					rowData[c]= (cell==null)?"":formatter.formatCellValue(cell);
				}
				
				rows.add(rowData);
			}
			
			
			
		}
		catch(IOException e){
			throw new RuntimeException("Failed reading Excel File:" +filePath,e);
		}
		
		Object[][] data = new Object[rows.size()][];
		for(int i=0;i<rows.size();i++) {
			data[i] = rows.get(i);
		}
		return data;
	}

}
