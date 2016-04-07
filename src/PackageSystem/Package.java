package PackageSystem;

public class Package {
	public Resident packageOwner;
	public String description;
	public DatabaseSupport database;
	public String location;
	public Employee logger;
	public String note;
	public int packageID;
	public Package p;
	public Resident r;
	public boolean deliveredStatus;
	
	public Package(Resident recipient, String info) {
		packageOwner = recipient;
		description = info;
		database = DatabaseSupport.getSingleton();
		deliveredStatus = false;
	}
	
	
	
	public boolean addPackage(int name, String info) {
		
		//get resident and then create package
		r = database.getResident(name);
		p = new Package(r, info);
		
		//add package to resident's list of packages
		r.packages.add(p);
		
		//put in database
		boolean status = database.putPackage(p);
		boolean status2 = database.putListOfPackages(r.packages);//UGH: we don't need to do this.
		if (status && status2) return true;
		
		return false;
	}
	
	public boolean addDescription(int packageID, String description) {
		
		//get package
		p = database.getPackage(packageID);//do we really need to do this? we already got the package from the database
		p.description = description;
		
		//put description in database
		boolean status = database.putDescription(p);
		if (status) 
			return true;
		
		return false;
	}
	
	public boolean addNote(int packageID, String note) {
		
		//get package
		p = database.getPackage(packageID);
		p.note = note;
		
		//put description in database
		boolean status = database.putNote(p);
		if (status) return true;
		
		return false;
	}
	
	public boolean addLocation(int packageID, String location) {
		//get package
		p = database.getPackage(packageID);
		p.location = location;
		
		//put description in database
		boolean status = database.putLocation(p);
		if (status) return true;
				
		
		return false;
	}
	
	public boolean deliverPackage(int packageID) {
		
		//get package from database
		p = database.getPackage(packageID);
		deliveredStatus = true;
		
		//put back in database
		boolean status = database.deliverPackage(p);
		if (status) return true;
		
		return false;
	}
	
}