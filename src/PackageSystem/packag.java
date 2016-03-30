package PackageSystem;

public class packag {
	public resident packageOwner;
	public String description;
	public databaseSupport database;
	
	public packag(String recipient, String info) {
		resident owner = new resident(recipient);
		packageOwner = owner;
		description = info;
		database = new databaseSupport();
	}
	
	public boolean addPackage(String rid, String info) {
		
		//create package
		packag p = new packag(rid, info);
		
		//put in database
		boolean status = database.putPackage(p);
		if (status) return true;
		
		return false;
	}

}
