/*
 * Fireworks.java
 * Source: Canvas.weber.edu
 * 
 */
//package Mahjong;

import java.awt.*;
import static java.lang.Math.*;
import java.util.*;
import javax.swing.*;


/**
 * Implements a "rewards" display consisting of fireworks streaking into the sky and exploding in star bursts.
 */

public class Fireworks implements Runnable
{
	private	Color[]		colors = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.WHITE };
	private	Random		rand = new Random();
	private	JPanel		panel;			// the panel on which the fireworks are drawn
	private	int		width;			// panel width
	private	int		height;			// panel height

	private	boolean		sound = true;		// play an "explosion" sound for each star burst
	private	int		explosions = 50;	// the number of explosions (0 ==> infinite)
	private	int		maxDelay = 1000;	// the maximum delay between consecutive launches
	private	Thread		fireThread = null;	// the thread that fires the fireworks
	private	ThreadGroup	group = new ThreadGroup("Fireworks");
	private	Color		oldBG;			// the old background color


	/**
	 * Creates a fireworks display that can be placed in a frame or an option pane.  The display is
	 * drawn on an internal panel that is retrieved by calling {@link #getPanel()}.
	 */

	public Fireworks()
	{
		panel = new JPanel();
	}



	/**
	 * Creates a fireworks display that is drawn on the provided panel.
	 * @param panel The panel on which the fireworks display is drawn.  Use this constructor to draw
	 * on an existing and visible panel.
	 */

	public Fireworks(JPanel panel)
	{
		this.panel = panel;
	}



	/**
	 * Configures the fireworks display to either play the sound of an exploding firework or not.
	 * @param sound Play an explosion sound clip if true or execute silently if false.
	 */

	public void setSound(boolean sound)
	{
		this.sound = sound;
	}



	/**
	 * Configure the fireworks display by setting the number of and the maximum time between
	 * consecutive firework launches.
	 * @param explosions The number of fireworks launched; set to 0 for an unending display.
	 * @param maxDelay The maximum time (in milliseconds) between setting off fireworks; the
	 * average time between launches is maxDelay / 2.
	 */

	public void setExplosions(int explosions, int maxDelay)
	{
		this.explosions = explosions;
		this.maxDelay = maxDelay;
	}



	/**
	 * Starts the fireworks display.  This method starts a new thread, making it safe to call
	 * this method with the dispatch thread.
	 */

	public void fire()
	{
		width = panel.getWidth();
		height = panel.getHeight();
		oldBG = panel.getBackground();
                System.out.println("[" + width + ", " + height + "]");

		panel.setBackground(Color.BLACK);

		(fireThread = new Thread(group, this)).start();
	}



	/**
	 * Stops the fireworks display.  Fireworks already fired are allowed to finish but no new
	 * fireworks are fired after this method is called.
	 */

	public void stop()
	{
		try
		{
			explosions = -1;
			fireThread.join();
			group.interrupt();
			panel.setBackground(oldBG);
			panel.repaint();
		}
		catch (InterruptedException ie)
		{
			JOptionPane.showMessageDialog(panel, "Error stopping fireworks",
					"Fireworks Error", JOptionPane.ERROR_MESSAGE);
		}
	}



	/**
	 * Launches the fireworks at random intervals uniformly distributed in [0 .. maxDelay-1].
	 */

	public void run()
	{
		try
		{
			for (int i = 0; explosions == 0 || i < explosions; i++)
			{
				new Thread(group, new StarBurst()).start();
				Thread.sleep(rand.nextInt(maxDelay));
			}
		}
		catch (InterruptedException ie) {}
	}



	/**
	 * Gets the panel on which the fireworks display is drawn.
	 * @return the fireworks display, either the internal panel created or the external panel
	 * associated during construction.
	 */

	public JPanel getPanel()
	{
		return panel;
	}


	/**
	 * Draws a line segment in XOR mode on the fireworks display panel.
	 * @param x0 X-coordinate of the starting point.
	 * @param y0 Y-coordinate of the starting point.
	 * @param x1 X-coordinate of the ending point.
	 * @param y1 Y-coordinate of the ending point.
	 * @param color The color of the line segment.
	 */

	synchronized private void drawLine(int x0, int y0, int x1, int y1, Color color)
	{
		Graphics	g = panel.getGraphics();
		g.setXORMode(color);
		g.drawLine(x0, y0, x1, y1);
		g.setPaintMode();
	}


	/**
	 * Draws a "star burst" explosion consisting of lines radiating from a central point.
	 * @param x X-coordinate of the center of the explosion.
	 * @param y Y-coordinate of the center of the explosion.
	 * @param r The radius of the explosion.
	 * @param color The color of the radiating lines.
	 */

	synchronized private void explode(int x, int y, int r, Color color)
	{
		for (double phi = 0; phi < 2*PI; phi += PI/16)
			new AnimatedLine(x, y, r, phi, 50, color, true).start();
	}



	/**
	 * Represents a complete star burst explosion with sound.
	 */

	public class StarBurst implements Runnable
	{
		private	PlayClip	explosion = new PlayClip("audio/explosion.wav");
		private	int		r = rand.nextInt(50) + 50;
		private	Color		color = colors[rand.nextInt(colors.length)];
		private	double		theta = PI * (rand.nextInt(30) + 75) / 180;


		/**
		 * Plays the sound of an exploding firework.
		 */

		public void play()
		{
			if (sound)
				explosion.play();
		}


		/**
		 * Animates the star burst lines radiating from the center of the explosion.
		 */

		public void run()
		{
			try
			{
				int	length = height - 2 * r;
				int	x0 = rand.nextInt(width-400)+200;
				int	y0 = height + r;
				int	x1 = (int)round(x0 + length * cos(theta));
				int	y1 = (int)round(y0 - length * sin(theta));

				new AnimatedLine(x0, y0, length, theta, r, color, false).start().join();

				play();
				explode(x1, y1, 2 * r, color);
			}
			catch (InterruptedException ie) {}
		}
	}



	/**
	 * Draws an animated line segment that appears to move or to lengthen over time.
	 */

	public class AnimatedLine implements Runnable
	{
		int	xStart;				// X-coordinate of the staring of the line segment
		int	yStart;				// Y-coordinate of the staring of the line segment
		int	step;				// how much the line length changes each iteration
		int	length;				// the final length of the line
		double	theta;				// the line's angle of elevation
		Color	color;				// the color of the line
		boolean	burst;				// star burst (true) or ascending firework (false)


		/**
		 * Constructs an animated line that begins at (xStart,yStart) and moves to (xEnd,yEnd)
		 * in increments of step size.
		 * @param xStart The X-coordinate of the start of the line segment.
		 * @param yStart The Y-coordinate of the start of the line segment.
		 * @param xEnd The X-coordinate of the end of the line segment.
		 * @param yEnd The Y-coordinate of the end of the line segment.
		 * @param step The animation step size; how much the line grows by.
		 * @param color The color of the line.
		 * @param burst The line is a star burst (true) or ascending firework (false).  Star burst
		 * lines reach their full length before they are erased; ascending fireworks are drawn and
		 * erased so that they appear to by shooting upwards.
		 */

		public AnimatedLine(int xStart, int yStart, int xEnd, int yEnd, int step,
				Color color, boolean burst)
		{
			this.step = step;
			this.color = color;
			this.step = step;
			this.burst = burst;

			theta = atan2(yEnd + yStart, xEnd - xStart);
			double	dx = xEnd - xStart;
			double	dy = yEnd - yStart;
			length = (int)round(sqrt(dx*dx + dy*dy));
		}


		/**
		 * Constructs an animated line that begins at (xStart,yStart) and continues until it
		 * reaches "length" in length.
		 * @param xStart The X-coordinate of the start of the line segment.
		 * @param yStart The Y-coordinate of the start of the line segment.
		 * @param length The total length of the line.
		 * @param theta The angle, measured from the horizontal, at which the line is drawn
		 * @param step The animation step size; how much the line grows by.
		 * @param color The color of the line.
		 * @param burst The line is a star burst (true) or ascending firework (false).  Star burst
		 * lines reach their full length before they are erased; ascending fireworks are drawn and
		 * erased so that they appear to by shooting upwards.
		 */

		public AnimatedLine(int xStart, int yStart, int length, double theta, int step,
				Color color, boolean burst)
		{
			this.xStart = xStart;
			this.yStart = yStart;
			this.theta = theta;
			this.length = length;
			this.step = step;
			this.color = color;
			this.burst = burst;
		}


		/**
		 * Starts the animation.
		 */

		public Thread start()
		{
			Thread	t = new Thread(group, this);
			t.start();
			return t;
		}



		/**
		 * Animates the line by drawing and erasing segments every 100 milliseconds.
		 */

		public void run()
		{
			try
			{
				int	x0 = xStart;
				int	y0 = yStart;

				while (distance(x0 - xStart, y0 - yStart) < length * length)
				{
					int	x1 = (int)round(x0 + step * cos(theta));
					int	y1 = (int)round(y0 - step * sin(theta));

					drawLine(x0, y0, x1, y1, color);
					Thread.sleep(100);
					if (!burst)
						drawLine(x0, y0, x1, y1, color);
					else
						drawLine(x0, y0, x0, y0, color);

					x0 = x1;
					y0 = y1;
				}

				Thread.sleep(100);
				if (burst)
					drawLine(xStart, yStart, x0, y0, color);
			}
			catch (InterruptedException ie) {}
		}



		// simplifies calculating the distance between two points for loop control

		private int distance(int dx, int dy)
		{
			return dx * dx + dy * dy;
		}
	}


	/**
	 * Test and demonstration.
	 */

	public static void main(String[] args)
	{
		JFrame		frame = new JFrame();
		Fireworks	fw = new Fireworks();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 800);
		frame.add(fw.getPanel());
		frame.setVisible(true);

		fw.setExplosions(0, 1000);
		fw.fire();


		try
		{
			Thread.sleep(10000);
			fw.stop();
		}
		catch (InterruptedException ie) {}
	}
}

