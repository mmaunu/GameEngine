package org.francisparker.mmaunu.gameengine.testing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import org.francisparker.mmaunu.gameengine.AIObject;
import org.francisparker.mmaunu.gameengine.CollisionDetector;
import org.francisparker.mmaunu.gameengine.ControllableObject;
import org.francisparker.mmaunu.gameengine.Drawable;
import org.francisparker.mmaunu.gameengine.GameFrame;

public class Floor extends AIObject
{
	//data associated with all floor objects...not instance data
	//All floor objects share these features
	public static int 	VERTICAL_GAP_BETWEEN_FLOORS = 100,
						HORIZONTAL_GAP_WIDTH = 100,
						MIN_WIDTH_FOR_FLOOR_OBJECT = 50,
						HEIGHT_OF_FLOOR_OBJECT = 10,
						VERTICAL_SPEED_FOR_FLOOR = 3;
	
	//This stores a reference to the other floor object that is at the same height as this one
	//This was necessary because of the way that I remove Floor objects and add new ones...
	//I wanted to make sure that I was only adding two new ones for each removed pair, so
	//I needed to be able to remove them in pairs.
	private Floor partner;
	

	public Floor(int x, int y, Image img, int width, int height, int speed)
	{
		super(x, y, img, width, height, speed);
		partner = null;
	}
	
	public void setPartner(Floor pardner)
	{
		partner = pardner;
	}


	//overriding the draw method since this doesn't use an image...the default
	//draw method draws with an image
	public void draw(Graphics g)
	{
		g.setColor(Color.blue);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}


	@Override
	public void act()
	{	
		int newY = getY();
		newY -= getSpeed();
		
		//Look for collision with user-controlled object...if you get one, modify the 
		//yCoordinate of the object to "bump it" along with the floor...in this way,
		//the moving floor actually moves the object as well
		Drawable possibleCollision = CollisionDetector.moveCollidesWith(
				this, getX(), newY);
		
		if(possibleCollision instanceof ControllableObject)
		{
			ControllableObject hero = GameFrame.getControllableObject();
			int heroNewY = hero.getY();
			heroNewY -= this.getSpeed();
			hero.setY(heroNewY);
		}

		
		
		
		//if this floor goes offscreen, 1) add a point to the score, 2) remove the related 
		//floor objects, and 3) add two new floor objects that are just offscreen (but only 
		//on successful removal in step number 2...so you don't double-add new floors).
		if(newY >= 0)
			setY( newY );
		else
		{
			GameFrame.incrementScore(1);
			
			//remove this one and the partner...if they were already removed, don't add new
			//pieces of floor (to avoid adding duplicate and conflicting pieces)
			boolean removed = GameFrame.removeDrawableObject(this);
			GameFrame.removeDrawableObject(this.partner);
			if(removed)
			{
				Floor[] pair = Floor.makePairOfFloorsWithGap(GameFrame.getHeightOfGameArea(), 
						Floor.VERTICAL_SPEED_FOR_FLOOR);
				GameFrame.addDrawableObject(pair[0]);
				GameFrame.addDrawableObject(pair[1]);
				
				//increase the game's speed when a new level appears
				int increaseSpeedConstant = 8;	//higher number makes increase slower
				int currFPS = GameFrame.getFPS();
				GameFrame.setFPS(currFPS + GameFrame.getScore()/increaseSpeedConstant);
			}
			
			
		}
		
	}
	
	

	/**
	 * This method creates and returns a pair of Floor objects that really represent one long floor with 
	 * a gap in it...a gap that the main character of the game can slide through. The two Floor objects 
	 * returned will have the same yCoordinate, be at least MIN_WIDTH_FOR_FLOOR_OBJECT long, and have a horizontal 
	 * gap between them that is HORIZONTAL_GAP_WIDTH wide. The location of the gap is randomly determined but will
	 * not violate the description given.
	 * @param yCoordOfFloors The height that you want these floors to be at in the game
	 * @param speed The initial speed of these two Floor objects
	 * @return a pair of Floor objects with the same height but with a horizontal gap between them
	 */
	public static Floor[] makePairOfFloorsWithGap(int yCoordOfFloors, int speed)
	{
		Floor[] pair = new Floor[2];

		int xLocOfGap = MIN_WIDTH_FOR_FLOOR_OBJECT + (int)(Math.random() * 
				(GameFrame.getWidthOfGameArea() - 2*MIN_WIDTH_FOR_FLOOR_OBJECT - HORIZONTAL_GAP_WIDTH));

		pair[0] = new Floor(0, yCoordOfFloors, null, xLocOfGap, 
				HEIGHT_OF_FLOOR_OBJECT, speed);
		pair[1] = new Floor(xLocOfGap + HORIZONTAL_GAP_WIDTH, yCoordOfFloors, null, 
				GameFrame.getWidthOfGameArea() - (xLocOfGap + HORIZONTAL_GAP_WIDTH), 
				HEIGHT_OF_FLOOR_OBJECT, speed);

		pair[0].setPartner( pair[1] );
		pair[1].setPartner( pair[0] );
		
		return pair;
	}

}
