package org.francisparker.mmaunu.gameengine.testing;

import java.awt.Color;
import java.awt.Image;
import javax.imageio.ImageIO;

import org.francisparker.mmaunu.gameengine.GameFrame;

public class RisingLevelsWithGapsGame
{

	//see the constructor for the good stuff
	public static void main(String[] args)
	{
		new RisingLevelsWithGapsGame();
	}
	
	
	@SuppressWarnings("static-access")
	public RisingLevelsWithGapsGame()
	{
		Image heroImg = null;
		try
		{
			heroImg = ImageIO.read(this.getClass().getResource("/hero.png"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//System.exit(0);
		}
		
		
		//Create user-controlled characters and other game elements
		FloorHero hero = new FloorHero(100, 100, heroImg, 28, 46, 15);
		
		
		//Create a GameFrame object and add the controllable character
		GameFrame mainWindow = new GameFrame("Your Title Here", 800, 600, true, false);
		mainWindow.setControllableObject(hero);
		
		
		//Add a bunch of Floor objects to the game...getting the numbers right here
		//was a bit of math and a bit of trial and error
		for(int yCoord = 500; 
				yCoord < 500 + 5 * Floor.VERTICAL_GAP_BETWEEN_FLOORS; 
				yCoord += Floor.VERTICAL_GAP_BETWEEN_FLOORS)
		{
			//Use helper method in Floor that creates two floor objects at the same height
			//but separated by a gap...the logic is in the Floor method...check it out
			//if you are so inclined
			Floor[] pair = Floor.makePairOfFloorsWithGap(yCoord, Floor.VERTICAL_SPEED_FOR_FLOOR);
			
			mainWindow.addDrawableObject(pair[0]);
			mainWindow.addDrawableObject(pair[1]);
		}
		
		
		//set a few settings
		mainWindow.setGameBackground(Color.red);
		mainWindow.setScoreBackground(Color.white);
		mainWindow.setNumberLivesBackground(Color.white);
		mainWindow.setFPS(24);
		
		//show the game
		mainWindow.setVisible(true);
	}
	
}
