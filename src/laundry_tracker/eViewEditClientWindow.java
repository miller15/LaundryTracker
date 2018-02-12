package laundry_tracker;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class eViewEditClientWindow extends JFrame{
//This class is what will show when you want to edit a particular client's info. You are viewing their info and can make changes.
	
	private static final long serialVersionUID = 1L;
	//private static JFrame frame;
	private JPanel contentPane;
	private JPanel panel;
	private JFrame frmEditContact;
	private static JTextField txtFname;
	private static JTextField txtLname;
	private static JFormattedTextField txtPhone;
	private static JTextArea txtNotesClient;
	private static JCheckBox chkMonday;
	private static JCheckBox chkTuesday;
	private static JCheckBox chkWednesday;
	private static JCheckBox chkThursday;
	private static JCheckBox chkFriday;
	private static JCheckBox chkSaturday;
	private static JCheckBox chkSunday;
	private static JCheckBox chkEligibleToday;
	private static JCheckBox chkLoadOutstanding;


/**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					eViewEditClientWindow window = new eViewEditClientWindow(client_id);
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the application.
	 */
	public eViewEditClientWindow(int client_id) {
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		frmEditContact = new JFrame();
		frmEditContact.setTitle("View/UpdateContact");
		frmEditContact.setBounds(100, 100, 586, 685);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder()));
		frmEditContact.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblDescription = new JLabel("<html>Use this page to view a client's info.<br/>You can also make changes to the client.</html>");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 0, 5, 0);
		gbc_lblDescription.gridx = 1;
		gbc_lblDescription.gridy = 0;
		panel.add(lblDescription, gbc_lblDescription);
		
		JLabel lblFname = new JLabel("First Name");
		GridBagConstraints gbc_lblFname = new GridBagConstraints();
		gbc_lblFname.anchor = GridBagConstraints.EAST;
		gbc_lblFname.insets = new Insets(0, 0, 5, 5);
		gbc_lblFname.gridx = 0;
		gbc_lblFname.gridy = 1;
		panel.add(lblFname, gbc_lblFname);
		
		txtFname = new JTextField();
		txtFname.setColumns(10);
		GridBagConstraints gbc_txtFname = new GridBagConstraints();
		gbc_txtFname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFname.insets = new Insets(0, 0, 5, 0);
		gbc_txtFname.gridx = 1;
		gbc_txtFname.gridy = 1;
		panel.add(txtFname, gbc_txtFname);
		
		JLabel lblLname = new JLabel("Last Name");
		GridBagConstraints gbc_lblLname = new GridBagConstraints();
		gbc_lblLname.anchor = GridBagConstraints.EAST;
		gbc_lblLname.insets = new Insets(0, 0, 5, 5);
		gbc_lblLname.gridx = 0;
		gbc_lblLname.gridy = 2;
		panel.add(lblLname, gbc_lblLname);
		
		txtLname = new JTextField();
		txtLname.setColumns(10);
		GridBagConstraints gbc_txtLname = new GridBagConstraints();
		gbc_txtLname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLname.insets = new Insets(0, 0, 5, 0);
		gbc_txtLname.gridx = 1;
		gbc_txtLname.gridy = 2;
		panel.add(txtLname, gbc_txtLname);
		
		JLabel lblPhone = new JLabel("Phone Number");
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.EAST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 0;
		gbc_lblPhone.gridy = 3;
		panel.add(lblPhone, gbc_lblPhone);
		
		txtPhone = new JFormattedTextField((Object) null);
		txtPhone.setToolTipText("i.e. 703-345-5555");
		GridBagConstraints gbc_txtPhone = new GridBagConstraints();
		gbc_txtPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPhone.insets = new Insets(0, 0, 5, 0);
		gbc_txtPhone.gridx = 1;
		gbc_txtPhone.gridy = 3;
		panel.add(txtPhone, gbc_txtPhone);
		
		JLabel lblEligibility = new JLabel("Eligibility:");
		GridBagConstraints gbc_lblEligibility = new GridBagConstraints();
		gbc_lblEligibility.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblEligibility.insets = new Insets(0, 0, 5, 5);
		gbc_lblEligibility.gridx = 0;
		gbc_lblEligibility.gridy = 4;
		panel.add(lblEligibility, gbc_lblEligibility);
		
		JLabel lblExplanation = new JLabel("A checkmark means the client is allowed to drop off laundry that day.");
		GridBagConstraints gbc_lblExplanation = new GridBagConstraints();
		gbc_lblExplanation.anchor = GridBagConstraints.SOUTH;
		gbc_lblExplanation.insets = new Insets(0, 0, 5, 0);
		gbc_lblExplanation.gridx = 1;
		gbc_lblExplanation.gridy = 4;
		panel.add(lblExplanation, gbc_lblExplanation);
		
		chkMonday = new JCheckBox("Monday");
		GridBagConstraints gbc_chkMonday = new GridBagConstraints();
		gbc_chkMonday.anchor = GridBagConstraints.WEST;
		gbc_chkMonday.insets = new Insets(0, 0, 5, 0);
		gbc_chkMonday.gridx = 1;
		gbc_chkMonday.gridy = 5;
		panel.add(chkMonday, gbc_chkMonday);
		
		chkTuesday = new JCheckBox("Tuesday");
		GridBagConstraints gbc_chkTuesday = new GridBagConstraints();
		gbc_chkTuesday.anchor = GridBagConstraints.WEST;
		gbc_chkTuesday.insets = new Insets(0, 0, 5, 0);
		gbc_chkTuesday.gridx = 1;
		gbc_chkTuesday.gridy = 6;
		panel.add(chkTuesday, gbc_chkTuesday);
		
		chkWednesday = new JCheckBox("Wednesday");
		GridBagConstraints gbc_chkWednesday = new GridBagConstraints();
		gbc_chkWednesday.anchor = GridBagConstraints.WEST;
		gbc_chkWednesday.insets = new Insets(0, 0, 5, 0);
		gbc_chkWednesday.gridx = 1;
		gbc_chkWednesday.gridy = 7;
		panel.add(chkWednesday, gbc_chkWednesday);
		
		chkThursday = new JCheckBox("Thursday");
		GridBagConstraints gbc_chkThursday = new GridBagConstraints();
		gbc_chkThursday.anchor = GridBagConstraints.WEST;
		gbc_chkThursday.insets = new Insets(0, 0, 5, 0);
		gbc_chkThursday.gridx = 1;
		gbc_chkThursday.gridy = 8;
		panel.add(chkThursday, gbc_chkThursday);
		
		chkFriday = new JCheckBox("Friday");
		GridBagConstraints gbc_chkFriday = new GridBagConstraints();
		gbc_chkFriday.anchor = GridBagConstraints.WEST;
		gbc_chkFriday.insets = new Insets(0, 0, 5, 0);
		gbc_chkFriday.gridx = 1;
		gbc_chkFriday.gridy = 9;
		panel.add(chkFriday, gbc_chkFriday);
		
		chkSaturday = new JCheckBox("Saturday");
		GridBagConstraints gbc_chkSaturday = new GridBagConstraints();
		gbc_chkSaturday.anchor = GridBagConstraints.WEST;
		gbc_chkSaturday.insets = new Insets(0, 0, 5, 0);
		gbc_chkSaturday.gridx = 1;
		gbc_chkSaturday.gridy = 10;
		panel.add(chkSaturday, gbc_chkSaturday);
		
		chkSunday = new JCheckBox("Sunday");
		GridBagConstraints gbc_chkSunday = new GridBagConstraints();
		gbc_chkSunday.anchor = GridBagConstraints.WEST;
		gbc_chkSunday.insets = new Insets(0, 0, 5, 0);
		gbc_chkSunday.gridx = 1;
		gbc_chkSunday.gridy = 11;
		panel.add(chkSunday, gbc_chkSunday);
		
		chkEligibleToday = new JCheckBox("Eligible Today");
		GridBagConstraints gbc_chkEligibleToday = new GridBagConstraints();
		gbc_chkEligibleToday.anchor = GridBagConstraints.WEST;
		gbc_chkEligibleToday.insets = new Insets(0, 0, 5, 0);
		gbc_chkEligibleToday.gridx = 1;
		gbc_chkEligibleToday.gridy = 12;
		panel.add(chkEligibleToday, gbc_chkEligibleToday);

		chkLoadOutstanding = new JCheckBox("Load In Progress");
		chkLoadOutstanding.setToolTipText("The client has a load that's either not completed or not picked up");
		GridBagConstraints gbc_chkLoadOutstanding = new GridBagConstraints();
		gbc_chkLoadOutstanding.anchor = GridBagConstraints.WEST;
		gbc_chkLoadOutstanding.insets = new Insets(0, 0, 5, 0);
		gbc_chkLoadOutstanding.gridx = 1;
		gbc_chkLoadOutstanding.gridy = 13;
		panel.add(chkLoadOutstanding, gbc_chkLoadOutstanding);
		
		JLabel lblNotes = new JLabel("Notes");
		GridBagConstraints gbc_lblNotes = new GridBagConstraints();
		gbc_lblNotes.anchor = GridBagConstraints.EAST;
		gbc_lblNotes.insets = new Insets(0, 0, 5, 5);
		gbc_lblNotes.gridx = 0;
		gbc_lblNotes.gridy = 14;
		panel.add(lblNotes, gbc_lblNotes);
		
		JScrollPane scrollPaneNotes = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 14;
		panel.add(scrollPaneNotes, gbc_scrollPane);
		
		txtNotesClient = new JTextArea();
		txtNotesClient.setWrapStyleWord(true);
		txtNotesClient.setToolTipText("Add any special notes here.");
		txtNotesClient.setLineWrap(true);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 15;
		scrollPaneNotes.setViewportView(txtNotesClient);
				
		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmEditContact.dispose();
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 15;
		panel.add(button, gbc_button);
		
		JButton button_1 = new JButton("Submit Changes");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean proceed = validateFields();
				if(proceed) {
					update_client(client_id);
					populate_window(client_id);
				}
				
			}
		});
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 15;
		panel.add(button_1, gbc_button_1);
		frmEditContact.setVisible(true);

		populate_window(client_id);
		
	}

	public static void populate_window(int client_id) {
		String client_query = "SELECT * FROM clients WHERE id = " + client_id;
		ResultSet client_info = zDatabaseHandlerBackend.select(client_query);
		try {
			//Populate the window with the client's info from the db.
			client_info.next();
			client_id = client_info.getInt("id");
			txtFname.setText(client_info.getString("fName"));
			txtLname.setText(client_info.getString("lName"));
			chkMonday.setSelected(client_info.getBoolean("monday"));
			chkTuesday.setSelected(client_info.getBoolean("tuesday"));
			chkWednesday.setSelected(client_info.getBoolean("wednesday"));
			chkThursday.setSelected(client_info.getBoolean("thursday"));
			chkFriday.setSelected(client_info.getBoolean("friday"));
			chkSaturday.setSelected(client_info.getBoolean("saturday"));
			chkSunday.setSelected(client_info.getBoolean("sunday"));
			chkEligibleToday.setSelected(client_info.getBoolean("eligible_today"));
			chkLoadOutstanding.setSelected(client_info.getBoolean("load_outstanding"));
			txtNotesClient.setText(client_info.getString("notes"));
		} catch (SQLException e) {
			//No client existed
			debug.show_error("No Client Exists", "Error: Cannot retrieve client for viewing and editing.");
		}
	}
	
	
	private boolean validateFields(){
		boolean valid = true;
		String fName = txtFname.getText();
		String lName = txtLname.getText();

		if(fName.length() > 20){
			debug.show_error("First Name", "First name too long");
			valid = false;
		} else if (fName.length() < 1){
			debug.show_error("First Name", "Please enter a first name");
			valid = false;
		} else if(lName.length() > 25){
			debug.show_error("Last Name", "Last name too long");
			valid = false;
		} else if (lName.length() < 1){
			int selection = debug.show_warning("Last Name", "Are you sure the last name should be blank?");
			if (selection == 1) {
				valid = false;
			} else {
				txtLname.setText(" ");
			}
		} 	
		return valid;
	}

	
	public static void update_client(int client_id) {
		boolean success;
		String fName = txtFname.getText();
		String lName = txtLname.getText();
		String phone_number = txtPhone.getText();
		boolean monday = chkMonday.isSelected();
		boolean tuesday = chkTuesday.isSelected();
		boolean wednesday = chkWednesday.isSelected();
		boolean thursday = chkThursday.isSelected();
		boolean friday = chkFriday.isSelected();
		boolean saturday = chkSaturday.isSelected();
		boolean sunday = chkSunday.isSelected();
		boolean eligible_today = chkEligibleToday.isSelected();
		boolean load_outstanding = chkLoadOutstanding.isSelected();
		String notes = txtNotesClient.getText();

		Map<String, Boolean> eligibility = new HashMap<String, Boolean>() ;
		eligibility.put("monday", monday);
		eligibility.put("tuesday", tuesday);
		eligibility.put("wednesday", wednesday);
		eligibility.put("thursday", thursday);
		eligibility.put("friday", friday);
		eligibility.put("saturday", saturday);
		eligibility.put("sunday", sunday);
		eligibility.put("today", eligible_today);
		eligibility.put("load_outstanding", load_outstanding);

		if(notes.isEmpty()) {
			notes = null;
		} 
		success = zDatabaseHandlerBackend.updateClient(client_id, fName, lName, phone_number, eligibility, notes);
		if(success){
			JOptionPane.showConfirmDialog(null, "Client " + fName + " " + lName + " added successfully.", "Success", -1);
		} else {
			JOptionPane.showConfirmDialog(null, "Client " + fName + " " + lName + " could not be added.", "Failure", -1);
		}
	}
	
	public static void update_eligible_today_flag(int client_id) {
		String updateEligibleToday = "UPDATE clients SET eligible_today = ";
		String selectClientLoads = "SELECT * FROM laundry_loads WHERE client_id = " + client_id;
		ResultSet clientLoads = zDatabaseHandlerBackend.select(selectClientLoads);
		try {
			while(clientLoads.next()) {
				String date = clientLoads.getString("drop_off");
				LocalDateTime dbDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				String currentDay = dateFormat.format(currentDate);
				String dbDay = dateFormat.format(dbDateTime);
				if (dbDay.equals(currentDay)) {
					updateEligibleToday += false + " WHERE id = " + client_id;
				} else {
					updateEligibleToday += true + " WHERE id = " + client_id;
				}
			}
		} catch (Exception e) {
			//Client didn't even have any laundry in the system.
			updateEligibleToday += true + " WHERE id = " + client_id;
		}
		zDatabaseHandlerBackend.update(updateEligibleToday);
	}

	public static void update_load_outstanding_flag(String client_id) {
		String updateLoadOutstanding = "UPDATE clients SET load_outstanding = ";
		boolean value = false;
		String selectClientLoads = "SELECT * FROM laundry_loads WHERE client_id = " + client_id;
		ResultSet clientLoads = zDatabaseHandlerBackend.select(selectClientLoads);
		try {
			while(clientLoads.next()) {
				String dropoff = clientLoads.getString("drop_off");
				String completed = clientLoads.getString("load_complete");
				String pickedup = clientLoads.getString("pick_up");
				if(dropoff != null && (completed == null || pickedup == null)) {
					//There is a load for this client that's been dropped off but not fully completed yet.
					value = true;
				}
			}
		} catch (Exception e){
			//Client didn't even have any laundry in the system.
			System.out.println(e);
		}
		updateLoadOutstanding += value + " WHERE id = " + client_id;
		zDatabaseHandlerBackend.update(updateLoadOutstanding);
	}
}
