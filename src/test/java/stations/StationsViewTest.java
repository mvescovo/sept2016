package stations;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.Main;
import data.State;
import data.Station;


/**
 * Tests for the StationsView class.
 *
 * @author michael
 */
public class StationsViewTest {
	

	private List<State> states = new ArrayList<State>();
	private Station thisStation = null;
	private State thisState = null;
	

	@Before
	public void setUp() throws Exception {
		thisStation = new Station("http://www.bom.gov.au/fwo/IDN60801/IDN60801.95722.json",
				"Temora",
                "New South Wales");
		thisState = new State("1", "Victoria");
	}


	@After
	public void tearDown() throws Exception {
		states = null;
		thisStation = null;
		thisState = null;
	}
	
	/*
	 * State object tests
	 */
	@Test
	public void TestCreateState() {
		
		assertEquals("Victoria", thisState.getName());
		assertEquals("1", thisState.getId());
	}
	
	@Test
	public void TestCreateStateList() {
		
		states.add(new State("1", "Antarctica"));
		states.add(new State("2", "New South Wales"));
		states.add(new State("3", "Northern Territory"));
		states.add(new State("4", "Queensland"));
		
		assertEquals(4, states.size());
	}
	
	@Test
	public void TestStateList() {
		
		states.add(new State("1", "Antarctica"));
		states.add(new State("2", "New South Wales"));
		states.add(new State("3", "Northern Territory"));
		states.add(new State("4", "Queensland"));
		
		assertEquals(4, states.size());
	}
	
	
	/*
	 * Station object tests
	 */
	@Test
	public void TestCreateStation() {

		assertEquals("Temora", thisStation.getCity());
		assertEquals("New South Wales", thisStation.getState());
		assertEquals("http://www.bom.gov.au/fwo/IDN60801/IDN60801.95722.json", thisStation.getUrl());
		
	}
	

	

}
