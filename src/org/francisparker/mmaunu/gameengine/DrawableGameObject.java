package org.francisparker.mmaunu.gameengine;

import java.awt.Graphics;
import java.awt.Image;

/**
 * This superclass represents an abstraction of all objects visible onscreen. The 
 * user-controlled object, any other movable objects, static terrain features (walls, lakes, 
 * etc.), etc. will all be subclasses of DrawableGameObject. Since this implements 
 * Drawable (and has a perfectly fine {@link Drawable#draw(Graphics)} method, you will
 * almost certainly NOT override the draw() method in your subclasses. You will only do
 * this if you want the drawing of your object to not simply draw the image associated
 * with your object (of if you didn't associate an image with your object). This will
 * probably be rare, so ask if you want some custom behavior.  
 * @author mmaunu
 *
 */
public class DrawableGameObject implements Drawable
{

	private int xCoord, yCoord;

	private int width, height;
	
	private Image image;
	
	public DrawableGameObject(int x, int y, Image img, int width, int height)
	{
		xCoord = x;
		yCoord = y;
		image = img;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Draws the object's associated image at the appropriate (x,y). The draw
	 * method does not attempt to scale the image to the appropriate width and height.
	 * You must scale your images to be the correct/desired size. If you do not want 
	 * your Drawable object to have an image associated with it (e.g. because you are 
	 * going to draw the image directly using Graphics methods like fillRect(), 
	 * fillOval(), etc.), then simply pass a null reference for the image and override
	 * the draw() method in your subclass. 
	 */
	public void draw(Graphics g)
	{
		if( image != null )
			g.drawImage(image, xCoord, yCoord, null);
		else
			g.drawString("error with image: " + getClass(), xCoord, yCoord);
	}
	
	/**
	 * Returns the x-coordinate of the object.
	 */
	public int getX()
	{
		return xCoord;
	}
	
	/**
	 * Returns the y-coordinate of the object.
	 */
	public int getY()
	{
		return yCoord;
	}
	
	/**
	 * Returns the width of the object. This is not necessarily the width of the image associated with
	 * this object...it is the width as indicated when constructing the object.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Returns the height of the object. This is not necessarily the height of the image associated with
	 * this object...it is the height as indicated when constructing the object.
	 */
	public int getHeight()
	{
		return height;
	}
	
	
	/**
	 * This method returns the image associated with this {@link Drawable} object.
	 * @return
	 */
	public Image getImage()
	{
		return image;
	}
	
	/**
	 * This method allows you to modify the location of this object.
	 * @param x New x-ccordinate to use. 
	 */
	public void setX(int x)
	{
		xCoord = x;
	}
	
	/**
	 * This method allows you to modify the location of this object.
	 * @param y New y-coordinate to use.
	 */
	public void setY(int y)
	{
		yCoord = y;
	}
	
	/**
	 * Set the width to the parameter.
	 * @param w New width for this object.
	 */
	public void setWidth(int w)
	{
		width = w;
	}
	
	/**
	 * Set the height to the parameter.
	 * @param h New height of the object.
	 */
	public void setHeight(int h)
	{
		height = h;
	}

	/**
	 * This allows you to change the image associated with this object.
	 * @param img New Image to use.
	 */
	public void setImage(Image img)
	{
		image = img;
	}
}
