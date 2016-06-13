package timetable.testing;

import timetable.parser.table.* ;
import timetable.parser.* ;
import java.net.* ;
import junit.framework.* ;
import java.io.* ;

public class SourceReaderTest extends TestCase
{
  private CharTest ct ;
  private SourceReaderTest srt ;
  
  private SourceReader SR ;
      
  public SourceReaderTest(String name)
  {
    super(name) ;
    ct = new CharTest() ;
  }
  
  public void setUp()
  {
    try
      {
	URL url = new URL
	  ("http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups/resource/g5699.html") ; //Macs2
	SR = new SourceReader(url);
      }
    catch(MalformedURLException e)
      {}
  }
  
  public class CharTest
  {
    FileReader in ;
    SourceReader sr ;
    
    SourceReader sourceReader ;
    
    SourceReader.Char srcA ;
    SourceReader.Char srcB ;
    
    SourceReader.Char srcC1 ;
    SourceReader.Char srcC2 ;
    
    
    public void setUp()
    {
      try
	{
	  URL url = new URL("http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups/resource/g5699.html") ; //Macs2
	  sourceReader  = new SourceReader(url);
	}
      catch(MalformedURLException e)
	{}
      
      srcA = sourceReader.new Char('A', 1) ;
      srcB = sourceReader.new Char('B', 2) ;
      
      srcC1 = sourceReader.new Char('C', 2) ;
      srcC2 = sourceReader.new Char('C', 2) ;
      
      try
	{
	  in = new FileReader("g5699.html");	    
	}
      catch(FileNotFoundException fnfe)
	{}
    }
    
    public void testToString()
    {
      setUp() ;
      assertEquals(srcA.ch,'g') ;
      assertEquals(srcB.pos,6) ; 
      assertEquals(srcA.toString(),"g") ;
    }
  }
  
  public void charTest_testToString()
  {
    ct.testToString() ;
  }

  public void testPeek()
  {
    ct.setUp() ;
    SR.get() ;
    SR.get() ;
    SR.get() ;
    SR.get() ;
    assertEquals(SR.peek(), 'T') ;
    assertEquals(SR.getCh(), 1) ;
    SR.get() ;
    SR.get() ;
    SR.get() ;
    SR.get() ;
    assertEquals(SR.peek(), ' ') ;
    assertEquals(SR.getCh(), 5) ;
    SR.get() ;
    SR.get() ;
    SR.get() ;
    SR.get() ;
    assertEquals(SR.peek(), 'E') ;
    assertEquals(SR.getCh(), 1 ) ;
  }

  public void testGet()
  {
    ct.setUp() ;
    SR.get() ;
    SR.get() ;
    SR.get() ;
    assertEquals(SR.peek(), 'H') ;
    assertEquals(SR.getCh(), 0) ;
    SR.get() ;
    SR.get() ;
    SR.get() ;
    assertEquals(SR.peek(), 'L') ;
    assertEquals(SR.getCh(), 3) ;
    SR.unget() ;
    SR.unget() ;
    assertEquals(SR.peek(), 'T') ;
    assertEquals(SR.getCh(), 1) ;
  }

  public void testGetCh()
  {
    ct.setUp() ;
    for(int n = 0 ; n < 100 ; n++)
      {
	SR.get() ;
	SR.get() ;
	SR.get() ;
      }
    assertEquals(SR.getCh(), 10) ;
    assertEquals(SR.peek(), '/') ;
    
    for(int r = 0 ; r < 1919 ; r++)
      {
	SR.get() ;
	SR.get() ;
      }
    SR.unget() ;
    SR.unget() ;
    assertEquals(SR.getCh(), 65) ;
    assertEquals(SR.peek(), '.') ;
  }
}



