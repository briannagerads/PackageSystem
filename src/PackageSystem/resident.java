package PackageSystem;

public class resident {
	public String name;
	public String address;
	public String phone;
	public String email;
	public databaseSupport database;

	public resident(String recipient) {
		name = recipient;
		database = new databaseSupport();
	}
	
	public resident(String name, String address, String phone, String email) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		database = new databaseSupport();
	}
	
	public boolean addResident(String name, String address, String phone, String email) {
		
		//create resident
		resident r = new resident(name, address, phone, email);
		
		//put in database
		boolean status = database.putResident(r);
		if (status) return true;
		
		return false;
	}

}
