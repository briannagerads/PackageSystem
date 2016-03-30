package PackageSystem;

public class databaseSupport {
	
	public boolean putPackage(packag p) {
		//put in database
		return true;
	}
	
	public boolean putResident(resident r) {
		//put in database
		return true;
	}
	
	public resident getResident(String name) {
		//retrieve from database
		resident r = null; //resident retrieved from database
		
		return r;
	}
	
	public boolean deleteResident(resident r) {
		//remove from database
		
		return true;
	}
	
	public packag getPackage(int packageID) {
		//retrieve from database
		packag p = null; //resident retrieved from database
		
		return p;
	}
	
	public boolean putDescription(packag p) {
		//put updated in database
		return true;
	}
	
	public boolean putNote(packag p) {
		//put updated in database
		return true;
	}
}
