package cs5060.project.dragonmaze;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
/**
 * Final Project: CS 5060
 * DragonMaze Project : mazeGenerate.java
 * 
 * This class generates a Maze using Recursive BackTracker algorithm
 * Algorithm Source: http://en.wikipedia.org/wiki/Maze_generation_algorithm#Recursive_backtracker
 * 
 * @author Deep A01631525
 *
 */

public class mazeGenerate
{
	private int rows, cols;
	private char label = 'l', label1 = 'l';
	private char maze[][], arrayCopy[][],arrayReach[][];
	private Random rand = new Random();
	private Stack<GridCell>s;
	private boolean result;
	private GridCell selectedNeighbor;
	
	private static final char DRAGON = 'D';
	private static final char WALL = 'X';
	private static final char HERO = 'H';
	private static final char PATH = '.';
	private static final char KEY = 'k';
	private static final char ENERGYDRINK = 'e';
	private static final char EXIT = 'E';

	/**
	 * Contructor to initialize the variables of this class
	 * @param rows
	 * @param cols
	 */
	
	public mazeGenerate(int rows, int cols)
	{
		this.rows = rows;
		this.cols =cols;
		maze = new char[rows][cols];
		arrayCopy = new char [rows][cols];
		arrayReach = new char [rows][cols];
		s = new Stack<GridCell>();
		result = false;
	}
	
	/**
	 * This method is used to generate a Maze using Recursive BackTracking Algorithm
	 *
	 * @source http://en.wikipedia.org/wiki/Maze_generation_algorithm#Recursive_backtracker
	 */
	public char[][] generateMaze()
	{
		//Initializing the blank maze
		for(int i =0; i<rows; i++)
		{
			for(int j = 0; j<cols;j++)
			{
				maze[i][j] = PATH;
			}
		}
		
	
		//Copying the array to labeled matrix
		for(int i =0; i<rows; i++)
		{
			for(int j = 0; j<cols;j++)
			{
				arrayCopy[i][j] = maze[i][j];
			}
		}
		

		//Setting the labels for boundaries
		for(int i1 = 0; i1< rows; i1++)
		{
			for(int j1 = 0; j1<cols; j1++)
			{
				arrayCopy[0][j1] = label;
				arrayCopy[i1][0] = label;
				arrayCopy[i1][cols-1] = label;
				arrayCopy[rows-1][j1] = label;
			}
		}
		
		//Initializing the path to traverse
		int r = 1;
		int c = 1;
		
		
		//Initializing a GridCell
		GridCell current = new GridCell(maze,r,c);
		current.setValue(WALL);
		
		//Find the total number of GridCells
		int total = rows*cols;
		
		//Initializing the visibility count
		int visited = 1;
		
		//BackTracking Algorithm implemented here
		while(visited<total)
		{
			//Array Out Of bound Conditions
			if((r>=rows-1) || (c>=cols-1) || (r < 1) || (c < 1))
				continue;
			/*
			 * 	If the current cell has any neighbours which have not been visited
			 *	Choose randomly one of the unvisited neighbours
			 *	Push the current cell to the stack
			 *	Remove the wall between the current cell and the chosen cell
			 *	Make the chosen cell the current cell and mark it as visited
			 */
			if(neighborsUnvisited(r,c))
			{
				randomSelection(current);
				s.push(current);
				selectedNeighbor.setValue(current.getValue());
				
				current = selectedNeighbor;
				visited++;
			}
			/*
			 * 	Else if stack is not empty
			 *	Pop a cell from the stack
			 *	Make it the current cell
			 */
			else if(!s.isEmpty())
			{
				current = s.pop();
			}
			/*
			 * Else
			 *	Pick a random cell, make it the current cell and mark it as visited
			 */
			else
			{
				r = rand.nextInt(rows-2)+1;
				c = rand.nextInt(rows-2)+1;
				current = new GridCell(maze,r,c);
				visited++;
			}
		}
		
		//Setting the boundaries
		for(int i1 = 0; i1< rows; i1++)
		{
			for(int j1 = 0; j1<cols; j1++)
			{
				maze[0][j1] = WALL;
				maze[i1][0] = WALL;
				maze[i1][cols-1] = WALL;
				maze[rows-1][j1] = WALL;
			}
		}
		
		//Setting the Reachability matrix
		for(int m = 0; m< rows; m++)
		{
			for(int n = 0; n< cols; n++)
			{
				arrayReach[m][n]=maze[m][n];						
			}
		}
		
		//Setting the Hero
		int u = (rand.nextInt(rows-3)+1);
		int v = (rand.nextInt(cols-3)+1);
		
		setHero(u,v);
		
		//Setting the Exit
		result = false;
		for(int m = 0; m< rows; m++)
		{
			for(int n = 0; n< cols; n++)
			{
				arrayReach[m][n]=maze[m][n];						
			}
		}
		setExit(u,v);
		
		//Setting the EnergyDrink
		result = false;
		for(int m = 0; m< rows; m++)
		{
			for(int n = 0; n< cols; n++)
			{
				arrayReach[m][n]=maze[m][n];						
			}
		}
		setEnergyDrink(u,v);
		
		//Setting the Key
		result = false;
		for(int m = 0; m< rows; m++)
		{
			for(int n = 0; n< cols; n++)
			{
				arrayReach[m][n]=maze[m][n];						
			}
		}
		setKey(u,v);
		
		//Setting the Dragon
		result = false;
		for(int m = 0; m< rows; m++)
		{
			for(int n = 0; n< cols; n++)
			{
				arrayReach[m][n]=maze[m][n];						
			}
		}
		setDragon(u,v);
		
		return maze;
		
	}

