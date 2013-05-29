/*
 * PlayClip.java
 * Source: canvas.weber.edu
 */
//package Mahjong;

import	java.net.*;
import	java.io.*;
import	javax.swing.JOptionPane;
import	javax.sound.sampled.*;


/**
 * Plays a short sound clip and terminates.  The sound clip file (wav or au) must be located in the
 * same directory or in a directory below the directory containing PlayClip.class.  This restriction
 * allows play clip to read clips from a jar file.
 */


public class PlayClip implements LineListener
{
	private	URL	url;
	private	boolean	showErrors;



	/**
	 * Constructs an object that can play the sound clip stored in file <code>name</code>.
	 * This is a convenience method that is the same as calling <code>PlayClip(name, false)</code>.
	 * @param name The sound clip file.
	 */

	public PlayClip(String name)
	{
		this(name, false);
	}



	/**
	 * Constructs an object that can play the sound clip stored in file <code>name</code>.
	 * @param name The sound clip file.
	 * @param showErrors If true, show any errors that occur while trying to open the sound clip;
	 * otherwise, do not show the errors.
	 */

	public PlayClip(String name, boolean showErrors)
	{
		this.showErrors = showErrors;
		url = PlayClip.class.getResource(name);
	}
	
	/**
	 * Plays the audio clip.
	 */
	
	public void play()
	{
		if (AudioSystem.isLineSupported(Port.Info.SPEAKER))
		{
			try
			{
				Clip			clip = null;		// the sound clip
				AudioInputStream	stream = AudioSystem.getAudioInputStream(url);
				
				clip = AudioSystem.getClip();
				clip.addLineListener(this);
				clip.open(stream);
				clip.start();
			}
			catch (LineUnavailableException lue)
			{
				if (showErrors)
					JOptionPane.showMessageDialog(null,
							"Speaker is unavailable for playback",
							"Sound Clip Error",
							JOptionPane.ERROR_MESSAGE);
			}
			catch (UnsupportedAudioFileException uafe)
			{
				if (showErrors)
					JOptionPane.showMessageDialog(null,
							"Sound clip file type is not supported",
							"Sound Clip Error",
							JOptionPane.ERROR_MESSAGE);
			}
			catch (IOException ioe)
			{
				if (showErrors)
					JOptionPane.showMessageDialog(null,
							"File I/O error: " + ioe,
							"Sound Clip Error",
							JOptionPane.ERROR_MESSAGE);
			}
		}
	}



	/**
	 * Detects when the sound clip completes and deallocates resources.
	 * This method is required by the LineListener interface and is called when the state of
	 * the sound clip changes.
	 * @param event an object encapsulating event details.
	 */

	public void update(LineEvent event)
	{
		if (event.getType() == LineEvent.Type.STOP)
			event.getLine().close();
	}



	/**
	 * Class test and validation.
	 */

	public static void main(String[] args)
	{
		try
		{
			PlayClip clip = new PlayClip("audio/stone-scraping.wav", true);

			clip.play();
			Thread.sleep(2000);

			clip.play();
			Thread.sleep(2000);
		}
		catch (InterruptedException ie) {}
	}
}
