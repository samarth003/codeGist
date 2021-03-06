
package com.leapmotion.codeGist;

import java.io.*;
import java.util.*;
import javafx.application.Platform;
import javafx.beans.value.*;
import javafx.embed.swing.JFXPanel;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.util.Duration;
import javax.swing.*;
import com.leapmotion.leap.*;

/** Example of playing all mp3 audio files in a given directory 
 * using a JavaFX MediaView launched from Swing 
 */

public class JavaFXVideoPlayerLaunchedFromSwing extends JFrame{
  
	public static final int MusicPaneWidth = 800;
	public static final int MusicPaneHeight = 600;
	private MusicPaneListener MusicPanel;
	private Controller controller;
	public JavaFXVideoPlayerLaunchedFromSwing(LeapFrame leapFrame, SceneGenerator scenePlay){
		
		super("Beats");
		MusicPanel = new MusicPaneListener(this, leapFrame, scenePlay);
		controller = new Controller();
		controller.addListener(MusicPanel);
	    final JFXPanel fxPanel = new JFXPanel();
	    
	    this.setSize(MusicPaneWidth, MusicPaneHeight);//TODO
	    this.setLocationRelativeTo(null);//TODO
	    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    this.getContentPane().add(fxPanel);
	  
	    Platform.runLater(new Runnable() {
	    //SwingUtilities.invokeLater(new Runnable(){
	      @Override public void run() {
	        initFX(fxPanel);        
	      }
	    });

		
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override public void run() {
//				initAndShowGUI();
//			}
//		});
	}

  public void initFX(JFXPanel fxPanel) {
    // This method is invoked on JavaFX thread
    Scene scene = new SceneGenerator().createScene(MusicPanel); //TODO: check on the argument
    fxPanel.setScene(scene);
  }  
}
