README
Author: Subhadeep Nag (A01631525)

Details about the classes:

1) DragonMaze.java: This class contains the main method of the entire program. It displays the menus,
   and performs the actions in accordance with the choices of the user. There are two main options to play the game
a. Generating the maze automatically
b. Generating the maze manually from a file, by entering the right file name with extension

2) mazeGenerate.java: This class generates an automatic maze by using a Recursive Backtracking Algorithm.
   Recursion and Stack is used in this approach. Recursion is mostly used for determining reach-ability and setting the actors,
   while Stack and Dynamic Arrays are used as a part of the Backtracking algorithm.
   The Actors are set in such a way, that no one will overlap each other.

3) Grid.java: This class is used to represent the 2 dimensional grid

4) World.java: This class is used to represent the world consisting of all the Actors, along with the current time and time-limit.
   The World class also contains the different sections of status messages.

5) GridCell.java: This class represents a cell in each grid. It consists of an (x,y) location holding an Actor.

6) Actor.java: The Actor class is designed where the conditions for moving the Hero and Dragon are mentioned.

7) Dragon.java: In this class, the movements of Dragon are programmed.
   The A* search path-finding Algorithm is used here for finding the shortest path
   to reach the hero and the movement of the Dragon is coded by implementing a Queue data Structure.
   Recursion is used in the pathfinding Algorithm also. Dragon inherits the Actor Class.

8) Hero.java: In this class, the various movements of Hero are programmed.

9) Exit.java, Key.java, Wall.java represents the Exit, Key and Wall positions respectively in a map, while Wall.java and Exit.java inherits the Actor class.

10) Queue.java: This class contains the linked list implementation of a queue, which is used for the movement of Dragon closer to the Player.

11) Extra Features used:
	a) The player gets a key to pass the exit marked by 'k' in the map
	b) The player gets an Energy Drink to boost its steps