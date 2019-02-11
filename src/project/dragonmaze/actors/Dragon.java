package cs5060.project.dragonmaze.actors;
import cs5060.project.dragonmaze.*;
/**
 * 
 * Final Project: CS 5060
 * DragonMaze Project : Dragon.java
 * 
 * The Dragon Class is one of the Actors that extends the Actor class
 * and controls the movement of the Dragon
 * 
 * Algorithm source: http://www.policyalmanac.org/games/aStarTutorial.htm
 * 
 * @author Deep A01631525
 *
 */


public class Dragon extends Actor
{	
	private int count = 0;
	private int xPos, yPos;
	private char array[][];
	private char arrayCopy[][];
	private char label = 'v';
	private static final Double INFINITY = 1000000000.0;
	private Queue<GridCell> queue ;
	private boolean result;
	
	
	/**
	 * This method is used to check if there is a Dragon in a GridCell
	 * @param grid 2D array
	 * @param r row index
	 * @param c column index
	 * @return  boolean true/false if the dragoon is found or not
	 */
	public boolean isDragon(char grid[][], int r, int c)
	{
		if ((grid[r][c]) == DRAGON)
			return true;
		else
			return false;
	}
	
	/**
	 * Returns the row location of the Dragon
	 * @return row location
	 */
	private int getXPos()
	{
		return xPos;
	}
	
	/**
	 * This method is used to find the column location of a dragon
	 * @return column location
	 */
	private int getYPos()
	{
		return yPos;
	}
	
	/**
	 * This method is used to find the location of the Dragon
	 */
	private void getDragonLocation ()
	{		
		
		
		for(int i=0; i<rows; i++)
		{
			for(int j=0; j<cols; j++)
			{
				if (array[i][j] == DRAGON)
				{
						xPos = i;
						yPos = j;
						break;
				}
			}
		}
	}
	
	/**
	 * This method is used to move the Dragon in different positions
	 * @param newRow new row location to be moved
	 * @param newCol new column location to be moved
	 * @param curRow current row location to be moved
	 * @param curCol current column location to be moved
	 */
	private void move(int newRow, int newCol, int curRow, int curCol,Grid g, World w)
	{
		if(array[newRow][newCol] == WALL)
		{
			array[newRow][newCol] = WALL;
			array[curRow][curCol] = DRAGON;
			count ++;
			
			//This condition is used to break the wall with a less probability
			if(count == 3)
			{
				array[newRow][newCol] = DRAGON;
				array[curRow][curCol] = PATH;
				count = 0;
				w.dynamicStatus="The Dragon Broke the Wall";
			}
		}
		
		else if(array[newRow][newCol] == EXIT)
		{
			array[newRow][newCol] = EXIT;
			array[curRow][curCol] = DRAGON;
			w.dynamicStatus = "The Dragon is Moving near the Exit";
			count = 0;
		}
		
		else if(array[newRow][newCol] == KEY)
		{
			array[newRow][newCol] = KEY;
			array[curRow][curCol] = DRAGON;
			w.dynamicStatus = "The Dragon is moving near the Key";
			count = 0;
		}
		else if(array[newRow][newCol] == HERO)
		{
			array[newRow][newCol] = DRAGON;
			array[curRow][curCol] = PATH;
			w.dynamicStatus = "The Dragon ate the Hero";
			count = 0;
		}
		
		else
		{
			array[newRow][newCol] = DRAGON;
			array[curRow][curCol] = PATH;
			count =0;
		}
	}
	
