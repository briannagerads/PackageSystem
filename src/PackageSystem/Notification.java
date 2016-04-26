package PackageSystem;

/**
 * 
 * @author briannagerads
 *
 */
public class Notification {
	
	public DatabaseSupport database;
	public Package p;
	public Resident r;
	
	
	public boolean notificationRecieveText(int packageID) {
		//get package
		p = database.getPackage(packageID);
		
		//get Resident?
		//r = database.getResident(packageOwner);
		
		//get phone number
		r.phone = database.getPhone(r);
		
		//send text
		
		
		return false;
	}
	
	public boolean notificationRecieveEmail(int packageID) {
		//get package
		p = database.getPackage(packageID);
		
		//get Resident?
		//r = database.getResident(packageOwner);
		
		//get phone number
		r.phone = database.getEmail(r);
		
		//send email
		
		
		return false;
	}
	
	public boolean notificationDeliverText(int packageID) {
		//get package
		p = database.getPackage(packageID);
		
		//get Resident?
		//r = database.getResident(packageOwner);
		
		//get phone number
		r.phone = database.getPhone(r);
		
		//send text
		
		
		return false;
	}
	
	public boolean notificationDeliverEmail(int packageID) {
		//get package
		p = database.getPackage(packageID);
		
		//get Resident?
		//r = database.getResident(packageOwner);
		
		//get phone number
		r.phone = database.getEmail(r);
		
		//send email
		
		
		return false;
	}
	
}
