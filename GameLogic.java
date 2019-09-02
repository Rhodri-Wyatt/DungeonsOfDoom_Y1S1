/**
 * Project is called Dungeons of Doom and is written for the second coursework of Principles of Programming.
 * Completed on 13/12/18
 *
 * Class GameLogic contains the main logical elements of the game.
 *
 * General notes: a tile in this code refers to a single item in the array.
 *                  I imagine each single item in the array as a tile on the floor of dungeon
 *
 */


//import for random start position
import java.util.Random;

public class GameLogic {

	private Map map;
	private HumanPlayer player;
	private Bot bot;

	//Variable holds the current value of the current tile the user is on. This could be either '.' 'E' or 'G'
    private char currentTile;
    //String to store the command entered from the input
    private String inputtedCommand;
    //Boolean to show the game has ended
    private Boolean gameEnded = false;

    //Define random number object to be used in setting the initial player position
    Random rand = new Random();

    //Keeps track of players turn. Player starts first. The bot and player take it in turns to go.
    private boolean playersTurn = true;

	/**
	 * Default constructor
	 */
	public GameLogic() {
        //use Map class to use map array in that class.
		map = new Map();
		player = new HumanPlayer();
		bot = new Bot();
	}

    /**
	 * Checks if the game is running
	 *
     * @return if the game is running.
     */
    protected boolean gameRunning() {
        if (gameEnded == false){
            return true;
        } else {
            return false;
        }
    }

    /**
	 * prints out the gold required to win.
     */
    protected void hello() {
        System.out.println("Gold to win :" + map.getGoldRequired());
    }
	
