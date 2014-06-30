package org.francisparker.mmaunu.gameengine;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * A helper class to perform collision logic. The main (only?) item of interest is the 
 * moveCollidesWith() method.
 * @author mmaunu
 *
 */
public class CollisionDetector
{

	/**
	 * When you call this method, send it the object that wants to move 
	 * (<i>mover</i>) and the new (x, y) coordinates that it wants to move to.
	 * This method will return null if the movement will not collide with another 
	 * object. If the movement will collide with another object, then this method
	 * returns the object itself. Notice that there is a problem if the movement
	 * will collide with multiple objects; only one of them will be detected and
	 * returned and no guarantee is made as to which object.
	 * @param mover The Drawable object that is moving to a new (x, y) position.
	 * @param desiredX The new x-coordinate that the mover wants to occupy.
	 * @param desiredY The new y-coordinate that the mover wants to occupy.
	 * @return null if no collision, the object collided with otherwise.
	 */
	public static Drawable moveCollidesWith(Drawable mover, int desiredX, int desiredY)
	{
		Drawable collidedWith = null;		//if we find an intersection, we'll change this

		Rectangle moverRect = new Rectangle( desiredX, desiredY, mover.getWidth(), mover.getHeight() );

		ArrayList<Drawable> drawableList = GameFrame.getDrawableList();
		
		for( int pos = 0; pos < drawableList.size(); pos++ )
		{
			Drawable curr = drawableList.get( pos );
			if( curr.equals( mover ) == false )	//only check for collisions with other Drawables
			{
				Rectangle currRect = new Rectangle( curr.getX(), curr.getY(), curr.getWidth(),
						curr.getHeight() );

				if( moverRect.intersects( currRect ) )
				{
					collidedWith = curr;		//we collided, so set collidedWith and stop looking
					break;
				}
			}
		}

		return collidedWith;

	}

}
