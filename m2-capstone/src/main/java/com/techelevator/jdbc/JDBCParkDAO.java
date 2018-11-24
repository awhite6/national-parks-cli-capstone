package com.techelevator.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Park;

public class JDBCParkDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Park> getAllParks() {

		List<Park> allParks = new ArrayList<Park>();
		String sqlGetAllParks = "SELECT * FROM park ORDER BY name";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);

		while (results.next()) {
			Park thePark = mapRowsToPark(results);
			allParks.add(thePark);
		}

		allParks.add(new Park("Quit"));
		return allParks;
	}

	private Park mapRowsToPark(SqlRowSet result) {
		Park park = new Park();
		park.setId(result.getLong("park_id"));
		park.setName(result.getString("name"));
		park.setLocation(result.getString("location"));
		park.setEstablishedDate(result.getDate("establish_date"));
		park.setArea(result.getDouble("area"));
		park.setVisitorCount(result.getInt("visitors"));
		park.setDescription(result.getString("description"));
		return park;
	}

}
