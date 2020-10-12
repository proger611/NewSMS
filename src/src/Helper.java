package src;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Helper {

	static Logger LOGGER = LoggerFactory.getLogger(Helper.class);

	public static int YEAR = 0;
	public static int MONTH = 1;
	public static int DAY = 2;

	public static int getDate(int d) {

		Calendar c = Calendar.getInstance();

		switch (d) {

		case 0:
			return c.get(Calendar.YEAR);

		case 1:
			return c.get(Calendar.MONTH) + 1;
		}

		return 0;
	}

	public static void createFolder(String name) {

		File myPath = new File("c:/Smsmanager/docs/", name);

		if (!myPath.exists()) {
			myPath.mkdir();
			LOGGER.info("Create folder c:/Smsmanager/docs/{}", name);
		}
	}

	public static String getDate(String frm) {
		DateFormat dateFormat = new SimpleDateFormat(frm);
		Date d = new Date();
		String date = dateFormat.format(d);
		return date;
	}

	public static String reverseDate(String dt, boolean b) {
		String[] sd = dt.split("/");
		
		if(b){
			int day = Integer.parseInt(sd[0]) + 1;
			sd[0] = String.valueOf(day);
		}
		String sdq = sd[2] + "-" + sd[1] + "-" + sd[0];
		return sdq;
	}

	public static void openDir(String folder) {
		Desktop desktop = null;
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
		}

		try {
			File f = new File("c:/Smsmanager/docs/" + folder);
			desktop.open(f);
		} catch (IOException e) {

		}

	}

	public static boolean isBlocked() {

		return true;
	}

}
