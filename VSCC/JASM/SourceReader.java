import java.io.*;

// BUGS: does not handle non-printing characters properly.

public class SourceReader implements ErrorTypesIF 
{
    public boolean EOF = false;

    ErrorHandler Error;

    String filename = "";  
    boolean interactive = false; // set true if program has a 'read' instruction

    BufferedReader in;

    public SourceReader(ErrorHandler E, String f) 
    {
        Error = E;
        E.SourceReader = this;
        filename = f;
    }

    BufferedReader openInput()
    {
        if (filename == "") 
            return new BufferedReader(new InputStreamReader(System.in) ,1);
        else
        {
          try
          {
            File f = new File(filename);
            return new BufferedReader
                 (new InputStreamReader(new FileInputStream(f)),1);
          }
          catch(IOException e)
          {
             System.err.println(e);
          }
        }
        return null;
    }

    String line = ""; public int lineNo = 0, charNo = 0;
    int startOfToken  = 0;

    public char peek() {return line.charAt(charNo+1);}

    public void markStartOfToken() {startOfToken = charNo;}
    
    public char get()
    {
        if (in == null) in = openInput();
        if (++charNo >= line.length()) readLine();
        char t = line.charAt(charNo);
        // processing here to deal with non-printing characters
        return t;
    }

    // unget undoes a get (when one too many characters read)
    public void unget() { if (charNo > 0) charNo--;}

    void readLine()
    {
        // always terminates line with a newline, even at EOF
        // (the InputStreamReader discards newlines in the input,
        // returning a null string for an empty line; it returns a
        // null at EOF.)
        charNo = 0;  
        lineNo++;
        try {line = in.readLine();}
        catch(IOException e) {}
        if (line == null)
        {
            if (filename != "" & interactive) // switch from reading file
            {
                filename = "";
                in = openInput();             // to reading standard input
                readLine();
                return;
            }
            else
            {             
                EOF = true;
                line = "\000\n";
            }
        }
        else line = line.concat("\n");
        if (false) System.out.println("               " + line);
    }

    public void printErrorLine()
    {
        Error.print( (Error.mode == OK)?
            "Resuming parsing at marked token in line " :
            "Error in your source program at line ");
        Error.print(lineNo + "\n" + line);

        // print a line marking the token in error
        // this is complicated by tabs 
        for (int i =0; i <=charNo; i++) 
        {
            if (i <  startOfToken) 
            {
                if (line.charAt(i) == '\t') Error.print("\t");
                else Error.print(" ");
            }
            else 
            {
                Error.print("^");
            }
        }
        Error.print("\n");    
    }

    public static void main(String[] args) 
    {
         ErrorHandler E = new ErrorHandler();
         SourceReader S = new SourceReader(E, "");
         while (!S.EOF) System.out.print(S.get());
    }
}
