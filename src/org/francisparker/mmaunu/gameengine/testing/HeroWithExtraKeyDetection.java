package org.francisparker.mmaunu.gameengine.testing;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.imageio.ImageIO;
import org.francisparker.mmaunu.gameengine.ControllableObject;
import org.francisparker.mmaunu.gameengine.GameFrame;

public class HeroWithExtraKeyDetection extends ControllableObject implements KeyListener
{

	public HeroWithExtraKeyDetection(int x, int y, Image img, int width, int height, int speed)
	{
		super(x, y, img, width, height, speed);

	}


	public void act()
	{
		int currX = getX();
		int currY = getY();
		int speed = getSpeed();

		int newX = currX;
		int newY = currY;

		if(isMovingUp())
			newY -= speed;			//recall that up is a smaller y-coordinate in pixels
		else if (isMovingDown())
			newY += speed;	

		if(isMovingLeft())
			newX -= speed;
		else if(isMovingRight())
			newX += speed;




		//ignore collisions for now...and ignoring the width and the height
		//of the game area, which is accessible via
		//GameFrame.HEIGHT_OF_GAME_AREA and GameFrame.WIDTH_OF_GAME_AREA
		setY(newY);
		setX(newX);

	}


	/**
	 * This user-controlled object can also respond to keyboard input by 
	 * implementing the KeyListener interface and then adding the "hero" as 
	 * a keylistener to the GameFrame object.
	 */
	public void keyPressed(KeyEvent evt)
	{
		//The KeyEvent object can tell us the code for the key that the user pressed
		int key = evt.getKeyCode();

		//VK_SPACE represents the spacebar...in general, use VK_X to detect an x or VK_TAB to 
		//detect the tab key...VK stands for Virtual Key
		if( key == KeyEvent.VK_SPACE )
		{
			try
			{
				Image img = ImageIO.read( this.getClass().getResource("/flower.gif") );
				GameFrame.addDrawableObject( new SimpleFlower(getX(), getY(), img, 39, 36 ));
				GameFrame.incrementScore(1);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
			

	}


	@Override
	public void keyReleased(KeyEvent evt)
	{
		//empty since you are only dropping flowers when the space bar is pressed
		//and just ignoring the release...
	}


	@Override
	public void keyTyped(KeyEvent evt)
	{
		//Ignoring the "typed" event...just handling the "pressed" event
	}

}
