import junit.framework.* ;

public class LinkedListImplTest extends TestCase
{
	protected LinkedList empty ;
	protected LinkedList full ;
	protected LinkedList append ;
	protected Integer[] fullValues ;
	
	
	protected void setUp()
	{
		empty = new LinkedListImpl() ;
		full = new LinkedListImpl() ;
		append = new LinkedListImpl() ;
		for (int i = 1; i < 4; i++)
		{
			full.addHead(new Integer(i)) ;
		}
		append.addHead(new Integer(5)) ;
		//fullValues = new Integer[2] ;
		
	}
	
	public void testIsEmpty()
	{
		assertTrue(empty.isEmpty()) ;
		assertTrue(!(full.isEmpty())) ;
	}
	
	public void testGetHead()
	{
		assertEquals(full.getHead(), new Integer(3)) ;
		assertNull(empty.getHead()) ;
	}
	
	public void testGetTail()
	{
		int counter = 0 ;
		LinkedList fullTail = full.getTail() ;
		LinkedListIterator fullIterator = fullTail.iterator() ;
		Integer[] tailValues = { new Integer(2), new Integer(1) } ;
		while (fullIterator.hasNext())
		{
			assertEquals(fullIterator.next(), tailValues[counter]) ;
			counter++ ;
		}
		assertNull(empty.getTail()) ;
	}
	
	public void testAddTail()
	{
		int counter = 0 ;
		full.addTail(new Integer(4)) ;
		LinkedListIterator fullIterator = full.iterator() ;
		Integer[] fullValues = { new Integer(3), new Integer(2),
								 new Integer(1), new Integer(4) } ;
		while (fullIterator.hasNext())
		{
			assertEquals(fullIterator.next(), fullValues[counter]) ;
			counter++ ;
		}
		
		empty.addTail(new Integer(4)) ;
		assertEquals(empty.getHead(), new Integer(4)) ;
	}
	
	public void testAppend()
	{
		full = full.append(append) ;
		int counter = 0 ;
		LinkedListIterator fullIterator = full.iterator() ;
		Integer[] appendValues = { new Integer(3), new Integer(2),
								   new Integer(1), new Integer(5) } ;
		while (fullIterator.hasNext())
		{
			//assertEquals(appendValues[counter], fullIterator.next()) ;
			counter++ ;
		}
		assertEquals(counter, 4) ;
		
	/*	empty.append(append) ;
		counter = 0 ;
		LinkedListIterator emptyIterator = empty.iterator() ;
		Integer[] emptyValues = { new Integer(15), new Integer(10), new Integer(5) } ;
		while (counter < 3)
		{
			assertEquals(emptyIterator.next(), emptyValues[counter]) ;
			counter++ ;
		}	*/
	}
	
	public LinkedListImplTest(String name)
	{
		super(name) ;
	}
	
}