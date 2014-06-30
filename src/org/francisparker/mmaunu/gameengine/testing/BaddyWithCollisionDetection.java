package org.francisparker.mmaunu.gameengine.testing;

import java.awt.Image;
import javax.imageio.ImageIO;
import org.francisparker.mmaunu.gameengine.AIObject;
import org.francisparker.mmaunu.gameengine.CollisionDetector;
import org.francisparker.mmaunu.gameengine.ControllableObject;
import org.francisparker.mmaunu.gameengine.Drawable;
import org.francisparker.mmaunu.gameengine.GameFrame;

public class BaddyWithCollisionDetection extends AIObject
{

	private int currDirection;
	
	private static int DIR_UP = 1, DIR_DOWN = 2;
	
	public BaddyWithCollisionDetection(int x, int y, Image img, int width, int height, int speed)
	{
		super(x, y, img, width, height, speed);
		currDirection = DIR_UP;
	}

	
	public void act()
	{
		//just moves up and down...randomly dropping bombs...
		//also checks for collisions with user-controlled character
		
		int currY = getY();
		int speed = getSpeed();
		int height = getHeight();
		
		int newY = currY;
		
		if(currDirection == DIR_UP && newY - speed >= 0)
			newY -= speed;
		else if(currDirection == DIR_UP)
			currDirection = DIR_DOWN;
		else if (currDirection == DIR_DOWN && 
					newY + height + speed  <= GameFrame.getHeightOfGameArea())
			newY += speed;
		else 
			currDirection = DIR_UP;		
		
		
		int newX = getX();	//x-coordinate doesn't change...could just use getX() below
		
		//use this CollisionDetector method to see if the newX and newY
		//will cause THIS object to collide with another
		Drawable possibleCollision = CollisionDetector.moveCollidesWith(
											this, newX, newY);
		
		//if the collision happened and it is an instance of the controllable character
		if(possibleCollision instanceof ControllableObject)
		{
			GameFrame.decrementNumberLives(1);
			
		}
		
		//always move...even if a collision is detected...perhaps not the best
		setY(newY);		
		
		
		//randomly drop a bomb 1% of the time
		if(Math.random() < 0.01)
		{
			try
			{
				Image bombImage = ImageIO.read( this.getClass().getResource("/bomb.png") );
				Bomb b = new Bomb(getX(), getY(), bombImage, 32, 32, 0);
				GameFrame.addDrawableObject(b);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	

}
