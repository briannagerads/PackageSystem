package PackageSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author briannagerads
 *
 */
public class Resident {
	public String name;
	public String address;
	public String phone;
	public String email;
	public String username;
	public Resident r;
	public Package p;
	public int rid;
	public DatabaseSupport database;
	public List<Package> packages = new ArrayList<>();

	public Resident(String recipient) {
		name = recipient;
		database = DatabaseSupport.getSingleton();
	}
	
	public Resident(String name, String address, String phone, String email) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		database = DatabaseSupport.getSingleton();
	}

	
	private static Resident singleton = null;
	
	public static Resident getSingleton(){
		if(singleton == null){
			singleton = new Resident(null);
		}
		return singleton;
	}
	public boolean addResident(String name, String address, String phone, String email) {
		
		//create resident
		r = new Resident(name, address, phone, email);
		
		//put in database
		boolean status = database.putResident(r);
		if (status) return true;
		
		return false;
	}
	
	public boolean removeResident(int searchParam) {
		//identify type of search param to identify how to retrieve from database
		
		//get resident from database
		r = database.getResident(searchParam);
		packages = database.getListOfPackagesForResident(r);
		
		Package pack;
		//deliver any remaining undelivered packages
		while (!packages.isEmpty()) {
			pack = packages.get(0);
			pack.deliveredStatus = true;
			boolean test = database.deliverPackage(pack);
			if (test) 
				continue;
			else
				return false;
			//packages.remove(0);
		}
		
		//delete resident from database
		boolean status = database.deleteResident(r);
		if (status) return true;
		
		return false;
	}
	
	public List<Resident> searchRecipient(String searchParam) {
		//identify type of search param to identify how to retrieve from database
		
		//get residents from database
		return database.searchResident(searchParam);
	}
	
	/**
	 * Iteration 2
	 */
	public boolean addAddress(int rid, String address) {
		//get package
		r = database.getResident(rid);
		this.address = address;
		
		//put description in database
		if (database.putAddress(r)) return true;
				
		
		return false;
	}
	
	public boolean addPhone(int rid, String phone) {
		//get package
		r = database.getResident(rid);
		this.phone = phone;
		
		//put description in database
		if (database.putPhone(r)) return true;
				
		
		return false;
	}
	
	public boolean addEmail(int rid, String email) {
		//get package
		r = database.getResident(rid);
		this.email = email;
		
		//put description in database
		if (database.putEmail(r)) return true;
				
		
		return false;
	}
	
	public boolean addUsername(int rid, String username) {
		//get package
		r = database.getResident(rid);
		this.username = username;
		
		//put description in database
		if (database.putUsername(r)) return true;
				
		
		return false;
	}
	
	public List<Package> retrieveHistory(int rid) {
		//get package
		r = database.getResident(rid);
		
		//retrieve history from database
		List<Package> history = database.getHistory(r);			
		
		return history;
	}

}
