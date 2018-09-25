/*package laundry_tracker;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class hViewAllClients extends JPanel{
	JPanel panelViewAllClients;
	JScrollPane scrollPaneClients;
	static JTable clientsTable;
	
	public hViewAllClients() {
		panelViewAllClients = new JPanel();
		panelViewAllClients.setBorder(new TitledBorder(new EtchedBorder(), "All the clients in the system are visible."));		
		scrollPaneClients = new JScrollPane();
		GridBagConstraints gbc_scrollPaneClients = new GridBagConstraints();
		gbc_scrollPaneClients.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPaneClients.gridheight = 23;
		gbc_scrollPaneClients.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneClients.gridx = 3;
		gbc_scrollPaneClients.gridy = 0;
		panelViewAllClients.add(scrollPaneClients, gbc_scrollPaneClients);
		create_clients_table(scrollPaneClients);
	
	}
		private static void create_clients_table(JScrollPane scrollPaneClients) {

			String default_laundry_query = "SELECT id, CONCAT(fName, ' ', lName) AS Name FROM clients ORDER BY Name;";
			create_clients_table(scrollPaneClients, default_laundry_query);
		}
		
		private static void create_clients_table(JScrollPane scrollPaneClients, String laundry_query) {
			try {
				clientsTable = new JTable(buildTableModelClients(laundry_query));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			clientsTable.setBackground(Color.WHITE);
			clientsTable.setColumnSelectionAllowed(false);
			clientsTable.setRowSelectionAllowed(true);
			clientsTable.setFillsViewportHeight(true);
			clientsTable.setBorder(new MatteBorder(1, 2, 2, 2, (Color) new Color(0, 0, 0)));
			clientsTable.setAutoCreateRowSorter(true);
			clientsTable.setUpdateSelectionOnSort(true);
			clientsTable.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent mouseEvent) {
					JTable table = (JTable) mouseEvent.getSource();
					Point point = mouseEvent.getPoint();
					int row = table.rowAtPoint(point);
					int column = table.columnAtPoint(point);
					if (mouseEvent.getClickCount() == 2) {
						if (column == 1) {
							//Bring up the client info page
							//Make the client info page a separate window.
							tabbedPane.setSelectedComponent(panelViewAllClients);
							int client_id_row =  Integer.parseInt( (String) clientsTable.getValueAt(clientsTable.getSelectedRow(), 6));
							//Integer.parseInt(client_id_row);
							new eViewEditClientWindow(client_id_row);
							//Populate the fields in the Add Client tab with the selected client
							//populate
							System.out.println("Client page");
						} else if (column == 5) {
							//Bring up the edit/view notes page
							int laundry_id = (int) clientsTable.getValueAt(clientsTable.getSelectedRow(), 0);
							String[] options = {"Save and Close", "Close"};
							debug.show_note_prompt("laundry_loads", String.valueOf(laundry_id), "Laundry Note", "<html>These are all the notes about the current load of laundry.<br>You can make changes and/or additions.<html>", options, true);
							//Refresh the table
							cMainDashboardWindow.update_table();
						} else {
							//Tell user they can't double click here.
							debug.show_error("No Double click", "You can't double click this column.");
						}
					}
				}
			});

			scrollPaneClients.setViewportView(clientsTable);
		}

		private static DefaultTableModel buildTableModelClients(String laundry_query) throws SQLException {
			ResultSet rs = zDatabaseHandlerBackend.select(laundry_query);
			ResultSetMetaData metaData = rs.getMetaData();

			//names of columns:
			Vector<String> columnNames = new Vector<String>();
			int colCount = metaData.getColumnCount();
			for (int column = 1; column <= colCount; column++) {
				System.out.println("COLUMN: " + metaData.getColumnName(column));
				columnNames.add(metaData.getColumnName(column));
			}

			//data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (rs.next()) {
				System.out.println("NAME: " + rs.getString("Name"));
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= colCount; columnIndex++) {
					vector.add(rs.getObject(columnIndex));
				}
				data.add(vector);
			} rs.close();
				return new DefaultTableModel(data, columnNames) {
					private static final long serialVersionUID = 8365935879746194659L;
					@Override
				    //This makes it so users can't edit the table values.
				    public boolean isCellEditable(int i, int i1) {
				        return false;
				    }
				};
		}

	
	
	
	}*/