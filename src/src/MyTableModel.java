package src;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTableModel implements TableModel {

	Logger LOGGER = LoggerFactory.getLogger(MyTableModel.class);

	private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
	private List<Client> clients;
	private StaticStorage st=null;

	public MyTableModel(ArrayList<Client> clients) {
		this.clients = clients;
		try {
			st=StaticStorage.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addTableModelListener(TableModelListener listener) {

		listeners.add(listener);

	}

	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Boolean.class;
		default:
			return Object.class;
		}
	}

	public int getColumnCount() {

		return 12;

	}

	public String getColumnName(int columnIndex) {

		switch (columnIndex) {

		case 0:

			return "О";
		case 1:

			return "С";
		case 2:

			return "Клиника";

		case 3:

			return "Код";

		case 4:

			return "Имя отправ";
		case 5:
			
			return "Всего отпр смс";
		case 6:

			return "Всего оплач смс";

		case 7:

			return "Отпрв/мес. смс";

		case 8:
			return "Сред/мес смс";

		case 9:

			return "Дата счета";
		case 10:

			return "Em@il";
		case 11:

			return "Коммент";
		}

		return "";
	}

	public int getRowCount() {
		return clients == null ? 0 : clients.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		Client client = clients.get(rowIndex);

		switch (columnIndex) {

		case 0:

			return client.getOvd();
		case 1:
			
			return client.getStatus() ? "A" : "B";
			
		case 2:

			return client.getName();

		case 3:

			return client.getKod();

		case 4:

			return client.getSender();
		case 5:
			
			return client.getVsegosmssent();
			
		case 6:

			return client.getVsegopaysms();

		case 7:

			return client.getMonthsmssent();

		case 8:

			return client.getSredSmssent();

		case 9:

			return client.getDatepay();

		case 10:

			return client.getMail();

		case 11:

			return client.getComment();

		}

		return "";

	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {

		return (columnIndex == 0 || columnIndex == 2 || columnIndex == 4 || columnIndex == 10 || columnIndex == 11) ? true : false;
	}

	public void removeTableModelListener(TableModelListener listener) {

		listeners.remove(listener);

	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		String kod=null;
		String name=null;
		
		switch (columnIndex) {

		case 0:
			kod = clients.get(rowIndex).getKod();
			boolean chek =Boolean.valueOf(getValueAt(rowIndex,columnIndex).toString());
			
			if(chek){
				chek=false;
			}else{
				chek=true;
			}
			
			try {
				st.updateOverdraft(kod, chek);
				clients.get(rowIndex).setOvd(chek);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			return;
			
		case 2:
			
			 kod = clients.get(rowIndex).getKod();
			 name = (String)value;

			try {
				st.updateName(kod, name);
				clients.get(rowIndex).setName(name);
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
			return;
			
		case 4:
			
			 kod = clients.get(rowIndex).getKod();
			 name = (String)value;

			try {
				st.updateSender(kod, name);
				clients.get(rowIndex).setSender(name);
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
			return;
		
		case 10:
			 kod = clients.get(rowIndex).getKod();
			 name = (String)value;

			try {
				st.updateMail(kod, name);
				clients.get(rowIndex).setMail(name);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return;
			
		case 11:
			 kod = clients.get(rowIndex).getKod();
			 name = (String)value;
			 
			try {
				st.updateComent(kod, name);
				clients.get(rowIndex).setComment(name);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}	
	}

	public Client getClient(int num) {
		return clients.get(num);
	}
}
