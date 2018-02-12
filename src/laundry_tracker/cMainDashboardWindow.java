package laundry_tracker;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import java.awt.Font;
import com.toedter.calendar.JDateChooser;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;


public class cMainDashboardWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JPanel contentPane;
	private JTextField txtFname;
	private JTextField txtLname;
	private static String currUser;
	//private static dViewAllClientsWindow tableContentPane;
	private static JTable laundryTable;
	private JTextField txtFnameFilter;
	private JTextField txtLnameFilter;
	private static JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public static void runMessageTabs(){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cMainDashboardWindow frame = new cMainDashboardWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Couldn't show the main dashboard??");
				}
			}
		});
	}
	
	public cMainDashboardWindow() throws ParseException {
		setTitle("Manage Laundry");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 783, 535);
		JPanel panelAddClient = new JPanel();
		panelAddClient.setBorder(new TitledBorder(new EtchedBorder(), "Fill out the fields to enter a new client into the database. (* indicates required field)"));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new CompoundBorder(new LineBorder(new Color(128, 0, 0), 2, true), new LineBorder(new Color(218, 165, 32))));
		
		//tableContentPane = new dViewAllClientsWindow();
		//tableContentPane.setOpaque(true);
		JPanel panelLaundryList = new JPanel();
		
		tabbedPane.addTab("Laundry List", null, panelLaundryList, null);
		GridBagLayout gbl_viewLaundryList = new GridBagLayout();
		gbl_viewLaundryList.columnWidths = new int[]{0, 0, 80, 0, 0};
		gbl_viewLaundryList.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_viewLaundryList.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_viewLaundryList.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelLaundryList.setLayout(gbl_viewLaundryList);
		
		JButton btnDropoffNewLoad = new JButton("Drop off New Load");
		btnDropoffNewLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new gDropoffWindow();
			}
		});
		GridBagConstraints gbc_btnDropoffNewLoad = new GridBagConstraints();
		gbc_btnDropoffNewLoad.gridwidth = 3;
		gbc_btnDropoffNewLoad.insets = new Insets(0, 0, 5, 5);
		gbc_btnDropoffNewLoad.gridx = 0;
		gbc_btnDropoffNewLoad.gridy = 0;
		panelLaundryList.add(btnDropoffNewLoad, gbc_btnDropoffNewLoad);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 16;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 0;
		panelLaundryList.add(scrollPane, gbc_scrollPane);
		
		create_laundry_table(scrollPane);
