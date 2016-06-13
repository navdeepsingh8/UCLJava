//Linked List implementation
//Navdeep Daheley, October 2001

public class LinkedListImpl implements LinkedList
{
	public class ListElement
	{
		private Object datum ;
		private ListElement nextElement ;
		
		public ListElement(Object o, ListElement element)
		{
			datum = o ;
			nextElement = element ;
		}
		
		public void setNextElement(ListElement element)
		{
			nextElement = element ;
		}
		
		public ListElement getNextElement()
		{
			return nextElement ;
		}
		
		public Object getDatum()
		{
			return datum ;
		}
	}
	
	public class LinkedListIteratorImpl implements LinkedListIterator
	{
		private ListElement current = head ;
		
		public boolean hasNext()
		{
			if (current == null)
			{
				return false ;
			} /* else  if (current.getNextElement() == null)
			{
				return false ;
			} */ else {
				return true ;
			}
		}
		
		public Object next()
		{
			if (current == null)
			{
				return null ;
			}
			
			Object datumOut = current.getDatum() ;
			current = current.getNextElement() ;
			return datumOut ;
		}
	}
	
	public ListElement head = null ;
	private ListElement tail = null ;
	private int numberOfElements = 0 ;
	
	public boolean isEmpty()
	{
		if (head == null)
		{
			return true ;
		} else {
			return false ;
		}
	}
	
	public void addHead(Object o)
	{
		head = new ListElement(o, head) ;
	}

	public Object getHead()
	{
		if (head == null)
		{
			return null ;
		}
		return head.getDatum() ;
	}
	
	public void addTail(Object o)
	{
		if (head == null)
		{ head = new ListElement(o, null) ;	} else {
			LinkedListIterator tailIterator = iterator() ;
			int counter = 0 ;
			while (tailIterator.hasNext())
			{ 
				counter++ ; 
				tailIterator.next() ;
			}
			
			ListElement[] oldList = new ListElement[counter+1] ;
			counter = 0 ;
			while ((head != null)) {
				oldList[counter] = head ;
				head = head.getNextElement() ;
				counter++ ;
			}
			
			LinkedList newList = new LinkedListImpl() ;
			newList.addHead(o) ;
			for (int n = counter-1; n >= 0; n--)
			{
				if (oldList[n] != null)
				{
					newList.addHead(oldList[n].getDatum()) ;
				}
			}
		}
	}
	
	public LinkedList getTail()
	{
		if (head == null)
		{ return null ; } else {
		Object headStore = getHead() ;
		this.head = this.head.getNextElement() ;
		LinkedList tailList = new LinkedListImpl() ;
		LinkedListIterator tailIterator = iterator() ;
		while (tailIterator.hasNext())
		{
			tailList.addTail(tailIterator.next()) ;
		}
		addHead(headStore) ;
		return tailList ;
		}
	}
	
	public LinkedList append(LinkedList l)
	{
		LinkedListIterator appendIterator = l.iterator() ;
    	while (appendIterator.hasNext())
    	{
        	this.addTail(appendIterator.next()) ;
    	}
    	return this ;
	}
	
	public LinkedListIterator iterator()
	{
		return new LinkedListIteratorImpl() ;
	}
	
	public static void main(String[] args) 
	{
		
	}

}		