package org.francisparker.mmaunu.gameengine.testing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.francisparker.mmaunu.gameengine.Drawable;
import org.francisparker.mmaunu.gameengine.GameFrame;

/**
 * This example draws a "lake" and a "forest" image on the game area. However, 
 * you could also make Lake and Forest as subclasses of DrawableGameObject and 
 * add them to the game just like you add AIObjects and ControllableCharacters.
 * This was simply done for demo purposes.
 * <p>
 * This custom JPanel subclass also adds another silly little sub JPanel. Again,
 * this is just to illustrate the ability to do something like this. 
 * @author mmaunu
 *
 */

@SuppressWarnings("serial")
public class CustomGameJPanel extends JPanel
{
	private int width, height, score;
	private Image forestImage, lakeImage;
	
	private CustomSubJPanel subJPanel1, subJPanel2;

	public CustomGameJPanel(int width, int height)
	{
		super();
		this.width = width;
		this.height = height;
		score = 0;
		
		//making some other (sub) custom JPanels to show that you can add JPanels to 
		//your JPanels like JPanelCeption or something...
		subJPanel1 = new CustomSubJPanel(300,200, Color.yellow);
		subJPanel2 = new CustomSubJPanel(100,500, Color.cyan);
		
		setLayout(new BorderLayout());
		this.add(subJPanel1, BorderLayout.SOUTH);
		this.add(subJPanel2, BorderLayout.EAST);
		
		
		setBackground(Color.green);
		
		
		//load some images...they get drawn in the paintComponent() method...could have made 
		//Lake and Forest objects as subclasses of DrawableGameObject instead...this makes
		//them truly just decorations...just images drawn onscreen, no interaction possible
		//since they aren't objects
		try
		{
			forestImage = ImageIO.read( this.getClass().getResource("/forest.png") );
			lakeImage = ImageIO.read( this.getClass().getResource("/lake.png") );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	//This does all of the drawing for this custom game board...
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//draw a forest and a lake...and a message up top
		g.drawImage(forestImage, 30, 30, null);
		g.drawImage(lakeImage, 300, 300, null);
		g.setFont(new Font("Serif", Font.BOLD, 30));
		g.drawString("Welcome, Stranger!", 200, 50);
		
		//draw some fake score onscreen...you would need to add a method or
		//two to this class that sets or changes the score if you really wanted
		//the score thing to work...
		g.drawString("Score: " + score, 200, 20);
		
		
		//This gets a list of all Drawable items that have been added to the GameFrame
		//and then loops over them to draw them...
		ArrayList<Drawable> drawableList = GameFrame.getDrawableList();
		for(Drawable d: drawableList)
			d.draw(g);
	}
	
	
	
	
	//the methods below (all of the "size" methods) are here to make sure that the size
	//of this JPanel are correct...respect our requests, layout managers!!
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
