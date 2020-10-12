package src;
public class Client {

		private String name=null;
		private String kod=null;
		private String datepay=null;
		private String mail=null;
		private String komment=null;
		private int vsegosmssent=0;
		private int vsegopaysms=0;
		private int monthsmssent=0;
		private int sred_monthsmssent=0;
		private boolean status=true;
		private String sender;
		private boolean ovd=true;

		public Client() {
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
		
		public void setKod(String kod) {
			this.kod = kod.toLowerCase();
		}

		public String getKod() {
			return kod;
		}
		
		public void setVsegosmssent(int vsegosmssent) {
			this.vsegosmssent = vsegosmssent;
		}

		public int getVsegosmssent() {
			return vsegosmssent;
		}
		
		public void setMonthsmssent(int monthsmssent) {
			this.monthsmssent = monthsmssent;
		}

		public int getMonthsmssent() {

			return monthsmssent;

		}
		
		public void setSredSmssent(int sred_monthsmssent) {
			this.sred_monthsmssent = sred_monthsmssent;
		}
		
		public int getSredSmssent() {
			return sred_monthsmssent;
		}
		
		public void setDatepay(String datepay) {
			this.datepay = datepay;
		}
		
		public String getDatepay() {
			return datepay;
		}
		
		public void setMail(String mail) {
			this.mail = mail;
		}
		
		public String getMail() {
			return mail;
		}
		
		public void setComment(String komment) {
			this.komment = komment;
		}
		
		public String getComment() {
			return komment;
		}
		
		public void setStatus(boolean status) {
			this.status = status;
		}
		
		public boolean getStatus() {
			return status;
		}
		
		public void setVsegopaysms(int vsegopaysms) {
			this.vsegopaysms = vsegopaysms;
		}
		
		public int getVsegopaysms() {
			return vsegopaysms;
		}

		public String getSender() {
			return sender;
		}

		public void setSender(String sender) {
			this.sender = sender;
		}

		public boolean getOvd() {
			return ovd;
		}

		public void setOvd(boolean ovd) {
			this.ovd = ovd;
		}
		
	}