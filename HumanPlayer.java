import java.io.*;
/**
 * Runs the game with a human player and contains code needed to read inputs.
 * Any methods relating to the player is stored in this class.
 */

public class HumanPlayer {
    //variables to store the location of x and y
    private int xCoordinate;
    private int yCoordinate;
    //stores the amount of gold owned by the player
    private int goldOwned = 0;

    //accessors for coordinates
    protected int getXCoordinate(){
        return xCoordinate;
    }

    protected  int getYCoordinate(){
        return yCoordinate;
    }

    protected int getGoldOwned(){
        return goldOwned;
    }


    //mutators for coordinates
    protected void setXCoordinate(int newXCoordinate){
        xCoordinate = newXCoordinate;
    }

    protected void setYCoordinate(int newYCoordinate){
        yCoordinate = newYCoordinate;
    }


    /**
     * Reads player's input from the console and stores it as a command.
     *
     * @return : A string containing the input the player entered.
     */
    protected String getInputFromConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //tries to process the command
        try {
            //Keep on accepting input from the command-line
            while(true) {
                //reads the line and stores it as command
                String command = reader.readLine();

                //Otherwise, return command
                return command;
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return null;
        }
    }

    /**
     * Processes the command. It should return a reply in form of a String, as the protocol dictates.
     * Otherwise it should return the string "Invalid".
     * It checks that the command entered is valid.
     *
     * @param command : the inputted command from the user
     * @return : Processed output or Invalid if the @param command is wrong.
     */
    protected String getNextAction(String command) {
        //changes everything to uppercase and gets rid of any whitespace before or after the command
        command = command.toUpperCase().trim();
        if (command.equals("HELLO") || command.equals("GOLD") || command.equals("MOVE N") || command.equals("MOVE S") || command.equals("MOVE E") || command.equals("MOVE W") || command.equals("PICKUP") || command.equals("LOOK") || command.equals("QUIT")) {
            return command;
        } else {
            return "Invalid";
        }
    }

    //method increments the gold owned by the player
    protected void incrementGoldOwned (){
        goldOwned++;
    }

}