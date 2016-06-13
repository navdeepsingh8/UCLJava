public class ErrorHandler implements Interface.TokenIF, Interface.ErrorHandlerIF
{
    public int mode = OK; 

    // the following variables are updated by SourceReader
    // and Lexer through inform()
    String line = ""; 
    int lineNo = 0;
    int charNo = 0;  

    public void inform(String line, int lineNo)
    {
        this.line = line;
        this.lineNo = lineNo;
    }

    public static void print(String s) {System.err.print(s);}

    public void report
        (int mode, int type, Token actual, String expectedTokenType)
    {
        // report one error only before recovering unless FATAL 
        if (this.mode != OK && mode != FATAL) return; 
        this.mode = mode; 
        printErrorLine(actual);
        String msg;
        switch(type) 
        {
        case AT_EOF: msg = "At end of file"; break;
        case COMPILER_READ_AT_EOF: msg = "Compiler error: reading past EOF";
        case UNTERMINATED_STRING: msg = "String not terminated";   break;
        case MISMATCH: msg = "Unexpected input " + actual+". "
             + expectedTokenType +" expected"; break;
        case NO_TERMINATOR:
             msg = "Terminator assumed before marked token"; break;
        case STATEMENT: 
             msg = "No statement begins with "+actual; break;
        case OPERAND_EXPECTED: msg = "Improper token in expression: "
             + actual + ".  Operand or '(' expected"; break;
        case INVALID_FORMAT_REQUEST: msg = "Invalid format request"; break;
        default: 
             msg = "Unspecified error"; break;
        }
        print(msg);
        switch(mode)
        {
        case WARN:  print("\n"); this.mode = OK; break;
        case ERROR: print(".  Skipping ...\n"); break;
        default:    System.exit(0);
        }
    }

    public void report(int mode, int type, Token actual)
        {report(mode, type, actual, "");}

    public void report(int mode, int type)
        {report(mode, type, new Token(), "");}

    public void printErrorLine(Token actual)
    {
        print( (mode == OK)?
            "Resuming parsing at marked token in line " :
            "Error in your source program at line ");
        print(lineNo + "\n" + line); // line is terminated with '\n'

        // print a line marking the token in error
        // this is complicated by tabs 
        for (int i = 0; i <actual.startOfToken && i < line.length(); i++) 
        {
            if (line.charAt(i) == '\t') print("\t");
            else print(" ");
        }
        for (int i = 0; i< actual.tokenValue.length(); i++) 
             print("^");
        print("\n");    
    }

    public static void main(String[] args)
        {
             ErrorHandler E = new ErrorHandler();
             E.inform("this is line 3"+'\n', 3);
             E.report(WARN, MISMATCH, new Token(ADDOP, "+", 5), TERMINATOR);
        }
}
