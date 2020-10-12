package src;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelReport {
	private WritableCellFormat shapka;
	private WritableCellFormat firmadres,firmadres2;
	private String inputFile = null;
	ResultSet rs = null;
	
	ExcelReport(ResultSet rs){
		this.rs=rs;
	}
	
	public void setOutputFile(String inputFile) {
		this.inputFile = "c:/Smsmanager/docs/" + inputFile + "/" + inputFile + "_отчет.xls";;
	}

	public void write() throws IOException, WriteException {
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Отчет по смс", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		defoltResizeColumns(excelSheet);
		createLabel(excelSheet);
		 
		workbook.write();
		workbook.close();
	}

	private void createLabel(WritableSheet sheet)
			throws WriteException {

		WritableFont firmadresfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
		WritableFont texttable = new WritableFont(WritableFont.ARIAL, 10);
		
		shapka = new WritableCellFormat(firmadresfont);
		shapka.setAlignment(Alignment.CENTRE);
		addCaption(sheet, 0, 0, "Код врача",shapka);
		addCaption(sheet, 1, 0, "Сообщение",shapka);
		addCaption(sheet, 2, 0, "Телефон",shapka);
		addCaption(sheet, 3, 0, "Дата отправки",shapka);
		addCaption(sheet, 4, 0, "Количество смс",shapka);
		
		firmadres= new WritableCellFormat(texttable);
		firmadres2= new WritableCellFormat(texttable);
		firmadres2.setAlignment(Alignment.CENTRE);
		
		printColums(sheet);
		
	}
	
	private void printColums(WritableSheet sheet) throws WriteException{
		try {
			if(rs==null){
				return;
			}
			
			int i=1;
			
			while (rs.next()){
				for(int j=0;j<5;j++){
					if(j<2){
						addCaption(sheet, j, i, rs.getString(j+1),firmadres);
					}else{
						addCaption(sheet, j, i, rs.getString(j+1),firmadres2);
					}
				}
			i++;
			
			}
			
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null,
					"Ошибка записи отчета смс!",
					"Error",JOptionPane.ERROR_MESSAGE);	
		}
	}
	
	private void defoltResizeColumns(WritableSheet sheet) throws WriteException{
		
		sheet.setColumnView(0, 20);
		sheet.setColumnView(1, 90);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		
	}
	
	private void addCaption(WritableSheet sheet, int column, int row, String s,WritableCellFormat formattext)
			throws RowsExceededException, WriteException {
		Label label;
		
		label = new Label(column, row, s, formattext);
		sheet.addCell(label);
		
	}

}
