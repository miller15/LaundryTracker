package laundry_tracker;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

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
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JDesktopPane;
import javax.swing.Box;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;


public class cMainDashboardWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JPanel contentPane;
	private JTextField txtFname;
	private JTextField txtLname;
	private static String currUser;
	private static dViewAllClientsWindow tableContentPane;
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
		setTitle("Manage");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 496);
		JPanel recipientsPane = new JPanel();
		JPanel panelAddClient = new JPanel();
		panelAddClient.setBorder(new TitledBorder(new EtchedBorder(), "Fill out the fields to enter a new client into the database. (* indicates required field)"));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new CompoundBorder(new LineBorder(new Color(128, 0, 0), 2, true), new LineBorder(new Color(218, 165, 32))));
		
		JFrame tableFrame = new JFrame("recipientsFrame");
		//tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tableContentPane = new dViewAllClientsWindow();
		tableContentPane.setOpaque(true);
		tableFrame.setContentPane(tableContentPane);
		
		JPanel panelClientList = new JPanel();
		tabbedPane.addTab("Client List", null, panelClientList, null);
		
		//tabbedPane.add("Recipients2", tableContentPane);
				
		tabbedPane.add("Add Client", panelAddClient);
		GridBagLayout gbl_viewAddClient = new GridBagLayout();
		gbl_viewAddClient.columnWidths = new int[]{0, 0, 0};
		gbl_viewAddClient.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_viewAddClient.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_viewAddClient.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panelAddClient.setLayout(gbl_viewAddClient);
		
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
		gbc_lblNotes.insets = new Insets(0, 0, 5, 5);
		gbc_lblNotes.gridx = 0;
		gbc_lblNotes.gridy = 12;
		panelAddClient.add(lblNotes, gbc_lblNotes);
		
		JTextArea txtrNotes = new JTextArea();
		txtrNotes.setToolTipText("Add any special notes here.");
		txtrNotes.setWrapStyleWord(true);
		txtrNotes.setLineWrap(true);
		GridBagConstraints gbc_txtrNotes = new GridBagConstraints();
		gbc_txtrNotes.insets = new Insets(0, 0, 5, 0);
		gbc_txtrNotes.fill = GridBagConstraints.BOTH;
		gbc_txtrNotes.gridx = 1;
		gbc_txtrNotes.gridy = 12;
		panelAddClient.add(txtrNotes, gbc_txtrNotes);
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
				success = zDatabaseHandlerBackend.addClient(fName, lName, phone_number, eligibility);
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
*/		recipientsPane.setLayout(null);
				
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
		
/*	public static void sendMessage(String[] recipients, String ownerEmail)
	{
		String body = txtareaMessage.getText();
		String subject = txtSubject.getText();
		String userNameArray[] = ownerEmail.split("[@]");
		String userName = userNameArray[0];
		String password = JOptionPane.showInputDialog("Please enter your email account password: ");
		System.out.println("");
		System.out.println("cMainDashboardWindow.java: sendMessage: ");
		for(int i=0; i<recipients.length; i++){
			System.out.println(recipients[i]);
		}
		System.out.println("");
		zzzzzzzzSendEmailBackend.setupAndSend(subject, body, ownerEmail, password, recipients, userName);
	}
*/	
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
	
	public static void UPDATETABLE(){
		tableContentPane.updateTable();
	}

}
