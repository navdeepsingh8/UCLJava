//Navdeep Daheley, January 2001

class matrix
{
	public int[][] matrixAdd(int[][] matrixAa, int[][] matrixBa)
	{
		int aColumns = matrixAa[0].length ;
		int aRows = matrixAa.length ;
		int[][] matrixSum = new int[aRows][aColumns] ;
		for (int column = 0; column < aColumns; column++)
		{
			for (int row = 0; row < aRows; row++)
			{
				matrixSum[row][column] = matrixAa[row][column] + matrixBa[row][column] ;
			}
		}
		return matrixSum ;
	}
	
	public int[][] matrixSubtract(int[][] matrixAs, int [][] matrixBs)
	{
		int aColumns = matrixAs[0].length ;
		int aRows = matrixAs.length ;
		int[][] matrixSubtracted = new int[aRows][aColumns] ;
		for (int column = 0; column < aColumns; column++)
		{
			for (int row = 0; row < aRows; row++)
			{
				matrixSubtracted[row][column] = matrixAs[row][column] - matrixBs[row][column] ;
			}
		}
		return matrixSubtracted ;
	}
	
	public void matrixShow(int[][] matrix)
	{
		int columns = matrix[0].length ;
		int rows = matrix.length ;
		System.out.print("\n") ;
		for (int row = 0; row < rows; row++)
		{
			for (int column = 0; column < columns; column++)
			{
				System.out.print(" " + matrix[row][column] + '\t') ;
			}
			System.out.print("\n") ;
		}
		System.out.print("\n") ;
	}
	
	public static void main(String[] args)
	{
		matrix program = new matrix() ;
				
		System.out.println("This is matrix A:") ;
		int[][] matrixA = { {1, 2}, {3, 4} } ;
		program.matrixShow(matrixA) ;
		
		System.out.println("This is matrix B:") ;
		int[][] matrixB = { {5, 6}, {7, 8} } ;
		program.matrixShow(matrixB) ;
		
		System.out.println("This is the sum of matrices A and B:") ;
		int[][] matrixSum = program.matrixAdd(matrixA, matrixB) ;
		program.matrixShow(matrixSum) ;
		
		System.out.println("This is the subtraction of matrix B from A:") ;
		int[][] matrixSubtracted = program.matrixSubtract(matrixA, matrixB) ;
		program.matrixShow(matrixSubtracted) ;
	}
}
