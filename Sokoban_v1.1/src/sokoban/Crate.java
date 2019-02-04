/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v1.0
 * 11/01/19
 * Eclipse V2018-09 4.9.0 
 */



package sokoban;



public class Crate extends MapElement implements MovableMapElmt {

	private String fn = "res/Crate.png";
	
	public Crate()
	{
		
	}
	
	// Construct crate object and set x and y coordinates
	public Crate(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	// Check crate on diamond status, if true return Crate in Place image
	public void crateStatus(boolean cStatus)
	{
		if(cStatus == true)
		{
			this.fn = "res/CrateInPlace.png";
		}
		else this.fn = "res/Crate.png";
	}
	
	@Override
	public String filePath()
	{
		return fn;
	}
	
	// Update location of crate if moved
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
	
}	//END of Crate