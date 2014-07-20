package com.leapmotion.codeGist;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.*;


public class LeapListener extends Listener
{
	private LeapFrame pane;
	String clockwiseness;

	private JavaFXVideoPlayerLaunchedFromSwing musicChann;
	private SceneGenerator geneScene;
	
	public LeapListener(LeapFrame windowLeap, SceneGenerator scenePlay)
	{
		super();
		pane = windowLeap;
		geneScene = scenePlay;
		musicChann = new JavaFXVideoPlayerLaunchedFromSwing(windowLeap, scenePlay); 
	}
	public void onConnect(Controller controller)
	{
		System.out.println("controller has been connected");
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
	}
	public void onExit(Controller controller)
	{
		System.out.println("exited");
	}
	public void onFrame(Controller controller){
		if(!pane.isVisible())
		{
			return;
		}
		
		Frame currFrame = controller.frame();
		Frame prevFrame = controller.frame(1);	
		InteractionBox ibox = currFrame.interactionBox();
		
		if(!currFrame.hands().isEmpty())
		{
			int handsCount = currFrame.hands().count();
			if(handsCount == 1)
			{
				Hand handValue = currFrame.hands().get(0);
				int fingerCount = currFrame.fingers().count();
				if(fingerCount == 1)
				{
					Finger fingers = currFrame.fingers().frontmost();
					Vector stabilizedPosition = fingers.stabilizedTipPosition();
					Vector normalizedPosition = ibox.normalizePoint(stabilizedPosition);
					System.out.println("The normalized position is:" + normalizedPosition); 				
					float Xnorm = normalizedPosition.getX() * pane.winWidth;
					float Ynorm = pane.winDepth * (1 - normalizedPosition.getY());
					System.out.println(Xnorm + "," + Ynorm);					
				}
					GestureRecog(currFrame.gestures(), controller);	
			}
		}	
	}	
	@SuppressWarnings("deprecation")
	public void GestureRecog(GestureList gestures, Controller controller)
	{
		for (Gesture gesture : gestures)
		{
				switch(gesture.type())
				{
				case TYPE_CIRCLE:
					CircleGesture circle = new CircleGesture(gesture);

					if(circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/2)
					{
						clockwiseness = "clockwise";
						System.out.println(clockwiseness);
					}
					else
					{
						clockwiseness = "counterclockwise";
						System.out.println(clockwiseness);
					}
					break;
				default:
					System.out.println("Unknown gesture type.");
					break;
				}			
		}
		if(clockwiseness == "clockwise")
		{
			/*----generate a new frame consisting of music panel----*/
			pane.setVisible(false);
			musicChann.setVisible(true);
		}		
	}		
}
