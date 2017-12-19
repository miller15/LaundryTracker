package communication;
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


public class MainWindow {

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
			DatabaseHandler.initialize_db();
			firstTime = false;
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
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
		txtpnTitle.setText("Welcome to the\n Communicator Tool!");
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
				 *then proceed to the message tab (MessagePage.java).
				 */
				String uName = txtUname.getText();
				String pwd = new String(pwdPwd.getPassword());
				boolean allowAccess = true;
				
				String selectUsers = "SELECT userName, pWord from user_t WHERE userName='" + uName + "' AND pWord='" + pwd + "'";
				ResultSet current = DatabaseHandler.select(selectUsers);
				try{
					current.next();
				} catch (SQLException e) {
					allowAccess = false;
					JOptionPane.showConfirmDialog(null, "Error", "NO ENTRY", -1);
				}
				try{
					currUser = current.getString("userName");
					System.out.println("CURRENT USER: " + currUser);
				} catch (SQLException user) {
					allowAccess = false;
				}
				if(allowAccess){
						MessagePage.runMessageTabs();
				} else {
					JOptionPane.showConfirmDialog(null, "Please re-type your username and password.", "Login Error", -1);
					txtUname.setText("");;
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
				new CreateAccount();
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 5;
		frmLogin.getContentPane().add(btnRegister, gbc_btnNewButton_1);
	}
	
	public static String getCurrUser(){
		return currUser;
	}
	
	public static void setCurrUser(String userName){
		currUser = userName;
	}
}
