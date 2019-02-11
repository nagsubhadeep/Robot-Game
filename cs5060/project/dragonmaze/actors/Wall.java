package cs5060.project.dragonmaze.actors;
/**
 * Final Project: CS 5060
 * DragonMaze Project : Wall.java
 * 
 * This Wall Class extends the Actor class and manages the Walls in a Grid
 * 
 * @author Deep A01631525
 *
 */
public class Wall extends Actor
{
	/**
	 * Method to determine if the Wall is present in a GridCell
	 * @param grid
	 * @param r row Value
	 * @param c column Value
	 * @return
	 */
	public boolean isWall(char grid[][], int r, int c)
	{
		if ((grid[r][c]) == WALL)
			return true;
		else
			return false;
	}
}