/*		laundryTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table = (JTable) mouseEvent.getSource();
				Point point = mouseEvent.getPoint();
				int row = table.rowAtPoint(point);
				int column = table.columnAtPoint(point);
				if (mouseEvent.getClickCount() == 2) {
					if (column == 1) {
						//Bring up the client info page

						System.out.println("Client page");
					} else if (column == 5) {
						//Bring up the edit/view notes page
						int laundry_id = (int) laundryTable.getValueAt(laundryTable.getSelectedRow(), 0);
						String[] options = {"Save and Close", "Close"};
						debug.show_note_prompt("laundry_loads", String.valueOf(laundry_id), "Laundry Note", "<html>These are all the notes about the current load of laundry.<br>You can make changes and/or additions.<html>", options, true);
						//Refresh the table
						cMainDashboardWindow.update_table();
					} else {
						//Tell user they can't double click here.
						debug.show_error("No Double click", "You can't double click this column.");
					}
				}
			}
		});
*/		
		JButton btnMarkLoadComplete = new JButton("Mark Load Complete");
		GridBagConstraints gbc_btnMarkLoadComplete = new GridBagConstraints();
		gbc_btnMarkLoadComplete.gridwidth = 3;
		gbc_btnMarkLoadComplete.insets = new Insets(0, 0, 5, 5);
		gbc_btnMarkLoadComplete.gridx = 0;
		gbc_btnMarkLoadComplete.gridy = 1;
		panelLaundryList.add(btnMarkLoadComplete, gbc_btnMarkLoadComplete);
		btnMarkLoadComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//Select highlighted id from the table
					int laundry_id = (int) laundryTable.getValueAt(laundryTable.getSelectedRow(), 0);
					String client_id = (String) laundryTable.getValueAt(laundryTable.getSelectedRow(), 6);
					edit_laundry("markComplete", laundry_id, client_id, bWelcomeScreenWindow.getCurrUser());
					//Refresh the table
						//Don't update the client's eligible_today or outstanding load value
					cMainDashboardWindow.update_table();
				} catch (java.lang.ArrayIndexOutOfBoundsException e) {
					debug.show_error("No Selection", "Please select a row from the table!");
				}
			}
		});

		
		JButton btnMarkPickedUp = new JButton("Mark Picked Up");
		GridBagConstraints gbc_btnMarkPickedUp = new GridBagConstraints();
		gbc_btnMarkPickedUp.gridwidth = 3;
		gbc_btnMarkPickedUp.insets = new Insets(0, 0, 5, 5);
		gbc_btnMarkPickedUp.gridx = 0;
		gbc_btnMarkPickedUp.gridy = 2;
		panelLaundryList.add(btnMarkPickedUp, gbc_btnMarkPickedUp);
		btnMarkPickedUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//Select highlighted id from the table
					int laundry_id = (int) laundryTable.getValueAt(laundryTable.getSelectedRow(), 0);
					String client_id = (String) laundryTable.getValueAt(laundryTable.getSelectedRow(), 6);
					edit_laundry("pickup", laundry_id, client_id, bWelcomeScreenWindow.getCurrUser());
					//Refresh the table
						//Don't update the client's eligible_today or outstanding load value
					cMainDashboardWindow.update_table();
				} catch (java.lang.ArrayIndexOutOfBoundsException e) {
					debug.show_error("No Selection", "Please select a row from the table!");
				}
			}
		});

		
		JLabel lblFilters = new JLabel("Filters");
		lblFilters.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblFilters = new GridBagConstraints();
		gbc_lblFilters.gridwidth = 3;
		gbc_lblFilters.insets = new Insets(0, 0, 5, 5);
		gbc_lblFilters.gridx = 0;
		gbc_lblFilters.gridy = 5;
		panelLaundryList.add(lblFilters, gbc_lblFilters);
		
		JLabel lblEarliestDate = new JLabel("Earliest Date");
		GridBagConstraints gbc_lblEarliestDate = new GridBagConstraints();
		gbc_lblEarliestDate.gridwidth = 2;
		gbc_lblEarliestDate.anchor = GridBagConstraints.EAST;
		gbc_lblEarliestDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblEarliestDate.gridx = 0;
		gbc_lblEarliestDate.gridy = 6;
		panelLaundryList.add(lblEarliestDate, gbc_lblEarliestDate);
		
		JDateChooser dateChooserEarliest = new JDateChooser();
		GridBagConstraints gbc_dateChooserEarliest = new GridBagConstraints();
		gbc_dateChooserEarliest.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooserEarliest.fill = GridBagConstraints.BOTH;
		gbc_dateChooserEarliest.gridx = 2;
		gbc_dateChooserEarliest.gridy = 6;
		panelLaundryList.add(dateChooserEarliest, gbc_dateChooserEarliest);
				
		JLabel lblLatestDate = new JLabel("Latest Date");
		GridBagConstraints gbc_lblLatestDate = new GridBagConstraints();
		gbc_lblLatestDate.gridwidth = 2;
		gbc_lblLatestDate.anchor = GridBagConstraints.EAST;
		gbc_lblLatestDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblLatestDate.gridx = 0;
		gbc_lblLatestDate.gridy = 7;
		panelLaundryList.add(lblLatestDate, gbc_lblLatestDate);
		
		JDateChooser dateChooserLatest = new JDateChooser();
		GridBagConstraints gbc_dateChooserLatest = new GridBagConstraints();
		gbc_dateChooserLatest.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooserLatest.fill = GridBagConstraints.BOTH;
		gbc_dateChooserLatest.gridx = 2;
		gbc_dateChooserLatest.gridy = 7;
		panelLaundryList.add(dateChooserLatest, gbc_dateChooserLatest);
		
		//Radio Buttons for the filters!
		JLabel lblRequireCompleted = new JLabel("Require Completed");
		GridBagConstraints gbc_lblRequireCompleted = new GridBagConstraints();
		gbc_lblRequireCompleted.insets = new Insets(0, 0, 5, 5);
		gbc_lblRequireCompleted.gridx = 0;
		gbc_lblRequireCompleted.gridy = 8;
		panelLaundryList.add(lblRequireCompleted, gbc_lblRequireCompleted);
		
		JRadioButton rdbtnCompletedYes = new JRadioButton("Yes");
		rdbtnCompletedYes.setActionCommand("completedYes");
		GridBagConstraints gbc_rdbtnCompletedYes = new GridBagConstraints();
		gbc_rdbtnCompletedYes.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCompletedYes.gridx = 1;
		gbc_rdbtnCompletedYes.gridy = 8;
		panelLaundryList.add(rdbtnCompletedYes, gbc_rdbtnCompletedYes);
		
		JRadioButton rdbtnCompletedNo = new JRadioButton("No");
		rdbtnCompletedNo.setActionCommand("completedNo");
		GridBagConstraints gbc_rdbtnCompletedNo = new GridBagConstraints();
		gbc_rdbtnCompletedNo.anchor = GridBagConstraints.WEST;
		gbc_rdbtnCompletedNo.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCompletedNo.gridx = 2;
		gbc_rdbtnCompletedNo.gridy = 8;
		panelLaundryList.add(rdbtnCompletedNo, gbc_rdbtnCompletedNo);
		
		JLabel lblRequirePickedUp = new JLabel("Require Picked Up");
		GridBagConstraints gbc_lblRequirePickedUp = new GridBagConstraints();
		gbc_lblRequirePickedUp.insets = new Insets(0, 0, 5, 5);
		gbc_lblRequirePickedUp.gridx = 0;
		gbc_lblRequirePickedUp.gridy = 9;
		panelLaundryList.add(lblRequirePickedUp, gbc_lblRequirePickedUp);
		
		JRadioButton rdbtnPickedupYes = new JRadioButton("Yes");
		rdbtnPickedupYes.setActionCommand("pickedUpYes");
		GridBagConstraints gbc_rdbtnRdbtnpickedupYes = new GridBagConstraints();
		gbc_rdbtnRdbtnpickedupYes.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnRdbtnpickedupYes.gridx = 1;
		gbc_rdbtnRdbtnpickedupYes.gridy = 9;
		panelLaundryList.add(rdbtnPickedupYes, gbc_rdbtnRdbtnpickedupYes);
		
		JRadioButton rdbtnPickedupNo = new JRadioButton("No");
		rdbtnPickedupNo.setActionCommand("pickedUpNo");
		GridBagConstraints gbc_rdbtnpickedupNo = new GridBagConstraints();
		gbc_rdbtnpickedupNo.anchor = GridBagConstraints.WEST;
		gbc_rdbtnpickedupNo.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnpickedupNo.gridx = 2;
		gbc_rdbtnpickedupNo.gridy = 9;
		panelLaundryList.add(rdbtnPickedupNo, gbc_rdbtnpickedupNo);
		
		JLabel lblCurrentlyEligible = new JLabel("Currently Eligible?");
		GridBagConstraints gbc_lblCurrentlyEligible = new GridBagConstraints();
		gbc_lblCurrentlyEligible.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentlyEligible.gridx = 0;
		gbc_lblCurrentlyEligible.gridy = 10;
		panelLaundryList.add(lblCurrentlyEligible, gbc_lblCurrentlyEligible);
		
		JRadioButton rdbtnEligibleYes = new JRadioButton("Yes");
		rdbtnEligibleYes.setActionCommand("eligibleYes");
		GridBagConstraints gbc_rdbtnEligibleYes = new GridBagConstraints();
		gbc_rdbtnEligibleYes.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnEligibleYes.gridx = 1;
		gbc_rdbtnEligibleYes.gridy = 10;
		panelLaundryList.add(rdbtnEligibleYes, gbc_rdbtnEligibleYes);
		
		JRadioButton rdbtnEligibleNo = new JRadioButton("No");
		rdbtnEligibleNo.setActionCommand("eligibleNo");
		GridBagConstraints gbc_rdbtnEligibleNo = new GridBagConstraints();
		gbc_rdbtnEligibleNo.anchor = GridBagConstraints.WEST;
		gbc_rdbtnEligibleNo.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnEligibleNo.gridx = 2;
		gbc_rdbtnEligibleNo.gridy = 10;
		panelLaundryList.add(rdbtnEligibleNo, gbc_rdbtnEligibleNo);
		
	    //Group the radio buttons.
	    ButtonGroup btngrpCompleted = new ButtonGroup();
	    btngrpCompleted.add(rdbtnCompletedYes);
	    btngrpCompleted.add(rdbtnCompletedNo);
	    
	    ButtonGroup btngrpPickedup = new ButtonGroup();
	    btngrpPickedup.add(rdbtnPickedupYes);
	    btngrpPickedup.add(rdbtnPickedupNo);

	    ButtonGroup btngrpEligible = new ButtonGroup();
	    btngrpEligible.add(rdbtnEligibleYes);
	    btngrpEligible.add(rdbtnEligibleNo);
		
		JLabel lblFirstNameFilter = new JLabel("First Name");
		GridBagConstraints gbc_lblFirstNameFilter = new GridBagConstraints();
		gbc_lblFirstNameFilter.anchor = GridBagConstraints.EAST;
		gbc_lblFirstNameFilter.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstNameFilter.gridx = 0;
		gbc_lblFirstNameFilter.gridy = 11;
		panelLaundryList.add(lblFirstNameFilter, gbc_lblFirstNameFilter);
		
		txtFnameFilter = new JTextField();
		GridBagConstraints gbc_textFieldFName = new GridBagConstraints();
		gbc_textFieldFName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldFName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFName.gridx = 2;
		gbc_textFieldFName.gridy = 11;
		panelLaundryList.add(txtFnameFilter, gbc_textFieldFName);
		txtFnameFilter.setColumns(10);
		
		JLabel lblLastNameFilter = new JLabel("Last Name");
		GridBagConstraints gbc_lblLastNameFilter = new GridBagConstraints();
		gbc_lblLastNameFilter.anchor = GridBagConstraints.EAST;
		gbc_lblLastNameFilter.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastNameFilter.gridx = 0;
		gbc_lblLastNameFilter.gridy = 12;
		panelLaundryList.add(lblLastNameFilter, gbc_lblLastNameFilter);
		
		txtLnameFilter = new JTextField();
		GridBagConstraints gbc_textFieldLName = new GridBagConstraints();
		gbc_textFieldLName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldLName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLName.gridx = 2;
		gbc_textFieldLName.gridy = 12;
		panelLaundryList.add(txtLnameFilter, gbc_textFieldLName);
		txtLnameFilter.setColumns(10);
		
		JButton btnApplyFilters = new JButton("Apply Filters");
		btnApplyFilters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String earliest_date = "";
				String latest_date = "";
				String single_date = "";
				String completed = "";
				String pickedup = "";
				String eligible = "";
				String fName = "";
				String lName = "";
				boolean at_least_one = false;
				
				//Make all the where clauses if the filter is populated.
				System.out.println("DATE FORMAT: " + dateChooserEarliest.getDateFormatString());
				DateFormat df_early = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				DateFormat df_late = new SimpleDateFormat("yyyy-MM-dd 23:59:59.99");
				//DateFormat df_same = new SimpleDateFormat("yyyy-MM-dd");
				Date early_date = dateChooserEarliest.getDate();
				Date late_date = dateChooserLatest.getDate();
				if (early_date != null) {
					earliest_date = "drop_off >= '" + df_early.format(early_date) + "'";
				}
				if (late_date != null) {
					latest_date = "drop_off <= '" + df_late.format(late_date) + "'";
				}
