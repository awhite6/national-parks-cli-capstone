package com.techelevator;

import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.jdbc.JDBCCampgroundDAO;
import com.techelevator.view.Menu;

public class Campground {

	private long id;
	private long parkId;
	private String name;
	private long openMonth;
	private long closingMonth;
	private double dailyFee;

	public Campground() {

	}

	public Campground(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getParkId() {
		return parkId;
	}

	public void setParkId(long parkId) {
		this.parkId = parkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getOpenMonth() {
		return openMonth;
	}

	public void setOpenMonth(long openMonth) {
		this.openMonth = openMonth;
	}

	public long getClosingMonth() {
		return closingMonth;
	}

	public void setClosingMonth(long closingMonth) {
		this.closingMonth = closingMonth;
	}

	public double getDailyFee() {
		return dailyFee;
	}

	public void setDailyFee(double dailyFee) {
		this.dailyFee = dailyFee;
	}

	private Campground[] handleCampgroundSelection(BasicDataSource dataSource, Park park) {

		JDBCCampgroundDAO campground = new JDBCCampgroundDAO(dataSource);
		List<Campground> campgrounds = campground.getAllCampgroundsByParkId(park);
		Campground[] campgroundArray = new Campground[campgrounds.size()];

		for (int i = 0; i < campgrounds.size(); i++) {
			campgroundArray[i] = campgrounds.get(i);
		}

		return campgroundArray;
	}

	public Campground[] getCampgroundArray(BasicDataSource dataSource, Park park) {
		return handleCampgroundSelection(dataSource, park);
	}

	public void printCampgroundOptions(BasicDataSource dataSource, Park thePark, String menuSelectCommand, Menu menu) {
		menu.printCampgrounds(handleCampgroundSelection(dataSource, thePark), thePark.getName());
		System.out.println(menuSelectCommand);

	}

}
