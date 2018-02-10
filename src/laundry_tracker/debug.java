package laundry_tracker;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

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
	
	public static void show_note_prompt(String table, String id, String title, String message, String[] options) {
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
         if(selection == 0) {
        	 //Add a note.
        	 String addNote = "UPDATE " + table + " SET notes = '" + txtNote.getText() + "' WHERE id = " + id;
        	 zDatabaseHandlerBackend.update(addNote);
         } 

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
