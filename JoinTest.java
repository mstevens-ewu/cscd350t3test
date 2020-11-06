package cs350f20task3.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs350f20task3.A_Segment;
import cs350f20task3.Coordinates;
import cs350f20task3.Join;

class JoinTest {

	Join j1, j2;
	
	@BeforeEach
	void setUp() throws Exception {
		
		j1 = new Join(new TestSegmentClass("test", new Coordinates(1, 1), new Coordinates(-1, -1)), true);
		j2 = new Join(new TestSegmentClass("test", new Coordinates(1, 1), new Coordinates(-1, -1)), false);
	}

	@Test
	void testGetTargetSegment() {
		assertEquals("test", j1.getTargetSegment().getID());
	}

	@Test
	void testIsTargetSegmentTipCorDTrue() {
		assertTrue(j1.isTargetSegmentTipCorD());
	}
	
	@Test
	void testIsTargetSegmentTipCorDFalse() {
		assertFalse(j2.isTargetSegmentTipCorD());
	}
	
	/*
	 * A bare minimum implementation of A_Segment, so that A_Segment can bet tested independent of SegmentStraight
	 */
	private class TestSegmentClass extends A_Segment {

		public TestSegmentClass(String id, Coordinates tipCCoordinates, Coordinates tipDCoordinates) {
			super(id, tipCCoordinates, tipDCoordinates);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Coordinates calculatePosition(boolean isFromTipCorD, double distance) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public double getLength() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}

}
