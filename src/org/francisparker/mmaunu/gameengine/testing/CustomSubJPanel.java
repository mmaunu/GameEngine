package org.francisparker.mmaunu.gameengine.testing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CustomSubJPanel extends JPanel
{

	private int width, height;

	public CustomSubJPanel(int w, int h, Color bg)
	{
		super();
		width = w;
		height = h;
		setBackground(bg);
	}

	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawString("SubJPanel...yay!", 5, 20);
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
