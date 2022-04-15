package renx.uu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Exceluu {
	private static Logger logger = LoggerFactory.getLogger(Exceluu.class);

//	public static void main(String[] args) throws IOException {
//		Workbook wb = null;
//		File file = new File("C:\\Users\\Administrator\\Desktop\\新建 Microsoft Excel 工作表.xlsx");
//		String extString = file.getName().substring(file.getName().lastIndexOf("."));
//		InputStream is = null;
//		if (".xls".equals(extString)) {
//			wb = new HSSFWorkbook(is);
//		} else if (".xlsx".equals(extString)) {
//			wb = new XSSFWorkbook(is);
//		} else {
//			wb = null;
//		}
//
//		Sheet sheet = wb.getSheetAt(0);
//		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
//			Row row = sheet.getRow(i);
//			if (row == null)
//				continue;
//			int tempRowSize = row.getLastCellNum() + 1;
//			String[] values = new String[4];
//			for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
//				String value = "";
//				Cell cell = row.getCell(columnIndex);
//				value = cell.getStringCellValue();
//				values[columnIndex] = value.trim().replaceAll("\\n|\\t", "").replaceAll("\\s+", " ");
//			}
//			System.out.println(Arrays.toString(values));
//		}
//	}
//
//	public static Workbook readExcel(String filePath) throws IOException {
//		Workbook wb = null;
//		File file = new File(filePath);
//		String extString = file.getName().substring(file.getName().lastIndexOf("."));
//		InputStream is = null;
//		if (".xls".equals(extString)) {
//			wb = new HSSFWorkbook(is);
//		} else if (".xlsx".equals(extString)) {
//			wb = new XSSFWorkbook(is);
//		} else {
//			wb = null;
//		}
//
//		Sheet sheet = wb.getSheetAt(0);
//		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
//			Row row = sheet.getRow(i);
//			if (row == null)
//				continue;
//			int tempRowSize = row.getLastCellNum() + 1;
//			String[] values = new String[4];
//			for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
//				String value = "";
//				Cell cell = row.getCell(columnIndex);
//				value = cell.getStringCellValue();
//				values[columnIndex] = value.trim().replaceAll("\\n|\\t", "").replaceAll("\\s+", " ");
//			}
//		}
//		return wb;
//	}
//
//	public static Object getCellFormatValue(Cell cell) {
//		Object cellValue = null;
//		if (cell != null) {
//			// 判断cell类型
//			switch (cell.getCellType()) {
//			case Cell.CELL_TYPE_NUMERIC: {
//				cellValue = String.valueOf(cell.getNumericCellValue());
//				break;
//			}
//			case Cell.CELL_TYPE_FORMULA: {
//				// 判断cell是否为日期格式
//				if (DateUtil.isCellDateFormatted(cell)) {
//					// 转换为日期格式YYYY-mm-dd
//					cellValue = cell.getDateCellValue();
//				} else {
//					// 数字
//					cellValue = String.valueOf(cell.getNumericCellValue());
//				}
//				break;
//			}
//			case Cell.CELL_TYPE_STRING: {
//				cellValue = cell.getRichStringCellValue().getString();
//				break;
//			}
//			default:
//				cellValue = "";
//			}
//		} else {
//			cellValue = "";
//		}
//		return cellValue;
//	}

	public static List<List> parse(String path) throws FileNotFoundException, IOException {
		List<List> list = new ArrayList<List>();

		// 1. 读取一个文件,将文件转换成工作簿(WorkBook)
		Workbook workbook = new XSSFWorkbook(new FileInputStream(new File(path)));
		// 2. 从工作簿中读取工作表
		Sheet sheet = workbook.getSheetAt(0);// 根据索引获取工作表
		// 3. 从工作表中读取行(在获取最后一行的时候,+1操作)
		for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
			List values = new ArrayList();
			Row row = sheet.getRow(i);
			// 4. 从行中读取单元格
			for (int j = 0; j < row.getLastCellNum(); j++) {
				Cell cell = row.getCell(j);
				// 5. 从单元格中读取值
				// System.out.print(cell.getStringCellValue() + " ");
				Object value = null;
				if (cell != null)
					value = getCellValue(cell);
				values.add(value);
			}
			list.add(values);
		}
		return list;
	}

	public static Object getCellValue(Cell cell) {
		Object obj = null;
		CellType cellType = cell.getCellType(); // 获取单元格数据类型

		switch (cellType) {
		case STRING: {
			obj = cell.getStringCellValue();// 字符串
			break;
		}
		case NUMERIC: {
			if (DateUtil.isCellDateFormatted(cell)) {
				obj = cell.getDateCellValue();// 日期
			} else {
				Double dd = cell.getNumericCellValue();
				String dds = dd.toString();
				String[] ss = dds.split("\\.");
				String d = null;
				String d1 = ss[0];
				String d2 = ss.length > 0 ? ss[1] : null;
				if (d2 != null && !d2.isEmpty()) {
					if (d2.replaceAll("0", "").isEmpty())
						d2 = null;
				}
				d = d1;
				if (d2 != null && !d2.isEmpty())
					d = d + "." + d2;
				dd = Double.parseDouble(d);
				obj = dd; // 数字
			}
			break;
		}
		case BOOLEAN: {
			obj = cell.getBooleanCellValue(); // 布尔
			break;
		}
		default: {
			break;
		}
		}
		return obj;
	}
}
