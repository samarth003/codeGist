/*
package com.leapmotion.codeGist;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

//import com.leapmotion.codeGist.LeapFrame.theHandler;
import com.leapmotion.leap.*;

public class MusicPane extends JFrame 
{
	private JPanel panelMusic;
	public static final int MusicPaneWidth = 800;
	public static final int MusicPaneHeight = 600;
	
	private MusicPaneListener MusicPanel;
	private Controller controllerOne;
	
	public MusicPane(LeapFrame leapFrame)
	{
		//JFrame musicWindow = new JFrame();
		panelMusic = new JPanel();
		
		MusicPanel = new MusicPaneListener(this, leapFrame); //TODO:define a constructor for music pane in the leap motion listener class
		controllerOne = new Controller();
		controllerOne.addListener(MusicPanel);
		
		this.setSize(MusicPaneWidth, MusicPaneHeight);
		panelMusic.setBackground(Color.CYAN);
		//this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().add(panelMusic);
		
		musicHandler mHandle = new musicHandler();
		panelMusic.addMouseMotionListener(mHandle);
	}
	
	private class musicHandler implements MouseMotionListener
	{
		public void mouseMoved(MouseEvent event)
		{
	
		}
		public void mouseDragged(MouseEvent event)
		{

		}
	}
}
*/