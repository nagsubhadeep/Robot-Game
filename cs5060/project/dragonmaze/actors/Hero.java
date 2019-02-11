package cs5060.project.dragonmaze.actors;
import cs5060.project.dragonmaze.*;
/**
 * Final Project: CS 5060
 * DragonMaze Project : Hero.java
 * 
 * The Hero class is controlled by the player, and also
 * defines the various movements of the Player
 * 
 * @author Deep A01631525
 *
 */
public class Hero extends Actor
{
	/**
	 * Method to find the location of the Hero
	 * @param rows
	 * @param cols
	 * @param array
	 */
	protected void getHeroLocation (int rows,int cols, char[][] array)
	{		
		for(int i=0; i<rows; i++)
		{
			for(int j=0; j<cols; j++)
			{
				if (array[i][j] == HERO)
				{
						xPos = i;
						yPos = j;
						break;
				}
			}
		}
	}
	
	/**
	 * Method to find the row value of the Hero
	 * @return
	 */
	protected int getXPos()
	{
		return xPos;
	}
	
	/**
	 * Method to find the column value of the Hero
	 * @return
	 */
	protected int getYPos()
	{
		return yPos;
	}
	
	/**
	 * Method to move the Hero to the new position
	 * @param array
	 * @param newRow New row position to be moved to
	 * @param newCol New Column Position to be moved to
	 * @param curRow Current Row index
	 * @param curCol Current Column index
	 * @param g Grid
	 * @param w World
	 */
	protected void move(char array[][], int newRow, int newCol, int curRow, int curCol, Grid g, World w)
	{
		if(array[newRow][newCol] == WALL)
		{
			array[newRow][newCol] = WALL;
			array[curRow][curCol] = HERO;
		}
		
		else if(array[newRow][newCol] == DRAGON)
		{
			array[newRow][newCol] = DRAGON;
			array[curRow][curCol] = PATH;
			w.dynamicStatus = "The Dragon ate the Hero";
		}
		
		else if(array[newRow][newCol] == EXIT)
		{
			array[newRow][newCol] = EXIT;
			array[curRow][curCol] = PATH;
			w.dynamicStatus = "The Hero went out successfully";
		}
		
		else if(array[newRow][newCol] == KEY)
		{
			array[newRow][newCol] = HERO;
			array[curRow][curCol] = PATH;
			w.dynamicStatus = "The Hero grabbed the key to exit";
		}
		
		else
		{
			array[newRow][newCol] = HERO;
			array[curRow][curCol] = PATH;
			w.dynamicStatus = "The Hero is trapped by the dragon";
		}	
	}
}
