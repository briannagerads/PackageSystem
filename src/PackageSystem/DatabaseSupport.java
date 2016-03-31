package PackageSystem;

public class DatabaseSupport {
	
	public boolean putPackage(Package p) {
		//put in database
		return true;
	}
	
	public boolean putResident(Resident r) {
		//put in database
		return true;
	}
	
	public Resident getResident(String name) {
		//retrieve from database
		Resident r = null; //resident retrieved from database
		
		return r;
	}
	
	public boolean deleteResident(Resident r) {
		//remove from database
		
		return true;
	}
	
	public Package getPackage(int packageID) {
		//retrieve from database
		Package p = null; //resident retrieved from database
		
		return p;
	}
	
	public boolean putDescription(Package p) {
		//put updated in database
		return true;
	}
	
	public boolean putNote(Package p) {
		//put updated in database
		return true;
	}
	
	public boolean deliverPackage(Package p) {
		//put updated boolean into database
		return true;
	}
	
	public boolean putLocation(Package p) {
		//put updated boolean into database
		return true;
	}
}
