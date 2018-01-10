package communication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DatabaseHandler {
	static final String HOST_URL = "jdbc:mysql://localhost/";
	static final String DB_URL = "jdbc:mysql://localhost/communicate_db";
	static Connection connHost = null;

	static String USER = "root";
	static String PASS = "admin";
	
	static String currUser = MainWindow.getCurrUser();


	private static Connection connect_db()
	{
		Connection dbConn = null;
		try {
			dbConn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
			show_error("Database Error", e);
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
			USER = (String)JOptionPane.showInputDialog(null, "Please enter your BC username", "Username", JOptionPane.PLAIN_MESSAGE);
			System.out.println("USER:::::: "+ USER);
			PASS = (String)JOptionPane.showInputDialog(null, "Please enter your BC password", "Password", JOptionPane.PLAIN_MESSAGE);
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
			String sqlCreateDB = "CREATE DATABASE IF NOT EXISTS communicate_db";
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
		}
		
		return stmtCreateDB;
	}
	
	private static void create_db_tables(Connection dbConn)
	{
		try{
			Statement stmtCreateT = dbConn.createStatement();

			String createUser_t = "CREATE TABLE IF NOT EXISTS user_t(userName VARCHAR(15) PRIMARY KEY, "
					+ "pWord varChar(15) NOT NULL, "
					+ "email varChar(40) NOT NULL, "
					+ "fName varChar(15) NOT NULL, "
					+ "lName varChar(15) NOT NULL)";
				
			String createContact_t = "CREATE TABLE IF NOT EXISTS contact_t(" +
					"contactId INT PRIMARY KEY AUTO_INCREMENT, " + 
					"fName varChar(15) NOT NULL, " +
					"lName varChar(15) NOT NULL, " +
					"owner varChar(15) NOT NULL, " + 
					"email varChar(40) NOT NULL, " + 
					"FOREIGN KEY(owner) REFERENCES communicate_db.user_t(userName) ON DELETE CASCADE)";
	
			String createTeam_t = "CREATE TABLE IF NOT EXISTS team_t("
					+ "teamId INT PRIMARY KEY AUTO_INCREMENT, "
					+ "team varChar(15) NOT NULL, "
					+ "contactId INT, "
					+ "FOREIGN KEY(contactId) REFERENCES communicate_db.contact_t(contactId) ON DELETE CASCADE)";
			
			String createSubTeam_t = "CREATE TABLE IF NOT EXISTS subTeam_t("
					+ "subId INT PRIMARY KEY AUTO_INCREMENT, "
					+ "subTeam varChar(15), "
					+ "teamId INT, "
					+ "FOREIGN KEY(teamId) REFERENCES communicate_db.team_t(teamId) ON DELETE CASCADE)";
			
			String createPosition_t = "CREATE TABLE IF NOT EXISTS position_t("
					+ "posId INT PRIMARY KEY AUTO_INCREMENT, "
					+ "position varChar(15), "
					+ "subTeamId INT, "
					+ "FOREIGN KEY(subTeamId) REFERENCES communicate_db.subTeam_t(subId) ON DELETE CASCADE)";
			
/*			stmtCreateT.executeUpdate("DROP TABLE IF EXISTS position_t");
			System.out.println("Successfully dropped the position table.");
			stmtCreateT.executeUpdate("DROP TABLE IF EXISTS subTeam_t");
			System.out.println("Successfully dropped the subTeam table.");
			stmtCreateT.executeUpdate("DROP TABLE IF EXISTS team_t");
			System.out.println("Successfully dropped the team table.");
			stmtCreateT.executeUpdate("DROP TABLE IF EXISTS contact_t");
			System.out.println("Successfully dropped the contact table.");
			stmtCreateT.executeUpdate("DROP TABLE IF EXISTS user_t");
	*/		System.out.println("Successfully dropped the user table.");
			stmtCreateT.executeUpdate(createUser_t);
			System.out.println("Successfully created the user table.");
			stmtCreateT.executeUpdate(createContact_t);
			System.out.println("Successfully created the contact table.");
			stmtCreateT.executeUpdate(createTeam_t);
			System.out.println("Successfully created the team table.");
			stmtCreateT.executeUpdate(createSubTeam_t);
			System.out.println("Successfully created the subTeam table.");
			stmtCreateT.executeUpdate(createPosition_t);
			System.out.println("Successfully created the position table.");
		} catch(SQLException e1) {
			//e1.printStackTrace();
			show_error("Error creating database tables", e1);
		}
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

	
	public static boolean addUser(String fName, String lName, String uName,
			String password, String email) {
		boolean success;
		String insertUser = "INSERT INTO user_t(userName, pWord, email, fName, lName) VALUES ('"
				+ uName + "', '" + password + "', '" + email + "', '" + fName + "', '" + lName + "')";
		Connection dbConn = connect_db();
		try {
			Statement insert = dbConn.createStatement();
			insert.executeUpdate(insertUser);
			System.out.println("Successfully added the user: " + uName);
			success = true;
		} catch (SQLException e) {
			show_error("Create statement Error", "Please choose a different username.");
			success = false;
		}
		return success;
	}

	public static boolean addContact(String fName, String lName, String email, String team, String subTeam1, String position1, String position2, String subTeam2, String position3, String position4, String currentUser) {
		boolean worked = true;
		System.out.println("Current User from DatabaseHandler Classs: " + currentUser);
		String insertContact = "INSERT INTO contact_t(fName, lName, owner, email) VALUES ('"
				+ fName + "', '" + lName + "', '" + currentUser + "', '" + email + "')";
		Connection dbConn = connect_db();
		try{
			Statement insert = dbConn.createStatement();
			insert.executeUpdate(insertContact, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = insert.getGeneratedKeys(); 
			int contactId = 0;
			int teamId = 0;
			int subTeamId1 = 0;
			int subTeamId2 = 0;
			if(rs.next()){
				contactId = rs.getInt(1);
			}
		    System.out.println("FIRST: " + contactId);
		    
		    String insertTeam = "INSERT INTO team_t(team, contactId) VALUES ('"
		    		+ team + "', '" + contactId + "')";
		    insert.executeUpdate(insertTeam, Statement.RETURN_GENERATED_KEYS);
		    rs = insert.getGeneratedKeys();
			if(rs.next()){
				teamId = rs.getInt(1);
			}
			System.out.println("TEAMID: " + teamId);
		    
		    if(!subTeam1.equals("")){
			    String insertSubTeam1 = "INSERT INTO subTeam_t(subTeam, teamId) VALUES ('"
			    		+ subTeam1 + "', '" + teamId + "')";
			    insert.executeUpdate(insertSubTeam1, Statement.RETURN_GENERATED_KEYS);
			    rs = insert.getGeneratedKeys();
				if(rs.next()){
					subTeamId1 = rs.getInt(1);
				}
				System.out.println("SUBTEAMID1: " + subTeamId1);
			    
			    if(!position1.equals("")){
				    String insertPosition1 = "INSERT INTO position_t(position, subTeamId) VALUES ('"
				    		+ position1 + "', '" + subTeamId1 + "')";
				    insert.executeUpdate(insertPosition1);
				    
				    if(!position2.equals("")){
					    String insertPosition2 = "INSERT INTO position_t(position, subTeamId) VALUES ('"
					    		+ position2 + "', '" + subTeamId1 + "')";
					    insert.executeUpdate(insertPosition2);
				    }
			    }
			    if(!subTeam2.equals("")){
				    String insertSubTeam2 = "INSERT INTO subTeam_t(subTeam, teamId) VALUES ('"
				    		+ subTeam2 + "', '" + teamId + "')";
				    insert.executeUpdate(insertSubTeam2, Statement.RETURN_GENERATED_KEYS);
				    rs = insert.getGeneratedKeys();
					if(rs.next()){
						subTeamId2 = rs.getInt(1);
					}
					System.out.println("SUBTEAMID1: " + subTeamId2);
				    
				    if(!position3.equals("")){
					    String insertPosition3 = "INSERT INTO position_t(position, subTeamId) VALUES ('"
					    		+ position3 + "', '" + subTeamId2 + "')";
					    insert.executeUpdate(insertPosition3);
					    
					    if(!position4.equals("")){
						    String insertPosition4 = "INSERT INTO position_t(position, subTeamId) VALUES ('"
						    		+ position4 + "', '" + subTeamId2 + "')";
						    insert.executeUpdate(insertPosition4);
					    }
				    }
			    }
		    }		    

		} catch (SQLException e) {
			worked = false;
			show_error("Insert Error", e);
			e.printStackTrace();
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
		}
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
