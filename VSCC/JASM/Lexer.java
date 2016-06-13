public class Lexer implements TokenIF, ErrorTypesIF
{
    boolean atEOF = false;
    int lineNo = 0;

    SourceReader SourceReader;
    ErrorHandler Error;

    public Lexer(SourceReader S, ErrorHandler E)
    {
       SourceReader = S;
       Error = E;
    }

    public Token getToken()
    {
        // need to allow a lookahead when at EOF
        if (SourceReader.EOF) {
            if (!atEOF) {
                atEOF = true;
                return new Token(PAST_EOF, NONE);
            } else {Error.report(FATAL, AT_EOF);}
        }

        char t = SourceReader.get();

        // strip white space
        while (t == ' ' || t == '\t')
            t = SourceReader.get();

        // a comment?
        if (t==';') 
            while ( (t=SourceReader.get()) != '\n')
                ;

        lineNo = SourceReader.lineNo;
        SourceReader.markStartOfToken(); // for error printing

        // a newline?
        if (t == '\n')
            return new Token(NEWLINE, NONE);

        if (SourceReader.EOF) return new Token(EOF, NONE);

        StringBuffer lexeme = 
            new StringBuffer(String.valueOf(t));

        // an integer?
        if (Character.isDigit(t))
        {
            while (Character.isDigit(t=SourceReader.get()))    
                lexeme.append(t);
            SourceReader.unget();
            return new Token(NUM, lexeme.toString());
        }

        // a 'word'? i.e. a keyword or identifier? or a new-style label
        if (Character.isLetter(t)) 
        {
          while (Character.isLetterOrDigit(t=SourceReader.get()))
              lexeme.append(t);
          if (t != ':')
          {
            SourceReader.unget();

            // the word may be a keyword or an existing identifier.
            // if not, it is assumed to be an identifier and is added
            // to the symbol table
            String word = OpDef.mappedCode(lexeme.toString());
            if (word.equals("end")) return new Token(END, word);
            return new Token(OpDef.isOpCode(word)? OPCODE : ID, word); 
          }
          else
          {
            return new Token(LABEL, lexeme.toString());
          }
        }

        // a quoted string?
        if (t == '"') 
        {
            lexeme = new StringBuffer("");
            while ((t=SourceReader.get()) != '"' && t != '\n') 
            {
                lexeme.append(t); 
            }
            if (t == '\n') Error.report(Error.WARN, Error.UNTERMINATED_STRING);
            return new Token(STRING, lexeme.toString());
        }

        // a two-character token?
        if (!Character.isWhitespace(SourceReader.peek())) 
        {
            lexeme.append(SourceReader.get());
            String cc = lexeme.toString();
            if (OpDef.isOpCode(cc))
                 return new Token(OPCODE, cc); 
            // not a two-character token.
            // 'unget' the second character.
            SourceReader.unget();
        }

        // must be a one-character token
        String c = String.valueOf(t); 
        if (OpDef.isOpCode(c))
                 return new Token(OPCODE, c); 
        
        // what is it?
        return new Token(UNKNOWN, c);
    }

    public static void main(String[] args)
    {
        ErrorHandler E = new ErrorHandler();
        SourceReader S = new SourceReader(E, "");
        Lexer L = new Lexer(S, E);
        Token t;
        do {
             t = L.getToken();
             System.err.print(t + " "); 
             if (t.tokenType == NEWLINE) System.err.println();
         } while (t.tokenType != EOF);
    }
}
