/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v1.3
 * 25/01/19
 * Eclipse V2018-09 4.9.0 
 */

package sokoban;

public abstract interface MovableMapElmt {			// Interface for movable map element required methods

	// Sets new coordinates for movable objects
	void moveLocation(int x, int y);
	
    // Check for collisions when moving up
    boolean collisionUp(MapElement checkColl);
  
    // Check for collisions when moving down
    boolean collisionDown(MapElement checkColl);
    
    // Check for collisions when moving left
    boolean collisionLeft(MapElement checkColl);

    // Check for collisions when moving right
    boolean collisionRight(MapElement checkColl);
   
}	//END MovableMapElmt
	

