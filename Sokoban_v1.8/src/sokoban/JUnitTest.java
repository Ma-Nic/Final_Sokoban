/* Matt Nicol - 09001885
 * UINH17135 - OOP Sokoban v1.8
 * 04/02/19
 * Eclipse V2018-09 4.9.0 
 */

package sokoban;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class JUnitTest {



	@BeforeEach
	void setUp() throws Exception {
	}

	
	@Test
	void testGetX() {
		Crate x = new Crate();
		x.setX(12);
		int answer = x.getX();
		assertEquals(12, answer);
	}
	
	@Test
	void testGetY() {
		Crate y = new Crate();
		y.setY(6);
		int answer = y.getY();
		assertEquals(6, answer);
	}
	
	@Test
	void testCrateOnDiamond() {
		Diamond d = new Diamond();
		d.setHasCrate(true);
		boolean answer = d.getHasCrate();
		assertEquals(true, answer);
	}
	
	@Test
	void testNoCrateOnDiamond() {
		Diamond d = new Diamond();
		d.setHasCrate(false);
		boolean answer = d.getHasCrate();
		assertEquals(false, answer);
	}
	
	
}
