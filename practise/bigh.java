//N.Daheley January 2001

class bigh
{
	public void writeLn(int line)
	{
		System.out.print("*") ;
		for (int i = 0; i < 4; i++)
		{
			if (line == 3)
			{
				System.out.print("*") ;
			}
			else
			{
				System.out.print(" ") ;
			}
		}
		System.out.print("*") ;
	}
	
	public static void main(String[] args)
	{
		bigh myProg = new bigh() ;
		for (int n = 1; n < 6; n++)
		{
			for (int m = 0; m < 6; m++)
			{
				myProg.writeLn(n) ;
				System.out.print(" ") ;
			}
			System.out.print("\n") ;
		}
	}
}