/*				if (early_date != null && late_date != null && df.format(early_date).equalsIgnoreCase(df.format(late_date))) {
					earliest_date = latest_date = "";
					debug.print("DATESSSS: " + earliest_date + " : " + latest_date);
					single_date = "drop_off = '" + df.format(early_date) + "'";
				}
*/				
				try {
					if (btngrpCompleted.getSelection().isSelected()) {
						completed = btngrpCompleted.getSelection().getActionCommand();
/*						System.out.println("Value of completed (button model): " + completed);
*/						if (completed.equals("completedYes")) {
							completed = " load_complete IS NOT NULL ";
						} else if (completed.equals("completedNo")) {
							completed = " load_complete IS NULL ";
						} else {
							debug.print("THIS SHOULDNT HAVE HAPPENED completed");
						}
					}
				} catch (java.lang.NullPointerException e) {
					debug.print("Caught the error Completed: " + completed);
					//e.printStackTrace();
				}

				try {
					btngrpPickedup.getSelection();
						pickedup = btngrpPickedup.getSelection().getActionCommand();
						if (pickedup.equals("pickedUpYes")) {
							pickedup = " pick_up IS NOT NULL ";
						} else if (pickedup.equals("pickedUpNo")) {
							pickedup = " pick_up IS NULL ";
						} else {
							debug.print("THIS SHOULDNT HAVE HAPPENED pickedup");
						}
				} catch (java.lang.NullPointerException e) {
					debug.print("Caught the error Pickedup");
					//e.printStackTrace();
				}
				
				try {
					btngrpEligible.getSelection();
					eligible = btngrpEligible.getSelection().getActionCommand();
					if (eligible.equals("eligibleYes")) {
						eligible = " (eligible_today = TRUE AND load_outstanding = FALSE) ";
					} else if (eligible.equals("eligibleNo")) {
						eligible = " (eligible_today = FALSE OR load_outstanding = TRUE) ";
					} else {
						debug.print("THIS SHOULDNT HAVE HAPPENED eligible");
					}
				} catch (java.lang.NullPointerException e) {
					debug.print("Caught the error Eligible");
					//e.printStackTrace();
				}
				if (!txtFnameFilter.getText().isEmpty()) {
					fName = " fName = '" + txtFnameFilter.getText() + "' ";
					System.out.println("fName text: " + fName);
				}
				if (!txtLnameFilter.getText().isEmpty()) {
					lName = " lName = '" + txtLnameFilter.getText() + "' ";
					System.out.println("lName text: " + lName);
				}
				
				String[] filters = {earliest_date, latest_date, single_date, completed, pickedup, eligible, fName, lName};
				//Make sure at least one of the filters is populated. Otherwise, there is nothing to filter so don't do anything.
				//There is a separate "Clear Filters" button for getting the table back to its normal display.
				for (int j = 0; j < filters.length; j++) {
					if (!filters[j].isEmpty()) {
						at_least_one = true;
						break;
					}
				}
				if (at_least_one) {
					create_laundry_table(scrollPane, generate_filter_query(filters));
				}
