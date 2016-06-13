
package svmapp ;

import libsvm.* ;
import java.io.* ;
import java.lang.* ;
import java.util.* ;
import java.text.NumberFormat ;

/**
 * This class handles all input/output.
 * 
 * @author Navdeep Singh Daheley
 */
public class IO {
	
	private SVMApp app ;
	private gen_parameter genParam ;
	private NumberFormat nf ;
	
	/** maximum dimensionality of input vectors
	 * read in a file */
	private int max_index ;
	/** number of classes of data read in a file */
	private int nr_class ;
	/** temporary input vector array */
	private double[][] data ;
	/** temporary labels array */
	private int[] labels ;
	/** array of class labels */
	private int[] label ;

	public IO(SVMApp app,gen_parameter genParam) {
		this.app = app ;
		this.genParam = genParam ;
		nf = NumberFormat.getInstance() ;
		max_index = 0 ;
	}

	/**
	 * Writes training set to file in LIBSVM readable format
	 * @return String result of method
	 * @exception Exception error in saving file
	 */
 	public String tsWrite() throws Exception {
 		if (!app.getGen().getGenerated())
 			return "Generate some data first." ;
 		else {
 			File file = app.getView().getFileToSave() ;
    		if (file == null) return "Not saved." ;
    		
 			int[] labels = app.getGen().getLabels() ;
	 		double[][] ts = app.getGen().getTS() ;
	 		DataOutputStream fp = new DataOutputStream(new FileOutputStream(file.getPath())) ;
	 		for (int i = 0; i < genParam.nr_class; i++) {
	 			for (int j = 0; j < genParam.size; j++) {
	 				if ((genParam.loaded && (labels[j]==label[i]))||(!genParam.loaded && (labels[j]==i))) {
	 						fp.writeBytes(labels[j]+"") ;
			 				for (int d = 0; d < genParam.dimension; d++)
			 					fp.writeBytes(" "+(d+1)+":"+ts[j][d]) ;
			 				fp.writeBytes("\n") ;
			 			}
	 			}
	 		}
	 	return ("Saved to "+file.getName()+".") ;
	 	}
	 }
	 
	
	/**
	 * Loads a data set from a file formatted in the readable format
	 * into class arrays
	 * @return String result of method
	 * @exception Exception error in loading from file
	 */
	public String load() throws Exception {
		File file = app.getView().getFileToLoad() ;
    	if (file == null) 
    		return "Not loaded." ;
    		
		BufferedReader fp = new BufferedReader(new FileReader(file.getPath()));
		Vector vy = new Vector();
		Vector vx = new Vector();
		max_index = 0;
		
		while(true) {
			String line = fp.readLine();
			if(line == null) break;			
			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:,");			
			vy.addElement(st.nextToken());						
			int m = st.countTokens()/2 ;
			double[] x = new double[m];
			
			for(int j=0;j<m;j++) {
				st.nextToken() ; /* skips the indices */
				x[j] = Double.valueOf(st.nextToken()).doubleValue();
			}
			
			if(m>max_index) max_index = m ;			
			vx.addElement(x);
		}
		fp.close();
		
		/* assigns labels and input vectors */
		labels = new int[vy.size()] ;
		data = new double[vx.size()][max_index] ;
		for(int i=0; i<vx.size(); i++) {
			data[i] = (double[])vx.elementAt(i);
			labels[i] = Integer.parseInt((String)vy.elementAt(i));
		}
		
		/* calculates number of classes from labels */
		nr_class = 0;
		int max_nr_class = 16;
		label = new int[max_nr_class];	
		int[] count = new int[max_nr_class];
		int[] index = new int[labels.length];
		
		for(int i=0;i<labels.length;i++) { 
			int this_label = (int)labels[i];
			int j;
			for(j=0;j<nr_class;j++)
				if(this_label == label[j]) {
					++count[j];
					break;
				}
			index[i] = j;
			if(j == nr_class) {
				if(nr_class == max_nr_class) {
					max_nr_class *= 2;
					int[] new_data = new int[max_nr_class];
					System.arraycopy(label,0,new_data,0,label.length);
					label = new_data;
					
					new_data = new int[max_nr_class];
					System.arraycopy(count,0,new_data,0,count.length);
					count = new_data;
				}
				label[nr_class] = this_label;
				count[nr_class] = 1;
				++nr_class;
			}
		}
		return file.getName() ;			
	}
	
	
	/**
	 * Loads a validation set from file.
	 * @return String result of method
	 * @exception Exception error in loading from file
	 */
	public String vsLoad() throws Exception {
		if(!app.getTrain().getTrained())
 			return "Train an SVM first." ;
 			
		/* loads data from file into class arrays */
		String filename = load() ;
		/* checks dimension/classes of loaded validation data */
		if (max_index != genParam.dimension || nr_class != genParam.nr_class)
			return "Not Loaded. Validation data must have same dimension/classes as training data." ;
			
		app.getGen().setVsLabels(labels) ;
		app.getGen().setVS(data) ;
		app.getGen().setValGenerated(true) ;
		
		return ("Loaded "+filename+". ") ;
	}
	
