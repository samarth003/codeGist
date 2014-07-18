
package com.leapmotion.codeGist;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import com.leapmotion.leap.Gesture.*;
import com.leapmotion.leap.*;


public class SceneGenerator {    
	  
	  //private LeapFrame leapy;
	  final Label currentlyPlaying = new Label();
	  final ProgressBar progress = new ProgressBar();
	  private ChangeListener<Duration> progressChangeListener;
	  //private Gesture gesture;			//TODO: 24/6/14
	  private MusicPaneListener audioFrame; //TODO: 24/6/14
	  private GestureList gestures;			//TODO: 24/6/14
	  private Controller controller;
	  
	  private List<MediaPlayer> players = new ArrayList<MediaPlayer>();
	  private MediaView mediaView;
	  private Button play;
	  
	  public Scene createScene(MusicPaneListener frameMusic) 
	  {
		  final StackPane layout = new StackPane();
		  audioFrame = frameMusic; //TODO: 24/6/14
		  
		  // determine the source directory for the playlist
		  final File dir = new File("S:/songs/YehJawaniHaiDeewani");
		  if (!dir.exists() || !dir.isDirectory()) 
		  {
			  System.out.println("Cannot find video source directory: " + dir);
			  Platform.exit();
			  return null;
		  }

	    // create some media players.
	    //final List<MediaPlayer> players = new ArrayList<MediaPlayer>();
	    for (String file : dir.list(new FilenameFilter() 
	    {
	      @Override public boolean accept(File dir, String name) 
	      {
	        return name.endsWith(".mp3");
	      }
	    })) players.add(createPlayer("file:///" + (dir + "\\" + file).replace("\\", "/").replaceAll(" ", "%20")));
	    
	    if (players.isEmpty()) 
	    {
	      System.out.println("No audio found in " + dir);
	      Platform.exit();
	      return null;
	    }    

	    // create a view to show the mediaplayers.
	    mediaView = new MediaView(players.get(0));
	    //final MediaView mediaView = new MediaView(players.get(0));
	    final Button skip = new Button("Skip");
	    play = new Button("Pause");
	    //final Button play = new Button("Pause");
	    
	    // play each audio file in turn.
	    	for (int i = 0; i < players.size(); i++) 
	    	{
	    		final MediaPlayer player     = players.get(i);
	    		final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
	    		//if(leapy.isVisible() == false){
	    		player.setOnEndOfMedia(new Runnable() 
	    		{
	    			@Override public void run() 
	    			{
	    				player.currentTimeProperty().removeListener(progressChangeListener);
	    				mediaView.setMediaPlayer(nextPlayer);
	    				//if(!leapy.isVisible())
	    					nextPlayer.play();
	    			}
	    		});
	    		//}
	    	}
	    // allow the user to skip a track.
   		
	   //switchSong();
	   //switchSong(mediaView, players); //TODO: 24/6/14   
	   //playSong();
	   //playSong(mediaView, play); 		//TODO: 24/6/14
	    	
	    skip.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent actionEvent) {
/*	    	  
	        final MediaPlayer curPlayer = mediaView.getMediaPlayer();
	        MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
	        mediaView.setMediaPlayer(nextPlayer);
	        curPlayer.currentTimeProperty().removeListener(progressChangeListener);
	        curPlayer.stop();
	        nextPlayer.play();
*/	
	      }
	    });	
	    
