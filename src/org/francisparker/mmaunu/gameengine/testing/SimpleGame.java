package org.francisparker.mmaunu.gameengine.testing;

import java.awt.Color;
import java.awt.Image;
import javax.imageio.ImageIO;

import org.francisparker.mmaunu.gameengine.GameFrame;

public class SimpleGame
{
	//This simply constructs one of these objects...the real fun is in the constructor below
	public static void main(String[] args)
	{
		new SimpleGame();
	}
	

	@SuppressWarnings("static-access")
	public SimpleGame()
	{
		//Load up some images
		Image heroImg = null, baddyImg = null, flowerImg = null;
		try
		{	
			//read images relative to this class' location
			heroImg = ImageIO.read(this.getClass().getResource("/hero.png"));
			baddyImg = ImageIO.read(this.getClass().getResource("/baddy.png"));
			flowerImg = ImageIO.read(this.getClass().getResource("/flower.gif"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		//Create user-controlled characters and other game elements
		SimpleHero hero = new SimpleHero(100, 100, heroImg, 28, 46, 15);
		SimpleBaddy baddy1 = new SimpleBaddy(200, 300, baddyImg, 25, 50, 5);
		SimpleBaddy baddy2 = new SimpleBaddy(300, 100, baddyImg, 25, 50, 5);
		SimpleBaddy baddy3 = new SimpleBaddy(400, 450, baddyImg, 25, 50, 5);
		SimpleFlower flower1 = new SimpleFlower(250, 250, flowerImg, 39, 36);
		SimpleFlower flower2 = new SimpleFlower(50, 450, flowerImg, 39, 36);
		
		//Create a GameFrame object which represents the game or a container for the game
		GameFrame mainWindow = new GameFrame("Your Title Here", 600, 600, true, false);
		
		//Add the controllable character and the other objects to the game
		mainWindow.setControllableObject(hero);
		mainWindow.addDrawableObject(baddy1);
		mainWindow.addDrawableObject(baddy2);
		mainWindow.addDrawableObject(baddy3);
		mainWindow.addDrawableObject(flower1);
		mainWindow.addDrawableObject(flower2);

		//set a few settings
		mainWindow.setGameBackground(Color.red);
		mainWindow.setScoreBackground(Color.blue);
		mainWindow.setNumberLivesBackground(Color.green);
		mainWindow.setFPS(24);
		
		//show the game
		mainWindow.setVisible(true);
	}
	
	
}
