package org.francisparker.mmaunu.gameengine;
import java.util.ArrayList;


/**
 * This class runs the animation logic in a separate thread. You can configure it to
 * (roughly) achieve a desired framerate (a desired number of frames per second) though
 * there are certainly no guarantees here. You might want to shoot for something like 24 fps
 * or thereabouts. This class is designed to work with the {@link GameFrame} object.
 * 
 * @author mmaunu
 *
 */
public class AnimationManager implements Runnable
{	
	
	private boolean running;
	
	/**
	 * Constructs an AnimationManager and prepares it for running an animation. This will
	 * not actually start an animation, however, without creating a Thread and invoking
	 * the start() method on that Thread object.
	 */
	public AnimationManager()
	{
		running = true;
	}

	
	/**
	 * This method will stop the animaton from running. This means that all objects onscreen 
	 * will stop moving. Once stopped, you can't restart the animation via an AnimationManager 
	 * method. However, you can use the {@link GameFrame#restartAnimation()} method to do so.
	 */
	public void stopRunning()
	{
		running = false;
	}
	
	
	/**
	 * This method controls the speed of the animation. It cycles through the contents of the 
	 * {@link GameFrame}'s list of {@link Drawable} objects and draws them. For {@link Actable}
	 * objects, they are also commanded to act() once per animation cycle.
	 */
	public void run()
	{
		ArrayList<Drawable> list;
		
		
		int desiredLoopTimeInMS, fps;
		
		long beginLoopTime, endLoopTime;
		
		int sleepTime;
		
		while(running)
		{
			beginLoopTime = System.currentTimeMillis();
			
			fps = GameFrame.getFPS();
			if(fps <= 0) 
				fps = 24;
			desiredLoopTimeInMS = 1000/fps;
			
			list = GameFrame.getDrawableList();

			
			for( int pos = 0; pos < list.size(); pos++ )
			{
				Drawable d = list.get( pos );
				if( d instanceof Actable )
					((Actable)d).act();
			}

			
			GameFrame.getInstance().repaint();

			
			endLoopTime = System.currentTimeMillis();
			
			sleepTime = desiredLoopTimeInMS - (int)(endLoopTime - beginLoopTime);
			
			if(sleepTime < 5)
				sleepTime = 5;
			try
			{
				Thread.sleep(sleepTime);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
