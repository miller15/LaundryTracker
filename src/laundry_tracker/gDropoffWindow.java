package laundry_tracker;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class gDropoffWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6778610575414893137L;

	private JPanel contentPane;
	static JFrame frmDropoff;
	private JTextField txtLname;
	private JTextField txtFname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gDropoffWindow frame = new gDropoffWindow();
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
	public gDropoffWindow() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		frmDropoff = new JFrame();
		frmDropoff.setTitle("Laundry Dropoff");
		frmDropoff.setBounds(100, 100, 465, 486);
		//frmDropoff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDropoff.getContentPane().setLayout(new MigLayout("", "[][][grow]", "[][][][][:10.00:20px,grow][:50px:50px,grow 50][][][][]"));
		
		JLabel lblTitle = new JLabel("Use this page when a client drops off a load of laundry to be done.");
		frmDropoff.getContentPane().add(lblTitle, "cell 0 0 3 1,alignx center");
		
		JLabel lblFname = new JLabel("Client's First Name:");
		frmDropoff.getContentPane().add(lblFname, "cell 1 2,alignx trailing");
		
		txtFname = new JTextField();
		frmDropoff.getContentPane().add(txtFname, "cell 2 2,growx");
		txtFname.setColumns(10);
		
		JLabel lblLname = new JLabel("Client's Last Name:");
		frmDropoff.getContentPane().add(lblLname, "cell 1 3,alignx trailing");
		
		txtLname = new JTextField();
		frmDropoff.getContentPane().add(txtLname, "cell 2 3,growx");
		txtLname.setColumns(10);
		
		JLabel lblDropoff = new JLabel("Date Dropped Off:");
		lblDropoff.setToolTipText("Default is the current date if left blank");
		frmDropoff.getContentPane().add(lblDropoff, "cell 1 4,alignx trailing");
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setToolTipText("If left blank, default to current date");
		frmDropoff.getContentPane().add(dateChooser, "cell 2 4,grow");
		
		JLabel lblLaundryNotes = new JLabel("Notes:");
		frmDropoff.getContentPane().add(lblLaundryNotes, "cell 1 5,alignx trailing");
		
		JTextArea textAreaNotes = new JTextArea();
		textAreaNotes.setWrapStyleWord(true);
		textAreaNotes.setLineWrap(true);

		JScrollPane scrollPaneNotes = new JScrollPane(textAreaNotes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frmDropoff.getContentPane().add(scrollPaneNotes, "flowy,cell 2 5,grow");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmDropoff.dispose();
			}
		});
		frmDropoff.getContentPane().add(btnCancel, "cell 2 9,alignx left");

		JButton btnAddLaundry = new JButton("Add");
		btnAddLaundry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//need to add this method to the Database Handler class
				//Add the laundry load to the db
				String fName = txtFname.getText();
				String lName = txtLname.getText();
				java.util.Date dropOffDate = dateChooser.getDate();
				String notes = textAreaNotes.getText();
				String currUser = bWelcomeScreenWindow.getCurrUser();
				String dayOfWeek;
				boolean moveon = true;
				boolean added = false;
				int client_id = 0;
				
				if(fName == null || fName == " " || fName == "") {
					JOptionPane.showConfirmDialog(frmDropoff, "First name cannot be blank!");
					moveon = false;
					txtFname.setCursor(getCursor());
				} else if(lName == null || lName == " " || lName == "") {
					int selection = JOptionPane.showConfirmDialog(frmDropoff, "Are you sure you want the last name to be blank?", "Last Name Empty", 0);
					// 0 = yes; 1 = no
					if (selection == 1) {
						moveon = false;
						txtLname.setCursor(getCursor());
					} 
				} 
				if (moveon) {
					String checkClientExists = "SELECT id, notes, monday, tuesday, wednesday, thursday, friday, saturday, sunday, eligible_today, load_outstanding FROM clients WHERE fName = " + "'" + fName + "'" + " AND lName = " + "'" + lName + "'" + ";";
					ResultSet existence = zDatabaseHandlerBackend.select(checkClientExists);
					String clientNote = null;
					try {
						existence.next();
						client_id = existence.getInt("id");
						clientNote = existence.getString("notes");
					} catch (SQLException e) {
						//No client existed
						moveon = false;
						debug.show_error("No Client Exists", "There is no client with that name currently in the database. Please add the client first!");
					}

					if(clientNote != null) {
						debug.show_error("Client Note", clientNote);
						// 0 = yes; 1 = no
						int decision = debug.show_warning("Client Note", "Do you still wish to accept his/her laundry?");
						if (decision == 1) {
							moveon = false;
						}
					}
					if (moveon) {
							dayOfWeek = debug.getDayOfWeek();
							try {
								if (!existence.getBoolean(dayOfWeek)) {
									moveon = false;
									debug.show_error("Ineligible", "The client is ineligible for laundry service on " + dayOfWeek);
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						if (moveon) {
							try {
								if (!existence.getBoolean("eligible_today")) {
									moveon = false;
									String[] options =  {"Deny Additional Load","Override and Accept Load"};
									int choice = JOptionPane.showOptionDialog(null, fName + " " + lName + " has already had laundry done today. You can override this warning and accept his/her laundry.", "Double Dipping!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
									if (choice == 0) {
										moveon = false;
									} else {
										moveon = true;
									}
								} else if (existence.getBoolean("load_outstanding")) {
									moveon = false;
									String[] options =  {"Deny Additional Load","Override and Accept Load"};
									int choice = JOptionPane.showOptionDialog(null, fName + " " + lName + " has laundry that is yet to be complete or that hasn't been picked up yet. You can override this warning and accept his/her laundry, but it is recommended you check the Laundry List and have the client pickup his/her laundry before accepting another load.", "Double Dipping!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
									if (choice == 0) {
										moveon = false;
									} else {
										moveon = true;
									}
								}
							} catch (HeadlessException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} 
						if (moveon) {
							debug.print("NOTES:_" + notes + "_");
							if(!notes.isEmpty()) {
								added = zDatabaseHandlerBackend.addLaundryLoad(client_id, dropOffDate, currUser, notes);
							} else {
								added = zDatabaseHandlerBackend.addLaundryLoad(client_id, dropOffDate, currUser);
							}
						}
					}
				}
				if (added) {
					frmDropoff.dispose();
					eViewEditClientWindow.update_eligible_today_flag(client_id);
					eViewEditClientWindow.update_load_outstanding_flag(Integer.toString(client_id)); 
					debug.show_error("Add Laundry Success", "Laundry Load successfully added!");
					cMainDashboardWindow.update_table();
				}
/*					if(success == 1) {
						//cMainDashboardWindow.main(null);
						frmDropoff.dispose();
						cMainDashboardWindow.runMessageTabs();
					} else {
						txtUsername.setText("");
					}
				} else if(pwdFlagMismatch) {
					JOptionPane.showConfirmDialog(null, "Oops! Your password didn't match. Please re-type.", "Pasword Mismatch Error", -1);
					pwdPword.setText("");
					pwdConfirm.setText("");
				}
*/			}
		});
		frmDropoff.getContentPane().add(btnAddLaundry, "cell 2 8,alignx left");
				
		frmDropoff.setVisible(true);
		
	}

}
