import java.util.*;

public class SymbolTable implements Interface.TokenIF
{
    Hashtable ST = new Hashtable(); // symbol table

    Token tokenValues [] = {
        new Token(ADDOP, "+"),
        new Token(ADDOP, "-"),
        new Token(MULOP, "*"),
        new Token(MULOP, "/"),
        new Token(MULOP, "%"),
        new Token(LPAREND, "("),
        new Token(RPAREND, ")"),
        new Token(ASSIGN, "="),
        new Token(IF, "if"),
        new Token(TERMINATOR, ";"),
        new Token(PRINT, "print"),
        new Token(UNKNOWN, "???"),	//Tokens for:
        new Token(ELSE, "else"),	//else statement
        new Token(WHILE, "while"),	//while statement
        new Token(LCOMPOUND, "{"),	//left brace for compound statement
        new Token(RCOMPOUND, "}"),	//right brace for compound statement
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
