package cs5060.project.dragonmaze.actors;
import cs5060.project.dragonmaze.*;
import java.io.IOException;

/**
 * Final Project: CS 5060
 * DragonMaze Project : Actor.java
 * 
 * The Actor class maintains the various actors and their movements
 * @author Deep A01631525
 *
 */

public class Actor
{	
	protected int xPos, yPos;
	protected int rows, cols;
	private boolean Energy, Key;
	private String status;
	private boolean result, dragonMove;
	
	protected static final char DRAGON = 'D';
	protected static final char WALL = 'X';
	protected static final char HERO = 'H';
	protected static final char PATH = '.';
	protected static final char KEY = 'k';
	protected static final char ENERGYDRINK = 'e';
	protected static final char EXIT = 'E';

	/**
	 * This method is used to control the movements of the various actors
	 * in the grid
	 * @param w World
	 * @param array 2DArray
	 * @param g Grid
	 * @param r rows
	 * @param c columns
	 * @param timelimit Timelimit
	 */
	public boolean Move(World w, char[][] array, Grid g, int r, int c, int timelimit)
	{
		//Maintaining a status for game movements internally
		status = null;
		
		//Setting the rows and columns
		rows = r;
		cols = c;
		
		//Declaring new Actors
		Wall wall = new Wall();
		Dragon d = new Dragon();
		Exit e = new Exit();
		Key k = new Key();
		Hero h = new Hero();
		
		/*Initializing the actions
		 *Energy for EnergyDrink, Result for final result, dragonMove for the
		 *movement of Dragon*/
		Energy = false; Key = false; result = false; dragonMove = false;;
		
		//This segment runs unless the timelimit is reached
		while(w.time < timelimit)
		{
			char direction = ' ';
			
			//Reading the directions
			try
			{
				direction = (char)System.in.read();
			}
			
			catch (IOException ex)
			{
				ex.toString();
			}
			
			//Finding the location of Hero
			h.getHeroLocation(rows,cols,array);
			int xRow = h.getXPos();
			int yCol = h.getYPos();
			
			switch (direction)
			{
				/*
				 * u: case for upward movement
				 * d: case for down movement
				 * l: case for right movement
				 * r: case for right movement
				 * w: case for wait
				 * q: case for quitting the game
				 * 
				 */
				case 'r':
					//Array Out-Of-Bound Conditions	
					if(yCol == c-1)
					{
						if(Energy)
						{
							w.energyStepCount++;
						}
						dragonMove = d.moveDragon(array, rows, cols, g, w);
						w.time ++;
						w.display();
					}
					//Array Out-Of-Bound Conditions	for Energy steps
					else if(Energy && yCol == c-2)
					{
						w.time++;
						w.energyStepCount++;
						dragonMove = d.moveDragon(array, rows, cols, g, w);
						w.display();
					}
					
					
					else
					{
						//Checking for presence of Wall
						if(wall.isWall(array,xRow,yCol+1))
						{
							if(Energy)
							{
								w.energyStepCount++;
							}
							
							w.time++;
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							w.display();
						}
						
						if(Energy && wall.isWall(array,xRow,yCol+2))
						{
							w.energyStepCount++;
							w.time++;
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							w.display();
						}
						//Checking for the presence of key
						if(!Energy && k.isKey(array,xRow,yCol+1))
						{	
							w.time++;
							h.move(array, xRow, yCol+1, xRow, yCol,g,w);
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							Key = true;
							w.key = "YES";
							w.display();
						}
						
						if(Energy && k.isKey(array,xRow,yCol+2))
						{
							w.energyStepCount++;
							w.time++;
							h.move(array, xRow, yCol+2, xRow, yCol,g,w);
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							Key = true;
							w.key = "YES";
							w.display();
						}
						
						//Checking for the presence of Dragon
						else if(d.isDragon(array, xRow, yCol+1))
						{
							if(!Energy)
							{
								h.move(array, xRow, yCol+1, xRow, yCol,g,w);
								w.time++;
								w.status = "FAILURE";
								w.display();
								status = "FAILURE";
								
								return result;
							}
						}
						
						else if(Energy && d.isDragon(array, xRow, yCol+2))
						{
							h.move(array, xRow, yCol+2, xRow, yCol,g,w);
							w.time++;
							w.energyStepCount++;
							w.status = "FAILURE";
							w.display();
							status = "FAILURE";
							return result;
						}
						
						//Checking for the presence of Exit	
						else if(e.isExit(array, xRow, yCol+1))
						{
							if(!Energy && Key)
							{
								h.move(array, xRow, yCol+1, xRow, yCol,g,w);
								w.time++;
								w.status = "SUCCESS";
								w.display();
								status = "SUCCESS";
								return result;
							}
							
							if(!Energy && !Key)
							{
								w.time++;
								w.display();
							}
						}
						else if(Energy && e.isExit(array, xRow, yCol+2) && Key)
						{
							h.move(array, xRow, yCol+2, xRow, yCol,g,w);
							w.time++;
							w.status = "SUCCESS";
							w.display();
							status = "SUCCESS";
							return result;
						}
						
						else if (Energy && e.isExit(array, xRow, yCol+2) && !Key)
						{
							w.time++;
							w.energyStepCount++;
							w.display();
						}
						
						else
						{
							//Regular Movement with an Energy Drink
							if(Energy && !wall.isWall(array, xRow, yCol+1))
							{
								h.move(array, xRow, yCol+2, xRow, yCol, g, w);
								dragonMove = d.moveDragon(array, rows, cols, g, w);
								w.energyStepCount++;
								w.time++;
								w.display();
							}
							else
							{
								//Regular Movement without Energy Drink
								if(!Energy && array[xRow][yCol+1] == ENERGYDRINK)
								{
									Energy = true;
									h.move(array, xRow, yCol+1, xRow, yCol, g, w);
									dragonMove = d.moveDragon(array, rows, cols, g, w);
									w.dynamicStatus = "The Hero grabbed an energy Drink";
									w.time++;
									w.display();
									
								}
								if(!Energy && array[xRow][yCol+1] == PATH)
								{
									h.move(array, xRow, yCol+1, xRow, yCol, g, w);
									dragonMove = d.moveDragon(array, rows, cols, g, w);
									w.time++;
									w.display();
								}
							}
							//Get the position of Hero
							h.getHeroLocation(rows,cols,array);
							xRow = h.getXPos();
							yCol = h.getYPos();
							
						}
					}
					break;
						
				case 'l': 
					//Checking Array Out Of Bound Conditions
					if(yCol == 0)
					{
						if(Energy)
						{
							w.energyStepCount++;
						}
						dragonMove = d.moveDragon(array, rows, cols, g, w);
						w.time++;
						w.display();
					}
					if(Energy && yCol == 1)
					{
						w.time++;
						w.energyStepCount++;
						dragonMove = d.moveDragon(array, rows, cols, g, w);
						w.display();
					}
					else
					{
						//Checking for the presence of Wall
						if(wall.isWall(array,xRow,yCol-1))
						{
							if(Energy)
							{
								w.energyStepCount++;
							}
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							w.time++;
							w.display();
						}
						if(Energy && wall.isWall(array,xRow,yCol-2))
						{
							w.energyStepCount++;
							w.time++;
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							w.display();
						}
						//Checking for the presence of Key
						if(!Energy && k.isKey(array,xRow,yCol-1))
						{	
							w.time++;
							h.move(array, xRow, yCol-1, xRow, yCol,g,w);
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							Key = true;
							w.key = "YES";
							w.display();
						}
						
						if(Energy && k.isKey(array,xRow,yCol-2))
						{
							w.energyStepCount++;
							w.time++;
							h.move(array, xRow, yCol-2, xRow, yCol,g,w);
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							Key = true;
							w.key = "YES";
							w.display();
						}
						//Checking for the presence of Dragon
						else if(d.isDragon(array, xRow, yCol-1))
						{
							if(!Energy)
							{
								h.move(array, xRow, yCol-1, xRow, yCol,g,w);
								w.time++;
								w.status = "FAILURE";
								w.display();
								status = "FAILURE";
								return result;
							}
						}
						else if(Energy && d.isDragon(array, xRow, yCol-2))
						{
							h.move(array, xRow, yCol-2, xRow, yCol,g,w);
							w.time++;
							w.energyStepCount++;
							w.status = "FAILURE";
							w.display();
							status = "FAILURE";
							return result;
						}
						
						//Checking for the presence of exit
						else if(e.isExit(array, xRow, yCol-1))
						{
							if(!Energy && Key)
							{
								h.move(array, xRow, yCol-1, xRow, yCol, g,w);
								w.time++;
								w.status = "SUCCESS";
								w.display();
								status = "SUCCESS";
								return result;
							}
							
							if(!Energy && !Key)
							{
								w.time++;
								w.display();
							}
						}
						else if(Energy && e.isExit(array, xRow, yCol-2) && Key)
						{
							h.move(array, xRow, yCol-2, xRow, yCol,g,w);
							w.time++;
							w.status = "SUCCESS";
							w.display();
							status = "SUCCESS";
							return result;
						}
						else if(Energy && e.isExit(array, xRow, yCol-2) && !Key)
						{
							w.time++;
							w.energyStepCount++;
							w.display();
						}
						
						else
						{
							//Regular moves with an energy Drink
							if(Energy && !wall.isWall(array, xRow, yCol-1))
							{
								h.move(array, xRow, yCol-2, xRow, yCol, g, w);
								dragonMove = d.moveDragon(array, rows, cols, g, w);
								w.energyStepCount++;
								w.time++;
								w.display();
							}
							else
							{
								//Regular moves without an Energy Drink
								if(!Energy && array[xRow][yCol-1] == ENERGYDRINK)
								{
									Energy = true;
									h.move(array, xRow, yCol-1, xRow, yCol, g, w);
									dragonMove = d.moveDragon(array, rows, cols, g, w);
									w.time++;
									w.dynamicStatus = "The Hero grabbed an energy Drink";
									w.display();
								}
								if(!Energy && array[xRow][yCol-1] == PATH)
								{
									h.move(array, xRow, yCol-1, xRow, yCol, g, w);
									dragonMove = d.moveDragon(array, rows, cols, g, w);
									w.time++;
									w.display();
								}
							}
							//Getting the new position of Hero
							h.getHeroLocation(rows,cols,array);
							xRow = h.getXPos();
							yCol = h.getYPos();
						}
					}
					break;
						
				case 'u':
					//Checking Array Out Of Bound Conditions
					if (xRow == 0)
                    {
						if(Energy)
						{
							w.energyStepCount++;
						}
                        w.time++;
                        dragonMove = d.moveDragon(array, rows, cols, g, w);
                        w.display();
                    }
					
					else if(Energy && xRow== 1)
					{
						w.time++;
						w.energyStepCount++;
						dragonMove = d.moveDragon(array, rows, cols, g, w);
						w.display();
					}
					else
					{
						//checking for the presence of Wall
						if(wall.isWall(array,xRow-1,yCol))
						{
							if(Energy)
							{
								w.energyStepCount++;
							}
							
							w.time++;
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							w.display();
						}
						
						if((Energy && wall.isWall(array, xRow-2, yCol)))
						{
							w.energyStepCount++;
							w.time++;
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							w.display();
						}
						
						//Checking for the presence of Key
						if(!Energy && k.isKey(array,xRow-1,yCol))
						{	
							w.time++;
							h.move(array, xRow-1, yCol, xRow, yCol,g,w);
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							Key = true;
							w.key = "YES";
							w.display();
						}
						
						if(Energy && k.isKey(array,xRow-2,yCol))
						{
							w.energyStepCount++;
							w.time++;
							h.move(array, xRow-2, yCol, xRow, yCol,g,w);
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							Key = true;
							w.key = "YES";
							w.display();
						}
						//Checking for the presence of Dragon
						else if(d.isDragon(array, xRow-1, yCol))
						{
							if(!Energy)
							{
								h.move(array, xRow-1, yCol, xRow, yCol,g,w);
								w.time++;
								w.status = "FAILURE";
								w.display();
								status = "FAILURE";
								return result;
							}
						}
						
						else if(Energy && d.isDragon(array, xRow-2, yCol))
						{
							h.move(array, xRow-2, yCol, xRow, yCol,g,w);
							w.time++;
							w.energyStepCount++;
							w.status = "FAILURE";
							w.display();
							status = "FAILURE";
							return result;
						}
						
						
						//Checking for the presence of Exit
						else if(e.isExit(array, xRow-1, yCol))
						{
							if(!Energy && Key)
							{
								h.move(array, xRow-1, yCol, xRow, yCol,g,w);
								w.time++;
								w.status = "SUCCESS";
								w.display();
								status = "SUCCESS";
								return result;
							}
							if(!Energy && !Key)
							{
								w.time++;
								w.display();
							}
						}
						
						else if(Energy && e.isExit(array, xRow-2, yCol) && Key)
						{
							h.move(array, xRow-2, yCol, xRow, yCol,g,w);
							w.time++;
							w.energyStepCount++;
							w.status = "SUCCESS";
							w.display();
							status = "SUCCESS";
							return result;
						}
						
						else if(Energy && e.isExit(array, xRow-2, yCol) && !Key)
						{
							w.time++;
							w.energyStepCount++;
							w.display();
						}
						
						else
						{
							//Regular movement with an Energy Drink
							if(Energy && !wall.isWall(array, xRow-1, yCol))
							{
								h.move(array, xRow-2, yCol, xRow, yCol, g, w);
								dragonMove = d.moveDragon(array, rows, cols, g, w);
								w.energyStepCount++;
								w.time++;
								w.display();
							}
							else
							{
								//Regular movement without an Energy Drink
								if(!Energy && array[xRow -1][yCol] == ENERGYDRINK)
								{
									Energy = true;
									h.move(array, xRow-1, yCol, xRow, yCol, g, w);
									dragonMove = d.moveDragon(array, rows, cols, g, w);
									w.dynamicStatus = "The Hero grabbed an energy Drink";
									w.time++;
									w.display();
								}
								if(!Energy && array[xRow -1][yCol] == PATH)
								{
									h.move(array, xRow-1, yCol, xRow, yCol, g, w);
									dragonMove = d.moveDragon(array, rows, cols, g, w);
									w.time++;
									w.display();
								}
							}
							
							//Get the new location of Hero
							h.getHeroLocation(rows,cols,array);
							xRow = h.getXPos();
							yCol = h.getYPos();
							
						}
					}
					break;
						
				case 'd':
					//Checking the Array Out-of-Bound Conditions
					if (xRow == r - 1)
                    {
						if(Energy)
						{
							w.energyStepCount++;
						}
                        w.time++;
                        dragonMove = d.moveDragon(array, rows, cols, g, w);
                        w.display();
                    }
					else if(Energy && xRow == r-2)
					{
						w.time++;
						w.energyStepCount++;
						dragonMove = d.moveDragon(array, rows, cols, g, w);
						w.display();
					}
					else
					{
						//Checking for the presence of Wall
						if(wall.isWall(array,xRow+1,yCol))
						{
							if(Energy)
							{
								w.energyStepCount++;
							}
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							w.time++;
							w.display();
						}
						if(Energy && wall.isWall(array,xRow+2,yCol))
						{
							w.energyStepCount++;
							w.time++;
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							w.display();
						}
						
						//Checking for the presence of Key
						if(!Energy && k.isKey(array,xRow+1,yCol))
						{	
							w.time++;
							h.move(array, xRow+1, yCol, xRow, yCol,g,w);
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							Key = true;
							w.key = "YES";
							w.display();
						}
						
						if(Energy && k.isKey(array,xRow+2,yCol))
						{
							w.energyStepCount++;
							w.time++;
							h.move(array, xRow+2, yCol, xRow, yCol,g,w);
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							Key = true;
							w.key = "YES";
							w.display();
						}
						
						//Checking for the presence of Dragon
						else if(d.isDragon(array, xRow+1, yCol))
						{
							if(!Energy)
							{
								h.move(array, xRow+1, yCol, xRow, yCol,g,w);
								w.time++;
								w.status = "FAILURE";
								w.display();
								status = "FAILURE";
								return result;
							}
						}
						else if (Energy && d.isDragon(array, xRow+2, yCol))
						{

							h.move(array, xRow+2, yCol, xRow, yCol,g,w);
							w.energyStepCount++;
							w.time++;
							w.status = "FAILURE";
							w.display();
							status = "FAILURE";
							return result;
						}
						//Checking for the presence of Exit
						else if(e.isExit(array, xRow+1, yCol))
						{
							if(!Energy && Key)
							{
								h.move(array, xRow+1, yCol, xRow, yCol,g,w);
								w.time++;
								w.status = "SUCCESS";
								w.display();
								status = "SUCCESS";
								return result;
							}
							if(!Energy && !Key)
							{
								w.time++;
								w.display();
							}
						}
						else if(Energy && (e.isExit(array, xRow+2, yCol)) && Key)
						{
							h.move(array, xRow+2, yCol, xRow, yCol,g,w);
							w.time++;
							w.status = "SUCCESS";
							w.display();
							status = "SUCCESS";
							return result;
						}
						else if(Energy && (e.isExit(array, xRow+2, yCol)) && !Key)
						{
							w.time++;
							w.energyStepCount++;
							w.display();
						}
						
						else
						{
							//Regular movements with an Energy Drink
							if(Energy && !wall.isWall(array, xRow+1, yCol))
							{
								h.move(array, xRow+2, yCol, xRow, yCol, g, w);
								dragonMove = d.moveDragon(array, rows, cols, g, w);
								w.energyStepCount++;
								w.time++;
								w.display();
							}
							else
							{
								//Regular Movements without an Energy Drink
								if(!Energy && array[xRow+1][yCol] == ENERGYDRINK)
								{
									Energy = true;
									h.move(array, xRow+1, yCol, xRow, yCol, g, w);
									dragonMove = d.moveDragon(array, rows, cols, g, w);
									w.dynamicStatus = "The Hero grabbed an energy Drink";
									w.time++;
									w.display();
								}
								if(!Energy && array[xRow+1][yCol] == PATH)
								{
									h.move(array, xRow+1, yCol, xRow, yCol, g, w);
									dragonMove = d.moveDragon(array, rows, cols, g, w);
									w.time++;
									w.display();
								}
							}
							//Getting the new location of Hero
							h.getHeroLocation(rows,cols,array);
							xRow = h.getXPos();
							yCol = h.getYPos();
						}
					}
					
					break;
					
				case 'w':
					//Wait conditions with an Energy Drink
						if(Energy)
						{
							w.time++;
							w.energyStepCount++;
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							w.display();
						}
						//Wait Conditions without an energy Drink 
						else
						{
							w.time++;
							dragonMove = d.moveDragon(array, rows, cols, g, w);
							w.display();
						}
					    break;
				
				case 'q':
						status = "QUIT";
						return result;		
			}
			//Conditions for maintaining the steps with an EnergyDrink
			if (w.energyStepCount == 10)
			{
				Energy = false;
				w.dynamicStatus = "Energy Effect Over";
				w.energyStepCount = 0;
			}
			
			if(status!=null)
			{
				result = true;
				return result;
			}
			if(dragonMove == true)
			{
				result = false;
				return result;
			}
		}
		
		
		//Condition to check when the time equals timelimit
		if(w.time == timelimit)
		{
			status = "FAILURE";
			w.status = "FAILURE";
			w.display();
			result = false;
			return result;
		}
		return result;
		
	}

	
}
