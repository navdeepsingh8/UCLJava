import java.io.*;
import java.util.*;

public class Emitter implements Interface.EmitterIF, Interface.ErrorHandlerIF
{
    // symbols and variables for formatting the target code
    static final int TAB = 4, MAXINDENT = 32;
    int indentation = TAB;


    // for mapping operators to opcodes; see constructor
    Hashtable mapTable = new Hashtable();
 
    Optimiser Optimiser = null;
    ErrorHandler Error;
   
    public Emitter(Object O, ErrorHandler Error)
    {
        if (O != null)
        {
            Optimiser = (Optimiser) O;
            Optimiser.Emitter = this; // let Optimiser know of Emitter
        }
        this.Error = Error;
        mapTable.put("/", "div");
        mapTable.put("%", "mod");
        mapTable.put("==", "=");
        mapTable.put("!=", "<>");
        mapTable.put("++", "inc");
        mapTable.put("--", "dec");
        mapTable.put("PRINT", "print");
        mapTable.put("READ", "read");
    }

    public void emit(String opcode, String tokenvalue)
    {     
        if (Optimiser != null)
		 Optimiser.peepholeOptimiser(opcode, tokenvalue);
       else 
             output(opcode, tokenvalue);
    }

    public void emit(String opcode, int label)
    {     
        emit(opcode, String.valueOf(label));
    }

    public void emit(String opcode)
    { 
        if (opcode == INDENT) indentation += TAB;
        else if (opcode == UNINDENT) indentation -= TAB;
        else emit(opcode, "");
    }

    public void output(String opcode, String tokenvalue)
    {    
        if (Error.mode != OK) return; 
        if (opcode == FLUSH) return; // for printing source
        if (opcode == LABEL) skip(indentation - TAB);
        else skip(indentation);
        // see if the opcode needs mapping, eg "/" to div
        if (mapTable.containsKey(opcode)) 
             opcode = (String)mapTable.get(opcode);
        System.out.println(opcode + " " + tokenvalue);
    }

    void skip (int n) // indent n spaces
    {
        if (n>MAXINDENT) n = MAXINDENT;
        while (n-- > 0) System.out.print(" ");
    }

    public static void main(String[] args)
    {
      Emitter Emit = new Emitter(null, new ErrorHandler());
      System.out.println("\nSome code");
      for (int i = 0; i < 2; i++)
      {
        Emit.emit(RVALUE, "foo");
        Emit.emit(POP);
        Emit.emit(SEPARATOR, "comment");
        Emit.emit(INDENT);
        Emit.emit(PUSH,"1"); 
        Emit.emit("-");
        Emit.emit(GOTO,17);
        Emit.emit(LABEL,17);
        Emit.emit("div");
        Emit.emit(SEPARATOR, "the next instruction is /");
        Emit.emit("/");
        if (i == 0)
        {
           System.out.println("\nThe same code optimised");
           Emit = new Emitter(new Optimiser(true), new ErrorHandler());
        }
      }
    }
}
