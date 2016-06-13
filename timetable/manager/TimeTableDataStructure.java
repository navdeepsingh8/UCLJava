/**
 * Title:        timetable.manager.TimeTableDataStructure
 * Description:  Class which holds all Timetable data
 * Copyright:    Copyright (c) 2002
 */
 
package timetable.manager ;

import java.util.* ;
import java.net.* ;
import timetable.parser.table.Parser ;

public class TimeTableDataStructure implements TimeTableDataStructureIF {
	

 	/* Represents a timetable for a course.
  	 */
	public class TimeTable {
		
		private String[][] timetable;
		private int day ;
		private int hour ;
		private URL pointer ;		
		private int rowspan ;
	    private boolean rowspanSet ;
	  
		/* Timetable lecture information is held in a String array
		 * with rows representing days of the week and columns representing
		 * hours of the day in which lectures are held.
		 * The url pointer is for the web page timetable for that course.
		 * The rowspan and rowspanSet variables handle the task of
		 * concurrent lectures for a course.
		 */
		public TimeTable(String pointer)
		{
			timetable = new String[5][9] ;
			for (int row = 0; row < 5; row++) {
				for (int col = 0; col < 9; col++) {
					timetable[row][col] = "" ;
				}
			}
			day = 0 ;
			hour = 0 ;
			rowspan = 0 ;
			rowspanSet = false ;
			try {
 				this.pointer = new URL(pointer) ;
 			} catch (MalformedURLException m) {
 			}
		}
		
		/* Return the url of the web page
		 */
		public URL getPointer()
		{
			return pointer ;
		}
			
		/* Sets the maximum number of concurrent lectures expected
		 * for a day.
		 */
		public void setRowspan(int span)
		{
			rowspan = span ;
			rowspanSet = true ;
		}
		
		/* Adds lecture to current cell in the array. The colspan 
		 * represents the length of a lecture in hours
		 */
		public void addLecture(String lecture, int colspan)
		{
		 	/* if-else statements deal with task of incrementing
		 	 * the rows of the array, unless rowspan is greater than 1
		 	 * in which case multiple lectures may be added to cells in
		 	 * the same row.
		 	 */
		 	if (hour == 9 && rowspanSet == true) {
			    day++ ;
			    hour = 0 ;
			    rowspan-- ;
		  	} else if (hour == 9 && rowspan != 0) {
				hour = 0 ;
				rowspan-- ;
			} else if (hour == 9) {
				day++ ;
				hour = 0 ;
			}
			rowspanSet = false ;
			lecture.trim() ;
			
			/* for loop adds lecture details to number of cells defined
			 * by colspan
			 */
			for (int length = colspan; length > 0; length--) {
				
				// add first lecture to empty cell
				if (!lecture.equals(" ") && timetable[day][hour].equals("")) {
					timetable[day][hour] = calcTime(colspan, length).concat(lecture) ;
				// add multiple lecture to a non-empty cell
				} else if (!lecture.equals(" ") && !timetable[day][hour].equals("")) {
					String spacer = "<BR/><BR/>Also:<BR/>" ;
					timetable[day][hour] = 
					 timetable[day][hour].concat(spacer).concat(calcTime(colspan, length)).concat(lecture) ;
				// no lecture details
				} else if (lecture.equals(" ")) {}
				
				hour++ ;
			}
		}

		/* Return the day and time of the lecture as a String
		 */
		public String calcTime(int colspan, int length)
		{
			String dayName = null ;
			Integer time = null ;
			Integer endTime = null ;
			String timeHours = null ;
			String endTimeHours = null ;
			
			switch (day) {
				case 0 : dayName = "Monday" ; break ;
				case 1 : dayName = "Tuesday" ; break ;
				case 2 : dayName = "Wednesday" ; break ;
				case 3 : dayName = "Thursday" ; break ;
				case 4 : dayName = "Friday" ; break ;
		   	}
		   	time = new Integer(hour + 9 - colspan + length) ;
		   	timeHours = time.toString() ;
		   	timeHours = timeHours.concat(".00") ;
		   	endTime = new Integer(hour + 9 + length) ;
		   	endTimeHours = endTime.toString() ;
		   	endTimeHours = endTimeHours.concat(".00 ") ;
		   	
		   	return dayName.concat(" ").concat(timeHours).concat("-").concat(endTimeHours) ;
		}
		
