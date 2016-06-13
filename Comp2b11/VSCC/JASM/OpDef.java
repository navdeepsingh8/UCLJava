import java.util.*;
import java.lang.*;

public class OpDef implements MappingsIF
{
    public String opCode;       // the opcode
    public char   operandType,  // the type of its operand, if any
                  TOS,          // what it expects on top of stack
                  NTOS,         // what it expects next on stack
                  resultType;   // what it leaves on the stack

    public OpDef (String opCode, char operandType, 
                   char tos, char ntos, char resultType) 
    {
       this.opCode = opCode; 
       this.operandType = operandType; 
       this.TOS = tos; 
       this.NTOS = ntos; 
       this.resultType = resultType;
       opTable.put(opCode, this);
    }
    
    public String toString()
    {
         return '['+opCode + "][" + operandType + TOS + NTOS + resultType+']';
    }

    static Hashtable opTable = new Hashtable(100);

    static Hashtable mapTable = new Hashtable();

    public static boolean isOpCode(String s) {return opTable.containsKey(s);}

    public static String mappedCode(String s)
    {
        return mapTable.containsKey(s) ? (String)mapTable.get(s) : s ;
    }
             
    public static OpDef getDef(String s)
    { 
        return (OpDef)opTable.get(s); 
    }

    static 
    {
        new OpDef("lvalue", 'i', ' ', ' ', 'l');
        new OpDef("rvalue", 'i', ' ', ' ', 'r');
        new OpDef("push",   'r', ' ', ' ', 'r');
        new OpDef("store",  ' ', 'r', 'l', ' ');
        new OpDef("erots",  ' ', 'l', 'r', ' ');
        new OpDef("copy",   ' ', '*', ' ', '+');
        new OpDef("pop",    ' ', '*', ' ', ' ');
        new OpDef("swap",   ' ', '*', '*', '~');
        new OpDef("read",   ' ', ' ', ' ', 'r');
        new OpDef("write",  ' ', 'r', ' ', ' ');
        new OpDef("halt",   ' ', ' ', ' ', ' ');
        new OpDef("dump",   ' ', ' ', ' ', ' ');
        new OpDef("print",  's', ' ', ' ', ' ');
        new OpDef("label",  '*', ' ', ' ', ' ');
        new OpDef("goto",   '*', ' ', ' ', ' ');
        new OpDef("gotrue", '*', 'r', ' ', ' ');
        new OpDef("gofalse",'*', 'r', ' ', ' ');
        new OpDef("inc",    ' ', 'r', ' ', 'r');
        new OpDef("dec",    ' ', 'r', ' ', 'r');
        new OpDef("not",    ' ', 'r', ' ', 'r');
        new OpDef("or",     ' ', 'r', 'r', 'r');
        new OpDef("and",    ' ', 'r', 'r', 'r');
        new OpDef("<",      ' ', 'r', 'r', 'r');
        new OpDef("<=",     ' ', 'r', 'r', 'r');
        new OpDef("=",      ' ', 'r', 'r', 'r');
        new OpDef(">",      ' ', 'r', 'r', 'r');
        new OpDef(">=",     ' ', 'r', 'r', 'r');
        new OpDef("<>",     ' ', 'r', 'r', 'r');
        new OpDef("+",      ' ', 'r', 'r', 'r');
        new OpDef("-",      ' ', 'r', 'r', 'r');
        new OpDef("/",      ' ', 'r', 'r', 'r');
        new OpDef("%",      ' ', 'r', 'r', 'r');
        new OpDef("*",      ' ', 'r', 'r', 'r');
        new OpDef("gcd",    ' ', 'r', 'r', 'r');
        new OpDef("eager",  ' ', ' ', ' ', ' '); //pseudocode
        new OpDef("lazy",   ' ', ' ', ' ', ' '); //pseudocode
        new OpDef("end",    ' ', ' ', ' ', ' '); //pseudocode
        // initialise mapTable
        for (int i = 0; i<map.length; i++)
           mapTable.put(map[i].synonym, map[i].opcode); 
    }
 
    public static void main(String[] args)
    {
        System.err.println("The OpDefs"); 
        Enumeration E = opTable.keys(); 
        while (E.hasMoreElements()) 
        {
            String key = (String)E.nextElement();
            OpDef p = getDef(key);
            System.out.println(key + "\t" + '[' + p.operandType +
                p.TOS +p.NTOS+p.resultType +']');
        }
        System.out.println("push "+ isOpCode("push")); 
        System.out.println("+ " + isOpCode("+"));
        System.out.println("div" + isOpCode("div"));
        System.out.println("++ " + isOpCode("++")); 
        System.out.println("plush " + isOpCode("plush")); 
        String s = "p", t = "ush"; 
        System.out.println(s+t + " " + isOpCode(s+t));
        System.out.println("++" + " " + isOpCode("++"));
    }
}
