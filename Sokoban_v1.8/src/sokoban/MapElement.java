/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v1.8
 * 04/02/19
 * Eclipse V2018-09 4.9.0 
 */

package sokoban;

public abstract class MapElement {			// Super class for all map elements, cannot be instantiated itself

	private int x_value = 0;
	private int y_value = 0;
	
	//Get x coord
	public int getX() 
	{
		return x_value;
	}
	
	//Get y coord
	public int getY() 
	{
		return y_value;
	}
	
	//Set x coord
	public void setX(int newX)
	{
		x_value = newX;
	}
	
	//Set y coord
	public void setY(int newY)
	{
		y_value = newY;
	}
	
	public String filePath() 
	{
		return null;
	}
	
}	// END MapElement
