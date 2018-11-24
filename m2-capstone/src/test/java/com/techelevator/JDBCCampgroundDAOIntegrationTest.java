package com.techelevator;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.jdbc.JDBCCampgroundDAO;


public class JDBCCampgroundDAOIntegrationTest extends DAOIntegrationTest {
	
	JDBCCampgroundDAO campground;
	
	@Before
	public void setup() {
		campground = new JDBCCampgroundDAO(dataSource);
	}
	
	@Test
	public void get_campgrounds_by_park_id() {
		Park park = new Park();
		park.setId(3);
		
		List<Campground> camps = campground.getAllCampgroundsByParkId(park);
		Assert.assertTrue(camps.size() > 0);
		
	}
	
	@Test
	public void get_campgrounds_by_campground_id() {
		List<Campground> campgroundById = campground.getOneCampgroundByCampgroundId((long) 1);
		Assert.assertTrue(campgroundById.size() == 1);
	}

}
