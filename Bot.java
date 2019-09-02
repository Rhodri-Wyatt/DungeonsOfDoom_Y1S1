import java.util.Random;

/**
 * performs all the logic of the bot.
 * bot chases the player and if the bot catches the player the game is over
 */


public class Bot {
    //coordinates for the bot
    int botX = 0;
    int botY = 0;

    //coordinates for where the bot thinks the player is
    int playerX = -1;
    int playerY = -1;

    boolean playerCatched = false;
    int moveCount = 0;
    char botCurrentTile;

    //ahead moves north and south
    char ahead = 'N';
    //generalDirection moves east and west
    char generalDirection = 'W';

    //define random number object to be used in setting the initial player position
    Random rand = new Random();


    //accessors
    protected int getBotX(){
        return botX;
    }

    protected int getBotY() {
        return botY;
    }


    /**
     * Creates a start position for the bot.
     * Will not let the the start position to be on the player or on the wall.
     * @param : map
     */
    //pass map so that it can be used in the subroutine
    protected void createBotStartPosition(Map map) {
       //this is a guard to make the code more robust
        boolean botAcceptablePosition = false;

        //keeps setting new x and y values until it is an acceptable position
        while (botAcceptablePosition == false) {
            //sets the start position as a random number on the usable part of the map
            botX = rand.nextInt(map.getMap()[0].length - 1);
            botY = rand.nextInt(map.getMap().length - 1);

            //Code checks if the start position is on a gold
            // Note X and y are different in a 2d array as what you'd expect. read it as y then x
            if (map.getMap()[botY][botX] != 'P' && map.getMap()[botY][botX] != '#') {
                botAcceptablePosition = true;
                botCurrentTile = map.getMap()[botY][botX];
                //changes this position in the map to equal B
                map.getMap()[botY][botX] = 'B';
            }
        }
    }

    /**
     * botLogic moves the bot 4 times then lets the bot look once.
     * @param : map
     */
    protected void botLogic(Map map) {
        if (moveCount < 4){

            moveBot(map);
            moveCount ++;
        } else {
            botLook(map);
            //resets the moveCount
            moveCount = 0;
        }
    }

    /**
     * code allows bot to look around in a 5 by 5 grid.
     * @param : map
     */
    protected void botLook(Map map){
        //loops through y values
        for (int y = botY - 2; y < botY + 3; y++) {
            //loops through x values
            for (int x = botX - 2; x < botX + 3; x++) {
                //this checks that the x and y coordinate is a real position in the map before the trying to access it.
                // this is a form of error management in order to make the code more robust
                if (y >= 0 && y < map.getMap().length && x >= 0 && x < map.getMap()[0].length ) {
                    // set player coordinates if player is found
                    if (map.getMap()[y][x] == 'P') {
                        playerY = y;
                        playerX = x;
                        System.out.println("Watch out! Bot has spotted player!");
                    }
                }
            }
        }
    }


    /**
     * move bot moves the bot in a particular sequence.
     * @param : map
     */

