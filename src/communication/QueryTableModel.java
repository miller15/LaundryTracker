package communication;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class QueryTableModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private static Vector results;
/*	private static String driver = "com.mysql.jdbc.Driver";
	Connection db;
*/	
	public QueryTableModel() {
	    results = new Vector();
	    
	    /*new org.postgresql.Driver();
	    try {
            Class.forName(driver);
            db = DriverManager.getConnection(url, user, password);
          statement = db.createStatement();
    } catch (ClassNotFoundException e) {
            ErrConsoleData.getInstance().append(e.toString());
         // e.printStackTrace();
    } */
	  }
	
	public  void populateTable(){
		//results = new Vector();
		//String sql = "SELECT contactId, fName, lName, owner, email FROM contact_t";
		String sql = "SELECT contact_t.contactId, fName, team_t.team FROM contact_t JOIN team_t ON contact_t.contactId=team_t.contactId";
		ResultSet rs = DatabaseHandler.select(sql);
		System.out.println("RESULT SET: " + rs);
		if(rs == null){
			JOptionPane.showConfirmDialog(null, "There are no entries in the database. Please add some contacts", "No data", -1);
		} else {
			int numRows = 0;
			try {
				if (rs.last()) {
				    numRows = rs.getRow();
				    // Move to beginning
				    rs.beforeFirst();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			ResultSetMetaData metaData = null;
			try {
				metaData = rs.getMetaData();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int numColumns = 0;
			try {
				numColumns = metaData.getColumnCount();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String[] colNames = new String[numColumns];
			for (int h =0; h < numColumns; h++){
				try {
					colNames[h] = metaData.getColumnName(h+1);
					System.out.println("Column Name " + h + ": " + colNames[h]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				Object[][] rows = new Object[numRows][numColumns];
				int count = 0;
				while (rs.next()) {
					count = rs.getRow();
					//String[] record = new String[numColumns];
					for(int i=0; i < numColumns; i++){
						rows[count-1][i] = rs.getString(i+1);
						System.out.println("Got here");
						//record[i] = rs.getString(i+1);
						//System.out.println("Record " + i + " : " + record[i]);
						System.out.println("Data[" + (count-1) + "][" + i + "]" + " : " + rows[count-1][i]);						
					}
					//count++;
					//results.addElement(record);
					//System.out.println("Whole Record: " + record);
				}
				System.out.println("Rows[" + (0) + "][" + 0 + "]" + " : " + rows[0][0]);
				System.out.println("Rows[" + (0) + "][" + 1 + "]" + " : " + rows[0][1]);
				System.out.println("Rows[" + (0) + "][" + 2 + "]" + " : " + rows[0][2]);
				System.out.println("Rows[" + (1) + "][" + 0 + "]" + " : " + rows[1][0]);
				System.out.println("Rows[" + (1) + "][" + 1 + "]" + " : " + rows[1][1]);
				System.out.println("Rows[" + (1) + "][" + 2 + "]" + " : " + rows[1][2]);




			} catch (SQLException e) {
				e.printStackTrace();
			}
			//fireTableChanged(null);
		}
		System.out.println("Populated Table");
		
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
