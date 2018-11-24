package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.view.DesignAndFormat;


public class DesignAndFormatTest {
	DesignAndFormat daf;
	
	@Before
	public void setup() {
		daf = new DesignAndFormat();
	}
	
	@Test
	public void get_month_for_int() {
		int month = 5;
		
		Assert.assertTrue("May" == daf.getMonthForInt(month));
		Assert.assertFalse("September" == daf.getMonthForInt(2));
	}
	
	@Test
	public void max_rv_length() {
		int maxRvLength = 4;
		
		Assert.assertTrue("4".equals(daf.maxRVLength(maxRvLength)));
		Assert.assertTrue("N/A".equals(daf.maxRVLength(0)));
	}
	
}
