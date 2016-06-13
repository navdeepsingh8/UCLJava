public class Lexer implements Interface.TokenIF, Interface.ErrorHandlerIF
{
    private ErrorHandler Error;
    private SourceReader SourceReader;
    private Token previousToken = null;
    private Token currentToken = null;
    private Token nextToken = null;
    private boolean atEOF = false;
    private SymbolTable SymbolTable = new SymbolTable();

    public Lexer(ErrorHandler Error, Object Emitter, boolean printSource) 
    {
        SourceReader = new SourceReader(Error, Emitter, printSource);
        this.Error = Error; // Emitter and printSource not used by Lexer
    }
    
    public Token getToken()
    {
        currentToken = lexToken();
        // a "+" in the context of a string is a CATOP not an ADDOP
        if (currentToken.tokenValue.equals("+"))
        {
            if (previousToken.tokenType == STRING
             || nextToken().tokenType == STRING)
            {
                 currentToken.tokenType = CATOP;
            }
        }
        previousToken = currentToken;
        return currentToken;
    }

    public Token nextToken()
    {
        if (nextToken == null) nextToken = lexToken();
        return nextToken;
    }

    private Token lexToken()
    {
        // see if the next token has already been lexed
        if (nextToken != null) {
            Token t = nextToken;
            nextToken = null;
            return t;
        }

        // need to allow a lookahead when at EOF
        if (SourceReader.EOF) {
            if (!atEOF) {
                atEOF = true;
                return new Token(PAST_EOF);
            } else {Error.report(FATAL, AT_EOF);}
        }

        SourceReader.Char t = SourceReader.get();

        // strip white space
        while (Character.isWhitespace(t.ch))
            t = SourceReader.get();
        if (SourceReader.EOF) return new Token(EOF);
        int startOfToken = t.pos;
        StringBuffer lexeme = new StringBuffer("");

        // an integer?
        if (Character.isDigit(t.ch))
        {
            while (Character.isDigit(t.ch))
            {    
                lexeme.append(t.ch);
                t = SourceReader.get();
            }
            SourceReader.unget();
            return new Token(NUM, lexeme.toString(), startOfToken);
        }

        // a 'word'? i.e. a keyword or identifier?
        if (Character.isLetter(t.ch)) 
        {
            while (Character.isLetterOrDigit(t.ch))
            {
                lexeme.append(t.ch);
                t=SourceReader.get();
            }
            SourceReader.unget();

            // the word may be a keyword or an existing identifier.
            // if not, it is assumed to be an identifier and is added
            // to the symbol table
            String word = lexeme.toString();
            if (SymbolTable.containsKey(word)) 
            {
                String type = SymbolTable.getTokenType(word);
                return new Token(type, word, startOfToken);
            } else {
                Token token = new Token(ID, word, startOfToken);
                SymbolTable.addToken(token);
                return token;
            }
        }

        // a quoted string?
        if (t.ch == '"') 
        {
            lexeme.append('"');
            t = SourceReader.get();
            while (t.ch != '"' && t.ch != '\n')
            {
                lexeme.append(t);
                t = SourceReader.get();
            }
            lexeme.append('"');
            Token token = 
              new Token(STRING, lexeme.toString(), startOfToken);
            if (t.ch == '\n') Error.report
                (Error.WARN, Error.UNTERMINATED_STRING, token, "");
            return token;
        }

        // a two-character token?
        if (!Character.isWhitespace(SourceReader.peek())) 
        {
            SourceReader.Char nextChar = SourceReader.get();
            String cc = String.valueOf(t.ch) + nextChar.ch;
            if (SymbolTable.containsKey(cc))
               return new Token(SymbolTable.getTokenType(cc), cc, startOfToken);
            // not a two-character token.
            // 'unget' the second character.
            SourceReader.unget();
        }
  
        // must be a one-character token
        String c = String.valueOf(t.ch);
        if (SymbolTable.containsKey(c))
             return new Token(SymbolTable.getTokenType(c), c, startOfToken);
        else return new Token(UNKNOWN, c, startOfToken);
    }

    public static void main(String[] args)
    {
        ErrorHandler E = new ErrorHandler();
        Lexer L = new Lexer(E, null, true);
        do 
            System.out.print(L.getToken() + " "); 
        while (!L.atEOF);
    }
}
