package timetable.parser.url ;

import java.net.* ;
import timetable.parser.* ;
import timetable.manager.URLDataStructure.URLDataList ;

public class Parser implements 
    TokenIF, Interface.EmitterIF, Interface.ErrorHandlerIF

// Usage: java Compiler [-S] [-O] [-p]
// flags: -S don't print source
//        -O don't optimise
//        -p print unoptimised code
// (Upper case is used to denote 'not', here, but case is ignored in fact.
//  Default settings are print source, optimise, don't print unoptimised.)
//
// The source code may be output as comments interspersed in the asm
// code.  This is controlled by a boolean parameter printSource in the 
// SourceReader, which is created by the Lexer.  The source code is 
// printed somewhat earlier than is desirable, owing to the need for one
// (and occasionally two) tokens of lookahead.
//
// The asm code may be optimised.  A simple peephole optimiser is supplied.
// If an optimiser is used a boolean parameter printUnoptimised 
// in its constructor controls printing of the unoptimised code as comments. 
// The supplied optimiser sometimes optimises out indentations.  This is of
// course a feature and not a bug.

{    
    Lexer Lexer;
    Token lookaheadToken;
	URLDataList list;
    
    public Parser(URL url, URLDataList data)
	{

		Lexer = new Lexer(url);
	    lookaheadToken = Lexer.lexToken(false);
	   	this.list = data ;
	    file();
	}
    
    public void file()
    {
		while(lookaheadToken.tokenType != EOF) {
			if(lookaheadToken.tokenType==UL) {
				ULMethod();
			} else {
	    		lookaheadToken = Lexer.lexToken(false);
	    	}
		}
    }

    public void ULMethod()
    {
		//System.out.println("Method");
		match(UL);
		while(lookaheadToken.tokenType != SUL) {
			ListItem();
		}
		match(SUL);
    }

    public void ListItem()
    {
		while (lookaheadToken.tokenType !=L1) {
			lookaheadToken =  Lexer.lexToken(false);
		}
		match(L1);
		match(A,true);
	
		match(HREF,true);
		String URL = Lexer.getQuote();
		String text = Lexer.getString();
		list.addTimeTableURL(text, URL) ;
		//System.out.println(text+" " + URL);
		match(SA);
		while(lookaheadToken.tokenType != L1&&lookaheadToken.tokenType != SUL) {
			lookaheadToken = Lexer.lexToken(false);
		}
	}
	
    void match(String expectedTokenType, boolean PrintS)
	{
	    if (lookaheadToken.tokenType == expectedTokenType) {
	    	lookaheadToken = Lexer.lexToken(PrintS);
	   	} else {
			System.out.println("error");
		}
	}
		    
    void match(String expectedTokenType)
	{
	    match(expectedTokenType, false);
	}

    
    public static void main(String[] args)
	{
	    /*Parser C = new Parser("pane.html");
	    C.file();*/
	}
}