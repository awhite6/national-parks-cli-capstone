package com.techelevator.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Campground;
import com.techelevator.Park;

public class JDBCCampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Campground> getAllCampgroundsByParkId(Park park) {
		List<Campground> campgrounds = new ArrayList<Campground>();
		String sqlGetAllCampgrounds = "SELECT * FROM campground WHERE park_id = ?;";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgrounds, park.getId());

		while (results.next()) {
			Campground campground = mapRowsToCampground(results);
			campgrounds.add(campground);

		}

		return campgrounds;
	}

	public List<Campground> getOneCampgroundByCampgroundId(long campgroundId) {

		List<Campground> campgrounds = new ArrayList<Campground>();

		String sqlGetCampgroundById = "SELECT * FROM campground WHERE campground_id = ?;";

		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetCampgroundById, campgroundId);

		while (result.next()) {
			Campground campground = mapRowsToCampground(result);
			campgrounds.add(campground);
		}

		return campgrounds;
	}

	private Campground mapRowsToCampground(SqlRowSet result) {
		Campground campground = new Campground();

		campground.setId(result.getLong("campground_id"));
		campground.setParkId(result.getLong("park_id"));
		campground.setName(result.getString("name"));
		campground.setOpenMonth(result.getLong("open_from_mm"));
		campground.setClosingMonth(result.getLong("open_to_mm"));
		campground.setDailyFee(result.getDouble("daily_fee"));

		return campground;
	}
}
