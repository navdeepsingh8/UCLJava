import java.io.PrintStream;

public class FullCompiler
    implements Interface.TokenIF, Interface.EmitterIF, Interface.ErrorHandlerIF
{

    ErrorHandler Error;
    Emitter Emitter;
    Lexer Lexer;
    Token lookaheadToken;
    int label;
    int mismatchCount;

    public FullCompiler(boolean flag, boolean flag1, boolean flag2)
    {
        label = 17;
        mismatchCount = 0;
        Error = new ErrorHandler();
        if(flag1)
        {
            Emitter = new Emitter(new Optimiser(flag2), Error);
        } else
        {
            Emitter = new Emitter(null, Error);
        }
        Lexer = new Lexer(Error, Emitter, flag);
        lookaheadToken = Lexer.getToken();
    }

    int unique()
    {
        return label++;
    }

    public void program()
    {
        statement();
        while(lookaheadToken.tokenType != "EOF") 
        {
            Emitter.emit("; ----");
            statement();
        }
        match("EOF");
    }

    void statement()
    {
        if(lookaheadToken.tokenType == "LBRACE")
        {
            compound_statement();
        } else
        if(lookaheadToken.tokenType == "ID")
        {
            assignment_statement();
        } else
        if(lookaheadToken.tokenType == "PRINT")
        {
            print_statement();
        } else
        if(lookaheadToken.tokenType == "IF")
        {
            if_statement();
        } else
        if(lookaheadToken.tokenType == "WHILE")
        {
            while_statement();
        } else
        if(lookaheadToken.tokenType == "DO")
        {
            do_while_statement();
        } else
        if(lookaheadToken.tokenType == "FOR")
        {
            for_loop_statement();
        } else
        {
            Error.report(2, 5, lookaheadToken);
        }
        if(Error.mode != 0)
        {
            recover();
        }
    }

    void compound_statement()
    {
        match("LBRACE");
        while(lookaheadToken.tokenType != "RBRACE") 
        {
            statement();
            if(lookaheadToken.tokenType != "RBRACE")
            {
                Emitter.emit("; ----");
            }
        }
        match("RBRACE");
    }

    void assignment_statement()
    {
        assignment_expression();
        Emitter.emit("pop");
        match("TERMINATOR");
    }

    void print_statement()
    {
        match("PRINT");
        match("LPAREND");
        do
        {
            if(lookaheadToken.tokenType == "STRING")
            {
                Emitter.emit("PRINT", lookaheadToken.tokenValue);
                match("STRING");
            } else
            {
                expression();
                Emitter.emit("write");
            }
            if(lookaheadToken.tokenType == "CATOP")
            {
                match("CATOP");
            } else
            {
                match("RPAREND");
                match("TERMINATOR");
                return;
            }
        } while(true);
    }

    void if_statement()
    {
        match("IF");
        int i = unique();
        int j = unique();
        match("LPAREND");
        expression();
        match("RPAREND");
        Emitter.emit("gofalse", i);
        statement();
        if(lookaheadToken.tokenType == "ELSE")
        {
            Emitter.emit("goto", j);
            Emitter.emit("label", i);
            match("ELSE");
            statement();
            Emitter.emit("label", j);
        } else
        {
            Emitter.emit("label", i);
        }
    }

    void while_statement()
    {
        int i = unique();
        int j = unique();
        match("WHILE");
        Emitter.emit("label", i);
        match("LPAREND");
        expression();
        match("RPAREND");
        Emitter.emit("gofalse", j);
        Emitter.emit("indent");
        statement();
        Emitter.emit("unindent");
        Emitter.emit("goto", i);
        Emitter.emit("label", j);
    }

    void do_while_statement()
    {
        int i = unique();
        match("DO");
        Emitter.emit("label", i);
        Emitter.emit("indent");
        statement();
        Emitter.emit("unindent");
        match("WHILE");
        match("LPAREND");
        expression();
        match("RPAREND");
        Emitter.emit("gotrue", i);
        match("TERMINATOR");
    }

    void for_loop_statement()
    {
        int loop = unique();
        int next = unique();
        int body = unique();
        int increment = unique();
        
        match("FOR");
        match("LPAREND");
        
        assignment_expression();
        Emitter.emit("pop");
        match("TERMINATOR");
        
        Emitter.emit("label", loop);
        expression();
        Emitter.emit("gotrue", body);
        Emitter.emit("goto", next);
        match("TERMINATOR");
        
        Emitter.emit("label", increment);
        assignment_expression();
        Emitter.emit("pop");
        Emitter.emit("goto", loop);
        match("RPAREND");
        
        Emitter.emit("label", body);
        statement();
        Emitter.emit("goto", increment);
        
        Emitter.emit("label", next);
    }

    void expression()
    {
        arithmetic_expression();
        if(lookaheadToken.tokenType == "RELOP")
        {
            Token token = lookaheadToken;
            match("RELOP");
            arithmetic_expression();
            Emitter.emit(token.tokenValue);
        }
    }

    void arithmetic_expression()
    {
        term();
        while(lookaheadToken.tokenType == "ADDOP") 
        {
            Token token = lookaheadToken;
            match("ADDOP");
            term();
            Emitter.emit(token.tokenValue);
        }
    }

    void term()
    {
        factor();
        while(lookaheadToken.tokenType == "MULOP") 
        {
            Token token = lookaheadToken;
            match("MULOP");
            factor();
            Emitter.emit(token.tokenValue);
        }
    }

    void factor()
    {
        if(lookaheadToken.tokenType == "ID" && Lexer.nextToken().tokenType == "ASSIGN")
        {
            assignment_expression();
        } else
        if(lookaheadToken.tokenType == "ID" && Lexer.nextToken().tokenType == "INCOP")
        {
            assignment_expression();
        } else
        if(lookaheadToken.tokenType == "ID")
        {
            Emitter.emit("rvalue", lookaheadToken.tokenValue);
            match("ID");
        } else
        if(lookaheadToken.tokenType == "NUM")
        {
            Emitter.emit("push", lookaheadToken.tokenValue);
            match("NUM");
        } else
        if(lookaheadToken.tokenType == "LPAREND")
        {
            match("LPAREND");
            expression();
            match("RPAREND");
        } else
        if(lookaheadToken.tokenType == "READ")
        {
            match("READ");
            Emitter.emit("READ");
        } else
        {
            Error.report(2, 7, lookaheadToken);
        }
    }

    void assignment_expression()
    {
        if(lookaheadToken.tokenType == "ID" && Lexer.nextToken().tokenType == "INCOP")
        {
            Token token = lookaheadToken;
            match("ID");
            Emitter.emit("rvalue", token.tokenValue);
            Emitter.emit("lvalue", token.tokenValue);
            Emitter.emit("rvalue", token.tokenValue);
            Token token2 = lookaheadToken;
            match("INCOP");
            Emitter.emit(token2.tokenValue);
            Emitter.emit("store");
        } else
        {
            Token token1 = lookaheadToken;
            match("ID");
            match("ASSIGN");
            Emitter.emit("lvalue", token1.tokenValue);
            Emitter.emit("indent");
            expression();
            Emitter.emit("unindent");
            Emitter.emit("store");
            Emitter.emit("rvalue", token1.tokenValue);
        }
    }

    void match(String s)
    {
        if(Error.mode == 0)
        {
            if(lookaheadToken.tokenType == s)
            {
                lookaheadToken = Lexer.getToken();
            } else
            if(s == "TERMINATOR")
            {
                Error.report(1, 6, lookaheadToken, "");
            } else
            {
                Error.report(2, 4, lookaheadToken, s);
            }
        } else
        if(mismatchCount++ > 10)
        {
            lookaheadToken = Lexer.getToken();
        }
    }

    void recover()
    {
        for(; lookaheadToken.tokenType != "TERMINATOR" && lookaheadToken.tokenType != "RBRACE"; lookaheadToken = Lexer.getToken()) { }
        lookaheadToken = Lexer.getToken();
        mismatchCount = 0;
        Error.mode = 0;
        if(lookaheadToken.tokenType != "EOF")
        {
            Error.printErrorLine(lookaheadToken);
        }
    }

    public static void main(String args[])
    {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = false;
        for(int i = 0; i < args.length; i++)
        {
            if(args[i].toLowerCase().equals("-p"))
            {
                flag2 = true;
            } else
            if(args[i].toLowerCase().equals("-o"))
            {
                flag1 = false;
            } else
            if(args[i].toLowerCase().equals("-s"))
            {
                flag = false;
            } else
            {
                System.err.println("Flags: -S -O -p. " + args[i] + " no good");
            }
        }

        FullCompiler fullcompiler = new FullCompiler(flag, flag1, flag2);
        fullcompiler.program();
    }
}
