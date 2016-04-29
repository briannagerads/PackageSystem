package PackageSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author davidboschwitz
 * @author briannagerads
 *
 */
public class DatabaseSupport {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://dbosch-pi-2.student.iastate.edu/coms362";

	// Database credentials
	static final String USER = "smile";
	static final String PASS = "jCRMXKQC9hSV68ZL";
	private static DatabaseSupport singleton = null;
	private boolean closed = false;
	private Connection connection;

	public static DatabaseSupport getSingleton() {
		if (singleton == null || singleton.closed) {
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
		open();
	}

	private void open() {
		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			closed = true;
		}
	}

	public void close() throws SQLException {
		closed = true;
		connection.close();
	}
	//
	// private ResultSet query(String sql) throws SQLException {
	// return DriverManager.getConnection(DB_URL, USER,
	// PASS).createStatement().executeQuery(sql);
	// }

	public ResultSet query(String sql) throws SQLException {
		closed = true;
		if(connection.isClosed())
			open();
		return connection.createStatement().executeQuery(sql);
	}

	public int update(String sql) throws SQLException {
		closed = true;
		if(connection.isClosed())
			open();
		return connection.createStatement().executeUpdate(sql);
	}

	private Package packageFromRS(ResultSet rs) throws SQLException {
		Package p = new Package(getResident(rs.getInt("resident")), 
				"");
		
		p.packageID = rs.getInt(1);

		p.logger = getEmployee(rs.getInt("logger"));
		p.location = rs.getString("location");
		p.note = rs.getString("notes");
		p.deliveredStatus = rs.getBoolean("status");
		p.date = rs.getString("timestamp");
		p.logger = getEmployee(rs.getInt("logger"));
		p.company = rs.getString("company");

		return p;
	}

	private Resident residentFromRS(ResultSet rs) throws SQLException {
		Resident r;
		// get params
		String name = rs.getString("name");
		String address = rs.getString("address");
		String phone = rs.getString("phone");
		String email = rs.getString("email");

		// create object
		r = new Resident(name, address, phone, email);
		r.rid = rs.getInt("uid");
		r.username = rs.getString("username");
		return r;
	}

	public boolean putPackage(Package p) {		
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("INSERT INTO `coms362`.`packages` (`pid`, `resident`, `info`, `timestamp`, `logger`, `notes`, `status`, `location`, `company`) VALUES (NULL, '"
					+ p.packageOwner.rid + "', '" 
					+ p.description + "', CURRENT_TIMESTAMP, '"
					+p.logger.employeeID+"', '"
					+p.note+"', 0, '"
					+p.location+"', '"
					+p.company+"');");

			// close up

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return i != -1;
	}

	public boolean putResident(Resident r) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("INSERT INTO `coms362`.`residents` (`uid`, `name`, `address`, `phone`, `email`, `username`) VALUES (NULL, '"
					+ r.name + "', '" + r.address + "', '" + r.phone + "', '" + r.email + "', '"+r.username+"');");

