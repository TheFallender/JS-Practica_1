package com.amazing;

public class User { //User class
	
	private String email; 			//Email of the user
	private String password; 		//Password of the user
	private long login = 0; 		//Login date
	private long last_login = 0; 	//Last login date
	private String admin = "0"; 	//Admin
	
	protected User() { //User basic Constructor
		//Email
		while(true) { 																				//Loop to get the email
			String email_data = Filter.filter_s("Insert your email: "); 								//Email Data 
			if (email_data.equals("exit")) 																//Prevent exit as email
				System.out.println("ERROR - You can't enter 'exit' as an email."); 							//Reports that you can't put 'exit' as an email
			else {
				IO.read("d_user", "u_email=" + email_data, 1, false);										//Read User
				if (IO.data_a[0] == null) { 																	//User doesn't exist, proceed
					this.email = email_data;																	//Set email
					break;																						//Break the loop
				}
				else 																						//User already exists
					System.out.println("ERROR - User already exist, try another email.");						//Report that the user already exists
			}
		}
		//Password
		try { 																							//Try to encrypt password
			this.password = Encrypter.encrypt(Filter.filter_s("Insert your password: ")); 					//Encrypt password and set it
		} catch (Exception e) {																			//Illegal Exception
			throw new IllegalArgumentException("ERROR - Error illegal operation on the encryption.");		//Report that there was an illegal operation
		}
		
		//Date
		this.login = Long.parseLong(Login_method.date()); //Set the login date to the moment of the creation
	}
	
	protected User(String[] data) { //User data Constructor
		this.email = data[0];								//Set Email
		this.password = data[1];							//Set Password
		this.login = Long.parseLong(Login_method.date());	//Set Login
		this.last_login = Long.parseLong(data[3]);			//Set Last Login
		this.admin = data[4];								//Set Admin
	}
	
	protected String r_email () { //Returns the Email
		return this.email;
	}
	
	protected String r_pass () { //Returns the Password (encrypted)
		return this.password;
	}
	
	protected long r_date(boolean login) { //Returns the Date
		return (login) ? this.login : this.last_login;
	}

	protected boolean r_admin() { //Returns the Admin value
		return (admin.equals("1")) ? true : false;
	}
	
	protected void print() { //Prints User
		System.out.println("Email: " + (this.email));			//Print Email
		System.out.println("Password: ********");				//Print Password (nothing)
		System.out.println("Login: " + this.login);				//Print Login
		System.out.println("Last login: " + this.last_login);	//Print Last Login
		if(r_admin()) {	//Checks if its admin
			System.out.println("This user has admin access");	//Print Admin
		}
	}
	
	protected void save() {	//Saves the data on the file
		//String
		String[] aux = new String[5];					//Auxiliar string to save
		aux[0] = "u_email=" + this.email;					//Set Email
		aux[1] = "u_password=" + this.password;				//Set Password
		aux[2] = "u_login=" + this.login;					//Set Login
		aux[3] = "u_last_login=" + this.last_login;			//Set Last Login
		aux[4] = "u_admin=" + this.admin;					//Set Admin
		
		//Write data
		IO.write("d_user", aux, true);
	}
}