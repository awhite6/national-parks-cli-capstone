//package com.techelevator.jdbc;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//
//import com.techelevator.Campground;
//import com.techelevator.Park;
//import com.techelevator.Site;
//
//public class JDBCSiteDAO {
//
//	private JdbcTemplate jdbcTemplate;
//
//	public JDBCSiteDAO(DataSource dataSource) {
//		this.jdbcTemplate = new JdbcTemplate(dataSource);
//	}
//
//	private int userFromMonth;
//	private int userToMonth;
//
//	public List<Site> getAvailableSitesByCampgroundId(Campground campground, Date fromDate, Date toDate) {
//
//		userFromMonth = fromDate.getMonth() + 1;
//		userToMonth = toDate.getMonth() + 1;
//
//		List<Site> sites = new ArrayList<>();
//
//		String sqlGetAvailableSitesByCampgroundId = "SELECT distinct * FROM site"
//				+ " JOIN campground ON site.campground_id = campground.campground_id" + " WHERE site.campground_id = ?"
//				+ " AND site_id NOT IN" + " (SELECT site.site_id FROM site"
//				+ " JOIN reservation ON reservation.site_id = site.site_id"
//				+ " WHERE ((? >= reservation.from_date AND ? <= reservation.to_date) OR (? >= reservation.from_date AND ? <= reservation.to_date)) "
//				+ " AND ((? >= campground.open_from_mm AND ? < campground.open_to_mm) OR (? >= campground.open_from_mm AND ? < campground.open_to_mm))) "
//				+ " ORDER BY daily_fee" + " LIMIT 5;";
//		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSitesByCampgroundId, campground.getId(),
//				fromDate, fromDate, toDate, toDate, String.valueOf(userFromMonth), String.valueOf(userToMonth),
//				String.valueOf(userFromMonth), String.valueOf(userToMonth));
//
//		while (results.next()) {
//			Site site = mapSiteToRowSet(results);
//
//			if (isValidDate(userFromMonth, userToMonth, campground)) {
//				sites.add(site);
//			}
//		}
//		return sites;
//	}
//
//	public List<Site> getAvailableSitesByParkId(Park thePark, Date fromDate, Date toDate) {
//
//		userFromMonth = fromDate.getMonth() + 1;
//		userToMonth = toDate.getMonth() + 1;
//		
//		List<Site> sites = new ArrayList<>();
//
//		String sqlGetAvailableSitesByParkId = "SELECT distinct * FROM site"
//				+ " JOIN campground ON site.campground_id = campground.campground_id"
//				+ " JOIN park ON campground.park_id = park.park_id" + " WHERE park.park_id = ? AND site_id NOT IN"
//				+ " (select site.site_id from site" + " JOIN reservation ON reservation.site_id = site.site_id"
//				+ " WHERE ((? >= reservation.from_date AND ? <= reservation.to_date) "
//				+ "OR (? >= reservation.from_date AND ? <= reservation.to_date))) ORDER BY daily_fee LIMIT 5;" ;
//	
//
//
//		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSitesByParkId, 1, fromDate, fromDate,
//				toDate, toDate);
//
//		while (results.next()) {
//			Site site = mapSiteToRowSet(results);
//			List<Campground> campgrounds = thePark.getCampgrounds();
//			Map<Long, Campground> mapOfCampgrounds = new HashMap<Long, Campground>();
//			for (Campground c : campgrounds) {
//				mapOfCampgrounds.put(c.getId(), c);
//			}
//			
//			for (Long l : mapOfCampgrounds.keySet()) {
//				Campground camp = mapOfCampgrounds.get(l);
//				
//				if (site.getCampgroundId() == camp.getId()) {
//					if (isValidDate(userFromMonth, userToMonth, camp)) {
//						sites.add(site);
//					}
//				}
//			}
//		}
//			
//		return sites;
//	}
//
//	private Site mapSiteToRowSet(SqlRowSet result) {
//
//		Site site = new Site();
//
//		site.setSiteId(result.getLong("site_id"));
//		site.setCampgroundId(result.getLong("campground_id"));
//		site.setSiteNumber(result.getInt("site_number"));
//		site.setMaxOccupancy(result.getInt("max_occupancy"));
//		site.setAccessible(result.getBoolean("accessible"));
//		site.setMaxRvLength(result.getInt("max_rv_length"));
//		site.setUtilities(result.getBoolean("utilities"));
//
//		return site;
//	}
//	
//	private boolean isValidDate(int userFromMonth, int userToMonth, Campground campground) {
//		if (((userFromMonth) > campground.getOpenMonth() && (userFromMonth) < campground.getClosingMonth())
//				&& ((userToMonth > campground.getOpenMonth() && (userToMonth < campground.getClosingMonth())))) {
//			return true;
//		}
//		
//		return false;
//	}
//	
//
//
//}

