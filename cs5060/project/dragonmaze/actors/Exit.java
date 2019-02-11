package cs5060.project.dragonmaze.actors;
/**
 * Final Project: CS 5060
 * DragonMaze Project : Exit.java
 * 
 * This Exit Class extends the Actor class and manages the Exit in a Grid
 * 
 * @author Deep A01631525
 *
 */
public class Exit extends Actor
{
	/**
	 * This method determines if there is an Exit in a GridCell
	 * @param grid
	 * @param r row index
	 * @param c column index
	 * @return
	 */
	public boolean isExit(char grid[][], int r, int c) 
	{
		if (grid[r][c] == EXIT)
			return true;
		else
			return false;
	}
}
