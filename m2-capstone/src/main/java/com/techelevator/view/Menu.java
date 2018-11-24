package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import com.techelevator.Campground;
import com.techelevator.Park;
import com.techelevator.Reservation;
import com.techelevator.Site;
import com.techelevator.jdbc.JDBCCampgroundDAO;

public class Menu {

	private PrintWriter out;
	private Scanner in;

	DesignAndFormat designFormat = new DesignAndFormat();
	NumberFormat formatter = new DecimalFormat("#$##.00");
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	NumberFormat numberFormat = new DecimalFormat("###,###,###");

	public Menu() {

	}

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	public Object getChoiceFromOptions(Object[] options, boolean pickingCampground) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options, pickingCampground);
		}

		return choice;
	}

	public Object getChoiceFromOptions(Park[] options, boolean pickingFromParks) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options, pickingFromParks);
		}

		return choice;
	}

	public Object getChoiceFromOptions(Campground[] options, String parkName) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options, parkName);
			choice = getChoiceFromUserInput(options);
		}

		return choice;
	}

	public Object getChoiceFromOptions(Campground[] options, String parkName, String newCommand,
			boolean pickingFromParks) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options, parkName, newCommand);
			choice = getChoiceFromUserInput(options, pickingFromParks);
		}

		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();

		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption <= options.length && selectedOption > 0) {
				choice = options[selectedOption - 1];
			}

			else {
				choice = null;
			}

		} catch (NumberFormatException e) {

		}

		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}

		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options, boolean pickingFromParks) {
		Object choice = null;
		String userInput = in.nextLine();

		try {
			int selectedOption = Integer.valueOf(userInput);

			if (Integer.valueOf(userInput) == 0 && pickingFromParks == false) {
				return new Campground(0);
			}

			else if (selectedOption <= options.length && selectedOption > 0) {
				choice = options[selectedOption - 1];
			}

			else {
				choice = null;
			}

		} catch (NumberFormatException e) {

		}

		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}

		return choice;
	}

	public void printCampgrounds(Campground[] options, String parkName) {
		out.println();
		out.println("-----------------------------------------------------------------------------------------------");
		out.println("                      ***" + parkName + " National Park Camgrounds***");
		out.println();
		out.printf("%-45s %-15s %-15s %-20s", "      Name", "   Open", "   Close", "   Daily Fee");

		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;

			out.println();
			out.println();
			out.print(optionNum + "# ");
			out.printf("%-45s %-15s %-15s %-20s", options[i].getName(),
					designFormat.getMonthForInt((int) options[i].getOpenMonth()),
					designFormat.getMonthForInt((int) options[i].getClosingMonth()),
					formatter.format(options[i].getDailyFee()));
		}

		out.println();
		out.println("-----------------------------------------------------------------------------------------------");
		out.println();
		out.println();
		out.flush();
	}

	public void displayParkInformation(Park parkChoice) {
		System.out.println();
		System.out.println(
				"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println(parkChoice.getName());
		System.out.printf("%-20s %-20s", "Location:", parkChoice.getLocation());
		System.out.println();
		System.out.printf("%-20s %-20s", "Established:", dateFormat.format(parkChoice.getEstablishedDate()));
		System.out.println();
		System.out.printf("%-20s %-20s", "Area:", numberFormat.format(parkChoice.getArea()) + " sq km");
		System.out.println();
		System.out.printf("%-20s %-20s", "Annual visitors:", numberFormat.format(parkChoice.getVisitorCount()));
		System.out.println();
		System.out.println();
		System.out.println(parkChoice.getDescription());
		System.out.println(
				"*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println();
	}

	public Campground chooseDesiredCampground(Campground[] campgrounds, Park park) {
		String newCommand = "\nPlease choose a campground (press 0 to cancel) ";
		Campground userChoiceCampground = (Campground) getChoiceFromOptions(campgrounds, park.getName(), newCommand,
				false);
		return userChoiceCampground;

	}

	public String chooseStartDate(String userInput) {
		String startDate;

		startDate = userInput;
		System.out.println();

		return startDate;
	}

	public String chooseEndDate(Date startDate, String userInput) {
		String endDate;

		endDate = userInput;
		System.out.println();

		return endDate;

	}

	public long displayMatchingResults(List<Site> matchingResults, Reservation reservation,
			JDBCCampgroundDAO campgroundDAO) {
		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------");
		System.out.println("           ***Results Matching your Search Criteria***");
		System.out.println();

		if (matchingResults.size() < 1) {
			System.out.println("   ***No campsites are availble for your dates, "
					+ "please enter new dates or pick a new camp ground!***");
			return -1;
		} else {

			System.out.printf("%-10s %-10s %-15s %-15s %-10s %-15s", "Site No.", "Max Occup.", "Accessible?",
					"Max RV Length", "Utility", "Cost");
			for (Site s : matchingResults) {
				String cost = "$" + formatter
						.format(new BigDecimal(reservation.calculateTotalCost(s.getCampgroundId(), campgroundDAO)));
				System.out.println();
				System.out.printf("%-10s %-10s %-15s %-15s %-10s %-15s", s.getSiteNumber(), s.getMaxOccupancy(),
						designFormat.getAccessible(s.isAccessible()), designFormat.maxRVLength(s.getMaxRvLength()),
						designFormat.getUtility(s.isUtilities()), cost);

			}
		}

		System.out.println();
		System.out.println(
				"-----------------------------------------------------------------------------------------------");
		System.out.println();
		return chooseSite(matchingResults);
	}

	public String chooseName(String userInput) {
		String userName = "";

		userName = userInput;
		System.out.println(
				"-----------------------------------------------------------------------------------------------");
		return userName;
	}

	public void displayReservationId(long reservationId) {
		System.out.println();
		System.out.println("**************************************************************************");
		System.out.println("   The reservation has been made and the confirmation id is " + reservationId);
		System.out.println();
		System.out.println("   Thank you for making a reservation with us! Have a nice day! :)");
		System.out.println("**************************************************************************");
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	private void displayMenuOptions(Park[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i].getName());
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	private void displayMenuOptions(Campground[] options, String parkName) {
		out.println();
		out.println("-----------------------------------------------------------------------------------------------");
		out.println("                      ***" + parkName + " National Park Campgrounds***");
		out.println();
		out.printf("%-45s %-15s %-15s %-20s", "      Name", "   Open", "   Close", "   Daily Fee");

		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println();
			out.println();
			out.print(optionNum + "# ");
			out.printf("%-45s %-15s %-15s %-20s", options[i].getName(),
					designFormat.getMonthForInt((int) options[i].getOpenMonth()),
					designFormat.getMonthForInt((int) options[i].getClosingMonth()),
					formatter.format(options[i].getDailyFee()));
		}

		out.println();
		out.println("-----------------------------------------------------------------------------------------------");
		out.println();
		out.print("\nPlease choose an option >>> ");
		out.flush();

	}

	private void displayMenuOptions(Campground[] options, String parkName, String newCommand) {
		out.println();
		out.println("-----------------------------------------------------------------------------------------------");
		out.println("                      ***" + parkName + " National Park Campgrounds***");
		out.println();
		out.printf("%-45s %-15s %-15s %-20s", "      Name", "   Open", "   Close", "   Daily Fee");

		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println();
			out.println();
			out.print(optionNum + "# ");
			out.printf("%-45s %-15s %-15s %-20s", options[i].getName(),
					designFormat.getMonthForInt((int) options[i].getOpenMonth()),
					designFormat.getMonthForInt((int) options[i].getClosingMonth()),
					formatter.format(options[i].getDailyFee()));
		}

		out.println();
		out.println("-----------------------------------------------------------------------------------------------");
		out.println();
		out.print(newCommand);
		out.flush();

	}

	private long chooseSite(List<Site> matchingResults) {
		String userInput;
		long siteId = -1;

		System.out.println();

		while (siteId < 0) {
			System.out.println(
					"-----------------------------------------------------------------------------------------------");
			System.out.print("      Please enter a site to be reserved (press 0 to cancel): ");
			userInput = in.nextLine();
			System.out.println();

			if (userInput.equals("")) {
				continue;
			}

			for (Site s : matchingResults) {
				if (!userInput.equals(String.valueOf(s.getSiteNumber())) && !userInput.equals("0")) {
					continue;
				} else if (Integer.valueOf(userInput) == 0) {
					siteId = 0;
					break;
				} else {
					siteId = s.getSiteId();
					break;
				}
			}

			if (siteId < 0) {
				System.out.println("        ***Please choose a valid option!***");
			}
		}

		return siteId;
	}

	public String displayStartDateInstructions() {
		System.out.println("----------------------------------------------------");
		System.out.println("|   Please choose arrival date (mm/dd/yyyy):       |");
		System.out.println("----------------------------------------------------");

		return getUserInput();
	}

	public String displayEndDateInstructions() {
		System.out.println("----------------------------------------------------");
		System.out.println("|   Please choose departure date (mm/dd/yyyy):       |");
		System.out.println("----------------------------------------------------");

		return getUserInput();
	}

	public String displayNameInstructions() {
		String name;
		System.out.println();
		System.out.print("      Please choose a name for reservation:  ");

		while (true) {
			name = getUserInput();

			if (name.length() < 1) {
				System.out.println("Please enter a name for the reservation!");
				continue;
			} else {
				break;
			}
		}

		return name;
	}

	public String getUserInput() {
		return in.nextLine();
	}

	public void printPleaseChooseValidDate() {
		// TODO Auto-generated method stub
		System.out.println("Please choose a valid date after your arrival date!!!");

	}
}
