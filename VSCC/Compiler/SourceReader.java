import java.io.*;

// BUGS: does not handle non-printing characters properly.

public class SourceReader 
    implements Interface.ErrorHandlerIF, Interface.EmitterIF
{
    public boolean EOF = false;

    ErrorHandler Error;
    Emitter Emitter;
    boolean printSource;

    public SourceReader(ErrorHandler Error,
                        Object Emitter, boolean printSource)
    {
        this.Error = Error;
        this.Emitter = (Emitter) Emitter;
        this.printSource = printSource;
    }

    public class Char
    {
        char ch;
        int pos;

        public Char(char c, int p)
        {
            ch = c;
            pos = p;
        }
        public String toString()
        { return ch + ""; }
    }

    final BufferedReader in = 
        new BufferedReader(new InputStreamReader(System.in) ,1);

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
        Error.inform(line, lineNo);
        return new SourceReader.Char(t, charNo);
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
        if (EOF) {Error.report(FATAL, COMPILER_READ_AT_EOF);}
        lineNo++;
        try {line = in.readLine();} 
        catch(IOException e) {}
        if (printSource)
        {
            if (Emitter == null) 
               System.out.println("\n" + lineNo + ": " + line);
            else 
            {
                Emitter.emit(FLUSH);
                Emitter.emit(SEPARATOR, "> " + lineNo +": " + line);
            }
        }
        if (line == null) {EOF = true; line = "\000\n";}
        else line = line.concat(" \n");
    }

    public static void main(String[] args) 
    {
        SourceReader S = 
           new SourceReader(new ErrorHandler(), null, true);
        while (!S.EOF) System.out.print(S.get());
    }
}
