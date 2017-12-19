package communication;
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
import java.awt.event.ActionEvent;


public class CreateAccount extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6778610575414893137L;

	private JPanel contentPane;
	
	private JFrame frmCreateaccount;
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
					CreateAccount frame = new CreateAccount();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public CreateAccount() {
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
		frmCreateaccount.getContentPane().add(btnCancel, "cell 2 9,alignx left");

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//need to add this method to the Database Handler class
				char[] pWord = pwdPword.getPassword();
				char[] pWordConfirm = pwdConfirm.getPassword();
				String password = new String(pWord);
				String passwordConfirm = new String(pWordConfirm);
				boolean success;
				boolean pwdFlag = true;
				boolean uNameFlag = true;
				if(password.equals("") || passwordConfirm.equals("")){
					pwdFlag = false;
				} else {
					for(int i=0; i < pWord.length; i++)
					{
						if(pWord[i] != pWordConfirm[i]){
							pwdFlag = false; //check that the password is correct
						}
					}
				}
				if(txtUsername.getText().equals("")){
					uNameFlag = false;
					System.out.println("USERNAME EMPTY");
				}
				if(pwdFlag && uNameFlag){
					success = DatabaseHandler.addUser(txtFname.getText(), txtLname.getText(), txtUsername.getText(), password, txtEmail.getText());
					MainWindow.setCurrUser(txtUsername.getText());
					if(success){
						//MessagePage.main(null);
						frmCreateaccount.dispose();
						MessagePage.runMessageTabs();
					} else{
						txtUsername.setText("");
					}
				}else if(!pwdFlag){
					JOptionPane.showConfirmDialog(null, "Password does not match. Please re-type password fields.", "Pasword Error", -1);
					pwdPword.setText("");
					pwdConfirm.setText("");
				} else if(!uNameFlag){
					JOptionPane.showConfirmDialog(null, "Username is empty. Please enter a username.", "Username Error", -1);
				}
			}
		});
		frmCreateaccount.getContentPane().add(btnRegister, "cell 2 8,alignx left");
		
		frmCreateaccount.setVisible(true);
		
	}

}
