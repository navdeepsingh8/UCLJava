package timetable.parser ;

import java.io.*;
import java.net.* ;

// BUGS: does not handle non-printing characters properly.

public class SourceReader 
    implements Interface.ErrorHandlerIF, Interface.EmitterIF
{
    public boolean EOF = false;
	URLConnection conn = null;
	BufferedReader in;

    public SourceReader(URL url)
    {
    	try
    	{
    		conn = url.openConnection();
      		conn.connect();
      		in = new BufferedReader(new InputStreamReader(conn.getInputStream())) ;
        }
    	catch (IOException e)
    	{
			System.out.println("Can't open file: " + url.getFile());
			System.exit(0);
    	}
    }

    public class Char
    {
        public char ch;
        public int pos;

        public Char(char c, int p)
        {
            ch = c;
            pos = p;
        }
        public String toString()
        { return ch + ""; }
    }

 	


    String line = ""; 
    int lineNo = 0;
    int charNo = 0;

    public char peek() {return line.charAt(charNo+1);}
    
    public SourceReader.Char get()
    {
        if (++charNo >= line.length()) readLine();
        char t = line.charAt(charNo);
        // processing to deal with non-printing characters 
        // would come here
        return new SourceReader.Char(t, charNo);
    }

    // unget undoes a get (when one too many characters read)
    public void  unget() 
    { 
		if (charNo > 0) charNo--;
	}
    
    public int getCh()
    {
    	return charNo;
    }

    private void readLine()
    {
        // always terminates line with a newline, even at EOF
        // (the InputStreamReader discards newlines in the input,
        // returning a null string for an empty line; it returns a
        // null at EOF.)
        charNo = 0;  
        
        if (EOF) {
        	System.out.println("eof error");System.exit(1);
        }
        
        lineNo++;
        
        try {
        	line = in.readLine();
        } catch(IOException e) {}
        
        if (line == null) {
        	EOF = true; line = "\000\n";
        } else {
        	line = line.concat(" \n");
        }
   //     line.toLowerCase();
   //     System.out.println(line);
    }

    public static void main(String[] args) 
    {
        /*SourceReader S = new SourceReader("macs1.html");
        while (!S.EOF) System.out.print(S.get());*/
    }
}
