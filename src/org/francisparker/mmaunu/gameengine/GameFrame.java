package org.francisparker.mmaunu.gameengine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class can be used in two different ways that I will refer to default mode and 
 * custom mode.
 * <p>
 * In <b>default mode</b>, you will get an onscreen window that you can customize a 
 * little bit but you will not have too many options available to you. You will be able 
 * to set a background image for the main window, keep track of and display a score for the 
 * game, and also maintain a "number of lives" display. You can also disable the score and/or 
 * the "number of lives" displays if you don't want them (via parameters to the 
 * {@link GameFrame#GameFrame(String, int, int, boolean, boolean)} constructor).
 * <p>
 * In <b>custom mode</b>, you have complete control over the contents of the window. You 
 * accomplish this by creating your own custom JPanels that hold your various regions of 
 * interest. You then create another JPanel that contains all of your other custom JPanels
 * inside of it. Finally, you use the {@link GameFrame#GameFrame(String, int, int, JPanel)} 
 * constructor that accepts a JPanel as a parameter; the JPanel parameter should be the
 * one that contains all of your visual elements.
 * <p>
 * <b>YOU CAN'T COMBINE THESE MODES! You must either use default mode or custom mode.</b>
 *
 * 
 * @author mmaunu
 *
 */

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements KeyListener
{

	private static ArrayList<Drawable> drawableList;
	private static ControllableObject controllableObject;
	
	private static GameFrame gameFrameInstance;
	
	
	/*
	 * In custom mode, you create a JPanel with all of the content for the window inside
	 * of it and then pass that to this version of the constructor 
	 * {@link #GameFrame(String, int, int, JPanel)}. If you do not use custom mode, then 
	 * you get a default game setup with a large game area occupying the majority of the 
	 * window with a score and number of lives display at the top of the window (both 
	 * optional). You can still customize the content of the game (obviously) by adding 
	 * elements to the game and customizing how the elements behave and interact with 
	 * each other. Longest field comment evar!
	 */
	private boolean customModeEnabled;
	
	//default setup...simple subclasses of JPanel
	private GameArea gameArea;
	private ScoreArea scoreArea;
	private NumberLivesArea numberLivesArea;
	private static int score, numberLives, heightOfNonGameAreas, widthOfNonGameAreas;
	private static int HEIGHT_OF_GAME_AREA, WIDTH_OF_GAME_AREA;
	
	private static AnimationManager animationManager;
	private static Thread animationManagerThread;
	private static int animationFPS;


	
	/**
	 * Use this constructor for "default" mode (where you get a playing area, an optional 
	 * score area, and another optional "number of lives" area). You can disable the
	 * optional areas via parameters to the constructor.
	 * @param title		A title for the window
	 * @param width		The desired width for the entire window
	 * @param height	The desired height for the entire window
	 * @param scoreBoardDisplayed 	True to display a score board, false otherwise
	 * @param livesLeftDisplayed 	True to display a display for lives left, false otherwise
	 */
	public GameFrame(String title, int width, int height, 
			boolean scoreBoardDisplayed, boolean livesLeftDisplayed)
	{
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		setScore(0);
		setNumberLives(0);
		customModeEnabled = false;
		drawableList = new ArrayList<Drawable>();
		gameFrameInstance = this;
		
		
		//briefly setup the GUI to calculate the size of the Frame's borders (via Insets)
		setSize(100,100);
		pack();
		Insets insets = getInsets();
		
		//put standard elements into play with standard heights for score and number of lives
		//but calculate the height of the GameArea
		if(scoreBoardDisplayed || livesLeftDisplayed)
			heightOfNonGameAreas = 50;
		else
			heightOfNonGameAreas = 0;		
		
		HEIGHT_OF_GAME_AREA = height - heightOfNonGameAreas - insets.top - insets.bottom;
		WIDTH_OF_GAME_AREA = width - insets.right - insets.left;
		
		if(scoreBoardDisplayed && livesLeftDisplayed)
			widthOfNonGameAreas = WIDTH_OF_GAME_AREA/2;
		else if(scoreBoardDisplayed || livesLeftDisplayed)
			widthOfNonGameAreas = WIDTH_OF_GAME_AREA;
		else
			widthOfNonGameAreas = 0;
		
		gameArea = new GameArea(WIDTH_OF_GAME_AREA, HEIGHT_OF_GAME_AREA);
		scoreArea = new ScoreArea(widthOfNonGameAreas, heightOfNonGameAreas);
		numberLivesArea = new NumberLivesArea(widthOfNonGameAreas, heightOfNonGameAreas);
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(gameArea, BorderLayout.CENTER);
		Box b = Box.createHorizontalBox();
		if(scoreBoardDisplayed)
			b.add(scoreArea);
		if(livesLeftDisplayed)
			b.add(numberLivesArea);
		if(scoreBoardDisplayed || livesLeftDisplayed)
			contentPane.add(b, BorderLayout.NORTH);
		
		
		animationFPS = 24;
		animationManager = new AnimationManager();
		animationManagerThread = new Thread(animationManager);
		animationManagerThread.start();
		setSize(width, height);
		
	}
	
	/**
	 * Use this constructor if you want control over the contents of the screen (if you
	 * don't want the default GameArea and optional ScoreBoard/NumberLives areas onscreen).
	 * Specify a title for the program, a width and a height, and a JPanel that you have 
	 * loaded up with your onscreen elements. 
	 * @param title The title to be displayed in the program's title bar.
	 * @param width The width of the entire program.
	 * @param height The height of the entire program.
	 * @param content A JPanel that displays the contents of your game.
	 */
	public GameFrame(String title, int width, int height, JPanel content)
	{
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		customModeEnabled = true;
		drawableList = new ArrayList<Drawable>();
		gameFrameInstance = this;
		
		//briefly setup the GUI to calculate the size of the Frame's borders (via Insets)
		setSize(100,100);
		pack();
		Insets insets = getInsets();
		//best guess for width and height of game area...
		HEIGHT_OF_GAME_AREA = height - insets.top - insets.bottom;
		WIDTH_OF_GAME_AREA = width - insets.right - insets.left;
		
		setContentPane(content);
		
		animationFPS = 24;
		animationManager = new AnimationManager();
		animationManagerThread = new Thread(animationManager);
		animationManagerThread.start();
		setSize(width, height);
	}
	
	/**
	 * Sets the one user-controlled object for this game.
	 * @param controllableObj The object that the user will control via keyboard. By default,
	 * this object is controlled using either the WASD keys or the arrow keys.
	 */
	public static void setControllableObject(ControllableObject controllableObj)
	{
		controllableObject = controllableObj;
		if( controllableObject != null && ! (drawableList.contains(controllableObject)) )
			drawableList.add(controllableObject);
	}
	
	/**
	 * Returns the user-controlled object in this game.
	 * @return The user-controlled object.
	 */
	public static ControllableObject getControllableObject()
	{
		return controllableObject;
	}
	
	/**
	 * This method removes the specified {@link ControllableObject} object.
	 * @param controllableObject The element to remove.
	 * @return true if the element was removed and false if it wasn't (e.g. if it wasn't in
	 * 			the list to begin with).
	 */
	public static boolean removeControllableObject(ControllableObject controllableObj)
	{
		if(controllableObject != null)
			return drawableList.remove(controllableObj);
		else
			return false;
	}
	
	
	/**
	 * Adds the passed object to the list of things to be drawn onscreen.
	 * @param drawableObject Element to be added.
	 */
	public static void addDrawableObject(DrawableGameObject drawableObject)
	{
		if(drawableObject != null)	
			drawableList.add(drawableObject);
	}
	
	/**
	 * This method removes the specified {@link DrawableGameObject} object.
	 * @param drawableObject The element to remove.
	 * @return true if the element was removed and false if it wasn't (e.g. if it wasn't in
	 * 			the list to begin with).
	 */
	public static boolean removeDrawableObject(DrawableGameObject drawableObject)
	{
		if(drawableObject != null)
			return drawableList.remove(drawableObject);
		else
			return false;
	}
	
	/**
	 * This method returns the list of all {@link Drawable} objects in the game.
	 * @return
	 */
	public static ArrayList<Drawable> getDrawableList()
	{
		return drawableList;
	}
	
	/**
	 * This allows you to customize the image in the background for the main game area. 
	 * This can be used to set a "map" or just a cool image for the game. The user-
	 * controlled character can not interact with elements of the image unless you actually
	 * create {@link AIObject}s or {@link DrawableGameObject}s for those elements.
	 * @param img The new background image for the "number of lives" area.
	 */
	public void setGameBackground(Image img)
	{
		if(!customModeEnabled)
			gameArea.setBackgroundImage(img);
	}
	
	/**
	 * This allows you to customize the color of the background for the default game area.
	 * @param c Color to change the background to.
	 */
	public void setGameBackground(Color c)
	{
		if(!customModeEnabled)
			gameArea.setBackground(c);
	}
	
	/**
	 * This allows you to customize the image in the background for the default display for
	 * the score.
	 * @param img The new background image for the "number of lives" area.
	 */
	public void setScoreBackground(Image img)
	{
		if(!customModeEnabled)
			scoreArea.setBackgroundImage(img);
	}
	
	/**
	 * This allows you to customize the color of the background for the default display for
	 * the score.
	 * @param c Color to change the background to.
	 */
	public void setScoreBackground(Color c)
	{
		if(!customModeEnabled)
			scoreArea.setBackground(c);
	}
	
	/**
	 * This allows you to customize the image in the background for the default display for
	 * the number of lives.
	 * @param img The new background image for the "number of lives" area.
	 */
	public void setNumberLivesBackground(Image img)
	{
		if(!customModeEnabled)
			numberLivesArea.setBackgroundImage(img);
	}
	
	/**
	 * This allows you to customize the color of the background for the default display for
	 * the number of lives.
	 * @param c Color to change the background to.
	 */
	public void setNumberLivesBackground(Color c)
	{
		if(!customModeEnabled)
			numberLivesArea.setBackground(c);
	}

	/**
	 * In custom mode, this returns a best guess. In default mode, this returns the height
	 * of the actual game area.
	 * @return	height of game area
	 */
	public static int getHeightOfGameArea()
	{
		return HEIGHT_OF_GAME_AREA;
		
	}
	
	/**
	 * In custom mode, this returns a best guess. In default mode, this returns the actual
	 * width of the game area.
	 * @return
	 */
	public static int getWidthOfGameArea()
	{
		return WIDTH_OF_GAME_AREA;
	}
	
	/**
	 * This method will set the score to be whatever value is passed (negative values are
	 * allowed here).
	 * @param s The new value for the score.
	 */
	public static void setScore(int s)
	{
		score = s;
	}
	
	/**
	 * This method returns the score for the game.
	 * @return The score.
	 */
	public static int getScore()
	{
		return score;
	}
	
	/**
	 * This method increases the score by the specified amount. The parameter must 
	 * be positive.
	 * @param x Amount to increase the score by.
	 */
	public static void incrementScore(int x)
	{
		if(x > 0)
			score += x;
		else
			throw new IllegalArgumentException("Param to incrementScore() must be " +
					"positive. Received " + x);
	}
	
	/**
	 * This method decreases the score by the parameter, which must be a positive value.
	 * @param x Amount to decrease the score by. Must be positive.
	 */
	public static void decrementScore(int x)
	{
		if(x > 0)
			score -= x;
		else
			throw new IllegalArgumentException("Param to decrementScore() must be " +
					"positive. Received " + x);
	}
	
	
	/**
	 * Sets the number of lives left to the parameter.
	 * @param numberLives New value for the number of lives left in the game.
	 */
	public static void setNumberLives(int numberLives)
	{
		GameFrame.numberLives = numberLives;
	}
	

	/**
	 * This method tells you how many lives are left (assuming that you are not in 
	 * custom mode).
	 * @return number of lives available
	 */
	public static int getNumberLives()
	{
		return numberLives;
	}
	
	/**
	 * Decrease the number of lives by the parameter, which must be positive.
	 * @param x A positive number to decrease the total number of lives by.
	 */
	public static void decrementNumberLives(int x)
	{
		if( x > 0 )
			numberLives -= x;
		else
			throw new IllegalArgumentException("Param to decrementNumberLives() must be " + 
					"positive. Received " + x);
	}
	
	/**
	 * Increase the number of lives by the parameter, which must be positive.
	 * @param x A positive number to increase the total number of lives by.
	 */
	public static void incrementNumberLives(int x)
	{
		if(x > 0)
			numberLives += x;
		else
			throw new IllegalArgumentException("Param to incrementNumberLives() must be " + 
					"positive. Received " + x);
	}

	/**
	 * Set the desired frames per second for the animation. Film is shot in 24 fps 
	 * (for reference). The default value here is, in fact, 24. 
	 * @param fps desired frames per second for the animation
	 */
	public static void setFPS(int fps)
	{
		if(fps > 0)
			animationFPS = fps;
	}
	
	/**
	 * This method tells you the current "frames per second" used by the 
	 * {@link AnimationManager}.
	 * @return
	 */
	public static int getFPS()
	{
		return animationFPS;
	}
	
	/**
	 * This method stops the {@link AnimationManager} from animating the game. It can 
	 * be used as a pause.
	 */
	public static void stopAnimation()
	{
		if(animationManager != null)
			animationManager.stopRunning();
	}
	
	/**
	 * This method restarts the animation. After stopping the animation, this method 
	 * can resume (or unpause) it.
	 */
	public static void restartAnimation()
	{
		if(animationManager != null)
			animationManager.stopRunning();
		animationManager = new AnimationManager();
		animationManagerThread = new Thread(animationManager);
		animationManagerThread.start();
	}
	
	/**
	 * This returns an instance of the GameFrame object if one has already been created. 
	 * If one has not been created, then this returns null...this DOES NOT create a new
	 * instance for you.
	 * @return
	 */
	public static GameFrame getInstance()
	{
		if(gameFrameInstance != null)
			return gameFrameInstance;
		else
			throw new IllegalStateException("The getInstance() method can't be called unless you "+
					"have previously constructed your own instance of a GameFrame object. This " +
					"is not designed to enforce a Singleton design pattern...");
	}
	
	/**
	 * This method helps implement the WASD keys' (or arrows) control of the game's 
	 * {@link ControllableObject}.
	 */
	public void keyPressed(KeyEvent evt)
	{
		if(controllableObject == null)
			return;
		
		
		int key = evt.getKeyCode();
		
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
			controllableObject.setMovingUp(true);
		else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
			controllableObject.setMovingDown(true);
		else if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
			controllableObject.setMovingLeft(true);
		else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
			controllableObject.setMovingRight(true);	
	}

	/**
	 * This method helps implement the WASD keys' (or arrows) control of the 
	 * game's {@link ControllableObject}.
	 */
	public void keyReleased(KeyEvent evt)
	{
		if(controllableObject == null)
			return;
		
		
		int key = evt.getKeyCode();
		
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
			controllableObject.setMovingUp(false);
		else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
			controllableObject.setMovingDown(false);
		else if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
			controllableObject.setMovingLeft(false);
		else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
			controllableObject.setMovingRight(false);
		
	}

	/**
	 * Just a stub method. I'm not handling keyTyped events...just the keyPressed
	 * and keyReleased events.
	 */
	public void keyTyped(KeyEvent e)
	{
		//blank on porpoise
		
	}

}