/*				if(earliest_date.isEmpty() && latest_date.isEmpty() && completed.isEmpty() && pickedup.isEmpty() && eligible.isEmpty() && fName.isEmpty() && lName.isEmpty()) {
					debug.print("Nothing was filled");
				} else {
					create_laundry_table(scrollPane, generate_filter_query(filters));
				}
*/			}
		});
		GridBagConstraints gbc_btnApplyFilters = new GridBagConstraints();
		gbc_btnApplyFilters.gridwidth = 3;
		gbc_btnApplyFilters.insets = new Insets(0, 0, 5, 5);
		gbc_btnApplyFilters.gridx = 0;
		gbc_btnApplyFilters.gridy = 13;
		panelLaundryList.add(btnApplyFilters, gbc_btnApplyFilters);
		
		JButton btnGenerateReport = new JButton("Generate Report");
		GridBagConstraints gbc_btnGenerateReport = new GridBagConstraints();
		gbc_btnGenerateReport.gridwidth = 3;
		gbc_btnGenerateReport.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenerateReport.gridx = 0;
		gbc_btnGenerateReport.gridy = 14;
		panelLaundryList.add(btnGenerateReport, gbc_btnGenerateReport);
		
		JButton btnClearFilters = new JButton("Clear Filters");
		btnClearFilters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				create_laundry_table(scrollPane);
				reset_filters();
			}

			private void reset_filters() {
				debug.print("RESETTING");
				dateChooserEarliest.setDate(null);
				dateChooserLatest.setDate(null);
				txtFnameFilter.setText("");
				txtLnameFilter.setText("");
				rdbtnCompletedYes.setSelected(false);
				rdbtnCompletedNo.setSelected(false);
				rdbtnPickedupYes.setSelected(false);
				rdbtnPickedupNo.setSelected(false);
				rdbtnEligibleYes.setSelected(false);
				rdbtnEligibleNo.setSelected(false);
				btngrpCompleted.clearSelection();
				btngrpPickedup.clearSelection();
				btngrpEligible.clearSelection();
			}
		});
		GridBagConstraints gbc_btnClearFilters = new GridBagConstraints();
		gbc_btnClearFilters.gridwidth = 3;
		gbc_btnClearFilters.insets = new Insets(0, 0, 0, 5);
		gbc_btnClearFilters.gridx = 0;
		gbc_btnClearFilters.gridy = 15;
		panelLaundryList.add(btnClearFilters, gbc_btnClearFilters);
		
		
		tabbedPane.add("Add Client", panelAddClient);
		GridBagLayout gbl_panelAddClient = new GridBagLayout();
		gbl_panelAddClient.columnWidths = new int[]{0, 0, 0};
		gbl_panelAddClient.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelAddClient.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelAddClient.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panelAddClient.setLayout(gbl_panelAddClient);
		
		JLabel lblFirstName = new JLabel("*First Name");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.EAST;
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 0;
		panelAddClient.add(lblFirstName, gbc_lblFirstName);
		
		txtFname = new JTextField();
		GridBagConstraints gbc_txtFname = new GridBagConstraints();
		gbc_txtFname.insets = new Insets(0, 0, 5, 0);
		gbc_txtFname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFname.gridx = 1;
		gbc_txtFname.gridy = 0;
		panelAddClient.add(txtFname, gbc_txtFname);
		txtFname.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.EAST;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 1;
		panelAddClient.add(lblLastName, gbc_lblLastName);
		
		txtLname = new JTextField();
		GridBagConstraints gbc_txtLname = new GridBagConstraints();
		gbc_txtLname.insets = new Insets(0, 0, 5, 0);
		gbc_txtLname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLname.gridx = 1;
		gbc_txtLname.gridy = 1;
		panelAddClient.add(txtLname, gbc_txtLname);
		txtLname.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone Number");
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.EAST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 0;
		gbc_lblPhone.gridy = 2;
		panelAddClient.add(lblPhone, gbc_lblPhone);
		
		GridBagConstraints gbc_frmtdtxtfldMaskformatterMf = new GridBagConstraints();
	    
		MaskFormatter phone = null;
		phone = new MaskFormatter("###-###-####");

	    phone.setPlaceholderCharacter('_');
	    JFormattedTextField ftfPhone = new JFormattedTextField(phone);
	    ftfPhone.setToolTipText("i.e. 703-345-5555");
	    panelAddClient.add(ftfPhone);

		gbc_frmtdtxtfldMaskformatterMf.insets = new Insets(0, 0, 5, 0);
		gbc_frmtdtxtfldMaskformatterMf.fill = GridBagConstraints.HORIZONTAL;
		gbc_frmtdtxtfldMaskformatterMf.gridx = 1;
		gbc_frmtdtxtfldMaskformatterMf.gridy = 2;
		panelAddClient.add(ftfPhone, gbc_frmtdtxtfldMaskformatterMf);
		
		JLabel lblEligibility = new JLabel("Eligibility:");
		GridBagConstraints gbc_lblEligibility = new GridBagConstraints();
		gbc_lblEligibility.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblEligibility.insets = new Insets(0, 0, 5, 5);
		gbc_lblEligibility.gridx = 0;
		gbc_lblEligibility.gridy = 3;
		panelAddClient.add(lblEligibility, gbc_lblEligibility);
		
		JLabel lblACheckmarkMeans = new JLabel("A checkmark means the client is allowed to drop off laundry that day.");
		GridBagConstraints gbc_lblACheckmarkMeans = new GridBagConstraints();
		gbc_lblACheckmarkMeans.anchor = GridBagConstraints.SOUTH;
		gbc_lblACheckmarkMeans.insets = new Insets(0, 0, 5, 0);
		gbc_lblACheckmarkMeans.gridx = 1;
		gbc_lblACheckmarkMeans.gridy = 3;
		panelAddClient.add(lblACheckmarkMeans, gbc_lblACheckmarkMeans);
		
		JLabel lblDefaultIsEligible = new JLabel("Default is eligible every day.");
		GridBagConstraints gbc_lblDefaultIsEligible = new GridBagConstraints();
		gbc_lblDefaultIsEligible.insets = new Insets(0, 0, 5, 0);
		gbc_lblDefaultIsEligible.gridx = 1;
		gbc_lblDefaultIsEligible.gridy = 4;
		panelAddClient.add(lblDefaultIsEligible, gbc_lblDefaultIsEligible);
		
		JCheckBox chckbxMonday = new JCheckBox("Monday");
		chckbxMonday.setSelected(true);
		GridBagConstraints gbc_chckbxMonday = new GridBagConstraints();
		gbc_chckbxMonday.anchor = GridBagConstraints.WEST;
		gbc_chckbxMonday.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxMonday.gridx = 1;
		gbc_chckbxMonday.gridy = 5;
		panelAddClient.add(chckbxMonday, gbc_chckbxMonday);
						
		JCheckBox chckbxTuesday = new JCheckBox("Tuesday");
		chckbxTuesday.setSelected(true);
		GridBagConstraints gbc_chckbxTuesday = new GridBagConstraints();
		gbc_chckbxTuesday.anchor = GridBagConstraints.WEST;
		gbc_chckbxTuesday.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxTuesday.gridx = 1;
		gbc_chckbxTuesday.gridy = 6;
		panelAddClient.add(chckbxTuesday, gbc_chckbxTuesday);
		
		JCheckBox chckbxWednesday = new JCheckBox("Wednesday");
		chckbxWednesday.setSelected(true);
		GridBagConstraints gbc_chckbxWednesday = new GridBagConstraints();
		gbc_chckbxWednesday.anchor = GridBagConstraints.WEST;
		gbc_chckbxWednesday.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxWednesday.gridx = 1;
		gbc_chckbxWednesday.gridy = 7;
		panelAddClient.add(chckbxWednesday, gbc_chckbxWednesday);
		
		JCheckBox chckbxThursday = new JCheckBox("Thursday");
		chckbxThursday.setSelected(true);
		GridBagConstraints gbc_chckbxThursday = new GridBagConstraints();
		gbc_chckbxThursday.anchor = GridBagConstraints.WEST;
		gbc_chckbxThursday.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxThursday.gridx = 1;
		gbc_chckbxThursday.gridy = 8;
		panelAddClient.add(chckbxThursday, gbc_chckbxThursday);
		
		JCheckBox chckbxFriday = new JCheckBox("Friday");
		chckbxFriday.setSelected(true);
		GridBagConstraints gbc_chckbxFriday = new GridBagConstraints();
		gbc_chckbxFriday.anchor = GridBagConstraints.WEST;
		gbc_chckbxFriday.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxFriday.gridx = 1;
		gbc_chckbxFriday.gridy = 9;
		panelAddClient.add(chckbxFriday, gbc_chckbxFriday);
		
		JCheckBox chckbxSaturday = new JCheckBox("Saturday");
		chckbxSaturday.setSelected(true);
		GridBagConstraints gbc_chckbxSaturday = new GridBagConstraints();
		gbc_chckbxSaturday.anchor = GridBagConstraints.WEST;
		gbc_chckbxSaturday.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxSaturday.gridx = 1;
		gbc_chckbxSaturday.gridy = 10;
		panelAddClient.add(chckbxSaturday, gbc_chckbxSaturday);
		
		JCheckBox chckbxSunday = new JCheckBox("Sunday");
		chckbxSunday.setSelected(true);
		GridBagConstraints gbc_chckbxSunday = new GridBagConstraints();
		gbc_chckbxSunday.anchor = GridBagConstraints.WEST;
		gbc_chckbxSunday.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxSunday.gridx = 1;
		gbc_chckbxSunday.gridy = 11;
		panelAddClient.add(chckbxSunday, gbc_chckbxSunday);
		
		JLabel lblNotes = new JLabel("Notes");
		GridBagConstraints gbc_lblNotes = new GridBagConstraints();
		gbc_lblNotes.anchor = GridBagConstraints.EAST;
		gbc_lblNotes.insets = new Insets(0, 0, 5, 5);
		gbc_lblNotes.gridx = 0;
		gbc_lblNotes.gridy = 12;
		panelAddClient.add(lblNotes, gbc_lblNotes);
		
		JTextArea txtNotes = new JTextArea();
		txtNotes.setToolTipText("Add any special notes here.");
		txtNotes.setWrapStyleWord(true);
		txtNotes.setLineWrap(true);
		GridBagConstraints gbc_txtNotes = new GridBagConstraints();
		gbc_txtNotes.insets = new Insets(0, 0, 5, 0);
		gbc_txtNotes.fill = GridBagConstraints.BOTH;
		gbc_txtNotes.gridx = 1;
		gbc_txtNotes.gridy = 12;
		panelAddClient.add(txtNotes, gbc_txtNotes);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.WEST;
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 13;
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean proceed = true;
				proceed = validateFields();
				if (proceed) {
					addClient();
				}
			}

			private boolean validateFields(){
				boolean valid = true;
				String fName = txtFname.getText();
				String lName = txtLname.getText();

				if(fName.length() > 20){
					show_error("First Name", "First name too long");
					txtFname.setText("");
					valid = false;
				} else if (fName.length() < 1){
					show_error("First Name", "Please enter a first name");
					valid = false;
				} else if(lName.length() > 25){
					show_error("Last Name", "Last name too long");
					txtLname.setText("");
					valid = false;
				} else if (lName.length() < 1){
					int selection = show_warning("Last Name", "Are you sure the last name should be blank?");
					if (selection == 1) {
						valid = false;
						System.out.println(valid);
					} else {
						txtLname.setText(" ");
					}
				} 	
				return valid;
			}
			
			private void addClient() {
				boolean success;
				String fName = txtFname.getText();
				String lName = txtLname.getText();
				String phone_number = ftfPhone.getText();
				String notes = txtNotes.getText();
				boolean monday = chckbxMonday.isSelected();
				boolean tuesday = chckbxTuesday.isSelected();
				boolean wednesday = chckbxWednesday.isSelected();
				boolean thursday = chckbxThursday.isSelected();
				boolean friday = chckbxFriday.isSelected();
				boolean saturday = chckbxSaturday.isSelected();
				boolean sunday = chckbxSunday.isSelected();

				currUser = bWelcomeScreenWindow.getCurrUser();
				//System.out.println("Current User from cMainDashboardWindow class: " + currUser);
				Map<String, Boolean> eligibility = new HashMap<String, Boolean>() ;
				eligibility.put("monday", monday);
				eligibility.put("tuesday", tuesday);
				eligibility.put("wednesday", wednesday);
				eligibility.put("thursday", thursday);
				eligibility.put("friday", friday);
				eligibility.put("saturday", saturday);
				eligibility.put("sunday", sunday);
				if(!notes.isEmpty()) {
					success = zDatabaseHandlerBackend.addClient(fName, lName, phone_number, eligibility, notes);
				} else {
					success = zDatabaseHandlerBackend.addClient(fName, lName, phone_number, eligibility);
				}
				if(success){
					txtFname.setText("");
					txtLname.setText("");
					ftfPhone.setText("");
					JOptionPane.showConfirmDialog(null, "Client " + fName + " " + lName + " added successfully.", "Success", -1);
					//UPDATE THE TABLE HERE
					//tableContentPane.updateTable();
				} else {
					JOptionPane.showConfirmDialog(null, "Client " + fName + " " + lName + " could not be added.", "Failure", -1);
				}
			}
		});
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("TO_DO: UPDATE clients set ... WHERE fName = AND lName = ...");
			}
		});
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.anchor = GridBagConstraints.WEST;
		gbc_btnUpdate.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate.gridx = 0;
		gbc_btnUpdate.gridy = 13;
		panelAddClient.add(btnUpdate, gbc_btnUpdate);
		
		panelAddClient.add(btnAdd, gbc_btnAdd);
		
