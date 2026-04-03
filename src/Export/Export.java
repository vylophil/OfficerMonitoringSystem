/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Export;
/**
 *
 * @author mariane
 */
import java.io.FileOutputStream;
import java.sql.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import DataBaseConnection.DBConn;
import java.io.File;
import javax.swing.filechooser.FileSystemView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Export {

    public static void exportAllData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Excel File");
        fileChooser.setSelectedFile(new File("Election_Records.xlsx"));
        
        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File fileToSave = fileChooser.getSelectedFile();
        if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")) {
            fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".xlsx");
        }

        try (Connection conn = DBConn.getConnection();
             Workbook workbook = new XSSFWorkbook()) {

            // Create date style for proper Excel date formatting
            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
            
            CellStyle headerStyle = createHeaderStyle(workbook);
            
            // Export tables with proper date handling
            exportOfficerTable(conn, workbook, headerStyle, dateStyle);
            exportPositionTable(conn, workbook, headerStyle);
            exportElectedToTable(conn, workbook, headerStyle, dateStyle);
            exportElectionHistory(conn, workbook, headerStyle, dateStyle); // NEW: Added election history

            try (FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(null, 
                "Export successful!\nSaved as: " + fileToSave.getAbsolutePath());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error exporting data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Election history with officer and position details
    private static void exportElectionHistory(Connection conn, Workbook workbook, 
                                            CellStyle headerStyle, CellStyle dateStyle) throws SQLException {
        String sql = """
            SELECT 
                e.electID as 'Election_ID',
                CONCAT(o.Fname, ' ', o.MI, ' ', o.Lname) as 'Officer_Name',
                o.gender as 'Gender',
                o.Phone_num as 'Phone',
                o.Bdate as 'Birth_Date',
                p.name as 'Position',
                p.description as 'Position_Description',
                e.department as 'Department',
                e.date_from as 'Elected_From',
                e.date_to as 'Elected_To',
                DATEDIFF(e.date_to, e.date_from) as 'Days_in_Office'
            FROM elected_to e
            JOIN officer o ON e.officerID = o.officerID
            JOIN position p ON e.positionID = p.positionID
            ORDER BY e.date_from DESC
            """;
        
        Sheet sheet = workbook.createSheet("Election History");

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                Cell cell = headerRow.createCell(i - 1);
                cell.setCellValue(metaData.getColumnLabel(i));
                cell.setCellStyle(headerStyle);
            }

            // Create data rows
            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Cell cell = row.createCell(i - 1);
                    Object value = rs.getObject(i);
                    
                    // Special handling for date columns
                    String columnName = metaData.getColumnLabel(i);
                    if (columnName.equals("Elected_From") || columnName.equals("Elected_To") || columnName.equals("Birth_Date")) {
                        java.sql.Date sqlDate = rs.getDate(i);
                        if (sqlDate != null) {
                            cell.setCellValue(sqlDate);
                            cell.setCellStyle(dateStyle);
                        } else {
                            cell.setCellValue("");
                        }
                    } else {
                        setCellValue(cell, value);
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }
        }
    }

    // Enhanced Officer table export
    private static void exportOfficerTable(Connection conn, Workbook workbook, 
                                         CellStyle headerStyle, CellStyle dateStyle) throws SQLException {
        String sql = "SELECT officerID, Fname, MI, Lname, gender, Phone_num, Bdate FROM officer";
        Sheet sheet = workbook.createSheet("Officers");

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Officer ID", "First Name", "Middle Initial", "Last Name", "Gender", "Phone Number", "Birth Date"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Create data rows
            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                
                // Handle regular columns
                for (int i = 1; i <= 6; i++) {
                    Cell cell = row.createCell(i - 1);
                    Object value = rs.getObject(i);
                    setCellValue(cell, value);
                }
                
                // Special handling for birthDate
                Cell dateCell = row.createCell(6);
                java.sql.Date Bdate = rs.getDate("Bdate");
                if (Bdate != null) {
                    dateCell.setCellValue(Bdate);
                    dateCell.setCellStyle(dateStyle);
                } else {
                    dateCell.setCellValue("");
                }
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
    }

    // Enhanced Position table export
    private static void exportPositionTable(Connection conn, Workbook workbook, CellStyle headerStyle) throws SQLException {
        String sql = "SELECT positionID, name, description FROM position";
        Sheet sheet = workbook.createSheet("Positions");

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Position ID", "Position Name", "Description"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Create data rows
            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= 3; i++) {
                    Cell cell = row.createCell(i - 1);
                    Object value = rs.getObject(i);
                    setCellValue(cell, value);
                }
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
    }

    // Enhanced Elected_To table export
    private static void exportElectedToTable(Connection conn, Workbook workbook, 
                                           CellStyle headerStyle, CellStyle dateStyle) throws SQLException {
        String sql = "SELECT electID, officerID, positionID, department, date_from, date_to FROM elected_to";
        Sheet sheet = workbook.createSheet("Election Records");

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Election ID", "Officer ID", "Position ID", "Department", "Date From", "Date To"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Create data rows
            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                
                // Handle first 4 columns (non-date)
                for (int i = 1; i <= 4; i++) {
                    Cell cell = row.createCell(i - 1);
                    Object value = rs.getObject(i);
                    setCellValue(cell, value);
                }
                
                // Handle date_from
                Cell dateFromCell = row.createCell(4);
                java.sql.Date dateFrom = rs.getDate("date_from");
                if (dateFrom != null) {
                    dateFromCell.setCellValue(dateFrom);
                    dateFromCell.setCellStyle(dateStyle);
                } else {
                    dateFromCell.setCellValue("");
                }
                
                // Handle date_to
                Cell dateToCell = row.createCell(5);
                java.sql.Date dateTo = rs.getDate("date_to");
                if (dateTo != null) {
                    dateToCell.setCellValue(dateTo);
                    dateToCell.setCellStyle(dateStyle);
                } else {
                    dateToCell.setCellValue("");
                }
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
    }

    // Export only election history 
    public static void exportElectionHistoryOnly() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Election History");
        fileChooser.setSelectedFile(new File("Election_History.xlsx"));
        
        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File fileToSave = fileChooser.getSelectedFile();
        if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")) {
            fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".xlsx");
        }

        try (Connection conn = DBConn.getConnection();
             Workbook workbook = new XSSFWorkbook()) {

            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
            
            CellStyle headerStyle = createHeaderStyle(workbook);
            
            // Export only the comprehensive election history
            exportElectionHistory(conn, workbook, headerStyle, dateStyle);

            try (FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(null, 
                "Election History exported successfully!\nSaved as: " + fileToSave.getAbsolutePath());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error exporting election history: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static void setCellValue(Cell cell, Object value) {
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue(value.toString());
        }
    }
}