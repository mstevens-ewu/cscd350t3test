package cs350f20task3.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs350f20task3.Coordinates;
import cs350f20task3.TrackManager;
import cs350f20task3.tests.A_SegmentTest.TestSegmentClass;

/*
 * NOTE: If there's something wrong with any other part of the program this will likely fail, fix those first and test again.
 */
class TrackManagerTest {

	TrackManager t;
	
	@BeforeEach
	void setUp() {
		t = new TrackManager();
	}

	/*
	 * NOTE: This test assumes that your concrete list class starts indexing at zero (if there is nothing in the list, it adds the object at 0)
	 */
	@Test
	void testAddAndGetSegment() {
		t.addSegments(new TestSegmentClass("test", new Coordinates(1, 1), new Coordinates(-1, -1)));
		assertEquals("test", t.getSegments().get(0).getID());
	}
	
	@Test
	void testAddAndGetSegments() {
		t.addSegments(new TestSegmentClass("test", new Coordinates(1, 1), new Coordinates(-1, -1)), new TestSegmentClass("test", new Coordinates(1, 1), new Coordinates(-1, -1)));
		assertEquals(2, t.getSegments().size());
	}
	
	/*
	 * Your compile method must work
	 */
	@Test
	void testAddSegmentsAfterCompile() {
		//This should technically compile
		t.addSegments(new TestSegmentClass("test1", new Coordinates(1, 1), new Coordinates(-1, -1)), new TestSegmentClass("test2", new Coordinates(1, 1), new Coordinates(-1, -1)));
		t.compile();
		assertThrows(RuntimeException.class, () -> t.addSegments(new TestSegmentClass("test", new Coordinates(1, 1), new Coordinates(-1, -1))));
	}

	/*
	 * Sees what your getSegments method returns before anything is added. Should be an empty list.
	 */
	@Test
	void testGetSegmentsAfterNothingAdded() {
		assertTrue(t.getSegments().isEmpty());
	}
	
	

}
