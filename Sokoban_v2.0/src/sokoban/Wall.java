/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v2.0
 * 19/02/19
 * Eclipse V2018-09 4.9.0 
 */

package sokoban;



public class Wall extends MapElement {
	
	public Wall()
	{
		
	}

	// Construct wall object and set x and y coordinates
	public Wall(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	@Override
	public String filePath()
	{
		String fn = "res/Wall.png";
		return fn;
	}
	
	
}	//END of Wall