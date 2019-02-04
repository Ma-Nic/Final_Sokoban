/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v1.3
 * 25/01/19
 * Eclipse V2018-09 4.9.0 
 */



package sokoban;


public class Tile extends MapElement {
		
	public Tile()
	{
		
	}
	
	// Construct tile object and set x and y coordinates
	public Tile(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
		
	@Override
	public String filePath()
	{
		String fn = "res/Tile.png";
		return fn;
	}
	
}	//END of Tile