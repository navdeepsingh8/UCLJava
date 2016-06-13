/**
 * Title:        timetable.manager.ServletUtilities
 * Description:  Servlet helper class which interacts with data structures
 * Copyright:    Copyright (c) 2002
 */
 
 package timetable.manager ;
 
 import java.util.* ;
 
 /* Declared abstract as its methods should be available at all times
  * to servlets and server pages and its data objects constant for
  * duration of server uptime.
  */
 public abstract class ServletUtilities {
 	
 	private static URLDataStructureIF urlData = new URLDataStructure() ;
 	private static TimeTableDataStructureIF ttds = new TimeTableDataStructure(urlData) ;
 	
 	/* Return all course lists in URLDataStructure.
 	 */
 	public static ArrayList getURLDataLists()
 	{
 		return urlData.getURLDataLists() ;
 	}
 	
 	/* Return courses for a given list.
 	 */
 	public static ArrayList getCourses(String urlList)
 	{
 		return urlData.getCourses(urlList) ;
 	}
 	
 	/* Return lecture info for next (or current) lecture.
 	 */
 	public static String getLecture(String urlList, String course, String current)
 	{
 		/* Retrieve current time and day and convert
 		 * values to system used by TimeTableDataStructure.TimeTable
 		 */
 		Calendar calendar = new GregorianCalendar();
 		int day = 0 ;
 		int hour = 0 ;
 		int calDay = calendar.get(Calendar.DAY_OF_WEEK) ;
 		int calHour = calendar.get(Calendar.HOUR_OF_DAY) ;
 		int calDate = calendar.get(Calendar.DATE) ;
 		int calMonth = calendar.get(Calendar.MONTH) ;
 		int calYear = calendar.get(Calendar.YEAR) ;
 		int calMin = calendar.get(Calendar.MINUTE) ;
 		
 		System.out.println("\n"+calDate+"/"+calMonth+"/"+calYear+" ["+calHour+":"+calMin+"] "
 							+"requested " + urlList + " " + course + " current:" + current) ;
 		 		
 		if (calDay < 2) {
 			day = calDay + 5 ;
 		} else {
 			day = calDay - 2 ;
 		}
 		
 		if (calHour < 9) {
 			hour = calHour + 15 ;
 			if (day == 0) {
 				day = 6 ;
 			} else {
 				day-- ;
 			}
 		} else {
 			hour = calHour - 9 ;
 		}
 		
 		return ttds.getNextLecture(urlList, course, day, hour, current) ;
 	}
 }