package org.francisparker.mmaunu.gameengine;
/**
 * Implement this interface in any class that should be able to move around onscreen or
 * actively interact with other game elements. You should not actually have to implement 
 * the interface in your class because you will either make a subclass of 
 * {@link ControllableObject} for user-controlled onscreen objects or a subclass of
 * {@link AIObject} for non-user-controlled onscreen objects.
 * @author mmaunu
 *
 */
public interface Actable
{
	public void act();
}
