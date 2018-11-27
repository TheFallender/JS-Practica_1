package amazing.inside;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import amazing.amazing.Amazing;

public class Login_method { //Login class
	public static boolean login_method_in() { //Log in method
		//Variables
		String email; //Email
		String e_password; //Encrypted password
		
		//Loop for the login
		while(true) { //User check
			email = Filter.filter_s("Insert your email: "); //Filters the email
			
			//Exit check
			if (email.equals("exit")) 	//Email is exit
				return false; 				//Get out of the log in
			
			//Password encryption
			try {
				e_password = Encrypter.encrypt(Filter.filter_s("Insert your password: ")); //Get the encrypted password
			}
			catch (Exception e) { //Illegal operation
				throw new IllegalArgumentException("ERROR - Error illegal operation on the encryption."); //Report that there was an illegal operation
			}
			
			//Read the data from the User file
			IO.read("d_user", "u_email=" + email, 5, false);
			
			//Check if User exists
			if (IO.data().isEmpty()) 													//User not found
				System.out.println("ERROR - Username and password doesn't match."); 		//For security reasons, it doesn't report that there isn't a user with that email
			else { //User exists
				//Checks if password matches
				if (e_password.equals(IO.data().get(1))) { //Password match login in
					//User create
					Amazing.set_a_user(true); //Set the new user based on the array
					
					//Data array
					String[] data = new String[2]; //String to pass through modify
					data[0] = email; //Set Email
					data[1] = "u_login=" + date(); //Login to modify
					
					//Modify
					IO.modify("d_user", data, 2); //Modify the login info
					
					//Return value
					return true;
				}
				else //Password doesn't match
					System.out.println("ERROR - Username and password doesn't match."); //Report that the passwords don't match
			}
		}
	}
	
	public static void login_method_out() { //Log out method
		//Data array
		String[] data = new String[3]; 							//String to pass through modify
		data[0] = "u_email=" + Amazing.get_a_user().r_email(); 	//Set the email
		data[1] = "u_login=0"; 								//Set the login
		data[2] = "u_last_login=" + date(); 			//Set the last login
		
		//Modify
		IO.modify("d_user", data, 2); //Modify the login info
		
		//Reset the user
		Amazing.set_a_user(false);
	}
	
	public static String date() { //Get the actual date
    	DateTimeFormatter date_format = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm"); 	//Date format of the Date
    	LocalDateTime date = LocalDateTime.now(); 											//Set the Date now
    	
    	//Return value
    	return date_format.format(date); //Date with the defined format
	}
}
