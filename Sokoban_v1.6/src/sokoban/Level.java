/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v1.5
 * 25/01/19
 * Eclipse V2018-09 4.9.0 
 */


package sokoban;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Level {

		
	private WarehouseKeeper keeper;					// Declare player object
	private ArrayList<Wall> walls;					// Declare ArrayList for Wall objects
	private ArrayList<Tile> tiles;					// Declare ArrayList for Tile objects
	private ArrayList<Diamond> diamonds;			// Declare ArrayList for Diamond objects
	private ArrayList<Crate> crates;				// Declare ArrayList for Crate objects
	private int currMoves = 0;						// Declare int to hold the current player move
	private MapElement[][] map;						// Declare multi-array to store map characters
	boolean gamePlay;								// Declare boolean for if game is running -true- or not -false-
	private Tile tile;

	
	public Level()
	{
		// Level constructor with default values for variables
		walls = new ArrayList<>();
		tiles = new ArrayList<>();
		diamonds = new ArrayList<>();
		crates = new ArrayList<>();
		tile = new Tile();
		currMoves = 0;		
		map = new MapElement[25][15];
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
	                    	  keeper = new WarehouseKeeper(x,y);
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
	
	/** 
	 * Return current number of player moves
	 * @return String value of current moves
	 */
	public int getCurrMoves() 
	{
		return currMoves;
	}
	
	/** 
	 * Return current number of player moves
	 * @param newMoves Input for new player move count
	 */
	public void setCurrMoves(int newMoves)
	{
		currMoves = newMoves;
	}
	
	/**
	 *  Loop through diamonds array and compare coordinates to crate
	 *  @param crateOnDiamond Crate instance to be checked if on a diamond tile
	 *  @returns True if crate is on diamond, false if not
	 */
	private void checkIfCrateOnDiamond(Crate crateOnDiamond) 
	{
		for(int dC=0; dC<diamonds.size(); dC++) 
		{
			Diamond diamondWithCrate = diamonds.get(dC);
			if(diamondWithCrate.getX() == crateOnDiamond.getX() && diamondWithCrate.getY() == crateOnDiamond.getY())
			{
				crateOnDiamond.crateStatus(true);
			}
		}//END for loop
		crateOnDiamond.crateStatus(false);;
	 }	//END checkIfCrateOnDiamond	
	
	
	/**
	 *  Loop through diamonds array and check for crates
	 *  If crate is present, set hasCrate to true
	 */
 	private void diamondsWithCrates() 
 	{	
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
    		Diamond allDiamondsWithCrates = diamonds.get(i);
    		if (allDiamondsWithCrates.getHasCrate() == true) 
    		{	
    			cratesPlaced++;
    		}
    	}
		if(cratesPlaced == diamonds.size()) 
		{
			return true;
		}
		return false;
	}	//END levelComplete
 	
 	/**
 	 * Checks the player movement. Switch case implemented based on direction
 	 * Checks if wall or crate is in way of movement and states whether move is legal
 	 * @param whatIsMoving	Player or crate instance
 	 * @param direction	Input from keyboard stroke
 	 * @return If move is legal or illegal
 	 */
    private String checkMovement(MovableMapElmt whatIsMoving, String direction) {
    	
//    	System.out.println(direction);
//    	System.out.println(whatIsMoving);
       switch (direction) 
       {
        case "UP":

        	for (int wTile = 0; wTile < walls.size();wTile++) 	// Check through wall array
        	{	
        		Wall wall = walls.get(wTile);
        		if (whatIsMoving.collisionUp(wall)) 	// Check if player or crate will collide with wall
        		{		
        			System.out.println("cUP - player detected wall");
        			return "Illegal Move";						// Return Illegal if so
        		} 
        	}
        	
        	if(whatIsMoving instanceof WarehouseKeeper) 					// If warehouse keeper is moving
        	{	
        		for (int cI = 0;cI < crates.size();cI++) 					// Check through crate array
        		{
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionUp(crate)) 
        			{	
        				if(checkMovement(crate,direction)=="OK") 			// If movement is allowed
        				{	
        					crate.moveLocation(0, -1);			 						// Move crate
        					setMapElmt(crate.getX(),crate.getY(),crate);				// Update map element array with new object
        					checkIfCrateOnDiamond(crate);		 						// Check if on diamond
        					diamondsWithCrates();										// Check diamond tiles for crates
        	            	SokobanDriver.myControllerHandle.refreshMap(getMap());		// Refresh map tiles
        	            	System.out.println("cUP - player moved crate");
        					return "Crate";
        				} else if(checkMovement(crate,direction)=="Illegal Move") 		// Else throw Illegal move
        				{		
                			System.out.println("cUP - player moving crate detected wall");
        					return "Illegal Move";
        				}
        			 }
        		  }
            	currMoves++;												// Update total moves
            	SokobanDriver.myControllerHandle.setMoveNum();				// Update move counter display
        		return "OK";
        	  }
        	
        	if(whatIsMoving instanceof Crate) 						// If crate is moving
        	{	
        		for (int cI = 0; cI < crates.size();cI++) 			// Loop through crate array
        		{	
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionUp(crate))	// Check if crate movement is legal
        			{	
            			System.out.println("cUP - crate detected wall");
        				return "Illegal Move";						// Return false if not
        			}
        		 }
            	SokobanDriver.myControllerHandle.refreshMap(getMap());		// Refresh map tiles
        		return "OK";										// Else move is OK
        	 }
        	
        	
        case "LEFT":

        	for (int wTile = 0; wTile < walls.size();wTile++) 
        	{	
        		Wall wall = walls.get(wTile);
        		if (whatIsMoving.collisionLeft(wall)) 
        		{		
        			System.out.println("cLEFT - player detected wall");
        			return "Illegal Move";
        		}
        	}
        	
        	if(whatIsMoving instanceof WarehouseKeeper) 
        	{	
            	currMoves++;												// Update total moves
            	SokobanDriver.myControllerHandle.setMoveNum();				// Update move counter display
        		for (int cI = 0; cI < crates.size();cI++) 
        		{
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionLeft(crate)) 
        			{	
        				if(checkMovement(crate,direction)=="OK") 
        				{	
        					crate.moveLocation(-1, 0);
        					setMapElmt(crate.getX(),crate.getY(),crate);				// Update map element array with new object
        					checkIfCrateOnDiamond(crate);
        					diamondsWithCrates();
        	            	SokobanDriver.myControllerHandle.refreshMap(getMap());		// Refresh map tiles
        	            	System.out.println("cLEFT - player moved crate");
        					return "Crate";
        				} else if(checkMovement(crate,direction)=="Illegal Move") 
        				{		
                			System.out.println("cLEFT - player moving crate detected wall");
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
            			System.out.println("cLEFT - crate detected wall");
        				return "Illegal Move";
        			}
        		 }
            	SokobanDriver.myControllerHandle.refreshMap(getMap());		// Refresh map tiles
        		return "OK";
        	 }
        	
        	
        case "DOWN":

        	for (int wTile = 0; wTile < walls.size();wTile++) 
        	{	
        		Wall wall = walls.get(wTile);
        		if (whatIsMoving.collisionDown(wall)) 
        		{		
        			System.out.println("cDOWN - player detected wall");
        			return "Illegal Move";
        		}
        	}
        	
        	if(whatIsMoving instanceof WarehouseKeeper) 
        	{	
            	currMoves++;												// Update total moves
            	SokobanDriver.myControllerHandle.setMoveNum();				// Update move counter display
        		for (int cI = 0; cI < crates.size();cI++) 
        		{
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionDown(crate)) 
        			{	
        				if(checkMovement(crate,direction)=="OK") 
        				{	
        					crate.moveLocation(0, 1);
        					setMapElmt(crate.getX(),crate.getY(),crate);				// Update map element array with new object
        					checkIfCrateOnDiamond(crate);
        					diamondsWithCrates();
        	            	SokobanDriver.myControllerHandle.refreshMap(getMap());		// Refresh map tiles
        	            	System.out.println("cDOWN - player moved crate");
        					return "Crate";
        				} else if(checkMovement(crate,direction)=="Illegal Move") 
        				{		
                			System.out.println("cDOWN - player moving crate detected wall");
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
            			System.out.println("cDOWN - crate detected wall");
        				return "Illegal Move";
        			}
        		 }
            	SokobanDriver.myControllerHandle.refreshMap(getMap());		// Refresh map tiles
        		return "OK";
        	 }
        	
        case "RIGHT":

        	for (int wTile = 0; wTile < walls.size();wTile++) 
        	{	
        		Wall wall = walls.get(wTile);
        		if (whatIsMoving.collisionRight(wall)) 
        		{		
        			System.out.println("cRIGHT - player detected wall");
        			return "Illegal Move";
        		}
        	}
        	
        	if(whatIsMoving instanceof WarehouseKeeper) 
        	{	
            	currMoves++;												// Update total moves
            	SokobanDriver.myControllerHandle.setMoveNum();				// Update move counter display
        		for (int cI = 0; cI < crates.size();cI++) 
        		{
        			Crate crate = crates.get(cI);
        			if (whatIsMoving.collisionRight(crate)) 
        			{	
        				if(checkMovement(crate,direction)=="OK") 
        				{	
        					crate.moveLocation(1, 0);
        					setMapElmt(crate.getX(),crate.getY(),crate);				// Update map element array with new object
        					checkIfCrateOnDiamond(crate);
        					diamondsWithCrates();
        	            	SokobanDriver.myControllerHandle.refreshMap(getMap());		// Refresh map tiles
        	            	System.out.println("cRIGHT - player moved crate");
        					return "Crate";
        				} else if(checkMovement(crate,direction)=="Illegal Move") 
        				{		
                			System.out.println("cRIGHT - player moving crate detected wall");
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
            			System.out.println("cRIGHT - crate detected wall");
        				return "Illegal Move";
        			}
        		 }
            	SokobanDriver.myControllerHandle.refreshMap(getMap());		// Refresh map tiles
        		return "OK";
        	 }       	
        }	//END Switch case
       return "OK";       
    }	//END checkMovement
 	
 	
    /**
     *  Work out what checks to implement based on player input
     * @param keyCode String value passed from direction button clicked
     */
    void getKeyInput(String keyCode) {
    	   	

    	switch (keyCode) {
    	
    	case "UP":    
    		
    		if(checkMovement(keeper,keyCode) == "Illegal Move") 		//Confirm if move is legal
    		{	
    			break;													// Do nothing if not
    		}
//			setMapElmt(keeper.getX(),keeper.getY(),tile);			// Update map element array with new object
            SokobanDriver.myControllerHandle.LevelArea.getChildren().remove(keeper.getX(),keeper.getY());
    		keeper.moveLocation(0, -1);									// Move warehouse keeper if it is
			setMapElmt(keeper.getX(),keeper.getY(),keeper);			// Update map element array with new object
        	SokobanDriver.myControllerHandle.refreshMap(getMap());		// Refresh map tiles


    		break;
        	
        case "DOWN":   
        	
        	if(checkMovement(keeper,keyCode) == "Illegal Move") 
        	{	
        		break;
        	}
//			setMapElmt(keeper.getX(),keeper.getY(),tile);			// Update map element array with new object
            SokobanDriver.myControllerHandle.LevelArea.getChildren().remove(keeper.getX(),keeper.getY());
        	keeper.moveLocation(0, 1);
			setMapElmt(keeper.getX(),keeper.getY(),keeper);			// Update map element array with new object
        	SokobanDriver.myControllerHandle.refreshMap(getMap());	// Refresh map tiles
        	break;
        	
        case "LEFT":
        	
        	if(checkMovement(keeper,keyCode) == "Illegal Move") 
        	{
        		break;
        	}
//			setMapElmt(keeper.getX(),keeper.getY(),tile);			// Update map element array with new object
            SokobanDriver.myControllerHandle.LevelArea.getChildren().remove(keeper.getX(),keeper.getY());
        	keeper.moveLocation(-1, 0);
			setMapElmt(keeper.getX(),keeper.getY(),keeper);			// Update map element array with new object
        	SokobanDriver.myControllerHandle.refreshMap(getMap());		// Refresh map tiles
        	break;
        	
        case "RIGHT":
        	
        	if(checkMovement(keeper,keyCode) =="Illegal Move") 
        	{	
        		break;
        	}
//			setMapElmt(keeper.getX(),keeper.getY(),tile);			// Update map element array with new object
            SokobanDriver.myControllerHandle.LevelArea.getChildren().remove(keeper.getX(),keeper.getY());
        	keeper.moveLocation(1, 0);
			setMapElmt(keeper.getX(),keeper.getY(),keeper);			// Update map element array with new object
        	SokobanDriver.myControllerHandle.refreshMap(getMap());		// Refresh map tiles
        	break;
        	
        default:
        	break;
        	}
    	while (levelComplete()==true)
    	{
    		System.out.println("Level Completed!");
    		System.out.println("Select a new level");
    		break;
    	}
    	System.out.println("Your total number of moves: "+currMoves);
    	}

    
}	//END Level
