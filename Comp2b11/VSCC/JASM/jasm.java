import java.lang.*;
import java.util.*;
import CountingStack;
import java.math.BigInteger;

public class jasm implements TokenIF
{
    int pc = 0;

    Parser Parser;
    Memory Memory;
    Lexer Lexer; // used for run-time input as well as loading
    ErrorHandler Error;

    public jasm(Parser P, Memory M, Lexer L, ErrorHandler E)
        {Parser = P; Memory = M; Lexer = L; Error = E;}
         
    CountingStack s = new CountingStack();  // run time stack
    final int MAXSTACK = 50;

    Hashtable Data = new Hashtable(100);

    void save(String id, String value) 
    {
       if (id.length() == 0 | !Character.isLetter(id.charAt(0)))
           abort("lvalue expected as popped operand of store");
       if (value.length() == 0 | !Character.isDigit(value.charAt(0)))
           abort("rvalue expected as popped operand of store");
       Data.put(id, value);
    }

    String get(String id)
    {
       if (id.length() == 0 | !Character.isLetter(id.charAt(0)))
           abort("lvalue expected as popped operand of rvalue");
       if (Data.containsKey(id)) return (String)Data.get(id); 
       abort("Uninitialized variable " + id);
       return "0"; // to keep compiler happy
    }

    public void interpret()
    { 
        MemoryEl p; 
        try
        {
            while (pc < Memory.pc) 
               execute(p = Memory.program[pc]);
        }
        catch (EmptyStackException e) 
        {
            abort("Stack underflow"); 
        }
        catch (ArithmeticException e) 
        {
            abort("Arithmetic error (division by zero?)");
        }
        catch (Exception e)
        {
            abort("Exception : " + e);
        }
        finally
        {
            terminate("Normal");
        }
        System.out.println("End of execution");
    }

    void terminate(String msg)
    {
            if (pc >= Memory.pc) pc = Memory.pc - 1; //'run off the end'
            System.out.println("\n\n" + msg + " termination at");
            printLine(pc);
            dump();
            if ((Memory.stackCheckingMode != "") 
               ||Memory.warned 
               || (msg != "Normal")) 
            {
                     Memory.printProgram();
                     Memory.checkProgramStack();
            }
            System.exit(0);
    }

    void printLine(int pc)
    {
        MemoryEl p = Memory.program[pc];
        System.out.print(p.lineNo + ": " + p.opCode + " " + p.operand);
    }
    
    void execute(MemoryEl p)
    {
        OpDef op = OpDef.getDef(p.opCode);
        // System.out.print("Executing line "); printLine(pc);
        if (MAXSTACK > 0 && s.count() > MAXSTACK)
          abort("Stack overflow"); // change MAXSTACK
        if (((op.TOS  != ' ') && (s.count() < 1))
        ||  ((op.NTOS != ' ') && (s.count() < 2)))
          abort("Attempted stack underflow"); // without popping stack
        String tos = "", ntos = "";
        if (op.TOS != ' ') tos = (String)s.pop();
        if (op.NTOS != ' ') ntos = (String)s.pop();

        String c = p.opCode;
        if      (c == "lvalue") {s.push(p.operand);}
        else if (c == "rvalue") {s.push(get(p.operand));}
        else if (c == "push")   {s.push(p.operand);}
        else if (c == "store")  {save(ntos, tos);}
        else if (c == "erots")  {save(tos, ntos);}
        else if (c == "copy")   {s.push(tos); s.push(tos);}
        else if (c == "pop")    {}
        else if (c == "swap")   {s.push(tos); s.push(ntos);}
        else if (c == "read")   {s.push(read());}
        else if (c == "write")  {System.out.print(tos);}
        else if (c == "halt")   {terminate("Normal");}
        else if (c == "dump")   {dump();}
        else if (c == "print")  {print(p.operand);}
        else if (c == "label")  {/* stored by parser */}
        else if (c == "goto")   {pc = goTo(p.operand) - 1;}
        else if (c == "gotrue") {if (!tos.equals("0"))pc = goTo(p.operand) - 1;}
        else if (c == "gofalse"){if ( tos.equals("0"))pc = goTo(p.operand) - 1;}
        else if (c == "inc")    {s.push(eval(tos, "+", "1"));}
        else if (c == "dec")    {s.push(eval(tos, "-", "1"));}
        else if (c == "not")    {s.push(tos.equals("0")?"1":"0");}
        else if (c == "and")    {s.push((tos != "0") && (ntos != "0")?"1":"0");}
        else if (c == "or")     {s.push((tos != "0") || (ntos != "0")?"1":"0");}
        else {s.push(eval(ntos, c, tos));}
        pc++;
    }

