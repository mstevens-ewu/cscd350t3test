package cs350f20task3.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs350f20task3.A_Segment;
import cs350f20task3.Coordinates;
import cs350f20task3.SegmentStraight;
import cs350f20task3.TrackManager;
import cs350f20task3.tests.A_SegmentTest.TestSegmentClass;

/*
 * NOTE: If there's something wrong with any other part of the program this will likely fail, fix those first and test again.
 */
class TrackCompileTest {

	private TrackManager t;
	
	@BeforeEach
	void setUp() throws Exception {
		t = new TrackManager();
	}

	//Tests compile without adding any segments
	@Test
	void testCompileEmpty() {
		assertThrows(RuntimeException.class, () -> t.compile());
	}
	
	//Tests compile with one segment added
	@Test
	void testCompileOne() {
		t.addSegments(new TestSegmentClass("test", new Coordinates(1, 1), new Coordinates(-1, -1)));
		assertThrows(RuntimeException.class, () -> t.compile());
	}
	
	//Tests compile with segments that should be joined C<==>D
	@Test
	void testCompileCtoD() {
		SegmentStraight s1, s2, s3, s4;
		
		s1 = new SegmentStraight("s1", new Coordinates(-1, 1), new Coordinates(1, 1));
		s2 = new SegmentStraight("s2", new Coordinates(1, 1), new Coordinates(-1, -1));
		s3 = new SegmentStraight("s3", new Coordinates(-1, -1), new Coordinates(1, -1));
		s4 = new SegmentStraight("s4", new Coordinates(1, -1), new Coordinates(-1, 1));
		
		t.addSegments(s1, s3, s4, s2);
		
		t.compile();
		
		checkJoin(s1, s4, false, s2, true);
		checkJoin(s2, s1, false, s3, true);
		checkJoin(s3, s2, false, s4, true);
		checkJoin(s4, s3, false, s1, true);
		
	}
	
	//Tests compile with segments that should be joined C<==>C and D<==>D
	@Test
	void testCompileCtoCandDtoD() {
		SegmentStraight s1, s2, s3, s4;
		
		s1 = new SegmentStraight("s1", new Coordinates(-1, 1), new Coordinates(1, 1));
		s2 = new SegmentStraight("s2", new Coordinates (-1, -1), new Coordinates(1, 1));
		s3 = new SegmentStraight("s3", new Coordinates(-1, -1), new Coordinates(1, -1));
		s4 = new SegmentStraight("s4", new Coordinates(-1, 1), new Coordinates(1, -1));
		
		t.addSegments(s1, s3, s4, s2);
		
		t.compile();
		
		checkJoin(s1, s4, true, s2, false);
		checkJoin(s2, s3, true, s1, false);
		checkJoin(s3, s2, true, s4, false);
		checkJoin(s4, s1, true, s3, false);
	}
	
	//Compile shouldn't be able to be called twice
	@Test
	void testCompileTwice() {
		SegmentStraight s1, s2;
		s1 = new SegmentStraight("s1", new Coordinates(-1, 1), new Coordinates(1, 1));
		s2 = new SegmentStraight("s2", new Coordinates(-1, 1), new Coordinates(1, 1));
		
		t.addSegments(s1, s2);
		
		t.compile();
		
		assertThrows(RuntimeException.class, () -> t.compile());
	}
	
	/*
	 * Is testCompileCtoD but tests with the segments in the order they should be joined in. Helps with debugging if this passes but testCompileCtoD fails.
	 */
	@Test
	void testCompileInOrder() {
		SegmentStraight s1, s2, s3, s4;
		
		s1 = new SegmentStraight("s1", new Coordinates(-1, 1), new Coordinates(1, 1));
		s2 = new SegmentStraight("s2", new Coordinates(1, 1), new Coordinates(-1, -1));
		s3 = new SegmentStraight("s3", new Coordinates(-1, -1), new Coordinates(1, -1));
		s4 = new SegmentStraight("s4", new Coordinates(1, -1), new Coordinates(-1, 1));
		
		t.addSegments(s1, s2, s3, s4);
		
		t.compile();
		
		checkJoin(s1, s4, false, s2, true);
		checkJoin(s2, s1, false, s3, true);
		checkJoin(s3, s2, false, s4, true);
		checkJoin(s4, s3, false, s1, true);
	}
	
	//Tests with basic orphaned tip ?<--><-->?
	@Test
	void testCompileOrphanedTip() {
		SegmentStraight s1, s2;
		s1 = new SegmentStraight("s1", new Coordinates(-1, 1), new Coordinates(1, 1));
		s2 = new SegmentStraight("s2", new Coordinates(1, -1), new Coordinates(1, 1));
		
		t.addSegments(s1, s2);
		
		assertThrows(RuntimeException.class, () -> t.compile());
	}
	
