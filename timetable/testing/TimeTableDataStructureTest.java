
package timetable.testing ;

import timetable.manager.* ;
import java.util.* ;
import java.net.* ;
import junit.framework.* ;

public class TimeTableDataStructureTest extends TestCase
{
    private TimeTableTest ttt ;
         
    public TimeTableDataStructureTest(String name)
    {
        super(name) ;
	ttt = new TimeTableTest() ;
    }
    
    public class TimeTableTest
    {
	final String pointerA = "http://www.cs.ucl.ac.uk/a" ;
	final String pointerB = "http://www.cs.ucl.ac.uk/b" ;
	final String pointerC = "http://www.cs.ucl.ac.uk/c" ;
	final String pointerD = "http://www.cs.ucl.ac.uk/d" ;
	
	TimeTableDataStructure timeTableDataStructure ;
	URLDataStructure urlData ;

      TimeTableDataStructure.TimeTable tableA ;
      TimeTableDataStructure.TimeTable tableB ;
      TimeTableDataStructure.TimeTable tableC ;
      TimeTableDataStructure.TimeTable tableD ;
      
	URL urlA ;
	URL urlB ;
	URL urlC ;
	
	public void setUp()
	{
	    urlData = new URLDataStructure() ;
	    timeTableDataStructure = new TimeTableDataStructure(urlData) ;
	    
	    tableA = timeTableDataStructure.new TimeTable(pointerA) ;
	    tableB = timeTableDataStructure.new TimeTable(pointerB) ;
	    tableC = timeTableDataStructure.new TimeTable(pointerC) ;
	    tableD = timeTableDataStructure.new TimeTable(pointerD) ;
	    
	    
	    try {
		urlA = new URL(pointerA) ;
	    } catch (MalformedURLException m) {
	    }
	    
	    try {
		urlB = new URL(pointerB) ;
	    } catch (MalformedURLException m) {
	    }
	    
	    try {
	      urlC = new URL(pointerC) ;
	    } catch (MalformedURLException m) {
	    }
	    
	}
	
	public void testGetPointer()
	{
	    setUp() ;  //needs to be called each time.
	    assertEquals(tableA.getPointer(), urlA) ;
	    assertEquals((tableA.getPointer()).toString(), pointerA) ;
	    assertTrue((tableA.getPointer()).equals(urlA)) ;
	    
	    assertTrue((tableB.getPointer()).equals(urlB)) ;
	    assertEquals(tableC.getPointer(), urlC) ;
	    
	    assertTrue(!((tableB.getPointer()).equals(urlC))) ; 
	    assertTrue(!((tableC.getPointer()).equals(urlA))) ; 
	}			
	
	public void testSetRowspan()
	{
	    int n ;
	    setUp() ;
	    tableA.setRowspan(2) ; //this means 9*2 lectures are needed
	    for(n = 0 ; n < 6 ; n++)
		{
		    tableA.addLecture("Maths", 1) ;
		    tableA.addLecture("CS", 1) ;
		    tableA.addLecture("Stats", 1) ;
		}
	    
	    assertEquals(tableA.getLecture(0, 0, "1"), 
			 "Monday 9.00-10.00 Maths<BR/><BR/>Also:<BR/>Monday 9.00-10.00 Maths") ;
	    assertEquals(tableA.getLecture(0,1, "2"), 
			 "Monday 10.00-11.00 CS<BR/><BR/>Also:<BR/>Monday 10.00-11.00 CS") ;
	    assertEquals(tableA.getLecture(0,2, "3"), 
			 "Monday 11.00-12.00 Stats<BR/><BR/>Also:<BR/>Monday 11.00-12.00 Stats") ;
	    
	    tableB.setRowspan(2) ; //this means 9*2 lectures are needed
	    
	    tableB.addLecture("Maths", 1) ;
	    tableB.addLecture("CS", 1) ;
	    for(n = 0 ; n < 7 ; n++)
	      {
		tableB.addLecture(" ", 1) ;
	      }
	    tableB.addLecture("Stats", 2) ; //colspan 2  
	    
	    assertEquals(tableB.getLecture(0,0, "1"), 
			 "Monday 9.00-10.00 Maths<BR/><BR/>Also:<BR/>Monday 9.00-11.00 Stats") ;
	    assertEquals(tableB.getLecture(0,1, "2"), 
			 "Monday 10.00-11.00 CS<BR/><BR/>Also:<BR/>Monday 9.00-11.00 Stats") ;
	    assertEquals(tableB.getLecture(0,2, "0"), 
			 "Monday 10.00-11.00 CS<BR/><BR/>Also:<BR/>Monday 9.00-11.00 Stats") ;
	}
	
	public void testAddLecture()
	{
	  setUp() ;
	  
	  tableC.addLecture("Maths", 1) ;
	  tableC.addLecture("CS", 1) ;
	  tableC.addLecture("Stats", 1) ;		
	  
	  assertEquals(tableC.getLecture(0,0, "1"), "Monday 9.00-10.00 Maths") ;
	  assertEquals(tableC.getLecture(0,1, "1"), "Monday 10.00-11.00 CS") ;
	  assertEquals(tableC.getLecture(0,2, "1"), "Monday 11.00-12.00 Stats") ;
	}
      
	public void testGetLecture() 
	{
	  setUp() ;
	  
	  tableA.addLecture("History", 1) ;
	  tableA.addLecture("Art", 1) ;
	  tableA.addLecture("Geography", 1) ;		
	  
	  assertEquals(tableA.getLecture(0,0, "2"), "Monday 9.00-10.00 History") ;
	  assertEquals(tableA.getLecture(0,1, "2"), "Monday 10.00-11.00 Art") ;
	  assertEquals(tableA.getLecture(0,2, "2"), "Monday 11.00-12.00 Geography") ;
	}
      
	public void testCalcTime()
	{
	    setUp() ;
	    
	    tableA.addLecture("Theory", 1) ;
	    tableA.addLecture("Software", 1) ;
	    tableA.addLecture("Spanish", 1) ;	
	  
	    assertEquals(tableD.calcTime(1, 1), "Monday 9.00-10.00 ") ;
	    
	    tableB.addLecture("Maths", 1) ;
	    tableB.addLecture("CS", 1) ;
	    for(int n = 0 ; n < 7 ; n++)
	      {
		tableB.addLecture(" ", 1) ;
	      }
	    tableB.addLecture("Stats", 2) ; //colspan 2  
	    
	    assertEquals(tableB.calcTime(1, 1), "Tuesday 11.00-12.00 ") ;
	    assertEquals(tableB.calcTime(4, 2), "Tuesday 9.00-13.00 ") ;
	    assertEquals(tableB.calcTime(2, 2), "Tuesday 11.00-13.00 ") ;
	    
	}
    }
    
    public void testTimeTable_GetPointer()
    {
	ttt.testGetPointer() ;
    }

    public void testTimeTable_SetRowspan()
    {
	ttt.testSetRowspan() ;
    }

    public void testTimeTable_AddLecture()
    {
	ttt.testAddLecture() ;
    }

    public void testTimeTable_CalcTime()
    {
	ttt.testCalcTime() ;
    }
    
    public void testTimeTable_GetLecture()
    {
	ttt.testGetLecture() ;
    }
    
    public void testAddLecture()
    {
      //same
    }
    
    public void testGetNextLecture()
    {
      //same
    }
}












