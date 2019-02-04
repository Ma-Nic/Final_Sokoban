/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v1.2
 * 24/01/19
 * Eclipse V2018-09 4.9.0 
 */


package sokoban;



public class WarehouseKeeper extends MapElement implements MovableMapElmt {
	

	public WarehouseKeeper()
	{
		
	}
	
	// Construct warehouse keeper object and set x and y coordinates
	public WarehouseKeeper(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	@Override
	public String filePath()
	{
		String fn = "res/WarehouseKeeper.png";
		return fn;
	}
	

	@Override
	public void moveLocation(int x, int y) {
        int newX = getX() + x;
        int newY = getY() + y;
        setX(newX);
        setY(newY);
	}
	

	/**
	 * Checks up movement (y - 1) for collisions against other elements
	 * @return true if collision is detected 
	 */
	@Override
	public boolean collisionUp(MapElement checkColl) {
		if(getY()-1 == checkColl.getY() && getX() == checkColl.getX()) 
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks down movement (y + 1) for collisions against other elements
	 * @return true if collision is detected 
	 */
	@Override
	public boolean collisionDown(MapElement checkColl) {
		if(getY()+1 == checkColl.getY() && getX() == checkColl.getX()) 
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks left movement (x - 1) for collisions against other elements
	 * @return true if collision is detected 
	 */
	@Override
	public boolean collisionLeft(MapElement checkColl) {
		if(getX()-1 == checkColl.getX() && getY() == checkColl.getY()) 
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks right movement (x + 1) for collisions against other elements
	 * @return true if collision is detected 
	 */
	@Override
	public boolean collisionRight(MapElement checkColl) {
		if(getX()+1 == checkColl.getX() && getY() == checkColl.getY()) 
		{
			return true;
		}
		return false;
	}	


}	//END of WarehouseKeeper