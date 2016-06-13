package timetable.testing;


import timetable.parser.table.* ;
import timetable.parser.* ;
import java.net.* ;
import junit.framework.* ;
import java.io.* ;

public class Table_LexerTest extends TestCase implements TokenIF
{
  Lexer lexer ; 
  FileReader in ;
  
  public Table_LexerTest(String name)
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
  
  
  public void testGetInt()
  {
    lexer.lexToken(true) ;
    assertEquals(lexer.getInt(true), 2) ;
    lexer.getString() ;
    lexer.lexToken(true) ;
    assertEquals(lexer.getInt(true), 3) ;
  }
  
  public void testGetString()
  {
    assertEquals(lexer.getString(), "") ;
    lexer.lexToken(true) ;
    assertEquals(lexer.getString(), " Group: MACS2 Group: MACS2 Fullscreen Index Help Finder Weeks: 6-10, 12-16 (Term1) ") ;
    lexer.lexToken(true) ;
    lexer.getInt(false) ;
    lexer.lexToken(true) ;
    lexer.getInt(true) ;
    lexer.lexToken(true) ;
    assertEquals(lexer.getString(), " ") ;
  }

  public void testLexToken()
  {
    assertEquals(lexer.lexToken(true).toString(), new Token(TABLE).toString().concat("[<TABLE]")) ; 
    lexer.getInt(false) ;
    assertEquals(lexer.lexToken(true).toString(), "TR[<TR]") ; 
    lexer.getInt(true) ;
    assertEquals(lexer.lexToken(true).toString(), "TH[<TH]") ; 
    lexer.getString() ;
    assertEquals(lexer.lexToken(true).toString(), "STH[</TH]") ;  
  }
}

  