/*		JButton btnSend = new JButton("Send");
		btnSend.setBounds(429, 311, 57, 23);
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				String[] toArray = new String[2];
				toArray[0] = "sitmiller@gmail.com";
				toArray[1] = "tam008@eagles.bridgewater.edu";
				String body = txtareaMessage.getText();
				String subject = txtSubject.getText();
				String from = "sitmiller@gmail.com";
				//String from = "test";
				
				String usernameArray[] = from.split("[@]");
				String username = usernameArray[0];
				System.out.println(username);
				//String username = "sitmiller";
				//String username = JOptionPane.showInputDialog("Please enter your email username: ");
				String password = JOptionPane.showInputDialog("Please enter your email password: ");
				//System.out.println(username);
				zzzzzzzzSendEmailBackend.setupAndSend(subject, body, from, password, toArray, username);				
			}
		});
*/		//recipientsPane.setLayout(null);
				
/*		recipientsPane.add(btnSend);
*/		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
		);
		getContentPane().setLayout(groupLayout);
	}
		
protected String generate_filter_query(String[] filters) {
	List full_filters = new ArrayList();
	String laundry_query = 
		  "SELECT ll.id, "
		+ "CONCAT(fName, ' ', lName) AS Name, "
		+ "CONCAT(drop_off) AS 'Dropped Off On', "
		+ "CONCAT(load_complete) AS 'Completed On', "
		+ "CONCAT(pick_up) AS 'Picked Up On', "
		+ "CONCAT(ll.notes) AS 'Note', "
		+ "CONCAT(c.id) AS 'Client ID' "
		+ "FROM laundry_loads ll "
		+ "JOIN clients c "
		+ "ON ll.client_id = c.id WHERE ";

	//Populate the list with the filters that the user indicated.
	for (int i=0; i<filters.length; i++) {
		if (!filters[i].isEmpty()) {
			full_filters.add(filters[i]);
		}
	}
	//Add the appropriate WHERE clauses to the query
	int list_length = full_filters.size();
	for (int i=0; i<list_length; i++) {
		laundry_query += full_filters.get(i);
		if(i+1 != list_length) {
			laundry_query += " AND ";
		}
	}
	debug.print("Filter Query: " + laundry_query);
		return laundry_query;
	}

