public class ErrorHandler implements TokenIF, ErrorTypesIF
{
    public int mode = OK; 

    SourceReader SourceReader; // null if not loaded by SourceReader

    public void print(String s) {System.err.print(s);}

    public void report
        (int mode, int type, Token actual, String expectedTokenType)
    {
        // report one error only before recovering unless FATAL 
        if (this.mode != OK && mode != FATAL) return; 
        this.mode = mode; 
        if (SourceReader != null) SourceReader.printErrorLine();
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
        case WRONG_OPERAND_TYPE: 
             msg = "Wrong type of operand(s) on stack"; break;
        case STACK_UNDERFLOW: msg = "This will cause a stack underflow"; break;
        case POSSIBLE_STACK_ERROR: 
             msg = "There may be a run time stack error here"; break;
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

    public void printErrorLine() {SourceReader.printErrorLine();}

    public void report(int mode, int type, Token actual)
        {report(mode, type, actual, "");}

    public void report(int mode, int type)
        {report(mode, type, new Token(NONE, NONE), "");}

    public static void main(String[] args)
        {
            ErrorHandler E = new ErrorHandler();
            E.report(WARN, MISMATCH, new Token(OPCODE, NONE), STRING);}
}
