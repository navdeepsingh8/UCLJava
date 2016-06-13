
public class MemoryEl 
{
    public int lineNo;
    public String opCode;
    public String operand;
    public String stack;

    public MemoryEl(int lineNo, String opCode, String operand)
    {
        this.lineNo = lineNo;
        this.opCode = opCode;
        this.operand = operand; 
        this.stack= "";
    }
}

