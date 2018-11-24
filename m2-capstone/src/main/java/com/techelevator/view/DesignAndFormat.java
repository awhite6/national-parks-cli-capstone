package com.techelevator.view;

public class DesignAndFormat {

	public String getMonthForInt(int m) {
		String monthName = "";

		if (m == 12) {
			monthName = "December";
		} else if (m == 11) {
			monthName = "November";
		} else if (m == 10) {
			monthName = "October";
		} else if (m == 9) {
			monthName = "September";
		} else if (m == 8) {
			monthName = "August";
		} else if (m == 7) {
			monthName = "July";
		} else if (m == 6) {
			monthName = "June";
		} else if (m == 5) {
			monthName = "May";
		} else if (m == 4) {
			monthName = "April";
		} else if (m == 3) {
			monthName = "March";
		} else if (m == 2) {
			monthName = "February";
		} else if (m == 1) {
			monthName = "January";
		}
		return monthName;
	}

	protected String getUtility(boolean utility) {
		if (utility == false) {
			return "N/A";
		} else {
			return "Yes";
		}
	}

	protected String getAccessible(boolean isAccessible) {
		if (isAccessible == true) {
			return "Yes";
		} else {
			return "No";
		}
	}

	public String maxRVLength(int maxRVLength) {
		if (maxRVLength == 0) {
			return "N/A";
		} else {
			return String.valueOf(maxRVLength);
		}
	}

}
