package cs350f20task3.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import cs350f20task3.A_Segment;
import cs350f20task3.Coordinates;
import cs350f20task3.Join;
import cs350f20task3.SegmentStraight;
import cs350f20task3.TrackManager;

/*
 * NOTE: If there's something wrong with other methods in SegmentStraight this may fail. If any tests there fail, fix that stuff before fixing this stuff.
 * 
 * ALSO NOTE: This test does not always (see types) use / rely on your track compile method. If something fails here, it's not a problem with the track compiler.
 */
class CalculatePositionTest {

	/*
	 * Look at calculatePosition.csv
	 * Type 0 joins every tip C<=>D
	 * Type 1 joins tips in order of s1 to s4: C---D<=>D---C<=>C---D<=>D---C<=>s1_C
	 * Type 2 uses your track compiler
	 * 
	 * Tolerates a epsilon of 0.0001
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "calculatePosition.csv", numLinesToSkip = 1)
	void testCalculatePosition(int type,
			double s1_c_x,
			double s1_c_y,
			double s1_d_x,
			double s1_d_y,
			double s2_c_x,
			double s2_c_y,
			double s2_d_x,
			double s2_d_y,
			double s3_c_x,
			double s3_c_y,
			double s3_d_x,
			double s3_d_y,
			double s4_c_x,
			double s4_c_y,
			double s4_d_x,
			double s4_d_y,
			int test_seg,
			char test_tip,
			double test_len,
			double expect_x,
			double expect_y
	) {
		SegmentStraight s1, s2, s3, s4;
		
		s1 = new SegmentStraight("s1", new Coordinates(s1_c_x, s1_c_y), new Coordinates(s1_d_x, s1_d_y));
		s2 = new SegmentStraight("s2", new Coordinates(s2_c_x, s2_c_y), new Coordinates(s2_d_x, s2_d_y));
		s3 = new SegmentStraight("s3", new Coordinates(s3_c_x, s3_c_y), new Coordinates(s3_d_x, s3_d_y));
		s4 = new SegmentStraight("s4", new Coordinates(s4_c_x, s4_c_y), new Coordinates(s4_d_x, s4_d_y));
		
		switch(type) {
		case 0: {
			joinSegments(s1, false, s2, true);
			joinSegments(s2, false, s3, true);
			joinSegments(s3, false, s4, true);
			joinSegments(s4, false, s1, true);
			break;
		}
		case 1: {
			joinSegments(s1, false, s2, false);
			joinSegments(s2, true, s3, true);
			joinSegments(s3, false, s4, false);
			joinSegments(s4, true, s1, true);
			break;
		}
		case 2: {
			TrackManager t = new TrackManager();
			t.addSegments(s1, s2, s3, s4);
			t.compile();
			break;
		}
		default: throw new RuntimeException("Illigal test type");
		}
		
		SegmentStraight s;
		switch(test_seg) {
		case 1: s = s1; break;
		case 2: s = s2; break;
		case 3: s = s3; break;
		case 4: s = s4; break;
		default: throw new RuntimeException("Illigal test segment");
		}
		
		Coordinates c = s.calculatePosition(test_tip == 'c', test_len);
		
		assertEquals(expect_x, c.getX(), 0.0001, "returned x is invalid");
		assertEquals(expect_y, c.getY(), 0.0001, "returned y is invalid");
	}
	
	/*
	 * calculatePosition should throw something if distance exceeds length of segment and there is no join to pass it off to.
	 */
	@Test
	void testCalculatePositionPastEndFromC () {
		SegmentStraight s1 = new SegmentStraight("s1", new Coordinates(-1, 1), new Coordinates(1, 1));
		assertThrows(RuntimeException.class, () -> s1.calculatePosition(true, 3));
	}
	
	@Test
	void testCalculatePositionPastEndFromD () {
		SegmentStraight s1 = new SegmentStraight("s1", new Coordinates(-1, 1), new Coordinates(1, 1));
		assertThrows(RuntimeException.class, () -> s1.calculatePosition(false, 3));
	}
	
	//DO NOT USE THIS CODE IN YOUR PROJECT. Doing so would be considered cheating.
	private void joinSegments(A_Segment s1, boolean s1AtTipC, A_Segment s2, boolean s2AtTipC) {
		
		if(s1AtTipC) {
			s1.joinTipC(new Join(s2, s2AtTipC));
		} else {
			s1.joinTipD(new Join(s2, s2AtTipC));
		}
		
		if(s2AtTipC) {
			s2.joinTipC(new Join(s1, s1AtTipC));
		} else {
			s2.joinTipD(new Join(s1, s1AtTipC));
		}
	}

}
