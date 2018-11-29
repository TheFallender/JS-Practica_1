package amazing.amazing;

import amazing.inside.Encrypter;
import amazing.inside.Filter;
import amazing.inside.IO;
import amazing.inside.Localization;
import amazing.inside.Login_method;

public class User { //User class
	
	private String email; 			//Email of the user
	private String password; 		//Password of the user
	private String login = "0"; 		//Login date
	private String last_login = "0"; 	//Last login date
	private String admin = "0"; 	//Admin
	
	public User() { //User basic Constructor
		//Email
		while(true) { 																				//Loop to get the email
			String email_data = Filter.filter_s(Localization.get("objects", "user_crt_em")); 							//Email Data 
			if (email_data.equals("exit")) 																//Prevent exit as email
				System.out.println(Localization.get("objects", "user_crt_err_exit")); 							//Reports that you can't put 'exit' as an email
			else {
				IO.read("d_user", "u_email=" + email_data, 1, false);										//Read User
				if (IO.data().isEmpty()) { 																	//User doesn't exist, proceed
					this.email = email_data;																	//Set email
					break;																						//Break the loop
				}
				else 																						//User already exists
					System.out.println(Localization.get("objects", "user_crt_err_exist"));						//Report that the user already exists
			}
		}
		//Password
		try { 																							//Try to encrypt password
			this.password = Encrypter.encrypt(Filter.filter_s(Localization.get("objects", "user_crt_pw"))); 	//Encrypt password and set it
		} catch (Exception e) {																			//Illegal Exception
			throw new IllegalArgumentException(Localization.get("inside", "enc_err_illgl"));		//Report that there was an illegal operation
		}
		
		//Date
		this.login = Login_method.date(); //Set the login date to the moment of the creation
	}
	
	public User(String[] data) { //User data Constructor
		this.email = data[0];								//Set Email
		this.password = data[1];							//Set Password
		this.login = Login_method.date();	//Set Login
		this.last_login = data[3];			//Set Last Login
		this.admin = data[4];								//Set Admin
	}
	
	public String r_email () { //Returns the Email
		return this.email;
	}
	
	public void s_pass (String new_pw) { //Set the Password (encrypted)
		this.password = new_pw;
	}
	
	public String r_pass () { //Returns the Password (encrypted)
		return this.password;
	}
	
	public String r_date(boolean login) { //Returns the Date
		return (login) ? this.login : this.last_login;
	}

	public boolean r_admin() { //Returns the Admin value
		return (admin.equals("1"));
	}
	
	public void print() { //Prints User
		System.out.println(Localization.get("objects", "user_print_em") + (this.email));			//Print Email
		System.out.println(Localization.get("objects", "user_print_pw") + "********");				//Print Password (nothing)
		System.out.println(Localization.get("objects", "user_print_lg") + this.login);				//Print Login
		System.out.println(Localization.get("objects", "user_print_llg") + this.last_login);	//Print Last Login
		if(r_admin()) {	//Checks if its admin
			System.out.println(Localization.get("objects", "user_print_adm"));	//Print Admin
		}
	}
	
	public void save() {	//Saves the data on the file
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

	public void s_admin(String level) { //Sets the admin level
		this.admin = "1";
	}
}