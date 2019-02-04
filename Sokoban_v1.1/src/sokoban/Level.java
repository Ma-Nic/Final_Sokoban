/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v1.0
 * 11/01/19
 * Eclipse V2018-09 4.9.0 
 */


package sokoban;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Level {

		
	private WarehouseKeeper keeper;					// Declare player object
	private ArrayList<Wall> walls;					// Declare ArrayList for Wall objects
	private ArrayList<Tile> tiles;					// Declare ArrayList for Tile objects
	private ArrayList<Diamond> diamonds;			// Declare ArrayList for Diamond objects
	private ArrayList<Crate> crates;				// Declare ArrayList for Crate objects
	private int currMoves;							// Declare int to hold the current player move
	private MapElement[][] map;						// Declare multi-array to store map characters
	boolean gamePlay;								// Declare boolean for if game is running -true- or not -false-
	
	public Level()
	{
		// Level constructor with default values for variables
		walls = new ArrayList<>();
		tiles = new ArrayList<>();
		diamonds = new ArrayList<>();
		crates = new ArrayList<>();
		currMoves = 0;		
		map = new MapElement[23][13];
		gamePlay = true;
		
	}
	
	public void loadLevel(String levelSelected)
	{
		String filePath = "res/" + levelSelected + ".txt";
	
		try (BufferedReader fileIn = new BufferedReader(new FileReader(filePath)))
		{
		    int x = 0;
		    int y = 0;  
			String input;
			
			// Loop through level file to read all lines, add to Map Element array
		    while ((input = fileIn.readLine()) != null) 
		    {   			  
		    	System.out.println(input);

		    	for (int i = 0; i < input.length(); i++) {
		    	  char mapIcon = input.charAt(i);
	                  switch (mapIcon) {			// Switch case while reading file dependent on what character is found
	                  	  case ' ': 		//tile	                  		
	                  		  Tile tile = new Tile(x,y);			// Create new instance of object
	                  		  setMapElmt(x, y, tile);				// Add object to the map array
	                  		  tiles.add(tile);						// Add object to it's object array
	                  		  break;
	                      case 'X':			//wall
	                    	  Wall wall = new Wall(x,y);
	                  		  setMapElmt(x, y , wall);
	                  		  walls.add(wall);
	                          break;
	                      case '.':			//diamond	                          
	                    	  Diamond diamond = new Diamond(x,y);
	                  		  setMapElmt(x, y, diamond);
	                  		  diamonds.add(diamond);
	                          break;
	                      case '*':			//crate
	                    	  Crate crate = new Crate(x,y);
	                  		  setMapElmt(x, y, crate);
	                  		  crates.add(crate);
	                          break;
	                      case '@':	        //warehouseKeeper
	                    	  WarehouseKeeper keeper = new WarehouseKeeper(x,y);
	                          setMapElmt(x, y, keeper);
	                          break;
	                      default:
	                          System.out.println("A wild Character has appeared..");
	                          System.out.println("You blacked out..");
	                          System.exit(0);		    			  
	                  }	//END switch case

	                    x++;            // Every loop, increase x and y to fit within the grid
	                    if (x >= input.length()) 
	                    {
	                        y++;
	                        x = 0;
	                    }	                    
		    	  } //END for loop
		    } //END while loop
		
		}	catch (FileNotFoundException fnfe) {				// Catch error if file is missing
			System.out.println("Sorry, I can't seem to find " + levelSelected);
			System.out.println("Make sure it's where it should be!");
			fnfe.printStackTrace();
			System.exit(0);
		}	catch (IOException e) {						// Catch any read errors with file
			e.printStackTrace();
		}	//END try catch	
	}	//END Level loadLevel

	// Build map array with map elements translated from level file
	public void setMapElmt(int x, int y, MapElement mapElement) 
	{
	       map[x][y] = mapElement;
	}

	// Return map array
	public MapElement[][] getMap()
	{
		return map;
	}
	
	// Return current number of player moves
	public int getCurrMoves() 
	{
		return currMoves;
	}
	
	private void checkCrate(Crate crateOnDiamond) 
	{	/**
		 *  Loop through diamonds array and compare coordinates to crate
		 *  @returns True if crate is on diamond, false if not
		 */
		for(int dC=0; dC<diamonds.size(); dC++) 
		{
			Diamond onDiamond = diamonds.get(dC);
			if(onDiamond.getX() == crateOnDiamond.getX() && onDiamond.getY() == crateOnDiamond.getY())
			{
				crateOnDiamond.crateStatus(true);
			}
		}//END for loop
		crateOnDiamond.crateStatus(false);;
	 }	
	
	
 	private void diamondsWithCrates() 
 	{	/*
 		 *  Loop through diamonds array and check for crates
 		 *  If crate is present, set hasCrate to true
 		 */
		for(int dC=0; dC<diamonds.size(); dC++) 
		{
			Diamond onDiamond = diamonds.get(dC);
			onDiamond.setHasCrate(false);
			
			for(int cD=0; cD<crates.size(); cD++) 
			{
				Crate crateOnDiamond = crates.get(cD);
				if(onDiamond.getX() == crateOnDiamond.getX() && onDiamond.getY() == crateOnDiamond.getY()) 
				{
					onDiamond.setHasCrate(true);
				}
			}
		}
	}
 	
	/**
	 * Checks all diamond tiles for crates, if all are true then level is complete 
	 * @return True if all diamonds have tiles, false if not
	 */
	boolean levelComplete() {
		
		int cratesPlaced = 0;
		
		for(int i=0; i<diamonds.size(); i++) 
		{			
    		Diamond finalD = diamonds.get(i);
    		if (finalD.getHasCrate()) 
    		{	
    			cratesPlaced++;
    		}
    	}
		if(cratesPlaced == diamonds.size()) 
		{
			return true;
		}
		return false;
	}
 	
 	/**
 	 * Checks the player movement. Switch case implemented based on direction
 	 * Checks if wall or crate is in way of movement and states whether move is legal
 	 * @param whatIsMoving	Player or crate instance
 	 * @param direction	Input from keyboard stroke
 	 * @return If move is legal or illegal
 	 */
    private String checkMovement(MovableMapElmt whatIsMoving, String direction) {

       switch (direction) 
       {
        case "UP":
        	for (int wTile = 0; wTile < walls.size();wTile++) 	// Check through wall array
        	{	
        		Wall wall = walls.get(wTile);
        		if (whatIsMoving.collisionUp(wall)) 			// Check if player or crate will collide with wall
        		{		
        			return "Illegal Move";						// Return Illegal if so
        		}
        	}
        	
        	if(whatIsMoving instanceof WarehouseKeeper) 		// If warehouse keeper is moving
        	{	
        		for (int cI = 0; cI < crates.size();cI++) 		// Check through crate array
        		{
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionUp(crate)) 
        			{	
        				if(checkMovement(crate,direction)=="OK") // If movement is allowed
        				{	
        					crate.moveLocation(0, -1);			 // Move crate
        					checkCrate(crate);					 // Check if on diamond
        					diamondsWithCrates();				 // Check diamond tiles for crates
        					return "Crate";
        				} else if(checkMovement(crate,direction)=="Illegal Move") 	// Else throw Illegal move
        				{		
        					return "Illegal Move";
        				}
        			 }
        		  }
        		return "OK";
        	  }
        	
        	if(whatIsMoving instanceof Crate) 					// If crate is moving
        	{	
        		for (int cI = 0; cI < crates.size();cI++) 		// Loop through crate array
        		{	
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionUp(crate))		// Check if crate movement is legal
        			{	
        				return "Illegal Move";					// Return false if not
        			}
        		 }
        		return "OK";									// Else move is OK
        	 }
        	
        	
        case "LEFT":
        	for (int wTile = 0; wTile < walls.size();wTile++) 
        	{	
        		Wall wall = walls.get(wTile);
        		if (whatIsMoving.collisionLeft(wall)) 
        		{		
        			return "Illegal Move";
        		}
        	}
        	
        	if(whatIsMoving instanceof WarehouseKeeper) 
        	{	
        		for (int cI = 0; cI < crates.size();cI++) 
        		{
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionLeft(crate)) 
        			{	
        				if(checkMovement(crate,direction)=="OK") 
        				{	
        					crate.moveLocation(0, -1);
        					checkCrate(crate);
        					diamondsWithCrates();
        					return "Crate";
        				} else if(checkMovement(crate,direction)=="Illegal Move") 
        				{		
        					return "Illegal Move";
        				}
        			 }
        		  }
        		return "OK";
        	  }
        	
        	if(whatIsMoving instanceof Crate) 
        	{	
        		for (int cI = 0; cI < crates.size();cI++) 
        		{	
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionLeft(crate))
        			{	
        				return "Illegal Move";
        			}
        		 }
        		return "OK";
        	 }
        	
        	
        case "DOWN":
        	for (int wTile = 0; wTile < walls.size();wTile++) 
        	{	
        		Wall wall = walls.get(wTile);
        		if (whatIsMoving.collisionDown(wall)) 
        		{		
        			return "Illegal Move";
        		}
        	}
        	
        	if(whatIsMoving instanceof WarehouseKeeper) 
        	{	
        		for (int cI = 0; cI < crates.size();cI++) 
        		{
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionDown(crate)) 
        			{	
        				if(checkMovement(crate,direction)=="OK") 
        				{	
        					crate.moveLocation(0, -1);
        					checkCrate(crate);
        					diamondsWithCrates();
        					return "Crate";
        				} else if(checkMovement(crate,direction)=="Illegal Move") 
        				{		
        					return "Illegal Move";
        				}
        			 }
        		  }
        		return "OK";
        	  }
        	
        	if(whatIsMoving instanceof Crate) 
        	{	
        		for (int cI = 0; cI < crates.size();cI++) 
        		{	
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionDown(crate))
        			{	
        				return "Illegal Move";
        			}
        		 }
        		return "OK";
        	 }
        	
        case "RIGHT":
        	for (int wTile = 0; wTile < walls.size();wTile++) 
        	{	
        		Wall wall = walls.get(wTile);
        		if (whatIsMoving.collisionRight(wall)) 
        		{		
        			return "Illegal Move";
        		}
        	}
        	
        	if(whatIsMoving instanceof WarehouseKeeper) 
        	{	
        		for (int cI = 0; cI < crates.size();cI++) 
        		{
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionRight(crate)) 
        			{	
        				if(checkMovement(crate,direction)=="OK") 
        				{	
        					crate.moveLocation(0, -1);
        					checkCrate(crate);
        					diamondsWithCrates();
        					return "Crate";
        				} else if(checkMovement(crate,direction)=="Illegal Move") 
        				{		
        					return "Illegal Move";
        				}
        			 }
        		  }
        		return "OK";
        	  }
        	
        	if(whatIsMoving instanceof Crate) 
        	{	
        		for (int cI = 0; cI < crates.size();cI++) 
        		{	
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionRight(crate))
        			{	
        				return "Illegal Move";
        			}
        		 }
        		return "OK";
        	 }       	
        }	//END Switch case
       return "OK";       
    }	//END checkMovement
 	
 	
    // Work out what checks to implement based on player input
    void getKeyInput(String keyStroke) {
    	
//    	String moveDir = keyStroke.toString();
    	switch (keyStroke) {
    	
    	case "UP":    
    		
    		if(checkMovement(keeper,keyStroke) == "Illegal Move") 
    		{	
    			break;
    		}
    		keeper.moveLocation(0, -1);
    		currMoves++;
    		break;
        	
        case "DOWN":   
        	
        	if(checkMovement(keeper,keyStroke) == "Illegal Move") 
        	{	
        		break;
        	}
        	keeper.moveLocation(0, 1);
        	currMoves++;
        	break;
        	
        case "LEFT":
        	
        	if(checkMovement(keeper,keyStroke) == "Illegal Move") 
        	{
        		break;
        	}
        	keeper.moveLocation(-1, 0);
        	currMoves++;
        	break;
        	
        case "RIGHT":
        	if(checkMovement(keeper,keyStroke)=="Illegal Move") 
        	{	
        		break;
        	}
        	keeper.moveLocation(1, 0);
        	currMoves++;
        	break;
        	
        default:
        	break;
        	}
    	levelComplete();
    	System.out.println("Your total number of moves: "+currMoves);
    	}

    
}	//END Level
