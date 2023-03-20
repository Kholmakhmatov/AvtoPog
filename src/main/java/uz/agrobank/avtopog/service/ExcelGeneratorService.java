package uz.agrobank.avtopog.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uz.agrobank.avtopog.model.LdSvGateAdd;
import uz.agrobank.avtopog.response.ResponseDto;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelGeneratorService {
    private final List<ResponseDto<LdSvGateAdd>> responseDtoList;
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGeneratorService(List<ResponseDto<LdSvGateAdd>> responseDtoList) {
        this.responseDtoList = responseDtoList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("Add card");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "МФО", style);
        createCell(row, 1, "АНКЕТА", style);
        createCell(row, 2, "Карта раками", style);
        createCell(row, 3, "муддати", style);
        createCell(row, 4, "Status", style);
        createCell(row, 5, "Reason", style);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (ResponseDto<LdSvGateAdd> record : responseDtoList) {
            LdSvGateAdd ldSvGateAdd = record.getObj();
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, ldSvGateAdd.getBranch(), style);
            createCell(row, columnCount++, ldSvGateAdd.getId(), style);
            createCell(row, columnCount++, ldSvGateAdd.getCardNumber(), style);
            createCell(row, columnCount++, ldSvGateAdd.getExpiryDate(), style);
            createCell(row, columnCount++, record.getSuccess(), style);
            createCell(row, columnCount++, record.getMessage(), style);
        }
    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
