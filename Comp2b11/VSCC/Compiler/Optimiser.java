public class Optimiser implements Interface.EmitterIF
{
    Emitter Emitter;
    String savedOpcode = "", 
           savedValue = "";
    boolean DEBUG;

    public Optimiser(boolean debug) {DEBUG = debug;}
    public Optimiser(Emitter E, boolean debug) {Emitter = E; DEBUG = debug;}

    public void peepholeOptimiser(String opcode, String value)
    {
        if (DEBUG) {
            output("                    ; " + opcode, value); 
        }
        if (opcode == SEPARATOR && savedOpcode == SEPARATOR) {
           if (value.equals(savedValue) || value == ""){
               /* discard repeated or new null separator */
           } else if (savedValue == "") {
               savedValue = value;  // discard old null separator
           } else {
               flush();savedOpcode = opcode; savedValue = value;
           }
        } else if (opcode == SEPARATOR || 
               opcode == GOTO || opcode == RVALUE || opcode == PUSH) {
               flush();savedOpcode = opcode; savedValue = value;
        } else if (savedOpcode == "") {
               output(opcode, value);
        } else if (opcode == LABEL && savedOpcode == GOTO &&
               value.equals(savedValue)){
               clear(); output(LABEL, value);
        } else if (opcode == POP &&
               (savedOpcode == RVALUE || savedOpcode == PUSH)) {
               clear();
        } else if (opcode.equals("+")&& savedOpcode == PUSH && 
                   savedValue.equals("1")) {
               clear(); output(INC,"");
        } else if (opcode.equals("-") && savedOpcode == PUSH && 
                   savedValue.equals("1")) {
               clear(); output(DEC,"");
        } else if (opcode.equals("*") && savedOpcode == PUSH && 
                   savedValue.equals("2")) {
               clear(); output(COPY,""); output("+","");
        } else {
               flush(); output(opcode, value);
        }
    }

    void output(String opcode, String value)
    {
        if (Emitter != null) Emitter.output(opcode, value);
        else System.out.println(opcode + " " + value);
    }
    void flush()
    {
         if (savedOpcode != "") output(savedOpcode, savedValue);
         clear();
     }

    void clear()
    {
         savedOpcode = ""; savedValue = "";
    }

    public static void main(String[] args)
    {
        Optimiser O = new Optimiser(true);
        O.peepholeOptimiser(GOTO, "17");
        O.peepholeOptimiser(LABEL, "17");
        O.peepholeOptimiser(PUSH, "2");
        O.peepholeOptimiser("*", "");
        O.peepholeOptimiser(PUSH, "1");
        O.peepholeOptimiser("+", "");
        O.peepholeOptimiser(SEPARATOR, "comment");
        O.peepholeOptimiser(SEPARATOR, "comment");
        O.peepholeOptimiser(RVALUE, "x");
        O.peepholeOptimiser(POP, "");
        O.peepholeOptimiser(PUSH, "17");
        O.peepholeOptimiser(POP, "");
        O.peepholeOptimiser(PUSH, "1");
        O.peepholeOptimiser("-", "");
    }
}
