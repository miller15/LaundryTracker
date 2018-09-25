package laundry_tracker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.toedter.calendar.JDateChooser;

public class debug {
	public static void print(String printStr) {
		System.out.println(printStr);
	}
	
	public static void show_error(String title, Object message)
	{
		JOptionPane.showConfirmDialog(null, message, title, -1);
	}

	public static String getDayOfWeek() {
		String dayOfWeek = null;
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		switch (day) {
			case Calendar.SUNDAY:
				dayOfWeek = "sunday";
			case Calendar.MONDAY:
				dayOfWeek = "monday";
			case Calendar.TUESDAY:
				dayOfWeek = "tuesday";
			case Calendar.WEDNESDAY:
				dayOfWeek = "wednesday";
			case Calendar.THURSDAY:
				dayOfWeek = "thursday";
			case Calendar.FRIDAY:
				dayOfWeek = "friday";
			case Calendar.SATURDAY:
				dayOfWeek = "saturday";
		}
		return dayOfWeek;
	}
	
	public static int show_warning(String title, Object message)
	{
		int selection = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
		// 0=yes; 1=no; 2=cancel
		return selection;
	}
	
	public static int show_question(String title, Object message)
	{
		int selection = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
		// 0=yes; 1=no
		return selection;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void show_note_prompt(String table, String id, String title, String message, String[] options, boolean edit_existing) {
		//This can be used for client notes and laundry notes.
		//It can also be used for adding a note and editing a note.
		//To edit, pass a value of true for the addition parameter.
		
		JFrame frame = new JFrame(title);
		final SpringLayout layout = new SpringLayout();
		final JPanel panel = new JPanel(layout);
		panel.setPreferredSize(new Dimension(350, 160));
		
		JLabel lblNote = new JLabel(message);
		panel.add(lblNote);
		JTextArea txtNote = new JTextArea();
		txtNote.setBorder(BorderFactory.createLineBorder(Color.black));
        txtNote.setLineWrap(true);
        txtNote.setWrapStyleWord(true);
        if(edit_existing) {
        	String retrieve_note = "SELECT notes FROM " + table + " WHERE id = " + id;
    		ResultSet note_result = zDatabaseHandlerBackend.select(retrieve_note);
    		try {
    			System.out.println("GETTING CURRENT NOTES: ");
    			note_result.next();
    			String current_note = note_result.getString("notes");
    			print(current_note);
    			txtNote.setText(current_note);
    		} catch (SQLException e) {
    			debug.show_error("Error retrieving laundry_load", "Could not retrieve laundry_load notes data.");
    			e.printStackTrace();
    		}

        }
        JScrollPane scrollPane = new JScrollPane(txtNote,
        		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(300, 125));
        panel.add(scrollPane);

        layout.putConstraint(SpringLayout.WEST, lblNote,
                0,
                SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, scrollPane,
                10,
                SpringLayout.SOUTH, lblNote);
        
         int selection = JOptionPane.showOptionDialog(frame,
                panel,
                title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                "No Note");
         String newNote = txtNote.getText();
    
         if(selection == 0) {
        		 //Add a note.
        	 String addNote = null;
        	 if (newNote.isEmpty() || newNote.equals("\n")) {
        		 System.out.println("NO NOTE ENETERED");
        		 addNote = "UPDATE " + table + " SET notes = null WHERE id = " + id;
        		 debug.print(addNote);
        	 } else {
        		 addNote = "UPDATE " + table + " SET notes = '" + newNote + "' WHERE id = " + id;
        		 debug.print("ENTERING NOTE: ");
        		 debug.print(addNote);
        	 }
        	 zDatabaseHandlerBackend.update(addNote);
         } 

	}
	
	public static List show_confirm_dialog(String editType) {
		List date_bool = new ArrayList();
		boolean quit = true;
		String chosenDate = null;
		String title = "Confirm date for " + editType;
		JFrame confirmFrame = new JFrame();
		final SpringLayout layout = new SpringLayout();
		final JPanel panel = new JPanel(layout);
		panel.setPreferredSize(new Dimension(350, 160));
		
		String messageType = "";
		if (editType == "markComplete") {
			messageType = "laundry completion.";
		} else if (editType == "pickup") {
			messageType = "pick-up.";
		}
		JLabel lblInstruction = new JLabel("You may edit the date and time of the " + messageType);
		panel.add(lblInstruction);
		
		JTextField dateChooser = new JTextField();
		dateChooser.setToolTipText("yyyy-MM-dd HH:mm:ss");
/*		JDateChooser dateChooser = new JDateChooser();
*/
		//Current Date and Time
		DateFormat dateFormatString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = new Date();
		LocalDateTime datetime = LocalDateTime.parse(dateFormatString.format(date), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		dateChooser.setText(dateFormatString.format(date));
		
		panel.add(dateChooser);
		
        layout.putConstraint(SpringLayout.WEST, lblInstruction,
                0,
                SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, dateChooser,
                10,
                SpringLayout.SOUTH, lblInstruction);
        String[] options = {"Continue", "Cancel"};
        int selection = JOptionPane.showOptionDialog(confirmFrame,
                panel,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                "Continue");
        if (selection == 0) {
        	//Continue
        	quit = false;
        	chosenDate = dateChooser.getText();
            //chosenDate = dateFormatString.format(dateChooser.getDate());
            date_bool.add(chosenDate);
            date_bool.add(quit);
        } else {
        	date_bool.add(chosenDate);
        	date_bool.add(quit);
        	confirmFrame.dispose();
        }
		return date_bool;
	}


	
	/*	Future forgot password skeleton. - email code from old project
	 * public static void sendMessage(String[] recipients, String ownerEmail)
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

	
	public static void main(String[] args) {
/*		String password = "myPassword";
		print(password);
		byte[] passwordByte = password.getBytes();
		String passwordString = passwordByte
		print(passwordByte.toString());
*/		

		//byte[] salt = Password.getNextSalt();
		char[] pword = "tim".toCharArray();
		//byte[] hash = Password.hash(pword, salt);
		System.out.println("PWORD two lines: " + pword.toString());
		char[] tim = "tim".toCharArray();
		System.out.println("TIM one line: " + "tim".toCharArray().toString());
		System.out.println("tim two lines: " + tim.toString());
//		System.out.println("Salt: " + salt);
//		System.out.println("pword: " + pword);
//		System.out.println("hash: " + hash);
		//boolean same = Password.isExpectedPassword("tim".toCharArray(), salt, hash);
		//System.out.println(same);
	}

}
