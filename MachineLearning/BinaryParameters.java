 
 public class BinaryParameters {
 	
 	
 	private FileInput in ;
 	private int[][] components ;
 	private double[] parameter ;
 	
 	
 	public BinaryParameters(String filename, int dim, int samples) {
 		in = new FileInput(filename) ;
 		components = new int[samples][dim] ;
 		parameter = new double[dim] ;
 		readData() ;
 	}
 	
 	
 	private void readData() {
 		
 		for (int s = 0; s < components.length; s++) {
 			in.readCharacter() ;
 			for (int d = 0; d < components[s].length; d++) {
 				char[] nextChar = {in.readCharacter()} ;
 				components[s][d] = Integer.parseInt(new String(nextChar)) ;
 				in.readCharacter() ;
 			}
 		}
 	}
 	
 	
 	public void ml() {
 		
  		for (int d = 0; d < parameter.length; d++) {
 			double p = 0 ;
 			for (int s = 0; s < components.length; s++) {
 				p += components[s][d] ;
 			}
 			parameter[d] = p/components.length ;
 		}
 		printP() ;
 	}
 	
 	
 	public void map() {
 		double alpha = 3 ;
 		double beta = 3 ;
 		for (int d = 0; d < parameter.length; d++) {
 			double p = 0 ;
 			for (int s = 0; s < components.length; s++) {
 				p += components[s][d] ;
 			}
 			parameter[d] = (p+alpha-1)/(components.length+beta+alpha-2) ;
 		}
 		printP() ;				
 	}
 	
 	
 	public void printP() {
 		
 		for (int d = 0; d < parameter.length; d++) {
 			System.out.println("P["+(d+1)+"] = "+parameter[d]) ;
 		}
 	}
 	
 	
 	public static void main(String[] args) {
 		
 		BinaryParameters question5 = new BinaryParameters("binarydigits.txt", 64, 100) ;
 		question5.ml() ;
 		question5.map() ;
 	}
 	
 }