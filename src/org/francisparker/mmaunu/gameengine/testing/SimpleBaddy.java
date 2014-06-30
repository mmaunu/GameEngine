package org.francisparker.mmaunu.gameengine.testing;

import java.awt.Image;
import org.francisparker.mmaunu.gameengine.AIObject;
import org.francisparker.mmaunu.gameengine.GameFrame;

public class SimpleBaddy extends AIObject
{
	//The currDirection should always be either DIR_UP or DIR_DOWN.
	//Using named variables instead of just a 1 and a 2 so that it 
	//makes more sense when you read a line of code.
	private int currDirection;
	private static int DIR_UP = 1, DIR_DOWN = 2;
	
	
	//Template...you will make your constructor just like this one and have it call
	//super just like this one...any extra variables get initialized as well
	public SimpleBaddy(int x, int y, Image img, int width, int height, int speed)
	{
		super(x, y, img, width, height, speed);
		currDirection = DIR_UP;
	}

	
	public void act()
	{
		//just moves up and down...terrible AI...but does check for collisions
		
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
		
		setY(newY);		
	}
	

}
