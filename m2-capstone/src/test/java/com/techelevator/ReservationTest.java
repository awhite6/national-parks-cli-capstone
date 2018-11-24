package com.techelevator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.jdbc.JDBCCampgroundDAO;
import com.techelevator.view.Menu;


public class ReservationTest {
	
	Reservation reservation;
	Menu menu;
	BasicDataSource dataSource;
	Campground campground;
	JDBCCampgroundDAO campgroundDAO;
	public LocalDate startDate;
	public LocalDate endDate;
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	
	@Before
	public void setup() {

		dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		reservation = new Reservation();
		menu = new Menu(System.in, System.out);
		campground = new Campground();
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		campground.setId(6);
		reservation.setFromDate(new Date(2019, 10, 02));
		reservation.setToDate(new Date(2019, 10, 02));
		//date = new Date(";
		startDate = LocalDate.of(2018, 10, 23);
		endDate = LocalDate.of(2018, 10, 25);
	//	reservation.setFromDate(startDate);
	//	reservation.setToDate(endDate);
	}
	
	@Test
	public void calculate_total_cost() {
		double cost = reservation.calculateTotalCost(campground.getId(), campgroundDAO);
		Assert.assertTrue( cost > 0);
	}
}
