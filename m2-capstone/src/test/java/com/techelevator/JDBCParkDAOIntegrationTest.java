package com.techelevator;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.jdbc.JDBCParkDAO;

public class JDBCParkDAOIntegrationTest extends DAOIntegrationTest {
	
	JDBCParkDAO park;
	
	@Before 
	public void setup() {
		park = new JDBCParkDAO(dataSource);
	}
	
	@Test
	public void get_all_parks() {
		List<Park> parks = park.getAllParks();
		Assert.assertTrue(parks.size() > 0);
	}

}
