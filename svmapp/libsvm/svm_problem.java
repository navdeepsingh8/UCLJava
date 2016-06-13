//svm_problem describes the problem
package libsvm;
public class svm_problem implements java.io.Serializable
{
	public int l;			//l is the number of training examples
	public double[] y;		//y contains the example labels/target values
	public svm_node[][] x;	//x a sparse matrix of training vectors
}