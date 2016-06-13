public class Compiler implements 
    Interface.TokenIF, Interface.ErrorHandlerIF

{    
    ErrorHandler Error; 
    Lexer Lexer;
    Token lookaheadToken;

    public Compiler(boolean printSource)
    {
       Error = new ErrorHandler();
       Lexer = new Lexer(Error, null, printSource);
       lookaheadToken = Lexer.getToken();
    }

    public void program()
    {
        statement(); 
        while (lookaheadToken.tokenType != EOF)
        {
            statement();
        }
        match(EOF);
    }

    void statement()
    {
        if (lookaheadToken.tokenType == IF) if_statement();
        else if (lookaheadToken.tokenType == ID) assignment_statement();
        else if (lookaheadToken.tokenType == PRINT) print_statement();
        else if (lookaheadToken.tokenType == LCOMPOUND) compound_statement();	//recognises left brace token
        else if (lookaheadToken.tokenType == WHILE) while_statement();			//recognises while token
        else Error.report(ERROR, STATEMENT, lookaheadToken);    
        if (Error.mode != OK) recover(); 
           
    }

    void compound_statement()	//deals with compound statements
    {
        match(LCOMPOUND) ;		//matches left brace token
        while (lookaheadToken.tokenType != RCOMPOUND)
        {
            statement();   		//checks for tokens in compound statement
        }  
        match(RCOMPOUND) ;		//matches right brace token
    }    
    
    void while_statement()		//deals with while statements
    {
        match(WHILE) ;			//matchs while token
        match(LPAREND); expression(); match(RPAREND);	//matches parentheses tokens and expression
        statement();			//checks for tokens in statement
    }
 
    void if_statement()
    {
        match(IF); 
        match(LPAREND); expression(); match(RPAREND); 
        statement();
        
        if (lookaheadToken.tokenType == ELSE)	//checks for else token
        {
           match(ELSE); 						//matches else token
           statement();							//checks for tokens in statement
        }
    }   

    void assignment_statement()
    {   
        match(ID); 
        match(ASSIGN); 
        expression();
        match(TERMINATOR);
    }

    void print_statement() 
    {
        match(PRINT); 
        match(LPAREND);
          if (lookaheadToken.tokenType == STRING)
          {
            match(STRING);
          }
          else
          {
             expression();
          }
        match(RPAREND);
        match(TERMINATOR);        
    }

    void expression() 
    {
        term();
        while (lookaheadToken.tokenType == ADDOP)
        {
            match(ADDOP);
            term();
        }
    }

    void term()
    {
        factor();
        while (lookaheadToken.tokenType == MULOP) 
        {
            match (MULOP);
            factor();
        }
    }

    void factor()
    {  
        if (lookaheadToken.tokenType == ID) 
        {
            match(ID);
        }
        else if (lookaheadToken.tokenType == NUM) 
        {
            match(NUM);
        }
        else if (lookaheadToken.tokenType == LPAREND)
             {match(LPAREND); expression(); match(RPAREND);}
        else Error.report
             (ERROR, OPERAND_EXPECTED, lookaheadToken);
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

        for (int i = 0; i < args.length; i++)
        {
            if ((args[i].toLowerCase()).equals("-s")) printSource = false;
            else System.err.println("Flags: -s. " + args[i] + " no good");
        }
        Compiler C = new Compiler(printSource);
        C.program();
    }
}


