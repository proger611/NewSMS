package src;

import java.io.File;
import java.io.IOException;

import javax.swing.JTable;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Statistic {

	private WritableCellFormat shapka;
	private WritableCellFormat tablep1;
	private String inputFile;
	private JTable table = null;

	public Statistic(JTable table) {
		this.table = table;
	}

	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void write() throws IOException, WriteException {
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Статистика", 0);

		WritableSheet excelSheet = workbook.getSheet(0);
		defoltResizeColumns(excelSheet);
		createLabel(excelSheet);

		workbook.write();
		workbook.close();
	}

	private void createLabel(WritableSheet sheet) throws WriteException {

		WritableFont firmname = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD, true);

		WritableFont texttable = new WritableFont(WritableFont.ARIAL, 10);

		// timesBoldUnderline.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
		shapka = new WritableCellFormat(firmname);
		shapka.setAlignment(Alignment.CENTRE);
		shapka.setBorder(Border.ALL, BorderLineStyle.THIN);

		addCaption(sheet, 0, 0, "O", shapka);
		addCaption(sheet, 1, 0, "С", shapka);

		addCaption(sheet, 2, 0, "Клиника", shapka);
		addCaption(sheet, 3, 0, "Код", shapka);
		addCaption(sheet, 4, 0, "Имя отправ", shapka);
		addCaption(sheet, 5, 0, "Всего отпр смс", shapka);
		addCaption(sheet, 6, 0, "Всего оплач смс", shapka);
		addCaption(sheet, 7, 0, "Отпрв/мес смс", shapka);
		addCaption(sheet, 8, 0, "Сред/мес смс", shapka);
		addCaption(sheet, 9, 0, "Дата счета", shapka);
		addCaption(sheet, 10, 0, "Em@il", shapka);
		addCaption(sheet, 11, 0, "Коммент", shapka);

		tablep1 = new WritableCellFormat(texttable);
		tablep1.setBorder(Border.ALL, BorderLineStyle.THIN);

		int row = table.getRowCount();

		for (int j = 0; j < row; j++) {
			for (int i = 0; i < 12; i++) {
				Object obj = table.getValueAt(j, i);
				addCaption(sheet, i, ++j, obj instanceof String ? (String) obj
						: String.valueOf(obj), tablep1);
				j--;
			}
		}
	}

	private void defoltResizeColumns(WritableSheet sheet) throws WriteException {

		sheet.setColumnView(0, 10);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		sheet.setColumnView(6, 20);
		sheet.setColumnView(7, 20);
		sheet.setColumnView(8, 20);
		sheet.setColumnView(9, 30);
		sheet.setColumnView(10, 80);

		// sheet.mergeCells(1, 5, 3, 5);
		// sheet.mergeCells(1, 6, 5, 6);
		// sheet.mergeCells(1, 7, 5, 7);
		// sheet.mergeCells(4, 5, 5, 5);

	}

	private void addCaption(WritableSheet sheet, int column, int row, String s,
			WritableCellFormat formattext) throws RowsExceededException,
			WriteException {
		Label label;
		// CellFormat cformat=new CellFormat();

		label = new Label(column, row, s, formattext);
		// label.;
		sheet.addCell(label);

		// sheet.
	}

}