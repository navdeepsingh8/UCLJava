package timetable.parser.url ;

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
    
    public int getInt()
    {
        SourceReader.Char t = SourceReader.get();
		while (!Character.isDigit(t.ch))
	    {
			t= SourceReader.get();
		}
		return  Integer.parseInt(t.toString()) ;
	}  
    
    public String getString()
    {
		String x = textBuffer.toString();
		textBuffer = new StringBuffer("");
		x=x.replace('\n',' ');
		StringBuffer y = new StringBuffer("");
		int charNo = 0;
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
			}
			if (x.charAt(c) == '<')
			{
				text = false ;
			}
		}
		return y.toString().trim();
    } 
    

    public String getQuote()
    {
		String x = textBuffer.toString();
	
		StringBuffer y = new StringBuffer("");
		int charNo = 0;
		boolean text = false;
		while (x.charAt(charNo) !='"')
		{
			charNo++;
		}
		charNo++;
		while(x.charAt(charNo)!='"')
			{
				y.append(x.charAt(charNo));
				charNo++;
			}
		return y.toString();
	}

    public Token lexToken(boolean printS)
    {
        // need to allow a lookahead when at EOF
        if (SourceReader.EOF) {
            if (!atEOF) {
                atEOF = true;
                return new Token(PAST_EOF);
            } else {//System.out.println("FATAL, AT_EOF");
	    return new Token(EOF);}
        }

		// tag
        SourceReader.Char t = SourceReader.get();
        if (printS) {
        	textBuffer.append(t.ch);
        }

		if (t.ch == '<') {
			StringBuffer tag = new StringBuffer("");
			tag.append(t.ch);
			t = SourceReader.get();
			
			if (printS) {
	        	textBuffer.append(t.ch);
	        }
			
			while (t.ch != '>' && !Character.isWhitespace(t.ch)) {
				tag.append(t.ch);
				t = SourceReader.get();
		        if (printS)
	        		textBuffer.append(t.ch);
			}
			
			SourceReader.unget();
	
			if(textBuffer.length() != 0) {
				textBuffer.deleteCharAt(textBuffer.length()-1);	
			}
			
			String word = tag.toString();
			
			if (SymbolTable.containsKey(word)) {
				String type = SymbolTable.getTokenType(word);
				return new Token(type, word);
			} else {
		   		return lexToken(printS);
			}
	    }

		// end of tag
		if (t.ch == '>') {
			return lexToken(printS);
		}

		// words
		if (Character.isLetter(t.ch)) {
			StringBuffer lexeme = new StringBuffer("");
			while (Character.isLetter(t.ch)) {
				lexeme.append(t.ch);
				t=SourceReader.get();
		        if (printS)
	        		textBuffer.append(t.ch);
			}
			SourceReader.unget();
			if(textBuffer.length() != 0) {
				textBuffer.deleteCharAt(textBuffer.length()-1);
			}
			String word = lexeme.toString();
			if (SymbolTable.containsKey(word)) {
				String type = SymbolTable.getTokenType(word);
				return new Token(type, word);
			} else {
				return lexToken(printS);
			}
		}
       
		if (!Character.isLetter(t.ch)) {
			return lexToken(printS);
		}
		
		return new Token(UNKNOWN);
    }

    public static void main(String[] args)
    {
        /*Lexer L = new Lexer("pane.html");
        do 
            System.out.println(L.lexToken(false) + " "); 
        while (!L.atEOF);*/
    }
}