/*-----------------------------------------------------------------------------------------------------------*/	    
/* 	
	     //if(gesture.type() == Gesture.Type.TYPE_SWIPE)
	 	public void switchSong(MediaView mediaView, List<MediaPlayer> players)
	     {
	    	 final MediaPlayer curPlayer = mediaView.getMediaPlayer();
		     MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
		     mediaView.setMediaPlayer(nextPlayer);
		     curPlayer.currentTimeProperty().removeListener(progressChangeListener);
		     curPlayer.stop();
		     nextPlayer.play(); 
	     }
	     
	     if(gesture.type() == Gesture.Type.TYPE_SCREEN_TAP)
	     public void playSong(MediaView mediaView)
	     {
	    	 if ("Pause".equals(play.getText())) {
		          mediaView.getMediaPlayer().pause();
		          play.setText("Play");
		        } else {
		          mediaView.getMediaPlayer().play();
		          play.setText("Pause");
		        }
	     }
	     
*/	    
/*--------------------------------------------------------------------------------------------------------------------------*/	    
	    
	    // allow the user to play or pause a track.
	    play.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent actionEvent) {
/*	    
	    	  if ("Pause".equals(play.getText())) {
	          mediaView.getMediaPlayer().pause();
	          play.setText("Play");
	        } else {
	          mediaView.getMediaPlayer().play();
	          play.setText("Pause");
	        }
*/	        	      
	      }	      
	    });

	    // display the name of the currently playing track.
	    mediaView.mediaPlayerProperty().addListener(new ChangeListener<MediaPlayer>() 
	    		{
	    			@Override public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) 
	    			{
	    				setCurrentlyPlaying(newPlayer);
	    			}
	    		});

	    // start playing the first track.
	    mediaView.setMediaPlayer(players.get(0));
	    mediaView.getMediaPlayer().play();
	    setCurrentlyPlaying(mediaView.getMediaPlayer());

	    // silly invisible button used as a template to get the actual preferred size of the Pause button.
	    Button invisiblePause = new Button("Pause");
	    invisiblePause.setVisible(false);
	    play.prefHeightProperty().bind(invisiblePause.heightProperty());
	    play.prefWidthProperty().bind(invisiblePause.widthProperty());

	    // layout the scene.
	    layout.setStyle("-fx-background-color: cornsilk; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");
	    layout.getChildren().addAll(
	      invisiblePause,
	      VBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(
	        currentlyPlaying,
	        mediaView,
	        HBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(skip, play, progress).build()
	      ).build()
	    );
	    progress.setMaxWidth(Double.MAX_VALUE);
	    HBox.setHgrow(progress, Priority.ALWAYS);
	    return new Scene(layout, 800, 600);
	  }
	  
	  /** sets the currently playing label to the label of the new media player and updates the progress monitor. */
  
	private void setCurrentlyPlaying(final MediaPlayer newPlayer) {
	    progress.setProgress(0);
	    progressChangeListener = new ChangeListener<Duration>() {
	      @Override public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
	        progress.setProgress(1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
	      }
	    };
	    newPlayer.currentTimeProperty().addListener(progressChangeListener);

	    String source = newPlayer.getMedia().getSource();
	    source = source.substring(0, source.length() - ".mp4".length());
	    source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
	    currentlyPlaying.setText("Now Playing: " + source);
	  }
	  
	  /** @return a MediaPlayer for the given source which will report any errors it encounters */

	private MediaPlayer createPlayer(String aMediaSrc) {
	    System.out.println("Creating player for: " + aMediaSrc);
	    final MediaPlayer player = new MediaPlayer(new Media(aMediaSrc));
	    player.setOnError(new Runnable() {
	      @Override public void run() {
	        System.out.println("Media error occurred: " + player.getError());
	      }
	    });
	    return player;
	  }
	

	
	//public void switchSong(MediaView mediaView, List<MediaPlayer> players)
    public void switchSong()
	{
   	 	final MediaPlayer curPlayer = mediaView.getMediaPlayer();
	     MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
	     mediaView.setMediaPlayer(nextPlayer);
	     curPlayer.currentTimeProperty().removeListener(progressChangeListener);
	     curPlayer.stop();
	     nextPlayer.play(); 
    }
	
	//public void playSong(MediaView mediaView, Button play)
    public void playSong()
	{
   	 if ("Pause".equals(play.getText())) {
	          mediaView.getMediaPlayer().pause();
	          play.setText("Play");
	        } else {
	          mediaView.getMediaPlayer().play();
	          play.setText("Pause");
	        }
    }
    
}
