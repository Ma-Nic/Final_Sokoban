/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v1.8
 * 04/02/19
 * Eclipse V2018-09 4.9.0 
 */


package sokoban;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SokobanDriver extends Application {
	
	static SokobanController myControllerHandle;
	
	// FXML Launcher to start game
	public void start(Stage primaryStage) throws IOException
	{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SokobanGUI.fxml"));
			Parent root = loader.load();
			myControllerHandle = (SokobanController)loader.getController();
			
			Scene scene = new Scene(root);
			
	        primaryStage.setTitle("Let's Play Sokoban!");
			primaryStage.setScene(scene);
			primaryStage.show();
	}
	
	
	// Main program
	public static void main(String args[]) 
	{
		launch(args);
	}

}
