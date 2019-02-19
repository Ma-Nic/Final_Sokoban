/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v2.0
 * 19/02/19
 * Eclipse V2018-09 4.9.0 
 */

package sokoban;



public class Diamond extends MapElement {
	
	private boolean hasCrate = false;
	
	public Diamond()
	{
		
	}

	// Construct diamond object and set x and y coordinates
	public Diamond(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * @param dhc Boolean value to set whether the diamond has a crate or not
	 */
	public void setHasCrate(boolean dhc) 
	{
			hasCrate = dhc;
	}
    
	/**
	 * @return Return whether diamond has a crate or not
	 */
	public boolean getHasCrate()
	{
		return hasCrate;
	}
	
	@Override
	public String filePath()
	{
		String fn = "res/Diamond.png";
		return fn;
	}

}	//END of Diamond
