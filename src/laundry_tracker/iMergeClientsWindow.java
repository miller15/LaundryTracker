package laundry_tracker;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.Font;
import javax.swing.SwingConstants;

public class iMergeClientsWindow {

	private JFrame frmMergeClients;
	private static JTextField txtFname1;
	private static JTextField txtFname2;
	private static JTextField txtLname1, txtLname2;
	private static JFormattedTextField txtPhone1, txtPhone2;
	private static JTextArea txtNotesClient1, txtNotesClient2;
	private static JCheckBox chkMonday1, chkMonday2;
	private static JCheckBox chkTuesday1, chkTuesday2;
	private static JCheckBox chkWednesday1, chkWednesday2;
	private static JCheckBox chkThursday1, chkThursday2;
	private static JCheckBox chkFriday1, chkFriday2;
	private static JCheckBox chkSaturday1, chkSaturday2;
	private static JCheckBox chkSunday1, chkSunday2;
	private static JCheckBox chkEligibleToday1, chkEligibleToday2;
	private static JCheckBox chkLoadOutstanding1, chkLoadOutstanding2;
	private JButton btnSubmit;


	/**
	 * Launch the application.
	 */
	public static void main(int client_id1, int client_id2) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					iMergeClientsWindow window = new iMergeClientsWindow(client_id1, client_id2);
					window.frmMergeClients.setVisible(true);
				} catch (Exception e) {
					System.out.println("Oops, didnt get far merging :P");
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public iMergeClientsWindow(int id1, int id2) {
		/*JFrame mergeFrame = */initialize(id1, id2);
		//mergeFrame.setVisible(true);
		populate_client1(select_client_info(id1));
		populate_client2(select_client_info(id2));
		frmMergeClients.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int id1, int id2) {
		frmMergeClients = new JFrame();
		frmMergeClients.setTitle("Merge Clients");
/*		frmMergeClients.setBounds(100, 100, 1132, 758);
*/		frmMergeClients.setBounds(100, 100, 1200, 900);
		frmMergeClients.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMergeClients.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblDescription = new JLabel("<html>Use this page to merge two clients into one.<br/>"
				+ "Review each client's info and choose the one with the most accurate info.<br/>"
				+ "When there are differences between the two clients, the system will keep the info of the client you choose and disregard the other client.<br/>"
				+ "You may also edit the info fields for the client you choose to keep.</html>");
		lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
		frmMergeClients.getContentPane().add(lblDescription, BorderLayout.NORTH);
		
		JRadioButton rdbtnKeepClient1 = new JRadioButton("Keep client 1");
		frmMergeClients.getContentPane().add(rdbtnKeepClient1, BorderLayout.WEST);
		JRadioButton rdbtnKeepClient2 = new JRadioButton("Keep client 2");
		frmMergeClients.getContentPane().add(rdbtnKeepClient2, BorderLayout.EAST);
		
	    ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnKeepClient1);
	    group.add(rdbtnKeepClient2);
	    rdbtnKeepClient1.setActionCommand("Keep client 1");
	    rdbtnKeepClient2.setActionCommand("Keep client 2");
	    	    
		JSplitPane splitPane = new JSplitPane();
		frmMergeClients.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JPanel panel1 = new JPanel();
		splitPane.setLeftComponent(panel1);
		GridBagLayout gbl_panel1 = new GridBagLayout();
		gbl_panel1.columnWidths = new int[]{0, 51, 0, 0};
		gbl_panel1.rowHeights = new int[]{0, 14, 0, 0};
		gbl_panel1.columnWeights = new double[]{};
		gbl_panel1.rowWeights = new double[]{};
		panel1.setLayout(gbl_panel1);
		{
			JLabel lblClient1 = new JLabel("Client 1");
			lblClient1.setFont(new Font("Tahoma", Font.BOLD, 11));
			GridBagConstraints gbc_lblClient1 = new GridBagConstraints();
			gbc_lblClient1.gridwidth = 4;
			gbc_lblClient1.insets = new Insets(0, 0, 5, 5);
			gbc_lblClient1.gridx = 0;
			gbc_lblClient1.gridy = 0;
			panel1.add(lblClient1, gbc_lblClient1);
			
			JLabel lblFname = new JLabel("First Name");
			GridBagConstraints gbc_lblFname = new GridBagConstraints();
			gbc_lblFname.anchor = GridBagConstraints.EAST;
			gbc_lblFname.insets = new Insets(0, 0, 5, 5);
			gbc_lblFname.gridx = 0;
			gbc_lblFname.gridy = 1;
			panel1.add(lblFname, gbc_lblFname);
			
			txtFname1 = new JTextField();
			txtFname1.setColumns(10);
			GridBagConstraints gbc_txtFname1 = new GridBagConstraints();
			gbc_txtFname1.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtFname1.insets = new Insets(0, 0, 5, 0);
			gbc_txtFname1.gridx = 1;
			gbc_txtFname1.gridy = 1;
			panel1.add(txtFname1, gbc_txtFname1);
			
			JLabel lblLname = new JLabel("Last Name");
			GridBagConstraints gbc_lblLname = new GridBagConstraints();
			gbc_lblLname.anchor = GridBagConstraints.EAST;
			gbc_lblLname.insets = new Insets(0, 0, 5, 5);
			gbc_lblLname.gridx = 0;
			gbc_lblLname.gridy = 2;
			panel1.add(lblLname, gbc_lblLname);
			
			txtLname1 = new JTextField();
			txtLname1.setColumns(10);
			GridBagConstraints gbc_txtLname1 = new GridBagConstraints();
			gbc_txtLname1.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtLname1.insets = new Insets(0, 0, 5, 0);
			gbc_txtLname1.gridx = 1;
			gbc_txtLname1.gridy = 2;
			panel1.add(txtLname1, gbc_txtLname1);
			
			JLabel lblPhone = new JLabel("Phone Number");
			GridBagConstraints gbc_lblPhone = new GridBagConstraints();
			gbc_lblPhone.anchor = GridBagConstraints.EAST;
			gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
			gbc_lblPhone.gridx = 0;
			gbc_lblPhone.gridy = 3;
			panel1.add(lblPhone, gbc_lblPhone);
			
			txtPhone1 = new JFormattedTextField((Object) null);
			txtPhone1.setToolTipText("i.e. 703-345-5555");
			GridBagConstraints gbc_txtPhone1 = new GridBagConstraints();
			gbc_txtPhone1.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPhone1.insets = new Insets(0, 0, 5, 0);
			gbc_txtPhone1.gridx = 1;
			gbc_txtPhone1.gridy = 3;
			panel1.add(txtPhone1, gbc_txtPhone1);
			
			JLabel lblEligibility = new JLabel("Eligibility:");
			GridBagConstraints gbc_lblEligibility = new GridBagConstraints();
			gbc_lblEligibility.anchor = GridBagConstraints.SOUTHEAST;
			gbc_lblEligibility.insets = new Insets(0, 0, 5, 5);
			gbc_lblEligibility.gridx = 0;
			gbc_lblEligibility.gridy = 4;
			panel1.add(lblEligibility, gbc_lblEligibility);
			
			JLabel lblExplanation = new JLabel("A checkmark means the client is allowed to drop off laundry that day.");
			GridBagConstraints gbc_lblExplanation = new GridBagConstraints();
			gbc_lblExplanation.anchor = GridBagConstraints.SOUTH;
			gbc_lblExplanation.insets = new Insets(0, 0, 5, 0);
			gbc_lblExplanation.gridx = 1;
			gbc_lblExplanation.gridy = 4;
			panel1.add(lblExplanation, gbc_lblExplanation);
			
			chkMonday1 = new JCheckBox("Monday");
			GridBagConstraints gbc_chkMonday1 = new GridBagConstraints();
			gbc_chkMonday1.anchor = GridBagConstraints.WEST;
			gbc_chkMonday1.insets = new Insets(0, 0, 5, 0);
			gbc_chkMonday1.gridx = 1;
			gbc_chkMonday1.gridy = 5;
			panel1.add(chkMonday1, gbc_chkMonday1);
			
			chkTuesday1 = new JCheckBox("Tuesday");
			GridBagConstraints gbc_chkTuesday1 = new GridBagConstraints();
			gbc_chkTuesday1.anchor = GridBagConstraints.WEST;
			gbc_chkTuesday1.insets = new Insets(0, 0, 5, 0);
			gbc_chkTuesday1.gridx = 1;
			gbc_chkTuesday1.gridy = 6;
			panel1.add(chkTuesday1, gbc_chkTuesday1);
			
			chkWednesday1 = new JCheckBox("Wednesday");
			GridBagConstraints gbc_chkWednesday1 = new GridBagConstraints();
			gbc_chkWednesday1.anchor = GridBagConstraints.WEST;
			gbc_chkWednesday1.insets = new Insets(0, 0, 5, 0);
			gbc_chkWednesday1.gridx = 1;
			gbc_chkWednesday1.gridy = 7;
			panel1.add(chkWednesday1, gbc_chkWednesday1);
			
			chkThursday1 = new JCheckBox("Thursday");
			GridBagConstraints gbc_chkThursday1 = new GridBagConstraints();
			gbc_chkThursday1.anchor = GridBagConstraints.WEST;
			gbc_chkThursday1.insets = new Insets(0, 0, 5, 0);
			gbc_chkThursday1.gridx = 1;
			gbc_chkThursday1.gridy = 8;
			panel1.add(chkThursday1, gbc_chkThursday1);
			
			chkFriday1 = new JCheckBox("Friday");
			GridBagConstraints gbc_chkFriday1 = new GridBagConstraints();
			gbc_chkFriday1.anchor = GridBagConstraints.WEST;
			gbc_chkFriday1.insets = new Insets(0, 0, 5, 0);
			gbc_chkFriday1.gridx = 1;
			gbc_chkFriday1.gridy = 9;
			panel1.add(chkFriday1, gbc_chkFriday1);
			
			chkSaturday1 = new JCheckBox("Saturday");
			GridBagConstraints gbc_chkSaturday1 = new GridBagConstraints();
			gbc_chkSaturday1.anchor = GridBagConstraints.WEST;
			gbc_chkSaturday1.insets = new Insets(0, 0, 5, 0);
			gbc_chkSaturday1.gridx = 1;
			gbc_chkSaturday1.gridy = 10;
			panel1.add(chkSaturday1, gbc_chkSaturday1);
			
			chkSunday1 = new JCheckBox("Sunday");
			GridBagConstraints gbc_chkSunday1 = new GridBagConstraints();
			gbc_chkSunday1.anchor = GridBagConstraints.WEST;
			gbc_chkSunday1.insets = new Insets(0, 0, 5, 0);
			gbc_chkSunday1.gridx = 1;
			gbc_chkSunday1.gridy = 11;
			panel1.add(chkSunday1, gbc_chkSunday1);
			
			chkEligibleToday1 = new JCheckBox("Eligible Today");
			GridBagConstraints gbc_chkEligibleToday1 = new GridBagConstraints();
			gbc_chkEligibleToday1.anchor = GridBagConstraints.WEST;
			gbc_chkEligibleToday1.insets = new Insets(0, 0, 5, 0);
			gbc_chkEligibleToday1.gridx = 1;
			gbc_chkEligibleToday1.gridy = 12;
			panel1.add(chkEligibleToday1, gbc_chkEligibleToday1);
	
			chkLoadOutstanding1 = new JCheckBox("Load In Progress");
			chkLoadOutstanding1.setToolTipText("The client has a load that's either not completed or not picked up");
			GridBagConstraints gbc_chkLoadOutstanding1 = new GridBagConstraints();
			gbc_chkLoadOutstanding1.anchor = GridBagConstraints.WEST;
			gbc_chkLoadOutstanding1.insets = new Insets(0, 0, 5, 0);
			gbc_chkLoadOutstanding1.gridx = 1;
			gbc_chkLoadOutstanding1.gridy = 13;
			panel1.add(chkLoadOutstanding1, gbc_chkLoadOutstanding1);
			
			JLabel lblNotes = new JLabel("Notes");
			GridBagConstraints gbc_lblNotes = new GridBagConstraints();
			gbc_lblNotes.anchor = GridBagConstraints.EAST;
			gbc_lblNotes.insets = new Insets(0, 0, 5, 5);
			gbc_lblNotes.gridx = 0;
			gbc_lblNotes.gridy = 14;
			panel1.add(lblNotes, gbc_lblNotes);
			
			JScrollPane scrollPaneNotes = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 1;
			gbc_scrollPane.gridy = 14;
			panel1.add(scrollPaneNotes, gbc_scrollPane);
			
			txtNotesClient1 = new JTextArea();
			txtNotesClient1.setRows(12);
			txtNotesClient1.setWrapStyleWord(true);
			txtNotesClient1.setToolTipText("Add any special notes here.");
			txtNotesClient1.setLineWrap(true);
			GridBagConstraints gbc_textArea = new GridBagConstraints();
			gbc_textArea.fill = GridBagConstraints.BOTH;
			gbc_textArea.insets = new Insets(0, 0, 5, 0);
			gbc_textArea.gridx = 1;
			gbc_textArea.gridy = 15;
			scrollPaneNotes.setViewportView(txtNotesClient1);
		}		
		
		JPanel panel2 = new JPanel();
		splitPane.setRightComponent(panel2);
		GridBagLayout gbl_panel2 = new GridBagLayout();
		gbl_panel2.columnWidths = new int[]{0, 51, 0, 0};
		gbl_panel2.rowHeights = new int[]{0, 14, 0, 0};
		gbl_panel2.columnWeights = new double[]{};
		gbl_panel2.rowWeights = new double[]{};
		panel2.setLayout(gbl_panel2);
		{
			JLabel lblClient2 = new JLabel("Client 2");
			lblClient2.setFont(new Font("Tahoma", Font.BOLD, 11));
			GridBagConstraints gbc_lblClient2 = new GridBagConstraints();
			gbc_lblClient2.gridwidth = 4;
			gbc_lblClient2.insets = new Insets(0, 0, 5, 5);
			gbc_lblClient2.gridx = 0;
			gbc_lblClient2.gridy = 0;
			panel2.add(lblClient2, gbc_lblClient2);
			
			JLabel lblFname = new JLabel("First Name");
			GridBagConstraints gbc_lblFname = new GridBagConstraints();
			gbc_lblFname.anchor = GridBagConstraints.EAST;
			gbc_lblFname.insets = new Insets(0, 0, 5, 5);
			gbc_lblFname.gridx = 0;
			gbc_lblFname.gridy = 1;
			panel2.add(lblFname, gbc_lblFname);
			
			txtFname2 = new JTextField();
			txtFname2.setColumns(10);
			GridBagConstraints gbc_txtFname2 = new GridBagConstraints();
			gbc_txtFname2.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtFname2.insets = new Insets(0, 0, 5, 0);
			gbc_txtFname2.gridx = 1;
			gbc_txtFname2.gridy = 1;
			panel2.add(txtFname2, gbc_txtFname2);
			
			JLabel lblLname = new JLabel("Last Name");
			GridBagConstraints gbc_lblLname = new GridBagConstraints();
			gbc_lblLname.anchor = GridBagConstraints.EAST;
			gbc_lblLname.insets = new Insets(0, 0, 5, 5);
			gbc_lblLname.gridx = 0;
			gbc_lblLname.gridy = 2;
			panel2.add(lblLname, gbc_lblLname);
			
			txtLname2 = new JTextField();
			txtLname2.setColumns(20);
			GridBagConstraints gbc_txtLname2 = new GridBagConstraints();
			gbc_txtLname2.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtLname2.insets = new Insets(0, 0, 5, 0);
			gbc_txtLname2.gridx = 1;
			gbc_txtLname2.gridy = 2;
			panel2.add(txtLname2, gbc_txtLname2);
			
			JLabel lblPhone = new JLabel("Phone Number");
			GridBagConstraints gbc_lblPhone = new GridBagConstraints();
			gbc_lblPhone.anchor = GridBagConstraints.EAST;
			gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
			gbc_lblPhone.gridx = 0;
			gbc_lblPhone.gridy = 3;
			panel2.add(lblPhone, gbc_lblPhone);
			
			txtPhone2 = new JFormattedTextField((Object) null);
			txtPhone2.setToolTipText("i.e. 703-345-5555");
			GridBagConstraints gbc_txtPhone2 = new GridBagConstraints();
			gbc_txtPhone2.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPhone2.insets = new Insets(0, 0, 5, 0);
			gbc_txtPhone2.gridx = 1;
			gbc_txtPhone2.gridy = 3;
			panel2.add(txtPhone2, gbc_txtPhone2);
			
			JLabel lblEligibility = new JLabel("Eligibility:");
			GridBagConstraints gbc_lblEligibility = new GridBagConstraints();
			gbc_lblEligibility.anchor = GridBagConstraints.SOUTHEAST;
			gbc_lblEligibility.insets = new Insets(0, 0, 5, 5);
			gbc_lblEligibility.gridx = 0;
			gbc_lblEligibility.gridy = 4;
			panel2.add(lblEligibility, gbc_lblEligibility);
			
			JLabel lblExplanation = new JLabel("A checkmark means the client is allowed to drop off laundry that day.");
			GridBagConstraints gbc_lblExplanation = new GridBagConstraints();
			gbc_lblExplanation.anchor = GridBagConstraints.SOUTH;
			gbc_lblExplanation.insets = new Insets(0, 0, 5, 0);
			gbc_lblExplanation.gridx = 1;
			gbc_lblExplanation.gridy = 4;
			panel2.add(lblExplanation, gbc_lblExplanation);
			
			chkMonday2 = new JCheckBox("Monday");
			GridBagConstraints gbc_chkMonday2 = new GridBagConstraints();
			gbc_chkMonday2.anchor = GridBagConstraints.WEST;
			gbc_chkMonday2.insets = new Insets(0, 0, 5, 0);
			gbc_chkMonday2.gridx = 1;
			gbc_chkMonday2.gridy = 5;
			panel2.add(chkMonday2, gbc_chkMonday2);
			
			chkTuesday2 = new JCheckBox("Tuesday");
			GridBagConstraints gbc_chkTuesday2 = new GridBagConstraints();
			gbc_chkTuesday2.anchor = GridBagConstraints.WEST;
			gbc_chkTuesday2.insets = new Insets(0, 0, 5, 0);
			gbc_chkTuesday2.gridx = 1;
			gbc_chkTuesday2.gridy = 6;
			panel2.add(chkTuesday2, gbc_chkTuesday2);
			
			chkWednesday2 = new JCheckBox("Wednesday");
			GridBagConstraints gbc_chkWednesday2 = new GridBagConstraints();
			gbc_chkWednesday2.anchor = GridBagConstraints.WEST;
			gbc_chkWednesday2.insets = new Insets(0, 0, 5, 0);
			gbc_chkWednesday2.gridx = 1;
			gbc_chkWednesday2.gridy = 7;
			panel2.add(chkWednesday2, gbc_chkWednesday2);
			
			chkThursday2 = new JCheckBox("Thursday");
			GridBagConstraints gbc_chkThursday2 = new GridBagConstraints();
			gbc_chkThursday2.anchor = GridBagConstraints.WEST;
			gbc_chkThursday2.insets = new Insets(0, 0, 5, 0);
			gbc_chkThursday2.gridx = 1;
			gbc_chkThursday2.gridy = 8;
			panel2.add(chkThursday2, gbc_chkThursday2);
			
			chkFriday2 = new JCheckBox("Friday");
			GridBagConstraints gbc_chkFriday2 = new GridBagConstraints();
			gbc_chkFriday2.anchor = GridBagConstraints.WEST;
			gbc_chkFriday2.insets = new Insets(0, 0, 5, 0);
			gbc_chkFriday2.gridx = 1;
			gbc_chkFriday2.gridy = 9;
			panel2.add(chkFriday2, gbc_chkFriday2);
			
			chkSaturday2 = new JCheckBox("Saturday");
			GridBagConstraints gbc_chkSaturday2 = new GridBagConstraints();
			gbc_chkSaturday2.anchor = GridBagConstraints.WEST;
			gbc_chkSaturday2.insets = new Insets(0, 0, 5, 0);
			gbc_chkSaturday2.gridx = 1;
			gbc_chkSaturday2.gridy = 10;
			panel2.add(chkSaturday2, gbc_chkSaturday2);
			
			chkSunday2 = new JCheckBox("Sunday");
			GridBagConstraints gbc_chkSunday2 = new GridBagConstraints();
			gbc_chkSunday2.anchor = GridBagConstraints.WEST;
			gbc_chkSunday2.insets = new Insets(0, 0, 5, 0);
			gbc_chkSunday2.gridx = 1;
			gbc_chkSunday2.gridy = 11;
			panel2.add(chkSunday2, gbc_chkSunday2);
			
			chkEligibleToday2 = new JCheckBox("Eligible Today");
			GridBagConstraints gbc_chkEligibleToday2 = new GridBagConstraints();
			gbc_chkEligibleToday2.anchor = GridBagConstraints.WEST;
			gbc_chkEligibleToday2.insets = new Insets(0, 0, 5, 0);
			gbc_chkEligibleToday2.gridx = 1;
			gbc_chkEligibleToday2.gridy = 12;
			panel2.add(chkEligibleToday2, gbc_chkEligibleToday2);
	
			chkLoadOutstanding2 = new JCheckBox("Load In Progress");
			chkLoadOutstanding2.setToolTipText("The client has a load that's either not completed or not picked up");
			GridBagConstraints gbc_chkLoadOutstanding2 = new GridBagConstraints();
			gbc_chkLoadOutstanding2.anchor = GridBagConstraints.WEST;
			gbc_chkLoadOutstanding2.insets = new Insets(0, 0, 5, 0);
			gbc_chkLoadOutstanding2.gridx = 1;
			gbc_chkLoadOutstanding2.gridy = 13;
			panel2.add(chkLoadOutstanding2, gbc_chkLoadOutstanding2);
			
			JLabel lblNotes2 = new JLabel("Notes");
			GridBagConstraints gbc_lblNotes2 = new GridBagConstraints();
			gbc_lblNotes2.anchor = GridBagConstraints.EAST;
			gbc_lblNotes2.insets = new Insets(0, 0, 5, 5);
			gbc_lblNotes2.gridx = 0;
			gbc_lblNotes2.gridy = 14;
			panel2.add(lblNotes2, gbc_lblNotes2);
			
			JScrollPane scrollPaneNotes2 = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 1;
			gbc_scrollPane.gridy = 14;
			panel2.add(scrollPaneNotes2, gbc_scrollPane);
			
			txtNotesClient2 = new JTextArea();
			txtNotesClient2.setRows(12);
			txtNotesClient2.setWrapStyleWord(true);
			txtNotesClient2.setToolTipText("Add any special notes here.");
			txtNotesClient2.setLineWrap(true);
			GridBagConstraints gbc_textArea = new GridBagConstraints();
			gbc_textArea.fill = GridBagConstraints.BOTH;
			gbc_textArea.insets = new Insets(0, 0, 5, 0);
			gbc_textArea.gridx = 1;
			gbc_textArea.gridy = 15;
			scrollPaneNotes2.setViewportView(txtNotesClient2);
		}		
		
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean mergeSuccessful;
	    	    if (rdbtnKeepClient1.isSelected()) {
	    	    	mergeSuccessful = eViewEditClientWindow.validateFields(txtFname1.getText(), txtLname1.getText());
					if (mergeSuccessful) {
						mergeSuccessful = eViewEditClientWindow.update_client(id1, txtFname1.getText(), txtLname1.getText(), txtPhone1.getText(), 
								chkMonday1.isSelected(), chkTuesday1.isSelected(), chkWednesday1.isSelected(),
								chkThursday1.isSelected(), chkFriday1.isSelected(), chkSaturday1.isSelected(),
								chkSunday1.isSelected(), chkEligibleToday1.isSelected(), chkLoadOutstanding1.isSelected(),
								txtNotesClient1.getText());
						if(mergeSuccessful) {
							//Before deleting the client, need to take all id2's loads and make them id1's loads
							System.out.println("MERGING into client1");
							switch_loads(id2, id1);
							delete_client(id2);
							frmMergeClients.dispose();
						}
					}
				} else if (rdbtnKeepClient2.isSelected()) {
					mergeSuccessful = eViewEditClientWindow.validateFields(txtFname2.getText(), txtLname2.getText());
					if (mergeSuccessful) {
						mergeSuccessful = eViewEditClientWindow.update_client(id2, txtFname2.getText(), txtLname2.getText(), txtPhone2.getText(), 
								chkMonday2.isSelected(), chkTuesday2.isSelected(), chkWednesday2.isSelected(),
								chkThursday2.isSelected(), chkFriday2.isSelected(), chkSaturday2.isSelected(),
								chkSunday2.isSelected(), chkEligibleToday2.isSelected(), chkLoadOutstanding2.isSelected(),
								txtNotesClient2.getText());
						if(mergeSuccessful) {
							//Before deleting the client, need to take all id1's loads and make them id2's loads
							System.out.println("MERGING into client2");
							switch_loads(id1, id2);
							delete_client(id1);
							frmMergeClients.dispose();
						}
					}
	    	    } else {
	    	    	System.out.println("Please select a client to keep");
	    	    }
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frmMergeClients.getContentPane().add(btnSubmit, BorderLayout.SOUTH);
		/*return frmMergeClients;*/
		
	}

	protected void switch_loads(int old, int keep) {
		String update_loads = "UPDATE laundry_loads SET client_id = " + keep + " WHERE client_id = " + old;
		System.out.println(update_loads);
		zDatabaseHandlerBackend.update(update_loads);
		cMainDashboardWindow.update_table();	
		cMainDashboardWindow.update_clients_table();
	}

	protected void delete_client(int client_id) {
		//Yes, delete it
		String delete_client = "DELETE FROM clients WHERE id = " + client_id;
		System.out.println(delete_client);
		zDatabaseHandlerBackend.update(delete_client);
		cMainDashboardWindow.update_table();
		cMainDashboardWindow.update_clients_table();
	}
	
	public static ResultSet select_client_info(int id) {
		String client_query = "SELECT * FROM clients WHERE id = " + id;
		ResultSet client_info = zDatabaseHandlerBackend.select(client_query);
		return client_info;
	}
	
	public static void populate_client1(ResultSet client_info) {
		try {
			//Populate the window with the client's info from the db.
			client_info.next();
			txtFname1.setText(client_info.getString("fName"));
			txtLname1.setText(client_info.getString("lName"));
			chkMonday1.setSelected(client_info.getBoolean("monday"));
			chkTuesday1.setSelected(client_info.getBoolean("tuesday"));
			chkWednesday1.setSelected(client_info.getBoolean("wednesday"));
			chkThursday1.setSelected(client_info.getBoolean("thursday"));
			chkFriday1.setSelected(client_info.getBoolean("friday"));
			chkSaturday1.setSelected(client_info.getBoolean("saturday"));
			chkSunday1.setSelected(client_info.getBoolean("sunday"));
			chkEligibleToday1.setSelected(client_info.getBoolean("eligible_today"));
			chkLoadOutstanding1.setSelected(client_info.getBoolean("load_outstanding"));
			txtNotesClient1.setText(client_info.getString("notes"));
		} catch (SQLException e) {
			//No client existed
			debug.show_error("No Client Exists", "Error: Cannot retrieve client for viewing and editing.");
		}
	}
	
	public static void populate_client2(ResultSet client_info) {
		try {
			//Populate the window with the client's info from the db.
			client_info.next();
			txtFname2.setText(client_info.getString("fName"));
			txtLname2.setText(client_info.getString("lName"));
			chkMonday2.setSelected(client_info.getBoolean("monday"));
			chkTuesday2.setSelected(client_info.getBoolean("tuesday"));
			chkWednesday2.setSelected(client_info.getBoolean("wednesday"));
			chkThursday2.setSelected(client_info.getBoolean("thursday"));
			chkFriday2.setSelected(client_info.getBoolean("friday"));
			chkSaturday2.setSelected(client_info.getBoolean("saturday"));
			chkSunday2.setSelected(client_info.getBoolean("sunday"));
			chkEligibleToday2.setSelected(client_info.getBoolean("eligible_today"));
			chkLoadOutstanding2.setSelected(client_info.getBoolean("load_outstanding"));
			txtNotesClient2.setText(client_info.getString("notes"));
		} catch (SQLException e) {
			//No client existed
			debug.show_error("No Client Exists", "Error: Cannot retrieve client for viewing and editing.");
		}
	}
}