	/**
	 * Loads training set from a file.
	 * @return String result of method
	 * @exception Exception error in loading file
	 */
	public String tsLoad() throws Exception {
		
		/* loads data from file and sets up training set and label arrays */
		String filename = load() ;
		app.getGen().setLabels(labels) ;
		app.getGen().setTS(data) ;
				
		/* sets general data parameters */
		genParam.size = data.length ;
		genParam.nr_class = nr_class ;
		genParam.dimension = max_index ;
		genParam.loaded = true ;
		genParam.filename = filename ;
		app.getChart().killRanges() ;
		
		app.getGen().setGenerated(true) ;
		return ("Loaded "+filename+": "+genParam.size+"eg/"+genParam.dimension+"dim/"+genParam.nr_class+"class.") ;
	}
	
	/** 
	 * Returns output string from training
	 * @param model trained SVM
	 * @return String result of method
	 */
	public String svmOutput(svm_model model) {
		
		StringBuffer output = new StringBuffer() ;
		svm_parameter outputParam = model.param ;
		
		if (app.getGenParam().filename != null) output.append("Data source: "+app.getGenParam().filename+"\n") ;
		output.append("SVM Type: "+svm.svm_type_table[outputParam.svm_type]+"\n");
		output.append("Kernel Type: "+svm.kernel_type_table[outputParam.kernel_type]+"\n");
		
		if(outputParam.kernel_type == svm_parameter.POLY) output.append("Kernel Degree: "+outputParam.degree+"\n");

		if(outputParam.kernel_type == svm_parameter.POLY ||
		   outputParam.kernel_type == svm_parameter.RBF ||
		   outputParam.kernel_type == svm_parameter.SIGMOID)
			output.append("Kernel Gamma: "+outputParam.gamma+"\n");

		if(outputParam.kernel_type == svm_parameter.POLY ||
		   outputParam.kernel_type == svm_parameter.SIGMOID)
			output.append("Kernel Coefficient: "+outputParam.coef0+"\n");

		int nr_class = model.nr_class;
		int l = model.l;
		output.append("Number of Classes: "+nr_class+"\n");
		if (app.getGen().getWeighted()) {
			nf.setMaximumFractionDigits(2) ;
			output.append("Classes weighted by: ") ;
			for(int c=0;c<model.nr_class;c++) output.append(nf.format(outputParam.weight[c])+",") ;
			output.append("\n") ;
		}
		output.append("SVs per class: ");
		for(int c=0;c<model.nr_class;c++) output.append(model.nSV[c]+", ") ;
		output.append("\n") ;
		output.append("Total number of Support Vectors: "+l+"\n");
		app.getGen().vsGen(true) ;
		output.append(app.getTrain().validate()) ;
		
		return output.toString() ;
	}
	
	
	/**
	 * Writes a trained svm_model to file.
	 * @see libsvm.svm#svm_save_model
	 * @return String result of method
	 */
	public String svmWrite() throws Exception {
		if (app.getTrain().getTrained()) {
	 		File file = app.getView().getFileToSave() ;
        	if (file == null) {
        		return "Not saved." ;
        	}
	 		svm.svm_save_model(file.getPath(),app.getTrain().getModel());
	 		return ("Saved to "+file.getPath()+".") ;
	 	} else return "Train an SVM first." ;
	}
 }