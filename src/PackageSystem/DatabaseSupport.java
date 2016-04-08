package PackageSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSupport {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://dbosch-pi-2.student.iastate.edu/coms362";

	// Database credentials
	static final String USER = "smile";
	static final String PASS = "jCRMXKQC9hSV68ZL";

	private static DatabaseSupport singleton = null;

	public static DatabaseSupport getSingleton() {
		if (singleton == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
			singleton = new DatabaseSupport();
		}
		return singleton;
	}
	
	private DatabaseSupport() {
	}

//	private void init() {
//		try {
//			// STEP 2: Register JDBC driver
//
//			// STEP 3: Open a connection
//			System.out.println("Connecting to database...");
//
//			// STEP 4: Execute a query
//			System.out.println("Creating statement...");
//			stmt = conn.createStatement();
//			String sql;
//			sql = "SELECT id, first, last, age FROM Employees";
//			ResultSet rs = stmt.executeQuery(sql);
//
//			// STEP 5: Extract data from result set
//			while (rs.next()) {
//				// Retrieve by column name
//				int id = rs.getInt("id");
//				int age = rs.getInt("age");
//				String first = rs.getString("first");
//				String last = rs.getString("last");
//
//				// Display values
//				System.out.print("ID: " + id);
//				System.out.print(", Age: " + age);
//				System.out.print(", First: " + first);
//				System.out.println(", Last: " + last);
//			}
//			// STEP 6: Clean-up environment
//			rs.close();
//			stmt.close();
//			conn.close();
//		} catch (SQLException se) {
//			// Handle errors for JDBC
//			se.printStackTrace();
//		} catch (Exception e) {
//			// Handle errors for Class.forName
//			e.printStackTrace();
//		} finally {
//			// finally block used to close resources
//			try {
//				if (stmt != null)
//					stmt.close();
//			} catch (SQLException se2) {
//			} // nothing we can do
//			try {
//				if (conn != null)
//					conn.close();
//			} catch (SQLException se) {
//				se.printStackTrace();
//			} // end finally try
//		} // end try
//		System.out.println("Goodbye!");
//	}

	private ResultSet query(String sql) throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS).createStatement().executeQuery(sql);
	}

	public boolean putPackage(Package p) {
		// put in database
		try {
			//query database
			ResultSet rs = query("INSERT INTO `coms362`.`packages` (`pid`, `resident`, `notes`, `timestamp`) VALUES (NULL, '"
					+ p.packageID + "', '" + p.note + "', CURRENT_TIMESTAMP);");

			
			//close up
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean putResident(Resident r) {
		// put in database
		try {
			//query database
			ResultSet rs = query("INSERT INTO `coms362`.`residents` (`uid`, `name`, `address`, `phone`, `email`) VALUES (NULL, '"
					+ r.name + "', '" + r.address + "', '" + r.phone + "', '" + r.email + "');");
			
			//close up
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
		return true;
	}

	public Resident getResident(int uid) {
		Resident r = null; // resident retrieved from database
		try {
			//query database
			ResultSet rs = query("SELECT * FROM `coms362`.`residents` WHERE `uid` = " + uid + ";");
			
			//get fields
			String name = rs.getString("name");
			String address = rs.getString("address");
			String phone = rs.getString("phone");
			String email = rs.getString("email");
			
			//create object
			r = new Resident(name, address, phone, email);
			
			//close up
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
		return r;
	}

	public boolean deleteEmployee(Employee e) {
		// remove from database

		return true;
	}

	public boolean putEmployee(Employee e) {
		// put in database
		try {
			//query database
			ResultSet rs = query("INSERT INTO `coms362`.`employees` (`eid`, `name`) VALUES (NULL, '" + e.name + "');");
			
			//close up
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
		return true;
	}

	public Employee getEmployee(int eid) {
		// retrieve from database
		Employee e = null; // resident retrieved from database
		try {
			ResultSet rs = query("SELECT * FROM `coms362`.`employees` WHERE `eid` = " + eid + ";");
			String name = rs.getString("name");
			e = new Employee(name);
			e.employeeID = eid;
			
			//close up
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
		return e;
	}

	public boolean deleteResident(Resident r) {
		// remove from database

		return true;
	}

	public Package getPackage(int packageID) {
		// retrieve from database
		Package p = null; // resident retrieved from database
		try {
			//query database
			ResultSet rs = query("SELECT * FROM `coms362`.`packages` WHERE `pid` = " + packageID);

			p = new Package(getResident(rs.getInt("resident")), rs.getString("info"));
			p.packageID = packageID;

			p.logger = getEmployee(rs.getInt("logger"));
			p.location = rs.getString("location");
			p.note = rs.getString("notes");
			p.deliveredStatus = rs.getBoolean("status");
			
			//close up
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}

		return p;
	}
	
	public List<Package> getListOfPackagesForResident(Resident r) {
		return getListOfPackagesForResident(r.rid);
	}
	
	public List<Package> getListOfPackagesForResident(int residentID) {
		ArrayList<Package> list = new ArrayList<>();
		try{
			ResultSet rs = query("SELECT * FROM `coms362`.`packages` WHERE `resident` = "+residentID+";");
			
			//iterate through all results
			while(rs.next()){
				//create package object
				Package p = new Package(getResident(rs.getInt("resident")), rs.getString("info"));
				p.packageID = rs.getInt("pid");

				//get and set values
				p.logger = getEmployee(rs.getInt("loggerEID"));
				p.location = rs.getString("location");
				p.note = rs.getString("notes");
				p.deliveredStatus = rs.getBoolean("status");
				
				//add to list
				list.add(p);
			}
			
			//close up
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public boolean putDescription(Package p) {
		// put updated in database
		try {
			//query database
			ResultSet rs = query("UPDATE `coms362`.`packages` SET `info` = '"+p.description+"' WHERE `packages`.`pid` = " + p.packageID + ";");
			
			//close up
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean putNote(Package p) {
		// put updated in database
		try {
			//query database
			ResultSet rs = query("UPDATE `coms362`.`packages` SET `note` = '"+p.note+"' WHERE `packages`.`pid` = " + p.packageID + ";");
			
			//close up
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deliverPackage(Package p) {
		// put updated boolean into database
		try {
			//query database
			ResultSet rs = query("UPDATE `coms362`.`packages` SET `status` = '1' WHERE `packages`.`pid` = " + p.packageID + ";");
			
			//close up
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean putLocation(Package p) {
		// put updated boolean into database
		try {
			//query database
			ResultSet rs = query("UPDATE `coms362`.`packages` SET `location` = '"+p.location+"' WHERE `packages`.`pid` = " + p.packageID + ";");
			
			//close up
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<Resident> searchResident(String searchParam){
		ArrayList<Resident> list = new ArrayList<>();
		try {
			//query database
			ResultSet rs = query("SELECT * FROM `coms362`.`residents` WHERE `name` LIKE '%"+searchParam+"%' OR `address` LIKE '%"+searchParam+"%' OR `phone` LIKE '%"+searchParam+"%' OR `email` LIKE '%"+searchParam+"%'");
			
			while(rs.next()){
				Resident r;
				//get params
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				
				//create object
				r = new Resident(name, address, phone, email);
				list.add(r);
			}
			
			//close up
			rs.getStatement().getConnection().close();
			rs.getStatement().close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
}