    protected void moveBot(Map map) {

       /*
       Move Sequence that the bot follows:
       1) if player coordinates are known move towards the coordinates
       2) else
            a) move ahead (start as North)
                i) if the tile ahead (North) is a wall then move generalDirection (start as West) 1 tile and set ahead to South
                    - if west is a wall then change generalDirection to East (and reverse if east is wall)
                ii) change ahead to south
            b) move (ahead) South
                i) if the tile (ahead) South is a wall then move gengeralDirection 1 space
                    - if west is a wall then change generalDirection to East (and reverse if east is wall)
                ii) change ahead to north
        */


        //bot will move to last spotted posjtion of player
        if (playerX > -1 && playerY > -1) {
            //if player is North of bot then move bot north (decrement y coord)
            if (map.getMap()[botY - 1][botX] != '#' && playerY < botY) {
                moveBotNorth(map);
            }
            //if player is South of bot then move bot south (increment y coord)
            else if (map.getMap()[botY + 1][botX] != '#' && playerY > botY) {
               moveBotSouth(map);
            }
            //if player is East of bot then move bot east (increment x coord)
            else if (map.getMap()[botY][botX + 1] != '#' && playerX > botX) {
                moveBotEast(map);
            }
            //if player is West of bot then move bot west (decrement x coord)
            else if (map.getMap()[botY][botX - 1] != '#' && playerX < botX) {
               moveBotWest(map);
            }

            //this keeps the bot moving once it has reached where the player used to be.
            if (botY == playerY && botX == playerX){
                playerY = -1;
                playerY = -1;
            }

        } else {
            //try move ahead
            if (ahead == 'N') {
                //if the space ahead of the bot is a wall then
                if (map.getMap()[botY - 1][botX] == '#') {
                    //change ahead to south
                    ahead = 'S';
                    //move in general direction
                    if (generalDirection == 'W') {
                        // moves west if no wall
                        if (map.getMap()[botY][botX - 1] == '#') {
                            generalDirection = 'E';
                            //bot doesn't actually move it just changes the direction
                        } else {
                            //move bot west
                           moveBotWest(map);
                        }
                    } else if (generalDirection == 'E') {
                        // moves east if no wall
                        if (map.getMap()[botY][botX + 1] == '#') {
                            generalDirection = 'W';
                            //bot doesn't actually move it just changes the direction
                        } else {
                            //move bot East
                            moveBotEast(map);
                        }
                    }
                } else {
                    //moves the bot north
                    moveBotNorth(map);
                }
            }
            //this code is now repeated for if ahead is S
            else if (ahead == 'S'){
                if (map.getMap()[botY + 1][botX] == '#') {
                    //change ahead to south
                    ahead = 'N';
                    //move in general direction
                    if (generalDirection == 'W') {
                        // moves west if no wall
                        if (map.getMap()[botY][botX - 1] == '#') {
                            generalDirection = 'E';
                            //bot doesn't actually move it just changes the direction
                        } else {
                            //move bot west
                            moveBotWest(map);
                        }
                    } else if (generalDirection == 'E') {
                        // moves east if no wall
                        if (map.getMap()[botY][botX + 1] == '#') {
                            generalDirection = 'W';
                            //bot doesn't actually move it just changes the direction
                        } else {
                            //move bot East
                            moveBotEast(map);
                        }
                    }
                } else {
                    //moves the bot north
                    moveBotSouth(map);
                }
            }
        }
    }

    /**
     * move the bot north
     * @param : map
     */
    protected void moveBotNorth(Map map) {
        //change the current position of the bot to store Bot current tile
        map.getMap()[botY][botX] = botCurrentTile;
        //change the new tile to store current bot
        botCurrentTile = map.getMap()[botY - 1][botX];
        //change the location in the map to 'B'
        map.getMap()[botY - 1][botX] = 'B';
        //update the x coord for the bot
        botY = botY - 1;
    }

    /**
     * move the bot east
     * @param : map
     */
    protected void moveBotEast(Map map) {
        //change the current position of the bot to store Bot current tile
        map.getMap()[botY][botX] = botCurrentTile;
        //change the new tile to store current bot
        botCurrentTile = map.getMap()[botY][botX + 1];
        //change the location in the map to 'B'
        map.getMap()[botY][botX + 1] = 'B';
        //update the x coord for the bot
        botX = botX + 1;
    }

    /**
     * move the bot south
     * @param : map
     */
    protected void moveBotSouth(Map map) {
        //change the current position of the bot to store Bot current tile
        map.getMap()[botY][botX] = botCurrentTile;
        //change the new tile to store current bot
        botCurrentTile = map.getMap()[botY + 1][botX];
        //change the location in the map to 'B'
        map.getMap()[botY + 1][botX] = 'B';
        //update the x coord for the bot
        botY = botY + 1;
    }

    /**
     * move the bot west
     * @param : map
     */
    protected void moveBotWest(Map map) {
        //change the current position of the bot to store Bot current tile
        map.getMap()[botY][botX] = botCurrentTile;
        //change the new tile to store current bot
        botCurrentTile = map.getMap()[botY][botX - 1];
        //change the location in the map to 'B'
        map.getMap()[botY][botX - 1] = 'B';
        //update the x coord for the bot
        botX = botX - 1;
    }
}