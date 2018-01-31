package laundry_tracker;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class bWelcomeScreenWindow {

	private JFrame frmLogin;
	private JTextField txtUname;
	private JPasswordField pwdPwd;
	private static boolean firstTime = true;
	private static String currUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		/*
		 * If this is the first time showing the main page, then initialize the database.
		 * Otherwise, it has already been created so we don't need to do this.
		 */
		if(firstTime = true){
			System.out.println("initializing database");
			zDatabaseHandlerBackend.initialize_db();
			firstTime = false;
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bWelcomeScreenWindow window = new bWelcomeScreenWindow();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public bWelcomeScreenWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 351, 355);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{56, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmLogin.getContentPane().setLayout(gridBagLayout);
		
		JTextArea txtpnTitle = new JTextArea();
		txtpnTitle.setEditable(false);
		txtpnTitle.setFont(new Font("Shannon Extra Bold", Font.BOLD, 15));
		txtpnTitle.setText("Welcome to the\n Lamb Center's laundry tracking tool!");
		GridBagConstraints gbc_txtpnTitle = new GridBagConstraints();
		gbc_txtpnTitle.anchor = GridBagConstraints.NORTH;
		gbc_txtpnTitle.gridwidth = 5;
		gbc_txtpnTitle.insets = new Insets(0, 0, 5, 0);
		gbc_txtpnTitle.gridx = 0;
		gbc_txtpnTitle.gridy = 0;
		frmLogin.getContentPane().add(txtpnTitle, gbc_txtpnTitle);
		
		JLabel lblPageDescriptor = new JLabel("Please Login or Register.");
		GridBagConstraints gbc_lblPageDescriptor = new GridBagConstraints();
		gbc_lblPageDescriptor.gridwidth = 2;
		gbc_lblPageDescriptor.insets = new Insets(0, 0, 5, 5);
		gbc_lblPageDescriptor.gridx = 1;
		gbc_lblPageDescriptor.gridy = 1;
		frmLogin.getContentPane().add(lblPageDescriptor, gbc_lblPageDescriptor);
		
		JLabel lblUname = new JLabel("Username");
		GridBagConstraints gbc_lblUname = new GridBagConstraints();
		gbc_lblUname.gridwidth = 2;
		gbc_lblUname.anchor = GridBagConstraints.EAST;
		gbc_lblUname.insets = new Insets(0, 0, 5, 5);
		gbc_lblUname.gridx = 0;
		gbc_lblUname.gridy = 2;
		frmLogin.getContentPane().add(lblUname, gbc_lblUname);
		
		txtUname = new JTextField();
		GridBagConstraints gbc_txtUname = new GridBagConstraints();
		gbc_txtUname.anchor = GridBagConstraints.NORTH;
		gbc_txtUname.gridwidth = 2;
		gbc_txtUname.insets = new Insets(0, 0, 5, 5);
		gbc_txtUname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUname.gridx = 2;
		gbc_txtUname.gridy = 2;
		frmLogin.getContentPane().add(txtUname, gbc_txtUname);
		txtUname.setColumns(10);
		
		JLabel lblPwd = new JLabel("Password");
		GridBagConstraints gbc_lblPwd = new GridBagConstraints();
		gbc_lblPwd.gridwidth = 2;
		gbc_lblPwd.anchor = GridBagConstraints.EAST;
		gbc_lblPwd.insets = new Insets(0, 0, 5, 5);
		gbc_lblPwd.gridx = 0;
		gbc_lblPwd.gridy = 3;
		frmLogin.getContentPane().add(lblPwd, gbc_lblPwd);
		
		pwdPwd = new JPasswordField();
		GridBagConstraints gbc_pwdPwd = new GridBagConstraints();
		gbc_pwdPwd.gridwidth = 2;
		gbc_pwdPwd.insets = new Insets(0, 0, 5, 5);
		gbc_pwdPwd.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwdPwd.gridx = 2;
		gbc_pwdPwd.gridy = 3;
		frmLogin.getContentPane().add(pwdPwd, gbc_pwdPwd);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/*check the user's account info and if it is correct,
				 *then proceed to the message tab (cMainDashboardWindow.java).
				 */
				String uName = txtUname.getText();
				String pwd = new String(pwdPwd.getPassword());
				String salt = null;
				String pWordSaltHash = null;
				boolean allowAccess = true;
				
				String selectUsers = "SELECT userName, pWordSaltHash, salt from users WHERE userName='" + uName + "'";
				ResultSet current = zDatabaseHandlerBackend.select(selectUsers);

				try{
					current.next();
					currUser = current.getString("username");
					pWordSaltHash = current.getString("pWordSaltHash");
					salt = current.getString("salt");
/*					System.out.println("currUser: " + currUser);
					System.out.println("DB pWordSaltHash: " + pWordSaltHash);
					System.out.println("salt: " + salt);
*/				} catch (SQLException e) {
					allowAccess = false;
					JOptionPane.showInputDialog(null, "Uh oh! Username not found. If you forgot your username, please type your email to have it sent to you.", "Incorrect Username");
				}
				if(allowAccess) {
					//Their username was legit, so now check their password.
					String inputpWordSaltHash = zDatabaseHandlerBackend.calculatePwordSaltStringHash(salt, pwd);
					//System.out.println("inputpWordSaltHash: " + inputpWordSaltHash);
					if(inputpWordSaltHash != pWordSaltHash) {
						allowAccess = false;
					} else {
						cMainDashboardWindow.runMessageTabs();
					}
				} 
				if(!allowAccess) {
					JOptionPane.showInputDialog(null, "If you forgot your password, please type your username to reset it.", "Incorrect Password");
					txtUname.setText("");
					pwdPwd.setText("");

				}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 4;
		frmLogin.getContentPane().add(btnLogin, gbc_btnNewButton);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new aCreateAccountWindow();
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 5;
		frmLogin.getContentPane().add(btnRegister, gbc_btnNewButton_1);
		
		JButton btnForgotPassword = new JButton("Forgot Password");
		btnForgotPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				zzzzzzzzSendEmailBackend.forgotPassword();
			}
		});
		GridBagConstraints gbc_btnForgotPassword = new GridBagConstraints();
		gbc_btnForgotPassword.insets = new Insets(0, 0, 5, 5);
		gbc_btnForgotPassword.gridx = 2;
		gbc_btnForgotPassword.gridy = 6;
		frmLogin.getContentPane().add(btnForgotPassword, gbc_btnForgotPassword);
	}
	
	public static String getCurrUser(){
		return currUser;
	}
	
	public static void setCurrUser(String userName){
		currUser = userName;
	}
}
