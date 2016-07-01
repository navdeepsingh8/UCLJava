/**
 * Title:       SingleLayerPerceptron.java
 * Description: Represents a Single Layer Perceptron Neural Net
 * Author:		Navdeep Daheley, MACS4
 */
 
 import java.lang.Math ;
 import java.util.ArrayList ;
 import java.awt.Paint;
 import java.awt.Color;
 import org.jfree.data.XYSeries;
 import org.jfree.data.XYSeriesCollection;
 import org.jfree.chart.JFreeChart;
 import org.jfree.chart.ChartFactory;
 import org.jfree.chart.ChartFrame ;
 import org.jfree.chart.plot.XYPlot ;
 import org.jfree.chart.plot.PlotOrientation ;
 import org.jfree.chart.renderer.XYItemRenderer;
 import org.jfree.chart.renderer.XYDotRenderer; 
 import org.jfree.chart.renderer.StandardXYItemRenderer;
 import org.jfree.chart.axis.ValueAxis;
 import org.jfree.chart.axis.NumberAxis;
 
 
 class SingleLayerPerceptron {
 	
 	private double[] weights ;
 	private double[][] trainingSet ;
 	private int[] trainingSetLabels ;
 	private XYSeriesCollection trainingSetData ;
 	private double[] interval1 ;
	private double[] interval2 ;
 	
 	
 	public static class VectorOps {
 		
 		public static double innerProduct(double[] v, double[] u) {
 			double sum = 0 ;
 			for (int count = 0; count < v.length; count++) {
 				sum = sum + v[count]*u[count] ;
 			}
 			return sum ;
 		}
 	
 		public static double[] scalarProduct(double[] v, double s) {
 			double[] u = new double[v.length] ;
 			for (int count = 0; count < u.length; count++) {
 				u[count] = v[count]*s ;
 			}
 			return u ;
 		}
 		
 		public static double[] sum(double[] v, double[] u) {
 			double[] sum = new double[v.length] ;
 			for (int count = 0; count < sum.length; count++) {
 				sum[count] = v[count] + u[count] ;
 			}
 			return sum ;
 		}
 	}
 	
 	
 	public SingleLayerPerceptron(int sizeOfTrainingSet, double a0, double a1) {
 		weights = new double[3] ;
 		for (int count = 0; count < weights.length; count++) {
 			weights[count] = 10*Math.random();
 		}
 		System.out.println("Intial weights are "+weights[0]+","+weights[1]+","+weights[2]) ;
 		trainingSet = new double[sizeOfTrainingSet][] ;
 		trainingSetLabels = new int[sizeOfTrainingSet] ;
 		trainingSetData = new XYSeriesCollection() ;
 		generateTrainingSet(a0, a1) ;
 	}
 	
 	
 	/*Generates training set and plots onto a graph.
 	 */
  	private void generateTrainingSet(double a0, double a1) {
 		
 		interval1 = new double[2] ;
 		interval1[0] = Math.min((-3*a0)/a1, (3*a0)/a1) ;
 		interval1[1] = Math.max((-3*a0)/a1, (3*a0)/a1) ;
 		 		
 		interval2 = new double[2] ;
 		interval2[0] = Math.min(-3*a0, 3*a0) ;
 		interval2[1] = Math.max(-3*a0, 3*a0) ;
 		 			
  		XYSeries classOne = new XYSeries("Class One Examples") ;
  		XYSeries classTwo = new XYSeries("Class Two Examples") ;
  		for (int count = 0; count < trainingSet.length; count++) {
 			double[] example = new double[3] ;
			example[0] = interval1[0] + (interval1[1]-interval1[0])*Math.random() ;
			example[1] = interval2[0] + (interval2[1]-interval2[0])*Math.random() ;	
			example[2] = 1d ; /*3rd component set to 1 so training set examples
							   *can be used directly with Perceptron algorithm
							   */
			trainingSet[count] = example ;
			if (example[1] <= a1*example[0] + a0) {
				trainingSetLabels[count] = 1 ;
				classOne.add(example[0],example[1]) ;
			} else {
				trainingSetLabels[count] = -1 ;
				classTwo.add(example[0],example[1]) ;
			}
 		}
 		
 		trainingSetData.addSeries(classOne) ;
 		trainingSetData.addSeries(classTwo) ;
 		JFreeChart chart = ChartFactory.createScatterPlot("Training Set Data", "x1", "x2", trainingSetData,
 							PlotOrientation.VERTICAL,true,false, false);
 		ChartFrame frame = new ChartFrame("Training Set", chart);
 		frame.pack() ;
 		frame.setVisible(true) ;
 	}
 	
 	
 	public int discriminant(int example) {
 		double output = VectorOps.innerProduct(weights,trainingSet[example]) ;
 		if (output >= 0) {
 			return 1 ; } else { return -1 ;	}
 	}
 	
 	
 	public int[] misclassified() {
 		ArrayList mc = new ArrayList() ;
 		for (int count = 0; count < trainingSet.length; count++) {
 			if (discriminant(count)*trainingSetLabels[count] < 0) {
 				mc.add(new Integer(count)) ;
 			}
 		}
 		System.out.println(mc.size()+" misclassified!") ;
 		int[] misclassified = new int[mc.size()] ;
 		for (int count = 0; count < misclassified.length; count++) {
 		Integer mcValue = (Integer)mc.get(count) ;
 		misclassified[count] = mcValue.intValue() ;
 		}
 		return misclassified ;
 	}
 	
 	
 	public void batch(double trainingRate, double batchThreshold) {
 		int t = 0 ;
 		double[] productOverMC = {0,0,0} ; 
		double criterion ;
		int[] misclassified ;
		XYSeries criterionSeries = new XYSeries("Criterion Function") ;
		XYSeries misclassificationSeries = new XYSeries("Misclassification") ;
 		do {
 			t++ ;
 			double[] scalarOverMC = {0,0,0} ;
 			criterion = 0 ;
 			System.out.print("\n"+t+": ") ;
 			misclassified = misclassified() ; 			
 			for (int count = 0; count < misclassified.length; count++) {
 				scalarOverMC = VectorOps.sum(
 					scalarOverMC,VectorOps.scalarProduct(
 						trainingSet[misclassified[count]],(double)trainingSetLabels[misclassified[count]]
 					)
 				) ;
				criterion += trainingSetLabels[misclassified[count]]*
							VectorOps.innerProduct(weights,trainingSet[misclassified[count]]) ;
 			}
			criterionSeries.add((double)t,-1*criterion) ;
			misclassificationSeries.add((double)t,(double)misclassified.length/(double)trainingSet.length) ;
 			productOverMC = VectorOps.scalarProduct(scalarOverMC,(double)trainingRate) ;
 			weights = VectorOps.sum(weights,productOverMC) ;
 		} while (Math.sqrt(VectorOps.innerProduct(productOverMC,productOverMC)) > batchThreshold /*misclassified.length > 0*/) ;
 		
 		System.out.println("Final weights are: "+weights[0]+","+weights[1]+","+weights[2]) ;
 		XYChart("Criterion Function against t", "t", "Criterion Function", criterionSeries) ;
		XYChart("Misclassifications against t", "t", "Misclassifications", misclassificationSeries) ;
		
 	}
 	
 	
 	public void online(double trainingRate) {
 		int t = 0 ; int T = 0 ;
 		int misclassified = 1;
 		double criterion ;
 		XYSeries misclassificationSeries = new XYSeries("Misclassification") ;
 		XYSeries criterionSeries = new XYSeries("Criterion Function") ;
 		do {
 			T++ ;
 			misclassified = 0 ;
 			criterion = 0 ;
 			for (int count = 0; count < trainingSet.length; count++) {
 				double[] scalarMC = {0,0,0} ;
 				if (discriminant(count)*trainingSetLabels[count] < 0) {
 					t++ ;
 					misclassified++ ;
 					scalarMC = VectorOps.scalarProduct(
 						VectorOps.scalarProduct(
 							trainingSet[count],trainingSetLabels[count])
 							,trainingRate) ;
 					criterion += trainingSetLabels[count]*
							VectorOps.innerProduct(weights,trainingSet[count]) ;
					weights = VectorOps.sum(weights,scalarMC) ;
 				}
 			}
 			System.out.println(T+" : "+misclassified+" misclassified!") ;
 			misclassificationSeries.add((double)T,(double)misclassified/(double)trainingSet.length) ;
 			criterionSeries.add((double)T,-1*criterion) ;
 		} while (misclassified > 0) ;
 		
 		XYChart("Misclassification against t", "t", "Misclassification", misclassificationSeries) ;
 		XYChart("Criterion Function against t", "t", "Criterion Function", criterionSeries) ;
 		System.out.println("T="+T+" , Final weights are: "+weights[0]+","+weights[1]+","+weights[2]) ;
 	}
 	
 	
 	public void widrowHoff(double initialRate, double threshold) {
 		int t = 0 ; int T = 0 ;
 		double misclassified;
 		double criterion ;
 		double[] finalProduct = {0,0,0} ;
 		XYSeries misclassificationSeries = new XYSeries("Misclassification") ;
 		XYSeries criterionSeries = new XYSeries("Criterion Function") ;
 		do {
 			T++ ;
 			System.out.print("\nT="+T+": ") ;
 			misclassified = (double)misclassified().length ;
 			misclassificationSeries.add((double)T,misclassified/(double)trainingSet.length) ;
 			criterion = 0 ;
 			double[] product = {0,0,0} ;
 			double[] products = {0,0,0} ;
 			for (int count = 0; count < trainingSet.length; count++) {
 					t++ ;
 					criterion += Math.pow((VectorOps.innerProduct(weights,trainingSet[count])
 											-(double)trainingSetLabels[count]),2) ;
 					product = VectorOps.scalarProduct(trainingSet[count],(-1*VectorOps.innerProduct(
 								weights,trainingSet[count])+(double)trainingSetLabels[count])) ;
 					products = VectorOps.sum(products,product) ;
 					weights = VectorOps.sum(weights,VectorOps.scalarProduct(
 								product,(initialRate/(double)t))) ;
 			}
 			finalProduct = VectorOps.scalarProduct(products, (initialRate/(double)t)) ;
 			criterionSeries.add((double)T,criterion/2) ;
 		} while (Math.sqrt(VectorOps.innerProduct(finalProduct,finalProduct)) > threshold) ;
 		
 		XYChart("Misclassification against t", "t", "Misclassification", misclassificationSeries) ;
 		XYChart("Criterion Function against t", "t", "Criterion Function", criterionSeries) ;
 		System.out.println("T="+T+" , Final weights are: "+weights[0]+","+weights[1]+","+weights[2]) ;
 	}
 	
 	
	public void XYChart(String title, String x, String y, XYSeries data) {
		JFreeChart chart = ChartFactory.createXYLineChart(title, x, y, new XYSeriesCollection(data), 
							PlotOrientation.VERTICAL, true, false, false) ;
		ChartFrame frame = new ChartFrame(title, chart) ;
		frame.pack() ;
		frame.setVisible(true) ;
	}
	
	
	public void drawBorder() {
 		XYSeries borderSeries = new XYSeries("Discriminant Border") ;
 		for (int count = (int)interval1[0]; count <= (int)interval1[1]; count++) {
 			borderSeries.add((double)count, -1*(weights[0]*count+weights[2])/weights[1])  ;
 		}
        XYItemRenderer setRenderer = new XYDotRenderer();
        ValueAxis domainAxis = new NumberAxis("x1");
        ValueAxis rangeAxis = new NumberAxis("x2");
        XYPlot setPlot = new XYPlot(trainingSetData, domainAxis, rangeAxis, setRenderer);
        
        XYItemRenderer borderRenderer = new StandardXYItemRenderer();
        setPlot.setSecondaryDataset(0, new XYSeriesCollection(borderSeries));
        setPlot.setSecondaryRenderer(0, borderRenderer);
 		
 		JFreeChart borderChart = new JFreeChart(setPlot);
 		ChartFrame frame = new ChartFrame("Border over Training Set", borderChart) ;
		frame.pack() ;
		frame.setVisible(true) ;
  	}
	
	
	public static void main(String[] args) {
		SingleLayerPerceptron Question1 = new SingleLayerPerceptron(1000, 2, 3) ;
		Question1.batch(0.001, 0.001) ;		
		//Question1.online(0.001) ;
		//Question1.widrowHoff(5, 0.001) ;
		//Question1.drawBorder() ;		
  	}
 
 }