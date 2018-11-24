package com.techelevator.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Campground;
import com.techelevator.Reservation;
import com.techelevator.view.Menu;

public class JDBCReservationDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Reservation> getReservationByCampgroundId(Campground id) {
		List<Reservation> reservations = new ArrayList<>();
		String sqlGetReservationByCampgroundId = "SELECT * FROM reservation JOIN site ON site.site_id = reservation.site_id WHERE campground_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservationByCampgroundId, id.getId());

		while (results.next()) {
			Reservation reservation = mapReservationToRowSet(results);
			reservations.add(reservation);
		}

		return reservations;
	}

	public List<Reservation> getListOfAllReservations() {

		List<Reservation> allReservations = new ArrayList<>();

		String sqlListOfAllReservations = "SELECT * FROM reservation JOIN site ON site.site_id = reservation.site_id;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlListOfAllReservations);

		while (results.next()) {
			Reservation reservation = mapReservationToRowSet(results);
			allReservations.add(reservation);
		}

		return allReservations;
	}

	public void makeReservation(Reservation reservation, Menu menu) {
		createNewReservation(reservation);
		long id = getReservationId(reservation);
		menu.displayReservationId(id);
	}

	private void createNewReservation(Reservation reservation) {
		String sqlInsertReservation = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) VALUES (?, ?, ?, ?, current_timestamp);";
		jdbcTemplate.update(sqlInsertReservation, reservation.getSiteId(), reservation.getName(),
				reservation.getFromDate(), reservation.getToDate());
	}

	private long getReservationId(Reservation reservation) {
		long reservationId = 0;
		String sql = "SELECT reservation_id FROM reservation WHERE site_id = ? AND name = ? AND from_date = ? AND to_date = ?;";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, reservation.getSiteId(), reservation.getName(),
				reservation.getFromDate(), reservation.getToDate());
		while (result.next()) {
			Reservation newReservation = new Reservation();
			newReservation.setReservationId(result.getInt("reservation_id"));
			reservationId = newReservation.getReservationId();
		}

		return reservationId;
	}

	private Reservation mapReservationToRowSet(SqlRowSet result) {
		Reservation reservation = new Reservation();
		reservation.setReservationId(result.getInt("reservation_id"));
		reservation.setSiteId(result.getLong("site_id"));
		reservation.setName(result.getString("name"));
		reservation.setFromDate(result.getDate("from_date"));
		reservation.setToDate(result.getDate("to_date"));
		reservation.setCreateDate(result.getDate("create_date"));
		reservation.setCampgroundId(result.getLong("campground_id"));
		return reservation;
	}
}
