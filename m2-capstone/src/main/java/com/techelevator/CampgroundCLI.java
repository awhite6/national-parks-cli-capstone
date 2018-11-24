package com.techelevator;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.jdbc.JDBCCampgroundDAO;
import com.techelevator.jdbc.JDBCReservationDAO;
import com.techelevator.jdbc.JDBCSiteDAO;
import com.techelevator.view.Menu;

public class CampgroundCLI {

	private static final String MENU_PREVIOUS_SCREEN = "Return to Previous Screen";
	private static final String MENU_SELECT_COMMAND = "Select a Command";

	private static final String PARK_MENU_CAMPGROUNDS = "View Campgrounds";
	private static final String PARK_MENU_RESERVATION = "Search for Reservation";
	private static final String[] PARK_MENU_OPTIONS = new String[] { PARK_MENU_CAMPGROUNDS, PARK_MENU_RESERVATION,
			MENU_PREVIOUS_SCREEN };

	private static final String CAMPGROUND_MENU_RESERVATION = "Search for Available Reservation";
	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] { CAMPGROUND_MENU_RESERVATION,
			MENU_PREVIOUS_SCREEN };

	private static final long CANCEL_RESERVATION = 0;

	private Menu menu;
	private JDBCCampgroundDAO campgroundDAO;
	private JDBCSiteDAO siteDAO;
	private JDBCReservationDAO reservationDAO;
	private boolean reservationMade = false;

	private Park thePark = new Park();
	private Campground theCampground = new Campground();
	private Reservation reservation;

	public static void main(String[] args) {

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run(dataSource);
	}

	public CampgroundCLI(DataSource datasource) {

		menu = new Menu(System.in, System.out);
		campgroundDAO = new JDBCCampgroundDAO(datasource);
		siteDAO = new JDBCSiteDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
	}

	public void run(BasicDataSource dataSource) {
		String choice;
		boolean pickingFromParks = true;

		while (true) {
			reservationMade = false;
			thePark = thePark.selectParkFromOptions(dataSource, menu, MENU_SELECT_COMMAND, pickingFromParks);
			quit();

			while (true) {
				choice = (String) menu.getChoiceFromOptions(PARK_MENU_OPTIONS);

				if (choice.equals(PARK_MENU_CAMPGROUNDS)) {
					campgroundsMenu(dataSource, choice, pickingFromParks);
				} else if (choice.equals(PARK_MENU_RESERVATION)) {
					makeReservationThroughPark();
					if (reservation.getSiteId() <= CANCEL_RESERVATION) {
						break;
					}
					createReservation();
					break;
				} else if (choice.equals(MENU_PREVIOUS_SCREEN)) {
					break;
				}
				if (reservationMade == true) {
					break;
				}
			}
		}
	}

	private void quit() {
		if (thePark.getName().equalsIgnoreCase("Quit")) {
			System.exit(0);
		}
	}

	private void createReservation() {
		reservation.setName(menu.chooseName(menu.displayNameInstructions()));
		reservationDAO.makeReservation(reservation, menu);
	}

	private void makeReservationThroughPark() {
		reservation = new Reservation(menu, thePark);
		thePark.setCampgrounds(campgroundDAO.getAllCampgroundsByParkId(thePark));
		List<Site> availableSites = siteDAO.getAvailableSitesByPark(thePark, reservation.getFromDate(),
				reservation.getToDate());

		reservation.setSiteId(menu.displayMatchingResults(availableSites, reservation, campgroundDAO));

	}

	private void makeReservationThroughCampgroundMenu(BasicDataSource dataSource, boolean pickingFromParks) {
		while (true) {
			reservation = new Reservation(menu, theCampground.getCampgroundArray(dataSource, thePark), thePark,
					pickingFromParks);
			if (reservation.getCampgroundId() == CANCEL_RESERVATION) {
				break;
			}
			List<Site> availableSites = siteDAO.getAvailableSitesByCampground(reservation.getDesiredCampground(),
					reservation.getFromDate(), reservation.getToDate());
			reservation.setSiteId(menu.displayMatchingResults(availableSites, reservation, campgroundDAO));
			if (reservation.getSiteId() == -1) {
				continue;
			}
			if (reservation.getSiteId() == CANCEL_RESERVATION) {
				break;
			}
			reservation.setName(menu.chooseName(menu.displayNameInstructions()));
			reservationDAO.makeReservation(reservation, menu);
			reservationMade = true;
			if (reservationMade == true) {
				break;
			}

		}
	}

	private void campgroundsMenu(BasicDataSource dataSource, String choice, boolean pickingFromParks) {
		while (true) {
			theCampground.printCampgroundOptions(dataSource, thePark, MENU_SELECT_COMMAND, menu);
			choice = (String) menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS, pickingFromParks);
			if (choice.equals(CAMPGROUND_MENU_RESERVATION)) {
				makeReservationThroughCampgroundMenu(dataSource, pickingFromParks);
				if (reservationMade == true) {
					break;
				}
			}
			if (choice.equals(MENU_PREVIOUS_SCREEN)) {
				break;
			}
		}
	}

}