protected void edit_laundry(String editType, int laundry_id, String client_id, String current_user) {
	boolean proceed = true;
	String[] options = {"Add This Note", "No Note"};
	debug.show_note_prompt("laundry_loads", String.valueOf(laundry_id), "Laundry Note", "Would you like to add a note to this laundry entry?", options, false);
	debug.show_note_prompt("clients", client_id, "Client Note", "Would you like to add a note to this client's profile?", options, false); 
	if(editType == "pickup") {
		System.out.println("pickingup");
		//Check to make sure the laundry is marked as complete.
		String check_complete = "SELECT load_complete FROM laundry_loads WHERE id = " + laundry_id;
		ResultSet complete_check = zDatabaseHandlerBackend.select(check_complete);
		try {
			complete_check.next();
			if(complete_check.getDate("load_complete") == null) {
				int choice = debug.show_warning("Laundry not completed.", "The laundry is not marked as complete. Would you like to mark it complete first?");
				if(choice == 0) {
					edit_laundry("markComplete", laundry_id, client_id, current_user);
				} else if(choice == 2) {
					proceed = false;
				}
			} 
			complete_check.close();
		} catch (SQLException e) {
			debug.show_error("Error retrieving laundry_load", "Could not retrieve laundry_load data.");
			e.printStackTrace();
		}
		if (proceed) {
			String mark_laundry_picked_up = "UPDATE laundry_loads SET pick_up = CURRENT_TIMESTAMP, pick_up_sig = '" + bWelcomeScreenWindow.getCurrUser() + "' WHERE id = " + laundry_id;
			zDatabaseHandlerBackend.updateEntry(mark_laundry_picked_up);
			eViewEditClientWindow.update_load_outstanding_flag(false, client_id);
		} else {
			debug.show_error("Action Canceled", "No edit will be made to the laundry load.");
		}
	} else if(editType == "markComplete") {
		String mark_laundry_complete = "UPDATE laundry_loads SET load_complete = CURRENT_TIMESTAMP, load_complete_sig = '" + bWelcomeScreenWindow.getCurrUser() + "' WHERE id = " + laundry_id;
		zDatabaseHandlerBackend.updateEntry(mark_laundry_complete);
	}
	
		
}

