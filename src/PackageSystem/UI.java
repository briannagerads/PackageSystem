package PackageSystem;

import java.io.BufferedReader;
import java.util.List;

/**
 * 
 * @author davidboschwitz
 *
 */
public class UI {

	public static void main(String... strings) {
		// init reader for system.in (user input)
		BufferedReader in = new BufferedReader(new java.io.InputStreamReader(System.in));
		try {
			String line = null;
			while ((line = in.readLine()) != null) {
				try{
				String cmd = line.substring(0, line.indexOf(' '));
				String msg = "";
				if (line.length() > line.indexOf(' ') + 1)
					;
				msg = line.substring(line.indexOf(' ') + 1);
				Resident r;
				Package p;
				Employee me = null;
				List<Package> pList;
				List<Resident> rList;
				if(me == null && !cmd.equals("login")){
					System.out.println("You must be logged in to do that.");
				}
				switch (cmd) {
				case "login":
					if((me = DatabaseSupport.getSingleton().getEmployee(Integer.parseInt(msg))) != null){
						System.out.println("Hello "+me.name);
					}else{
						System.out.println("login failed");
					}
					break;
				case "logout":
					//just exit the program
					return;
					
				case "add-package":
					if ((new Package(null, null)).addPackage(Integer.parseInt(msg.substring(0, msg.indexOf(';'))),
							msg.substring(msg.indexOf(';') + 1))) {
						System.out.println("Added new package success");
					} else {
						System.out.println("Added new package failed");
					}
					break;
				case "deliver-package":
					if ((new Package(null, null)).deliverPackage(Integer.parseInt(msg))) {
						System.out.println("Deliver package success");
					} else {
						System.out.println("Deliver package failed");
					}
					break;
					
				case "list-all-packages":
					pList = DatabaseSupport.getSingleton().getAllPackages();
					for(Package pl : pList){
						System.out.println(pl.packageID+"; "+pl.r.name+"; "+pl.description);
					}
					System.out.println();
					break;
				case "add-resident":
					String name = msg.substring(0, msg.indexOf(';'));

					msg = msg.substring(msg.indexOf(';') + 1);
					String address = msg.substring(0, msg.indexOf(';'));

					msg = msg.substring(msg.indexOf(';') + 1);
					String phone = msg.substring(0, msg.indexOf(';'));

					msg = msg.substring(msg.indexOf(';') + 1);
					String email = msg.substring(0, msg.indexOf(';'));

					if ((new Resident(null)).addResident(name, address, phone, email)) {
						System.out.println("Added new resident success");
					} else {
						System.out.println("Added new resident failed");
					}
					break;
				}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
			// hopefully shouldn't happen
		}
	}
}
