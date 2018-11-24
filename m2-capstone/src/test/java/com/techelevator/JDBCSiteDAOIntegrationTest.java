package com.techelevator;

import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.jdbc.JDBCCampgroundDAO;
import com.techelevator.jdbc.JDBCSiteDAO;
import com.techelevator.view.Menu;

public class JDBCSiteDAOIntegrationTest extends DAOIntegrationTest {
	
	JDBCSiteDAO siteDAO;
	JdbcTemplate jdbcTemplate = new JdbcTemplate();
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	JDBCCampgroundDAO campgroundDAO = new JDBCCampgroundDAO(dataSource);
	
	Menu menu;
	Date fromDate;
	Date toDate;
	Campground campground;
	Park park;
	
	@Before
	public void setup() {
		 siteDAO = new JDBCSiteDAO(dataSource);
		 menu = new Menu(System.in, System.out);
;
		 campground = new Campground();
		 campground.setId(1);
		 campground.setClosingMonth(10);
		 campground.setOpenMonth(04);
		 campground.setParkId(1);
		 campground.setDailyFee(20);
		 campground.setName("my campground");
		 
		 
		 park = new Park();
		 park.setId(1);
		 park.setName("Alina's Park");
		 park.setCampgrounds(campgroundDAO.getAllCampgroundsByParkId(park));
		 
	}
	
	@Test
	public void get_available_sites_by_campground_id_closed_campground() {
		fromDate = new Date(2018, 11, 24);
	    toDate = new Date(2018, 11, 26);
		List<Site> availableSites = siteDAO.getAvailableSitesByCampground(campground, fromDate, toDate);
		Assert.assertTrue(availableSites.size() == 0);
	}
	
	@Test
	public void get_available_sites_by_campground_id_open_campground() {
		fromDate = new Date(2018, 5, 24);
	    toDate = new Date(2018, 5, 26);
		List<Site> availableSites = siteDAO.getAvailableSitesByCampground(campground, fromDate, toDate);
		Assert.assertTrue(availableSites.size() > 0);
	}
	
	@Test
	public void get_available_sites_by_park_with_available_sites() {
		fromDate = new Date(2018, 5, 24);
	    toDate = new Date(2018, 5, 26);
		List<Site> availableSites = siteDAO.getAvailableSitesByPark(park, fromDate, toDate);
		Assert.assertTrue(availableSites.size() > 0);
	}
	
	@Test
	public void get_available_sites_by_park_with_no_available_sites() {
		fromDate = new Date(2018, 3, 24);
	    toDate = new Date(2018, 3, 26);
		List<Site> availableSites = siteDAO.getAvailableSitesByPark(park, fromDate, toDate);
		Assert.assertFalse(availableSites.size() > 0);
	}
}
	

