public interface ErrorTypesIF
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
        WRONG_OPERAND_TYPE = 8,
        STACK_UNDERFLOW = 9,
        POSSIBLE_STACK_ERROR = 10,
        
        // lexing error types
        AT_EOF = 21,
        UNTERMINATED_STRING = 22,

        // compiler errors
        COMPILER_READ_AT_EOF = 31,
        INVALID_FORMAT_REQUEST = 32,
        
        OTHER = 99;
}