	/**
	 * This method is manage the movement of Dragons.
	 * according to the location of the other Actors
	 * @param array 2D Array
	 * @param rows rows
	 * @param cols cols
	 * @param g Grid
	 * @param w World
	 */
	public boolean moveDragon(char[][] array, int rows, int cols, Grid g, World w)
	{
		this.rows = rows;
		this.cols = cols;
		this.array = array;
		arrayCopy = new char[rows][cols];
		queue = new Queue<GridCell>();
		result = false;
		
		//Initializing the Labeling array
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j<cols; j++)
			{
				arrayCopy[i][j]= array[i][j];
			}
		}
		
		//Find position of Dragon
		getDragonLocation ();
		
		int dX = getXPos();
		int dY = getYPos();
		
		//Find location of Hero
		Hero h = new Hero();
		h.getHeroLocation(rows, cols, array);
		int hX = h.getXPos();
		int hY = h.getYPos();
	
		//Method for Finding the path to reach Hero 
		findPath(dX,dY,hX,hY);
		
		//This GridCell is the nearest cell that the Dragon had to move on
		GridCell gc = queue.remove();
		if(gc.getValue() == HERO)
		{
			move(gc.getXLocation(), gc.getYLocation(), dX, dY,g,w);
			w.status = "FAILURE";
			result = true;
		}
		else
		{
			move(gc.getXLocation(), gc.getYLocation(), dX, dY,g,w);
			result = false;
		}
		return result;
		
		
	}
	
	/**
	 * This method is used to find the nearest path to reach the Hero
	 * by implementing the A* Search Algorithm
	 * @param dX Row Location of Dragon
	 * @param dY Column Location of Dragon
	 * @param hX Row Location of Hero
	 * @param hY Column Location of Hero
	 * @source http://www.policyalmanac.org/games/aStarTutorial.htm
	 */
	private boolean findPath(int dX, int dY, int hX, int hY)
	{
		//Base Cases
		
		//Array Out-Of-Bound conditions
		if((dX>=rows-1) || (dY>=cols-1) || (dX < 1) || (dY < 1))
			return false;
		
		//Condition if the Hero is Found
		if(array[dX][dY]==HERO)
			return true;
		
		//Condition for other Actors in the Grid
		if(array[dX][dY] == WALL || array[dX][dY] == KEY || array[dX][dY] == ENERGYDRINK|| array[dX][dY] == EXIT)
			return false;
		
		//Labeling the Matrix when a location is visited
		arrayCopy[dX][dY]= label;
		
		//Intitalizing the weights
		double costRight = 0.0, costLeft = 0.0, costUp = 0.0, costDown = 0.0;
		
		//Finding the neighbors
		//If the Array is Labeled or filled with other Actors,
		//mark it as visited and assigning an infinite cost to it
		
		//Right Neighbor
		GridCell gcRight = new GridCell(array,dX,dY+1);
		if(IsLabelled(dX,dY+1) || gcRight.getValue() == WALL || gcRight.getValue() == KEY || gcRight.getValue() == ENERGYDRINK || gcRight.getValue() == EXIT)
		{
			costRight = INFINITY;
		}
		else
		{
			arrayCopy[dX][dY+1] = label ;
			costRight = 10.0 + calculateHeuristic(gcRight.getXLocation(), gcRight.getYLocation(), hX, hY);
		}
		
		//Left Neighbor
		GridCell gcLeft = new GridCell(array,dX,dY-1);
		if(IsLabelled(dX,dY-1) || gcLeft.getValue() == WALL ||gcLeft.getValue() == KEY || gcLeft.getValue() == ENERGYDRINK || gcLeft.getValue() == EXIT)
		{
			costLeft = INFINITY;
		}
		else
		{
			arrayCopy[dX][dY-1] = label ;
			costLeft = 10.0 + calculateHeuristic(gcLeft.getXLocation(),gcLeft.getYLocation(), hX, hY);
		}
		
		//Upper Neighbor
		GridCell gcUp = new GridCell(array,dX-1,dY);
		if(IsLabelled(dX-1,dY) || gcUp.getValue() == WALL ||gcUp.getValue() == KEY || gcUp.getValue() == ENERGYDRINK || gcUp.getValue() == EXIT)
		{
			costUp = INFINITY;
		}
		else
		{
			arrayCopy[dX-1][dY] = label ;
			costUp = 10.0 + calculateHeuristic(gcUp.getXLocation(),gcUp.getYLocation(), hX, hY);
		}
		
		//Lower Neighbor
		GridCell gcDown = new GridCell(array,dX,dY+1);
		if(IsLabelled(dX,dY+1) || gcDown.getValue() == WALL ||gcDown.getValue() == KEY || gcDown.getValue() == ENERGYDRINK || gcDown.getValue() == EXIT)
		{
			costDown = INFINITY;
		}
		else
		{
			arrayCopy[dX][dY+1] = label ;
			costDown = 10.0 + calculateHeuristic(gcDown.getXLocation(),gcDown.getYLocation(), hX, hY);
		}
		
		//Determine the minimum cost
		double minCost = getMinimum(costRight, costLeft, costUp, costDown);
		
		GridCell gc = null;
		
		//Finding the GridCell with minimum cost
		if(minCost == costRight)
			gc = gcRight;
		if (minCost == costLeft)
			gc = gcLeft;
		if (minCost == costDown)
			gc = gcDown;
		if (minCost == costUp)
			gc = gcUp;
		
		//Adding the GridCell with minimum cost to a queue
		queue.add(gc);
		//get the location of the new GridCell
		dX = gc.getXLocation();
		dY = gc.getYLocation();
		
		//Recursive Solution Construction
		findPath(dX,dY,hX,hY);
		
		return false;
		
	}
	
	/**
	 * This method is used to calculate the Heuristic in an A* Search
	 * Algorithm using Manhattan Distance
	 * @param dX Row Location of a Dragon
	 * @param dY Column Location of a Dragon
	 * @param hX Row Location of a Hero
	 * @param hY Column Location of a Hero
	 * @return the Heuristic Value
	 */
	private double calculateHeuristic(int dX, int dY, int hX, int hY)
	{
		return Math.sqrt(Math.pow((hX - dX), 2)+ Math.pow((hY - dY), 2));
	}

	/**
	 * This method is used to determine if a particular GridCell is already visited or not
	 * @param dX row location of the GridCell
	 * @param dY column location of the GridCell
	 * @return true/false
	 */
	private boolean IsLabelled(int dX, int dY)
	{
		if(arrayCopy[dX][dY] == label)
			return true;
		else
			return false;
	}
	
	/**
	 * This method is used to find the minimum of all the 4 costs
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 */
	private double getMinimum(double a, double b, double c, double d)
	{
		return Math.min(Math.min(Math.min(a,b),c),d);
	}
}