private static void create_laundry_table(JScrollPane scrollPane, String laundry_query) {
	try {
		laundryTable = new JTable(buildTableModel(laundry_query));
	} catch (SQLException e) {
		e.printStackTrace();
	}
	laundryTable.setBackground(Color.WHITE);
	laundryTable.setColumnSelectionAllowed(false);
	laundryTable.setRowSelectionAllowed(true);
	laundryTable.setFillsViewportHeight(true);
	laundryTable.setBorder(new MatteBorder(1, 2, 2, 2, (Color) new Color(0, 0, 0)));
	laundryTable.setAutoCreateRowSorter(true);
	laundryTable.setUpdateSelectionOnSort(true);
	laundryTable.addMouseListener(new MouseAdapter() {
		public void mousePressed(MouseEvent mouseEvent) {
			JTable table = (JTable) mouseEvent.getSource();
			Point point = mouseEvent.getPoint();
			int row = table.rowAtPoint(point);
			int column = table.columnAtPoint(point);
			if (mouseEvent.getClickCount() == 2) {
				if (column == 1) {
					//Bring up the client info page
					//Make the client info page a separate window.

					System.out.println("Client page");
				} else if (column == 5) {
					//Bring up the edit/view notes page
					int laundry_id = (int) laundryTable.getValueAt(laundryTable.getSelectedRow(), 0);
					String[] options = {"Save and Close", "Close"};
					debug.show_note_prompt("laundry_loads", String.valueOf(laundry_id), "Laundry Note", "<html>These are all the notes about the current load of laundry.<br>You can make changes and/or additions.<html>", options, true);
					//Refresh the table
					cMainDashboardWindow.update_table();
				} else {
					//Tell user they can't double click here.
					debug.show_error("No Double click", "You can't double click this column.");
				}
			}
		}
	});

	scrollPane.setViewportView(laundryTable);
}

