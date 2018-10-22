package com.amazing;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;

public class io_text {
	private static final String data_path = "src/Data/"; //Path to the Data Folder
	protected static String data[] = null; //Data array
	protected static int c = 0; //Counter
	
	protected static void read_every (String file_type, String input) {	//Reads all the filtered data from file
		String file_path = data_path + file_type;	//Path to the file
		try(BufferedReader reader = new BufferedReader(new FileReader(file_path))) { //Tries to open the file
			String line = ""; //Line data
			switch (file_type) {
				case "d_product_u": //Product user
					data = new String[10];
					while((line = reader.readLine()) != null){ //Buffered Reader searches for the input
						if (line.equals("pu_u_id=" + input)) { //Check if this is the requested line
							if (c > 10) {
								break;
							}
							else {
								data[c] = line;
								data[c] = data[c].replaceAll(".+=", "");
								c++;
							}
						}
					}
					break;
				case "d_product": //Product
					data = new String[50];
					while((line = reader.readLine()) != null){ //Buffered Reader searches for the input
						if (line.equals("p_category=" + input)) { //Check if this is the requested line
							if (c > 50) {
								break;
							}
							else {
								data[c] = line;
								data[c] = data[c].replaceAll(".+=", "");
								c++;
							}
						}
					}
				case "d_category": //Category
					data = new String[5];
					while((line = reader.readLine()) != null){ //Buffered Reader searches for the input
						data[c] = line;
						data[c] = data[c].replaceAll(".+=", "");
						c++;
					}
					break;
			}
			reader.close();
		}
		catch (Exception FileNotFoundException) { //Exception Catch
			System.out.println("ERROR - File not found.");
		}
	}
	
	protected static void read_d (String file_type, String input) {	//Reads one data from the file
		String file_path = data_path + file_type;	//Path to the file
		try(BufferedReader reader = new BufferedReader(new FileReader(file_path))) { //Tries to open the file
			String line = ""; //Line data
			
			switch (file_type) {
				case "d_user": //User
					data = new String[4];
					while((line = reader.readLine()) != null){ //Buffered Reader searches for the user
						if (line.equals("u_email=" + input)) { //Check if this is the user line
							for (int i = 0; i < 4; i++) {
								data[i] = line;
								data[i] = data[i].replaceAll(".+=", "");
								if (i < 3) {
									reader.readLine();
								}
							}
							break;
						}
					}
					break;
				case "d_product_u": //Product user
					data = new String[2];
					while((line = reader.readLine()) != null){ //Buffered Reader searches for the user
						if (line.equals("pu_u_id=" + input)) { //Check if this is the user line
							for (int i = 0; i < 2; i++) {
								data[i] = line;
								data[i] = data[i].replaceAll(".+=", "");
								if (i < 1) {
									reader.readLine();
								}
							}
							break;
						}
					}
					break;
				case "d_product": //Product
					data = new String[4];
					while((line = reader.readLine()) != null){ //Buffered Reader searches for the user
						if (line.equals("p_name=" + input)) { //Check if this is the user line
							for (int i = 0; i < 4; i++) {
								data[i] = line;
								data[i] = data[i].replaceAll(".+=", "");
								if (i < 3) {
									reader.readLine();
								}
							}
							break;
						}
					}
					break;
			}
			reader.close();
		}
		catch (Exception FileNotFoundException) { //Exception Catch
			System.out.println("ERROR - File not found.");
		}
	}
	
	protected static void write (String file_type) {	//Writes the new object
		String file_path = data_path + file_type;	//Path to the file
		try(FileWriter writer =  new FileWriter(file_path, true)) {	//Tries to open the file
			switch (file_type) {
				case "d_user": //User
					writer.write("u_email=" + amazing.active_user.r_email() + "\n");
					writer.write("u_password=" + amazing.active_user.r_pass() + "\n");
					writer.write("u_login=" + amazing.active_user.r_date(true) + "\n");
					writer.write("u_last_login=" + amazing.active_user.r_date(false) + "\n");
					break;
			}
			writer.close();
		}
		catch (Exception IOException) { //Exception Catch
			System.out.println("ERROR - File not found.");
		}
	}
	
	protected static void write_login (String user, boolean log_in, user object_u) { //Writes the date of the login
		String file_path = data_path + "d_user";
		try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) { //Tries to open the file
			String line = ""; //Line data
			String text = ""; //Text from the file
			while((line = reader.readLine()) != null){ //Buffered Reader searches for the user
				if (line.equals("u_email=" + user)) { //Check if this is the user line
					text += line + "\r\n"; //User
					reader.readLine();
					text += line + "\r\n"; //Password
					reader.readLine();
					if (log_in) { //Log in, login is written
						//Login
						text += "u_login=" + object_u.r_date(true) + "\r\n";
						//Last login
						reader.readLine();
						text += line + "\r\n";
					}
					else { //Log out, login is erased and 
						//Login
						text += "u_login=0\r\n";
						//Last login
						reader.readLine();
						text += "u_last_login=" + object_u.r_date(false) + "\r\n";
					}
				}
				else { //Get all the code
					text += line + "\r\n";
				}
			}
			reader.close();
			try(FileWriter writer = new FileWriter(file_path, false)){ //Try to open the file
	            writer.write(text); //Write the text on the file
	            writer.close();
			}
			catch (Exception IOException) { //Exception Catch
				System.out.println("ERROR - File not found.");
			}

		}
		catch (Exception FileNotFound){
			System.out.println("ERROR - File not found.");
		}
	}
	
	protected static int search_user (String input) { //Searches the user inside the files
		String file_path = data_path + "d_user";
		try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) { //Tries to open the file
			String line = ""; //Line data
			while((line = reader.readLine()) != null){ //Buffered Reader searches for the user
				if (line.equals("u_email=" + input)) { //Check if this is the user line
					return 1;
				}
			}
			reader.close();
		}
		catch (Exception FileNotFound){
			System.out.println("ERROR - File not found.");
			return -1;
		}
		return 0;
	}
}
