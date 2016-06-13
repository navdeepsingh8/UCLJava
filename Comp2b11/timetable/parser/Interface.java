package timetable.parser ;

import java.net.* ;

public interface Interface
{
 
  public interface ErrorHandlerIF
   {
    int 
        // error severity types
        OK = 0,
        WARN = 1,
        ERROR = 2,
        FATAL = 3,

        // parsing error types
        MISMATCH = 4,
        STATEMENT = 5,
        NO_TERMINATOR = 6,
        OPERAND_EXPECTED = 7,
        
        // lexing error types
        AT_EOF = 21,
        UNTERMINATED_STRING = 22,

        // compiler errors
        COMPILER_READ_AT_EOF = 31,
        INVALID_FORMAT_REQUEST = 32,
        
        OTHER = 99;
   }

  public interface EmitterIF
  {
    String // opcodes and pseudocodes
        COPY = "copy",
        DEC = "dec",
        GOTO = "goto",
        GOTRUE = "gotrue",
        GOFALSE = "gofalse",
        INC = "inc",
        LVALUE = "lvalue",
        RVALUE = "rvalue",
        LABEL = "label",
        POP = "pop",
        PUSH = "push",
        STORE = "store",
        WRITE = "write",
        FLUSH = "flush",
        INDENT = "indent",
        UNINDENT = "unindent",
        SEPARATOR = "; ----";
   }
}