		/* Return the current or next lecture from given
		 * day and hour.
		 */
		public String getLecture(int currentDay, int currentHour, String current)
		{
			int currentDay1 = currentDay ;
			int currentHour1 = currentHour ;
			/* if-else statements sets hour and day to next
			 * ones in which lectures may be held
			 */
			if (currentDay1 > 4) {
				currentDay1 = 0 ;
				currentHour1 = 0 ;
			} else if (currentDay1 < 4 && currentHour1 > 8) {
				currentDay1++ ;
				currentHour1 = 0 ;
			}
			
			/* while loop increments through hours and
			 * days until a lecture is located in the array
			 */
			while (timetable[currentDay1][currentHour1].equals(""))
			{
				if (currentHour1 == 8) {
					if (currentDay1 == 4) {
						currentDay1 = 0 ;
						currentHour1 = 0 ;
					} else {
						currentDay1++ ;
						currentHour1 = 0 ;
					}
				} else {
				  currentHour1++ ;
				}
			}
			
			/* if-else statement deals with task of returning next lecture
			 * or lecture after the next according to user selection
			 */
			if (current.equals("0")) {
				
				int currentDay2 = currentDay1 ;
				int currentHour2 = currentHour1 ;
				
				while (timetable[currentDay2][currentHour2].equals("") ||
						timetable[currentDay2][currentHour2].equals(timetable[currentDay1][currentHour1]))
				{
					if (currentHour2 == 8) {
						if (currentDay2 == 4) {
							currentDay2 = 0 ;
							currentHour2 = 0 ;
						} else {
							currentDay2++ ;
							currentHour2 = 0 ;
						}
					} else {
				  		currentHour2++ ;
					}
				}
				return timetable[currentDay2][currentHour2] ;
			
			} else {
				return timetable[currentDay1][currentHour1] ;
			}
		}
	}
	

	private HashMap lists ;
	private URLDataStructureIF urlData ;
	private Parser parser;
	
	/* HashMap of course lists, each a HashMap of courses
	 * allows easy search and retrieval of TimeTable objects.
	 * URLDataStructure contains urls of all course web pages.
	 */
	public TimeTableDataStructure(URLDataStructureIF urlData)
	{
		lists = new HashMap() ;
		this.urlData = urlData ;
	}
	
	/* Return next lecture details for given course list, course,
	 * day and hour.
	 */			
	public String getNextLecture(String urlList, String course, int day, int hour, String current)
	{
		/* If a TimeTable object for given course list and course
		 * exists within data structure its getLecture method is called
		 * outright else a TimeTable for the course is created, using
		 * its web page url from the URLDataStructure, and passed to 
		 * the parser to generate its timetable array.
		 */
		HashMap timetables = new HashMap() ;
		
		if (!lists.containsKey(urlList)) {
			lists.put(urlList, timetables) ;
		} else {
			timetables = (HashMap)lists.get(urlList) ;
			if (timetables.containsKey(course)) {
				TimeTable timetable = (TimeTable)timetables.get(course) ;
				return timetable.getLecture(day, hour, current) ;
			}
		} 
		
		String url = urlData.getURL(urlList, course) ;
		TimeTable timetable = new TimeTable(url) ;
		timetables.put(course, timetable) ;
		System.out.println("Adding timetable: " + course + " to " + urlList) ;
		parser = new Parser(timetable.getPointer(), timetable) ;
		return timetable.getLecture(day, hour, current) ;		
	}
}