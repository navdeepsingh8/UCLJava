//Linked List interface copied from Exercises 1
//Navdeep Daheley, October 2001

public interface LinkedList
{
	public Object getHead() ;		// Return object at head of list or null if
									// list is empty.
	public LinkedList getTail() ;	// Return a copy of the tail of the list
									// (all elements except the first).
									
	public void addHead(Object o) ;	// Add object to head of list.
	public void addTail(Object o) ;	// Add object at end of list.
	public LinkedList append(LinkedList l) ;
									// Return a  new list consisting of
									// a copy of the current list with a copy
									// of the argument list l appended.
	public boolean isEmpty() ;		// Return true if no objects in the list.
	public LinkedListIterator iterator() ;
									// Return an iterator object for the list.
}