import java.util.Stack;


public class CountingStack extends Stack
{
    protected int counter;

    public CountingStack()
    {
        counter = 0;
    }

    public int count() {return counter;}

    public Object push(Object o)
    {
        super.push(o);
        counter++;
        return o;
    }

    public Object pop()
    {
        counter--;
        return super.pop();
    }

    public static void main(String args[])
    {
        CountingStack s = new CountingStack();
        System.out.println("Count = "+s.count());
    }
}

