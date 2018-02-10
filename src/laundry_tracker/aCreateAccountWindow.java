package laundry_tracker;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class aCreateAccountWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6778610575414893137L;

	private JPanel contentPane;
	
	static JFrame frmCreateaccount;
	private JTextField txtUsername;
	private JPasswordField pwdPword;
	private JPasswordField pwdConfirm;
	private JTextField txtEmail;
	private JTextField txtLname;
	private JTextField txtFname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					aCreateAccountWindow frame = new aCreateAccountWindow();
					//frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("DIDNT GET FAR");
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public aCreateAccountWindow() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		frmCreateaccount = new JFrame();
		frmCreateaccount.setTitle("Create Account");
		frmCreateaccount.setBounds(100, 100, 450, 300);
		//frmCreateaccount.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCreateaccount.getContentPane().setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][][]"));
		
		JLabel lblTitle = new JLabel("Use this page to register as a new user. All fields are required.");
		frmCreateaccount.getContentPane().add(lblTitle, "cell 0 0 3 1,alignx center");
		
		JLabel lblFname = new JLabel("First Name:");
		frmCreateaccount.getContentPane().add(lblFname, "cell 1 2,alignx trailing");
		
		txtFname = new JTextField();
		frmCreateaccount.getContentPane().add(txtFname, "cell 2 2,growx");
		txtFname.setColumns(10);
		
		JLabel lblLname = new JLabel("Last Name:");
		frmCreateaccount.getContentPane().add(lblLname, "cell 1 3,alignx trailing");
		
		txtLname = new JTextField();
		frmCreateaccount.getContentPane().add(txtLname, "cell 2 3,growx");
		txtLname.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		frmCreateaccount.getContentPane().add(lblUsername, "cell 1 4,alignx trailing");
		
		txtUsername = new JTextField();
		frmCreateaccount.getContentPane().add(txtUsername, "cell 2 4,growx");
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		frmCreateaccount.getContentPane().add(lblPassword, "cell 1 5,alignx trailing");
		
		pwdPword = new JPasswordField();
		frmCreateaccount.getContentPane().add(pwdPword, "cell 2 5,growx");
		
		JLabel lblConfirm = new JLabel("Confirm Password:");
		frmCreateaccount.getContentPane().add(lblConfirm, "cell 1 6,alignx trailing");
		
		pwdConfirm = new JPasswordField();
		frmCreateaccount.getContentPane().add(pwdConfirm, "cell 2 6,growx");
		
		JLabel lblEmail = new JLabel("Email Address:");
		frmCreateaccount.getContentPane().add(lblEmail, "cell 1 7,alignx trailing");
		
		txtEmail = new JTextField();
		frmCreateaccount.getContentPane().add(txtEmail, "cell 2 7,growx");
		txtEmail.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCreateaccount.dispose();
			}
		});
		frmCreateaccount.getContentPane().add(btnCancel, "cell 2 9,alignx left");

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//need to add this method to the Database Handler class
				char[] pWord = pwdPword.getPassword();
				char[] pWordConfirm = pwdConfirm.getPassword();
				String uName = txtUsername.getText();
				int success;
				boolean pwdFlagEmpty = false; //ensure password isn't blank
				boolean pwdFlagLength = false; //ensure password is at least 12 characters
				boolean pwdFlagMismatch = false; //ensure the password matches confirm password field
				boolean uNameFlagEmpty = false; //ensure username isn't blank
				boolean uNameFlagLength = false; //ensure username isn't longer than 25 characters (25 is most db can hold)
				if(pWord.equals("") || pWordConfirm.equals("")) {
					pwdFlagEmpty = true;
				} else if(pWord.length < 1) {
					pwdFlagLength = true;
				} else if (!Arrays.equals(pWord, pWordConfirm)) {
					pwdFlagMismatch = true;
				}
				if(uName.equals("")) {
					uNameFlagEmpty = true;
				} else if(uName.length() > 25) {
					uNameFlagLength = true;
				}
				if(!uNameFlagEmpty && !uNameFlagLength && !pwdFlagEmpty && !pwdFlagLength && !pwdFlagMismatch){
					byte[] salt = Password.getNextSalt();
					System.out.println("*****This is what's going into the db based off user registration: ");
					System.out.println("Pword char[] raw input [C@629f0666: " + pWord);
					System.out.println("Salt Raw byte[]: " + salt);
					byte[] pWordSaltHash = Password.hash(pWord, salt);
					System.out.println("Raw pWordSaltHash to be inserted byte[]: " + pWordSaltHash);
					
					success = zDatabaseHandlerBackend.addUser(txtFname.getText(), txtLname.getText(), txtUsername.getText(), pWordSaltHash, txtEmail.getText(), salt);
					bWelcomeScreenWindow.setCurrUser(txtUsername.getText());
					if(success == 1) {
						//cMainDashboardWindow.main(null);
						frmCreateaccount.dispose();
						cMainDashboardWindow.runMessageTabs();
					} else {
						txtUsername.setText("");
					}
				} else if(uNameFlagEmpty) {
					JOptionPane.showConfirmDialog(null, "Username is empty. Please enter a username.", "Username Empty Error", -1);
				} else if(uNameFlagLength) {
					JOptionPane.showConfirmDialog(null, "Username is too long. Username can be a max of 25 characters.", "Username Length Error", -1);
					txtUsername.setText("");
				} else if(pwdFlagEmpty) {
					JOptionPane.showConfirmDialog(null, "You must enter a password!", "Pasword Empty Error", -1);
					pwdPword.setText("");
					pwdConfirm.setText("");
				} else if(pwdFlagLength) {
					JOptionPane.showConfirmDialog(null, "Your password must be at least 12 characters long.", "Pasword Length Error", -1);
					pwdPword.setText("");
					pwdConfirm.setText("");
				} else if(pwdFlagMismatch) {
					JOptionPane.showConfirmDialog(null, "Oops! Your password didn't match. Please re-type.", "Pasword Mismatch Error", -1);
					pwdPword.setText("");
					pwdConfirm.setText("");
				}
			}
		});
		frmCreateaccount.getContentPane().add(btnRegister, "cell 2 8,alignx left");
		
		frmCreateaccount.setVisible(true);
		
	}

}