	/**
	 * prints out the gold currently owned by the player.
     */
    protected void gold() {
        System.out.println("Gold owned : " + player.getGoldOwned());
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @param direction : The direction of the movement.
     * @return : Protocol if success or not.
     */
    protected void move(char direction) {
        //inverse x and y as what is expected as we are dealing with a 2D array
        if (direction == 'N' && checkIfValidMove( player.getXCoordinate() , player.getYCoordinate() - 1) == true) {
            //sets the tile of where the player has just left back to what it used to be
            map.getMap()[player.getYCoordinate()] [player.getXCoordinate()] = currentTile;
            //sets current tile to be the tile the player is about to walk on
            currentTile = map.getMap()[player.getYCoordinate() - 1] [player.getXCoordinate()];
            //updates the map so P has moved N one tile
            map.getMap()[player.getYCoordinate() - 1] [player.getXCoordinate()] = 'P';
            //updates the player y coordinates to represent the change
            player.setYCoordinate( player.getYCoordinate() - 1);
            //print out that operation was a success
            System.out.println("SUCCESS");

        } else if (direction == 'E' && checkIfValidMove( player.getXCoordinate() + 1 , player.getYCoordinate()) == true) {
            //very similar code to that of moving north so im not going to re-comment.
            map.getMap()[player.getYCoordinate()][player.getXCoordinate()] = currentTile;
            currentTile = map.getMap()[player.getYCoordinate()][player.getXCoordinate() + 1];
            map.getMap()[player.getYCoordinate()][player.getXCoordinate() + 1] = 'P';
            player.setXCoordinate(player.getXCoordinate() + 1);
            System.out.println("SUCCESS");
        } else if (direction == 'S' && checkIfValidMove( player.getXCoordinate() , player.getYCoordinate() + 1) == true) {
            //very similar code to that of moving north so im not going to re-comment.
            map.getMap()[player.getYCoordinate()] [player.getXCoordinate()] = currentTile;
            currentTile = map.getMap()[player.getYCoordinate() + 1] [player.getXCoordinate()];
            map.getMap()[player.getYCoordinate() + 1] [player.getXCoordinate()] = 'P';
            player.setYCoordinate( player.getYCoordinate() + 1);
            System.out.println("SUCCESS");

        } else if (direction == 'W' && checkIfValidMove( player.getXCoordinate() - 1 , player.getYCoordinate()) == true) {
            //very similar code to that of moving north so im not going to recomment.
            map.getMap()[player.getYCoordinate()][player.getXCoordinate()] = currentTile;
            currentTile = map.getMap()[player.getYCoordinate()][player.getXCoordinate() - 1];
            map.getMap()[player.getYCoordinate()][player.getXCoordinate() - 1] = 'P';
            player.setXCoordinate(player.getXCoordinate() - 1);
            System.out.println("SUCCESS");
        } else {
            System.out.println("FAIL. Unsuccessful Move.");
        }
    }

    /**
     * Method allows the user to view the map in a 5 by 5 grid with the player in the middle
     */
    protected void look() {
        //for loop that loops through the the y values (starting with the two before P and ending with two after P
        for (int i = player.getYCoordinate() - 2; i < player.getYCoordinate() + 3; i++) {
            //for loop that loops through the the x values (starting with the two before P and ending with two after P
            for (int j = player.getXCoordinate() - 2; j < player.getXCoordinate() + 3; j++) {
                //checks y and x are valid coordinates. This makes the code more robust
                if (i >= 0 && i < map.getMap().length && j >= 0 && j < map.getMap()[0].length) {
                    System.out.print(map.getMap()[i][j]);
                } else {
                    //if the position is out of bounds then display it as wall.
                    System.out.print('#');
                }
            }
            //print line after end of the row
            System.out.println();
        }
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     *
     */
    protected void pickup() {
        //check if the player is on a gold tile
        if (currentTile == 'G'){
            //add 1 to the gold owned
            player.incrementGoldOwned();
            //replace 'G' with '.'
            currentTile = '.';
            System.out.println("SUCCESS. Gold Owned: " + player.getGoldOwned());
        } else {
            System.out.println("FAIL. Gold Owned: " + player.getGoldOwned());
        }
    }

    /**
     * Quits the game, shutting down the application.
     * if the player is above the exit and they have more gold than needed then they win the game.
     */
    protected void quitGame() {
        if (currentTile == 'E' && player.getGoldOwned() >= map.getGoldRequired()){
            System.out.println("Win. Congratulations! You have escaped the " + map.getMapName());
        } else {
            System.out.println("LOSE");
        }
        gameEnded = true;
    }


    /**
     * method checks if a move is valid before being executed
     *
     * @param proposedX : holds the value of the potential new x coordinate
     * @param proposedY : hold the value of the potential new y coodinate
     * @return either true or false
     */
    private boolean checkIfValidMove(int proposedX, int proposedY){
        if (map.getMap()[proposedY][proposedX] != '#'){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Create a random start position for the player
     * only accept the position if it is not a wall nor gold.
     */
    protected void createStartPosition(){
        //create the start x and y positions
        int startX = 0;
        int startY = 0;
        //this is a flag used to stop the loop and allows the code to know the position is acceptable.
        boolean acceptablePosition = false;

        //keeps seeting new x and y values until it is an acceptable position
        while (acceptablePosition == false){
            //sets the start position as a random number on the usable part of the map
            startX = rand.nextInt(map.getMap()[0].length - 1) ;
            startY = rand.nextInt(map.getMap().length - 1);

            //checks if the start position is on a gold or a wall
            if (map.getMap()[startY][startX] != 'G' && map.getMap()[startY][startX] != '#'){
                acceptablePosition = true;
                //set the current tile to where the start position is.
                currentTile = map.getMap()[startY][startX];
                //change the position in map to be 'P'
                map.getMap()[startY][startX] = 'P';
            }
        }
        //set the player coordinates to start coordinates
        player.setXCoordinate(startX);
        player.setYCoordinate(startY);
    }


    /**
     * Checks whether the player has been caught by the bot.
     *
     * @return either true or false
     */
    private boolean playerCaught(){
        //if the respective x and y coordinates of the bot and the player are the same.
        if (player.getYCoordinate() == bot.getBotY() && player.getXCoordinate() == bot.getBotX()){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * The main method of the GameLogic Class. Contains how the processes of the game run.
     *
     * @param args
     */
	public static void main(String[] args) {
        //create the object (logic) on the gameLogic class (constructor)
        GameLogic logic = new GameLogic();

        //This chooses which map is to be used. The method is located in the map class.
        logic.map.selectMap();

        //Create start position for the player
        logic.createStartPosition();

        //Create start position for the bot
        logic.bot.createBotStartPosition(logic.map);

        System.out.println("Game Started!");

        //This loops until either the player is caught or the game has ended.
        while (logic.gameRunning() == true && logic.playerCaught() == false) {

            //if it is the players turn
            if (logic.playersTurn == true) {

                //get the users command.
                logic.inputtedCommand = logic.player.getNextAction(logic.player.getInputFromConsole());

                //set to bots turn
                logic.playersTurn = false;

                //calls subroutines based off of the inputted command
                switch (logic.inputtedCommand.toUpperCase().trim()) {
                    case "HELLO":
                        logic.hello();
                        break;
                    case "GOLD":
                        logic.gold();
                        break;
                    case "MOVE N":
                        logic.move('N');
                        break;
                    case "MOVE E":
                        logic.move('E');
                        break;
                    case "MOVE S":
                        logic.move('S');
                        break;
                    case "MOVE W":
                        logic.move('W');
                        break;
                    case "PICKUP":
                        logic.pickup();
                        break;
                    case "LOOK":
                        logic.look();
                        break;
                    case "QUIT":
                        logic.quitGame();
                        break;
                    default:
                        //'default' is the switch vesion of an 'else' statement
                        System.out.println("Command not recognised");
                        break;
                }
            } else {
                //this calls the botLogic method which moves or looks for the bot.
                logic.bot.botLogic(logic.map);
                //set the players turn to true.
                logic.playersTurn = true;
            }
        }

        if (logic.playerCaught()){
            System.out.println("Bot has caught player! GAME OVER!");
            //ends the game
            logic.gameEnded = true;
        }
    }
}