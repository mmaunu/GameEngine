package org.francisparker.mmaunu.gameengine.testing;

import java.awt.Image;
import org.francisparker.mmaunu.gameengine.ControllableObject;


public class SimpleHero extends ControllableObject
{

	public SimpleHero(int x, int y, Image img, int width, int height, int speed)
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

		setY(newY);
		setX(newX);

	}

}
