package PackageSystem;

public class Employee {
	public String name;
	public int employeeID;
	public DatabaseSupport database;
	
	public Employee(String name) {
		this.name = name;
	}
	
	public boolean addEmployee(String name) {
		
		//create employee
		Employee e = new Employee(name);
		
		//put in database
		boolean status = database.putEmployee(e);
		if (status) return true;
		
		return false;
	}
	
	public boolean removeEmployee(String name) {
		
		//get employee from database
		Employee e = database.getEmployee(name);
		
		//remove employee from database
		boolean status = database.deleteEmployee(e);
		if (status) return true;
		
		return false;
	}
}
