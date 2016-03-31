package PackageSystem;

public class Resident {
	public String name;
	public String address;
	public String phone;
	public String email;
	public DatabaseSupport database;

	public Resident(String recipient) {
		name = recipient;
		database = new DatabaseSupport();
	}
	
	public Resident(String name, String address, String phone, String email) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		database = new DatabaseSupport();
	}
	
	public boolean addResident(String name, String address, String phone, String email) {
		
		//create resident
		Resident r = new Resident(name, address, phone, email);
		
		//put in database
		boolean status = database.putResident(r);
		if (status) return true;
		
		return false;
	}
	
	public boolean removeResident(String searchParam) {
		//identify type of search param to identify how to retrieve from database
		
		//get resident from database
		Resident r = database.getResident(searchParam);
		
		//deliver any remaining undelivered packages
		
		//delete resident from database
		boolean status = database.deleteResident(r);
		if (status) return true;
		
		return false;
	}
	
	public Resident searchRecipient(String searchParam) {
		//identify type of search param to identify how to retrieve from database
		
		//get resident from database
		Resident r = database.getResident(searchParam);
		
		return r;
	}

}
