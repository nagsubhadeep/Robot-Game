package cs5060.project.dragonmaze;
/**
 * Final Project: CS 5060
 * DragonMaze Project : GridCell.java
 * 
 * GridCell class represents a cell of the Grid.
 * Each cell has a location (x, y), and it can have one Actor.
 * 
 * @author Deep A01631525
 *
 */
public class GridCell
{
	private char array[][];
	private int rowValue, colValue;
	
	/**
	 * Constructor to initialize the Grid and the location
	 * @param array
	 * @param rowValue
	 * @param colValue
	 */
	public GridCell(char array[][], int rowValue, int colValue)
	{
		this.rowValue = rowValue;
		this.colValue = colValue;
		this.array=array;
	}
	
	/**
	 * Method to return the row value
	 * @return
	 */
	public int getXLocation()
	{
		return rowValue;
	}
	
	/**
	 * Method to return the column value
	 * @return
	 */
	public int getYLocation()
	{
		return colValue;
	}
	
	/**
	 * Method to find the value of a GridCell
	 * @return
	 */
	public char getValue()
	{
		char value = array[rowValue][colValue];
		return value;
	}
	
	/**
	 * Method to set the values of a GridCell
	 * @param value
	 * @return
	 */
	public char setValue(char value)
	{
		//Ensuring that it has one actor
		if(value == '.' || value == 'X'|| value == 'H' ||value == 'D' ||value == 'E')
			array[rowValue][colValue]= value;
		return value;
	}
}
