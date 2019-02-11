package cs5060.project.dragonmaze;
import cs5060.project.dragonmaze.actors.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Final Project: CS 5060
 * DragonMaze Project : DragonMaze.java
 * 
 * DragonMaze class runs the core functionality of the Game.
 * The Game consists of a World consisting of various Actors.
 * 
 * The Hero has to escape from the Dragon and exit through the
 * door before the game expires
 * 
 * There are 2 modes for the Game: an Automatic Maze Generation,
 * and loading the Game from the File
 *  
 * @author Subhadeep Nag (A01631525)
 *
 *
 */
public class DragonMaze
{

	private static Scanner input = new Scanner(System.in); 
	private static Random rand = new Random();
	public static void main(String[] args) throws IOException
	{		
		System.out.println("Welcomed to the DragonHero Game");
		
		int choice = 0;
		boolean IsOver = false;
		while (!IsOver)
		{
			choice = getChoice();
			
			int rows = 0, cols = 0, timelimit = 0;
			char array[][];
			Grid grid; World world; Actor actor;
			
			switch(choice)
			{
				/*
				 * Case for generating an automatic Maze
				 */
				case 1:	
						try
						{
							System.out.println("Press 1 for easy, 2 for medium, 3 for hard.");
							
							int in = input.nextInt();
							if(in == 1)
							{
								rows = Math.abs(rand.nextInt(20)+20);
								cols = Math.abs(rand.nextInt(20)+20);
								timelimit = Math.abs(rand.nextInt(20)+50);
							}
							else if (in == 2)
							{
								rows = Math.abs(rand.nextInt(30)+20);
								cols = Math.abs(rand.nextInt(30)+20);
								timelimit = Math.abs(rand.nextInt(10)+40);
							}
							else if (in == 3)
							{
								rows = Math.abs(rand.nextInt(40)+20);
								cols = Math.abs(rand.nextInt(40)+20);
								timelimit = Math.abs(rand.nextInt(10)+30);
							}
							else
							{
								System.out.println("Invalid Input");
								continue;
							}
							
							//Method to generate mazes
							mazeGenerate maze = new mazeGenerate(rows,cols);
							array = maze.generateMaze();
							
							grid = new Grid();
							world = new World(array , rows, cols, timelimit, grid);
							actor = new Actor();
							world.display();
							
							IsOver = actor.Move(world, array, grid, rows, cols, timelimit);
							continue;
							
						}
						catch(IllegalArgumentException e)
						{
							System.out.println("Please try again Later");
							continue;
						}
						catch(InputMismatchException e)
						{
							System.out.println(e.toString());
							continue;
						}
						
				case 2: 
					/*
					 * Case for reading a Map from a file
					 */
						try
						{
							System.out.println("Enter the name of the file with extension");
							String s = input.next();
							
							String newString = "maps/"+s;

							grid = new Grid();
							array = grid.load(newString.trim());
							rows = grid.getRows();
							cols = grid.getColumns();
							timelimit = grid.getTimelimit();
							
							world= new World(array , rows, cols, timelimit, grid);
							actor = new Actor();
							world.display();
							
							IsOver = actor.Move(world, array, grid,rows,cols,timelimit);
							continue;
						}
						catch (FileNotFoundException f)
						{
							System.out.println("The entered file is not found");
							System.out.println();
							continue;
						}
				case 3:
					/*
					 * Case for Help and Instructions
					 */
						Help help = new Help();
						help.display();
						System.out.println();
						continue;
						
				case 4:
					/*
					 * Case for the About Us Section
					 */
						System.out.println("About Us");
						System.out.println("Name: Subhadeep Nag");
						System.out.println("A#: A01631525");
						System.out.println("Version: 1.0");
						System.out.println("Date: 12/6/2012");
						System.out.println();
						continue;
				case 5:
					/*
					 * Case to Quit the menu 
					 */
						System.out.println("GoodBye");
						System.out.println();
						System.exit(0);
						
			}
		}		
	}
	
	/**
	 * A method to select the choices in a menu
	 * @return choice
	 */
	private static int getChoice()
	{
		int choice= 0;
		while (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice!=5)
		{
			System.out.println("Please enter your choices");
			System.out.println("1: Generate Your Own Maze");
			System.out.println("2: Load Maze from File");
			System.out.println("3: Help with Instructions");
			System.out.println("4: About");
			System.out.println("5: Quit the menu");
		
			choice = input.nextInt();
			if (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice!=5)
			{
				System.out.println("Incorrect Choice. Please Try Again");
				System.out.println();
			}
		}
		return choice;
	}
}
