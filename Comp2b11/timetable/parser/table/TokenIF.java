 package timetable.parser.table ;

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
	TABLE = "TABLE",
	STABLE = "STABLE",
	TH = "TH",
	STH = "STH",
	TR = "TR",
	STR = "STR",
	TD = "TD",
	STD = "STD",
	TEXT = "TEXT",
	STEXT = "STEXT",
	RSPAN= "RSPAN",
	CSPAN = "CSPAN",
	EOF = "EOF",
	PAST_EOF = "PAST_EOF",
	NONE="NONE",
        UNKNOWN = "UNKNOWN"; // last one 
  }  