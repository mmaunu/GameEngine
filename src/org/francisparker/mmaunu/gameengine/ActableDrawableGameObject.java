package org.francisparker.mmaunu.gameengine;
import java.awt.Image;



/**
 * This class is the superclass of all characters in the game engine. 
 * This includes the "main" character that a player controls with the 
 * keyboard and all other characters or objects that move around onscreen
 * independent of player control. 
 * 
 * @author mmaunu
 *
 */

public abstract class ActableDrawableGameObject extends DrawableGameObject implements Actable
{
	private int speed;
	
	/**
	 * Constructs a ActableDrawableGameObject object.
	 * @param x			Starting xCoordinate
	 * @param y			Starting yCoordinate
	 * @param img		Image to associate with ActableDrawableGameObject object
	 * @param width		Width of object onscreen (in pixels)
	 * @param height	Height of object onscreen (in pixels)
	 * @param speed		Speed of object onscreen (in pixels per animation cycle)
	 */
	public ActableDrawableGameObject( int x, int y, Image img, int width, int height, int speed )
	{
		super(x, y, img, width, height);
		this.speed = speed;
	}
	
	/**
	 * You must provide unique implementations of act() in your subclasses.
	 * There is no default "behavior" for onscreen objects.
	 */
	public abstract void act();
	
	/**
	 * Returns the speed of this object. For objects that don't move, this might be 0.
	 * @return speed of object in pixels per time-cycle
	 */
	public int getSpeed()
	{
		return speed;
	}
	
	/**
	 * Allows you to modify the speed of this object.
	 * @param newSpeed New value for the speed. Must be positive.
	 */
	public void setSpeed(int newSpeed)
	{
		speed = Math.abs(newSpeed);
	}
	
	
	/**
	 * This checks to see if the parameter has the same location and dimensions as this object.
	 * If so, and the parameter is another ActableDrawableGameObject, true is returned; 
	 * false otherwise. 
	 */
	public boolean equals(Object obj)
	{
		if(! (obj instanceof ActableDrawableGameObject) )
			return false;
		
		ActableDrawableGameObject other = (ActableDrawableGameObject)obj;
		
		return 	this.getX() == other.getX() && this.getY() == other.getY() &&
				this.getWidth() == other.getWidth() && this.getHeight() == other.getHeight();
	}
}
