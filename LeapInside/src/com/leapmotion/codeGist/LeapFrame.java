package com.leapmotion.codeGist;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.leapmotion.leap.Controller;
public class LeapFrame extends JFrame
{
	private JPanel panelLeap;
	public static final int winWidth = 800;
	public static final int winDepth = 600;
	
	private LeapListener panelListener;
	private Controller controller;
	//private SceneGenerator playMe;
	//public LeapFrame(SceneGenerator scenePlay)	/*---constructor defining the window characteristics---*/
	public LeapFrame()
	{	
		panelLeap = new JPanel();
		panelListener = new LeapListener(this);
		//playMe = scenePlay;
		//panelListener = new LeapListener(this, scenePlay);
		controller = new Controller();
		controller.addListener(panelListener);
		
		this.setSize(winWidth, winDepth);
		panelLeap.setBackground(Color.WHITE);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().add(panelLeap);
		
		theHandler handler = new theHandler();
		panelLeap.addMouseMotionListener(handler);
		
	}//end of LeapFrame
	
	
	
	/*class to handle the mouse motion events*/

	private class theHandler implements MouseMotionListener
	{
		public void mouseMoved(MouseEvent event)
		{
			
		}
		public void mouseDragged(MouseEvent event)
		{

		}
	}
		
/*
	private class theHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if((event.getSource() == panelLeap))
			{
				
			}
		}
	}
*/	
	public static void main(String args[])
	{
		new LeapFrame();
		//new LeapFrame(playMe);
	}
}
