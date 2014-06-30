package org.francisparker.mmaunu.gameengine.testing;

import java.awt.Image;
import javax.imageio.ImageIO;

import org.francisparker.mmaunu.gameengine.GameFrame;

public class GameWithCustomJPanelExample
{
	//construct game object...see constructor below
	public static void main(String[] args)
	{
		new GameWithCustomJPanelExample();
	}
	
	@SuppressWarnings("static-access")
	public GameWithCustomJPanelExample()
	{
		//load images
		Image heroImg = null, baddyImg = null;
		try
		{
			heroImg = ImageIO.read(this.getClass().getResource("/hero.png") );
			baddyImg = ImageIO.read(this.getClass().getResource("/baddy.png") );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


		//Create user-controlled characters and other game elements
		SimpleHero hero = new SimpleHero(100, 100, heroImg, 28, 46, 15);
		SimpleBaddy baddy1 = new SimpleBaddy(200, 300, baddyImg, 25, 50, 5);
		SimpleBaddy baddy2 = new SimpleBaddy(300, 100, baddyImg, 25, 50, 5);
		
		
		//Construct a custom JPanel subclass that represents the entire playing area.
		//This needs to include all onscreen regions. You can access the onscreen Drawable
		//objects that you add to GameFrame (like the ControllableCharacter, the AIObjects,
		//and the DrawableGameObjects) via the method call GameFrame.getDrawableList().
		//See the CustomGameJPanel subclass' paintComponent() method for more info.
		CustomGameJPanel customJPanel = new CustomGameJPanel(590, 560);
		
		//construct the game object and pass it your custom JPanel object
		GameFrame mainWindow = new GameFrame("Your Title Here", 600, 600, customJPanel);
		
		//add a user-controlled object and other things to the game
		mainWindow.setControllableObject(hero);
		mainWindow.addDrawableObject(baddy1);
		mainWindow.addDrawableObject(baddy2);
		
		//configure frames per second, show game
		mainWindow.setFPS(24);
		mainWindow.setVisible(true);
	}
	
}
