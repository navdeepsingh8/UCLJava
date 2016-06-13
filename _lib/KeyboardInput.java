import java.io.* ;
/**
 *  A simple input class to read values typed at the command line.  If
 *  an error occurs during input, any exceptions thrown are caught and
 *  a default value returned.
 *
 *  @version 1.1 1999.08.18
 *  @author Graham Roberts
 *  @author Russel Winder
 */
public class KeyboardInput 
{
  /**
   *  The buffered stream that works the keyboard so that we can read
   *  from it sensibly.
   */
  private final BufferedReader in =
    new BufferedReader(new InputStreamReader (System.in)) ;

  /**
   *  Read an <CODE>int</CODE> value from keyboard input.  The default
   *  return value is 0.
   */
  public final synchronized int readInteger() 
  {
    String input = "" ;
    int value = 0 ;
    try 
    {
      input = in.readLine() ;
    }
    catch (IOException e) { }
    if (input != null) 
    {
      try 
      {
        value = Integer.parseInt(input) ;
      }
      catch (NumberFormatException e) { }
    }
    return value ;
  }

  /**
   *  Read a <CODE>long</CODE> value from keyboard input.  The default
   *  return value is 0L.
   */
  public final synchronized long readLong() 
  {
    String input = "" ;
    long value = 0L ;
    try 
    {
      input = in.readLine() ;
    }
    catch (IOException e) { }
    if (input != null) {
      try 
      {
        value = Long.parseLong(input) ;
      }
      catch (NumberFormatException e) { }
    }
    return value ;
  }

  /** 
   * Read a <CODE>double</CODE> value from keyboard input.  The
   *  default return value is 0.0.
   */
  public final synchronized double readDouble() 
  {
    String input = "" ;
    double value = 0.0D ;
    try 
    {
      input = in.readLine() ;
    }
    catch (IOException e) { }
    if (input != null) {
      try 
      {
        value = Double.parseDouble(input) ;
      }
      catch (NumberFormatException e) { }
    }
    return value ;
  }

  /**
   *  Read a <CODE>float</CODE> value from keyboard input.  The
   *  default return value is 0.0F.
   */
  public final synchronized float readFloat() 
  {
    String input = "" ;
    float value = 0.0F ;
    try 
    {
      input = in.readLine() ;
    }
    catch (IOException e) { }
    if (input != null) {
      try 
      {
        value = Float.parseFloat(input) ;
      }
      catch (NumberFormatException e) { }
    }
    return value ;
  }

  /**
   *  Read a <CODE>char</CODE> value from keyboard input.  The default
   *  return value is ' ' (space).
   */
  public final synchronized char readCharacter() 
  {
    char c = ' ' ;
    try 
    {
      c = (char)in.read() ;
    }
    catch (IOException e) {}
    return c ;
  }

  /**
   *  Read an <CODE>String</CODE> value from keyboard input.  The
   *  default return value is "" (the empty string).
   */
  public final synchronized String readString() 
  {
    String s = "";
    try 
    {
      s = in.readLine() ;
    }
    catch (IOException e) {}
    if (s == null) 
    {
      s = "" ;
    }
    return s ;
  }
}
