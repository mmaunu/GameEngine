package org.francisparker.mmaunu.gameengine;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * This class was designed to work with the {@link GameFrame} class in my GameEngine project.
 * It is responsible for displaying the key components of the game: the user-controlled 
 * onscreen object as well as any other visual elements of the game ("bad guys"; terrain
 * features like walls, barriers, or lakes; projectiles; etc.). This class gets a list of
 * all Drawable objects from the {@link GameFrame} and then draws them. The repainting of
 * this screen is triggered periodically by the {@link AnimationManager} class. 
 * @author mmaunu
 *
 */
@SuppressWarnings("serial")
public class GameArea extends JPanel
{
	private int width, height;
	private Image backgroundImage;

	/**
	 * The width and height are immutable and this object is not resizable in general.
	 * @param width
	 * @param height
	 */
	public GameArea(int width, int height)
	{
		this.width = width;
		this.height = height;

	}

	/**
	 * Sets an optional background image to be drawn onscreen.
	 * @param img An image to be drawn under all of the other game components.
	 */
	public void setBackgroundImage(Image img)
	{
		backgroundImage = img;
	}

	/**
	 * This draws the background color and/or background image and then simply
	 * draws all of the Drawable objects obtained from {@link GameFrame}.
	 * @param g
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(backgroundImage != null)
			g.drawImage(backgroundImage, 0, 0, null);
		
		ArrayList<Drawable> drawableList = GameFrame.getDrawableList();
		
		for(Drawable d: drawableList)
			d.draw(g);
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
}
