package PackageSystem;

public class packag {
	public resident packageOwner;
	public String description;
	public databaseSupport database;
	public String location;
	public Employee logger;
	public String note;
	public int packageID;
	public boolean deliveredStatus;
	
	public packag(String recipient, String info) {
		resident owner = new resident(recipient);
		packageOwner = owner;
		description = info;
		database = new databaseSupport();
		deliveredStatus = false;
	}
	
	public boolean addPackage(String rid, String info) {
		
		//create package
		packag p = new packag(rid, info);
		
		//put in database
		boolean status = database.putPackage(p);
		if (status) return true;
		
		return false;
	}
	
	public boolean addDescription(int packageID, String description) {
		
		//get package
		packag p = database.getPackage(packageID);
		p.description = description;
		
		//put description in database
		boolean status = database.putDescription(p);
		if (status) return true;
		
		return false;
	}
	
	public boolean addNote(int packageID, String note) {
		
		//get package
		packag p = database.getPackage(packageID);
		p.note = note;
		
		//put description in database
		boolean status = database.putNote(p);
		if (status) return true;
		
		return false;
	}
	
	public boolean addLocation(int packageID, String location) {
		//get package
		packag p = database.getPackage(packageID);
		p.location = location;
		
		//put description in database
		boolean status = database.putDescription(p);
		if (status) return true;
				
		
		return false;
	}
	
	public boolean deliverPackage(int packageID) {
		
		//get package from database
		packag p = database.getPackage(packageID);
		deliveredStatus = true;
		
		//put back in database
		boolean status = database.putPackage(p);
		if (status) return true;
		
		return false;
	}
	
}
