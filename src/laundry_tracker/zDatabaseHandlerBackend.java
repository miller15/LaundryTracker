package laundry_tracker;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Dictionary;
import java.util.Map;

import javax.swing.JOptionPane;

public class zDatabaseHandlerBackend {
	static final String HOST_URL = "jdbc:mysql://localhost/";
	static final String DB_URL = "jdbc:mysql://localhost/laundry_tracker";
	static Connection connHost = null;

	static String USER = "root";
	static String PASS = "admin";
	
	static String currUser = bWelcomeScreenWindow.getCurrUser();


	private static Connection connect_db()
	{
		Connection dbConn = null;
		boolean dbConnectionSuccess = false;
		while(!dbConnectionSuccess) {
			try {
				if( USER != null && PASS != null) {
					dbConn = DriverManager.getConnection(DB_URL, USER, PASS);
					dbConnectionSuccess = true;
				} else {
					JOptionPane.showMessageDialog(aCreateAccountWindow.frmCreateaccount, "There was an error. Please try again or contact the developer.");
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				//show_error("Database Error", e);
				USER = (String)JOptionPane.showInputDialog(aCreateAccountWindow.frmCreateaccount, "You may have changed the database's username. Please enter the new db username (default was root)", "Username", JOptionPane.PLAIN_MESSAGE);
				PASS = (String)JOptionPane.showInputDialog(aCreateAccountWindow.frmCreateaccount, "You may have changed the database's password. Please enter the new db password (default was admin)", "Password", JOptionPane.PLAIN_MESSAGE);
			}
		}
		return dbConn;
	}
	
	private static Connection connect_host()
	{
		//Connection hostConn = null;
		try {
			System.out.println("trying connect to db");
			connHost = DriverManager.getConnection(HOST_URL, USER, PASS);
			//JOptionPane.showConfirmDialog(null, "Connected!!", "Connection Established", -1);
			
		} catch (SQLException e) {
			String message = e.getMessage();
			System.out.println("MESSAGE: " + message);
			e.printStackTrace();
			USER = (String)JOptionPane.showInputDialog(null, "It appears you changed the database's username. Please enter the new db username (default was root)", "Username", JOptionPane.PLAIN_MESSAGE);
			System.out.println("USER:::::: "+ USER);
			PASS = (String)JOptionPane.showInputDialog(null, "It appears you changed the database's password. Please enter the new db password (default was admin)", "Password", JOptionPane.PLAIN_MESSAGE);
			connect_host();
			//show_error("Connection to host error", "Sorry, but we could not connect to the host.");
			e.printStackTrace();
		}
		return connHost;
	}
	
	private static Statement create_db_skeleton() {
		Statement stmtCreateDB = null;
		try {
			stmtCreateDB = connHost.createStatement();
			String sqlCreateDB = "CREATE DATABASE IF NOT EXISTS laundry_tracker";
			try{
				stmtCreateDB.executeUpdate(sqlCreateDB);
				System.out.println("Created the database skeleton.");
			} catch(SQLException e) {
				//nothing we can do
				//e.printStackTrace();
				show_error("Error creating db skeleton", e);
				//System.out.println("OOPS!! Something went wrong creating the database!");
			}
		} catch (SQLException e1) {
			//e1.printStackTrace();
			show_error("Error connecting to database", e1);
		} /*finally{
			disconnect_db(stmtCreateDB);
		}*/
		
		return stmtCreateDB;
	}
	
	private static void create_db_tables(Connection dbConn)
	{
		Statement stmtCreateT = null;
		try{
			stmtCreateT = dbConn.createStatement();

			String createUsers = "CREATE TABLE IF NOT EXISTS users("
					+ "userName VARCHAR(25) PRIMARY KEY, "
					+ "pWordSaltHash VARBINARY(50) NOT NULL COMMENT 'This is the hash of the password combined with the salt.', "
					+ "email varChar(50) NOT NULL, "
					+ "fName varChar(20) NOT NULL, "
					+ "lName varChar(25) NOT NULL,"
					+ "salt VARBINARY(50) NOT NULL)";
				
			String createClients = "CREATE TABLE IF NOT EXISTS clients(" +
					"id INT PRIMARY KEY AUTO_INCREMENT, " + 
					"fName varChar(20) NOT NULL, " +
					"lName varChar(25), " +
					"monday bool NOT NULL DEFAULT TRUE COMMENT 'True means eligible for laundry.', " + 
					"tuesday bool NOT NULL DEFAULT TRUE, " + 
					"wednesday bool NOT NULL DEFAULT TRUE, " + 
					"thursday bool NOT NULL DEFAULT TRUE, " + 
					"friday bool NOT NULL DEFAULT TRUE, " + 
					"saturday bool NOT NULL DEFAULT TRUE, " + 
					"sunday bool NOT NULL DEFAULT TRUE, " + 
					//eligible_today value gets updated via Windows Task Scheduler event named UpdateTodayFlag. Bash script located in C:\ProgramData\MySQL\MySQL Server 5.7\ updateTodayFlag.sh
					"eligible_today bool NOT NULL DEFAULT TRUE COMMENT 'This will save the state of a clients laundry for the current day. At midnight, all clients eligible_today statuses will reset to TRUE using an auto event created with windows task scheduler', " +
					"load_outstanding bool NOT NULL DEFAULT FALSE COMMENT 'This indicates if a user has any loads they havent picked up yet. True means they have dropped off or completed loads that havent been picked up. False means theyre all caught up and good to go!', " +
					"notes BLOB)";

			String createLaundryLoads = "CREATE TABLE IF NOT EXISTS laundry_loads(" +
					"id INT PRIMARY KEY AUTO_INCREMENT, " +
					"client_id INT NOT NULL, " +
					"drop_off datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
					"drop_off_signature varChar(15) NOT NULL COMMENT 'This is the username of the person who logged the client dropping off the laundry.', " +
					"load_complete datetime, " +
					"load_complete_sig varChar(15) COMMENT 'This is the username of the person who marked the load as completed (aka ready for pickup).', " +
					"pick_up datetime, " +
					"pick_up_sig varChar(15) COMMENT 'This is the username of the person who handed the laundry to the client.', " +
					"notes BLOB, " +
					"FOREIGN KEY(client_id) REFERENCES clients(id) ON DELETE CASCADE ON UPDATE CASCADE)";
			
			String insertClients = "INSERT INTO clients (fName, lName) VALUES ('Tim', 'Miller')";
			String insertUsers = "INSERT INTO users (userName, pWordSaltHash, email, fName, lName, salt) VALUES ('km', 'km', 'km@km', 'kim', 'last', 'salt')";
			String insertLaundryLoads = "INSERT INTO laundry_loads (client_id, drop_off_signature) VALUES (1,'km'), (1,'km')";
			
			String createClientsArchive = "CREATE TABLE IF NOT EXISTS clients_archive(" +
					"id INT PRIMARY KEY AUTO_INCREMENT, " + 
					"fName varChar(20) NOT NULL, " +
					"lName varChar(25) NOT NULL, " +
					"monday bool NOT NULL DEFAULT TRUE COMMENT 'True means eligible for laundry.', " + 
					"tuesday bool NOT NULL DEFAULT TRUE, " + 
					"wednesday bool NOT NULL DEFAULT TRUE, " + 
					"thursday bool NOT NULL DEFAULT TRUE, " + 
					"friday bool NOT NULL DEFAULT TRUE, " + 
					"saturday bool NOT NULL DEFAULT TRUE, " + 
					"sunday bool NOT NULL DEFAULT TRUE, " + 
					"eligible_today bool NOT NULL DEFAULT TRUE COMMENT 'This will save the state of a clients laundry for the current day. Since its an archive, it does not get updated.', " +
					"load_outstanding bool NOT NULL DEFAULT FALSE COMMENT 'This indicates if a user has any loads they havent picked up yet. True means they have dropped off or completed loads that havent been picked up. False means theyre all caught up and good to go!', " +
					"notes BLOB)";
			
			String createLaundryLoadsArchive = "CREATE TABLE IF NOT EXISTS laundry_loads_archive("
					+ "id INT PRIMARY KEY AUTO_INCREMENT, "
					+ "client_id INT NOT NULL, "
					+ "drop_off datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, "
					+ "drop_off_signature varChar(15) NOT NULL COMMENT 'This is the username of the person who logged the client dropping off the laundry.', "
					+ "load_complete datetime, "
					+ "load_complete_sig varChar(15) COMMENT 'This is the username of the person who marked the load as completed (aka ready for pickup).', "
					+ "pick_up datetime, "
					+ "pick_up_sig varChar(15) COMMENT 'This is the username of the person who handed the laundry to the client.', "
					+ "notes BLOB, "
					+ "FOREIGN KEY(client_id) REFERENCES clients_archive(id) ON DELETE CASCADE ON UPDATE CASCADE)";
			
			stmtCreateT.executeUpdate("DROP TABLE IF EXISTS users");
			System.out.println("Successfully dropped the users table.");
			stmtCreateT.executeUpdate("DROP TABLE IF EXISTS laundry_loads");
			System.out.println("Successfully dropped the laundry_loads table.");
			stmtCreateT.executeUpdate("DROP TABLE IF EXISTS clients");
			System.out.println("Successfully dropped the clients table.");
			stmtCreateT.executeUpdate("DROP TABLE IF EXISTS laundry_loads_archive");
			System.out.println("Successfully dropped the laundry_loads_archive table.");
			stmtCreateT.executeUpdate("DROP TABLE IF EXISTS clients_archive");
			System.out.println("Successfully dropped the clients_archive table.");
			stmtCreateT.executeUpdate(createUsers);
			System.out.println("Successfully created the users table.");
			stmtCreateT.executeUpdate(createClients);
			System.out.println("Successfully created the clients table.");
			stmtCreateT.executeUpdate(createLaundryLoads);
			System.out.println("Successfully created the laudry loads table.");
			stmtCreateT.executeUpdate(createClientsArchive);
			System.out.println("Successfully created the clients archive table.");
			stmtCreateT.executeUpdate(createLaundryLoadsArchive);
			System.out.println("Successfully created the laundry loads archive table.");
			stmtCreateT.execute(insertClients);
			stmtCreateT.execute(insertUsers);
			stmtCreateT.execute(insertLaundryLoads);
			
		} catch(SQLException e1) {
			//e1.printStackTrace();
			show_error("Error creating database tables", e1);
		} /*finally{
			disconnect_db(stmtCreateT);
			disconnect_host(dbConn);
		}*/
	}
	
	private static void disconnect_db(Statement create)
	{
		try{
			if(create!=null)
				create.close();
				System.out.println("Disconnecting from DB");
		}catch(SQLException e){
			show_error("Database disconnect error", e);
		}
	}
	
	private static void disconnect_host(Connection hostConn){
		try{
			if(hostConn!=null){
				hostConn.close();
				System.out.println("Disconnecting from host");
			}
		}catch(SQLException e3){
			e3.printStackTrace();
		}
	}
		
	public static void initialize_db(){
		Statement stmtCreateDB = null;

		System.out.println("Connecting to Database's host");
		//connHost = DriverManager.getConnection(HOST_URL, USER, PASS); //connecting to the host
		connHost = connect_host();
		//System.out.println("$$$$Connection Established to: " + HOST_URL);
		
		System.out.println("Creating the database at " + DB_URL);
		stmtCreateDB = create_db_skeleton();
		
		Connection connDB = connect_db(); //connecting to the actual DB we just created on the host					
		create_db_tables(connDB);
		System.out.println("DB SUCCESS!!!!");
		
		disconnect_db(stmtCreateDB);
		disconnect_host(connHost);
	
		System.out.println("Goodbye!");

	}

	public static void reBoot(){
		Connection connDB = connect_db();
	}
	
	public static void show_error(String title, Object message)
	{
		JOptionPane.showConfirmDialog(null, message, title, -1);
	}

	
	public static int addUser(String fName, String lName, String uName,
								  byte[] pWordSaltHash, String email, byte[] salt) {
		boolean success;

		String insertUser = "INSERT INTO users(userName, pWordSaltHash, email, fName, lName, salt) VALUES ('"
				+ uName + "', " + "?" + ", '" + email + "', '" + fName + "', '" + lName + "', " + "?" + ")";
		System.out.println("*****This is what is actually being inserted: ");
		System.out.println(insertUser);
		Connection dbConn = connect_db();
		PreparedStatement pstmt = null;
		int successful = 0;
		try {
			pstmt = dbConn.prepareStatement(insertUser);
			pstmt.setBytes(1, pWordSaltHash);
			pstmt.setBytes(2, salt);
			System.out.println("PSTMT*****:");
			System.out.println(pstmt);
			successful = pstmt.executeUpdate();
			System.out.println(successful);
			if (successful == 1) {
				System.out.println("INSERT worked");
			} else {
				System.out.println("INSERT FAILED");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally{
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			disconnect_host(dbConn);
		}
/*		try {
			Statement insert = dbConn.createStatement();
			insert.executeUpdate(insertUser);
			ResultSet current = zDatabaseHandlerBackend.select("SELECT * FROM users WHERE userName = 'tim'");
			System.out.println("All the stuff we just added: uname, hash, salt: ");
			System.out.println(current.getString("username"));
			System.out.println(current.getString("pWordSaltHash"));
			System.out.println(current.getString("salt"));

			//System.out.println("Successfully added the user: " + uName);
			success = true;
		} catch (SQLException e) {
			show_error("Create statement Error", "That user already exists. Please choose a different username.");
			System.out.println(e);
			success = false;
		}
*/		return successful;
	}

	public static String calculatePwordSaltStringHash(String saltString, String password) {
		
		//need the salt to be in a bytes[]
		//System.out.println("Salt in function: " + saltString);
		//byte[] saltBytes = saltString.getBytes(StandardCharsets.ISO_8859_1 ); // Java 7+ only
		//System.out.println("saltBytes after one manip: " + saltBytes.toString());
	    //Hash the password using the SHA-256 algorithm
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
		}
//		byte[] pWordHashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8)); //Input: string. Output: bytes[] --Now the password has been hashed!
		String pWordHashString = digest.digest(password.getBytes(StandardCharsets.UTF_8)).toString(); //Input: string. Output: bytes[] --Now the password has been hashed!
		
		//Concatenate the pWordHash (byte[]) onto the saltBytes.
/*		byte[] pWordSaltBytes = new byte[saltBytes.length + pWordHashBytes.length];
		System.arraycopy(saltBytes, 0, pWordSaltBytes, 0, saltBytes.length);
		System.arraycopy(pWordHashBytes, 0, pWordSaltBytes, saltBytes.length, pWordHashBytes.length);
*/		String pWordSaltString = saltString + pWordHashString;
		
		
		//Now finally, we can create the hash that will be stored in the db, used to validate the user's password when logging in.
		//Input: String. Output: string (by default it's a bytes[] but we convert it to string because that's what's useful to us.)
//		String pWordSaltHashStr = digest.digest(pWordSaltBytes**.toString()**.getBytes(StandardCharsets.UTF_8)).toString();
		String pWordSaltHashStr = digest.digest(pWordSaltString.getBytes(StandardCharsets.ISO_8859_1)).toString();
		System.out.println("Shouldn't have run this method. pWordSaltHashString at the end: " + pWordSaltHashStr);
		return pWordSaltHashStr;
	}
	
	public static boolean addClient(String fName, String lName, String phone_number, Map<String, Boolean> eligibility, String notes) {
		boolean worked = true;
		//System.out.println("Current User from zDatabaseHandlerBackend Class: " + currentUser);
		String insertClient = "INSERT INTO clients(fName, lName, monday, tuesday, wednesday, thursday, friday, saturday, sunday, notes) VALUES ('"
				+ fName + "', '" + lName + "', " + eligibility.get("monday") + ", " + eligibility.get("tuesday") + ", " + eligibility.get("wednesday") + ", " + eligibility.get("thursday") + ", " + eligibility.get("friday") + ", " + eligibility.get("saturday") + ", " + eligibility.get("sunday") + ", '" + notes + "')";
		System.out.println(insertClient);
		Connection dbConn = connect_db();
		Statement insert = null;
		try{
			insert = dbConn.createStatement();
			insert.executeUpdate(insertClient, Statement.RETURN_GENERATED_KEYS);		    
		} catch (SQLException e) {
			worked = false;
			show_error("Insert Error. Please try again. If error continues, contact the developer.", e);
			e.printStackTrace();
		} finally{
			disconnect_db(insert);
			disconnect_host(dbConn);
		}
		return worked;
	}

	public static boolean addClient(String fName, String lName, String phone_number, Map<String, Boolean> eligibility) {
		boolean worked = true;
		String insertClient = "INSERT INTO clients(fName, lName, monday, tuesday, wednesday, thursday, friday, saturday, sunday) VALUES ('"
				+ fName + "', '" + lName + "', " + eligibility.get("monday") + ", " + eligibility.get("tuesday") + ", " + eligibility.get("wednesday") + ", " + eligibility.get("thursday") + ", " + eligibility.get("friday") + ", " + eligibility.get("saturday") + ", " + eligibility.get("sunday") + ")";
		System.out.println(insertClient);
		Connection dbConn = connect_db();
		Statement insert = null;
		try{
			insert = dbConn.createStatement();
			insert.executeUpdate(insertClient, Statement.RETURN_GENERATED_KEYS);		    
		} catch (SQLException e) {
			worked = false;
			show_error("Insert Error. Please try again. If error continues, contact the developer.", e);
			e.printStackTrace();
		} finally{
			disconnect_db(insert);
			disconnect_host(dbConn);
		}
		return worked;
	}
	
	public static boolean addLaundryLoad(int client_id, java.util.Date dropOffDate, String drop_off_sig, String notes) {
		boolean worked = true;
		java.sql.Date sqlDate = null;
		try {
			sqlDate = new java.sql.Date(dropOffDate.getTime());
			String insertLaundryLoad = "INSERT INTO laundry_loads(client_id, drop_off, drop_off_signature, notes) VALUES ('"
					+ client_id + "', '" + sqlDate + "', '" + drop_off_sig + "', '" + notes + "')";
			System.out.println("************************");
			System.out.println(insertLaundryLoad);
			Connection dbConn = connect_db();
			Statement insert = null;
			try {
				insert = dbConn.createStatement();
				insert.executeUpdate(insertLaundryLoad, Statement.RETURN_GENERATED_KEYS);
			} catch (SQLException e) {
				worked = false;
				System.out.println(e);
				show_error("Insert laundry_load error. Please try again. If error continues, contact the developer.", e);
			} finally{
				disconnect_db(insert);
				disconnect_host(dbConn);
			}
		} catch (java.lang.NullPointerException e) {
			worked = false;
			debug.show_error("Choose a date", "You must choose a dropoff date!");
		}
		return worked;
	}
	
	public static boolean addLaundryLoad(int client_id, java.util.Date dropOffDate, String drop_off_sig) {
		boolean worked = true;
		java.sql.Date sqlDate = null;
		try {
			sqlDate = new java.sql.Date(dropOffDate.getTime());
			String insertLaundryLoad = "INSERT INTO laundry_loads(client_id, drop_off, drop_off_signature) VALUES ('"
					+ client_id + "', '" + sqlDate + "', '" + drop_off_sig + "')";
			Connection dbConn = connect_db();
			Statement insert = null;
			try {
				insert = dbConn.createStatement();
				insert.executeUpdate(insertLaundryLoad, Statement.RETURN_GENERATED_KEYS);
			} catch (SQLException e) {
				worked = false;
				System.out.println(e);
				show_error("Insert laundry_load error. Please try again. If error continues, contact the developer.", e);
			} finally{
				disconnect_db(insert);
				disconnect_host(dbConn);
			}
		} catch (java.lang.NullPointerException e) {
			worked = false;
			debug.show_error("Choose a date", "You must choose a dropoff date!");
		}
		return worked;
	}
	
	public static boolean updateEntry(String mark_laundry_complete) {
		//Run any db query that starts with UPDATE
		debug.print(mark_laundry_complete);
		boolean worked = true;
		try {
			Connection dbConn = connect_db();
			Statement update = null;
			try {
				update = dbConn.createStatement();
				System.out.println(update);
				int result = update.executeUpdate(mark_laundry_complete);
				System.out.println("Result: " + result);
			} catch (SQLException e) {
				worked = false;
				System.out.println(e);
				show_error("Update failed.", "Update failed. Please try again.");
			} finally{
				disconnect_db(update);
				disconnect_host(dbConn);
			}
		} catch (java.lang.NullPointerException e) {
			worked = false;
			debug.show_error("Connection error", "Couldn't connect to the database.");
		} 
		return worked;
	}

	public static boolean updateClient(int client_id, String fName, String lName, String phone_number, Map<String, Boolean> eligibility, String notes) {
		boolean worked = true;
		String updateClient = "UPDATE clients SET "
				+ "fName = '" + fName 
				+ "', lName = '" + lName 
				+ "', monday = " + eligibility.get("monday") 
				+ ", tuesday = " + eligibility.get("tuesday") 
				+ ", wednesday = " + eligibility.get("wednesday") 
				+ ", thursday = " + eligibility.get("thursday") 
				+ ", friday = " + eligibility.get("friday") 
				+ ", saturday = " + eligibility.get("saturday") 
				+ ", sunday = " + eligibility.get("sunday") 
				+ ", eligible_today = " + eligibility.get("today") 
				+ ", load_outstanding = " + eligibility.get("load_outstanding")
				+ ", notes = ";
		if(notes == null) {
			updateClient += "null";
		} else {
			updateClient += "'" + notes + "'";
		}
		System.out.println(updateClient);
		Connection dbConn = connect_db();
		Statement update = null;
		try{
			update = dbConn.createStatement();
			update.executeUpdate(updateClient, Statement.RETURN_GENERATED_KEYS);		    
		} catch (SQLException e) {
			worked = false;
			show_error("Update Error. Please try again. If error continues, contact the developer.", e);
			e.printStackTrace();
		} finally{
			disconnect_db(update);
			disconnect_host(dbConn);
		}
		return worked;
	}
	
	public static void delete(String delete){
		Connection dbConn = connect_db();
		Statement stDelete = null;
		try {
			stDelete = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try{
			stDelete.execute(delete);
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			disconnect_db(stDelete);
			disconnect_host(dbConn);
		}

	}
	
	public static void update(String update) {
		Connection dbConn = connect_db();
		Statement stUpdate = null;
		try { 
			stUpdate = dbConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		try {
			stUpdate.executeUpdate(update);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			disconnect_db(stUpdate);
			disconnect_host(dbConn);
		}
	}
	
	public static ResultSet select(String sql) {
		Connection dbConn = connect_db();
		Statement select = null;
		try {
			select = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ResultSet rs = null;
		try {
			rs = select.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*finally{
			disconnect_db(select);
			disconnect_host(dbConn);
		}*/
		return rs;
		//STEP 5: Extract data from result set
/*		try {
			while(rs.next()){
				//Retrieve by column name
			    int id  = rs.getInt("contactId");
			    String first = rs.getString("fName");
			    String team = rs.getString("team");
			    String last = rs.getString("lName");
			    String owner = rs.getString("owner");
			    String email = rs.getString("email");


			    //Display values
			    System.out.print("Contact ID: " + id);
			    System.out.print(", First Name: " + first);
			    System.out.println(", Team: " + team);
			    System.out.print(", Last Name: " + last);
			    System.out.print(", Owner: " + owner);
			    System.out.println(", Email: " + email);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
	}

	
}
