package com.leapmotion.codeGist;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.JFrame;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.*;

public class MusicPaneListener extends Listener
{
	String clockwiseness;
	//int count;
	private MusicPane musicChann;
	//private static final int MAX_FRAME_COUNT = 20;
	private LeapFrame listenerPane;
	
	public MusicPaneListener(MusicPane musPane)
	{
		super();
		musicChann = musPane;
		//listenerPane = new LeapFrame();
	}
	public void onConnect(Controller controller)
	{
		System.out.println("controllerOne has been connected");
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
	}
	public void onExit(Controller controller)
	{
		System.out.println("exiting controllerOne");
	}
	public void onFrame(Controller controller)
	{
		Frame currFrameOne = controller.frame();
		Frame prevFrameOne = controller.frame(1);
		
		InteractionBox ibox = currFrameOne.interactionBox();
		
		if(!currFrameOne.hands().isEmpty())
		{
			int handsCount = currFrameOne.hands().count();
			if(handsCount == 1)
			{
				Hand handValue = currFrameOne.hands().get(0);
				int fingerCount = currFrameOne.fingers().count();
				if(fingerCount == 1)
				{
					Finger fingers = currFrameOne.fingers().frontmost();
					Vector stabilizedPosition = fingers.stabilizedTipPosition();
					//InteractionBox ibox = new InteractionBox();
					Vector normalizedPosition = ibox.normalizePoint(stabilizedPosition);
					System.out.println("The normalized position is:" + normalizedPosition); //check for the vector position					
					float Xnorm = normalizedPosition.getX() * musicChann.MusicPaneWidth;//pane.getWidth();
					float Ynorm = musicChann.MusicPaneHeight * (1 - normalizedPosition.getY());
					//System.out.println(pane.winWidth);
					System.out.println(Xnorm + "," + Ynorm);					
				}
				GestureRecog(currFrameOne.gestures(), controller);
			}
		}
	}
	
	public void GestureRecog(GestureList gestures, Controller controllerOne)
	{
		for (Gesture gesture : gestures)
		{
				switch(gesture.type())
				{
				case TYPE_CIRCLE:
					CircleGesture circle = new CircleGesture(gesture);
					//String clockwiseness;

					if(circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/2)
					{
						clockwiseness = "clockwise on musicPane";
						System.out.println(clockwiseness);
						//new MusicPane();
					}
					else
					{
						clockwiseness = "counterclockwise on musicPane";
						System.out.println(clockwiseness);
					}
					break;
				default:
					System.out.println("Unknown gesture type.");
					break;
				}			
		}
		
		if(clockwiseness == "counterclockwise on musicPane")
		{
			//listenerPane.setVisible(true);
			musicChann.setVisible(false);
			listenerPane.setVisible(true);
		}
		
	}	
}

