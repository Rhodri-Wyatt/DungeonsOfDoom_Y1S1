import java.io.*;
import java.util.ArrayList;

/**
 * Reads and contains in memory the map of the game.
 *
 */
public class Map {
	// Representation of the map as a 2d array of characters
	private char[][] map;
	// Map name of the mapName
	private String mapName;
	// Gold required for the human player to win
	private int goldRequired;
	
	/**
	 * Default constructor, creates the default map "Very small Labyrinth of doom".
	 * fills the map array with a default map. If there is not external maps then this map is used.
	 */
	public Map() {
		mapName = "Very small Labyrinth of Doom";
		goldRequired = 2;
		map = new char[][]{
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
		};
	}


    /**
     * @return : Gold required to exit the current map.
     */
    protected int getGoldRequired() {
	//this retrieves the number of gold needed to win
       return goldRequired;
    }

    /**
	 * this retrieves a data item of the map
     * @return : The map as stored in memory.
     */
    protected char[][] getMap() {
        return map;
    }


    /**
     * @return : The name of the current map.
     */
    protected String getMapName() {
	//this gets the name of the map
       return mapName;
    }

	/**
	 * this methods gets all the maps from the directory
	 * stores the maps in an array
	 * user inputs a number to represent which map he wishes to choose
	 *
	 */
	protected void selectMap(){
		/*
		Code to get filenames of all the files in a folder of a certain type modified from
		https://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
		 */

		//validInput is used as a flag to check that the input entered is acceptable.
		boolean validInput = false;
		//map number represents the map that the user wishes to play.
		//It is initially set to a null value forcing the user to choose a map.
		int mapNumber = -1;

		//The pathname of where the maps are stored in the same directory as where the java files are stored .
		String pathname = System.getProperty("user.dir");
		//file is the folder containing all files folder this program is located in. this includes java and txt files.
		//File is an object for storing files where all the
		File folder = new File(pathname);
		//creates an array of all the files in the folder
		File[] arrayOfFiles = folder.listFiles();
		//create an arraylist to store files with txt extension
		ArrayList<File> arrayOfMaps = new ArrayList<File>();

		//loops through the list of maps and adds .txt to the array list
		for (int i = 0; i < arrayOfFiles.length; i++){
			if (arrayOfFiles[i].getName().contains(".txt")){
				arrayOfMaps.add(arrayOfFiles[i]);
			}
		}

		//leave method if no maps are added to the arraylist
		if (arrayOfMaps.size() == 0){
			return;
		}

		//We need the user to be able to choose the map
		// we do this by looping through the list and printing the txt filenames.
		for (int j = 0; j < arrayOfMaps.size(); j++){
			System.out.println((j + 1) + ". " + arrayOfMaps.get(j).getName());
		}

		//now get the user to enter in a number
		//starts from 1 as its easier for people to input instead of 0
		System.out.println("Enter from 1 to "+ arrayOfMaps.size() +" in map number for corresponding map."  );

		//retrieves map number user wishes to use.
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		//loop keeps checking for valid input by the user
		while (!validInput) {
			//tries to process the command
			try {
				//reads the line and stores it as input
				String input = reader.readLine();
				//try and get a valid number from the user input
				try {
					//if the number is between 0 and the number of maps then it is valid
					if (Integer.parseInt(input) > 0 && Integer.parseInt(input) <= arrayOfMaps.size()) {
						validInput = true;
						//set map number to an integer version of the inputted string number
						mapNumber = Integer.parseInt(input) - 1;
					} else {
						//else ask user to input a valid number
						System.out.println("Please enter a number between 1 and " + arrayOfMaps.size());
					}
				} catch (Exception e) {
					System.out.println("Please input a number");
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
		}
		//call the readMap function
		readMap(arrayOfMaps.get(mapNumber));
	}





    /**
     * Reads the map from file.
     *
     * @param fileContainingMap : the actual txt file
     */
    protected void readMap(File fileContainingMap) {
    	//temp variables are used to store the text from the map before it is fixed.
		//temp variables are needed as data is inputted as strings and contains text before the useful data.
    	String tempMapName = "";
    	String tempGoldRequired = "";
    	//creates an arraylist of type String called mapRowsAsString to store the rows of the map as strings.
    	ArrayList<String> mapRowsAsStrings = new ArrayList<>();

    	try {
			BufferedReader reader = new BufferedReader(new FileReader(fileContainingMap));
			//reads the first line into a temporary string for mapname
			tempMapName = reader.readLine();
			//reads the second line into a temporary string for goldRequired
			tempGoldRequired = reader.readLine();

			//reads each individual line of the map into the array.
			//map has to be a complete rectangle
			while (true) {
				String mapLine = reader.readLine();
				if (mapLine != null){
					mapRowsAsStrings.add(mapLine);
				} else {
					break;
				}
			}

			//sets the variables to the usable values (gets rid of informative text)
			mapName = tempMapName.substring(4);
			goldRequired = Integer.parseInt(tempGoldRequired.substring(4));

			fillMapArray(mapRowsAsStrings);

		} catch (Exception e) {
			System.out.println("Issue with loading map");
		}
	}


	/**
	 * fills the map array with the rows from the
	 *
	 * @param mapRowsAsStrings : an array list of the rows of the map
	 */
	protected void fillMapArray (ArrayList<String> mapRowsAsStrings){
    	//remember y then x
		//y = the rows of the list. x = how many characters are in the first item.
    	map = new char[mapRowsAsStrings.size()][mapRowsAsStrings.get(0).length()];
    	for (int y = 0; y < mapRowsAsStrings.size(); y++){
    		for (int x = 0; x < mapRowsAsStrings.get(0).length(); x++){
				//re-initiate the map
				map[y][x] = mapRowsAsStrings.get(y).charAt(x);
			}
		}
		System.out.println(mapName + " - Map loaded successfully.");
	}








}
