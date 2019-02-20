/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v2.0
 * 19/02/19
 * Eclipse V2018-09 4.9.0 
 */

package sokoban;

public class SokobanGame {
	
	protected Level currentLevel;
	
	public SokobanGame()
	{
		
	}
	
	public SokobanGame(int lvl)
	{
		selectLevel(currentLevel, lvl);
	}
	
	/**
	 * @param level Passes the level number based on user choice from SokobanController
	 * @param Lvl Passes level class from SokobanController
	 */
	public static void selectLevel(Level Lvl, int level)
	{
		
		switch (level) 
		{	
		case 1: 
			Lvl.loadLevel("level1");
			break;
		case 2: 
			Lvl.loadLevel("level2");
			break;
		case 3: 
			Lvl.loadLevel("level3");
			break;
		case 4: 
			Lvl.loadLevel("level4");
			break;
		case 5: 
			Lvl.loadLevel("level5");
			break;
		default: System.out.println("Please Select a Level");
		break;
		}	
	}		//END of selectLevel
		
	/**
	 * @param lvl Passes level class from SokobanController
	 * @param level Passes current level from SokobanController
	 */
	public static void resetLevel(Level lvl, int level) 
	{
		selectLevel(lvl, level);		// Reset current level
		lvl.setCurrMoves(0);
	}	//END of resetLevel

	
	
}	//END of SokobanGame
