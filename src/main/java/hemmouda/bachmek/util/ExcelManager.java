package hemmouda.bachmek.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import hemmouda.bachmek.business.GradeManager.GradeState;
import hemmouda.bachmek.listeners.InitListener;

import jakarta.servlet.http.HttpServletResponse;

public class ExcelManager {
	
	private static final short HEADER_COLOR = IndexedColors.GREY_25_PERCENT.getIndex();
	private static final FillPatternType HEADER_FILL = FillPatternType.SOLID_FOREGROUND;
	
	private static final short PASSED_COLOR = IndexedColors.GREEN.getIndex();
	private static final FillPatternType PASSED_FILL = FillPatternType.SOLID_FOREGROUND;
	
	private static final short FAILED_COLOR = IndexedColors.RED.getIndex();
	private static final FillPatternType FAILED_FILL = FillPatternType.SOLID_FOREGROUND;

	/**
	 * @return true if the data in the column is unique
	 * (case ignored) across all rows
	 */
	public static boolean assertUnique (List <String []> data, int column) {
		for (int i = 0; i < data.size(); i++) {
			String [] row = data.get(i);
			if (row[column] == null) {
				continue;
			}
			for (int j = 0; j < data.size(); j++) {
				if (i == j) {
					continue;
				}
				if (StringManager.equalsIgnoreCase(row[column], data.get(j)[column])) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void writeGrades (String file_name, String sheet_name, String headers [], Object data [][], HttpServletResponse response) {
		// I don't know why it still gives resources leak warning even tho i close it later on
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet sheet = workbook.createSheet(sheet_name);
		 
		int row_num = 0;
		Row row = sheet.createRow(row_num++);
		
		int cell_num;
		CellStyle header_style = workbook.createCellStyle();
		header_style.setFillForegroundColor(HEADER_COLOR);
		header_style.setFillPattern(HEADER_FILL);
		for (cell_num = 0; cell_num < headers.length; cell_num++) {
			Cell cell = row.createCell(cell_num);
			cell.setCellValue(headers[cell_num]);
			cell.setCellStyle(header_style);
		}
		
		CellStyle passed_style = workbook.createCellStyle();
		passed_style.setFillForegroundColor(PASSED_COLOR);
		passed_style.setFillPattern(PASSED_FILL);
		CellStyle failed_style = workbook.createCellStyle();
		failed_style.setFillForegroundColor(FAILED_COLOR);
		failed_style.setFillPattern(FAILED_FILL);
		
		if (data != null) {
			int last_index = (data.length == 0) ? -1 : data [0].length -1;
			for (; row_num -1 < data.length; row_num++) {
				row = sheet.createRow(row_num);
				for (cell_num = 0; cell_num < data[row_num -1].length; cell_num++) {
					if (cell_num == last_index) {
						Cell cell = row.createCell(cell_num);
						cell.setCellValue("" +data[row_num -1][cell_num]);
						switch (GradeState.getGradeState("" +data[row_num -1][cell_num])) {
						case PASSED: {
							cell.setCellStyle(passed_style);
							break;
						}
						case FAILED: {
							cell.setCellStyle(failed_style);
							break;
						}
						default: break;
						}
					} else {
						row.createCell(cell_num).setCellValue("" +data[row_num -1][cell_num]);
					}
				}
			}
		}
		
        response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" +file_name +ExcelManager.FileManager.EXTENSION);
        
		OutputStream out;
		try {
			out = response.getOutputStream();
			workbook.write(out);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
}
	
	public static void write (String file_name, String sheet_name, String headers [], Object data [][], HttpServletResponse response) {
				// I don't know why it still gives resources leak warning even tho i close it later on
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook();
				
				XSSFSheet sheet = workbook.createSheet(sheet_name);
				 
				int row_num = 0;
				Row row = sheet.createRow(row_num++);
				
				int cell_num;
				CellStyle header_style = workbook.createCellStyle();
				header_style.setFillForegroundColor(HEADER_COLOR);
				header_style.setFillPattern(HEADER_FILL);
				for (cell_num = 0; cell_num < headers.length; cell_num++) {
					Cell cell = row.createCell(cell_num);
					cell.setCellValue(headers[cell_num]);
					cell.setCellStyle(header_style);
				}
				
				if (data != null) {
					for (; row_num -1 < data.length; row_num++) {
						row = sheet.createRow(row_num);
						for (cell_num = 0; cell_num < data[row_num -1].length; cell_num++) {
							row.createCell(cell_num).setCellValue("" +data[row_num -1][cell_num]);
						}
					}
				}
				
		        response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition", "attachment; filename=" +file_name +ExcelManager.FileManager.EXTENSION);
		        
				OutputStream out;
				try {
					out = response.getOutputStream();
					workbook.write(out);
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	
	public static String create (String file_name, String sheet_name, String headers [], Object data [][]) {
		// I don't know why it still gives resources leak warning even tho i close it later on
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet sheet = workbook.createSheet(sheet_name);
		 
		int row_num = 0;
		Row row = sheet.createRow(row_num++);
		
		int cell_num;
		CellStyle header_style = workbook.createCellStyle();
		header_style.setFillForegroundColor(HEADER_COLOR);
		header_style.setFillPattern(HEADER_FILL);
		for (cell_num = 0; cell_num < headers.length; cell_num++) {
			Cell cell = row.createCell(cell_num);
			cell.setCellValue(headers[cell_num]);
			cell.setCellStyle(header_style);
		}
		
		if (data != null) {
			for (; row_num -1 < data.length; row_num++) {
				row = sheet.createRow(row_num);
				for (cell_num = 0; cell_num < data[row_num -1].length; cell_num++) {
					row.createCell(cell_num).setCellValue("" +data[row_num -1][cell_num]);
				}
			}
		}
		
		File file = FileManager.write(workbook, file_name);
		
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return File.separator +FileManager.PATH +file.getName();
	}
	
	public static List <String []> read (String file_name) {
		try {
			FileInputStream file = new FileInputStream(FileManager.getFile(file_name));
			
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			
			XSSFSheet sheet = workbook.getSheetAt(0);
			if (sheet == null) {
				workbook.close();
				return null;
			}
			
			int length = sheet.getPhysicalNumberOfRows();
			Row header = sheet.getRow(0);
			if (header == null) {
				workbook.close();
				return null;
			}
			int width = header.getPhysicalNumberOfCells();
			
			List <String []> rows = new ArrayList <> (); 
			
			for (int i = 1; i < length; i++) {
				if (sheet.getRow(i) == null) {
					continue;
				}
				String [] row = new String [width];
				boolean isEmpty = true;
				
				for (int j = 0; j < width; j++) {
					Cell cell = sheet.getRow(i).getCell(j);
					
					if (cell != null) {
						switch (cell.getCellType()) {
							case NUMERIC:
								isEmpty = false;
								double value_d = cell.getNumericCellValue();
								long value_l = new BigDecimal(value_d).longValue();
								if (value_d == value_l) {
									row [j] = "" +value_l;
								} else {
									row [j] = "" +value_d;
								}
								break;
							case STRING:
								isEmpty = false;
								row [j] = cell.getStringCellValue(); //What does getRichStringCellValue do?
								break;
							case FORMULA: // Only recently added the formula one. Not the proper way to do it ofc! 27_03_2023
								switch (cell.getCachedFormulaResultType()) {
						        case NUMERIC:
						        	isEmpty = false;
						        	double value_d2 = cell.getNumericCellValue();
									long value_l2 = new BigDecimal(value_d2).longValue();
									if (value_d2 == value_l2) {
										row [j] = "" +value_l2;
									} else {
										row [j] = "" +value_d2;
									}
									break;
						        case STRING:
						        	isEmpty = false;
						        	row [j] = cell.getStringCellValue();
						        	break;
						        default:
						        	break;
						    }
								break;
							default:
								break;
						}
					} 
				}
				
				if (!isEmpty) {
					rows.add(row);
				}
			}
			
			file.close();
			workbook.close();
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static class FileManager {

		// Was like so
//		public static final String PATH = "WEB-INF/excels" +File.separator;
		// Turned it to this
		public static final String PATH = "WEB-INF" + File.separator + "excels" +File.separator;
		public static final String EXTENSION = ".xlsx";
		
		public static synchronized boolean notExists (String file_name) {
			return ! new File (InitListener.getRootPath() +PATH +file_name +EXTENSION).exists();
		}
		
		public static File write (XSSFWorkbook workbook, String file_name) {
			File file = createFile(file_name);
			FileOutputStream out;
			try {
				out = new FileOutputStream(file);
				workbook.write(out);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return file;
		}
		
		public static File getFile (String file_name) {
			return new File (InitListener.getRootPath() +PATH +file_name +EXTENSION);
		}
		
		public synchronized static File createFile (String file_name) {
			final String FULL_PATH = InitListener.getRootPath() +PATH;
			File file = new File (FULL_PATH +file_name +EXTENSION);
			try {
				if (file.createNewFile()) {
					return file;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < Integer.MAX_VALUE ;i++) {
				file = new File (FULL_PATH +file_name +"_" +i +EXTENSION);
				try {
					if (file.createNewFile()) {
						return file;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		public static boolean deleteFile(String file_name) {
			return getFile(file_name).delete();
		}
		
	}
	 
}
