package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticStorage {

	Logger LOGGER = LoggerFactory.getLogger(MyTableModel.class);

	public static String NAMEPK = null;
	public static String IP = null;

	private static Connection con;
	private static Connection con242;
	private static StaticStorage instance;

	private StaticStorage() throws Exception {
		this.connection();
		this.connection242();
	}

	public static synchronized StaticStorage getInstance() throws Exception {
		if (instance == null) {
			instance = new StaticStorage();
		}
		return instance;
	}

	private void connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://192.168.0.241/smss";
			con = DriverManager.getConnection(url, "sms", "sms_#_sms");
		} catch (ClassNotFoundException e) {

			LOGGER.error("ClassNotFoundException: Ошибка соединения с базой данной!");

			JOptionPane
					.showMessageDialog(
							null,
							"ClassNotFoundException: Ошибка соединения с базой данной!",
							"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);

		} catch (SQLException e) {

			LOGGER.error("SQLException: Ошибка соединения с базой данной!");

			JOptionPane.showMessageDialog(null,
					"SQLException: Ошибка соединения с базой данной!", "Error",
					JOptionPane.ERROR_MESSAGE);

			System.exit(0);
		}

	}
	
	private void connection242() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://192.168.0.242/smss";
			con242 = DriverManager.getConnection(url, "sms", "sms_#_sms");
		} catch (ClassNotFoundException e) {
			
			LOGGER.error("ClassNotFoundException: Ошибка соединения с базой данной!");
			
			JOptionPane
			.showMessageDialog(
					null,
					"ClassNotFoundException: Ошибка соединения с базой данной!",
					"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			
		} catch (SQLException e) {
			
			LOGGER.error("SQLException: Ошибка соединения с базой данной!");
			
			JOptionPane.showMessageDialog(null,
					"SQLException: Ошибка соединения с базой данной!", "Error",
					JOptionPane.ERROR_MESSAGE);
			
			System.exit(0);
		}
		
	}

	public Collection<Client> getAllClients() throws SQLException {
		Collection<Client> clients = new ArrayList<Client>();

		Statement stmt = null;
		PreparedStatement prstmt = null;
		ResultSet rs = null;

		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT user_id kod, user_name name, user_email email FROM sms_users where user_name!='' ORDER BY user_name");
			
			while (rs.next()) {
					
				Client st = new Client();
				
				String kod = rs.getString("kod");
				boolean sts = clinicIsBlocked(kod);
				st.setKod(nameSplit(kod, sts));
				
				st.setName(rs.getString("name"));
				st.setMail(rs.getString("email"));
				st.setStatus(!sts);	
				
				clients.add(st);
			}

			//rs = stmt.executeQuery("SELECT user_id kod, sum(sms_count) sms_sent FROM message group by user_id ");
			rs = stmt.executeQuery("select h.User_Id kod, (h.count + IFNULL(s.count, 0)) sms_sent from (select User_Id, sum(sms_count) count from message where User_Id is not null " +
					"GROUP BY User_Id) h left JOIN balanse_archive s on h.User_Id = s.User_Id");

			while (rs.next()) {
				for (int i = 0; i < clients.size(); i++) {
					
					if ((((ArrayList<Client>) clients).get(i).getKod()).equals(rs.getString("kod")==null ? null : rs.getString("kod").toLowerCase())) {
						
						((ArrayList<Client>) clients).get(i).setVsegosmssent(
								rs.getInt("sms_sent"));
						break;
					}
				}
			}

			prstmt = con
					.prepareStatement("SELECT user_id kod, sum(sms_count) countm FROM message where month(message_time_received)=? and year(message_time_received)=? group by user_id ");
			prstmt.setInt(1, Helper.getDate(Helper.MONTH));
			prstmt.setInt(2, Helper.getDate(Helper.YEAR));
			rs = prstmt.executeQuery();

			while (rs.next()) {
				for (int i = 0; i < clients.size(); i++) {
					if ((((ArrayList<Client>) clients).get(i).getKod())
							.equals(rs.getString("kod")==null ? null : rs.getString("kod").toLowerCase())) {
						((ArrayList<Client>) clients).get(i).setMonthsmssent(
								rs.getInt("countm"));
						break;
					}
				}
			}

			prstmt = con.prepareStatement("SELECT user_id kod, sum(sms_count) countm FROM message where year(message_time_received)=? group by user_id ");
			prstmt.setInt(1, Helper.getDate(Helper.YEAR));
			rs= prstmt.executeQuery();

			while (rs.next()) {
				for (int i = 0; i < clients.size(); i++) {
					if ((((ArrayList<Client>) clients).get(i).getKod())
							.equals(rs.getString("kod")==null ? null : rs.getString("kod").toLowerCase())) {
						((ArrayList<Client>) clients).get(i).setSredSmssent(
								rs.getInt("countm")
										/ Helper.getDate(Helper.MONTH));
						break;
					}
				}
			}

			rs = stmt.executeQuery("SELECT id_clinic id, sms_pay pay,  conbuilder sender, data_pay dt, eqconbuilder comment, ovdt ovd FROM smsmanager ORDER BY id_clinic");

			while (rs.next()) {
				for (int i = 0; i < clients.size(); i++) {
					String kod = rs.getString("id")==null ? "" : rs.getString("id").toLowerCase();
					
					boolean sts = clinicIsBlocked(kod);
					kod = nameSplit(kod, sts);
					
					if ((((ArrayList<Client>) clients).get(i).getKod())
							.equals(kod)) {
						((ArrayList<Client>) clients).get(i).setVsegopaysms(
								rs.getInt("pay"));
						((ArrayList<Client>) clients).get(i).setDatepay(
								rs.getString("dt"));
						((ArrayList<Client>) clients).get(i).setComment(
								rs.getString("comment"));
						((ArrayList<Client>) clients).get(i).setSender(rs.getString("sender"));
						((ArrayList<Client>) clients).get(i).setOvd(rs.getInt("ovd")==1 ? true : false);
						break;
					}
				}
			}

		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (prstmt != null) {
				stmt.close();
			}
		}

		return clients;
	}
	
	public void updateStatus(String kod, String nkod) throws SQLException{
		
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("UPDATE sms_users SET user_id = ? WHERE user_id = ?");
			stmt.setString(1, kod);
			stmt.setString(2, nkod);
			stmt.execute();
			
			stmt = con.prepareStatement("UPDATE smsmanager SET id_clinic = ? WHERE id_clinic = ?");
			stmt.setString(1, kod);
			stmt.setString(2, nkod);
			stmt.execute();
			
			stmt = con242.prepareStatement("UPDATE sms_users SET user_id = ? WHERE user_id = ?");
			stmt.setString(1, kod);
			stmt.setString(2, nkod);
			stmt.execute();

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
	}
	
    public void updateSender(String kod, String sender) throws SQLException{
		
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("UPDATE smsmanager SET conbuilder = ? WHERE id_clinic = ?");
			stmt.setString(1, sender);
			stmt.setString(2, kod);
			stmt.execute();

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		
	}
	
	
	public void insertClient(String kod, String name, String mail)
			throws SQLException {
		PreparedStatement stmt = null;

		try {
			stmt = con
					.prepareStatement("insert into sms_users VALUES (?, ?, ?,'ZANZARA')");
			stmt.setString(1, kod);
			stmt.setString(2, name);
			stmt.setString(3, mail);
			stmt.execute();

			stmt = con
					.prepareStatement("insert into smsmanager VALUES (null,?,0,null,'Stomatology',null,null,null,1,0)");
			stmt.setString(1, kod);
			stmt.execute();
			
			stmt = con242
					.prepareStatement("insert into sms_users VALUES (?, ?, ?, null, null)");
			stmt.setString(1, kod);
			stmt.setString(2, name);
			stmt.setString(3, mail);
			stmt.execute();
			
			System.err.println("ok");

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void updateClientName(Client client) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("UPDATE sms_users SET user_name=? WHERE user_id=?");
			stmt.setString(1, client.getName());
			stmt.setString(2, client.getKod());

			stmt.execute();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void updateClientMail(Client client) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("UPDATE sms_users SET user_email=? WHERE user_id=?");
			stmt.setString(1, client.getMail());
			stmt.setString(2, client.getName());

			stmt.execute();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void updateClientStatus(Client client) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("UPDATE sms_users SET user_id=? WHERE user_id=?");
			stmt.setString(1, client.getKod());
			stmt.setString(2, client.getKod());

			stmt.execute();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void updateDatePay(Client client) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = con
					.prepareStatement("UPDATE smsmanager SET data_pay=? WHERE user_id=?");
			stmt.setString(1, client.getKod());
			stmt.setString(2, client.getKod());

			stmt.execute();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void updateSmsPay(String kod, int add, int old) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement("UPDATE smsmanager SET sms_pay=? WHERE id_clinic=?");
			
			stmt.setInt(1, add+old);
			stmt.setString(2, kod);

			stmt.execute();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	public void updateOverdraft(String kod, boolean b) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement("UPDATE smsmanager SET ovdt=? WHERE id_clinic=?");
			
			stmt.setInt(1, b ? 1 : 0);
			stmt.setString(2, kod);

			stmt.execute();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	public ResultSet getReport(String kod, String sdt, String pdt) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		
		stmt = con.createStatement();
		
		try {
//			rs =stmt.executeQuery("SELECT sub_user_id,message_text,phone_number,message_time_sent,sms_count FROM message WHERE user_id ='"+kod+"' and message_time_sent>='"+sdt+"' and message_time_sent<='"+pdt+"'");
			rs =stmt.executeQuery("SELECT sub_user_id,message_text,phone_number,message_time_received,sms_count FROM message WHERE user_id ='"+kod+"' and Message_Time_Received>='"+sdt+"' and Message_Time_Received<='"+pdt+"'");
			
//			stmt.setString(1, kod);
//			stmt.setString(2, sdt);
//			stmt.setString(2, pdt);

//			rs = stmt.executeQuery();
			return rs;
		} finally {
			
		}
	}
	
	public Connection getConnection() {
		return con;
	}
	
	public boolean sysConnect() throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;

		stmt = con.createStatement();

		rs = stmt.executeQuery("SELECT system1,system2,count1 from system_sms");
		rs.next();

		if ((rs.getString("system1")).equals(rs.getString("system2"))) {
			return true;

		}
		return false;
	}
	
	private String nameSplit(String kod, boolean b){
		
		if(b){
			String[] mas = kod.split("\\(");
			
			return mas[0];
		}
		
		return kod;
	}
	
	private boolean clinicIsBlocked(String kod){
		
		if(kod.indexOf("blocked") > -1){
			return true;
		}
		return false;
	}
	
	public void updateComent(String kod, String com) throws SQLException{
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("UPDATE smsmanager SET eqconbuilder=? WHERE id_clinic=?");
			stmt.setString(1, com);
			stmt.setString(2, kod);
			stmt.execute();

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	public void updateName(String kod, String name) throws SQLException{
		PreparedStatement stmt = null;

		try {
			
			stmt = con.prepareStatement("UPDATE sms_users SET User_Name=? WHERE User_Id=?");
			stmt.setString(1, name);
			stmt.setString(2, kod);
			stmt.execute();

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	public void updateMail(String kod, String mail) throws SQLException{
		PreparedStatement stmt = null;

		try {
			
			stmt = con.prepareStatement("UPDATE sms_users SET User_Email=? WHERE User_Id=?");
			stmt.setString(1, mail);
			stmt.setString(2, kod);
			stmt.execute();

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	public int getPayNumber() throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		
		stmt = con.createStatement();
		
		try {
			
			rs = stmt.executeQuery("Select numb1 from system_sms ");
			rs.next();
			
			return rs.getInt("numb1");
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	public void setPayNumber(int nmbr) throws SQLException{
		PreparedStatement stmt = null;

		try {
			
			stmt = con.prepareStatement("UPDATE system_sms set numb1=?");
			stmt.setInt(1, nmbr);
			stmt.execute();

		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
}
