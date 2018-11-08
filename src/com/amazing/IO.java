package com.amazing;

import java.io.File; 			//File checks
import java.io.FileReader; 		//File Reader 1
import java.io.BufferedReader; 	//File Reader 2
import java.io.FileWriter;		//File Writer
import java.util.Date;			//Date
import java.io.IOException;		//Exception

public class IO {
	protected static String data_path = ""; //Path to the Data Folder
	protected static String data_a[] = null; //Data array
	protected static int data_c = 0; //Data count size
	
	protected static void read (String file_type, String search_for, int array_size, boolean repeat) {	//Reads all the filtered data from file
		String file_path = data_path + file_type;	//Path to the file
		try(BufferedReader reader = new BufferedReader(new FileReader(file_path))) { //Tries to open the file
			data_a = new String[array_size];
			data_c = 0; //Counter
			String line = ""; //Line data
			for (line = reader.readLine(); line != null; line = reader.readLine()) { //Search in the file
				if (line.contains(search_for)) { //Check if this is the requested line
					if (!repeat) //Remove code if it is not going to search for the same term
						search_for = "";
					if (data_c >= array_size) //Break if the array size is reached
						break;
					else {
						data_a[data_c] = line; //Copies the line
						data_a[data_c] = data_a[data_c].replaceAll(".+=", ""); //Cleans the line data
						data_c++; //Increases the data count
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
					writer.write("u_email=" + input[0] + "\r\n");
					writer.write("u_password=" + input[1] + "\r\n");
					writer.write("u_login=" + input[2] + "\r\n");
					writer.write("u_last_login=" + input[3] + "\r\n");
					writer.write("u_admin=" + input[4] + "\r\n");
					break;
				case "d_category": //Category
					writer.write("category=" + input[0] + "\r\n");
					break;
				case "d_product": //Product
					writer.write("p_category=" + input[0] + "\r\n");
					writer.write("p_id=" + input[1] + "\r\n");
					writer.write("p_name=" + input[2] + "\r\n");
					writer.write("p_price=" + input[3] + "\r\n");
					writer.write("p_stock=" + input[4] + "\r\n");
					break;
				case "d_product_user": //Product User
					writer.write("pu_u_id=" + input[0] + "\r\n");
					writer.write("pu_p_id=" + input[1] + "\r\n");
					writer.write("pu_number=" + input[2] + "\r\n");
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
		String aux = "";
		try(BufferedReader reader = new BufferedReader(new FileReader("data_path"))) { //Tries to open the file
			String line = reader.readLine(); //Line data
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
				data_path = line.replaceAll("DATA_PATH=", ""); //Sets the defined path
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
		files_check(data_path, true);
		files_check(data_path + "d_category", false);
		files_check(data_path + "d_product", false);
		files_check(data_path + "d_user", false);
		files_check(data_path + "d_product_user", false);
	}
	
	
	//Auxiliary functions
	private static void files_check(String path, boolean folder) { //Checks if file exists, if not it creates one
		try {
			File file = new File(path);
			if (!file.exists())
				if (folder)
					file.mkdir();
				else		
					file.createNewFile();
		} 
		catch (IOException e) {
			System.out.println("WAIT WHAT?");
		}
	}
	
	protected static void modify (String file_type, String input, int skip, int amount) { //Writes the date of the login on the user
		String file_path = data_path + file_type;
		try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) { //Tries to open the file
			String line = ""; //Line data
			String text = ""; //Text from the file
			String aux = "";
			boolean applied = false;
			while((line = reader.readLine()) != null){ //Buffered Reader searches for the user
				if (!applied) {
					if (line.contains(input)) { //Check if this is the user line
						for (int i = 0; i < skip; i++) { //Once found, lines to skip
							text += line + "\r\n";
							line = reader.readLine();
						}
						switch (file_type) {
							case "d_user":
								if (!Login_method.logged_in) { //Login in, login is written
									Date d = new Date(); //Get date
									text += "u_login=" + d.getTime() + "\r\n"; //Login
								}
								else { //Log out, login is erased and last login gets the value of login
									text += "u_login=0\r\n"; //Delete login info
									aux = line; //Use auxiliar
									aux = aux.replaceAll(".+=", ""); //Modify line
									reader.readLine(); //Next line
									text += "u_last_login=" + aux + "\r\n"; //Last login
								}
								break;
							case "d_product":
								aux = line;
								aux = aux.replaceAll(".+=", ""); //Modify line
								int stock = Integer.parseInt(aux);
								text += "p_stock=" + (stock - amount) + "\r\n";
								break;
						}
						applied = true;
					}
					else //Get the line
						text += line + "\r\n";
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