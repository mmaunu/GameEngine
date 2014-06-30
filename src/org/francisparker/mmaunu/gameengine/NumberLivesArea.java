package org.francisparker.mmaunu.gameengine;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * This is a class designed to work with the {@link GameFrame} class in my GameEngine project.
 * This queries the GameFrame object for the number of lives left in the game and simply 
 * reports that value. The background color is configurable and/or you can set a background 
 * image for the object. Both of these are achieved via the 
 * {@link GameFrame#setNumberLivesBackground(java.awt.Color)}
 * and {@link GameFrame#setNumberLivesBackground(Image)} methods.
 * @author mmaunu
 *
 */
@SuppressWarnings("serial")
public class NumberLivesArea extends JPanel
{
	private int width, height;
	private Image backgroundImage;

	/**
	 * Specify the width and height of this area. This can't be modified after construction.
	 * @param width width of JPanel. This is immutable.
	 * @param height height of JPanel. This is immutable.
	 */
	public NumberLivesArea(int width, int height)
	{
		this.width = width;
		this.height = height;

	}

	/**
	 * Simply paints a background color and/or image and the number of lives left as
	 * reported by the GameFrame class.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(backgroundImage != null)
			g.drawImage(backgroundImage, 0, 0, null);
		
		g.drawString("Lives Left: " + GameFrame.getNumberLives(), 20, height/2);
	}
	

	public Dimension getSize(){
		return new Dimension( width, height );
	}

	public Dimension getMinimumSize(){
		return getSize();
	}

	public Dimension getMaximumSize(){
		return getSize();
	}

	public Dimension getPreferredSize(){
		return getSize();
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}


	public void setBackgroundImage(Image img)
	{
		backgroundImage = img;
	}

}

