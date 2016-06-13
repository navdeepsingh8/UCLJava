public interface TokenIF //token types and values
{

    // lexical token types
    // The String values are are used by the Error 
    // routines to help give more meaningful error 
    // messages; the Parser just needs the symbols.
    // note that the entries are separated by commas,
    // the last one is terminated with a semi-colon.
    String
        OPCODE = "OPCODE",
        END = "end", // of source, data may follow
        EOF = "EOF",
        PAST_EOF = "PAST_EOF",
        ID = "ID",
        LABEL = "LABEL",
        NONE = "NONE",
        NEWLINE = "NEWLINE",
        NUM = "NUM",
        STRING = "STRING",
        
        UNKNOWN = "UNKNOWN"; // see note 

        // note that the entries are separated by commas
        // the last one is followed by a brace.

 }

