package org.francisparker.mmaunu.gameengine.testing;

import java.awt.Image;

import javax.swing.JOptionPane;

import org.francisparker.mmaunu.gameengine.AIObject;
import org.francisparker.mmaunu.gameengine.CollisionDetector;
import org.francisparker.mmaunu.gameengine.ControllableObject;
import org.francisparker.mmaunu.gameengine.Drawable;


public class Bomb extends AIObject
{

	public Bomb(int x, int y, Image image, int width, int height, int speed)
	{
		super(x, y, image, width, height, speed);

	}


	//If a bomb collides with a user-controlled object, then they explode
	public void act()
	{
		//this doesn't move so the newX and newY are the same as the current (x, y)
		int newX = getX();
		int newY = getY();
		
		//Bombs don't move so don't modify the x and y...this collision detection could
		//have been put inside of the ControllableObject instead and then this could have
		//been a subclass of DrawableGameObject and wouldn't have needed its own act() method.
		Drawable possibleCollision = CollisionDetector.moveCollidesWith(
				this, newX, newY);

		//if the collision happened and it is an instance of the controllable character
		if(possibleCollision instanceof ControllableObject)
		{
			//if you wanted to just quit when contact happens
			JOptionPane.showMessageDialog(null, "Ouch!");
			System.exit(1);
		}

	}

}
