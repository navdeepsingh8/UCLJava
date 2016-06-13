package timetable.parser.table ;

import timetable.manager.TimeTableDataStructure.TimeTable ;
import timetable.parser.* ;
import java.net.* ;

public class Parser implements TokenIF, Interface.EmitterIF, Interface.ErrorHandlerIF
{    
    Lexer Lexer;
    Token lookaheadToken;
    TimeTable table;
    
    public Parser(URL url, TimeTable data)
    {
	Lexer = new Lexer(url);
	lookaheadToken = Lexer.lexToken(false);
	this.table = data;
	file();
    }
    
    public void file()
    {
	while(lookaheadToken.tokenType != EOF) {
	    if(lookaheadToken.tokenType==TABLE) 
		{
		    table();
		} 
	    else 
		{
		    lookaheadToken = Lexer.lexToken(false);
		}
	}
    }
    
    public void table()
    {

	match(TABLE);
	headerRow();
	while(lookaheadToken.tokenType != STABLE) {
	    row();
	}
	match(STABLE);
    }
    
    public void headerRow()
    {
	
	match(TR);
	while (lookaheadToken.tokenType!= STR) {
	    lookaheadToken = Lexer.lexToken(false);
	}
	match(STR);
    }
    
    public void row()
    {
//	System.out.println("*****day*****");
	match(TR);
	match(TH);
	if (lookaheadToken.tokenType==RSPAN) 
	    {
		match(RSPAN,true);
		match(STH,true);
		int rowspan = Lexer.getInt(true);
		table.setRowspan(rowspan);
//		System.out.println("rowspan="+rowspan);
	    }
	
	    {

		match(STH);
	    }
	while (lookaheadToken.tokenType== TD) {
	    cell();
	}
	match(STR);
    }
    
    public void cell()
    {
//	System.out.println("****hour****");
	match(TD, true);
	int colspan = 1;
	if (lookaheadToken.tokenType==CSPAN) 
	{
	    Lexer.reset();
	    match(CSPAN, true);
	    colspan = Lexer.getInt(false);
//		System.out.println("colspan = "+colspan);
	}
	String text = Lexer.getString();  
//	System.out.println(text);

	table.addLecture(text, colspan);
	match(STD); 
    }
    
    void match(String expectedTokenType, boolean PrintS)
    {
	if (lookaheadToken.tokenType == expectedTokenType) 
	    {
		lookaheadToken = Lexer.lexToken(PrintS);
	    }
	else 
	    {}
    }
    
    void match(String expectedTokenType)
    {
	match(expectedTokenType, false);
    }
    
    
 /*  public static void main(String[] args)
    {
    	URL x;
	try
	    {
		x = new URL("http://www.cs.ucl.ac.uk/teaching/celcatdatahtm/term1/groups/resource/g6609.html");
		Parser C = new Parser(x);
	    }
	catch (MalformedURLException m)
	    {}
        
	
    }
   */ 
}

