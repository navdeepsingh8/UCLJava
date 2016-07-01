//Solves argmax(n) E[Yn]
//where Yn = 0 if you lose points on a turn of n throws
//		   = X1 + ... + Xn if every throw scores points

class PigsTurn {
	
	private double EofX ; //expected value of the score attained from a throw
	private double p ; //probability of pigging out on a throw
	
	public PigsTurn(double EofX, double p, int bound) {
		this.EofX = EofX ;
		this.p = p ;
		findN(bound) ;
	}
	
	private void findN(int bound) {
		int n = 0 ;
		do {
			n++ ;
			double EofYn = n*EofX*(1-geometricSum(n)) ;
			System.out.println(EofYn) ;
		} while (n < bound) ;
	}
	
	private double geometricSum(int n) {
		double sum = 0 ;
		for (int j = 0; j < n; j++) {
			Integer intJ = new Integer(j) ;
			sum += p*Math.pow((1-p),intJ.doubleValue()) ;
		}
		return sum ;
	}
	
	public static void main(String[] args) {
		PigsTurn p = new PigsTurn(5.0,0.2, 10) ;
	}
}