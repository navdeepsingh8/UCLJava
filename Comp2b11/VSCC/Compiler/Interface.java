public interface Interface
{
  public interface TokenIF //token types and values
  {
    // lexical token types
    // The String values are are used by the Error 
    // routines to help give more meaningful error 
    // messages; the Parser just needs the symbols.
    // note that the entries are separated by commas,
    // the last one is terminated with a semi-colon.
    String
        ADDOP = "ADDOP",
        ASSIGN = "ASSIGN",
        CATOP = "CATOP",
        DO = "DO",
        ELSE = "ELSE",
        EOF = "EOF",
        FOR = "FOR", 
        ID = "ID",
        INCOP = "INCOP",
        IF = "IF",
        LBRACE = "LBRACE",
        LPAREND = "LPAREND",
        MULOP = "MULOP",
        NONE = "NONE",
        NUM = "NUM",
        PAST_EOF = "PAST_EOF",
        PRINT = "PRINT", 
        RBRACE = "RBRACE",
        READ = "READ",
        RELOP = "RELOP",
            LT = "LT", LE = "LE", EQ = "EQ",
            NE = "!=", GE = "GE", GT = "GT",
        RPAREND = "RPAREND",
        STRING = "STRING",
        TERMINATOR = "TERMINATOR",
        WHILE = "WHILE",
        UNKNOWN = "UNKNOWN"; // last one 
  }  

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

