/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v2.0
 * 19/02/19
 * Eclipse V2018-09 4.9.0 
 */


package sokoban;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class SokobanController {
	
	protected int level;
	Level lvl = new Level();

    @FXML
    private MenuBar MenuBar;

    @FXML
    private MenuItem MenuLvl_1;

    @FXML
    private MenuItem MenuLvl_2;

    @FXML
    private MenuItem MenuLvl_3;

    @FXML
    private MenuItem MenuLvl_4;

    @FXML
    private MenuItem MenuLvl_5;

    @FXML
    private MenuItem GameInstructions;

    @FXML
    private MenuItem AboutGame;

    @FXML
    private MenuItem QuitGame;

    @FXML
    private Button ResetLevel;
    
    @FXML
    private Button leftButton;

    @FXML
    private Button upButton;

    @FXML
    private Button downButton;

    @FXML
    private Button rightButton;

    @FXML
    private Label LevelNum;
    
    public void setLevelNum(int levelNum)
    {
    	String ln = String.valueOf(levelNum);
    	LevelNum.setText(ln);
    }

    @FXML
    private Label MoveNum;
    
    public void setMoveNum()
    {
    	int currentMoves = lvl.getCurrMoves();
    	String cm = String.valueOf(currentMoves);
    	MoveNum.setText(cm);
    }
    
    @FXML
    private ImageView BackgroundImage;

    @FXML
    private GridPane LevelArea;
    
    public void setImage(MapElement[][] map) {
             
        getLevelArea().setHgap(0); 		//horizontal gap in pixels
        getLevelArea().setVgap(0);		//vertical gap in pixels
        getLevelArea().setPadding(new Insets(10, 10, 10, 10)); 	// Padding around the whole grid                
        
        refreshMap(map);
    }
    
    public void refreshMap(MapElement[][] map)
    {
        int y = 0;
        int x = 0;
        
        while (map[x][y] != null) {
            if (map[x][y] != null) {
                Image imageFile = new Image(new File (map[x][y].filePath()).toURI().toString(), 32, 32, true, true);
                getLevelArea().add(new ImageView(imageFile), x, y);
            }
            x++;
            if (map[x][y] == null) {
                y++;
                x = 0;
            }
        }
    }
    
    @FXML
    void goDown(ActionEvent event) {
    	lvl.getKeyInput("DOWN");
    }

    @FXML
    void goLeft(ActionEvent event) {
    	lvl.getKeyInput("LEFT");
    }

    @FXML
    void goRight(ActionEvent event) {
    	lvl.getKeyInput("RIGHT");
    }

    @FXML
    void goUp(ActionEvent event) {
    	lvl.getKeyInput("UP");
    }
    
    @FXML
    void closeGame(ActionEvent event) {			// Exit game
    System.exit(0);	
    }

    @FXML
    void resetLevel(ActionEvent event) {		// Reset current level
    	lvl.resetMap();
    	SokobanGame.resetLevel(lvl, level);
    	System.out.println("level reset");
    	refreshMap(lvl.getMap());
    }

    @FXML
    void selectLvl_1(ActionEvent event) {		// Select Level 1
    	level = 1;								// Set Level number to 1
    	this.getLevel();						// Load level
    }

    @FXML
    void selectLvl_2(ActionEvent event) {
    	level = 2;
    	this.getLevel();						// Load level
    }

    @FXML
    void selectLvl_3(ActionEvent event) {
    	level = 3;
    	this.getLevel();						// Load level
    }

    @FXML
    void selectLvl_4(ActionEvent event) {
    	level = 4;
    	this.getLevel();						// Load level
    }

    @FXML
    void selectLvl_5(ActionEvent event) {
    	level = 5;
    	this.getLevel();						// Load level
    }
    
    private void getLevel()
    {
    	SokobanGame.selectLevel(lvl, level);	// Load Level file
    	setImage(lvl.getMap());					// Build and display map  
    	System.out.println(level);				// Print level to console
    	setLevelNum(level);						// Set Level display number
    }

    @FXML
    void showAboutGame(ActionEvent event) throws IOException			// Show About Information 
    {			

		File textFile = new File("res/About.txt");	
		if(textFile.exists()){
	        Desktop desktop = Desktop.getDesktop();
	        if(textFile.exists()) desktop.open(textFile);;
		}else{
			System.out.println("Cannot find file : " + textFile.getAbsolutePath() + "/" + textFile.getName());
		}
    	
    }

    @FXML
    void showGameInstrc(ActionEvent event) throws IOException	// Show Game Instructions
    {
		File textFile = new File("res/Game_Instructions.txt");	
		if(textFile.exists()){
	        Desktop desktop = Desktop.getDesktop();
	        if(textFile.exists()) desktop.open(textFile);;
		}else{
			System.out.println("Cannot find file : " + textFile.getAbsolutePath() + "/" + textFile.getName());
		}
    	
    }

	public GridPane getLevelArea() {
		return LevelArea;
	}

	public void setLevelArea(GridPane levelArea) {
		LevelArea = levelArea;
	}

}
