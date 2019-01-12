/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v1.0
 * 11/01/19
 * Eclipse V2018-09 4.9.0 
 */


package sokoban;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class SokobanDriver extends Application {
	
	// FXML Launcher to start game
	public void start(Stage primaryStage) throws IOException
	{
			SokobanGame sokoban = new SokobanGame();
			Parent root = FXMLLoader.load(getClass().getResource("SokobanGUI.fxml"));
			Scene scene = new Scene(root);
			
	        primaryStage.setTitle("Let's Play Sokoban!");
			primaryStage.setScene(scene);
			primaryStage.show();
		
			
		// Record player input and work through movements, end game when all crates are in place
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    
		public void handle(KeyEvent event) {
			
			if(sokoban.currentLevel.gamePlay) 
			{			
				sokoban.currentLevel.getKeyInput(event.getCode());
				if(sokoban.currentLevel.levelComplete()) 
				{	
					sokoban.currentLevel.gamePlay = false;
				}
			 }
		}
		});
	}
	
	
	// Main program
	public static void main(String args[]) 
	{
		launch(args);
	}

}
