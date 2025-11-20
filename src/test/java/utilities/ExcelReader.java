package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * ExcelReader utility with read helpers and methods to mark cells red/green.
 * <p>
 * Requirements: Apache POI (poi and poi-ooxml) on the classpath.
 */
public class ExcelReader {

    // Use workbook provided by caller (does NOT open/close the workbook)
    private Sheet getSheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);
        }
        return sheet;
    }

    private Map<String, Integer> getColumnHeaderMap(Sheet sheet) {
        Map<String, Integer> headerMap = new LinkedHashMap<>();
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return headerMap;

        for (Cell cell : headerRow) {
            if (cell == null) continue;
            String header = cell.getCellType() == CellType.STRING ? cell.getStringCellValue().trim() : cell.toString().trim();
            headerMap.put(header, cell.getColumnIndex());
        }
        return headerMap;
    }

    /**
     * ✔ Get a single cell value using column header & row number.
     */
    public String getCellValue(String sheetName, String columnHeader, int rowNumber, Path filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath.toFile());
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = getSheet(workbook, sheetName);
            Map<String, Integer> headerMap = getColumnHeaderMap(sheet);

            Integer colIndex = headerMap.get(columnHeader);
            if (colIndex == null) {
                throw new RuntimeException("Column header not found: " + columnHeader);
            }

            Row row = sheet.getRow(rowNumber);
            if (row == null) return "";

            Cell cell = row.getCell(colIndex);
            return cell != null ? cell.toString() : "";
        }
    }

    //Returns entire row as Map<Header, Value>
    public Map<String, String> getRowData(String sheetName, int rowNumber, Path filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath.toFile());
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = getSheet(workbook, sheetName);
            Map<String, Integer> headerMap = getColumnHeaderMap(sheet);

            Row row = sheet.getRow(rowNumber);
            Map<String, String> rowData = new LinkedHashMap<>();

            for (String header : headerMap.keySet()) {
                int colIndex = headerMap.get(header);
                Cell cell = (row != null) ? row.getCell(colIndex) : null;
                rowData.put(header, cell != null ? cell.toString() : "");
            }
            return rowData;
        }
    }

    //Returns list of values in a specific column.
    public List<String> getColumnData(String sheetName, String columnHeader, Path filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath.toFile());
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = getSheet(workbook, sheetName);
            Map<String, Integer> headerMap = getColumnHeaderMap(sheet);

            Integer colIndex = headerMap.get(columnHeader);
            if (colIndex == null) {
                throw new RuntimeException("Column header not found: " + columnHeader);
            }

            List<String> values = new ArrayList<>();
            int lastRow = sheet.getLastRowNum();

            for (int i = 1; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    values.add("");
                    continue;
                }

                Cell cell = row.getCell(colIndex);
                values.add(cell != null ? cell.toString() : "");
            }
            return values;
        }
    }

    /**
     * MARK CELL RED
     * Sets the cell's background fill to solid RED and writes changes back to the file.
     * If the row or cell doesn't exist it will be created.
     *
     * @param sheetName    - name of the sheet
     * @param columnHeader - header text for the column (exact match)
     * @param rowNumber    - row index (0-based). NOTE: header row is expected at row 0.
     * @throws IOException if file IO fails
     */
    public void markCellRed(String sheetName, String columnHeader, int rowNumber, Path filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath.toFile());
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = getSheet(workbook, sheetName);
            Map<String, Integer> headerMap = getColumnHeaderMap(sheet);

            Integer colIndex = headerMap.get(columnHeader);
            if (colIndex == null) {
                throw new RuntimeException("Column header not found: " + columnHeader);
            }

            Row row = sheet.getRow(rowNumber);
            if (row == null) row = sheet.createRow(rowNumber);

            Cell cell = row.getCell(colIndex);
            if (cell == null) cell = row.createCell(colIndex);

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            cell.setCellStyle(style);

            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                workbook.write(fos);
                fos.flush();
            }
        }
    }

    /**
     * MARK CELL GREEN
     * Sets the cell's background fill to solid GREEN and writes changes back to the file.
     */
    public void markCellGreen(String sheetName, String columnHeader, int rowNumber, Path filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath.toFile());
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = getSheet(workbook, sheetName);
            Map<String, Integer> headerMap = getColumnHeaderMap(sheet);

            Integer colIndex = headerMap.get(columnHeader);
            if (colIndex == null) {
                throw new RuntimeException("Column header not found: " + columnHeader);
            }

            Row row = sheet.getRow(rowNumber);
            if (row == null) row = sheet.createRow(rowNumber);

            Cell cell = row.getCell(colIndex);
            if (cell == null) cell = row.createCell(colIndex);

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            cell.setCellStyle(style);

            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                workbook.write(fos);
                fos.flush();
            }
        }
    }

    public int getTotalRows(String sheetName, Path filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath.toFile());
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            // If you want ALL rows (including empty ones between data)
            int lastRowIndex = sheet.getLastRowNum(); // 0-based

            // If you want only rows that contain data:
            int physicalRows = sheet.getPhysicalNumberOfRows(); // counts non-empty rows

            return lastRowIndex + 1;  // convert 0-based to count
        }
    }

    /**
     * Copies an existing Excel file to a new file.
     * Creates the target directory automatically if missing.
     *
     * @param source source Excel file path
     * @param target new Excel file path to create
     */
    public static void copyExcelFile(Path source, Path target) throws IOException {
        // Ensure target directory exists
        if (target.getParent() != null) {
            Files.createDirectories(target.getParent());
        }

        try (
                FileInputStream fis = new FileInputStream(source.toFile());
                Workbook workbook = new XSSFWorkbook(fis);
                FileOutputStream fos = new FileOutputStream(target.toFile())
        ) {
            workbook.write(fos);
        }
    }
}
