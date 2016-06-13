import java.util.Stack;
import java.util.Hashtable;
import java.text.DecimalFormat;
import java.io.*;

public class Memory implements ErrorTypesIF
{ 
    ErrorHandler Error;

    public Memory(ErrorHandler E) {Error = E;};

    boolean warned = false;

    // stackCheckingMode is set by the parser if it
    // finds the directives 'eager' or 'lazy' in the asm source;
    // it controls the load time checking of the program
    public String stackCheckingMode = "";

    static String spaces = "                         "; // for printing source
 
    public Hashtable label = new Hashtable();

    public void putLabel(String l, int pc) 
    {
        if (label.containsKey(l))
            Error.print("Warning: duplicate label <" + l + "> ignored");
        else label.put(l, new Integer(pc));
    }

    public int getLabel(String l) // used by checkProgramStack and Interpreter
    {
        if (!label.containsKey(l))
        {
            Error.print("label not declared: " + label);
            return pc; // terminates program
        }
        return ((Integer)label.get(l)).intValue();
    }

    static final int MEMORY_SIZE = 500;

    MemoryEl program[] = new MemoryEl[MEMORY_SIZE];
    int pc = 0;    // ProgramCounter 

    public void store(int lineNo, String opCode, String operand)
    {
      if (Error.mode != OK) return;
      program[pc] = new MemoryEl(lineNo, opCode, operand);
      if (opCode == "label") putLabel(operand, pc);
      if (!warned) 
      {
        // simulate stack to check for errors 
        // state of stack is uncertain after a 'goto'.
        String prevStack ;
        if ((program[pc].opCode == "label")
                  && (stackCheckingMode == "lazy"))
                      prevStack = "*";
        else if (pc == 0) 
            prevStack = "";
        else
            prevStack = program[pc-1].stack; 
        program[pc].stack = simulateStack(opCode, prevStack);
        // printLine(pc);
      }
      pc++;
    }

    String simulateStack(String opCode, String prevStack)
    {
       if (warned) return "";
       // System.out.println("prevStack = " + prevStack);
       Stack stack = stringToStack(prevStack);
       OpDef op = OpDef.getDef(opCode);
       char tos =  conditionalPop(stack, op.TOS);
       char ntos = conditionalPop(stack, op.NTOS);
       //check that the types are as expected
       if ((tos == '-') || (ntos == '-')) 
       {
            Error.report(WARN, STACK_UNDERFLOW);
            warned = true;
       }
       else if (!matches(tos, op.TOS) || !matches(ntos, op.NTOS))
       {
           Error.report(WARN, WRONG_OPERAND_TYPE); 
           warned = true;
       }
       push(op.resultType, tos, ntos, stack);
       if (warned) return ""; else return stackToString(stack);
    }

    char conditionalPop(Stack stack, char type)
    // pops the simulated stack if an operand is expected
    // if the top of stack is '*' it pushes it back on
    // returns '-' if stack empty
    { 
       if (type == ' ') return ' ';
       if (stack.empty()) return '-';
       char tos = ((String)stack.pop()).charAt(0);
       if (tos == '*') stack.push("*");
       return tos;
    }

    boolean matches(char actual, char expected)
    {
        return (actual == expected) || (actual == '*') || (expected == '*'); 
    }

    // pushes the appropriate type onto the simulated stack
    void push(char type, char tos, char ntos, Stack stack)
    {
        switch (type)
        {
           case ' ': return;
           case '+': conditionalPush(stack, tos);
                     conditionalPush(stack, tos);
                     return;
           case '~': conditionalPush(stack, tos);
                     conditionalPush(stack, ntos);
                     return;
           default:  conditionalPush(stack, type);
         }
    }

    void conditionalPush(Stack stack, char type)
    // push 'type' onto stack unless it is a '*' and the tos is already '*'
    {
         if ((type == '*') && ((String)stack.peek()=="*")) return;
         stack.push(String.valueOf(type));
    }   

    String stackToString(Stack stack)
    {
        StringBuffer s = new StringBuffer("");
        while (!stack.empty())
        {
             s.append((String)stack.pop());
        }
        return s.toString();
    }

    Stack stringToStack(String s)
    {
        Stack st = new Stack();
        for (int i = s.length(); i > 0; i--)
        {
             st.push(s.substring(i-1,i));
        }
        return st;
    }

    void checkProgramStack()
    {
        for (int i = 0; i < this.pc; i++)
        {
            MemoryEl p = program[i];
            if (p.opCode == "goto"
              | p.opCode == "gotrue" 
              | p.opCode == "gofalse")
            {   // check stack here matches destination
                // System.err.println("Checking "); printLine(i);
                int j = getLabel(p.operand);
                MemoryEl d = program[j];
                if (!stackMatches(p.stack, d.stack))
                {
                     System.err.println
                        ("Possible stack underflow/overflow/mismatch");
                     printLine(System.err, min(i,j));
                     printLine(System.err, max(i,j));
                }
            }
        }
    }

    static boolean stackMatches(String s, String d)
    {
         if ( s.equals(d) | s.equals("*") | d.equals("*") ) return true;
         if ( s.equals("") | d.equals("") ) return false;
         return head(s)==head(d) & tail(s).equals(tail(d));
    }
    static char head(String s) {return s.charAt(0);}
    static String tail(String s) {return s.substring(1);}

    void printProgram()
    {
        System.out.println("The program");
        System.out.println("Memory Line  "+spaces+"Stack");

        int lastLineNo = 0;
        for (int i = 0; i < pc; i++)
        {
            // add blank lines as in source 
            MemoryEl p = program[i];
            while (++lastLineNo < p.lineNo)
            {   
                System.out.println("       " 
                  + ddd(lastLineNo) +  ": ");
            }
            printLine(i);
        }
        System.out.println();
    }

    public void printLine(int i) {printLine(System.out, i);}
    void printLine(PrintStream printStream, int i)
    {
        MemoryEl p = program[i];
        printStream.print("[" + ddd(i) + "]  "
            + ddd(p.lineNo) +  ": "
            + p.opCode + " ");
        if (p.opCode == "print")
            printStream.println("\"" + p.operand + "\"");
        else 
            printStream.println( p.operand 
            + spaces.substring(min(spaces.length(), 
                                   p.opCode.length() + p.operand.length()))
            + '[' + p.stack + ']');
    }

    public static int min (int m, int n) {return m<n?m:n;}
    public static int max (int m, int n) {return m>n?m:n;}

    String ddd(int n)
    {
        String sss = "   ";
        String s = String.valueOf(n%1000);
        return sss.substring(s.length()) + s;
    }

    public static void main(String[] args)
    {
         ErrorHandler E = new ErrorHandler();
         Memory M = new Memory(E);
         M.stackCheckingMode = "eager";
         M.store(1, "push", "1"); 
         M.store(2, "label", "loop");
         M.store(4, "lvalue", "foo"); 
         M.store(5, "print", "hello");
         M.store(6, "goto", "loop");
         M.printProgram();
         M.checkProgramStack();
    }

}
