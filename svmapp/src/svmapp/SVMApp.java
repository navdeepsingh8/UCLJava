
package svmapp ;

import libsvm.svm_parameter ;

/**
 * This is the top level class in the package containing the main() method which
 * is executed at runtime. It contains an instance of each of the other objects
 * of the package, and an svm_parameter object, as well as `get' methods which
 * return these objects
 *
 * @author Navdeep Singh Daheley
*/
public class SVMApp {
	
	private gen_parameter genParam;
	private svm_parameter svmParam ;
	private GUI view ;
	private IO io ;
	private Chart chart ;
	private Generator gen ;		
	private SVMTrain train ;
	
	public SVMApp() {
 		genParam = new gen_parameter() ;
 		svmParam = new svm_parameter() ;
 		io = new IO(this,genParam) ;
 		gen = new Generator(this,genParam) ;
 		train = new SVMTrain(this,svmParam) ;
 		chart = new Chart(this) ;
 		view = new GUI(this) ;
 	}
 	
 	public static void main(String[] args){
		SVMApp app = new SVMApp() ;
 	}
 	
 	public IO getIO() {
 		return io ;
 	}
	
	public Chart getChart() {
		return chart ;
	}
	
	public Generator getGen() {
		return gen ;
	}
	
	public SVMTrain getTrain() {
		return train ;
	}
	
	public GUI getView() {
		return view ;
	}
	
	public gen_parameter getGenParam() {
		return genParam ;
	}
	
	public svm_parameter getSVMParam() {
		return svmParam ;
	}
}