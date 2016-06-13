package svmapp ;

import libsvm.* ;
import java.text.NumberFormat ;
import javax.swing.JProgressBar ;
import javax.swing.JTextArea ;

/**
 * This class is responsible for training, cross validation and test validation
 * of a LIBSVM based SVM.
 *
 * @author Navdeep Singh Daheley
 */ 
public class SVMTrain {
	
	/** identifies training of the SVM */
	public static final int TRAIN = 0 ;
	/** identifies cross validation of the SVM */
	public static final int X_VALIDATE = 1 ;
	
	private SVMApp app ;
	private svm_parameter svmParam ;
	private svm_model model ;
	private double[][] SVs ;
	private int[] SVLabels ;
	private NumberFormat nf ;
	
	/** identifies whether an SVM has been trained */
	private boolean trained = false ;

	
	public SVMTrain(SVMApp app,	svm_parameter svmParam) {
		this.app = app ;
		this.svmParam = svmParam ;
		nf = NumberFormat.getInstance() ;				
	}
	
	/* Access methods */
	
	public svm_model getModel() {
		return model ;
	}
	
	public boolean getTrained() {
		return trained ;
	}
	
	public void setTrained(boolean trained) {
		this.trained = trained ;
	}
	
	public double[][] getSVs() {
		return SVs ;
	}
	
	public int[] getSVLabels() {
		return SVLabels ;
	}
	
	
	/**
	 * Sets parameters stored in the svm_parameter.
	 * @param svm svm type
	 * @param kernel kernel type
	 * @param degree kernel degree parameter
	 * @param gamma kernel gamma parameter
	 * @param co kernel coefficient parameter
	 * @param c violation parameter C
	 * @param nu violation parameter nu
	 * @param e e-SVR parameter e
	 * @param n cross validation parameter
	 */
	public void setSVMParams(int svm,int kernel,double degree,double gamma,double co,
								double c,double nu,double e,int n) {
		svmParam.svm_type = svm ;
		svmParam.kernel_type = kernel ;
		svmParam.degree = degree;
		svmParam.gamma = gamma;	// 1/k
		svmParam.coef0 = co;
		svmParam.C = c;
		svmParam.nu = nu;
		svmParam.p = e;
		svmParam.nr_fold = n ;
	}
	
	
	/**
	 * Constructs an svm_problem from input vector and label arrays.
	 * @param data input vector array
	 * @param labels labels array
	 * @return svm_problem constructed svm_problem
	 */
	public static svm_problem prob(double[][] data, int[] labels) {
		svm_problem prob = new svm_problem() ;
		prob.l = data.length ;
		prob.x = new svm_node[prob.l][];
		prob.y = new double[prob.l] ;
		for (int i = 0; i < prob.l; i++) {
			svm_node[] x = new svm_node[data[0].length] ;
			for (int j = 0; j < data[0].length; j++) {
				x[j] = new svm_node() ;
				x[j].index = j ;
				x[j].value = data[i][j] ;
			}
			prob.x[i] = x ;
			prob.y[i] = new Integer(labels[i]).doubleValue() ;
		}
		return prob ;
	}
	
	
	/**
	 * Trains or cross validates an SVM.
	 * @param type identifies training or cross validation
	 * @exception Exception error in training
	 */
	public void train(int type) throws Exception {
		if (!app.getGen().getGenerated())
			throw new Exception("Generate or load some data first.") ;
		
		/* sets gamma to 1/dimension if zero */
		gen_parameter genParam = app.getGenParam() ;
		if(svmParam.gamma == 0) svmParam.gamma = 1.0/genParam.dimension;
		/* checks and sets weights if weighted training data generated */
		if(app.getGen().getWeighted()) {
			svmParam.nr_weight = genParam.weights.length ;
			svmParam.weight_label = app.getGen().getLabels() ;
			svmParam.weight = genParam.weights ;
		} else {
			svmParam.nr_weight = 0;
			svmParam.weight_label = new int[0] ;
			svmParam.weight = new double[0] ;
		}
		
		/* constructs svm_problem */
		svm_problem prob = prob(app.getGen().getTS(),app.getGen().getLabels()) ;
		
		/* checks svm_problem and svm_param for consistency */
		String error_msg = svm.svm_check_parameter(prob,svmParam);
		if(error_msg != null)	
			throw new Exception(error_msg);
				
		/* checks if cross validation selected */
		if (type == X_VALIDATE) {
			if(svmParam.nr_fold > 1) {
				if (svmParam.nr_fold > prob.l)
					throw new Exception("Cross validation must not be more than "+prob.l+"-fold.") ;
				else {
					crossValidation(prob) ;
					return ;
				}
			} else {
				throw new Exception("Cross validation must be at least 2-fold.") ;
			}
		}
					
		/* trains SVM */
		JProgressBar bar = app.getView().getProgressBar() ;
		bar.setIndeterminate(true) ;
		JTextArea outputArea = app.getView().getOutputArea() ;
		outputArea.setText("") ;
		model = svm.svm_train(prob,svmParam);
		
		/* adds SVs to Instance arrays for plotting */
		svm_node[][] sv = model.SV ;
		int max_index = 0;
		for (int s=0; s<sv.length;s++) if(sv[s].length>max_index) max_index = sv[s].length ;
		if (max_index <= 3) {
			SVs = new double[sv.length][max_index] ;
			SVLabels = new int[sv.length] ;
			int j = 0;
			for (int c=0;c<model.nr_class;c++) {
				for (int i=j;i<j+model.nSV[c];i++) {
					double[] data = new double[max_index] ;
					for (int k=0;k<max_index;k++) 
						data[k] = model.SV[i][k].value ;
					SVs[i] = data ;
					SVLabels[i] = model.label[c] ;
				}
				j += model.nSV[c] ;
			}
		}
		
		trained = true ;
		bar.setIndeterminate(false) ;
		outputArea.setText(app.getIO().svmOutput(model)) ;
		outputArea.setCaretPosition(outputArea.getDocument().getLength()) ;
	}
	
