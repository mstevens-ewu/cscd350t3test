package cs350f20task3.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs350f20task3.A_Segment;
import cs350f20task3.Coordinates;
import cs350f20task3.Join;

/*
 * NOTE: If there is something wrong with your join and coordinate classes, it might cause these tests to fail.
 * If your join and coordinate classes have problems, fix those first and test again.
 */
class A_SegmentTest {

	private TestSegmentClass s1, s2;
	
	@BeforeEach
	void setUp() throws Exception {
		
		s1 = new TestSegmentClass("test_1", new Coordinates(-1.5, -1), new Coordinates(1.5, 1));
		s2 = new TestSegmentClass("test_2", new Coordinates(-1.5, -1), new Coordinates(1.5, 1));
		
	}

	/*
	 * Just testing constructor / getters
	 * Does it put out what was put in?
	 */
	@Test
	void testGetID() {
		assertEquals("test_1", s1.getID());
	}

	@Test
	void testGetTipCCoordinates() {
		assertEquals(-1.5, s1.getTipCCoordinates().getX());
		assertEquals(-1, s1.getTipCCoordinates().getY());
	}

	@Test
	void testGetTipDCoordinates() {
		assertEquals(1.5, s1.getTipDCoordinates().getX());
		assertEquals(1, s1.getTipDCoordinates().getY());
	}

	/*
	 * Doesn't test whether a join is actually valid by the track compiler's standards.
	 */
	@Test
	void testJoinTipC() {
		s1.joinTipC(new Join(s2, true));
		assertEquals("test_2", s1.getTipCJoin().getTargetSegment().getID());
		assertEquals(true, s1.getTipCJoin().isTargetSegmentTipCorD());
		
	}
	
	/*
	 * A join should only be able to be set once
	 */
	@Test
	void testJoinTipCOnlyOnce() {
		s1.joinTipC(new Join(s2, true));
		assertThrows(RuntimeException.class, () -> s1.joinTipC(new Join(s2, true)));
	}
	
	/*
	 * Should throw exception if it is passed a segment that isn't near
	 */
	@Test
	void testJoinTipCNotNear() {
		assertThrows(RuntimeException.class, () -> s1.joinTipC(new Join(new TestSegmentClass("s3", new Coordinates(1000, 1000), new Coordinates(-1000, -1000)), true)));	
	}
	
	/*
	 * A join should be null before anything is joined
	 */
	@Test
	void testJoinTipCBeforeSet() {
		assertEquals(null, s1.getTipCJoin());
	}

	@Test
	void testJoinTipD() {
		s1.joinTipD(new Join(s2, false));
		assertEquals("test_2", s1.getTipDJoin().getTargetSegment().getID());
		assertEquals(false, s1.getTipDJoin().isTargetSegmentTipCorD());
	}
	
	@Test
	void testJoinTipDOnlyOnce() {
		s1.joinTipD(new Join(s2, false));
		assertThrows(RuntimeException.class, () -> s1.joinTipD(new Join(s2, false)));
	}
	
	@Test
	void testJoinTipDNotNear() {
		assertThrows(RuntimeException.class, () -> s1.joinTipD(new Join(new TestSegmentClass("s3", new Coordinates(1000, 1000), new Coordinates(-1000, -1000)), true)));	
	}
	
	@Test
	void testJoinTipDBeforeSet() {
		assertEquals(null, s1.getTipDJoin());
	}
	
	/*
	 * A bare minimum implementation of A_Segment, so that A_Segment can bet tested independent of SegmentStraight
	 */
	static class TestSegmentClass extends A_Segment {

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
