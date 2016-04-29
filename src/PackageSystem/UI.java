package PackageSystem;

import java.io.BufferedReader;
import java.util.List;

/**
 * 
 * @author davidboschwitz
 * @author edwin
 *
 */
public class UI {

	public static Employee me = null;

	public static void main(String... strings) {
		System.out.println("Welcome to the PackageTrackingSystemController!");
		System.out.println("You must login with your employee ID to continue");
		System.out.println("---");
		System.out.println();
		// init reader for system.in (user input)
		BufferedReader in = new BufferedReader(new java.io.InputStreamReader(System.in));
		try {
			String line = null;
			while ((line = in.readLine()) != null) {
				try {
					String cmd;
					if (line.indexOf(' ') == -1) {
						cmd = line;
					} else {
						cmd = line.substring(0, line.indexOf(' '));
					}
					String msg = "";
					if (line.length() > line.indexOf(' ') + 1)
						msg = line.substring(line.indexOf(' ') + 1);
					Resident r;
					Package p;
					List<Package> pList;
					List<Resident> rList;
					if (me == null && !cmd.equals("login")) {
						System.out.println("You must be logged in to do that.");
						continue;
					}
					switch (cmd) {
					case "login":
						if ((me = DatabaseSupport.getSingleton().getEmployee(Integer.parseInt(msg))) != null) {
							System.out.println("Hello " + me.name);
						} else {
							System.out.println("login failed");
						}
						break;

					case "who-am-i":
						System.out.println("You are " + me.name + ", IDNum: " + me.employeeID);
						break;

					case "logout":
					case "exit":
					case "goodbye":
						// just exit the program
						System.out.println("Goodbye");
						return;
						
					case "help":
						System.out.println("PackageTrackingSystemController List of Commands");
						System.out.println();
						System.out.println("login <EmployeeID>                           //switch user");
						System.out.println("logout/exit/goodbye                          //end session");
						System.out.println("add-package <ResidentID>;<note>              //add new package");
						System.out.println("add-description <PackageID>;<description>    //update package description");
						System.out.println("add-note <PackageID>;<note>                  //update package note");
						System.out.println("add-location <PackageID>;<location>          //update package location");
						System.out.println("reroute-location <PackageID>;<newLocation>   //update package location");
						System.out.println("add-company <PackageID>;<company>            //update package company");
						System.out.println("list-all-packages                           //lists all packages in database");
						System.out.println("get-resident-id <ResidentIDNum>	             //get resident by ID");
						break;

					/*
					 * Employee
					 */
					case "add-employee":
						if (Employee.getSingleton().addEmployee(msg.substring(0, msg.indexOf(';')),
								Integer.parseInt(msg.substring(msg.indexOf(';') + 1))))
							System.out.println("The new employee was added successfully.");
						else
							System.out.println("Failed to add the new employee.");
						break;

					case "remove-employee":
						if (Employee.getSingleton().removeEmployee(Integer.parseInt(msg)))
							System.out.println("The employee was removed successfully.");
						else
							System.out.println("Failed to remove the employee.");
						break;

					/*
					 * Package
					 */
					case "add-package":
						if (Package.getSingleton().addPackage(Integer.parseInt(msg.substring(0, msg.indexOf(';'))),
								msg.substring(msg.indexOf(';') + 1))) {
							System.out.println("Added new package success");
						} else {
							System.out.println("Added new package failed");
						}
						break;

					case "add-description":
						if (Package.getSingleton().addDescription(Integer.parseInt(msg.substring(0, msg.indexOf(';'))),
								msg.substring(msg.indexOf(';') + 1))) {
							System.out.println("Added description success");
						} else {
							System.out.println("Added description failed");
						}
						break;

					case "add-note":
						if (Package.getSingleton().addNote(Integer.parseInt(msg.substring(0, msg.indexOf(';'))),
								msg.substring(msg.indexOf(';') + 1))) {
							System.out.println("Add note success");
						} else {
							System.out.println("Add note failed");
						}
						break;

					case "add-location":
						if (Package.getSingleton().addLocation(Integer.parseInt(msg.substring(0, msg.indexOf(';'))),
								msg.substring(msg.indexOf(';') + 1))) {
							System.out.println("Add location success");
						} else {
							System.out.println("Add location failed");
						}
						break;

					case "reroute-location":
						if (Package.getSingleton().reroutePackage(Integer.parseInt(msg.substring(0, msg.indexOf(';'))),
								msg.substring(msg.indexOf(';') + 1))) {
							System.out.println("Re-route success");
						} else {
							System.out.println("Re-route failed");
						}
						break;

					case "add-company":
						if (Package.getSingleton().addCompany(Integer.parseInt(msg.substring(0, msg.indexOf(';'))),
								msg.substring(msg.indexOf(';') + 1))) {
							System.out.println("Add company success");
						} else {
							System.out.println("Add company failed");
						}
						break;

					case "deliver-package":
						if (Package.getSingleton().deliverPackage(Integer.parseInt(msg))) {
							System.out.println("Deliver package success");
						} else {
							System.out.println("Deliver package failed");
						}
						break;

					case "list-all-packages":
						pList = DatabaseSupport.getSingleton().getAllPackages();
						for (Package pl : pList) {
							System.out.println(pl.packageID + " " + pl.packageOwner.name + " " + pl.description);
						}
						System.out.println();
						break;

					case "get-resident-id":
						r = DatabaseSupport.getSingleton().getResident(Integer.parseInt(msg));
						System.out.println("[" + r.rid + "] " + r.name + "\t" + r.address + "\t" + r.email + "\t"
								+ r.phone + "\t" + r.username);
						break;

					/*
					 * Resident
					 */
					case "add-resident":
						String name = msg.substring(0, msg.indexOf(';'));

						msg = msg.substring(msg.indexOf(';') + 1);
						String address = msg.substring(0, msg.indexOf(';'));

						msg = msg.substring(msg.indexOf(';') + 1);
						String phone = msg.substring(0, msg.indexOf(';'));

						msg = msg.substring(msg.indexOf(';') + 1);
						String email = msg;

						if (Resident.getSingleton().addResident(name, address, phone, email)) {
							System.out.println("Added new resident success");
						} else {
							System.out.println("Added new resident failed");
						}
						break;

					case "remove-resident":
						if (Resident.getSingleton().removeResident(Integer.parseInt(msg)))
							System.out.println("The resident was removed successfully.");
						else
							System.out.println("Failed to remove the resident.");

						break;

					case "search-resident":
						rList = Resident.getSingleton().searchRecipient(msg.substring(0));

						for (Resident rl : rList)
							System.out.println(rl.rid + "; " + rl.name + "; " + rl.address + "; " + rl.phone + "; "
									+ rl.email + "; " + rl.username);

						break;

					case "add-address":
						int rid = Integer.parseInt(msg.substring(0, msg.indexOf(';')));

						msg = msg.substring(msg.indexOf(';') + 1);
						address = msg.substring(0);

						if (Resident.getSingleton().addAddress(rid, address))
							System.out.println("The address was added successfully.");
						else
							System.out.println("Failed to add the address.");

						break;

					case "add-phone":
						rid = Integer.parseInt(msg.substring(0, msg.indexOf(';')));

						msg = msg.substring(msg.indexOf(';') + 1);
						phone = msg.substring(0);

						if (Resident.getSingleton().addPhone(rid, phone))
							System.out.println("The phone was added successfully.");
						else
							System.out.println("Failed to add the phone.");

						break;

					case "add-email":
						rid = Integer.parseInt(msg.substring(0, msg.indexOf(';')));

						msg = msg.substring(msg.indexOf(';') + 1);
						email = msg.substring(0);

						if (Resident.getSingleton().addEmail(rid, email))
							System.out.println("The email was added successfully.");
						else
							System.out.println("Failed to add the email.");

						break;

					case "add-username":
						rid = Integer.parseInt(msg.substring(0, msg.indexOf(';')));

						msg = msg.substring(msg.indexOf(';') + 1);
						String username = msg.substring(0);

						if (Resident.getSingleton().addUsername(rid, username))
							System.out.println("The username was added successfully.");
						else
							System.out.println("Failed to add the username.");

						break;

					case "list-history":
						pList = Resident.getSingleton().retrieveHistory(Integer.parseInt(msg));

						for (Package pl : pList)
							System.out.println(pl.packageID + "; " + pl.date + "; " + pl.location + "; "
									+ pl.description + "; " + pl.company + "; " + pl.note);

						break;
					default:
						System.out.println("Invalid Command: " + cmd);
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println();
			}
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
			// hopefully shouldn't happen
		}
	}
}
