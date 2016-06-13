//Class representing a queue of integers
//Navdeep Daheley, January 2001

class Queue
{
	private int[] elements ;
	private int position = 0 ;
	
	public Queue(int numberOfElements)
	{
		elements = new int[numberOfElements] ;
	}
	
	public void add(int value)
	{
		elements[position] = value ;
		position++ ;
	}
	
	public int remove()
	{
		int outValue = elements[0] ;
		for (int i = 0 ; i < (elements.length - 1); i++)
		{
			elements[i] = elements[i + 1] ;
		}
		position-- ;
		return outValue ;
	}
	
	public boolean isFull()
	{
		if (position == (elements.length - 1))	{
			return true ;
		}
		else	{
			return false ;
		}
	}
	
	public boolean isEmpty()
	{
		if (position == 0)	{
			return true ;
		}
		else	{
			return false ;
		}
	}
	
	public static void main(String[] args)
	{
		Queue exampleQueue = new Queue(5) ;
		exampleQueue.add(1) ;
		exampleQueue.add(2) ;
		System.out.println(exampleQueue.remove()) ;
		System.out.println(exampleQueue.remove()) ;
	}
}