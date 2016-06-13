public class Token implements Interface.TokenIF
{
    public String  tokenType,
                   tokenValue;
    public int     startOfToken = 0;

    public Token(String t, String v) 
        {tokenType = t; tokenValue = v;}

    public Token(String t, String v, int start) 
        {tokenType = t; tokenValue = v; startOfToken = start;}

    public Token(String t, char v)
        {tokenType = t; tokenValue = String.valueOf(v);}

    public Token(String t) 
        {tokenType = t; tokenValue = NONE;}

    public Token(char t)
        {tokenType = String.valueOf(t); tokenValue = NONE;}

    public Token()
        {tokenType = NONE; tokenValue = NONE;}

    public String toString() 
    {    
        if (tokenValue == NONE) return tokenType.toUpperCase();
        else return tokenType + "[" + tokenValue + "]";
    }
   
    public static void main(String args[])
    {
         System.out.println(new Token(ADDOP, "+"));
         System.out.println(new Token(CATOP, NONE));
    }
}