			// close up

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return i != -1;
	}

	public Resident getResident(int uid) {
		Resident r = null; // resident retrieved from database
		try {
			// query database
			ResultSet rs = query("SELECT * FROM `coms362`.`residents` WHERE `uid` = " + uid + ";");

			// get fields
			if (rs.next()) {
				r = residentFromRS(rs);
			}

			// close up
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
		return r;
	}

	public boolean deleteEmployee(Employee e) {
		// remove from database
		int i = -1;
		try {
			i = update("DELETE FROM `coms362`.`employees` WHERE `eid` = " + e.employeeID + ";");
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return i != -1;
	}

	public boolean putEmployee(Employee e) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("INSERT INTO `coms362`.`employees` (`eid`, `name`) VALUES (NULL, '" + e.name + "');");

			// close up

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return i != -1;
	}

	public Employee getEmployee(int eid) {
		// retrieve from database
		Employee e = null; // resident retrieved from database
		try {
			ResultSet rs = query("SELECT * FROM `coms362`.`employees` WHERE `eid` = " + eid + ";");
			if (rs.next()) {
				String name = rs.getString("name");
				e = new Employee(name, eid);
				e.employeeID = eid;
			}
			// close up
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
		return e;
	}

	public boolean deleteResident(Resident r) {
		// remove from database
		int i = -1;
		try {
			i = update("DELETE FROM `coms362`.`residents` WHERE `uid` = " + r.rid + ";");
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return i != -1;
	}

	public Package getPackage(int packageID) {
		// retrieve from database
		Package p = null; // resident retrieved from database
		try {
			// query database
			ResultSet rs = query("SELECT * FROM `coms362`.`packages` WHERE `pid` = " + packageID);
			if (rs.next()) {
				p = packageFromRS(rs);
			}

			// close up
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
		try {
			ResultSet rs = query("SELECT * FROM `coms362`.`packages` WHERE `resident` = " + residentID + ";");

			// iterate through all results
			while (rs.next()) {
				// create package object
				Package p = packageFromRS(rs);

				// add to list
				list.add(p);
			}

			// close up
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public boolean putDescription(Package p) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("UPDATE `coms362`.`packages` SET `info` = '" + p.description + "' WHERE `packages`.`pid` = "
					+ p.packageID + ";");

			// close up

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i != -1;
	}

	public boolean putNote(Package p) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("UPDATE `coms362`.`packages` SET `note` = '" + p.note + "' WHERE `packages`.`pid` = "
					+ p.packageID + ";");

			// close up

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i != -1;
	}

	public boolean deliverPackage(Package p) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("UPDATE `coms362`.`packages` SET `status` = '1' WHERE `packages`.`pid` = " + p.packageID + ";");

			// close up

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i != -1;
	}

	public boolean putLocation(Package p) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("UPDATE `coms362`.`packages` SET `location` = '" + p.location + "' WHERE `packages`.`pid` = "
					+ p.packageID + ";");

			// close up

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i != -1;
	}

	public List<Resident> searchResident(String searchParam) {
		ArrayList<Resident> list = new ArrayList<>();
		try {
			// query database
			ResultSet rs = query("SELECT * FROM `coms362`.`residents` WHERE `name` LIKE '%" + searchParam
					+ "%' OR `address` LIKE '%" + searchParam + "%' OR `phone` LIKE '%" + searchParam
					+ "%' OR `email` LIKE '%" + searchParam + "%'");

			while (rs.next()) {
				Resident r = residentFromRS(rs);
				list.add(r);
			}

			// close up
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * Add for Iteration 2
	 */

	public List<Package> getHistory(Resident r) {
		ArrayList<Package> list = new ArrayList<>();
		try {
			ResultSet rs = query("SELECT * FROM `coms362`.`packages` WHERE `resident` = " + r.rid + ";");

			// iterate through all results
			while (rs.next()) {
				// create package object
				Package p = packageFromRS(rs);

				// add to list
				list.add(p);
			}

			// close up
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public boolean putCompany(Package p) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("UPDATE `coms362`.`packages` SET `company` = '" + p.company + "' WHERE `packages`.`pid` = "
					+ p.packageID + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i != -1;
	}

	public boolean putDate(Package p) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("UPDATE `coms362`.`packages` SET `timestamp` = '" + p.date + "' WHERE `packages`.`pid` = "
					+ p.packageID + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i != -1;
	}

	public boolean putAddress(Resident r) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("UPDATE `coms362`.`residents` SET `address` = '" + r.address
					+ "' WHERE `residents`.`uid` = " + r.rid + ";");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i != -1;
	}

	public boolean putPhone(Resident r) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("UPDATE `coms362`.`residents` SET `phone` = '" + r.phone
					+ "' WHERE `residents`.`uid` = " + r.rid + ";");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i != -1;
	}

	public boolean putEmail(Resident r) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("UPDATE `coms362`.`residents` SET `email` = '" + r.email
					+ "' WHERE `residents`.`uid` = " + r.rid + ";");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i != -1;
	}

	public boolean putUsername(Resident r) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("UPDATE `coms362`.`residents` SET `username` = '" + r.username
					+ "' WHERE `residents`.`uid` = " + r.rid + ";");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i != -1;
	}

	/**
	 * Add for Iteration 3
	 */

	public List<Package> getAllPackages() {
		ArrayList<Package> list = new ArrayList<>();
		try {
			ResultSet rs = query("SELECT * FROM `coms362`.`packages`;");
			// this is an intense query (will return entire table), this is not
			// recommended, but fits the use case iterate through all results
			while (rs.next()) {
				// create package object
				Package p = packageFromRS(rs);
				// add to list
				list.add(p);
			}

			// close up
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public boolean putLogger(Package p) {
		// put update in database
		int i = -1;
		try {
			// query database
			i = update("UPDATE `coms362`.`packages` SET `logger` = '" + p.logger + "' WHERE `packages`.`pid` = "
					+ p.packageID + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i != -1;
	}

	public String getPhone(Resident r) {
		// get phone from database using existing methods
		Resident getR = getResident(r.rid);

		// return only phone
		return getR.phone;
	}

	public String getEmail(Resident r) {
		// get email from database using existing methods
		Resident getR = getResident(r.rid);

		// return only email
		return getR.email;
	}

}
