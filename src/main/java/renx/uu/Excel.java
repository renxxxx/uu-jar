package renx.uu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Excel {
	private static Logger logger = LoggerFactory.getLogger(Excel.class);
	public static void main(String[] args) throws IOException {
		Workbook wb = null;
		File file = new File("C:\\Users\\Administrator\\Desktop\\新建 Microsoft Excel 工作表.xlsx");
		String extString = file.getName().substring(file.getName().lastIndexOf("."));
		InputStream is = null;
		if (".xls".equals(extString)) {
			wb = new HSSFWorkbook(is);
		} else if (".xlsx".equals(extString)) {
			wb = new XSSFWorkbook(is);
		} else {
			wb = null;
		}

		Sheet sheet = wb.getSheetAt(0);
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null)
				continue;
			int tempRowSize = row.getLastCellNum() + 1;
			String[] values = new String[4];
			for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
				String value = "";
				Cell cell = row.getCell(columnIndex);
				value = cell.getStringCellValue();
				values[columnIndex] = value.trim().replaceAll("\\n|\\t", "").replaceAll("\\s+", " ");
			}
			System.out.println(Arrays.toString(values));
		}
	}

	public static Workbook readExcel(String filePath) throws IOException {
		Workbook wb = null;
		File file = new File(filePath);
		String extString = file.getName().substring(file.getName().lastIndexOf("."));
		InputStream is = null;
		if (".xls".equals(extString)) {
			wb = new HSSFWorkbook(is);
		} else if (".xlsx".equals(extString)) {
			wb = new XSSFWorkbook(is);
		} else {
			wb = null;
		}

		Sheet sheet = wb.getSheetAt(0);
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null)
				continue;
			int tempRowSize = row.getLastCellNum() + 1;
			String[] values = new String[4];
			for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
				String value = "";
				Cell cell = row.getCell(columnIndex);
				value = cell.getStringCellValue();
				values[columnIndex] = value.trim().replaceAll("\\n|\\t", "").replaceAll("\\s+", " ");
			}
		}
		return wb;
	}

	public static Object getCellFormatValue(Cell cell) {
		Object cellValue = null;
		if (cell != null) {
			// 判断cell类型
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: {
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			}
			case Cell.CELL_TYPE_FORMULA: {
				// 判断cell是否为日期格式
				if (DateUtil.isCellDateFormatted(cell)) {
					// 转换为日期格式YYYY-mm-dd
					cellValue = cell.getDateCellValue();
				} else {
					// 数字
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case Cell.CELL_TYPE_STRING: {
				cellValue = cell.getRichStringCellValue().getString();
				break;
			}
			default:
				cellValue = "";
			}
		} else {
			cellValue = "";
		}
		return cellValue;
	}
}