	/**
	 * Does cross validation.
	 * @param prob svm_problem representing training set
	 */
	public void crossValidation(svm_problem prob) {
		int i;
		int total_error = 0;
		//double total_error = 0;//used for SVR
		double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;
		int nr_fold = svmParam.nr_fold ;
		StringBuffer out = new StringBuffer() ;
		JProgressBar bar = app.getView().getProgressBar() ;
		bar.setIndeterminate(false) ;
		bar.setMinimum(0) ;
		bar.setMaximum(nr_fold) ;
		bar.setValue(0) ;
		JTextArea outputArea = app.getView().getOutputArea() ;
		if (nr_fold > 20) {
			outputArea.setText("Individual misclassification errors surpressed.") ;
		} else {
			outputArea.setText("") ;
		}

		/* random shuffle of data */
		for(i=0;i<prob.l;i++) {
			int j = i+(int)(Math.random()*(prob.l-i));
			svm_node[] tx;
			double ty;

			tx = prob.x[i];
			prob.x[i] = prob.x[j];
			prob.x[j] = tx;

			ty = prob.y[i];
			prob.y[i] = prob.y[j];
			prob.y[j] = ty;
		}

		for(i=0;i<nr_fold;i++) {
			
			/* begin and end denote set of examples used to test */
			int begin = i*prob.l/nr_fold;
			int end = (i+1)*prob.l/nr_fold;
			int j,k;
			svm_problem subprob = new svm_problem();
			bar.setValue(i) ;

			subprob.l = prob.l-(end-begin);
			subprob.x = new svm_node[subprob.l][];
			subprob.y = new double[subprob.l];

			k=0;
			for(j=0;j<begin;j++) {
				subprob.x[k] = prob.x[j];
				subprob.y[k] = prob.y[j];
				++k;
			} for(j=end;j<prob.l;j++) {
				subprob.x[k] = prob.x[j];
				subprob.y[k] = prob.y[j];
				++k;
			}

			/* for SVR
			if(param.svm_type == svm_parameter.EPSILON_SVR ||
			   param.svm_type == svm_parameter.NU_SVR) {
				svm_model submodel = svm.svm_train(subprob,param);
				double error = 0;
				for(j=begin;j<end;j++) {
					double v = svm.svm_predict(submodel,prob.x[j]);
					double y = prob.y[j];
					error += (v-y)*(v-y);
					sumv += v;
					sumy += y;
					sumvv += v*v;
					sumyy += y*y;
					sumvy += v*y;
				}
				System.out.print("Mean squared error = "+error/(end-begin)+"\n");
				total_error += error;			
			} else {*/
				/* constructs and trains submodel, testing on test subset */
				svm_model submodel = svm.svm_train(subprob,svmParam);
				int error = 0;
				for(j=begin;j<end;j++) {
					double v = svm.svm_predict(submodel,prob.x[j]);
					if(v != prob.y[j]) ++error;
				}
				nf.setMaximumFractionDigits(2) ;
				if (nr_fold <= 20) {
					outputArea.append("Fold "+(i+1)+": Misclassification Error = "+nf.format(100.0*error/(end-begin))+"% ("+error+"/"+(end-begin)+")\n");
					outputArea.setCaretPosition(outputArea.getDocument().getLength()) ;
				}
				total_error += error;
			//}
		}
				
		/* for SVR
		if(param.svm_type == svm_parameter.EPSILON_SVR || param.svm_type == svm_parameter.NU_SVR)
		{
			System.out.print("Cross Validation Mean squared error = "+total_error/prob.l+"\n");
			System.out.print("Cross Validation Squared correlation coefficient = "+
				((prob.l*sumvy-sumv*sumy)*(prob.l*sumvy-sumv*sumy))/
				((prob.l*sumvv-sumv*sumv)*(prob.l*sumyy-sumy*sumy))+"\n"
				);
		}
		else*/
		
		outputArea.append("\nCross Validation Generalisation Error Estimate = "+nf.format(100.0*total_error/prob.l)+"%");
		outputArea.setCaretPosition(outputArea.getDocument().getLength()) ;
		bar.setValue(0) ;
	}
	
	
	/**
	 * Peforms validation on test data.
	 * @return String result of method
	 */
	public String validate() {
		if (!app.getGen().getValGenerated())
			return "Generate some validation data first." ;
		
		/* constructs svm_problem from validation data */
		svm_problem prob = prob(app.getGen().getVS(),app.getGen().getVsLabels()) ;
		int error = 0;
		
		for(int i=0;i<prob.x.length;i++) {
			double v = svm.svm_predict(model,prob.x[i]);
			if(v != prob.y[i]) ++error;
		}
		
		return ("Misclassification Error = "+nf.format(100.0*error/prob.x.length)+"% ("+error+"/"+(prob.x.length)+")");
	}
}