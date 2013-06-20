package cs5060.project.dragonmaze;
/**
 * Final Project: CS 5060
 * DragonMaze Project : Key.java
 * 
 * The Key class is used to manage the Key in the Map that is necessary to exit the map
 * 
 * @author Deep A01631525
 *
 */
public class Key
{
	private static final char KEY = 'k';
	/**
	 * Method to find if a key is present in a grid
	 * 
	 * @param grid
	 * @param r
	 * @param c
	 * @return
	 */
	public boolean isKey(char grid[][], int r, int c)
	{
		if ((grid[r][c]) == KEY)
			return true;
		else
			return false;
	}
}
