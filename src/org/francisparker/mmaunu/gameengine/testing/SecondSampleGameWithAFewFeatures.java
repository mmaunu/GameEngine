package org.francisparker.mmaunu.gameengine.testing;

import java.awt.Color;
import java.awt.Image;
import javax.imageio.ImageIO;

import org.francisparker.mmaunu.gameengine.GameFrame;

public class SecondSampleGameWithAFewFeatures
{

	//This just constructs one of these objects...the constructor is where the fun is at
	public static void main(String[] args)
	{
		new SecondSampleGameWithAFewFeatures();
	}
	
	
	@SuppressWarnings("static-access")
	public SecondSampleGameWithAFewFeatures()
	{
		Image heroImg = null, baddyImg = null;
		try
		{
			//read images relative the this class' location
			heroImg = ImageIO.read(this.getClass().getResource("/hero.png") );
			baddyImg = ImageIO.read(this.getClass().getResource("/baddy.png") );
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//System.exit(0);
		}
		
		//construct our custom hero object
		HeroWithExtraKeyDetection hero = new HeroWithExtraKeyDetection(100, 100, 
					heroImg, 28, 46, 15);
		
		//construct some "baddies" to interact with
		BaddyWithCollisionDetection baddy1 = new BaddyWithCollisionDetection(200, 250, 
					baddyImg, 25, 50, 5);
		BaddyWithCollisionDetection baddy2 = new BaddyWithCollisionDetection(300, 100, 
					baddyImg, 25, 50, 5);
		BaddyWithCollisionDetection baddy3 = new BaddyWithCollisionDetection(400, 400, 
					baddyImg, 25, 50, 5);
		
		//construct the main game
		GameFrame mainWindow = new GameFrame("Your Title Here", 600, 600, true, true);
		
		//add the hero and other elements to the game
		mainWindow.setControllableObject(hero);
		mainWindow.addDrawableObject(baddy1);
		mainWindow.addDrawableObject(baddy2);
		mainWindow.addDrawableObject(baddy3);
		
		//IMPORTANT!!! This allows the "hero" class to react to key events like the user
		//pressing the spacebar. Without adding the KeyListener, you will only get the 
		//WASD or arrow keys to work (since that is in the Game Engine code).
		mainWindow.addKeyListener(hero);
		
		//set a few settings
		mainWindow.setGameBackground(Color.red);
		mainWindow.setScoreBackground(Color.blue);
		mainWindow.setNumberLivesBackground(Color.green);
		mainWindow.setFPS(24);
		
		//show the game
		mainWindow.setVisible(true);
	}
	
}
