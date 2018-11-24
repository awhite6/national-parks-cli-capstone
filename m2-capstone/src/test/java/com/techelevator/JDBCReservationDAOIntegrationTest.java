package com.techelevator;

import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.jdbc.JDBCReservationDAO;
import com.techelevator.view.Menu;

public class JDBCReservationDAOIntegrationTest extends DAOIntegrationTest {

	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	JDBCReservationDAO reservationDAO;
	Reservation reservation;
	Park park = new Park();
	Menu menu = new Menu(System.in, System.out);

	@Before
	public void setup() {
		reservationDAO = new JDBCReservationDAO(dataSource);
	}

	@Test
	public void get_reservation_by_campground_id() {
		Campground campground = new Campground();
		campground.setId(1);

		List<Reservation> reservations = reservationDAO.getReservationByCampgroundId(campground);
		Assert.assertTrue(reservations.size() > 0);
	}

	@Test
	public void make_reservation() {
		String sqlDeleteAllReservations = "DELETE FROM reservation";
		jdbcTemplate.update(sqlDeleteAllReservations);
		
		reservation = new Reservation();

		reservation.setFromDate(new Date(2018, 5, 10));
		reservation.setToDate(new Date(2018, 5, 15));
		reservation.setSiteId(1);
		reservation.setName("Croitor Family");
		reservationDAO.makeReservation(reservation, menu);

		List<Reservation> reservations = reservationDAO.getListOfAllReservations();

		Assert.assertTrue(reservations.size() == 1);

	}
}
