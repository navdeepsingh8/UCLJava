//Navdeep Daheley, February 2001
//Java implementation class of a Insertion Sortable Array
class ISArray
{
	private int[] _array ;
	
	public ISArray(int[] array)
	{
		_array = array ;
	}
	
	public void display()
	{
		for (int i = 0; i < _array.length; i++)
		{
			System.out.print("" + _array[i] + " ") ;
		}
		System.out.print("\n") ;
	}
	
	public void sort()
	{
		int temp = 0 ;
		int i = 0 ;
		for (int j = 1; j < _array.length; j++)
		{
			temp = _array[j] ;
			i = j - 1 ;
			while (i > -1 && _array[i] > temp)
			{
				_array[i + 1] = _array[i] ;
				i-- ;
			}
			_array[i + 1] = temp ;
		}
	}
	
	public static void main(String[] args)
	{
		int[] testNumbers = new int[5] ;
		testNumbers[0] = 4;
		testNumbers[1] = 3;
		testNumbers[2] = 8;
		testNumbers[3] = 2;
		testNumbers[4] = 6;
		ISArray testArray = new ISArray(testNumbers) ;
		testArray.display() ;
		testArray.sort() ;
		testArray.display() ;
	}
}	