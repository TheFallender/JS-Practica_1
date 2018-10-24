package com.amazing;

import java.io.File; 			//File checks
import java.io.FileReader; 		//File Reader 1
import java.io.BufferedReader; 	//File Reader 2
import java.io.FileWriter;		//File Writer
import java.util.Date;			//Date
import java.io.IOException;		//Exception

public class io_text {
	protected static String data_path = ""; //Path to the Data Folder
	protected static String data_a[] = null; //Data array
	protected static int data_c = 0; //Data count size
	
	protected static void read (String file_type, String search_for, int search_max,  int skip_in, int skip_length) {	//Reads all the filtered data from file
		String file_path = data_path + file_type;	//Path to the file
		try(BufferedReader reader = new BufferedReader(new FileReader(file_path))) { //Tries to open the file
			data_a = new String[search_max];
			data_c = 0; //Counter
			String line = ""; //Line data
			boolean skip_multiple = true;
			for (line = reader.readLine(); reader.readLine() != null; reader.readLine()) {
				if (line.contains(search_for)) { //Check if this is the requested line
					if (data_c > search_max)
						break;
					else {
						if (skip_multiple) { //Avoid multiple skips
							for(int i = 0; i < skip_in; i++) //Skip next number of lines
								line = reader.readLine();
							if (skip_in < 0) {
								skip_in *= -1;
								skip_multiple = false;
							}
						}
						//CHANGE THIS CHANGE THIS CHANGE THIS CHANGE THIS CHANGE THIS CHANGE THIS CHANGE THISCHANGE THIS
						if (skip_length == 0) { //No skips
							for (int i = 0; i < search_max; i++) {
								if (i != 0) //Next line
									line = reader.readLine(); //Reads the line
								data_a[data_c] = line; //Copies the line
								data_a[data_c] = data_a[data_c].replaceAll(".+=", ""); //Cleans the line data
								data_c++; //Increases the data count
							}
						}
						else { //Used for optimization, removes unnecesary checks and jumps straight to the next line with the same type.
							data_a[data_c] = line; //Copies the line
							data_a[data_c] = data_a[data_c].replaceAll(".+=", ""); //Cleans the line data
							data_c++; //Increases the data count
							for (int i = 0; i < skip_length; i++)
								if (reader.readLine() != null)
									reader.readLine();
						}
					}
				}
			}
			reader.close();
		}
		catch (Exception FileNotFoundException) { //Exception Catch
			System.out.println("ERROR - File not found.");
		}
	}
	
	protected static void write (String file_type, String input[]) {	//Writes the new object
		String file_path = data_path + file_type;	//Path to the file
		try(FileWriter writer =  new FileWriter(file_path, true)) {	//Tries to open the file
			switch (file_type) {
				case "d_user": //User
					writer.write("u_email=" + input[0] + "\n");
					writer.write("u_password=" + input[1] + "\n");
					writer.write("u_login=" + input[2] + "\n");
					writer.write("u_last_login=" + input[3] + "\n");
					break;
				case "d_category": //Category
					writer.write("category=" + input[0] + "\n");
					break;
				case "d_product": //Product
					writer.write("p_category=" + input[0] + "\n");
					writer.write("p_id=" + input[1] + "\n");
					writer.write("p_name=" + input[2] + "\n");
					writer.write("p_price=" + input[3] + "\n");
					writer.write("p_stock=" + input[4] + "\n");
					break;
				case "d_product_user ": //Product User
					writer.write("pu_u_id=" + input[0] + "\n");
					writer.write("pu_p_id=" + input[1] + "\n");
					writer.write("pu_number=" + input[1] + "\n");
					break;
			}
			writer.close();
		}
		catch (IOException e) { //Exception Catch
			System.out.println("ERROR - File not found.");
		}
	}

	
	//Starting function
	protected static void data_check () { //Checks if the data files exist
		File file_c = new File ("data_path");
		try(BufferedReader reader = new BufferedReader(new FileReader("data_path"))) { //Tries to open the file
			String line = ""; //Line data
			if (reader.readLine() != null) //Line exists
				line = reader.readLine();
			else { //Line doesn't exist, corrupted file, delete the old one and create a new one.
				file_c.delete();
				file_c.createNewFile();
			}
			reader.close();
			if (line == "") { //If there is no content on the file, add default path
				try(FileWriter writer =  new FileWriter("data_path", true)) { //Tries to open the file
					writer.write("DATA_PATH=src/Data/");
					writer.close();
					data_path = "src/Data/"; //Sets default path in the app
				}
				catch (Exception IOException) {} //Exception Catch
			}
			else { //If there is content on the file, read it and set it
				data_path = line.replaceAll(".+=", ""); //Sets the defined path
			}

		}
		catch (IOException e_1) { //There is no file, create a new one and set the default path
			try {
				file_c.createNewFile();
				FileWriter writer =  new FileWriter("data_path", true);
				writer.write("DATA_PATH=src/Data/");
				writer.close();
				data_path = "src/Data/"; //Sets default path in the app
			} 
			catch (IOException e_2) {}
		}
		//Files check
		files_check(data_path + "d_category");
		files_check(data_path + "d_product");
		files_check(data_path + "d_user");
		files_check(data_path + "d_product_user");
	}
	
	
	//Auxiliary functions
	private static void files_check(String path) { //Checks if file exists, if not it creates one
		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
		} 
		catch (IOException e) {}
	}
	
	protected static int search_user (String input) { //Searches the user inside the files
		String file_path = data_path + "d_user";
		try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) { //Tries to open the file
			String line = ""; //Line data
			while((line = reader.readLine()) != null){ //Buffered Reader searches for the user
				if (line.equals("u_email=" + input)) //Check if this is the user line
					return 1; //User found
			}
			reader.close();
		}
		catch (IOException e) { //Exception Catch
			System.out.println("ERROR - File not found.");
			return -1;
		}
		return 0; //No user found
	}
	
	protected static void write_login (String username, boolean is_log_in) { //Writes the date of the login on the user
		String file_path = data_path + "d_user";
		try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) { //Tries to open the file
			String line = ""; //Line data
			String text = ""; //Text from the file
			while((line = reader.readLine()) != null){ //Buffered Reader searches for the user
				if (line.equals(username)) { //Check if this is the user line
					text += line + "\r\n"; //User
					line = reader.readLine();
					text += line + "\r\n"; //Password
					line = reader.readLine();
					if (is_log_in) { //Login in, login is written
						//Login
						Date d = new Date();
						text += "u_login=" + d.getTime() + "\r\n";
						//Last login
						line = reader.readLine();
						text += line + "\r\n";
					}
					else { //Log out, login is erased and last login gets the value of login
						//Login
						text += "u_login=0\r\n";
						//Last login
						String aux = line;
						aux.replaceAll(".+=", "");
						reader.readLine();
						text += "u_last_login=" + aux + "\r\n";
					}
					break;
				}
				else //Get the line
					text += line + "\r\n";
			}
			reader.close();
			try(FileWriter writer = new FileWriter(file_path, false)){ //Try to open the file
	            writer.write(text); //Write the text on the file
	            writer.close();
			}
			catch (IOException e) { //Exception Catch
				System.out.println("ERROR - File not found.");
			}

		}
		catch (IOException e) { //Exception Catch
			System.out.println("ERROR - File not found.");
		}
	}
}
