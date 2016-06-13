//representation of a training vector's attribute as used in a sparse matrix
package libsvm;
public class svm_node implements java.io.Serializable
{
	public int index;	//denotes the component of the vector
	public double value;
}
