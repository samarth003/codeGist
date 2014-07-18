package com.leapmotion.codeGist;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.JFrame;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.*;

public class MusicPaneListener extends Listener
{
	String clockwiseness;
	//TODO:private MusicPane musicChann;
	private JavaFXVideoPlayerLaunchedFromSwing musicChann;
	private SceneGenerator geneScene;
	private LeapFrame listenerPane;
	
	//TODO:public MusicPaneListener(MusicPane musPane, LeapFrame frLeap)
	public MusicPaneListener(JavaFXVideoPlayerLaunchedFromSwing musPane, LeapFrame frLeap)
	{
		super();
		musicChann = musPane;
		listenerPane = frLeap;
		//geneScene = scenePlay;
		//listenerPane = new LeapFrame();
	}
	public void onConnect(Controller controller)
	{
		System.out.println("controllerOne has been connected");
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
	}
	public void onExit(Controller controller)
	{
		System.out.println("exiting controllerOne");
	}
	public void onFrame(Controller controller)
	{
		if(!musicChann.isVisible())
		{
			return;
		}
		
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
					//System.out.println("The normalized position is:" + normalizedPosition); //check for the vector position					
					float Xnorm = normalizedPosition.getX() * musicChann.MusicPaneWidth;//pane.getWidth();
					float Ynorm = musicChann.MusicPaneHeight * (1 - normalizedPosition.getY());
					//System.out.println(pane.winWidth);
					//System.out.println(Xnorm + "," + Ynorm);					
				}
				GestureRecog(currFrameOne.gestures(), controller);
			}
		}
	}
	
	public void GestureRecog(GestureList gestures, Controller controller)
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
				case TYPE_SWIPE:
					SwipeGesture swipe = new SwipeGesture(gesture);
					//if(swipe.direction())
						clockwiseness = "switch to next media file";
						System.out.println(clockwiseness);
					break;
				case TYPE_SCREEN_TAP:
					ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
					clockwiseness = "Let the media file Play";
					System.out.println(clockwiseness);
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
		
		if(clockwiseness == "switch to next media file"){
			//System.out.println(clockwiseness);
			geneScene.switchSong();
			
		}
		
		if(clockwiseness == "Let the media file Play"){
			//System.out.println(clockwiseness);
			geneScene.playSong();
		}		
	}	
}

