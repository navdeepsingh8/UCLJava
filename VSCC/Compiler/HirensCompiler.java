public class Compiler implements 
    Interface.TokenIF, Interface.EmitterIF, Interface.ErrorHandlerIF

// Usage: java Compiler [-S] [-O] [-p]
// flags: -S don't print source
//        -O don't optimise
//        -p print unoptimised code
// (Upper case is used to denote 'not', here, but case is ignored in fact.
//  Default settings are print source, optimise, don't print unoptimised.)
//
// The source code may be output as comments interspersed in the asm
// code.  This is controlled by a boolean parameter printSource in the 
// SourceReader, which is created by the Lexer.  The source code is 
// printed somewhat earlier than is desirable, owing to the need for one
// (and occasionally two) tokens of lookahead.
//
// The asm code may be optimised.  A simple peephole optimiser is supplied.
// If an optimiser is used a boolean parameter printUnoptimised 
// in its constructor controls printing of the unoptimised code as comments. 
// The supplied optimiser sometimes optimises out indentations.  This is of
// course a feature and not a bug.

{    
    ErrorHandler Error;
    Emitter Emitter ;
    Lexer Lexer;
    Token lookaheadToken;
    
    public Compiler
	(boolean printSource, boolean optimise, boolean printUnoptimised)
	{
	    Error = new ErrorHandler();
	    if (optimise)
		Emitter = new Emitter(new Optimiser(printUnoptimised), Error);
	    else
		Emitter = new Emitter(null, Error);
	    Lexer = new Lexer(Error, Emitter, printSource);
	    lookaheadToken = Lexer.getToken();
	}
    
    int label = 17; // for generating labels
    int unique() {return label++;}
    
    public void program()
	{
	    statement(); 
	    while (lookaheadToken.tokenType != EOF)
		{
		    Emitter.emit(SEPARATOR);
		    statement();
		}
	    match(EOF);
	}
    
    void statement()
	{
	    if (lookaheadToken.tokenType == LBRACE) compound_statement();
	    else if (lookaheadToken.tokenType == FOR) for_loop_statement();
	    else if (lookaheadToken.tokenType == DO) do_while_statement();
	    else if (lookaheadToken.tokenType == ID) assignment_statement();
	    else if (lookaheadToken.tokenType == PRINT) print_statement();
	    else if (lookaheadToken.tokenType == IF) if_statement();
	    else if (lookaheadToken.tokenType == WHILE) while_statement();
	    else Error.report(ERROR, STATEMENT, lookaheadToken);    
	    if (Error.mode != OK) recover();    
	}
    
    void compound_statement()
	{
	    match(LBRACE); 
	    while (lookaheadToken.tokenType != RBRACE)
		{
		    statement();
		    if (lookaheadToken.tokenType != RBRACE)
			Emitter.emit(SEPARATOR);
		}
	    match(RBRACE);
	}
    
    void assignment_statement()
	{   
	    assignment_expression();
	    Emitter.emit(POP); // discard expression value
	    match(TERMINATOR);
	}
    
    void print_statement() 
	{
	    match(PRINT); 
	    match(LPAREND);
	    while (true)
		{
		    if (lookaheadToken.tokenType == STRING)
			{
			    Emitter.emit(PRINT, lookaheadToken.tokenValue);
			    match(STRING);
			}
		    else
			{
			    expression();
			    Emitter.emit(WRITE);
			}
		    if (lookaheadToken.tokenType != CATOP) break;
		    match(CATOP);
		}
	    match(RPAREND);
	    match(TERMINATOR);        
	}
    
    void if_statement()
	{
	    // this is my code
	    match(IF);
	    int L1 = unique();
	    match(LPAREND); expression(); match(RPAREND);
	    Emitter.emit(GOFALSE, L1);
	    statement();
	    if (lookaheadToken.tokenType == ELSE)
		{
		    match(ELSE);
		    int L2 = unique();
		    Emitter.emit(GOTO, L2);
		    Emitter.emit(LABEL, L1);
		    statement();
		    Emitter.emit(LABEL, L2);
		}
	    else
		{
		    Emitter.emit(LABEL, L1);
		}
	    // my code ends here
	}
    
    void while_statement()
	{
	    int loop = unique(), next = unique();
	    match(WHILE);
	    Emitter.emit(LABEL, loop);
	    match(LPAREND); expression(); match(RPAREND);
	    Emitter.emit(GOFALSE, next);
	    Emitter.emit(INDENT);
	    statement();
	    Emitter.emit(UNINDENT);
	    Emitter.emit(GOTO, loop);
	    Emitter.emit(LABEL, next);
	}
    
    void do_while_statement()
	{
	    // do statement while (expression);
	    // my code
	    match(DO);
	    int L1 = unique();
	    Emitter.emit(LABEL, L1);
	    statement();
	    match(WHILE);
	    match(LPAREND); expression(); match(RPAREND);
	    Emitter.emit(GOTRUE, L1);
	    match(TERMINATOR);
	    // my code ends here
	}
    
    void for_loop_statement()
	{
	    // for (assignment_expression ; expression ; assignment_expression)
	    //      statement
	    
	    int loop = unique(), next = unique(), 
		body = unique(), increment = unique();
	    // my code 
	    match(FOR);
	    match(LPAREND);
            assignment_statement();

	    Emitter.emit(LABEL, loop);
	    expression();
	    Emitter.emit(GOFALSE, next);
	    Emitter.emit(GOTO, body);
	    match(TERMINATOR);  
	    
	    Emitter.emit(LABEL, increment);
	    assignment_expression();
	    Emitter.emit(POP);
	    match(RPAREND);
	    Emitter.emit(GOTO, loop);
	    
	    Emitter.emit(LABEL, body);
	    statement();
	    Emitter.emit(GOTO, increment);
	    Emitter.emit(LABEL, next);
	    // end of my code

	}
    
    void expression() 
	{
	    arithmetic_expression();
	    if (lookaheadToken.tokenType == RELOP)
		{
		    Token relop = lookaheadToken;
		    match(RELOP);
		    arithmetic_expression();
		    Emitter.emit(relop.tokenValue);
		}
	}
    
    void arithmetic_expression() 
	{
	    term();
	    while (lookaheadToken.tokenType == ADDOP)
		{
		    Token addop = lookaheadToken;
		    match(ADDOP);
		    term();
		    Emitter.emit(addop.tokenValue);
		}
	}
    
    void term()
	{
	    factor();
	    while (lookaheadToken.tokenType == MULOP) 
		{
		    Token mulop = lookaheadToken;
		    match (MULOP);
		    factor();
		    Emitter.emit(mulop.tokenValue);
		}
	}
    
    void factor()
	{
	    if (lookaheadToken.tokenType == ID 
		&& Lexer.nextToken().tokenType == ASSIGN
		|| lookaheadToken.tokenType == ID 
		&& Lexer.nextToken().tokenType == INCOP)
		{
		    assignment_expression();
		}
	    else if (lookaheadToken.tokenType == ID) 
		{
		    Emitter.emit(RVALUE, lookaheadToken.tokenValue);
		    match(ID);
		}
	    else if (lookaheadToken.tokenType == READ)
		{
		    Emitter.emit(READ); 
		    match(READ);
		}
	    else if (lookaheadToken.tokenType == NUM)
		{
		    Emitter.emit(PUSH, lookaheadToken.tokenValue);
		    match(NUM);
		}
	    else if (lookaheadToken.tokenType == LPAREND)
		{match(LPAREND); expression(); match(RPAREND);} 
	    else Error.report
		     (ERROR, OPERAND_EXPECTED, lookaheadToken);
	}
    
    void assignment_expression()
	{
	    if (lookaheadToken.tokenType == ID
		&& Lexer.nextToken().tokenType == INCOP)
		{
		    Token id = lookaheadToken; 
		    match(ID); 
		    Emitter.emit(RVALUE, id.tokenValue); 
		    Emitter.emit(LVALUE, id.tokenValue); 
		    Emitter.emit(RVALUE, id.tokenValue); 
		    Token incop = lookaheadToken; 
		    match(INCOP);         
		    Emitter.emit(incop.tokenValue); 
		    Emitter.emit(STORE);   
		}
	    else // expect ID = expression
		{
		    Token id = lookaheadToken;
		    match(ID); 
		    match(ASSIGN); 
		    Emitter.emit(LVALUE, id.tokenValue); 
		    Emitter.emit(INDENT); 
		    expression();
		    Emitter.emit(UNINDENT);
		    Emitter.emit(STORE);
		    // leave value of expression on stack
		    Emitter.emit(RVALUE, id.tokenValue); 
		}
	}
    
    int mismatchCount = 0; // number of consecutive mis-matches
    void match(String expectedTokenType) 
	// expect a particular token, error if not. 
	{
	    if (Error.mode == OK) 
		{
		    if (lookaheadToken.tokenType == expectedTokenType) 
			{lookaheadToken = Lexer.getToken();}
		    else if (expectedTokenType == TERMINATOR) 
			{Error.report(WARN, NO_TERMINATOR, lookaheadToken, "");}
		    else
			{
			    Error.report
				(ERROR, MISMATCH,
				 lookaheadToken, expectedTokenType);
			}
		}
	    else
		{
		    // read some input to ensure program terminates
		    if (mismatchCount++ > 10 ) 
			lookaheadToken = Lexer.getToken();
		}
	}
    
    void recover() 
	// synchronise source on next terminator
	// Lexer quits if at EOF
	{
	    while (lookaheadToken.tokenType != TERMINATOR
		   && lookaheadToken.tokenType != RBRACE)
		{lookaheadToken = Lexer.getToken();}
	    lookaheadToken = Lexer.getToken(); // read the TERMINATOR/RBRACE
	    mismatchCount = 0;
	    Error.mode = OK; 
	    if (lookaheadToken.tokenType != EOF)
		Error.printErrorLine(lookaheadToken);
	}
    
    public static void main(String[] args)
	{
	    boolean printSource = true;
	    boolean optimise = true;
	    boolean printUnoptimised = false;
	    
	    for (int i = 0; i < args.length; i++)
		{
		    if ((args[i].toLowerCase()).equals("-p")) printUnoptimised = true;
		    else if ((args[i].toLowerCase()).equals("-o")) optimise = false;
		    else if ((args[i].toLowerCase()).equals("-s")) printSource = false;
		    else System.err.println("Flags: -S -O -p. " + args[i] + " no good");
		}
	    Compiler C = new Compiler(printSource, optimise, printUnoptimised);
	    C.program();
	}
}




