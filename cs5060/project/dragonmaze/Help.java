package cs5060.project.dragonmaze;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Final Project: CS 5060
 * DragonMaze Project : Help.java
 * 
 * This class generates the instruction sheet
 * @author Deep A01631525
 *
 */
public class Help
{
	/**
	 * Method to display an Instruction File
	 * @throws IOException
	 */
	public void display() throws IOException
	{
		FileReader fr = new FileReader("Instructions.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		while(line!=null)
		{
			System.out.println(line);
			line = br.readLine();
		}
		br.close();
		
	}
}
