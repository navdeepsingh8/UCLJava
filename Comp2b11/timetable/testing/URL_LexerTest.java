package timetable.testing;


import timetable.parser.url.* ;
import timetable.parser.* ;
import java.net.* ;
import junit.framework.* ;
import java.io.* ;

public class URL_LexerTest extends TestCase implements TokenIF
{
  Lexer lexer ; 
  FileReader in ;

  public URL_LexerTest(String name)
  {
    super(name) ;
  }
  
  public void setUp()
    {
      try
	{
	  URL url = new URL("http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups/resource/g5699.html") ; //Macs2
	  lexer = new Lexer(url);
	}
      catch(MalformedURLException e)
	{}
      
      try
	{
	  in = new FileReader("g5699.html") ;	    
	}
      catch(FileNotFoundException fnfe)
	{}
    }
  
  public void testGetString()
  {
    assertEquals(lexer.getString(), "") ;
    lexer.lexToken(true) ;
    assertEquals(lexer.getString(), "Group: MACS2") ;
    lexer.lexToken(true) ;
    lexer.getInt() ;
    lexer.lexToken(true) ;
    lexer.getInt() ;
    lexer.lexToken(true) ;
    assertEquals(lexer.getString(),"Group: MACS2FullscreenWeeks: 6-10, 12-16 (Term1)      09:00- 10:00  10:00- 11:00  11:00- 12:00  12:00- 13:00  13:00- 14:00  14:00- 15:00  15:00- 16:00  16:00- 17:00  17:00- 18:00   MON") ;
  }

  public void testLexToken()
  {
    assertEquals(lexer.lexToken(true).toString(), "A[<A]") ; 
    lexer.getInt() ;
    assertEquals(lexer.lexToken(true).toString(), "SA[</A]") ; 
    lexer.getInt() ;
    assertEquals(lexer.lexToken(true).toString(), "SA[</A]") ; 
    lexer.getString() ;
    assertEquals(lexer.lexToken(true).toString(), "A[<A]") ;  
  }
  
}

  
