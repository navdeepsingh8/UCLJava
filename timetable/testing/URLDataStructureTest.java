/**
 * Title:        timetable.manager.URLDataStructureTest
 * Description:  A Class to test the above class
 * Copyright:    Copyright (c) 2002
 */

package timetable.testing ;

import java.net.* ;
import timetable.manager.* ;
import java.util.* ;
import junit.framework.* ;

public class URLDataStructureTest extends TestCase
{
    URLDataListTest udlt ; //means url data list test

    URLDataStructure UDS1 ;
    URLDataStructure UDS2 ;
    
    public URLDataStructureTest(String name)
    {
        super(name);
	udlt = new URLDataListTest() ;
	UDS1 = new URLDataStructure() ;
	UDS2 = new URLDataStructure() ;
    }
    
    public class URLDataListTest
    {
	final String term1 = "http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups" ;
	final String term2 = "http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups" ;
	
	final String a = "/g5699.html" ; // MaCS 2 us!
	final String b = "/g8206.html" ; // comp sci 4
	
	URLDataStructure urlDataStructure ;
	
	URLDataStructure.URLDataList udlA ;
        URLDataStructure.URLDataList udlB ;
	URLDataStructure.URLDataList udlC ;
	URLDataStructure.URLDataList udlD ;
	
	URL urlA ;
	URL urlB ;
	URL urlC ;
	URL urlD ;
	
	public void setUp()
	{
	    urlDataStructure = new URLDataStructure() ;
	   
	    udlA = urlDataStructure.new URLDataList(term1, a) ;
	    udlB = urlDataStructure.new URLDataList(term1, b) ;
	    udlC = urlDataStructure.new URLDataList(term2, a) ;
	    udlD = urlDataStructure.new URLDataList(term2, b) ;
	    
	    try {
		urlA = new URL(term1.concat(a)) ;
	    } catch (MalformedURLException m) {
	    }
	    
	    try {
		urlB = new URL(term1.concat(b)) ;
	    } catch (MalformedURLException m) {
	    }

	    try {
		urlC = new URL(term2.concat(a)) ;
	    } catch (MalformedURLException m) {
	    }

	    try {
		urlD = new URL(term2.concat(b)) ;
	    } catch (MalformedURLException m) {
	    }
	    
	    // We know that each of the objects have been initalised with 
	}
	

	//Note that is a malformed URL is found, then a null URL is created.
	
	public void testGetPointer()
	{
	    setUp() ;  //has to be run due to this being a member class.
	    
	    //test whether URL object is equal when got from object, and 
	    //what it was before. And do string comparision to.
	    
	    assertEquals(urlA.toString(), (udlA.getPointer()).toString()) ;
	    assertTrue(urlA.equals(udlA.getPointer())) ;
	    
	    assertEquals(urlB.toString(), (udlB.getPointer()).toString()) ;
	    assertTrue(urlB.equals(udlB.getPointer())) ;
	    
	    assertTrue(urlA.equals(udlA.getPointer())) ;
	    assertTrue(urlD.equals(udlD.getPointer())) ;
	    
	    //Some checks to see, that the objects hold non null objects.
	    assertTrue(!(urlD.equals(udlC.getPointer()))) ;
	    assertTrue(!(urlA.equals(udlB.getPointer()))) ;
	}
	
	public void testAddTimeTableURL()
	{
	    setUp() ;  //has to be run due to this being a member class.
	    
	    //Check to see if it is initally 0.
	    assertEquals((udlA.getCourseNames()).size(), 0) ;
	    assertEquals((udlB.getCourseNames()).size(), 0) ;
	    assertEquals((udlC.getCourseNames()).size(), 0) ;
	    assertEquals((udlD.getCourseNames()).size(), 0) ;
	    
	    // add some urls, and then check that size increments,
	    // and also get some using appropriate keys and see if the
	    // are the correct, or as least with what is expected.
	    
	    udlA.addTimeTableURL("MACS 1", "/g20113.html") ;
	    udlA.addTimeTableURL("MACS 2", "/g5699.html") ;
	    udlA.addTimeTableURL("MACS 3", "/g1675.html") ;
	    udlA.addTimeTableURL("MACS 4", "/g1675.html") ;
	    assertEquals((udlA.getCourseNames()).size(), 4) ;
	    
	    udlA.addTimeTableURL("CS 2", "/g1661.html") ;
	    udlA.addTimeTableURL("CS 3", "/g14474.html") ;
	    udlA.addTimeTableURL("CS 4", "/g8206.html") ;
	    assertEquals((udlA.getCourseNames()).size(), 7) ;
	    
	    assertEquals("http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups/g20113.html", 
			 (udlA.getTimeTableURL("MACS 1"))) ;
	    assertEquals("http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups/g5699.html", 
			 (udlA.getTimeTableURL("MACS 2"))) ;
	    assertEquals(udlA.getTimeTableURL("Something"), null) ;

	   
	    assertTrue(!((udlA.getTimeTableURL("MACS 2")).equals
			 (new String("http://WWW.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups/g5699.html")))) ;
	}
	
