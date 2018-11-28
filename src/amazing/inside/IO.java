package amazing.inside;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import amazing.amazing.Amazing;

public class IO { //Input Output class
	private static String data_path = ""; 	//Path to the Data Folder
	private static ArrayList<String> data_a = new ArrayList<>(); 			//Data array
	
	public static final List<String> data(){ //Returns the Data List
		return data_a;
	}
	
	public static void read (String file_type, String search_for, int array_size, boolean repeat) {	//Reads all the filtered data from file
		String file_path = data_path + file_type;											//Path to the file
		try(BufferedReader reader = new BufferedReader(new FileReader(file_path))) { 		//Tries to open the file
			data_a = new ArrayList<>(); 	 													//Data array with the size of the parameter
			for (String line = reader.readLine(); line != null; line = reader.readLine())				//Search in the file
				if (line.contains(search_for)) { 													//Check if this is the requested line
					if (!repeat) 																		//Remove code if it is not going to search for the same term
						search_for = "";																	//Now it accepts anything as the "" contains nothing
					if ((array_size != 0) && (data_a.size() >= array_size)) 							//Break if the array size is reached
						break;																				//Break the loop
					else																				//There is space for the data
						data_a.add(line.replaceFirst("^\\w+=", "")); 										//Cleans the line data
				}
		}
		catch (Exception FileNotFoundException) { //Exception Catch
			System.out.println(Localization.get("inside", "io_err_fnf")); //Reports that there was no file found
		}
	}
	
	public static void write (String file_type, String[] input, boolean append) { //Writes the array inside the selected file
		String file_path = data_path + file_type; 						//Path to the file
		try(FileWriter writer =  new FileWriter(file_path, append)) {	//Tries to open the file
			for (int i = 0; i < input.length; i++)							//For to write the array "input"
				writer.write(input[i] + "\r\n");								//Writes the selected data from the array
		}
		catch (IOException e) { //Exception Catch
			System.out.println(Localization.get("inside", "io_err_fnf")); //Reports that there was no file found
		}
	}

	
	//Starting function
	public static void data_check () { //Sets data path and checks files
		//Try to set the default path
		if (Amazing.get_test()) 																	//Test is active, set the path for test
			data_path = "src/Test/";															//Data path set
		else																				//Test is disabled, proceed
			try(BufferedReader reader = new BufferedReader(new FileReader("data_path"))) { 		//Tries to open the file
				String line = reader.readLine(); 													//Line data
				if (line == "") { 																	//If there is no content on the file, add default path
					try(FileWriter writer =  new FileWriter("data_path", true)) { 						//Tries to open/create the file
						writer.write("DATA_PATH=src/Data/");												//Writes the default path
						data_path = "src/Data/"; 															//Sets default path
					}
					catch (Exception IOException) { 													//Couldn't read the file (file not found)
						System.out.println(Localization.get("inside", "io_err_fnf")); 						//Reports that there was no file found
					}
				}
				else 																				//If there is content on the file, read it and set it
					data_path = line.replaceFirst(".*=", ""); 										//Sets the defined path
	
			}
			catch (IOException e_1) { 															//There is no file, create a new one and set the default path
				try (FileWriter writer =  new FileWriter("data_path", true)){						//Tryes to open the new file
					writer.write("DATA_PATH=src/Data/");												//Writes the default path
				} 
				catch (IOException e_2) { 															//Couldn't read the file (file not found)
					System.out.println(Localization.get("inside", "io_err_fnf")); 						//Reports that there was no file found
				}
				data_path = "src/Data/";															//In any case it sets the default path
			}

		//Files check
		files_check(data_path, true); 							//Check folder
		files_check(data_path + "d_category", false); 			//Check Category
		files_check(data_path + "d_product", false); 			//Check Product
		files_check(data_path + "d_user", false); 				//Check User
		files_check(data_path + "d_product_user", false); 		//Check Product User
		files_check(data_path + "d_converter_rate", false); 	//Check Converter Rate
		files_check(data_path + "d_region", false); 			//Check Region
		files_check(data_path + "d_locale", false); 			//Check Locale
		if (Amazing.get_test()) //Test is active
			files_check(data_path + "d_test", false); 			//Check Test
	}
	
	
	//Auxiliary functions
	public static void files_check(String path, boolean folder) { //Checks if a file exists, if not, it creates one
		try {																	//Tries to create the file/folder
			File file = new File(path);												//New file with the path
			if (!file.exists())	{													//No file exists with the same name
				if (folder)																//It's a folder
					file.mkdir();															//Creates the folder
				else																	//It's a file
					if (!file.createNewFile())												//Checks if the file couldn't be created
						System.out.println(Localization.get("inside", "io_err_crf")); 					//Reports that it couldn't create the file
			}
		} 
		catch (IOException e) { 												//Invalid path selected (admin access, other drive...)
			System.out.println(Localization.get("inside", "io_err_path")); 							//Reports that the selected path is invalid
		}
	}
	
	public static void modify (String file_type, String[] input, int skip) { //Writes the date of the login on the user
		String file_path = data_path + file_type; 										//Defines the path of the file with data path and the file name
		try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) { 	//Tries to open the file
			String line = ""; 																//Line of data
			StringBuilder text = new StringBuilder(); 										//Text from the file
			boolean applied = false;														//Boolean to check if the changes were made
			while((line = reader.readLine()) != null){ 										//Buffered Reader searches for the user
				if (!applied) {																	//If changes not applied
					if (line.contains(input[0])) { 													//Check if this is the searched line
						//Skip
						for (int j = 0; j < skip; j++) { 												//Once it's found, skips the number of lines selected
							text.append(line + "\r\n");														//Adds the line to the buffered reader
							line = reader.readLine();														//Reads the next line
						}
						
						//Write modified data
						for (int i = 1; i < input.length; i++) {										//Writes the lines from the array
							text.append(input[i] + "\r\n");															//Adds the lines to the text
							if(i < input.length - 1)														//If it isn't last line
								line = reader.readLine();														//Reads the next line
						}
						
						//Applied changes
						applied = true;																	//Changes applied, block access to optimize performance
					}
					else 																			//Does not contain the data
						text.append(line + "\r\n");														//Add lines to the text
				}
				else 																			//Changes applied
					text.append(line + "\r\n");														//Add lines to the text
			}
			//Writer
			try(FileWriter writer = new FileWriter(file_path, false)){ 						//Try to open the file to write
	            writer.write(text.toString()); 													//Write the text on the file
			}
			catch (IOException e) { 														//Couldn't create the writer
				System.out.println(Localization.get("inside", "io_err_fnf")); 					//Reports that there was no file found
			}
		}
		catch (IOException e) { 														//Couldn't create the reader
			System.out.println(Localization.get("inside", "io_err_fnf")); 					//Reports that there was no file found
		}
	}
}