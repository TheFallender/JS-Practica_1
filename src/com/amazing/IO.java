package com.amazing;

import java.io.File; 			//File checks
import java.io.FileReader; 		//File Reader 1
import java.io.BufferedReader; 	//File Reader 2
import java.io.FileWriter;		//File Writer
import java.io.IOException;		//Exception

public class IO {
	protected static String data_path = ""; //Path to the Data Folder
	protected static String[] data_a; //Data array
	protected static int data_c = 0; //Data count size
	
	protected static void read (String file_type, String search_for, int array_size, boolean repeat) {	//Reads all the filtered data from file
		String file_path = data_path + file_type;	//Path to the file
		try(BufferedReader reader = new BufferedReader(new FileReader(file_path))) { //Tries to open the file
			data_a = new String[array_size];
			data_a[0] = ""; //Check if no data is found
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
		}
		catch (Exception FileNotFoundException) { //Exception Catch
			System.out.println("ERROR - File not found.");
		}
	}
	
	protected static void write (String file_type, String[] input, boolean append) { //Writes the new object
		String file_path = data_path + file_type;	//Path to the file
		try(FileWriter writer =  new FileWriter(file_path, append)) {	//Tries to open the file
			for (int i = 0; i < input.length; i++)
				writer.write(input[i] + "\r\n");
		}
		catch (IOException e) { //Exception Catch
			System.out.println("ERROR - File not found.");
		}
	}

	
	//Starting function
	protected static void data_check () { //Checks if the data files exist
		try(BufferedReader reader = new BufferedReader(new FileReader("data_path"))) { //Tries to open the file
			String line = reader.readLine(); //Line data
			if (line == "") { //If there is no content on the file, add default path
				try(FileWriter writer =  new FileWriter("data_path", true)) { //Tries to open the file
					writer.write("DATA_PATH=src/Data/");
					data_path = "src/Data/"; //Sets default path in the app
				}
				catch (Exception IOException) { //Exception Catch
					System.out.println("ERROR - File not found");
				}
			}
			else { //If there is content on the file, read it and set it
				data_path = line.replaceAll("DATA_PATH=", ""); //Sets the defined path
			}

		}
		catch (IOException e_1) { //There is no file, create a new one and set the default path
			try (FileWriter writer =  new FileWriter("data_path", true)){
				writer.write("DATA_PATH=src/Data/");
				data_path = "src/Data/"; //Sets default path in the app
			} 
			catch (IOException e_2) {
				System.out.println("ERROR - File not found");
			}
		}
		if (Amazing.test) {
			data_path = "src/Test/";
		}
		//Files check
		files_check(data_path, true);
		files_check(data_path + "d_category", false);
		files_check(data_path + "d_product", false);
		files_check(data_path + "d_user", false);
		files_check(data_path + "d_product_user", false);
		files_check(data_path + "d_converter_rate", false);
	}
	
	
	//Auxiliary functions
	protected static void files_check(String path, boolean folder) { //Checks if file exists, if not it creates one
		try {
			File file = new File(path);
			if (!file.exists())
				if (folder)
					file.mkdir();
				else		
					file.createNewFile();
		} 
		catch (IOException e) {
			System.out.println("Error - Invalid path.");
		}
	}
	
	protected static void modify (String file_type, String[] input, int skip) { //Writes the date of the login on the user
		String file_path = data_path + file_type;
		try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) { //Tries to open the file
			String line = ""; //Line data
			StringBuilder text = new StringBuilder(); //Text from the file
			boolean applied = false;
			while((line = reader.readLine()) != null){ //Buffered Reader searches for the user
				if (!applied) {
					if (line.contains(input[0])) { //Check if this is the user line
						for (int j = 0; j < skip; j++) { //Once found, lines to skip
							text.append(line + "\r\n");
							line = reader.readLine();
						}
						for (int i = 1; i < input.length; i++) {
							text.append(input[i]);
							if(i < input.length - 1)
								line = reader.readLine();
						}
						
						applied = true;
					}
					else //Get the line
						text.append(line + "\r\n");
				}
				else //Get the line
					text.append(line + "\r\n");
			}
			try(FileWriter writer = new FileWriter(file_path, false)){ //Try to open the file
	            writer.write(text.toString()); //Write the text on the file
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