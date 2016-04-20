package PackageSystem;

public class Employee {
	public String name;
	public int employeeID;
	public DatabaseSupport database = DatabaseSupport.getSingleton();
	
	public Employee(String name) {
		this.name = name;
	}
	
	public boolean addEmployee(String name) {
		
		//check that employee doesn't already exist
		//boolean check = database.checkEmployeeExistence(name);
		//if (check) return false;
		
		//create employee
		Employee e = new Employee(name);
		
		//put in database
		boolean status = database.putEmployee(e);
		if (status) return true;
		
		return false;
	}
	
	public boolean removeEmployee(int eid) {
		
		//get employee from database
		Employee e = database.getEmployee(eid);
		
		//remove employee from database
		boolean status = database.deleteEmployee(e);
		if (status) return true;
		
		return false;
	}
}
