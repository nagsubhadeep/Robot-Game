package cs5060.project.dragonmaze;

/**
 * Final Project: CS 5060
 * DragonMaze Project : World.java
 * 
 * The World class contains global information about the game,
 * including the Grid, time, etc.
 * 
 * @author Deep A01631525
 *
 */
public class World
{
	public int timelimit;
	public int time = 0;
	public int energyStepCount=0;
	public String key = "NO";
    private Grid grid;
    public String status = "";
    public String dynamicStatus = "The hero is trapped inside a maze with a terrible dragon. " +
    		"Will (s)he be able to escape...?";
    
    private char array[][];
    private int row;
    private int col ;
    
    /**
     * Constructor to initialize the variables of World
     * @param array
     * @param row
     * @param col
     * @param timelimit
     * @param grid
     */
    public World(char array[][],int row, int col, int timelimit, Grid grid)
    {
    	this.array=array;
    	this.row = row;
    	this.col = col;
    	this.timelimit = timelimit;
    	
    	this.grid = grid;
    }
   
    /**
     * Method to Display the World
     */
    public void display()
	{
		System.out.println("Time limit: " +timelimit);
		System.out.println("Current Time: "+time );
		System.out.println("Energy Drink Step Count: " +energyStepCount );
		System.out.println("Key Grabbed: "+key);
		System.out.println("Status:"+status);
		System.out.println(""+dynamicStatus);
		grid.setGrid(array);
		grid.display(row,col);
			
	}
}
