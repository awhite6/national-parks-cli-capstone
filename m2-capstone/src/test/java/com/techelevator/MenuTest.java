package com.techelevator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.view.Menu;

public class MenuTest {

	private ByteArrayOutputStream output;

	Menu menu;
	Campground[] campgrounds;
	Park park;
	boolean pickingFromParks;
	
	@Before
	public void setup() {
		menu = new Menu(System.in, System.out);
		campgrounds = new Campground[] {new Campground()};
		park = new Park();
		pickingFromParks = true;
		output = new ByteArrayOutputStream();
	}
	
	@Test
	public void returns_object_corresponding_to_user_choice() {
		Integer expected = new Integer(456);
		Integer[] options = new Integer[] {  new Integer(123), expected, new Integer(789) };
		Menu menu = getMenuForTestingWithUserInput("2\n");

		Integer result = (Integer)menu.getChoiceFromOptions(options);
		
		Assert.assertEquals(expected, result);	  
	}
	
	@Test
	public void redisplays_menu_if_user_does_not_choose_valid_option() {
		Object[] options = new Object[] {  "Larry", "Curly", "Moe" };
		Menu menu = getMenuForTestingWithUserInput("4\n1\n");
		
		menu.getChoiceFromOptions(options);
		
		String menuDisplay = "\n"+
				 			 "1) "+options[0].toString()+"\n" + 
						     "2) "+options[1].toString()+"\n" +
						     "3) "+options[2].toString()+"\n\n" +
						     "Please choose an option >>> ";
		
		String expected = menuDisplay + 
					      "\n*** 4 is not a valid option ***\n\n" +
					      menuDisplay;
		
		Assert.assertEquals(expected, output.toString());
	}
	
	@Test
	public void redisplays_menu_if_user_enters_garbage() {
		Object[] options = new Object[] {  "Larry", "Curly", "Moe" };
		Menu menu = getMenuForTestingWithUserInput("Mickey Mouse\n1\n");
		
		menu.getChoiceFromOptions(options);
		
		String menuDisplay = "\n"+
							 "1) "+options[0].toString()+"\n" + 
						     "2) "+options[1].toString()+"\n" +
						     "3) "+options[2].toString()+"\n\n" +
						     "Please choose an option >>> ";
		
		String expected = menuDisplay + 
					      "\n*** Mickey Mouse is not a valid option ***\n\n" +
					      menuDisplay;
		
		Assert.assertEquals(expected, output.toString());
	}

	private Menu getMenuForTestingWithUserInput(String userInput) {
		ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
		return new Menu(input, output);
	}

	private Menu getMenuForTesting() {
		return getMenuForTestingWithUserInput("1\n");
	}
	
	@Test
	public void choose_start_date() {
		Assert.assertEquals(new Date(2018, 10, 24), menu.chooseStartDate("11/24/2018"));
	}
	
	@Test
	public void choose_end_date() {
		Assert.assertEquals(new Date(118, 10, 26), menu.chooseEndDate(new Date(2018, 11, 24), "11/26/2018"));
	}
	
	@Test 
	public void choose_name_for_reservation() {
		Assert.assertEquals("John Blue", menu.chooseName("John Blue"));
	}
	

}
