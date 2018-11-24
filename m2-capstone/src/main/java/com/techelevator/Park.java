package com.techelevator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.jdbc.JDBCParkDAO;
import com.techelevator.view.Menu;

public class Park {

	private long id;
	private String name;
	private String location;
	private Date establishedDate;
	private double area;
	private int visitorCount;
	private String description;

	private List<Campground> campgrounds = new ArrayList<Campground>();

	public Park() {

	}

	public Park(String quit) {
		name = quit;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getEstablishedDate() {
		return establishedDate;
	}

	public void setEstablishedDate(Date establishedDate) {
		this.establishedDate = establishedDate;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getVisitorCount() {
		return visitorCount;
	}

	public void setVisitorCount(int visitorCount) {
		this.visitorCount = visitorCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Campground> getCampgrounds() {
		return campgrounds;
	}

	public void setCampgrounds(List<Campground> campgrounds) {
		this.campgrounds = campgrounds;
	}

	private Park[] handleParkSelection(BasicDataSource dataSource) {
		JDBCParkDAO parkDAO = new JDBCParkDAO(dataSource);
		List<Park> parks = parkDAO.getAllParks();
		Park[] parkArray = new Park[parks.size()];
		for (int i = 0; i < parks.size(); i++) {
			parkArray[i] = parks.get(i);
		}

		return parkArray;
	}

	public Park selectParkFromOptions(BasicDataSource dataSource, Menu menu, String menuSelectCommand,
			boolean pickingFromParks) {
		Park parkChoice = (Park) menu.getChoiceFromOptions(handleParkSelection(dataSource), pickingFromParks);
		if (parkChoice.getName() != "Quit") {
			menu.displayParkInformation(parkChoice);
			System.out.println(menuSelectCommand);
		}
		return parkChoice;
	}

}
