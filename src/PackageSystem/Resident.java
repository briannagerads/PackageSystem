package PackageSystem;

import java.util.List;

public class Resident {
	public String name;
	public String address;
	public String phone;
	public String email;
	public Resident r;
	public int rid;
	public DatabaseSupport database;
	public List<Package> packages;

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

}
