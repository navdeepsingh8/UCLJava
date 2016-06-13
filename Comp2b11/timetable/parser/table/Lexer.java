package timetable.parser.table ;

import java.net.* ;
import timetable.parser.* ;

public class Lexer implements TokenIF, Interface.ErrorHandlerIF
{
    
    private SourceReader SourceReader;
    private Token currentToken = null;
    private boolean atEOF = false;
    private SymbolTable SymbolTable = new SymbolTable();
    private StringBuffer textBuffer = new StringBuffer("");
    
    public Lexer(URL url) 
    {
        SourceReader = new SourceReader(url);
    }
    public void reset()
    {
	textBuffer = new StringBuffer("");
    }
    
    public int getInt(boolean reset)
    {
	String x = textBuffer.toString();
	if(reset)
	    textBuffer = new StringBuffer("");
	//else
	//  System.out.print(x);
	int charNo = 0;
	//       SourceReader.Char t = SourceReader.get();

	while (!Character.isDigit(x.charAt(charNo)))
	    {
		charNo++;

		//System.out.print(t.ch);
	    }
	Character g = new Character(x.charAt(charNo));
	return  Integer.parseInt(g.toString()) ;
    }  
    
    public String getString()
    {
	String x = textBuffer.toString();
	textBuffer = new StringBuffer("");
	x=x.replace('\n',' ');
	StringBuffer y = new StringBuffer("");

	boolean text = false;
	
	for (int c = 0 ; c < x.length() ; c++)
	    {
		if (text&&x.charAt(c)!='<')
		    {
			if(!(x.charAt(c-1) == ' ' && x.charAt(c) == ' '))
			    {
				y.append(x.charAt(c));
			    }
		    }
		
		if (x.charAt(c) == '>')
		    {
			text = true ;
			y.append(' ');
		    }
		if (x.charAt(c) == '<')
		    {
			text = false ;
		    }
	    }
	StringBuffer z = new StringBuffer("");
	int charNo = 0;
	boolean first = false ;
	while (charNo < y.length())
	{
		if (y.charAt(charNo)==' ')
		{
			if(!first)
			{
				z.append(y.charAt(charNo));
				charNo++;
				first = true;
			}
			else 
			{
				charNo++;
			}
		}
		else
		{
			first = false;
			z.append(y.charAt(charNo));
			charNo++;
		}
	}

	return z.toString();
    } 
    
    
    public Token lexToken(boolean printS)
    {
        // need to allow a lookahead when at EOF
        if (SourceReader.EOF) {
            if (!atEOF) {
                atEOF = true;
                return new Token(PAST_EOF);
            } else {System.out.println("FATAL, AT_EOF");
	    return new Token(EOF);}
        }
	
	// tag
        SourceReader.Char t = SourceReader.get();
        if (printS)
	    textBuffer.append(t.ch);
	if (t.ch == '<')
	    {
		StringBuffer tag = new StringBuffer("");
		tag.append(t.ch);
		t = SourceReader.get();
		if (printS)
		    textBuffer.append(t.ch);
		while (t.ch != '>' && !Character.isWhitespace(t.ch))
		    {
			tag.append(t.ch);
			t = SourceReader.get();
			if (printS)
			    textBuffer.append(t.ch);
		    }
		SourceReader.unget();
		
		if(textBuffer.length() != 0)
		    textBuffer.deleteCharAt(textBuffer.length()-1);	
		String word = tag.toString();
		if (SymbolTable.containsKey(word)) 
		    {
			String type = SymbolTable.getTokenType(word);
			return new Token(type, word);
		    }
		else
		    {
			return lexToken(printS);
		    }
	    }
	
	// end of tag
	if (t.ch == '>')
	    {
		return lexToken(printS);
	    }
	
	// words
	if (Character.isLetter(t.ch))
	    {
		StringBuffer lexeme = new StringBuffer("");
		while (Character.isLetter(t.ch))
		    {
			lexeme.append(t.ch);
			t=SourceReader.get();
		        if (printS)
			    textBuffer.append(t.ch);
		    }
		SourceReader.unget();
		if(textBuffer.length() != 0)
		    textBuffer.deleteCharAt(textBuffer.length()-1);
		String word = lexeme.toString();
		if (SymbolTable.containsKey(word)) 
		    {
			String type = SymbolTable.getTokenType(word);
			return new Token(type, word);
		    } 
		else 
		    {
			return lexToken(printS);
		    }
	    }
	       
	if (!Character.isLetter(t.ch))
	    {
		return lexToken(printS);
	    }
	return new Token(UNKNOWN);
    }
    
    public static void main(String[] args)
    {
        /*Lexer L = new Lexer("macs1.html");
	  do {
	  System.out.println(L.lexToken(false) + " ");
	  }
	  while (!L.atEOF);*/
    }
}
