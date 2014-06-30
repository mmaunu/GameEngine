package org.francisparker.mmaunu.gameengine;
import java.awt.*;


/**
 * This class is the superclass of all onscreen objects that move. For example, in the 
 * game of Pac-Man you would create the "Ghost" class and the "Fruit" class as subclasses 
 * of AIObject; both of these objects move around onscreen and are not controlled by the 
 * player. If you are designing a game where an object doesn't move but still has some type
 * of "behavior", then you will make it a subclass of {@link AIObject} so that you can code
 * the "behavior" in the {@link AIObject#act()} method. If the object doesn't move and doesn't
 * exhibit a unique behavior (like a non-moving wall or barrier), you should use 
 * {@link DrawableGameObject} as the superclass.
 * @author mmaunu
 *
 */

public abstract class AIObject extends ActableDrawableGameObject
{

	/**
	 * Constructs an AIObject.
	 * @param x			Starting xCoordinate
	 * @param y			Starting yCoordinate
	 * @param img		Image to associate with ActableDrawableGameObject object
	 * @param width		Width of object onscreen (in pixels)
	 * @param height	Height of object onscreen (in pixels)
	 * @param speed		Speed of object onscreen (in pixels per animation cycle)
	 */
	public AIObject(int x, int y, Image img, int width, int height, int speed)
	{
		super(x,y,img,width,height,speed);
	}

	/**
	 * In your subclass of AIObject, you will provide a unique act() method that
	 * determines the behavior of this object. For Pac-Man, you would have the "Ghost"
	 * be a subclass of AIObject. In act(), you would determine how the ghost moves
	 * and what it does when it intersects with another onscreen object (which would vary
	 * by object...it bounces off of walls but really messes with Pac-Man).
	 */
	public abstract void act();

	
	/**
	 * This overridden version makes sure that AIObjects are only equal to other AIObjects.
	 */
	public boolean equals(Object o)
	{
		if( ! (o instanceof AIObject) )
			return false;
		
		return super.equals(o);
	}
	
}
