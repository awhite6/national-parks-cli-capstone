package com.techelevator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
//import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

//import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.jdbc.JDBCCampgroundDAO;
import com.techelevator.view.Menu;

public class Reservation {
	private int reservationId;
	private long siteId;
	private String name;
	private Date fromDate;
	private Date toDate;
	private Date createDate;
	private long campgroundId;
	private double totalCost;

	Campground desiredCampground;
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public Campground getDesiredCampground() {
		return desiredCampground;
	}

	public void setDesiredCampground(Campground desiredCampground) {
		this.desiredCampground = desiredCampground;
	}

	public Reservation() {

	}

	public Reservation(Menu menu, Campground[] campgrounds, Park thePark, boolean pickingFromParks) {
		desiredCampground = chooseCampground(menu, campgrounds, thePark);
		if (desiredCampground.getId() > 0) {
			campgroundId = desiredCampground.getId();
			fromDate = chooseStartDate(menu);
			toDate = chooseEndDate(menu, fromDate);
		}
	}

	public Reservation(Menu menu, Park thePark) {
		fromDate = chooseStartDate(menu);
		toDate = chooseEndDate(menu, fromDate);
	}

	public long getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	private Campground chooseCampground(Menu menu, Campground[] campgrounds, Park thePark) {
		return menu.chooseDesiredCampground(campgrounds, thePark);
	}

	public Date chooseStartDate(Menu menu) {
		dateFormat.setLenient(false);
		Date startDate;

		while (true) {
			String date = menu.chooseStartDate(menu.displayStartDateInstructions());
			try {
				startDate = dateFormat.parse(date);
				break;
			} catch (Exception e) {
				menu.printPleaseChooseValidDate();
			}
		}

		return startDate;
	}

	public Date chooseEndDate(Menu menu, Date startDate) {
		dateFormat.setLenient(false);
		Date endDate;

		while (true) {
			String date = menu.chooseEndDate(startDate, menu.displayEndDateInstructions());
			try {
				endDate = dateFormat.parse(date);
				if (endDate.before(startDate)) {
					menu.printPleaseChooseValidDate();
					continue;
				}

				break;

			} catch (Exception e) {

				menu.printPleaseChooseValidDate();

			}
		}

		return endDate;
	}

	public double calculateTotalCost(long campgroundId, JDBCCampgroundDAO campgroundDAO) {
		long daysOfStay = calculateNumberOfDays(
				LocalDate.of(getFromDate().getYear(), getFromDate().getMonth() + 1, getFromDate().getDate()),
				LocalDate.of(getToDate().getYear(), getToDate().getMonth() + 1, getToDate().getDate()));
		List<Campground> campground = campgroundDAO.getOneCampgroundByCampgroundId(campgroundId);

		totalCost = (daysOfStay * campground.get(0).getDailyFee());

		return totalCost;
	}

	private int calculateNumberOfDays(LocalDate fromDate, LocalDate toDate) {
		int daysOfStay = (int) ChronoUnit.DAYS.between(fromDate, toDate);
		return daysOfStay + 1;

	}

}
