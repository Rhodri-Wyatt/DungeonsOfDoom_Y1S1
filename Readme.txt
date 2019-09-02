Read Me!

As a brave adventurer looking for Aztec Gold your travels take you to the abandoned dungeons of Mexico. After months of travelling you have found the lost city of Tancutan. Despite hearing rumours of the dungeon being guarded by a Robot you decide to risk it and hence the game begins!

The aim of the game is to pick-up enough gold required to exit the dungeon. As the player you can navigate around the dungeon searching for the gold and the exit. Although, be careful of the bot. At first it may appear to be moving around on its accord but once you go near to it it will move towards and try and catch you!

Installation Instructions:
	1.	In Linux.Bath command line, navigate to the folder named ‘Project’
	2.	Type the command ‘javac *.java’ in order to compile the program
	3.	To run the program type in the command ‘java GameLogic’
	4.	From here you will be given the option to choose a map and start the game.

Game Instructions:
	> HELLO 
		– Displays the gold needed to win the game.
	> GOLD 
		– Displays the gold the player currently has.
	> MOVE <Direction> 
		– Moves the player in the chosen direction. 
		– Direction can either be N, E, S or W
	> PICKUP 
		– Allows player to pickup the Gold on the tile they are standing on.
	> LOOK 
		– View the map as a 5 by 5 grid around the player.
	> QUIT 
		– Quits the game. 
		–  In order for the user to win they must be standing on the Exit.
		– The use can Quit while not on the exit but will lose the game.

Game Logic.
	Both the player and the bot take it in turns to have a go. The player has the first turn and each instruction/command counts as a single go. Your character in this game is represented by a ‘P’. In order to win the game you must navigate through the map moving towards Gold tiles represented as ‘G’. Once you are on the ‘G’ tile the PICKUP command will allow you to collect the gold. When you have collected all of the gold needed to win the game you can move to one of the exit tiles represented as ‘E’. Once you are on the exit tile you can type QUIT to win the game. You can also look around the maze to view were the gold and exits are. Walls of the map are denoted by ‘#’. If you bump into a wall or miss-spell a command it will still count as your turn. The bot is represented as a ‘B’ and empty tiles are denoted as a ‘.’


Map Instruction.
	One feature of the game is the ability to add maps of your own. They must be saved as a .txt file and the format of the map is as follows:

	name <Map Name>
	win <Number of gold to win>
	#######
	#.....#
	#.....#
	#######

	The walls of the map are # and must be straight on all sides i.e. each line of the map has the same number of characters. Inside the walls you can put as much gold and as many exits as you wish. Just make sure that there are at least two empty tiles for the bot and the player to be placed.
	The .txt file of the map should be stored in the Project folder.

Brief Description of Implementation.
	As an initial start to creating the project I planned out on paper exactly what each command needed to do and how to do it. I then drew up class diagrams in order to see how each class relates to one another. As I had already written pseudocode on paper of how to program each command, I was able to easily write this up in java. The GameLogic class is where the processes of each command are written. I used the iterative approach to programming. I wanted to get a simple version of the game that works then built on the program by adding features as time went on. I decided to use the HumanPlayer class to store perform any tasks that relate to the player. This included storing x and y coordinates for the player and code for allowing the user to input a command. By using the already made map from the sample code I was able to get a working version of the game and build on it. I then decided to debug my code and make it more robust before working on the extra features. 
	The next feature I implemented was the ability to read in maps from a .txt file. Overall this wasn’t hard to do but being able to view all the .txt files in the directory was a challenge. This was overcome by doing some research into how java allows the storing of files as objects. The implementing of the bot was also fairly simple as a lot of the processes where the same. The look and move methods were almost identical to that of the player. However, the one issue I faced with the bot was getting it to move automatically without any human interaction. This was achieved by giving the bot a general direction (initially west) and allowing it to move from north to south and when it hit a wall to move toward the general direction. This would mean the bot would have the effect of bouncing off the wall.
