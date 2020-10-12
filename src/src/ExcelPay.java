package src;

import java.io.File;
import java.io.IOException;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.format.Border;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.write.WritableImage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class ExcelPay {
	
	private WritableCellFormat shapka,pust1,pust2,pust3,pust4;
	private WritableCellFormat table,tablep1,tablep2,tablep3,tablep4,nomer,nameklinic,table2,sumname,itog,otvet,firmadres,itogo1,itogo2;
	private String inputFile = null;
	private String sum = null, nclinic=null, payNumb = null;

	
	ExcelPay(String sum, String paynumber, String client){
		this.sum=sum;
		this.payNumb=paynumber;
		this.nclinic=client;
		
	}
	
	public void setOutputFile(String inputFile) {
		this.inputFile = "c:/Smsmanager/docs/" + inputFile + "/" + inputFile + "_счет.xls";
	}

	private void setinputImage(WritableSheet sheet) {
		File f=new File("c:/Smsmanager/src/Podpis.png");
		
		WritableImage wi = new WritableImage(1, 26, 8, 15,f);  
		sheet.addImage(wi);
	}


	public void write() throws IOException, WriteException {
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Счет за блок смс", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		defoltResizeColumns(excelSheet);
		
		createLabel(excelSheet);
		
		setinputImage(excelSheet);

		Helper.openDir(nclinic);		
		
		workbook.write();
		workbook.close();
	}

	private void createLabel(WritableSheet sheet)
			throws WriteException {
		
		WritableFont firmname = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true,UnderlineStyle.SINGLE);
		WritableFont firmadresfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
		WritableFont texttable = new WritableFont(WritableFont.ARIAL, 10);
		WritableFont account = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false);
		WritableFont note = new WritableFont(WritableFont.ARIAL, 8);
		
		shapka = new WritableCellFormat(firmname);
		addCaption(sheet, 1, 0, "ООО \"Сентор Софтвер\"",shapka);
		firmadres= new WritableCellFormat(firmadresfont);
		
		addCaption(sheet, 1, 2, "115054, г. Москва, ул.Валовая, д.14, строение 8,   тел.: (495) 665-00-47",firmadres);
		addCaption(sheet, 1, 4, "                                                Образец заполнения платежного поручения",firmadres);
		
		table = new WritableCellFormat(texttable);
		table.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		addCaption(sheet, 1, 5, "ИНН 7721531850",table);
		addCaption(sheet, 4, 5, "КПП 770501001",table);
		
		pust1=new WritableCellFormat(texttable);
		pust1.setBorder(Border.TOP, BorderLineStyle.THIN);
		pust1.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		addCaption(sheet, 6, 5, " ",pust1);
		addCaption(sheet, 7, 5, " ",pust1);
		
		pust2=new WritableCellFormat(texttable);
		pust2.setBorder(Border.LEFT, BorderLineStyle.THIN);
		pust2.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		addCaption(sheet, 6, 6, " ",pust2);
		
		pust3=new WritableCellFormat(texttable);
		pust3.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		addCaption(sheet, 7, 7, "40702810500220003086",pust3);
		addCaption(sheet, 7, 6, "",pust3);
		
		pust4=new WritableCellFormat(texttable);
		pust4.setBorder(Border.RIGHT, BorderLineStyle.THIN);
		pust4.setBorder(Border.LEFT, BorderLineStyle.THIN);
		addCaption(sheet, 6, 7, "Сч. №",pust4);
				
		
		tablep1=new WritableCellFormat(texttable);
		tablep1.setBorder(Border.LEFT, BorderLineStyle.THIN);
		addCaption(sheet, 1, 6, "Получатель",tablep1);		
		
		tablep2=new WritableCellFormat(texttable);
		tablep2.setBorder(Border.LEFT, BorderLineStyle.THIN);
		tablep2.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		addCaption(sheet, 1, 7, "ООО \"Сентор Софтвер\"",tablep2);
		
		//addCaption(sheet, 6, 7, "Сч. №",table);
		//addCaption(sheet, 7, 7, "40702810500220003086",table);
		
		tablep3=new WritableCellFormat(texttable);
		tablep3.setBorder(Border.LEFT, BorderLineStyle.THIN);
		addCaption(sheet, 1, 8, "Банк получателя",tablep3);
		
		addCaption(sheet, 6, 8, "БИК",table);
		addCaption(sheet, 7, 8, "044525787",table);
		
		tablep4=new WritableCellFormat(texttable);
		tablep4.setBorder(Border.LEFT, BorderLineStyle.THIN);
		tablep4.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		addCaption(sheet, 1, 9, "ОАО \"УРАЛСИБ\" г.Москва",tablep4);
		addCaption(sheet, 6, 9, "Сч. №",table);
		addCaption(sheet, 7, 9, "30101810100000000787",table);
		//SheetSettings sh=new SheetSettings();
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date dl = new Date();
        String date=dateFormat.format(dl);
        
		nomer = new WritableCellFormat(account);
		//addCaption(sheet, 1, 11, "СЧЕТ №",nomer);
		addCaption(sheet, 3, 11, payNumb+"см-СМС",nomer);
		addCaption(sheet, 4, 11, "от",nomer);
		addCaption(sheet, 5, 11, date,nomer);
		
		nameklinic = new WritableCellFormat(firmadres);
		
		addCaption(sheet, 1, 14, "Заказчик: ",nameklinic);
		addCaption(sheet, 3, 14, "ООО \""+nclinic+"\"                                                            ",nameklinic);
		addCaption(sheet, 1, 15, "Плательщик: ",nameklinic);
		addCaption(sheet, 3, 15, "ООО \""+nclinic+"\"                                                           ",nameklinic);
		
		table2= new WritableCellFormat(texttable);
		table2.setBorder(Border.ALL, BorderLineStyle.THIN);
		table2.setAlignment(Alignment.CENTRE);
		
		addCaption(sheet, 1, 17, "№",table2);
		addCaption(sheet, 2, 17, "Наименование товара",table2);
		//addCaption(sheet, 5, 17, "Единица измерения",table2);
		addCaption(sheet, 6, 17, "Кол-во",table2);
		addCaption(sheet, 7, 17, "Цена",table2);
		addCaption(sheet, 8, 17, "Сумма",table2);
		addCaption(sheet, 1, 18, "1",table2);
		addCaption(sheet, 2, 18, "SMS-блок 10000 SMS",table2);
		//addCaption(sheet, 5, 18, " ",table2);
		addCaption(sheet, 6, 18, "1",table2);
		addCaption(sheet, 7, 18, sum,table2);
		addCaption(sheet, 8, 18, sum,table2);
		
		itogo1= new WritableCellFormat(firmadres);
		itogo1.setAlignment(Alignment.RIGHT);
		addCaption(sheet, 7, 19, "Итого:",itogo1);
		addCaption(sheet, 7, 20, "Без налога (НДС).",itogo1);
		addCaption(sheet, 7, 21, "Всего к оплате:",itogo1);
		
		itogo2= new WritableCellFormat(firmadres);
		itogo2.setBorder(Border.ALL, BorderLineStyle.THIN);
		//itogo2.setVerticalAlignment(VerticalAlignment.TOP);
		itogo2.setAlignment(Alignment.CENTRE);
		
		
		addCaption(sheet, 8, 19, sum,itogo2);
		addCaption(sheet, 8, 20, "- ",itogo2);
		addCaption(sheet, 8, 21, sum,itogo2);
				
		sumname= new WritableCellFormat(texttable);
		addCaption(sheet, 1, 23, "Всего наименований 1, на сумму  ",sumname);
		itog= new WritableCellFormat(firmadres);
		
		addCaption(sheet, 1, 24, "Девять тысяч девятьсот девяносто руб. 00 коп.",itog);
		
		otvet= new WritableCellFormat(note);
		addCaption(sheet, 1, 43, "Отв.Сомова Марина",otvet);
		addCaption(sheet, 1, 44, "Тел.+7(915) 353-71-33",otvet);
		addCaption(sheet, 1, 45, "Somova@d4w.ru",otvet);
		
	}
	
	private void defoltResizeColumns(WritableSheet sheet) throws WriteException{
		
		sheet.setColumnView(0, 2);
		sheet.setColumnView(1, 6);
		sheet.setColumnView(2, 5);
		sheet.setColumnView(3, 26);
		sheet.setColumnView(4, 7);
		sheet.setColumnView(5, 9);
		sheet.setColumnView(6, 9);
		sheet.setColumnView(7, 9);
		sheet.setColumnView(8, 14);
				
		sheet.mergeCells(1, 5, 3, 5);
		sheet.mergeCells(1, 6, 5, 6);
		sheet.mergeCells(1, 7, 5, 7);
		sheet.mergeCells(4, 5, 5, 5);
		
		sheet.mergeCells(7, 5, 8, 5);
		sheet.mergeCells(7, 6, 8, 6);
		sheet.mergeCells(7, 8, 8, 8);
		sheet.mergeCells(7, 9, 8, 9);
		sheet.mergeCells(7, 7, 8, 7);
		
		sheet.mergeCells(1, 8, 5, 8);
		sheet.mergeCells(1, 9, 5, 9);
		sheet.mergeCells(1, 11, 2, 11);
		sheet.mergeCells(1, 14, 2, 14);
		sheet.mergeCells(1, 15, 2, 15);
		sheet.mergeCells(2, 17, 5, 17);
		sheet.mergeCells(2, 18, 5, 18);
		
	}
	
	private void addCaption(WritableSheet sheet, int column, int row, String s,WritableCellFormat formattext)
			throws RowsExceededException, WriteException {
		Label label;
		
		label = new Label(column, row, s, formattext);
		sheet.addCell(label);
		
	}

}
