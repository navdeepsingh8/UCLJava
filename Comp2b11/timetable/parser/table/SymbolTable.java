package timetable.parser.table ;

import java.util.*;

public class SymbolTable implements TokenIF
{
    Hashtable ST = new Hashtable(); // symbol table

    Token tokenValues [] = {
//	new Token("R", ">"),
	new Token("TABLE", "<TABLE"),
	new Token("STABLE", "</TABLE"),
	new Token("TH", "<TH"),
	new Token("STH","</TH"),
	new Token("TR", "<TR"),
	new Token("STR", "</TR"),
	new Token("TD", "<TD"),
	new Token("STD", "</TD"),
	new Token("RSPAN", "ROWSPAN"),
	new Token("CSPAN", "COLSPAN"),
        new Token("UNKNOWN", "???"),
    };

    public SymbolTable()
    {
        for (int i=0; i<tokenValues.length; i++)
        {
            ST.put(tokenValues[i].tokenValue, tokenValues[i].tokenType);
        }
    }

    public boolean containsKey(String s) 
        {return ST.containsKey(s);}

    public void addToken(Token t)
        {ST.put(t.tokenValue, t.tokenType);}

    public String getTokenType(String s) 
        {return (String)ST.get(s);}
    
    public static void main(String[] args)
    {
        // print out lexemes and their token types
        SymbolTable S = new SymbolTable();
        Enumeration E = S.ST.keys();
        while (E.hasMoreElements()) 
        {
            String key = (String)E.nextElement();
            System.out.println(key + "\t" + S.ST.get(key));
        }
    }
}
