package src;

public class SmsTimer {
	private static long START=0;
	private static long STOP=0;
	
	
	public static void START(){
		START=System.currentTimeMillis();
	}
	
	public static void STOP(){
		STOP=System.currentTimeMillis();
		System.out.println(STOP-START);
	}
	
	SmsTimer(){
	}
}
