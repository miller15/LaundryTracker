package communication;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
public class RecipientsTable extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomTableModel recipientsModel;
	JTable table;
	JScrollPane scrollPane;	
	JButton btnSend;
	static String currOwner = MainWindow.getCurrUser();
	boolean pressCtrl = false;
	private static JPopupMenu rClickPopup;
	private static JMenuItem edit;
	private static JMenuItem delete;

	public RecipientsTable() {
		super(new GridLayout(2,0));
		recipientsModel = new CustomTableModel();
		recipientsModel.setTable();
		table = new JTable(recipientsModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		btnSend = new JButton("Send");
        
		rClickPopup = new JPopupMenu();
		edit = new JMenuItem("Edit");
		delete = new JMenuItem("Delete");
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = table.getSelectedRow();
				int numCols = table.getColumnCount();
				String[] rowValue = null;
				String[] fieldValues = new String[numCols];
				for(int k=0; k<fieldValues.length; k++){
					fieldValues[k] = (String)table.getValueAt(index, k);
				}
				String select = "SELECT * from contact_t WHERE contactId = '" + fieldValues[6] + "'";
				ResultSet all = DatabaseHandler.select(select);
				ResultSetMetaData metaData = null;
				try {
					metaData = all.getMetaData();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				int numColumns = 0;
				try {
					numColumns = metaData.getColumnCount();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					rowValue = new String[numColumns];
					int count = 0;
					while (all.next()) {
						count = all.getRow();
						for(int i=0; i < numColumns; i++){
							rowValue[i] = all.getString(i+1);
							System.out.println("One row select: ");
							System.out.println("Data[" + (count-1) + "][" + i + "]" + " : " + rowValue[i]);						
						}
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
				EditContact.main(rowValue);
				System.out.println("GOT HERE");
				//MessagePage.UPDATETABLE();
				System.out.println("FINISHED HERE");
				
				System.out.println("*****************************:::::" + index);
			}
		});
		
		//////////////////////////////////////
		delete.addActionListener(this);
		rClickPopup.add(edit);
		rClickPopup.add(delete);
		
		//Add listener components
		MouseListener popupListener = new PopupListener();
		table.addMouseListener(popupListener);		
		
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomTableModel model = (CustomTableModel)table.getModel();
		        String recipients[] = new String[table.getSelectedRowCount()];
		        
				int rowIndeces[] = table.getSelectedRows();
				System.out.println("*****rowIndeces.length: " + rowIndeces.length);
				for(int i=0; i<rowIndeces.length; i++){
					rowIndeces[i] = table.convertRowIndexToModel(rowIndeces[i]);
					
					recipients[i] = (String)model.getValueAt(rowIndeces[i], 5);
					
					System.out.println("");
					System.out.println("RecipientsTable.java: actionListener: ");
					System.out.println(recipients[i]);
					System.out.println("");
					
										
				}
				MessagePage.sendMessage(recipients, model.getOwnerEmail());
			}
		});
		
		scrollPane = new JScrollPane(table);
		add(scrollPane);
		add(btnSend);

	}
	
	class PopupListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
	    }

	    public void mouseReleased(MouseEvent e) {
	        maybeShowPopup(e);
	    }

	    private void maybeShowPopup(MouseEvent e) {
	        if (e.isPopupTrigger()) {
	            rClickPopup.show(e.getComponent(),
	                       e.getX(), e.getY());
	        }
	    }
	}
	
	public void updateTable(){
		recipientsModel.setTable();
		add(scrollPane);
	}
	
	static class CustomTableModel extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String[] colNames;
		private Object[][] rows; 
		
		public void setTable(){
			//String sql = "SELECT owner, contact_t.contactId, fName AS 'First Name', team_t.team AS 'Team' FROM contact_t JOIN team_t ON contact_t.contactId=team_t.contactId WHERE owner = '" + currOwner + "'";
			//String sql = "SELECT owner, contact_t.contactId, fName AS 'First Name' FROM contact_t WHERE owner = '" + currOwner + "'";
			//String sql = "SELECT fName AS 'First Name', lName AS 'Last Name', team AS 'Team', subTeam AS 'Sub-team', position AS 'Position' FROM contact_t JOIN team_t JOIN subteam_t JOIN position_t WHERE owner = '" + currOwner + "'";
			//String sql = "SELECT fName AS 'First Name', lName AS 'Last Name' FROM contact_t JOIN team_t ON contact_t.contactId = team_t.contactId WHERE contact_t.owner = '" + currOwner + "'";
			String sql = "SELECT team_t.team AS 'Team', subTeam_t.subTeam AS 'Sub-Team', position_t.position AS 'Position', "
					+ "contact_t.lName AS 'Last Name', contact_t.fName AS 'First Name', contact_t.email, contact_t.contactId FROM contact_t "
					+ "JOIN team_t ON contact_t.contactId = team_t.contactId "
					+ "JOIN subTeam_t ON team_t.teamId = subTeam_t.teamId "
					+ "JOIN position_t ON subTeam_t.subId = position_t.subTeamId "
					+ "WHERE contact_t.owner = '" + currOwner + "' ";
			ResultSet rs = DatabaseHandler.select(sql);
			System.out.println("RESULT SET: " + rs);
			if(rs == null){
				JOptionPane.showConfirmDialog(null, "There are no entries in the database. Please add some contacts", "No data", -1);				
				colNames = new String[0];
				rows = new Object[0][0];
				System.out.println("Populating Empty Table");
			} else {
				int numRows = 0;
				try {
					if (rs.last()) {
					    numRows = rs.getRow();
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
				
				colNames = new String[numColumns];
				for (int h =0; h < numColumns; h++){
					try {
						colNames[h] = metaData.getColumnLabel(h+1);
						System.out.println("Column Name " + h + ": " + colNames[h]);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					rows = new Object[numRows][numColumns];
					int count = 0;
					while (rs.next()) {
						count = rs.getRow();
						for(int i=0; i < numColumns; i++){
							rows[count-1][i] = rs.getString(i+1);
							System.out.println("Got here");
							System.out.println("Data[" + (count-1) + "][" + i + "]" + " : " + rows[count-1][i]);						
						}
					}
					/*System.out.println("Rows[" + (0) + "][" + 0 + "]" + " : " + rows[0][0]);
					System.out.println("Rows[" + (0) + "][" + 1 + "]" + " : " + rows[0][1]);
					System.out.println("Rows[" + (0) + "][" + 2 + "]" + " : " + rows[0][2]);
					System.out.println("Rows[" + (1) + "][" + 0 + "]" + " : " + rows[1][0]);
					System.out.println("Rows[" + (1) + "][" + 1 + "]" + " : " + rows[1][1]);
					System.out.println("Rows[" + (1) + "][" + 2 + "]" + " : " + rows[1][2]);*/




				} catch (SQLException e) {
					e.printStackTrace();
				}
				//fireTableChanged(null);
			}
			System.out.println("Populated Table");
		}
		
		public int getColumnCount(){
			return colNames.length;
		}
		
		public int getRowCount() {
			return rows.length;
		}
		
		public String getColumnName(int col) {
			return colNames[col];
		}
		
		public Object getValueAt(int row, int col) {
			return rows[row][col];
		}
		
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}
		
		@SuppressWarnings("unused")
		private void printDebugData() {
			int numRows = getRowCount();
			int numCols = getColumnCount();
			
			for(int i=0; i<numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j=0; j<numCols; j++) {
					System.out.print("  " + rows[i][j]);
				}
				System.out.println();
			}
			System.out.println("----------------------");
		}
		
		private String getOwnerEmail() {
			String owner = "default";
			String sql = "SELECT email FROM user_t WHERE user_t.userName = '" + currOwner + "'";
			ResultSet rs = DatabaseHandler.select(sql);
			try {
				while (rs.next()) {
						owner =  rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return owner;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