package com.techelevator.jdbc;

import java.util.ArrayList;

import java.util.Date;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Campground;

import com.techelevator.Park;

import com.techelevator.Site;

public class JDBCSiteDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}

	private int userFromMonth;

	private int userToMonth;

	public List<Site> getAvailableSitesByCampground(Campground campground, Date fromDate, Date toDate) {

		userFromMonth = fromDate.getMonth() + 1;

		userToMonth = toDate.getMonth() + 1;

		List<Site> sites = new ArrayList<>();

		String sqlGetAvailableSitesByCampground = "SELECT distinct * FROM site"

				+ " JOIN campground ON site.campground_id = campground.campground_id" + " WHERE site.campground_id = ?"

				+ " AND site_id NOT IN" + " (SELECT site.site_id FROM site"

				+ " JOIN reservation ON reservation.site_id = site.site_id"

				+ " WHERE ((? >= reservation.from_date AND ? <= reservation.to_date) OR (? >= reservation.from_date AND ? <= reservation.to_date)) "

				+ " AND ((? >= campground.open_from_mm AND ? < campground.open_to_mm) OR (? >= campground.open_from_mm AND ? < campground.open_to_mm))) "

				+ " ORDER BY daily_fee" + " LIMIT 5;";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSitesByCampground, campground.getId(),

				fromDate, fromDate, toDate, toDate, String.valueOf(userFromMonth), String.valueOf(userToMonth),

				String.valueOf(userFromMonth), String.valueOf(userToMonth));

		while (results.next()) {

			Site site = mapSiteToRowSet(results);

			if (isValidDate(userFromMonth, userToMonth, campground)) {

				sites.add(site);

			}

		}

		return sites;

	}

	public List<Site> getAvailableSitesByPark(Park thePark, Date fromDate, Date toDate) {

		userFromMonth = fromDate.getMonth() + 1;

		userToMonth = toDate.getMonth() + 1;

		List<Site> sites = new ArrayList<>();

		String sqlGetAvailableSitesByParkId = "SELECT distinct * FROM site"

				+ " JOIN campground ON site.campground_id = campground.campground_id"

				+ " JOIN park ON campground.park_id = park.park_id" + " WHERE park.park_id = ? AND site_id NOT IN"

				+ " (select site.site_id from site" + " JOIN reservation ON reservation.site_id = site.site_id"

				+ " WHERE ((? >= reservation.from_date AND ? <= reservation.to_date) "

				+ "OR (? >= reservation.from_date AND ? <= reservation.to_date)))" + " LIMIT 5;";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSitesByParkId, thePark.getId(), fromDate,
				fromDate,

				toDate, toDate);

		while (results.next()) {

			Site site = mapSiteToRowSet(results);

			List<Campground> campgrounds = thePark.getCampgrounds();

			Map<Long, Campground> mapOfCampgrounds = new HashMap<Long, Campground>();

			for (Campground c : campgrounds) {

				mapOfCampgrounds.put(c.getId(), c);

			}

			for (Long l : mapOfCampgrounds.keySet()) {

				Campground camp = mapOfCampgrounds.get(l);

				if (site.getCampgroundId() == camp.getId()) {

					if (isValidDate(userFromMonth, userToMonth, camp)) {

						sites.add(site);

					}

				}

			}

		}

		return sites;

	}

	private Site mapSiteToRowSet(SqlRowSet result) {

		Site site = new Site();

		site.setSiteId(result.getLong("site_id"));

		site.setCampgroundId(result.getLong("campground_id"));

		site.setSiteNumber(result.getInt("site_number"));

		site.setMaxOccupancy(result.getInt("max_occupancy"));

		site.setAccessible(result.getBoolean("accessible"));

		site.setMaxRvLength(result.getInt("max_rv_length"));

		site.setUtilities(result.getBoolean("utilities"));

		return site;

	}

	private boolean isValidDate(int userFromMonth, int userToMonth, Campground campground) {

		if (((userFromMonth) >= campground.getOpenMonth() && (userFromMonth) < campground.getClosingMonth())

				&& ((userToMonth >= campground.getOpenMonth() && (userToMonth < campground.getClosingMonth())))) {

			return true;

		}

		return false;
	}
}
