package org.francisparker.mmaunu.gameengine;
import java.awt.*;

/**
 * This class is the superclass of a player-controlled character. In a game of Galaga, 
 * this would be the superclass of the human-controlled space ship; in a game of Pac-Man, 
 * this would be the superclass of the good ol' Pacster himself.
 * 
 * @author mmaunu
 *
 */
public abstract class ControllableObject extends ActableDrawableGameObject 
{
	private boolean movingUp, movingDown, movingRight, movingLeft;

	/**
	 * Constructs a ControllableObject.
	 * @param x			Starting xCoordinate
	 * @param y			Starting yCoordinate
	 * @param img		Image to associate with ActableDrawableGameObject object
	 * @param width		Width of object onscreen (in pixels)
	 * @param height	Height of object onscreen (in pixels)
	 * @param speed		Speed of object onscreen (in pixels per animation cycle)
	 */
	public ControllableObject( int x, int y, Image img, int width, int height, int speed )
	{
		super(x,y,img,width,height,speed);
		
		movingUp = false;
		movingDown = false;
		movingRight = false;
		movingLeft = false;
	}
	
	/**
	 * In your subclass, you must provide a unique act() method that determines the
	 * behavior of your subclass. The character's state (is it moving up, down, right, 
	 * left, not at all) will be set for you but you must decide what to do with that
	 * information and what to do when your character collides with other elements
	 * onscreen.
	 */
	public abstract void act();

	/**
	 * This method will get called for you by the Game Engine itself.
	 * @param b 
	 */
	public void setMovingUp( boolean b ) {
		movingUp = b;
		if( movingUp )
			movingDown = false;
	}

	/**
	 * This method will get called for you by the Game Engine itself.
	 * @param b 
	 */
	public void setMovingDown( boolean b ) {
		movingDown = b;
		if( movingDown )
			movingUp = false;
	}

	/**
	 * This method will get called for you by the Game Engine itself.
	 * @param b 
	 */
	public void setMovingLeft( boolean b ) {
		movingLeft = b;
		if( movingLeft )
			movingRight = false;
	}

	/**
	 * This method will get called for you by the Game Engine itself.
	 * @param b 
	 */
	public void setMovingRight( boolean b ) {
		movingRight = b;
		if( movingRight )
			movingLeft = false;
	}

	/**
	 * Allows you to determine if the player is hitting a movement key.
	 * @return
	 */
	public boolean isMovingUp() {
		return movingUp;
	}

	/**
	 * Allows you to determine if the player is hitting a movement key.
	 * @return
	 */
	public boolean isMovingDown() {
		return movingDown;
	}

	/**
	 * Allows you to determine if the player is hitting a movement key.
	 * @return
	 */
	public boolean isMovingLeft() {
		return movingLeft;
	}

	/**
	 * Allows you to determine if the player is hitting a movement key.
	 * @return
	 */
	public boolean isMovingRight() {
		return movingRight;
	}
	
	/**
	 * This overridden version makes sure that only ControllableObjects are equal to 
	 * other ControllableObjects.
	 */
	public boolean equals(Object o)
	{
		if( ! (o instanceof ControllableObject) )
			return false;
		
		return super.equals(o);
	}
}
