package timetable.parser.url ;

public interface TokenIF //token types and values
  {
    // lexical token types
    // The String values are are used by the Error 
    // routines to help give more meaningful error 
    // messages; the Parser just needs the symbols.
    // note that the entries are separated by commas,
    // the last one is terminated with a semi-colon.
    String
//	R = "R",
	UL = "UL",
	SUL = "SUL",
	A = "A",
	SA = "SA",
	L1 = "L1",
	HREF = "HREF",
	EOF = "EOF",
	PAST_EOF = "PAST_EOF",
	NONE="NONE",
        UNKNOWN = "UNKNOWN"; // last one 
  }  