	public void testGetTimeTableURL()
	{
	    setUp() ;  //has to be run due to this being a member class.
	   
	    udlB.addTimeTableURL("MACS 1", "/g20113.html") ;
	    udlB.addTimeTableURL("MACS 2", "/g5699.html") ;
	    udlB.addTimeTableURL("MACS 3", "/g1675.html") ;
	    udlB.addTimeTableURL("MACS 4", "/g1675.html") ;
	  	    
	    udlC.addTimeTableURL("CS 2", "/g1661.html") ;
	    udlC.addTimeTableURL("CS 3", "/g14474.html") ;
	    udlC.addTimeTableURL("CS 4", "/g8206.html") ;
	    
	    
	    assertEquals("http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups/g20113.html", 
			 (udlB.getTimeTableURL("MACS 1"))) ;
	    assertEquals("http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups/g5699.html", 
			 (udlB.getTimeTableURL("MACS 2"))) ;
	    assertEquals(udlB.getTimeTableURL("Something"), null) ;
	    
	    //MaCS1 instead of MaCS2
	    assertTrue(!((udlB.getTimeTableURL("MACS 1")).equals
			 ("http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups/g5699.html"))) ;
	    
	    assertEquals(udlC.getTimeTableURL("CS2"), null) ;
	}
	
	public void testGetCourseNames()
	{
	    setUp() ;  //has to be run due to this being a member class.
	    assertEquals((udlA.getCourseNames()).size(), 0) ;
	    assertEquals((udlB.getCourseNames()).size(), 0) ;
	    assertEquals((udlC.getCourseNames()).size(), 0) ;
	    assertEquals((udlD.getCourseNames()).size(), 0) ;
	    
	    udlA.addTimeTableURL("MACS 1", "/g20113.html") ;
	    assertEquals((udlA.getCourseNames()).size(), 1) ;
	    udlA.addTimeTableURL("MACS 2", "/g5699.html") ;
	    assertEquals((udlA.getCourseNames()).size(), 2) ;
	    
	    udlB.addTimeTableURL("MACS 3", "/g1675.html") ;
	    assertEquals((udlB.getCourseNames()).size(), 1) ;
	    udlB.addTimeTableURL("MACS 4", "/g1675.html") ;
	    assertEquals((udlB.getCourseNames()).size(), 2) ;
	    
	    udlC.addTimeTableURL("CS 2", "/g1661.html") ;
	    assertEquals((udlC.getCourseNames()).size(), 1) ;
	    udlC.addTimeTableURL("CS 3", "/g14474.html") ;
	    assertEquals((udlC.getCourseNames()).size(), 2) ;
	    

	    for(int n = 0 ; n < 1000 ; n++)
		{
		    udlD.addTimeTableURL(("CS 4"), "/g8206.html") ;
		}
	    assertEquals((udlD.getCourseNames()).size(), 1) ;
	    // this is always 1, as the hash table only allows 
	    // unique keys
	}
    }
    //sort this out later
    public void testURLDataList_GetPointer()
    {
	udlt.testGetPointer() ;
    }
    
    public void testURLDataList_AddTimeTableURL()
    {
	udlt.testAddTimeTableURL() ;
    }
    
    public void testURLDataList_GetTimeTableURL()
    {
	udlt.testGetTimeTableURL() ;
    }
    
    public void testURLDataList_GetCourseNames()
    {
	udlt.testGetCourseNames() ;
    }
    