    String eval(String ntos, String op, String tos)
    {
        //System.out.("Eval(" + ntos +"," + op + "," + tos + ")");
        BigInteger m = new BigInteger("0");
        BigInteger n = new BigInteger("0");
 
        try
        {
            m = new BigInteger(ntos);
            n = new BigInteger(tos);
        }
        catch(Exception e)
        {
            abort("Non-numeric operand popped from top of stack\n" + e);
        }
        String result = "";

        // arithmetic operators
        if      (op == "+")  result = (m.add(n)).toString();
        else if (op == "-")  result = (m.subtract(n)).toString();
        else if (op == "*")  result = (m.multiply(n)).toString();
        else if (op == "/")  result = (m.divide(n)).toString();
        else if (op == "%")  result = (m.remainder(n)).toString();
        else if (op=="gcd")  result = (m.gcd(n)).toString();
        if (result != "") return result.equals("0") ? "0" : result;

        // comparison operators
        int comparison = m.compareTo(n), bool = 0;
        if      (op == "<")  bool = comparison <  0 ? 1 : 0;
        else if (op == "<=") bool = comparison <= 0 ? 1 : 0;
        else if (op == "=")  bool = comparison == 0 ? 1 : 0; 
        else if (op == ">=") bool = comparison >= 0 ? 1 : 0;
        else if (op == ">")  bool = comparison >  0 ? 1 : 0;
        else if (op == "<>") bool = comparison != 0 ? 1 : 0;
        else abort("asm Error - unrecognised opcode");
        return bool == 0 ? "0" : "1";
    }

/*    static String eval(String ntos, String op, String tos)
    {
        //System.out.("Eval(" + ntos +"," + op + "," + tos + ")");
        long m = 0, n = 0, result = 0;
        try 
        {
            m = Integer.parseInt(ntos);
            n = Integer.parseInt(tos);
        }
        catch (Exception e)
        {
            abort("Wrong type of operand on stack: " + e);
        }
        if      (op == "+")  result = m + n;
        else if (op == "-")  result = m - n;
        else if (op == "*")  result = m * n;
        else if (op == "/")  result = m / n;
        else if (op == "%")  result = m % n;
        else if (op == "<")  result = m <  n ? 1 : 0;
        else if (op == "<=") result = m <= n ? 1 : 0;
        else if (op == "=")  result = m == n ? 1 : 0; 
        else if (op == ">=") result = m >= n ? 1 : 0;
        else if (op == ">")  result = m >  n ? 1 : 0;
        else if (op == "<>") result = m != n ? 1 : 0;
        else abort("asm Error - unrecognised opcode");
        // System.out.println(" = " + result);
        // the tos is tested for false ("0") with == so return a constant
        return result == 0 ? "0" : String.valueOf(result);
    }
*/
    int goTo(String L) {return Memory.getLabel(L);}

    void print(String operand)
    {
        int i = 0;
        operand = operand + " ";
        while (i < operand.length() - 1)
        {
            char ch = operand.charAt(i);
            if (ch == '\\') 
            {
                ch = operand.charAt(++i);
                switch (ch)
                {
                    case 'n': System.out.println(); break;
                    case 't': System.out.print('\t');break;
                    default: System.out.print(ch);
                 }
             }
             else System.out.print(ch);
             i++;
         }
    }
 /*
    void check(String c) //throws BadlyTypedException
    {
         // check that the stack contains the correctly typed operands
         OpDef opDef = OpDef.getDef(c);
         if (opDef.TOS != ' ') 
         {
               OpDef op = (OpDef)s.pop();
               if ((opDef.TOS != 'x') && (opDef.TOS != ' '));
         }
    }
 */     
    public String read()
    {
         Token input = Lexer.getToken();
         while (input.tokenType != NUM) 
         {
            if (input.tokenType == EOF) abort("End of file during a read");
            if (input.tokenType != NEWLINE) System.out.print("\007");
            input = Lexer.getToken();
         }
         return input.tokenValue;
    }

    public void dump()
    {
         Stack t = new Stack();
         System.out.println();System.out.println();
         if (s.empty()) System.out.println("The stack is empty");
         else System.out.println("Contents of Stack, top of stack first");
         while (!s.empty())
         {
             String tos = (String)s.pop();
             System.out.println(tos);
             t.push(tos);
         }
         // restore stack
         while (!t.empty()) s.push((String)t.pop());

        Enumeration E = Data.keys();
        System.out.println();
        while (E.hasMoreElements()) 
        {
            String key = (String)E.nextElement();
            System.out.println("Variable: "+ key + "\t" + Data.get(key));
        }
/*
        Enumeration F = Memory.label.keys();
        while (F.hasMoreElements()) 
        {
            String key = (String)F.nextElement();
            System.out.println("Label:" + key + "\t" + Memory.label.get(key));
        }
*/
    }
    public void abort(String msg)
    {
        System.out.println("\n" + msg);
        terminate("Abnormal");
    }

    public static void main(String[] args)
    {
       ErrorHandler E = new ErrorHandler();
       SourceReader S = new SourceReader(E, args.length == 0 ? "" : args[0]);
       Lexer L = new Lexer(S, E);        
       Memory M = new Memory(E);
       Parser P = new Parser(M, L, S, E);
       jasm I = new jasm(P, M, L, E);
       P.program();
       if (M.pc == 0) 
       {
           System.out.println("No program to execute");
           return;
       }
       try
       {
           M.checkProgramStack();
           I.interpret();
       }
       catch (Exception e)
       {
           I.abort("Unexpected error " + e);
       }
    }
}

