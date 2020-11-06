package cs350f20task3.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs350f20task3.Coordinates;
import cs350f20task3.SegmentStraight;

/*
 * NOTE: If there's something wrong with A_Segment, this may fail. If any tests from A_Segment fail, fix that stuff first and try again.
 */
class SegmentStraightTest {

	SegmentStraight s1;
	
	@BeforeEach
	void setUp() throws Exception {
		s1 = new SegmentStraight("test", new Coordinates(1.5, 2.0), new Coordinates(-1.5, -2.0));
	}
	
	/*
	 * Just testing constructor / getters
	 * Does it put out what was put in?
	 */
	@Test
	void testGetID() {
		assertEquals("test", s1.getID());
	}

	@Test
	void testGetTipCCoordinates() {
		assertEquals(1.5, s1.getTipCCoordinates().getX());
		assertEquals(2, s1.getTipCCoordinates().getY());
	}

	@Test
	void testGetTipDCoordinates() {
		assertEquals(-1.5, s1.getTipDCoordinates().getX());
		assertEquals(-2, s1.getTipDCoordinates().getY());
	}
	
	//According to the Q & A, zero length should not be allowed
	@Test
	void testZeroLength() {
		assertThrows(RuntimeException.class, () -> new SegmentStraight("test", new Coordinates(1.0, -1.0), new Coordinates(1.0, -1.0)));
	}

	@Test
	void testGetLength() {
		assertEquals(5.0, s1.getLength());
	}
	
	//If you used distance formula, these two shouldn't matter, but they're here on account of weird code.
	@Test
	void testGetLengthVertical() {
		assertEquals(2.0, new SegmentStraight("s2", new Coordinates(0, -1), new Coordinates(0, 1)).getLength());
	}
	
	@Test
	void testGetLengthHorizontal() {
		assertEquals(2.0, new SegmentStraight("s2", new Coordinates(-1, 0), new Coordinates(1, 0)).getLength());
	}

}
