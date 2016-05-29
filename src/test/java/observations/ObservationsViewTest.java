package observations;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Observation;
import data.State;
import data.Station;

/**
 * Tests for the ObservationsView class.
 *
 * @author michael
 */
public class ObservationsViewTest {

	private Station thisStation = null;
	private Observation thisObs = null;
	
	@Before
	public void setUp() throws Exception {
		thisStation = new Station("http://www.bom.gov.au/fwo/IDN60801/IDN60801.95722.json",
				"Temora",
                "New South Wales");
		thisObs = new Observation("1", "Dubbo", "20160422130000", "22.5", "Partly cloudy", "25.1",
				"0.0", "37");
	}


	@After
	public void tearDown() throws Exception {
		thisStation = null;
		thisObs = null;
	}
	
	/*
	 * Observation object tests
	 */
	@Test
	public void TestCreateObservation() {
		assertEquals("1", thisObs.getmId());
		assertEquals("Dubbo", thisObs.getmName());
		assertEquals("20160422130000", thisObs.getmDateTime());
		assertEquals("22.5", thisObs.getmApparentTemp());
		assertEquals("Partly cloudy", thisObs.getmCloud());
		assertEquals("25.1", thisObs.getmAirtemp());
		assertEquals("0.0", thisObs.getmRain());
		assertEquals("37", thisObs.getmHumidity());
	}
	
}
