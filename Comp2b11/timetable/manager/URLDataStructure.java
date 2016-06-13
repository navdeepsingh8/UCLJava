/**
 * Title:        timetable.manager.URLDataStructure
 * Description:  Class which holds all URL data
 * Copyright:    Copyright (c) 2002
 */
 
 package timetable.manager ;
 
 import java.net.* ;
 import java.util.* ;
 import timetable.parser.url.Parser ;
 
 public class URLDataStructure implements URLDataStructureIF {
 	
 	
  	/* Represents a list of course-url pairs from a web page.
  	 */
 	public class URLDataList {
 		
 		private String root ;
 		private String page ;
 		private URL pointer ;
 		private TreeMap timetableURLs ;
 		
 		/* The url of the web page, pointer, is given as root and page
 		 * strings to allow concatenation of the relative urls of 
 		 * timetables.
 		 * TreeMap of course-URL pairs is used to keep them in a
 		 * naturally sorted order.
 		 */
 		public URLDataList(String root, String page)
 		{
 			this.root = root ;
 			this.page = page ;
 			try {
 				this.pointer = new URL(root.concat(page)) ;
 			} catch (MalformedURLException m) {
 			}
 			timetableURLs = new TreeMap() ;
 		}
 		
 		/* Return the url of the page.
 		 */
 		public URL getPointer()
 		{
 			return pointer ;
 		}
 		
 		/* Add a course-url pair to the TreeMap.
 		 */
 		public void addTimeTableURL(String courseName, String coursePointer)
 		{
 			timetableURLs.put(courseName, root.concat(coursePointer)) ;
 		}
 		
 		/* Return url for a given course.
 		 */
 		public String getTimeTableURL(String courseName)
 		{
 			return (String)timetableURLs.get(courseName) ;
 		}
 		
 		/* Return all course names in the TreeMap.
 		 */
 		public ArrayList getCourseNames()
 		{
 			return new ArrayList(timetableURLs.keySet()) ;
 		}
 	}
 	 	
 	
 	/* TreeMap of URLDataLists keeps a naturally sorted order
 	 * and allows simple search and retrieval of elements.
 	 */
 	private TreeMap lists ;
 	private ListIterator listIterator ;
 	private Parser parser ;
 	
 	/* Adds URLDataLists for known web pages that contain course-url 
 	 * pair lists and parses them.
 	 */ 	
 	public URLDataStructure()
 	{
 		lists = new TreeMap() ;
 		lists.put("Term 1", new URLDataList("http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups/", "list.html")) ;
 		lists.put("Term 2", new URLDataList("http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term2/groups/", "list.html")) ;
 		lists.put("HatfieldB", new URLDataList("http://www.herts.ac.uk/timetable/Hatfield2001B/", "list.html")) ;
 		updateLists() ;
  	}
  	
 	/* Add course-URL pair to a URLDataList.
 	 * Method required for testing purposes only.
 	 */
 	public void addURL(String urlList, String course, String URL)
 	{
 		URLDataList theList = (URLDataList)lists.get(urlList) ;
 		theList.addTimeTableURL(course, URL) ;
 	}
 	
 	/* Return url for a given course on a given web page.
 	 */
 	public String getURL(String urlList, String course)
 	{
 		URLDataList tempURLDataList = (URLDataList)lists.get(urlList) ;
 		return tempURLDataList.getTimeTableURL(course) ;
 	}
 	
 	/* Return all course names from a URLDataList.
 	 */
 	public ArrayList getCourses(String urlList)
 	{
 		URLDataList tempURLDataList = (URLDataList)lists.get(urlList) ;
 		return tempURLDataList.getCourseNames() ;
 	}
 	
 	/* Return all URLDataLists in HashMap.
 	 */
 	public ArrayList getURLDataLists()
 	{
 		return new ArrayList(lists.keySet()) ;
 	}
 	
 	/* Pass each URLDataList in HashMap to the parser to generate
 	 * its course-url pairs from the web page at its pointer url.
 	 */
 	public void updateLists()
 	{
 		System.out.println("Updating URL lists.") ;
 		ArrayList updateLists = new ArrayList(lists.keySet()) ;
 		ListIterator listIterator = updateLists.listIterator() ;
 		while (listIterator.hasNext())
 		{
 			String name = (String)listIterator.next() ;
 			URLDataList tempList = (URLDataList)lists.get(name) ;
 			System.out.println("Adding "+name+" - "+tempList.getPointer().toString()) ;
 			parser = new Parser(tempList.getPointer(), tempList) ;
 		}
 	}
 	
 }	