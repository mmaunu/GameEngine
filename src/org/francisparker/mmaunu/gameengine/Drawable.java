package org.francisparker.mmaunu.gameengine;
import java.awt.Graphics;

/**
 * Implement this interface in any class that wants to create onscreen, visible objects.
 * Characters that run around onscreen will implement this interface but so will background
 * elements, terrain features, obstacles, power-ups, etc. The draw method here should
 * simply use the passed Graphics object to draw an image or representation of the object
 * at appropriate coordinates. The other methods focus on the location and dimensions of the
 * Drawable object which are useful for drawing but also for collision detection.
 * 
 * <b>You will probably not need to implement any of these methods yourself since the 
 * superclasses that you will extend ({@link ControllableObject}, {@link AIObject}, and 
 * {@link DrawableGameObject}) tend to have perfectly serviceable versions.
 * </b>
 * @author mmaunu
 *
 */
public interface Drawable
{
	public void draw(Graphics g);

	public int getX();
	public int getY();

	public int getWidth();
	public int getHeight();

}
