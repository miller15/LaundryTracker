package communication;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class EditContact extends JFrame{

	private static final long serialVersionUID = 1L;
	//private static JFrame frame;
	private JPanel contentPane;
	private JFrame frmEditContact;
	private JTextField txtEmail;
	private JTextField txtTeam;
	private JTextField txtSubteam1;
	private JTextField txtPosition1a;
	private JTextField txtLname;
	private JTextField txtFname;
	private JTextField txtSubteam2;
	private JTextField txtPosition1b;
	private JTextField txtPosition2a;
	private JTextField txtPosition2b;
	private static String[] fieldValues;

	/**
	 * Launch the application.
	 */
	public static void main(String[] rowValue) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditContact window = new EditContact(rowValue);
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditContact(String[] rowValue) {
		for(int i =0; i<rowValue.length; i++){
			System.out.println("~~~~~~~~ " + i + " ~~~~~");
		}
		String contactId = rowValue[0];
		System.out.println("rowValue[0]: " + rowValue[4]);
		for(int p=0; p<rowValue.length; p++){
			System.out.println(p + ": ");
			System.out.println(rowValue[p]);
		}
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		frmEditContact = new JFrame();
		frmEditContact.setTitle("Update Contact");
		frmEditContact.setBounds(100, 100, 450, 400);
		//frmEditContact.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEditContact.getContentPane().setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][][]"));
		
		JLabel lblTitle = new JLabel("Use this page to edit an existing user.");
		frmEditContact.getContentPane().add(lblTitle, "cell 0 0 3 1,alignx center");
		
		JLabel lblFname = new JLabel("First Name:");
		frmEditContact.getContentPane().add(lblFname, "cell 1 2,alignx trailing");
		
		txtFname = new JTextField();
		txtFname.setText((String) rowValue[1]);
		frmEditContact.getContentPane().add(txtFname, "cell 2 2,growx");
		txtFname.setColumns(10);
		
		JLabel lblLname = new JLabel("Last Name:");
		frmEditContact.getContentPane().add(lblLname, "cell 1 3,alignx trailing");
		
		txtLname = new JTextField();
		txtLname.setText((String) rowValue[2]);
		frmEditContact.getContentPane().add(txtLname, "cell 2 3,growx");
		txtLname.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email:");
		frmEditContact.getContentPane().add(lblEmail, "cell 1 4,alignx trailing");
		
		txtEmail = new JTextField();
		txtEmail.setText((String) rowValue[4]);
		frmEditContact.getContentPane().add(txtEmail, "cell 2 4,growx");
		txtEmail.setColumns(10);
		
		JLabel lblTeam = new JLabel("Team:");
		frmEditContact.getContentPane().add(lblTeam, "cell 1 5,alignx trailing");
		
		txtTeam = new JTextField();
		//txtTeam.setText((String) rowValue[0]);
		frmEditContact.getContentPane().add(txtTeam, "cell 2 5,growx");
		
		JLabel lblSubteam1 = new JLabel("Sub-team:");
		frmEditContact.getContentPane().add(lblSubteam1, "cell 1 6,alignx trailing");
		
		txtSubteam1 = new JTextField();
		//txtSubteam1.setText((String) rowValue[1]);
		frmEditContact.getContentPane().add(txtSubteam1, "cell 2 6,growx");
				
		JLabel lblPosition1a = new JLabel("Position");
		frmEditContact.getContentPane().add(lblPosition1a, "cell 1 7,alignx trailing");
		
		txtPosition1a = new JTextField();
		//txtPosition1a.setText((String) rowValue[2]);
		frmEditContact.getContentPane().add(txtPosition1a, "cell 2 7,growx");
		txtPosition1a.setColumns(10);
		
		JLabel lblPosition1b = new JLabel("Position");
		frmEditContact.getContentPane().add(lblPosition1b, "cell 1 8,alignx trailing");
		
		txtPosition1b = new JTextField();
		frmEditContact.getContentPane().add(txtPosition1b, "cell 2 8,growx");
		txtPosition1b.setColumns(10);
		
		JLabel lblSubteam2 = new JLabel("Sub-Team");
		frmEditContact.getContentPane().add(lblSubteam2, "cell 1 9,alignx trailing");

		txtSubteam2 = new JTextField();
		frmEditContact.getContentPane().add(txtSubteam2, "cell 2 9,growx");
		txtSubteam2.setColumns(10);
		
		JLabel lblPosition2a = new JLabel("Position");
		frmEditContact.getContentPane().add(lblPosition2a, "cell 1 10,alignx trailing");
		
		txtPosition2a = new JTextField();
		frmEditContact.getContentPane().add(txtPosition2a, "cell 2 10,growx");
		txtPosition2a.setColumns(10);
		
		JLabel lblPosition2b = new JLabel("Position");
		frmEditContact.getContentPane().add(lblPosition2b, "cell 1 11,alignx trailing");

		txtPosition2b = new JTextField();
		frmEditContact.getContentPane().add(txtPosition2b, "cell 2 11,growx");
		txtPosition2b.setColumns(10);

		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmEditContact.dispose();
			}
		});
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String delete = "DELETE FROM contact_t WHERE contactId = '" + contactId + "'";
				DatabaseHandler.delete(delete);
				System.out.println("FINISHED DELETE");
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
				String currUser = MainWindow.getCurrUser();
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
					JOptionPane.showConfirmDialog(null, "Contact " + fName + " updated successfully.", "Success", -1);
					//UPDATE THE TABLE HERE
					MessagePage.UPDATETABLE();
					frmEditContact.dispose();
				} else {
					JOptionPane.showConfirmDialog(null, "Contact " + fName + " could not be updated.", "Failure", -1);
				}
			}

		});
		
		frmEditContact.getContentPane().add(btnCancel, "cell 1 12,alignx left");
		frmEditContact.getContentPane().add(btnAdd, "cell 2 12,alignx right");
		frmEditContact.setVisible(true);

	}

	private static void show_error(String title, Object message)
	{
		JOptionPane.showConfirmDialog(null, message, title, -1);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
/*	public static void initialize() {
		
		frame = new JFrame();
		frame.setTitle("Edit Contact");
		frame.setBounds(100, 100, 450, 300);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
				
		
		
		
				JLabel lblFirstName = new JLabel("First Name");
				//lblFirstName.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(lblFirstName);
				
				JTextField txtFname = new JTextField();
				//txtFname.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(txtFname);
				txtFname.setColumns(10);
				
				JLabel lblLastName = new JLabel("Last Name");
				//lblLastName.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(lblLastName);
				
				JTextField txtLname = new JTextField();
				//txtLname.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(txtLname);
				txtLname.setColumns(10);
				
				JLabel lblNewLabel = new JLabel("Email");
				lblNewLabel.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(lblNewLabel);
				
				JTextField txtEmail = new JTextField();
				txtEmail.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(txtEmail);
				txtEmail.setColumns(10);
				
				JLabel lblTeam = new JLabel("Team");
				lblTeam.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(lblTeam);
				
				JTextField txtTeam = new JTextField();
				txtTeam.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(txtTeam);
				txtTeam.setColumns(10);
				
				JLabel lblSubteam1 = new JLabel("Sub-team");
				lblSubteam1.setBounds(0, 0, 23, 1);
				frame.getContentPane().add(lblSubteam1);
				
				JTextField txtSubteam1 = new JTextField();
				txtSubteam1.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(txtSubteam1);
				txtSubteam1.setColumns(10);
				
				JLabel lblPosition1a = new JLabel("Position");
				lblPosition1a.setBounds(0, 0, 23, 14);
				frame.getContentPane().add(lblPosition1a);
				
				JTextField txtPosition1a = new JTextField();
				txtPosition1a.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(txtPosition1a);
				txtPosition1a.setColumns(10);
				
				JLabel lblPosition1b = new JLabel("Position");
				lblPosition1b.setBounds(0, 0, 23, 14);
				frame.getContentPane().add(lblPosition1b);
				
				JTextField txtPosition1b = new JTextField();
				txtPosition1b.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(txtPosition1b);
				txtPosition1b.setColumns(10);
				
				JLabel lblSubteam2 = new JLabel("Sub-Team");
				lblSubteam2.setBounds(0, 0, 23, 14);
				frame.getContentPane().add(lblSubteam2);
				
				JTextField txtSubteam2 = new JTextField();
				txtSubteam2.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(txtSubteam2);
				txtSubteam2.setColumns(10);
				
				JLabel lblPosition2a = new JLabel("Position");
				lblPosition2a.setBounds(0, 0, 23, 14);
				frame.getContentPane().add(lblPosition2a);
				
				JTextField txtPosition2a = new JTextField();
				txtPosition2a.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(txtPosition2a);
				txtPosition2a.setColumns(10);
				
				JLabel lblPosition2b = new JLabel("Position");
				lblPosition2b.setBounds(0, 0, 23, 14);
				frame.getContentPane().add(lblPosition2b);
				
				JTextField txtPosition2b = new JTextField();
				txtPosition2b.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(txtPosition2b);
				txtPosition2b.setColumns(10);
				
				JButton btnAdd = new JButton("Add");
				btnAdd.setBounds(0, 0, 0, 0);
				frame.getContentPane().add(btnAdd);
				
				JLabel label = new JLabel("Use this page to register as a new user. All fields are required.");
				label.setBounds(0, 0, 300, 14);
				frame.getContentPane().add(label);
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
						String currUser = MainWindow.getCurrUser();
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
							MessagePage.runMessageTabs();
							//UPDATE THE TABLE HERE
							//tableContentPane.updateTable();
						} else {
							JOptionPane.showConfirmDialog(null, "Contact " + fName + " could not be added.", "Failure", -1);
						}
					}
				});
		frame.setVisible(true);

		
	} */
}