	/*
	 * Tests with lone orphaned tip  |-><--><--><-|        ?<-->?
	 *                               |------------|
	 */
	@Test
	void testCompileLoneOrphanedTip() {
		SegmentStraight s1, s2, s3, s4, s5;
		
		s1 = new SegmentStraight("s1", new Coordinates(-1, 1), new Coordinates(1, 1));
		s2 = new SegmentStraight("s2", new Coordinates(1, 1), new Coordinates(-1, -1));
		s3 = new SegmentStraight("s3", new Coordinates(-1, -1), new Coordinates(1, -1));
		s4 = new SegmentStraight("s4", new Coordinates(1, -1), new Coordinates(-1, 1));
		s5 = new SegmentStraight("s5", new Coordinates(20, 20), new Coordinates(25, 20));
		
		t.addSegments(s1, s3, s4, s2, s5);
		
		assertThrows(RuntimeException.class, () -> t.compile());
	}
	
	/*
	 * Tests with orphaned tip ?<--><--><-|
	 *                              ^-----|
	 */
	@Test
	void testCompileOrphanedTipLaso() {
		SegmentStraight s1, s2, s3;
		
		s1 = new SegmentStraight("s1", new Coordinates(-1, 1), new Coordinates(1, 1));
		s2 = new SegmentStraight("s2", new Coordinates(1, 1), new Coordinates(-1, -1));
		s3 = new SegmentStraight("s3", new Coordinates(-1, -1), new Coordinates(1, 1));
		
		t.addSegments(s1, s3, s2);
		
		assertThrows(RuntimeException.class, () -> t.compile());
	}
	
	//Tests with a group of 4 tips from different segments all near each other. Should throw an exception.
	@Test
	void testCompileGroupOf4() {
		SegmentStraight s1, s2, s3, s4;
		
		s1 = new SegmentStraight("s1", new Coordinates(0, 0), new Coordinates(0, 0.999));
		s2 = new SegmentStraight("s2", new Coordinates(0, 1.001), new Coordinates(0, 2));
		s3 = new SegmentStraight("s3", new Coordinates(0, 2), new Coordinates(0.001, 1.001));
		s4 = new SegmentStraight("s4", new Coordinates(0.001, 0.999), new Coordinates(0, 0));
		
		t.addSegments(s1, s2, s3, s4);
		
		assertThrows(RuntimeException.class, () -> t.compile());
	}
	
	//Tests with segments where a tip A is near tip B and tip B is near tip C, but tip C is not near tip A. Should throw an exception.
	@Test
	void testCompileCnearBnotA() {
		SegmentStraight s1, s2, s3, s4;
		
		s1 = new SegmentStraight("s1", new Coordinates(0, 0), new Coordinates(0, 0.999));
		s2 = new SegmentStraight("s2", new Coordinates(0, 1.001), new Coordinates(0, 2));
		s3 = new SegmentStraight("s3", new Coordinates(0, 2), new Coordinates(0.001, 1.001));
		s4 = new SegmentStraight("s4", new Coordinates(0.001, 1.001), new Coordinates(0, 0));
		
		t.addSegments(s1, s2, s3, s4);
		
		assertThrows(RuntimeException.class, () -> t.compile());
	}
	
	//Tests with segments where a tip A is near tip B and tip B is near tip C, but tip C is not near tip A. Should throw an exception.
	@Test
	void testCompileCnearAnotB() {
		SegmentStraight s1, s2, s3, s4;
		
		s1 = new SegmentStraight("s1", new Coordinates(0, 0), new Coordinates(0, 0.999));
		s2 = new SegmentStraight("s2", new Coordinates(0, 1.001), new Coordinates(0, 2));
		s3 = new SegmentStraight("s3", new Coordinates(0, 2), new Coordinates(0.001, 0.999));
		s4 = new SegmentStraight("s4", new Coordinates(0.001, 0.999), new Coordinates(0, 0));
		
		t.addSegments(s1, s2, s3, s4);
		
		assertThrows(RuntimeException.class, () -> t.compile());
	}
	
	private void checkJoin(A_Segment s, A_Segment tipCSeg, boolean tipCPointsToCorD, A_Segment tipDSeg, boolean tipDPointsToCorD) {
		assertEquals(tipCSeg.getID(), s.getTipCJoin().getTargetSegment().getID(), "Wrong segment: Tip c on segment " + s.getID());
		assertEquals(tipDSeg.getID(), s.getTipDJoin().getTargetSegment().getID(), "Wrong segment: Tip d on segment " + s.getID());
		assertEquals(tipCPointsToCorD, s.getTipCJoin().isTargetSegmentTipCorD(), "Wrong tip of target segment: Tip c on segment " + s.getID());
		assertEquals(tipDPointsToCorD, s.getTipDJoin().isTargetSegmentTipCorD(), "Wrong tip of target segment: Tip d on segment " + s.getID());
	}
}
