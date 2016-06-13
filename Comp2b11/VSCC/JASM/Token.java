
  public class Token implements TokenIF
  {
    public String 
        tokenType, 
        tokenValue;

    public Token(String t, String v) 
        {tokenType = t; tokenValue = v;}

    public String toString() 
    {    
        return tokenType + "[" + tokenValue + "]";
    }
}

