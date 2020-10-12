package src;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import jxl.write.WriteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.ExcelPay;

class Smsmanager extends JFrame implements ActionListener,
		ListSelectionListener {

	Logger LOGGER = LoggerFactory.getLogger(Smsmanager.class);

	private static final String ADD_USER = "adduser";
	private static final String SMSPAYUPD = "smspayupd";
	private static final String PAY = "pay";
	private static final String ASTATUS = "astatus";
	private static final String BSTATUS = "bstatus";
	private static final String REPORT = "report";
	private static final String UPD = "upd";
	private static final String KODPREFIX = "(blocked)";

	private StaticStorage ms = null;
	private MyTableModel model = null;

	private static final long serialVersionUID = 1L;

	ArrayList<Client> clients;

	private JFrame frmSmsManagerV;
	private JTextField find;

	private JTable table;
	private JButton button;
	private JButton butblock;
	private JButton addbutton;
	private JButton addsmsbut;
	private JButton createpaybutton;
	private JButton creatreport;
	private JButton blocactiv;
	private JLabel countusers;
	private JLabel finditog;
	public String result = "";
	public String kod = "";
	public String date = "";
	public boolean status = true;
	public String cname = null;

	public int selectedRows;
	public int modelRow;

	private JTextField addsmsfield;
	private JTextField datespo;
	private JTextField dates;
	private JTextField sumField;
	private JTextField kodtextField;
	private JTextField nametextField;
	private JTextField mailtextField;

	@SuppressWarnings("serial")
	public Smsmanager() throws Exception {

		LOGGER.info("Starting Smsmanager v10.0 ...");

		frmSmsManagerV = new JFrame();
		frmSmsManagerV.setTitle("Sms manager v10.0");
		frmSmsManagerV.setBounds(0, 0, 1225, 756);
		frmSmsManagerV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSmsManagerV.setResizable(false);
		frmSmsManagerV.setLocationRelativeTo(null);

		find = new JTextField();
		find.setColumns(10);
		find.addKeyListener(new PushFind());

		JLabel findtext = new JLabel("\u041D\u0430\u0439\u0442\u0438");

		JPanel statisticpanel = new JPanel();
		statisticpanel.setBorder(new TitledBorder(null,
				"\u041E\u0431\u0449\u0435\u0435", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		// scrollPane.setSize(300, 300);

		JLabel label_4 = new JLabel(
				"\u0412\u0441\u0435\u0433\u043E \u043F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u0442\u0435\u043B\u0435\u0439 \u0441\u043C\u0441: ");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));

		countusers = new JLabel("");
		countusers.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GroupLayout gl_statisticpanel = new GroupLayout(statisticpanel);
		gl_statisticpanel
				.setHorizontalGroup(gl_statisticpanel.createParallelGroup(
						Alignment.TRAILING).addGroup(
						Alignment.LEADING,
						gl_statisticpanel
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(label_4)
								.addPreferredGap(ComponentPlacement.RELATED,
										33, Short.MAX_VALUE)
								.addComponent(countusers,
										GroupLayout.PREFERRED_SIZE, 51,
										GroupLayout.PREFERRED_SIZE).addGap(18)));
		gl_statisticpanel.setVerticalGroup(gl_statisticpanel
				.createParallelGroup(Alignment.LEADING).addGroup(
						gl_statisticpanel
								.createSequentialGroup()
								.addGroup(
										gl_statisticpanel
												.createParallelGroup(
														Alignment.BASELINE)
												.addComponent(label_4)
												.addComponent(countusers))
								.addContainerGap(4, Short.MAX_VALUE)));
		statisticpanel.setLayout(gl_statisticpanel);

		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon("C:\\Smsmanager\\src\\2.jpg"));

		JPanel panel_6 = new JPanel();

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0441\u043C\u0441",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));

		addsmsfield = new JTextField();
		addsmsfield.setColumns(10);

		addsmsbut = new JButton("\u041E\u043A");
		addsmsbut.setName(SMSPAYUPD);
		// addsmsbut.setActionCommand("blok_but");
		addsmsbut.addActionListener(this);

		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(gl_panel_7
				.createParallelGroup(Alignment.LEADING)
				.addGap(0, 181, Short.MAX_VALUE)
				.addGroup(
						gl_panel_7
								.createSequentialGroup()
								.addGap(8)
								.addComponent(addsmsfield,
										GroupLayout.PREFERRED_SIZE, 74,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(addsmsbut,
										GroupLayout.PREFERRED_SIZE, 73,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		gl_panel_7
				.setVerticalGroup(gl_panel_7
						.createParallelGroup(Alignment.LEADING)
						.addGap(0, 53, Short.MAX_VALUE)
						.addGroup(
								gl_panel_7
										.createSequentialGroup()
										.addGroup(
												gl_panel_7
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																addsmsbut,
																GroupLayout.PREFERRED_SIZE,
																21,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																addsmsfield,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		panel_7.setLayout(gl_panel_7);

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"\u0414\u0435\u0442\u0430\u043B\u0438\u0437\u0430\u0446\u0438\u044F",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));

		JLabel label_10 = new JLabel("\u0441");
		label_10.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		JLabel label_11 = new JLabel("\u043F\u043E");
		label_11.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		date = Helper.getDate("dd/MM/yyyy");

		datespo = new JTextField(date) {
			/* Ограничени ввода сиволов для даты */
			public void replaceSelection(String content) {
				super.replaceSelection(content);
				String text = getText();

				if (text.length() > 10) {
					setText(text.substring(0, 10));
				}

				if (text.length() == 2) {
					text += "/";
					setText(text);
				}
				if (text.length() == 5) {
					text += "/";
					setText(text);
				}

			}
		};
		datespo.setColumns(10);

		dates = new JTextField(date) {
			/* Ограничени ввода сиволов для даты */
			public void replaceSelection(String content) {
				super.replaceSelection(content);
				String text = getText();

				if (text.length() > 10) {
					setText(text.substring(0, 10));
				}

				if (text.length() == 2) {
					text += "/";
					setText(text);
				}
				if (text.length() == 5) {
					text += "/";
					setText(text);
				}

			}
		};
		dates.setColumns(10);

		JLabel label_12 = new JLabel(
				"\u0424\u0430\u0439\u043B\u044B \u0432 c:\\docs\\");
		label_12.setFont(new Font("Times New Roman", Font.PLAIN, 12));

		creatreport = new JButton("Отчет");
		creatreport.setName(REPORT);
		creatreport.addActionListener(this);

		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8
				.setHorizontalGroup(gl_panel_8
						.createParallelGroup(Alignment.TRAILING)
						.addGap(0, 181, Short.MAX_VALUE)
						.addGroup(
								gl_panel_8
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_8
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_8
																		.createSequentialGroup()
																		.addGap(12)
																		.addGroup(
																				gl_panel_8
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								label_10,
																								GroupLayout.PREFERRED_SIZE,
																								14,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								label_11,
																								GroupLayout.PREFERRED_SIZE,
																								14,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panel_8
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								datespo,
																								Alignment.TRAILING,
																								GroupLayout.DEFAULT_SIZE,
																								107,
																								Short.MAX_VALUE)
																						.addComponent(
																								dates,
																								Alignment.TRAILING,
																								GroupLayout.DEFAULT_SIZE,
																								107,
																								Short.MAX_VALUE))
																		.addGap(22))
														.addComponent(
																label_12,
																Alignment.TRAILING,
																GroupLayout.PREFERRED_SIZE,
																91,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																gl_panel_8
																		.createSequentialGroup()
																		.addComponent(
																				creatreport,
																				GroupLayout.PREFERRED_SIZE,
																				148,
																				GroupLayout.PREFERRED_SIZE)
																		.addContainerGap(
																				11,
																				Short.MAX_VALUE)))));
		gl_panel_8
				.setVerticalGroup(gl_panel_8
						.createParallelGroup(Alignment.LEADING)
						.addGap(0, 157, Short.MAX_VALUE)
						.addGroup(
								gl_panel_8
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_8
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																dates,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_10))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_panel_8
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																datespo,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_11))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(creatreport)
										.addGap(13)
										.addComponent(label_12)
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		panel_8.setLayout(gl_panel_8);

		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				"\u0421\u0447\u0435\u0442\u0430", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));

		createpaybutton = new JButton("\u0421\u0447\u0435\u0442");
		createpaybutton.setName(PAY);
		createpaybutton.addActionListener(this);

		JLabel label_13 = new JLabel("\u0421\u0443\u043C\u043C\u0430");
		label_13.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		sumField = new JTextField("9990.00");
		sumField.setColumns(10);

		JLabel label_14 = new JLabel(
				"\u0424\u0430\u0439\u043B\u044B \u0432 c:\\docs\\");
		label_14.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		GroupLayout gl_panel_9 = new GroupLayout(panel_9);
		gl_panel_9
				.setHorizontalGroup(gl_panel_9
						.createParallelGroup(Alignment.TRAILING)
						.addGap(0, 180, Short.MAX_VALUE)
						.addGroup(
								gl_panel_9
										.createSequentialGroup()
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												gl_panel_9
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																gl_panel_9
																		.createSequentialGroup()
																		.addGroup(
																				gl_panel_9
																						.createParallelGroup(
																								Alignment.TRAILING,
																								false)
																						.addComponent(
																								createpaybutton,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addGroup(
																								gl_panel_9
																										.createSequentialGroup()
																										.addComponent(
																												label_13)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												sumField,
																												GroupLayout.PREFERRED_SIZE,
																												104,
																												GroupLayout.PREFERRED_SIZE)))
																		.addGap(19))
														.addGroup(
																gl_panel_9
																		.createSequentialGroup()
																		.addComponent(
																				label_14,
																				GroupLayout.PREFERRED_SIZE,
																				91,
																				GroupLayout.PREFERRED_SIZE)
																		.addContainerGap()))));
		gl_panel_9
				.setVerticalGroup(gl_panel_9
						.createParallelGroup(Alignment.TRAILING)
						.addGap(0, 127, Short.MAX_VALUE)
						.addGroup(
								gl_panel_9
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_9
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																sumField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_13))
										.addPreferredGap(
												ComponentPlacement.RELATED, 14,
												Short.MAX_VALUE)
										.addComponent(createpaybutton)
										.addGap(18).addComponent(label_14)));
		panel_9.setLayout(gl_panel_9);

		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new TitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"\u0417\u0430\u0431\u043B\u043E\u043A/\u0420\u0430\u0437\u0431\u043B\u043E\u043A",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));

		butblock = new JButton("\u0417\u0430\u0431\u043B");
		butblock.setName(BSTATUS);
		butblock.addActionListener(this);

		blocactiv = new JButton("\u0420\u0430\u0437\u0431\u043B");
		blocactiv.setName(ASTATUS);
		blocactiv.addActionListener(this);
		GroupLayout gl_panel_10 = new GroupLayout(panel_10);
		gl_panel_10.setHorizontalGroup(gl_panel_10
				.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 181, Short.MAX_VALUE)
				.addGap(0, 169, Short.MAX_VALUE)
				.addGroup(
						gl_panel_10
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(butblock,
										GroupLayout.PREFERRED_SIZE, 73,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(blocactiv,
										GroupLayout.PREFERRED_SIZE, 73,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		gl_panel_10.setVerticalGroup(gl_panel_10
				.createParallelGroup(Alignment.LEADING)
				.addGap(0, 81, Short.MAX_VALUE)
				.addGap(0, 54, Short.MAX_VALUE)
				.addGroup(
						gl_panel_10
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel_10
												.createParallelGroup(
														Alignment.BASELINE)
												.addComponent(blocactiv)
												.addComponent(butblock))
								.addContainerGap(20, Short.MAX_VALUE)));
		panel_10.setLayout(gl_panel_10);

		JPanel panel_11 = new JPanel();
		panel_11.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_11.setBorder(new TitledBorder(
				null,
				"\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u043F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u0442\u0435\u043B\u044F",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JLabel label_15 = new JLabel("\u041A\u043E\u0434");
		label_15.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		kodtextField = new JTextField();
		kodtextField.setToolTipText("");
		kodtextField.setColumns(10);
		// kodtextField.addActionListener(new AddPush());

		JLabel label_16 = new JLabel("\u041D\u0430\u0437\u0432");
		label_16.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		nametextField = new JTextField();
		nametextField.setColumns(10);

		JLabel label_17 = new JLabel("  @");
		label_17.setHorizontalTextPosition(SwingConstants.CENTER);
		label_17.setFont(new Font("Times New Roman", Font.PLAIN, 13));

		mailtextField = new JTextField();
		mailtextField.setColumns(10);

		addbutton = new JButton(
				"\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C");
		addbutton.setName(ADD_USER);
		addbutton.addActionListener(this);

		GroupLayout gl_panel_11 = new GroupLayout(panel_11);
		gl_panel_11
				.setHorizontalGroup(gl_panel_11
						.createParallelGroup(Alignment.LEADING)
						.addGap(0, 179, Short.MAX_VALUE)
						.addGap(0, 167, Short.MAX_VALUE)
						.addGroup(
								gl_panel_11
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_11
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_11
																		.createParallelGroup(
																				Alignment.LEADING,
																				false)
																		.addGroup(
																				gl_panel_11
																						.createSequentialGroup()
																						.addComponent(
																								label_15,
																								GroupLayout.PREFERRED_SIZE,
																								24,
																								GroupLayout.PREFERRED_SIZE)
																						.addGap(10)
																						.addComponent(
																								kodtextField,
																								GroupLayout.DEFAULT_SIZE,
																								113,
																								Short.MAX_VALUE))
																		.addGroup(
																				gl_panel_11
																						.createSequentialGroup()
																						.addComponent(
																								label_16)
																						.addGap(10)
																						.addComponent(
																								nametextField,
																								GroupLayout.DEFAULT_SIZE,
																								111,
																								Short.MAX_VALUE))
																		.addGroup(
																				gl_panel_11
																						.createSequentialGroup()
																						.addComponent(
																								label_17)
																						.addGap(18)
																						.addComponent(
																								mailtextField,
																								GroupLayout.DEFAULT_SIZE,
																								111,
																								Short.MAX_VALUE)))
														.addComponent(
																addbutton,
																GroupLayout.PREFERRED_SIZE,
																147,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		gl_panel_11
				.setVerticalGroup(gl_panel_11
						.createParallelGroup(Alignment.LEADING)
						.addGap(0, 173, Short.MAX_VALUE)
						.addGap(0, 146, Short.MAX_VALUE)
						.addGroup(
								gl_panel_11
										.createSequentialGroup()
										.addGap(3)
										.addGroup(
												gl_panel_11
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																kodtextField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_15))
										.addGroup(
												gl_panel_11
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_11
																		.createSequentialGroup()
																		.addGap(13)
																		.addComponent(
																				label_16,
																				GroupLayout.PREFERRED_SIZE,
																				11,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_panel_11
																		.createSequentialGroup()
																		.addGap(8)
																		.addComponent(
																				nametextField,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panel_11
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																mailtextField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label_17))
										.addPreferredGap(
												ComponentPlacement.RELATED, 23,
												Short.MAX_VALUE)
										.addComponent(addbutton).addGap(23)));
		panel_11.setLayout(gl_panel_11);
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6
				.setHorizontalGroup(gl_panel_6
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_6
										.createSequentialGroup()
										.addGroup(
												gl_panel_6
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																panel_9,
																GroupLayout.PREFERRED_SIZE,
																180,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																panel_8,
																GroupLayout.PREFERRED_SIZE,
																181,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																panel_11,
																GroupLayout.PREFERRED_SIZE,
																179,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																panel_10,
																GroupLayout.PREFERRED_SIZE,
																181,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																panel_7,
																GroupLayout.PREFERRED_SIZE,
																181,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		gl_panel_6.setVerticalGroup(gl_panel_6.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_6
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 53,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_11, GroupLayout.PREFERRED_SIZE,
								173, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_10, GroupLayout.PREFERRED_SIZE, 81,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_9, GroupLayout.PREFERRED_SIZE, 127,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 157,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		panel_6.setLayout(gl_panel_6);

		button = new JButton(
				"\u041E\u0431\u043D\u043E\u0432\u0438\u0442\u044C ");
		button.setName(UPD);
		button.addActionListener(this);

		finditog = new JLabel("");
		GroupLayout groupLayout = new GroupLayout(
				frmSmsManagerV.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				statisticpanel,
																				GroupLayout.PREFERRED_SIZE,
																				277,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(299)
																		.addComponent(
																				button,
																				GroupLayout.PREFERRED_SIZE,
																				130,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(28)
																		.addComponent(
																				findtext)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				find,
																				GroupLayout.PREFERRED_SIZE,
																				151,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				finditog)
																		.addPreferredGap(
																				ComponentPlacement.RELATED))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				scrollPane,
																				GroupLayout.DEFAULT_SIZE,
																				1010,
																				Short.MAX_VALUE)))
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(25)
																		.addComponent(
																				label_3,
																				GroupLayout.PREFERRED_SIZE,
																				164,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				panel_6,
																				GroupLayout.PREFERRED_SIZE,
																				183,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								find,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								findtext)
																						.addComponent(
																								finditog))
																		.addGap(18)
																		.addComponent(
																				scrollPane,
																				GroupLayout.DEFAULT_SIZE,
																				588,
																				Short.MAX_VALUE))
														.addComponent(
																panel_6,
																GroupLayout.PREFERRED_SIZE,
																626,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(44)
																		.addComponent(
																				label_3,
																				GroupLayout.PREFERRED_SIZE,
																				31,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(27)
																		.addComponent(
																				button,
																				GroupLayout.PREFERRED_SIZE,
																				35,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				statisticpanel,
																				GroupLayout.PREFERRED_SIZE,
																				57,
																				GroupLayout.PREFERRED_SIZE)))
										.addGap(16)));

		ms = StaticStorage.getInstance();

		if (ms.sysConnect()) {
			System.exit(0);
		}
	   
		/*limitied*/
//		kodtextField.disable();
//		nametextField.disable();
//		mailtextField.disable();
//		addsmsfield.disable();
//		addsmsbut.setEnabled(false);
//		blocactiv.setEnabled(false);
//		butblock.setEnabled(false);
		
		clients = (ArrayList<Client>) ms.getAllClients();

		model = new MyTableModel(clients);

		countusers.setText((model.getRowCount() + ""));

		this.createTable();

		this.setOptionColums();

		this.setSelectionModel();

		this.setSorterModel();

		table.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		scrollPane.setViewportView(table);

		frmSmsManagerV.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			if (c.getName().equals(ADD_USER)) {
				this.insertClient();
				this.reload();
				
				this.selected(kodtextField.getText().trim());
				this.cleanField();
			}
			if (c.getName().equals(UPD)) {
				this.reload();
			}
			if (c.getName().equals(SMSPAYUPD)) {
				this.updateSmsPay();
			}
			if (c.getName().equals(PAY)) {
				this.createPay();
			}
			if (c.getName().equals(REPORT)) {
				this.createReport();
			}

			if (c.getName().equals(ASTATUS)) {
				this.updateStatus(true);
			}
			if (c.getName().equals(BSTATUS)) {
				this.updateStatus(false);
			}

		}
	}

	private void updateStatus(final boolean b) {

		Thread t = new Thread() {

			public void run() {
				try {
					if (b) {
						// if(!status){
						ms.updateStatus(kod, kod + KODPREFIX);
						clients.get(modelRow).setStatus(b);
						// }
					} else {
						// if(status){
						ms.updateStatus(kod + KODPREFIX, kod);
						clients.get(modelRow).setStatus(b);
						// }
					}
					table.updateUI();
					
					LOGGER.info("Update status {} user {}!", b, kod);
				} catch (SQLException e) {
					LOGGER.error("Error reloadStudents!");

					JOptionPane.showMessageDialog(null,
							"Ошибка обновления статуса!", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};
		t.start();
	}

	private void updateSmsPay() {

		Thread t = new Thread() {

			public void run() {
				try {
					Client c = clients.get(modelRow);
					int pay = c.getVsegopaysms();
					int npay = Integer.parseInt(addsmsfield.getText());
					
					ms.updateSmsPay(kod,pay, npay);
					
					c.setVsegopaysms(pay + npay);
					table.updateUI();
					
					addsmsfield.setText("");
					
					LOGGER.info("Update sms pay for usser {}!", kod);
				} catch (SQLException e) {
					LOGGER.error("Ошибка обновления оплаченных смс!");

					JOptionPane.showMessageDialog(null,
							"Ошибка обновления оплаченных смс!", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};
		t.start();
	}

	private void createPay() {
		Thread t = new Thread() {
			public void run() {

				String nvalue = model.getClient(modelRow).getName();
				Helper.createFolder(nvalue);

				try {
					int num=ms.getPayNumber();
					String paynumb=String.valueOf(num);
					ExcelPay ex = new ExcelPay(sumField.getText(), paynumb,	nvalue);
					ex.setOutputFile(nvalue);
					ex.write();
					ms.setPayNumber(num+1);
					
					LOGGER.info("Create pay for user {}!", kod);

				} catch (Exception e) {
					LOGGER.error("Exception: Ошибка создания счета!");

					JOptionPane.showMessageDialog(null,
							"Exception: Ошибка создания счета!", "",
							JOptionPane.INFORMATION_MESSAGE);
				}

				Helper.openDir(nvalue);
			}
		};
		t.start();
	}

	private void createReport() {
		Thread t = new Thread() {
			public void run() {

				
				if(dates.getText().equals("") || datespo.getText().equals("")){
					exportStatistics();
					return ;
				}
				
				String date1 = Helper.reverseDate(dates.getText(), false);
				String date2 = Helper.reverseDate(datespo.getText(), true);

				if (date1.equals("") || date2.equals("")) {
					return;
				}

				String nvalue = model.getClient(modelRow).getName();
				Helper.createFolder(nvalue);

				try {

					ExcelReport ex = new ExcelReport(ms.getReport(kod, date1,
							date2));
					ex.setOutputFile(nvalue);
					ex.write();
					
					LOGGER.info("Create report for user {}!", kod);
				} catch (Exception e) {
					LOGGER.error("Exception: Ошибка создания отчета!");

					JOptionPane.showMessageDialog(null,
							"Exception: Ошибка создания отчета!", "",
							JOptionPane.INFORMATION_MESSAGE);
				}

				Helper.openDir(nvalue);
			}

			private void exportStatistics() {
				Statistic st = new Statistic(table);
				
				String path = "statistic";
				
				Helper.createFolder(path);
				st.setOutputFile("c:/Smsmanager/docs/" + path + "/statistic.xls");
				Helper.openDir(path);
				
				try {
					st.write();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	private void insertClient() {
		Thread t = new Thread() {
			public void run() {
				String kod = kodtextField.getText();
				String name = nametextField.getText();
				String mail = mailtextField.getText();

				kod = kod.trim();
				kod = kod.toLowerCase();
				if (kod.equals("") || name.equals("")) {
					return;
				}

				try {
					ms.insertClient(kod, name, mail);
					
					LOGGER.info("Add user {}!", kod);
				} catch (SQLException e) {
					LOGGER.error("This user already is {}!", kod);

					JOptionPane.showMessageDialog(null,
							"Такой пользователь уже есть!", "",
							JOptionPane.INFORMATION_MESSAGE);
					selected(kod);
				}
				
//				cleanField();
			}
		};
		t.start();

	}

	public void reload() {
		// Thread t = new Thread() {
		//
		// public void run() {
		// try {
		// clients = (ArrayList<Client>) ms.getAllClients();
		// table.setModel(new MyTableModel(clients));
		// setOptionColums();
		// } catch (SQLException e) {
		// LOGGER.error("Error reloadStudents!");
		//
		// JOptionPane.showMessageDialog(null,
		// "Ошибка обновления таблицы!", "",
		// JOptionPane.INFORMATION_MESSAGE);
		// }
		// }
		// };
		// t.start();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					clients = (ArrayList<Client>) ms.getAllClients();
					table.setModel(new MyTableModel(clients));
					setOptionColums();
					
					LOGGER.info("Table update {}!", kod);
				} catch (SQLException e) {
					LOGGER.error("Error reloadStudents!");

					JOptionPane.showMessageDialog(null,
							"Ошибка обновления таблицы!", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	private void cleanField() {
		kodtextField.setText("");
		nametextField.setText("");
		mailtextField.setText("");
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Smsmanager window = new Smsmanager();
					window.frmSmsManagerV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("serial")
	private void createTable() {
		table = new JTable(model) {
			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {

				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setHorizontalAlignment(SwingConstants.CENTER);

				if (column == 5) {

					row = table.convertRowIndexToModel(row);
					int smss = clients.get(row).getVsegosmssent();
					int smsp = clients.get(row).getVsegopaysms();

					if (smss > smsp) {

						renderer.setBackground(Color.red);

						return renderer;
					}
				}

				if (column == 1) {
					row = table.convertRowIndexToModel(row);
					if (clients.get(row).getStatus()) {

						renderer.setForeground(Color.GREEN);
						return renderer;
					}

					renderer.setForeground(Color.RED);
					return renderer;

				}

				return super.getCellRenderer(row, column);
			}

		};
	}

	class PushFind extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_ENTER) {

				table.clearSelection();

				String strInput = find.getText();
				strInput = strInput.trim();
				strInput = strInput.toLowerCase();

				for (int t = 0; t < clients.size(); t++) {
					String value = ((String) table.getValueAt(t, 2));
					value = value.toLowerCase();

					if ((value.indexOf(strInput)) != -1) {

						table.addRowSelectionInterval(t, t);
						table.scrollRectToVisible(table.getCellRect(t, 2, true));
					}
				}
			}
		}
	}

	private void setOptionColums() {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setMinWidth(20);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		
		table.getColumnModel().getColumn(1).setMinWidth(20);
		table.getColumnModel().getColumn(1).setMaxWidth(20);

		table.getColumnModel().getColumn(2).setMinWidth(200);
		table.getColumnModel().getColumn(3).setMinWidth(125);
		table.getColumnModel().getColumn(4).setMinWidth(110);
		table.getColumnModel().getColumn(5).setMinWidth(110);
		table.getColumnModel().getColumn(6).setMinWidth(110);
		table.getColumnModel().getColumn(7).setMinWidth(110);
		table.getColumnModel().getColumn(8).setMinWidth(90);
		table.getColumnModel().getColumn(9).setMinWidth(90);
		table.getColumnModel().getColumn(10).setMinWidth(170);
		table.getColumnModel().getColumn(11).setMinWidth(200);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 3; i < 9; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	private void setSorterModel() {

		TableRowSorter<MyTableModel> sorter = new TableRowSorter<MyTableModel>(
				model) {
			@Override
			public Comparator<?> getComparator(int column) {

				if (column == 5 || column == 6) {

					return new Comparator<String>() {
						@Override
						public int compare(String s1, String s2) {
							return Integer.parseInt(s1) - Integer.parseInt(s2);
						}
					};
				}
				return super.getComparator(column);
			}
		};

		sorter.setSortable(0, false);
		sorter.setSortable(1, false);
		sorter.setSortable(2, true); //
		sorter.setSortable(3, false);
		sorter.setSortable(4, false);
		sorter.setSortable(5, true); //
		sorter.setSortable(6, true); //
		sorter.setSortable(7, false);
		sorter.setSortable(8, false);

		sorter.setSortsOnUpdates(true);
		table.setRowSorter(sorter);
	}

	private void setSelectionModel() {
		ListSelectionModel selModel = table.getSelectionModel();

		selModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				int selectedRows = table.getSelectedRow();

				if (selectedRows > -1) {

					modelRow = table.convertRowIndexToModel(selectedRows);
				}
				Object value = model.getValueAt(modelRow, 1);
				status = Boolean.valueOf(value.toString());

				Object value1 = model.getValueAt(modelRow, 2);
				cname = (String) value1;

				Object value2 = model.getValueAt(modelRow, 3);
				kod = (String) value2;

			}
		});
	}

	private void selected(String kod) {
		for (int t = 0; t < clients.size(); t++) {

			String value = clients.get(t).getKod();

			if (kod.equals(value)) {

				table.addRowSelectionInterval(t, t);
				table.scrollRectToVisible(table.getCellRect(t, 2, true));

			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

	}

}
