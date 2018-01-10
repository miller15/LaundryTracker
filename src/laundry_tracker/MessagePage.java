package communication;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


public class MessagePage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JPanel contentPane;
	private JTextField txtFname;
	private JTextField txtLname;
	private JTextField txtEmail;
	private JTextField txtTeam;
	private JTextField txtSubteam1;
	private JTextField txtPosition1a;
	private JTextField txtSubteam2;
	private JTextField txtPosition1b;
	private JTextField txtPosition2a;
	private JTextField txtPosition2b;
	private static JTextField txtSubject;
	private static JTextArea txtareaMessage;
	private static String currUser;
	private static RecipientsTable tableContentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MessagePage frame = new MessagePage();
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
					MessagePage frame = new MessagePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MessagePage() {
		setTitle("Setup your message");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 395);
	
		JPanel messagePane = new JPanel();
		messagePane.setBorder(new TitledBorder(new EtchedBorder(), "Type your message in the box, then click the Recipients tab to choose recipients."));
		JPanel recipientsPane = new JPanel();
		JPanel createContacts = new JPanel();
		createContacts.setBorder(new TitledBorder(new EtchedBorder(), "Fill out the fields to enter contacts into the database."));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new CompoundBorder(new LineBorder(new Color(128, 0, 0), 2, true), new LineBorder(new Color(218, 165, 32))));
		tabbedPane.add("Message", messagePane);
		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.X_AXIS));
		JScrollPane messageScroll = new JScrollPane();
		messageScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
		messagePane.add(messageScroll);
		
		txtareaMessage = new JTextArea();
		txtareaMessage.setRows(1);
		txtareaMessage.setToolTipText("");
		messageScroll.setViewportView(txtareaMessage);
		txtareaMessage.setWrapStyleWord(true);
		txtareaMessage.setLineWrap(true);
		txtareaMessage.setText("Type your message here.");
		
		JPanel panel = new JPanel();
		messageScroll.setColumnHeaderView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setForeground(new Color(220, 20, 60));
		lblSubject.setBackground(Color.WHITE);
		panel.add(lblSubject);
		
		txtSubject = new JTextField();
		panel.add(txtSubject);
		txtSubject.setColumns(10);
		
		JFrame tableFrame = new JFrame("recipientsFrame");
		//tableFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tableContentPane = new RecipientsTable();
		tableContentPane.setOpaque(true);
		tableFrame.setContentPane(tableContentPane);
		
		tabbedPane.add("Recipients2", tableContentPane);
				
		tabbedPane.add("Create Contacts", createContacts);
		GridBagLayout gbl_createContacts = new GridBagLayout();
		gbl_createContacts.columnWidths = new int[]{0, 0, 0};
		gbl_createContacts.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_createContacts.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_createContacts.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		createContacts.setLayout(gbl_createContacts);
		
		JLabel lblFirstName = new JLabel("First Name");
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.anchor = GridBagConstraints.EAST;
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 0;
		gbc_lblFirstName.gridy = 1;
		createContacts.add(lblFirstName, gbc_lblFirstName);
		
		txtFname = new JTextField();
		GridBagConstraints gbc_txtFname = new GridBagConstraints();
		gbc_txtFname.insets = new Insets(0, 0, 5, 0);
		gbc_txtFname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFname.gridx = 1;
		gbc_txtFname.gridy = 1;
		createContacts.add(txtFname, gbc_txtFname);
		txtFname.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.anchor = GridBagConstraints.EAST;
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 0;
		gbc_lblLastName.gridy = 2;
		createContacts.add(lblLastName, gbc_lblLastName);
		
		txtLname = new JTextField();
		GridBagConstraints gbc_txtLname = new GridBagConstraints();
		gbc_txtLname.insets = new Insets(0, 0, 5, 0);
		gbc_txtLname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLname.gridx = 1;
		gbc_txtLname.gridy = 2;
		createContacts.add(txtLname, gbc_txtLname);
		txtLname.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Email");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		createContacts.add(lblNewLabel, gbc_lblNewLabel);
		
		txtEmail = new JTextField();
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.insets = new Insets(0, 0, 5, 0);
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 3;
		createContacts.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblTeam = new JLabel("Team");
		GridBagConstraints gbc_lblTeam = new GridBagConstraints();
		gbc_lblTeam.anchor = GridBagConstraints.EAST;
		gbc_lblTeam.insets = new Insets(0, 0, 5, 5);
		gbc_lblTeam.gridx = 0;
		gbc_lblTeam.gridy = 4;
		createContacts.add(lblTeam, gbc_lblTeam);
		
		txtTeam = new JTextField();
		GridBagConstraints gbc_txtTeam = new GridBagConstraints();
		gbc_txtTeam.insets = new Insets(0, 0, 5, 0);
		gbc_txtTeam.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTeam.gridx = 1;
		gbc_txtTeam.gridy = 4;
		createContacts.add(txtTeam, gbc_txtTeam);
		txtTeam.setColumns(10);
		
		JLabel lblSubteam1 = new JLabel("Sub-team");
		GridBagConstraints gbc_lblSubteam1 = new GridBagConstraints();
		gbc_lblSubteam1.anchor = GridBagConstraints.EAST;
		gbc_lblSubteam1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubteam1.gridx = 0;
		gbc_lblSubteam1.gridy = 5;
		createContacts.add(lblSubteam1, gbc_lblSubteam1);
		
		txtSubteam1 = new JTextField();
		GridBagConstraints gbc_txtSubteam1 = new GridBagConstraints();
		gbc_txtSubteam1.insets = new Insets(0, 0, 5, 0);
		gbc_txtSubteam1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSubteam1.gridx = 1;
		gbc_txtSubteam1.gridy = 5;
		createContacts.add(txtSubteam1, gbc_txtSubteam1);
		txtSubteam1.setColumns(10);
		
		JLabel lblPosition1a = new JLabel("Position");
		GridBagConstraints gbc_lblPosition1a = new GridBagConstraints();
		gbc_lblPosition1a.anchor = GridBagConstraints.EAST;
		gbc_lblPosition1a.insets = new Insets(0, 0, 5, 5);
		gbc_lblPosition1a.gridx = 0;
		gbc_lblPosition1a.gridy = 6;
		createContacts.add(lblPosition1a, gbc_lblPosition1a);
		
		txtPosition1a = new JTextField();
		GridBagConstraints gbc_txtPosition1a = new GridBagConstraints();
		gbc_txtPosition1a.insets = new Insets(0, 0, 5, 0);
		gbc_txtPosition1a.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPosition1a.gridx = 1;
		gbc_txtPosition1a.gridy = 6;
		createContacts.add(txtPosition1a, gbc_txtPosition1a);
		txtPosition1a.setColumns(10);
		
		JLabel lblPosition1b = new JLabel("Position");
		GridBagConstraints gbc_lblPosition1b = new GridBagConstraints();
		gbc_lblPosition1b.anchor = GridBagConstraints.EAST;
		gbc_lblPosition1b.insets = new Insets(0, 0, 5, 5);
		gbc_lblPosition1b.gridx = 0;
		gbc_lblPosition1b.gridy = 7;
		createContacts.add(lblPosition1b, gbc_lblPosition1b);
		
		txtPosition1b = new JTextField();
		GridBagConstraints gbc_txtPosition1b = new GridBagConstraints();
		gbc_txtPosition1b.insets = new Insets(0, 0, 5, 0);
		gbc_txtPosition1b.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPosition1b.gridx = 1;
		gbc_txtPosition1b.gridy = 7;
		createContacts.add(txtPosition1b, gbc_txtPosition1b);
		txtPosition1b.setColumns(10);
		
		JLabel lblSubteam2 = new JLabel("Sub-Team");
		GridBagConstraints gbc_lblSubteam2 = new GridBagConstraints();
		gbc_lblSubteam2.anchor = GridBagConstraints.EAST;
		gbc_lblSubteam2.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubteam2.gridx = 0;
		gbc_lblSubteam2.gridy = 8;
		createContacts.add(lblSubteam2, gbc_lblSubteam2);
		
		txtSubteam2 = new JTextField();
		GridBagConstraints gbc_txtSubteam2 = new GridBagConstraints();
		gbc_txtSubteam2.insets = new Insets(0, 0, 5, 0);
		gbc_txtSubteam2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSubteam2.gridx = 1;
		gbc_txtSubteam2.gridy = 8;
		createContacts.add(txtSubteam2, gbc_txtSubteam2);
		txtSubteam2.setColumns(10);
		
		JLabel lblPosition2a = new JLabel("Position");
		GridBagConstraints gbc_lblPosition2a = new GridBagConstraints();
		gbc_lblPosition2a.anchor = GridBagConstraints.EAST;
		gbc_lblPosition2a.insets = new Insets(0, 0, 5, 5);
		gbc_lblPosition2a.gridx = 0;
		gbc_lblPosition2a.gridy = 9;
		createContacts.add(lblPosition2a, gbc_lblPosition2a);
		
		txtPosition2a = new JTextField();
		GridBagConstraints gbc_txtPosition2a = new GridBagConstraints();
		gbc_txtPosition2a.insets = new Insets(0, 0, 5, 0);
		gbc_txtPosition2a.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPosition2a.gridx = 1;
		gbc_txtPosition2a.gridy = 9;
		createContacts.add(txtPosition2a, gbc_txtPosition2a);
		txtPosition2a.setColumns(10);
		
		JLabel lblPosition2b = new JLabel("Position");
		GridBagConstraints gbc_lblPosition2b = new GridBagConstraints();
		gbc_lblPosition2b.anchor = GridBagConstraints.EAST;
		gbc_lblPosition2b.insets = new Insets(0, 0, 5, 5);
		gbc_lblPosition2b.gridx = 0;
		gbc_lblPosition2b.gridy = 10;
		createContacts.add(lblPosition2b, gbc_lblPosition2b);
		
		txtPosition2b = new JTextField();
		GridBagConstraints gbc_txtPosition2b = new GridBagConstraints();
		gbc_txtPosition2b.insets = new Insets(0, 0, 5, 0);
		gbc_txtPosition2b.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPosition2b.gridx = 1;
		gbc_txtPosition2b.gridy = 10;
		createContacts.add(txtPosition2b, gbc_txtPosition2b);
		txtPosition2b.setColumns(10);
				
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				validateFields();
			}

			private void validateFields(){
				String fName = txtFname.getText();
				String lName = txtLname.getText();
				String email = txtEmail.getText();
				String team = txtTeam.getText();
				String subTeam1 = txtSubteam1.getText();
				String subTeam2 = txtSubteam2.getText();
				String position1 = txtPosition1a.getText();
				String position2 = txtPosition1b.getText();
				String position3 = txtPosition2a.getText();
				String position4 = txtPosition2b.getText();
				if(fName.length() > 15){
					show_error("First Name", "First name too long");
					txtFname.setText("");
				} else if (fName.length() < 1){
					show_error("First Name", "Please enter a first name");
				} else if(lName.length() > 15){
					show_error("Last Name", "Last name too long");
					txtLname.setText("");
				} else if (lName.length() < 1){
					show_error("Last Name", "Please enter a last name");
				} else if(email.length() > 40){
					show_error("Email", "Email too long");
					txtEmail.setText("");
				} else if (email.length() < 1){
					show_error("Email", "Please enter an email address");
				} else if(team.length() > 15){
					show_error("Team Name", "Team name too long");
					txtTeam.setText("");
				} else if (team.length() < 1){
					txtTeam.setText(" ");
					validateFields();
				} else if(subTeam1.length() > 15){
					show_error("Sub Team", "First sub-team name too long");
					txtSubteam1.setText("");
				} else if (subTeam1.length() < 1){
					txtSubteam1.setText(" ");
					validateFields();
				} else if(position1.length() > 15){
					show_error("Position Name", "First position name too long");
					txtPosition1a.setText("");
				} else if (position1.length() < 1){
					txtPosition1a.setText(" ");
					validateFields();
				} else if(position2.length() > 15){
					show_error("Position Name", "Second position name too long");
					txtPosition1b.setText("");
				} else if(subTeam2.length() > 15){
					show_error("Sub Team Name", "Second sub-team name too long");
					txtSubteam2.setText("");
				} else if(position3.length() > 15){
					show_error("Position Name", "Third position name too long");
					txtPosition2a.setText("");
				} else if(position4.length() > 15){
					show_error("Position Name", "Fourth position name too long");
					txtPosition2b.setText("");
				} else {
					addContact(fName, lName, email, team, subTeam1, position1, position2, subTeam2, position3, position4);
				}
			}
			
			private void addContact(String fName, String lName, String email, String team, String subTeam1, String position1, String position2, String subTeam2, String position3, String position4) {
				// TODO Auto-generated method stub
				boolean success;
				currUser = MainWindow.getCurrUser();
				System.out.println("Current User from MessagePage class: " + currUser);
				success = DatabaseHandler.addContact(fName, lName, email, team, subTeam1, position1, position2, subTeam2, position3, position4, currUser);
				if(success){
					txtFname.setText("");
					txtLname.setText("");
					txtEmail.setText("");
					txtTeam.setText("");
					txtSubteam1.setText("");
					txtSubteam2.setText("");
					txtPosition1a.setText("");
					txtPosition1b.setText("");
					txtPosition2a.setText("");
					txtPosition2b.setText("");
					JOptionPane.showConfirmDialog(null, "Contact " + fName + " added successfully.", "Success", -1);
					//UPDATE THE TABLE HERE
					tableContentPane.updateTable();
				} else {
					JOptionPane.showConfirmDialog(null, "Contact " + fName + " could not be added.", "Failure", -1);
				}
			}
		});
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.WEST;
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 11;
		createContacts.add(btnAdd, gbc_btnAdd);
		
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
				SendEmail.setupAndSend(subject, body, from, password, toArray, username);				
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
	    System.out.println("Running populate Table Method");
		System.out.println("Finished populate Table Method");
	}
		
	public static void sendMessage(String[] recipients, String ownerEmail)
	{
		String body = txtareaMessage.getText();
		String subject = txtSubject.getText();
		String userNameArray[] = ownerEmail.split("[@]");
		String userName = userNameArray[0];
		String password = JOptionPane.showInputDialog("Please enter your email account password: ");
		System.out.println("");
		System.out.println("MessagePage.java: sendMessage: ");
		for(int i=0; i<recipients.length; i++){
			System.out.println(recipients[i]);
		}
		System.out.println("");
		SendEmail.setupAndSend(subject, body, ownerEmail, password, recipients, userName);
	}
	
	private static void show_error(String title, Object message)
	{
		JOptionPane.showConfirmDialog(null, message, title, -1);
	}
	
	public static void UPDATETABLE(){
		tableContentPane.updateTable();
	}

}
