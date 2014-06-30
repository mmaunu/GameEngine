package org.francisparker.mmaunu.gameengine.testing;

import java.awt.Image;

import javax.swing.JOptionPane;

import org.francisparker.mmaunu.gameengine.CollisionDetector;
import org.francisparker.mmaunu.gameengine.ControllableObject;
import org.francisparker.mmaunu.gameengine.Drawable;
import org.francisparker.mmaunu.gameengine.GameFrame;

public class FloorHero extends ControllableObject
{

	public FloorHero(int x, int y, Image img, int width, int height, int speed)
	{
		super(x, y, img, width, height, speed);
	}

	@Override
	public void act()
	{
		//The floors can modify your yCoordinate and move you up a bit...
		//if you ever get pushed up against the top of the game, YOU LOSE!
		if(getY() <= 0)
		{
			GameFrame.stopAnimation();
			JOptionPane.showMessageDialog(null, "Game Over! Final Score: " + GameFrame.getScore());
			RisingLevelsWithGapsGame.main(null);
		}
		
		
		//get x and y values, modify by speed based on keyboard input
		int currX = getX();
		int currY = getY();
		int speed = getSpeed();

		int newX = currX;
		int newY = currY;

		if(isMovingUp())
			newY -= speed;			
		else if (isMovingDown())
			newY += speed;	

		if(isMovingLeft())
			newX -= speed;
		else if(isMovingRight())
			newX += speed;

		//check for collision...do it separately for each coordinate because you might
		//be able to go down but not sideways and it would be nice to allow that even when
		//the player is holding both the down key and a sideways key (like S-D or S-A).
		Drawable possibleCollision = CollisionDetector.moveCollidesWith(
				this, newX, currY);

		//if there is no collision, safe to modify xCoordinate to the newX value...check for 
		//object staying onscreen while you're at it
		if(possibleCollision == null && newX > 0 && newX + getWidth() <= GameFrame.getWidthOfGameArea())
			setX(newX);
		
		
		//Test again for the newY value...just reusing the possibleCollision variable since we don't need
		//the old object that it was pointing to anymore
		possibleCollision = CollisionDetector.moveCollidesWith(
				this, currX, newY);

		//if no collision, safe to change yCoordinate to newY
		if(possibleCollision == null && newY > 0 && newY + getHeight() <= GameFrame.getHeightOfGameArea())
			setY(newY);
			
			
	}


}