    public void testAddURL()
    {
	//if we have initially any size then we need to change
	//these numbers.
	assertEquals(UDS1.getCourses("Term 1").size(), 29) ; 
	assertEquals(UDS1.getCourses("Term 2").size(), 30) ;
	//same for any object of URLDataStructure
	assertEquals(UDS2.getCourses("Term 1").size(), 29) ; 
	assertEquals(UDS2.getCourses("Term 2").size(), 30) ;
	
	UDS1.addURL("Term 1", "MACS", "g20113.html") ;
	assertEquals(UDS1.getCourses("Term 1").size(), 30) ;

	UDS2.addURL("Term 2", "CS 2", "g1661.html") ;
	UDS2.addURL("Term 2", "CS 3", "g14474.html") ;
	
	assertTrue(UDS2.getCourses("Term 2").size() == 32) ;
	
	assertEquals(UDS2.getURL("Term 2", "CS 2"), 
		     "http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term2/groups/g1661.html") ;
	
	assertEquals(UDS2.getURL("Term 1", "MACS 19"), null) ; //there is none, i hope anyways!
	
	for(int n = 0 ; n < 2002 ; n++)
	    {
		UDS1.addURL("Term 2", "MACS 2", "g5699.html") ; 
	    }
	assertEquals(UDS1.getCourses("Term 2").size(), 31) ; //can't have repeated names
    }
    
    public void  testGetURL()
    {
	int n ;
	for(n = 0 ; n < 100 ; n++)
	    {
		UDS1.addURL("Term 2", "MACS 2", "g5699.html") ; 
	    }
	assertEquals(UDS1.getCourses("Term 2").size(), 31) ;

	UDS2.addURL("Term 2", "CS 4", "/g8206.html") ;
	UDS1.addURL("Term 2", "CS 4", "/g8206.html") ;   
	
	assertEquals(UDS1.getURL("Term 2", "g8206.html"), UDS2.getURL("Term 2", "g8206.html")) ;
    }
    
    public void testGetCourses()
    {
	//gives arraylist of courses in 1 list only
	assertTrue(UDS1.getCourses("Term 1").size() == 29) ;
	assertTrue(UDS1.getCourses("Term 2").size() == 30) ;
	assertTrue(UDS2.getCourses("Term 1").size() == 29) ;
	assertTrue(UDS2.getCourses("Term 2").size() == 30) ;
	
	for(int n = 0 ; n < 2002 ; n++)
	    {
		UDS1.addURL("Term 1", "CS 3" + n, "/g14474.html") ; 
	    }
	assertEquals(UDS1.getCourses("Term 1").size(), 2031) ;
	
	for(int n = 0 ; n < 110 ; n++)
	    {
		UDS2.addURL("Term 2", "MACS 4", "/g1675.html") ; 
	    }
	assertEquals(UDS2.getCourses("Term 2").size(), 31) ;
    }
    
    public void testGetURLDataLists()
    {
	//not much can be tested here, however as shown when using any
	//of the methods the tests show that the no. of lists is constant.
	assertEquals(UDS1.getURLDataLists().size(), 2) ;
	
	for(int n = 0 ; n < 1542 ; n++)
	    {
		UDS1.addURL("Term 2", "MACS 30", "/g1675.html") ; 
	    }
	assertEquals(UDS1.getCourses("Term 2").size() ,31) ;
	assertEquals(UDS1.getURLDataLists().size(), 2) ;
	UDS1.updateLists() ;
	assertTrue(UDS1.getURLDataLists().size() ==  2) ;
    }
    
    public void testUpdateLists()
    {
	assertEquals(UDS1.getCourses("Term 1").size(), 29) ;
	UDS1.updateLists() ;
	assertEquals(UDS1.getCourses("Term 1").size(), 29) ;
	
	UDS1.addURL("Term 2", "MACS 30", "/g1675.html") ; 
	assertEquals(UDS1.getCourses("Term 1").size(), 29) ;
	assertTrue(UDS1.getURLDataLists().size() ==  2) ;
	
	for(int n = 0 ; n < 247 ; n++)
	    {
		UDS2.addURL("Term 2", "MACS 4", "/g1675.html") ; 
		assertEquals(UDS1.getCourses("Term 1").size(), 29) ;
		assertTrue(UDS2.getURLDataLists().size() ==  2) ;
	    }
	assertEquals(UDS1.getCourses("Term 1").size(), 29) ;
	
	UDS1.getCourses("Term 2") ;
	assertEquals(UDS1.getCourses("Term 1").size(), 29) ;
	assertTrue(UDS1.getURLDataLists().size() ==  2) ;
    } 
    
}
