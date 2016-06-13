import java.io.*;

public class Parser implements TokenIF, ErrorTypesIF
{
    Token lookaheadToken;

    Memory Memory;
    Lexer Lexer;
    SourceReader SourceReader;
    ErrorHandler Error;

    public Parser(Memory M, Lexer L, SourceReader S, ErrorHandler E)
    {
        Memory = M;
        Lexer = L;
        SourceReader = S;
        Error = E;
    }

    public void program()
    { 
        lookaheadToken = Lexer.getToken();
        while ((lookaheadToken.tokenType != EOF)
            && (lookaheadToken.tokenType != END))
        {
		instruction(); 
        }
        match(lookaheadToken.tokenType); //match(EOF or END)
    }

    void instruction()
    {
        if (lookaheadToken.tokenType == NEWLINE) match(NEWLINE);
        else if (lookaheadToken.tokenType == OPCODE) opcode();
        else if (lookaheadToken.tokenType == LABEL) label();
        else if (lookaheadToken.tokenType == NUM) push();
        else Error.report(ERROR, STATEMENT, lookaheadToken); 
        if (Error.mode != OK) recover();    
    }

    void opcode()
    {
        // if the token is, say, {OPCODE, "lvalue"} then
        // the instance of OpDef for lvalue is retrieved;
        // this gives the type of operand expected for lvalue.
        OpDef opDef = OpDef.getDef(lookaheadToken.tokenValue); 
        match(OPCODE); 

        // check for interactive program - affects behaviour at EOF
        if (opDef.opCode.equals("read")) SourceReader.interactive = true;

        // the test for a pseudo-code of 'eager' is for a directive
        // which controls the checking of the program at load time;
        // it's a bit of a hack
        if (opDef.opCode.equals("eager"))
            Memory.stackCheckingMode = "eager";
        else if (opDef.opCode.equals("lazy"))
            Memory.stackCheckingMode = "lazy";
        else
        { 
            String operand = lookaheadToken.tokenValue; // see default below
            switch (opDef.operandType)
            {
               case 'i': match(ID); break;
               case 'r': match(NUM); break;
               case 's': match(STRING); break;
               case '*': match(lookaheadToken.tokenType); break;
               default: operand = "";  // no operand
            }
            Memory.store(Lexer.lineNo, opDef.opCode, operand); 
        }
        match(NEWLINE);
    }

    void label()
    {
        Memory.store(Lexer.lineNo, "label", lookaheadToken.tokenValue);
        match(LABEL);
        match(NEWLINE);
    }

    void push()
    {   // an integer is a degenerate form of 'push'
        String num = lookaheadToken.tokenValue;
        match(NUM); 
        Memory.store(Lexer.lineNo, "push", num); 
        match(NEWLINE); 
    }

    void match(String expectedTokenType) 
    // expect a particular token, error if not. 
    {
      //System.out.println("matching "+lookaheadToken);
        if (lookaheadToken.tokenType == expectedTokenType) 
           {lookaheadToken = Lexer.getToken();}
        else
        {
            Error.report
                (ERROR, MISMATCH,
                lookaheadToken, expectedTokenType);
            // read some input to ensure program terminates
            lookaheadToken = Lexer.getToken();
        }
    }

    void recover() 
    // synchronise source on next NEWLINE
    // Lexer quits if at EOF
    {
        while (lookaheadToken.tokenType != NEWLINE)
            {lookaheadToken = Lexer.getToken();}
        Error.mode = OK; 
        Error.printErrorLine();
    }

    public static void main(String[] args) throws IOException
    {
        ErrorHandler E = new ErrorHandler();
        SourceReader S = new SourceReader(E, "");
        Lexer L = new Lexer(S, E);
        Memory M = new Memory(E);
        Parser P = new Parser(M, L, S, E);
        P.program();
        M.printProgram();
    }
}