	/**
	 * Method used to select the random Neighbors
	 * @param g
	 */
	private void randomSelection(GridCell g)
	{
		
			int r = g.getXLocation();
			int c = g.getYLocation();
			
			//Maintaining the labeled matrix
			char nRight = arrayCopy[r][c+1];
			char nLeft = arrayCopy[r][c-1];
			char nUp = arrayCopy[r-1][c];
			char nDown = arrayCopy[r+1][c];
			
			//Initializing a Dynamic array of GridCells
			ArrayList<GridCell> gc = new ArrayList<GridCell>();
			GridCell Right = new GridCell(maze,r,c+1);
			GridCell Left = new GridCell(maze,r,c-1);
			GridCell Up = new GridCell(maze,r-1,c);
			GridCell Down = new GridCell (maze,r+1,c);
			
			//Adding the neighbors to the dynamic array, if they are not visited
			if(nRight!=label)
			{
				gc.add(Right);
			}
			if(nLeft!=label)
			{
				gc.add(Left);
			}
			if(nUp!=label)
			{
				gc.add(Up);
			}
			if(nDown!=label)
			{
				gc.add(Down);
			}
			//Generating a random number for selecting the visited Neighbors
			int random = rand.nextInt(gc.size());
			selectedNeighbor = gc.get(random);
		
	}

	/**
	 * Method to determine if any of the neighbors are Unvisited or not
	 * @param r
	 * @param c
	 * @return
	 */
	private boolean neighborsUnvisited(int r, int c)
	{
		if((arrayCopy[r][c+1] != label || arrayCopy[r][c-1] != label
				||arrayCopy[r-1][c] != label || arrayCopy [r+1][c] != label))
				return true;
		else
			return false;
	}
	
	/**
	 * Method to set a Hero in the Grid
	 * @param u
	 * @param v
	 */
	private void setHero(int u, int v)
	{

		if(maze[u][v] == HERO)
			return;
		
		else if(maze[u][v]!=WALL)
			maze[u][v] = HERO;
		
		else
		{
			u = (rand.nextInt(rows-2)+1);
			v = (rand.nextInt(cols-2)+1);
			
			setHero(u,v);
		}
		
	}
	
	/**
	 * Method to set a Exit in the Grid
	 * @param u
	 * @param v
	 */
	
	private void setExit(int u, int v)
	{

		if(maze[u][v] == EXIT)
			return;
		
		else if(maze[u][v]==PATH && 
				isReachable(u, v, EXIT, label1))
			maze[u][v] = EXIT;
		
		else
		{
			u = (rand.nextInt(rows-2)+1);
			v = (rand.nextInt(cols-2)+1);
			
			setExit(u,v);
		}
		
	}
	
	/**
	 * Method to set an Energy Drink in the Grid
	 * @param u
	 * @param v
	 */
	private void setEnergyDrink(int u, int v)
	{

		if(maze[u][v] == ENERGYDRINK)
			return;
		
		else if(maze[u][v]==PATH&&
				isReachable(u, v, ENERGYDRINK, label1))
			maze[u][v] = ENERGYDRINK;
		
		else
		{
			u = (rand.nextInt(rows-2)+1);
			v = (rand.nextInt(cols-2)+1);
			
			setEnergyDrink(u,v);
		}
		
	}
	
	/**
	 * Method to set a Key in the Grid
	 * @param u
	 * @param v
	 */
	private void setKey(int u, int v)
	{
		if(maze[u][v] == KEY)
			return;
		
		else if(maze[u][v]==PATH&&
				isReachable(u, v, KEY, label1))
			maze[u][v] = KEY;
		
		else
		{
			u = (rand.nextInt(rows-2)+1);
			v = (rand.nextInt(cols-2)+1);
			
			setKey(u,v);
		}
		
	}
	
	/**
	 * Method to set a Dragon in the Grid
	 * @param u
	 * @param v
	 */
	
	private void setDragon(int u, int v)
	{
		
		if(maze[u][v] == DRAGON)
			return;
		else if(maze[u][v] == PATH&& isReachable(u, v, DRAGON, label1))
			maze[u][v] = DRAGON;
		else
		{
			u = (rand.nextInt(rows-2))+1;
			v = (rand.nextInt(cols-2))+1;
			
			setDragon(u,v);
		}
	}
	
	/**
	 * Method to determine Map Reachability
	 * @param r row index
	 * @param c column index
	 * @param actor
	 * @param label1
	 * @return
	 */
	private   boolean isReachable(int r, int c, char actor,
			char label1)
	{

		//Base Cases
		//Array Out-Of-Bound
		if((r>=rows-1) || (c>=cols-1) || (r < 1) || (c < 1))
			return false;
		
		
		if (maze[r][c] == HERO) // condition if destination is reached
		{
			result = true;
		}
	
			
		if (maze[r][c] == WALL ||arrayReach[r][c]== label) /* condition 
										if there is WALL or label in the path*/
		{
			return false;
		}
		
		//condition for the path of movement
		if(maze[r][c]==actor || maze[r][c] == PATH)
		{
			arrayReach[r][c] = label1;
		}
		
		
		//Recursion Solution Construction using 4-pixel connectivity
		
		isReachable(r,c+1,actor,label1);
		isReachable(r-1,c,actor,label1);
		isReachable(r,c-1,actor,label1);
		isReachable(r+1,c,actor,label1);
		
		return result;
	}

}
