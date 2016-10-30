package cn.com.yibin.maomi.core.util;//package cn.com.yibin.maomi.core.util;
//
//import java.awt.Font;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.BeanUtils;
//
//import com.andsonap.core.constant.ExcelVersionEnum;
//import com.sun.rowset.internal.Row;
//
//
//public class PoiExcelUtil {
//	public static Workbook createExcelWorkbook(ExcelVersionEnum excelVersionEnum) {
//		Workbook wb = null;
//		switch (excelVersionEnum) {
//		case VERSION2003: {
//			wb = new HSSFWorkbook();
//			break;
//		}
//		case VERSION2007: {
//			wb = new XSSFWorkbook();
//			break;
//		}
//		}
//		return wb;
//	}
//
//	public static Workbook readWorkbook(String filepath, ExcelVersionEnum excelVersionEnum) throws Exception {
//		Workbook wb = null;
//		switch (excelVersionEnum) {
//		case VERSION2003: {
//			wb = new HSSFWorkbook(new FileInputStream(filepath));
//			break;
//		}
//		case VERSION2007: {
//			wb = new XSSFWorkbook(new FileInputStream(filepath));
//			break;
//		}
//		}
//		return wb;
//	}
//
//	public static Workbook readWorkbook(InputStream is, ExcelVersionEnum excelVersionEnum) throws Exception {
//		Workbook wb = null;
//		switch (excelVersionEnum) {
//		case VERSION2003: {
//			wb = new HSSFWorkbook(is);
//			break;
//		}
//		case VERSION2007: {
//			wb = new XSSFWorkbook(is);
//			break;
//		}
//		}
//		return wb;
//	}
//
//	public static void writeWorkBook(Workbook workbook, OutputStream os) throws Exception {
//		workbook.write(os);
//	}
//
//	public static Sheet createExcelSheet(Workbook workbook, String sheetname) {
//		if (StringUtils.isBlank(sheetname)) {
//			return workbook.createSheet();
//		}
//		return workbook.createSheet(sheetname);
//	}
//
//	public static Sheet createExcelSheet(Workbook workbook) {
//		return workbook.createSheet();
//	}
//
//	public static Row createExcelRow(Sheet sheet, int rowIndex) {
//		return sheet.createRow(rowIndex);
//	}
//
//	public static Cell createExcelCell(Row row, int colIndex) {
//		return row.createCell(colIndex);
//	}
//
//	public static Cell createLabelExcelCell(Row row, int colIndex, CellStyle style, Object cellValue, short align) {
//		// style.setAlignment(CellStyle.ALIGN_CENTER);
//		String value = StringUtil.emptyOpt(cellValue);
//		Cell cell = createExcelCell(row, colIndex);
//		style.setAlignment(align);
//		cell.setCellStyle(style);
//		cell.setCellType(Cell.CELL_TYPE_STRING);
//		cell.setCellValue(value);
//		return cell;
//	}
//
//	public static Cell createNumberExcelCell(Row row, int colIndex, CellStyle style, Object cellValue, int scale) {
//		NumberFormat nf = NumberFormat.getNumberInstance();
//		nf.setRoundingMode(RoundingMode.HALF_EVEN);
//		nf.setMaximumFractionDigits(scale);
//		String value = StringUtil.emptyOpt(cellValue);
//		double doubleValue = 0.0;
//		Cell cell = createExcelCell(row, colIndex);
//		cell.setCellStyle(style);
//		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//		if (value.matches("^([-]){0,1}([0-9]){1,}([.]){0,1}([0-9]){0,}$")) {
//			doubleValue = Double.parseDouble(value);
//			cell.setCellValue(doubleValue);
//		} else {
//			cell.setCellValue("");
//		}
//		return cell;
//	}
//
//	public static CellRangeAddress createMergeRegion(Sheet sheet, Cell cell, int rowspan, int colspan) {
//		CellStyle style = cell.getCellStyle();
//		style.setAlignment(CellStyle.ALIGN_CENTER);
//		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		int fromRowIndex = cell.getRowIndex();
//		int toRowIndex = (fromRowIndex + rowspan - 1);
//		int fromColumnIndex = cell.getColumnIndex();
//		int toColumnIndex = (fromColumnIndex + colspan - 1);
//		CellRangeAddress cellRangeAddress = new CellRangeAddress(fromRowIndex, toRowIndex, fromColumnIndex, toColumnIndex);
//		sheet.addMergedRegion(cellRangeAddress);
//		return cellRangeAddress;
//	}
//
//	// 强制刷新sheet中的公式
//	public static void forceRefreshFormula(Sheet sheet) {
//		sheet.setForceFormulaRecalculation(true);
//	}
//
//	public static CellStyle createCellStyle(Workbook workbook) {
//		return workbook.createCellStyle();
//	}
//
//	public static Font createFont(Workbook workbook) {
//		return workbook.createFont();
//	}
//
//	public static DataFormat createDataFormat(Workbook workbook) {
//		return workbook.createDataFormat();
//	}
//
//	public static void setFont(CellStyle style, Font font) {
//		style.setFont(font);
//	}
//
//	// 预定义样式
//	// 文件标题样式
//	public static CellStyle getExportFileNameStyle(Workbook workbook) {
//		CellStyle style = createCellStyle(workbook);
//		style.setAlignment(CellStyle.ALIGN_CENTER);
//		style.setAlignment(CellStyle.VERTICAL_CENTER);
//		style.setBorderTop(CellStyle.BORDER_THIN);
//		style.setBorderBottom(CellStyle.BORDER_THIN);
//		style.setBorderLeft(CellStyle.BORDER_THIN);
//		style.setBorderRight(CellStyle.BORDER_THIN);
//		style.setTopBorderColor(HSSFColor.BLACK.index);
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		Font font = createFont(workbook);
//		font.setFontName("宋体");
//		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// 粗体
//		font.setFontHeightInPoints((short) 15); // 字体大小
//		font.setColor(HSSFColor.RED.index);
//		font.setUnderline((byte) 0);
//		style.setFont(font);
//		return style;
//	}
//
//	// 导出日期样式
//	public static CellStyle getExportDateStyle(Workbook workbook) {
//		CellStyle style = createCellStyle(workbook);
//		style.setAlignment(CellStyle.ALIGN_CENTER);
//		style.setAlignment(CellStyle.VERTICAL_CENTER);
//		style.setBorderTop(CellStyle.BORDER_THIN);
//		style.setBorderBottom(CellStyle.BORDER_THIN);
//		style.setBorderLeft(CellStyle.BORDER_THIN);
//		style.setBorderRight(CellStyle.BORDER_THIN);
//		style.setTopBorderColor(HSSFColor.BLACK.index);
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		Font font = createFont(workbook);
//		font.setFontName("宋体");
//		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// 粗体
//		font.setFontHeightInPoints((short) 12); // 字体大小
//		font.setColor(HSSFColor.TEAL.index);
//		font.setUnderline((byte) 0);
//		style.setFont(font);
//		return style;
//	}
//
//	// 导出复杂表头样式
//	public static CellStyle getExportComplexHeaderTitleStyle(Workbook workbook) {
//		CellStyle style = createCellStyle(workbook);
//		style.setAlignment(CellStyle.ALIGN_CENTER);
//		style.setAlignment(CellStyle.VERTICAL_CENTER);
//		style.setBorderTop(CellStyle.BORDER_THIN);
//		style.setBorderBottom(CellStyle.BORDER_THIN);
//		style.setBorderLeft(CellStyle.BORDER_THIN);
//		style.setBorderRight(CellStyle.BORDER_THIN);
//		style.setTopBorderColor(HSSFColor.BLACK.index);
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		style.setFillBackgroundColor(HSSFColor.YELLOW.index2);
//		style.setFillForegroundColor(HSSFColor.YELLOW.index2);
//		style.setFillPattern(CellStyle.SOLID_FOREGROUND);//
//		Font font = createFont(workbook);
//		font.setFontName("Arial");
//		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// 粗体
//		font.setFontHeightInPoints((short) 10); // 字体大小
//		// font.setColor(HSSFColor.YELLOW.index2);
//		font.setUnderline((byte) 0);
//		style.setFont(font);
//		return style;
//	}
//
//	// 内容标题样式
//	public static CellStyle getExportContentTitleStyle(Workbook workbook) {
//		CellStyle style = createCellStyle(workbook);
//		style.setAlignment(CellStyle.ALIGN_CENTER);
//		style.setAlignment(CellStyle.VERTICAL_CENTER);
//		style.setBorderTop(CellStyle.BORDER_THIN);
//		style.setBorderBottom(CellStyle.BORDER_THIN);
//		style.setBorderLeft(CellStyle.BORDER_THIN);
//		style.setBorderRight(CellStyle.BORDER_THIN);
//		style.setTopBorderColor(HSSFColor.BLACK.index);
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		style.setFillBackgroundColor(HSSFColor.YELLOW.index2);
//		style.setFillForegroundColor(HSSFColor.YELLOW.index2);
//		style.setFillPattern(CellStyle.SOLID_FOREGROUND);//
//		Font font = createFont(workbook);
//		font.setFontName("Arial");
//		font.setBoldweight(Font.BOLDWEIGHT_BOLD);// 粗体
//		font.setFontHeightInPoints((short) 10); // 字体大小
//		// font.setColor(HSSFColor.YELLOW.index2);
//		font.setUnderline((byte) 0);
//		style.setFont(font);
//		return style;
//	}
//
//	// 普通文本样式
//	public static CellStyle getExportContentStyle(Workbook workbook) {
//		CellStyle style = createCellStyle(workbook);
//		style.setAlignment(CellStyle.ALIGN_RIGHT);
//		style.setAlignment(CellStyle.VERTICAL_CENTER);
//		style.setBorderTop(CellStyle.BORDER_THIN);
//		style.setBorderBottom(CellStyle.BORDER_THIN);
//		style.setBorderLeft(CellStyle.BORDER_THIN);
//		style.setBorderRight(CellStyle.BORDER_THIN);
//		style.setTopBorderColor(HSSFColor.BLACK.index);
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		style.setWrapText(true);
//		Font font = createFont(workbook);
//		font.setFontName("Arial");
//		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);// 粗体
//		font.setFontHeightInPoints((short) 10); // 字体大小
//		font.setColor(HSSFColor.BLACK.index);
//		font.setUnderline((byte) 0);
//		style.setFont(font);
//		// style.setWrapText(true);
//		return style;
//	}
//
//	// Number样式
//	public static CellStyle getExportContentNumberStyle(Workbook workbook) {
//		CellStyle style = createCellStyle(workbook);
//		style.setAlignment(CellStyle.ALIGN_CENTER);
//		style.setAlignment(CellStyle.VERTICAL_CENTER);
//		style.setBorderTop(CellStyle.BORDER_THIN);
//		style.setBorderBottom(CellStyle.BORDER_THIN);
//		style.setBorderLeft(CellStyle.BORDER_THIN);
//		style.setBorderRight(CellStyle.BORDER_THIN);
//		style.setTopBorderColor(HSSFColor.BLACK.index);
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		Font font = createFont(workbook);
//		font.setFontName("Arial");
//		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);// 粗体
//		font.setFontHeightInPoints((short) 10); // 字体大小
//		font.setColor(HSSFColor.BLACK.index);
//		font.setUnderline((byte) 0);
//		style.setFont(font);
//		return style;
//	}
//
//	// 日期样式
//	public static CellStyle getExportContentDateStyle(Workbook workbook) {
//		DataFormat dataFormat = createDataFormat(workbook);
//		CellStyle style = createCellStyle(workbook);
//		style.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
//		style.setAlignment(CellStyle.ALIGN_CENTER);
//		style.setAlignment(CellStyle.VERTICAL_CENTER);
//		style.setBorderTop(CellStyle.BORDER_THIN);
//		style.setBorderBottom(CellStyle.BORDER_THIN);
//		style.setBorderLeft(CellStyle.BORDER_THIN);
//		style.setBorderRight(CellStyle.BORDER_THIN);
//		style.setTopBorderColor(HSSFColor.BLACK.index);
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		Font font = createFont(workbook);
//		font.setFontName("Arial");
//		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);// 粗体
//		font.setFontHeightInPoints((short) 10); // 字体大小
//		font.setColor(HSSFColor.BLACK.index);
//		font.setUnderline((byte) 0);
//		style.setFont(font);
//		return style;
//	}
//
//	/**
//	 * 
//	 * @param sheet
//	 *            区域所在的sheet
//	 * @param rownum
//	 *            指定单元格所在行
//	 * @param column
//	 *            指定单元格所在列 删除指定单元格所在的区域
//	 */
//	public static void clearRegionAssign(Sheet sheet, int rownum) {
//		int n = sheet.getNumMergedRegions();
//		for (int i = (n - 1); i >= 0; i--) {
//			CellRangeAddress region = sheet.getMergedRegion(i);// .getMergedRegionAt(i);
//			if (region.getFirstRow() <= rownum && region.getLastRow() >= rownum) {
//				sheet.removeMergedRegion(i);
//			} else if (region.getLastRow() < rownum) {
//				break;
//			}
//		}
//	}
//
//	// 设置合并区域格式
//	public static void setMergeRegionsCellStyle(Sheet sheet, CellStyle cellStyle) {
//		int n = sheet.getNumMergedRegions();
//		for (int index = (n - 1); index >= 0; index--) {
//			CellRangeAddress mergeRegion = sheet.getMergedRegion(index);
//			for (int i = mergeRegion.getFirstRow(); i <= mergeRegion.getLastRow(); i++) {
//				Row row = sheet.getRow(i);
//				for (int j = mergeRegion.getFirstColumn(); j <= mergeRegion.getLastColumn(); j++) {
//					Cell cell = row.getCell(j);
//					if (cell == null) {
//						cell = row.createCell(j);
//					}
//					if (StringUtil.emptyOpt(cell.getStringCellValue()).trim().equals("")) {
//						cell.setCellStyle(cellStyle);
//					}
//				}
//			}
//		}
//	}
//
//	// 设置自动列宽
//	public static void setAutoColumnWidth(Sheet sheet, boolean isAuto) {
//		if (sheet.getLastRowNum() >= 1) {
//			for (int index = 0; index < sheet.getRow(0).getLastCellNum(); index++) {
//				sheet.autoSizeColumn(index, isAuto);
//			}
//		}
//	}
//
//	public static void setAutoColumnWidthByMap(Sheet sheet, Map<String, Integer> savedWidthMap) {
//		if (sheet.getLastRowNum() >= 1) {
//			for (int index = 0; index < sheet.getRow(0).getLastCellNum(); index++) {
//				//Integer width = savedWidthMap.get("col_" + index);
//				//sheet.setColumnWidth(index, Math.min(width.intValue(),255) * 256);
//				sheet.autoSizeColumn(index, true);
//			}
//		}
//	}
//
//	public static List<List<String>> readExcelToArray(InputStream is, ExcelVersionEnum excelVersionEnum) throws Exception {
//		List<List<String>> excelArray = new ArrayList<List<String>>();
//		Workbook wb = PoiExcelUtil.readWorkbook(is, excelVersionEnum);
//		Sheet sheet = wb.getSheetAt(0);
//		Iterator<Row> rit = sheet.rowIterator();
//
//		while (rit.hasNext()) {
//			List<String> cellArray = new ArrayList<String>();
//			Row row = (Row) rit.next();
//			for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) {
//				Cell cell = (Cell) cit.next();
//				cellArray.add(getStringCellValue(cell));
//			}
//			excelArray.add(cellArray);
//		}
//		is.close();
//		return excelArray;
//	}
//
//	/**
//	 * 获取单元格数据内容为字符串类型的数据
//	 * 
//	 * @param cell
//	 *            Excel单元格
//	 * @return String 单元格数据内容
//	 */
//	private static String getStringCellValue(Cell cell) {
//		String strCell = "";
//		switch (cell.getCellType()) {
//		case Cell.CELL_TYPE_STRING:
//			strCell = cell.getStringCellValue();
//			break;
//		case Cell.CELL_TYPE_NUMERIC:
//			try {
//				strCell = String.valueOf(cell.getNumericCellValue());
//			} catch (Exception e) {
//				strCell = cell.getStringCellValue().replaceAll(",", "");
//			}
//
//			break;
//		case Cell.CELL_TYPE_BOOLEAN:
//			strCell = String.valueOf(cell.getBooleanCellValue());
//			break;
//		case Cell.CELL_TYPE_BLANK:
//			strCell = "";
//			break;
//		default:
//			strCell = "";
//			break;
//		}
//		if (strCell.equals("") || strCell == null) {
//			return "";
//		}
//		return strCell;
//	}
//
//	  
//	//  Parameters:
//	//   startRow - the row to start shifting
//	//   endRow - the row to end shifting
//	//   n - the number of rows to shift
//	//   copyRowHeight - whether to copy the row height during the shift
//	//   resetOriginalRowHeight - whether to set the original row's height to the default
//
//	public static void insertRow(Sheet sheet, int starRow, int rows) throws Exception {
//		  Row sourceRow = null;
//		  try {
//			sheet.shiftRows(starRow, sheet.getLastRowNum(), rows,true,false);
//			  sourceRow = sheet.getRow(starRow+rows);
//			  for (int i = 0; i < rows; i++) {
//			   Row targetRow = null;
//			   Cell sourceCell = null;
//			   Cell targetCell = null;
//			   int m;
//			   targetRow = sheet.createRow(starRow + i);
//			   targetRow.setHeight(sourceRow.getHeight());
//			  
//			   for (m = sourceRow.getFirstCellNum(); m <= sourceRow.getLastCellNum(); m++) {
//			     
//				   sourceCell = sourceRow.getCell(m);
//				   
//			    targetCell = targetRow.createCell(m);
//			    if(null!=sourceCell){
//			       targetCell.setCellStyle(sourceCell.getCellStyle());
//			       targetCell.setCellType(sourceCell.getCellType());
//			    }
//			   }
//			  }
//		} catch (Exception e) {
//			throw new BusinessException("excel移行出错"+e.getMessage());
//		}
//	}
//
//	/************** Simon 附加 *****************/
//
//	// Copy 指定区单元格。顺序拷贝
//	// Parameters:
//	// ：sheet
//	// :starRow 开始行
//	// :endRow 结束行
//	// ：num 次数
//	public static void insertRowArea(Sheet sheet, int starRow, int endRow, int num) throws Exception {
//		int rowRegion = endRow - starRow + 1;
//		try {
//			sheet.shiftRows(endRow, sheet.getLastRowNum(), num * rowRegion, true, false);
//			CellRangeAddress region = null;
//			for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
//				region = sheet.getMergedRegion(i);
//				if ((region.getFirstRow() >= starRow - 1) && (region.getFirstRow() <= endRow - 1)) {
//					for (int j = 1; j < num + 1; j++) {
//						int tempstartRow = starRow + (rowRegion * j) + region.getFirstRow() - starRow;
//						CellRangeAddress temp = new CellRangeAddress(tempstartRow, tempstartRow, region.getFirstColumn(), region.getLastColumn());
//						sheet.addMergedRegion(temp);
//					}
//				}
//			}
//			for (int i = 1; i < num + 1; i++) {
//				for (int j = 0; j < rowRegion; j++) {
//					Row sourceRow = null;
//					Row targetRow = null;
//					Cell sourceCell = null;
//					Cell targetCell = null;
//					int m;
//					sourceRow = sheet.getRow(starRow + j - 1);
//
//					targetRow = sheet.createRow(starRow - 1 + i * rowRegion + j);
//					targetRow.setHeight(sourceRow.getHeight());
//					for (m = sourceRow.getFirstCellNum(); m <= sourceRow.getLastCellNum(); m++) {
//						sourceCell = sourceRow.getCell(m);
//						targetCell = targetRow.createCell(m);
//						if(null!=sourceCell){
//						targetCell.setCellStyle(sourceCell.getCellStyle());
//						targetCell.setCellType(sourceCell.getCellType());
//						targetCell.setCellValue(PoiExcelUtil.getCellValue(sourceCell));
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			 	throw new BusinessException("excel拷贝一个区域出错"+e.getMessage());
//		}
//	}
//
//	public static void deleteRowByArea(Sheet sheet, int starRow, int endRow) throws Exception {
//		try {
//			List<Row> rowlist = new ArrayList<Row>();
//			for (int i = starRow; i < endRow + 1; i++) {
//				rowlist.add(sheet.getRow(i));
//			}
//			for (int i = 0; i < rowlist.size(); i++) {
//				sheet.removeRow(rowlist.get(i));
//			}
//			List<Short> beforeheight = new ArrayList<Short>();
//			for (int i = endRow + 1; i < sheet.getLastRowNum(); i++) {
//				beforeheight.add(sheet.getRow(i).getHeight());
//			}
//			sheet.shiftRows(endRow + 1, sheet.getLastRowNum(), (endRow - starRow + 1) * -1, false, false);
//			for (int i = 0; i < beforeheight.size(); i++) {
//				sheet.getRow(starRow + i).setHeight(Short.valueOf(beforeheight.get(i).toString()));
//			}
//		} catch (Exception e) {
//			throw new BusinessException("excel删除一个区域出错"+e.getMessage());
//		}
//	};
//
//	public static boolean isCellDateFormatted(Cell cell) throws Exception {
//		return DateUtil.isCellDateFormatted(cell);
//	}
//
//	public static Date getJavaDate(double cellvalue) throws Exception {
//		return DateUtil.getJavaDate(cellvalue);
//	}
//
//	public static Row getRow(Sheet sheet, int rownum) {
//		if (null == sheet.getRow(rownum)) {
//			return sheet.createRow(rownum);
//		} else {
//			return sheet.getRow(rownum);
//		}
//	}
//
//	public static Cell getCell(Row row, int cellnum) {
//		if (null == row.getCell(cellnum)) {
//			return row.createCell(cellnum);
//		} else {
//			return row.getCell(cellnum);
//		}
//	}
//
//	public static String getCellValue(Cell cell) {
//		if (null == cell) {
//			return "";
//		}
//		String value = "";
//		try {
//			switch (cell.getCellType()) {
//			case Cell.CELL_TYPE_NUMERIC: {
//				try {
//					if (PoiExcelUtil.isCellDateFormatted(cell)) {
//						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//						value = sdf.format(PoiExcelUtil.getJavaDate(cell.getNumericCellValue())).toString();
//					} else {
//						value = MathUtil.decimal(cell.getNumericCellValue(), 10);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					value = cell.getStringCellValue() + "";
//					value = MathUtil.decimal(Double.parseDouble(value.replaceAll(",", "")), 10);
//				}
//				break;
//			}
//			case Cell.CELL_TYPE_STRING: {
//				value = cell.getStringCellValue() + "";
//				break;
//			}
//			case Cell.CELL_TYPE_BOOLEAN: {
//				value = cell.getBooleanCellValue() + "";
//				break;
//			}
//			case Cell.CELL_TYPE_FORMULA: {
//				try {
//					value = String.valueOf(cell.getNumericCellValue());
//					BigDecimal bd = new BigDecimal(value);
//					value = bd.toPlainString();
//				} catch (IllegalStateException e) {
//					try {
//						value = String.valueOf(cell.getRichStringCellValue());
//					} catch (Exception e1) {
//						value = String.valueOf(cell.getBooleanCellValue());
//					}
//
//				}
//			}
//			}
//		} catch (Exception e) {
//			value = "";
//		}
//		value = value.trim();
//		return value;
//	}
//
//	public static void copySheet(Sheet fromsheet, Sheet toSheet) throws Exception {
//
//		// BeanUtils.copyProperties(fromsheet,toSheet);
//		// 合并单元格
//		CellRangeAddress region = null;
//		for (int i = 0; i < fromsheet.getNumMergedRegions(); i++) {
//			region = fromsheet.getMergedRegion(i);
//			CellRangeAddress temp = new CellRangeAddress(region.getFirstRow(), region.getLastRow(), region.getFirstColumn(), region.getLastColumn());
//			toSheet.addMergedRegion(temp);
//		}
//		// 页面打印设置
//		PrintSetup sourceps = fromsheet.getPrintSetup();
//		PrintSetup targetps = toSheet.getPrintSetup();
//		// targetps.setLandscape(sourceps.getLandscape()); //
//		// 打印方向，true：横向，false：纵向(默认)
//		// targetps.setVResolution(sourceps.getVResolution());
//		// targetps.setPaperSize(sourceps.getPaperSize()); // 纸张类型
//		// targetps.setFooterMargin(sourceps.getFooterMargin());
//		// targetps.setHeaderMargin(sourceps.getFooterMargin());
//		BeanUtils.copyProperties(sourceps, targetps);
//
//		// 设置页边距
//		toSheet.setMargin(Sheet.TopMargin, fromsheet.getMargin(Sheet.TopMargin));
//		toSheet.setMargin(Sheet.BottomMargin, fromsheet.getMargin(Sheet.BottomMargin));
//		toSheet.setMargin(Sheet.LeftMargin, fromsheet.getMargin(Sheet.LeftMargin));
//		toSheet.setMargin(Sheet.RightMargin, fromsheet.getMargin(Sheet.RightMargin));
//		toSheet.setMargin(Sheet.HeaderMargin, fromsheet.getMargin(Sheet.HeaderMargin));
//		toSheet.setMargin(Sheet.FooterMargin, fromsheet.getMargin(Sheet.FooterMargin));
//
//		//Drawing patriarch = toSheet.createDrawingPatriarch();
//		// patriarch.createPicture(arg0, arg1)
//
//		// List<HSSFPictureData> pictures =
//		// fromsheet.getWorkbook().getAllPictures();
//
//		// 页眉
//
//		try {
//			int pictureIdx = 0;// wb.addPicture(data, wb.PICTURE_TYPE_JPEG);
//			Drawing drawing = toSheet.createDrawingPatriarch();
//			CreationHelper helper = toSheet.getWorkbook().getCreationHelper();
//			ClientAnchor anchor = helper.createClientAnchor();
//			anchor.setCol1(0);
//			anchor.setRow1(0);
//			Picture pict = drawing.createPicture(anchor, pictureIdx);
//			pict.resize();// 该方法只支持JPEG 和 PNG后缀文件
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// 插入图片
//
//		Row sourceRow = null;
//		Row targetRow = null;
//		Cell sourceCell = null;
//		Cell targetCell = null;
//		
//		for (int i = fromsheet.getFirstRowNum(); i <= fromsheet.getLastRowNum(); i++) {
//			sourceRow = fromsheet.getRow(i);
//			if (null != sourceRow) {
//				targetRow = toSheet.createRow(i);
//				targetRow.setHeight(sourceRow.getHeight());
//				for (int m = sourceRow.getFirstCellNum(); m <= sourceRow.getLastCellNum(); m++) {
//					if (i == fromsheet.getFirstRowNum()) {
//						toSheet.setColumnWidth(m, fromsheet.getColumnWidth(m));
//					}
//					
//					sourceCell = sourceRow.getCell(m);
//					if(null==sourceCell){
//						targetCell = targetRow.createCell(m);
//					}else{
//					targetCell = targetRow.createCell(m);
//					targetCell.setCellStyle(sourceCell.getCellStyle());
//					targetCell.setCellType(sourceCell.getCellType());
//					targetCell.setCellValue(PoiExcelUtil.getCellValue(sourceCell));
//					}
//					
//				}
//			} else {
//				targetRow = toSheet.createRow(i);
//			}
//		}
//	}
//}
