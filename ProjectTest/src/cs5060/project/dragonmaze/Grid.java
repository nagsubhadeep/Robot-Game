package cs5060.project.dragonmaze;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Final Project: CS 5060
 * DragonMaze Project : Grid.java
 * 
 * The Grid Class contains a description of the world.
 * @author Deep A01631525
 *
 */

public class Grid
{
	
	private int timelimit, rows, cols;
	public char array[][];
	
	/**
	 * This method is used to load the file
	 * @param s Name of the file
	 * @return Grid
	 * @throws IOException
	 */
	public char [][] load(String s) throws IOException
	{
		FileReader fr = new FileReader(s);
		BufferedReader br = new BufferedReader(fr);
		
		String line = br.readLine();  //priming read
		

		String[] entries = new String[line.length()];
        entries = line.split(" ");
        
        //Rows, Columns and time-limit is read from the file
        rows = Integer.parseInt(entries[0]);
        cols = Integer.parseInt(entries[1]);
        timelimit = Integer.parseInt(entries[2]);
        
        System.out.println(rows+" "+cols+" "+timelimit);
        
        
		char array[][]= new char[rows][cols];
		
        for (int i = 0; i < rows; i++)
        {
            line = br.readLine();
            if (line != null)
            {
            	char [] chararray = new char[line.length()];
                chararray = line.toCharArray();
                for (int j = 0; j < cols; j++)
                {
                    array[i][j] =chararray[j];
                    
                    
                }
            }
        }
		return array;
	
	}
	
	/**
	 * This method is used to get the number of rows
	 * @return
	 */
	public int getRows()
	{
		return rows;
	}
	
	/**
	 * This method is used to get the number of columns
	 * @return
	 */
	public int getColumns()
	{
		return cols;
	}
	
	/**
	 * This method is used to get the timelimit
	 * @return
	 */
	public int getTimelimit()
	{
		return timelimit;
	}
	
	/**
	 * This method is used to set the Grid
	 * @param values
	 */
	public void setGrid(char values[][])
	{
		array = values;	
	}
	
	/**
	 * This method is used to display the grid
	 * @param rows
	 * @param cols
	 */
	public void display(int rows, int cols)
	{		
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				System.out.print(array[i][j]);
			}
			System.out.println();
		}
	}
}
