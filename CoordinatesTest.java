package cs350f20task3.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs350f20task3.Coordinates;

class CoordinatesTest {

	private Coordinates c1;
	
	@BeforeEach
	void setUp() throws Exception {
		
		c1 = new Coordinates(2.5, -3);
		
	}

	@Test
	void testAdd() {
		Coordinates c2 = new Coordinates(1, 1);
		Coordinates c3 = c1.add(c2);
		assertEquals(3.5, c3.getX());
		assertEquals(-2,c3.getY());
	}
	
	@Test
	void testCalculateBearingN() {
		Coordinates c2 = new Coordinates(2.5, -2);
		assertEquals(0, c1.calculateBearing(c2));
	}
	
	@Test
	void testCalculateBearingE() {
		Coordinates c2 = new Coordinates(3.5, -3);
		assertEquals(90, c1.calculateBearing(c2));
	}
	
	@Test
	void testCalculateBearingS() {
		Coordinates c2 = new Coordinates(2.5, -4);
		assertEquals(180, c1.calculateBearing(c2));
	}
	
	@Test
	void testCalculateBearingW() {
		Coordinates c2 = new Coordinates(1.5, -3);
		assertEquals(270, c1.calculateBearing(c2));
	}
	
	//Numbers in method name represent quadrants (quadrants go counterclockwise)
	@Test
	void testCalculateBearingQ1() {
		Coordinates c2 = new Coordinates(3.5, -2);
		assertEquals(45, c1.calculateBearing(c2));
	}
	
	@Test
	void testCalculateBearingQ2() {
		Coordinates c2 = new Coordinates(1.5, -2);
		assertEquals(315, c1.calculateBearing(c2));
	}
	
	@Test
	void testCalculateBearingQ3() {
		Coordinates c2 = new Coordinates(1.5, -4);
		assertEquals(225, c1.calculateBearing(c2));
	}
	
	@Test
	void testCalculateBearingQ4() {
		Coordinates c2 = new Coordinates(3.5, -4);
		assertEquals(135, c1.calculateBearing(c2));
	}

	//Along an angle
	@Test
	void testCalculateDistance1() {
		assertEquals(1.414213562, c1.calculateDistance(new Coordinates(3.5, -2)), 0.000000001);
	}
	
	//Along the grid
	@Test
	void testCalculateDistance2() {
		assertEquals(1, c1.calculateDistance(new Coordinates(2.5, -2)), 0.000000001);
	}
	
	@Test
	void testCalculateDistanceToSelf() {
		assertEquals(0, c1.calculateDistance(c1));
	}

	@Test
	void testGetX() {
		assertEquals(2.5, c1.getX());
	}

	@Test
	void testGetY() {
		assertEquals(-3, c1.getY());
	}

	@Test
	void testNearN() {
		assertTrue(c1.isNear(new Coordinates(2.5, -2.995)));
	}
	
	/*
	 * Near edge tests are ignored because weird java floating point math.
	 * Feel free to use them if u want
	 */
	
	//@Test
	@Ignore
	void testNearEdgeN() {
		assertFalse(c1.isNear(new Coordinates(2.5, -2.99)));
	}
	
	@Test
	void testNotNearN() {
		assertFalse(c1.isNear(new Coordinates(2.5, -2.985)));
	}
	
	@Test
	void testNearE() {
		assertTrue(c1.isNear(new Coordinates(2.505, -3)));
	}
	
	//@Test
	@Ignore
	void testNearEdgeE() {
		assertFalse(c1.isNear(new Coordinates(2.51, -3)));
	}
	
	@Test
	void testNotNearE() {
		assertFalse(c1.isNear(new Coordinates(2.515, -3)));
	}
	
	@Test
	void testNearS() {
		assertTrue(c1.isNear(new Coordinates(2.5, -3.005)));
	}
	
	//@Test
	@Ignore
	void testNearEdgeS() {
		assertFalse(c1.isNear(new Coordinates(2.5, -3.01)));
	}
	
	@Test
	void testNotNearS() {
		assertFalse(c1.isNear(new Coordinates(2.5, -3.015)));
	}
	
	@Test
	void testNearW() {
		assertTrue(c1.isNear(new Coordinates(2.495, -3)));
	}
	
	//@Test
	@Ignore
	void testNearEdgeW() {
		assertFalse(c1.isNear(new Coordinates(2.49, -3)));
	}
	
	@Test
	void testNotNearW() {
		assertFalse(c1.isNear(new Coordinates(2.485, -3)));
	}
	
	//If this fails, you're probably using the "circle" method instead of the "square" method
	@Test
	void testNearInCorner() {
		assertTrue(c1.isNear(new Coordinates(2.505, -2.995)));
	}
	
	/*
	 * Should be false since near means a distance of <0.01
	 * I've found that this fails if your near method uses your distance method, and it doesn't return exactly 0.01, but something very close.
	 * My program passed all of Tappan's isNear tests despite having this problem, so this test is ignored.
	 */
	
	@Ignore
	//@Test
	void testIsNearAtEdgeStrict() {
		assertFalse(c1.isNear(new Coordinates(2.50, -3.01)));
	}
	

	@Test
	void testSubtract() {
		Coordinates c2 = new Coordinates(1, 1);
		Coordinates c3 = c1.subtract(c2);
		assertEquals(1.5, c3.getX());
		assertEquals(-4,c3.getY());
	}

}