private static void create_laundry_table(JScrollPane scrollPane) {
	String default_laundry_query = "SELECT laundry_loads.id, CONCAT(fName, ' ', lName) AS Name, CONCAT(drop_off) AS 'Dropped Off On', CONCAT(load_complete) AS 'Completed On', CONCAT(pick_up) AS 'Picked Up On', CONCAT(laundry_loads.notes) AS 'Note', CONCAT(clients.id) AS 'Client ID' FROM laundry_loads JOIN clients ON laundry_loads.client_id = clients.id";
	create_laundry_table(scrollPane, default_laundry_query);
}

private static DefaultTableModel buildTableModel(String laundry_query) throws SQLException {
	ResultSet rs = zDatabaseHandlerBackend.select(laundry_query);
	ResultSetMetaData metaData = rs.getMetaData();

	//names of columns:
	Vector<String> columnNames = new Vector<String>();
	int colCount = metaData.getColumnCount();
	for (int column = 1; column <= colCount; column++) {
		columnNames.add(metaData.getColumnName(column));
	}

	//data of the table
	Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	while (rs.next()) {
		Vector<Object> vector = new Vector<Object>();
		for (int columnIndex = 1; columnIndex <= colCount; columnIndex++) {
			if(columnIndex == 6) {
				if(rs.getObject(6) != null) {
					vector.add("yes");
				} else {
					vector.add("");
				}
			} else {
				vector.add(rs.getObject(columnIndex));
			}
		}
		data.add(vector);
	} rs.close();
		return new DefaultTableModel(data, columnNames) {
			private static final long serialVersionUID = 8365935879746194659L;
			@Override
		    //This makes it so users can't edit the table values.
		    public boolean isCellEditable(int i, int i1) {
		        return false;
		    }
		};
}

/*private static DefaultTableModel buildTableModel() throws SQLException {
	String default_laundry_query = "SELECT laundry_loads.id, CONCAT(fName, ' ', lName) AS Name, CONCAT(drop_off) AS 'Dropped Off On', CONCAT(load_complete) AS 'Completed On', CONCAT(pick_up) AS 'Picked Up On', CONCAT(laundry_loads.notes) AS 'Note', CONCAT(clients.id) AS 'Client ID' FROM laundry_loads JOIN clients ON laundry_loads.client_id = clients.id";
	return buildTableModel(default_laundry_query);
}
*/	
public static void update_table(){
	create_laundry_table(scrollPane);
}

private static void show_error(String title, Object message)
{
	JOptionPane.showConfirmDialog(null, message, title, -1);
}

private static int show_warning(String title, Object message)
{
	int selection = JOptionPane.showConfirmDialog(null, message, title, 0);
	// 0=yes; 1=no
	return selection;
}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cMainDashboardWindow frame = new cMainDashboardWindow();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}
