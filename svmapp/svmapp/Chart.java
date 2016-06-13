package svmapp ;

import libsvm.* ;
import org.jfree.data.* ;
import org.jfree.chart.* ;
import org.jfree.chart.plot.* ;
import org.jfree.chart.renderer.* ;
import org.jfree.chart.axis.* ;
import org.jmathplot.gui.*;
import java.awt.Color ;

/**
 * Handles all the charting of the package, making use of the JFreeChart
 * and JMathPlot libraries.
 *
 * @author Navdeep Singh Daheley
 */
public class Chart {
	
	/** identifies XYSeries[] of training set */
	public static final int TRAINING_SET = 0 ;
	/** identifies XYSeries[] of SVs */
	public static final int SUPPORT_VECTORS = 1 ;
	/** identifies XYSeries[] of validation set */
	public static final int VALIDATION_SET = 2 ;
	/** identifies class colours for 3D Scatter plot */
	public static final Color[] classColours = 
		{Color.red, Color.blue, Color.green, Color.yellow, Color.orange,
			Color.magenta, Color.cyan, Color.pink, Color.gray } ;
	
	private SVMApp app ;
	
	/**	data required for ContourPlot */
	private ContourDataset contourData ;
	/** x Range of XYPlot of training set */
	private Range preferredX ;
	/** y Range of XYPlot of training set */
	private Range preferredY ;
	/**	3D plot ranges */
	private double[][] preferred3D ;
	
	public Chart(SVMApp app) {
		this.app = app ;
	}
	
	
	/**
	 * Kills the preferred x and y Ranges. Used when new data
	 * is generated or loaded.
	 */
	public void killRanges() {
		preferredX = null ;
		preferredY = null ;
		preferred3D = null ;
	}
	
	
	/**
	 * Calls a scatter plot drawing method.
	 * @param plot identifies data to plot
	 * @return outcome of the method
	 */
	public String XYChart(int plot) {
		int dim = app.getGenParam().dimension ;
		if (dim==2)
			return XYChart2D(plot) ;
		else if (dim==3)
			return XYChart3D(plot) ;
		else
			return "Can only plot 2 or 3 dimensional vectors." ;
	}
	
	/**
	 * Draws a 2D Scatter Plot using JFreeChart
	 * @param plot identifies data to plot
	 * @return outcome of the method
	 */
	private String XYChart2D(int plot) {
		String title = null ;
		XYSeriesCollection collection = new XYSeriesCollection() ;
		switch(plot) {
			case TRAINING_SET :
				if (!app.getGen().getGenerated()) {
					return "Load or generate some data first.";
				} else {
					collection = createCollection(app.getGen().getTS(),app.getGen().getLabels(),
											app.getGenParam().nr_class) ;
					title = "Training Set" ;
				}
				break ;
			case VALIDATION_SET:
				if (!app.getGen().getValGenerated()) {
				return "Generate some validation data first." ;
				} else {
					collection = createCollection(app.getGen().getVS(),app.getGen().getVsLabels(),
											app.getGenParam().nr_class) ;
					title = "Validation Set" ;
				}
				break;
			case SUPPORT_VECTORS:
				if (!app.getTrain().getTrained()) {
				return "Train some data first." ;
				} else {
					collection = createCollection(app.getTrain().getSVs(),app.getTrain().getSVLabels(),
											app.getTrain().getModel().nr_class) ;
					title = "Support Vectors" ;
				break ;
			}
		}
		
		JFreeChart chart = ChartFactory.createScatterPlot(title, "x_0", "x_1", collection,
 							PlotOrientation.VERTICAL,true,false,false);
 		
 		/* Saves preferred ranges if training set chart, or retrieves
 		 * saved ranges to use for validation or SV plots */
 		if (plot==TRAINING_SET) {
 			preferredX = chart.getXYPlot().getDomainAxis().getRange() ;
 			preferredY = chart.getXYPlot().getRangeAxis().getRange() ;
 		} if (plot==SUPPORT_VECTORS || plot==VALIDATION_SET) {
 			if (preferredX != null && preferredY != null) {
 			chart.getXYPlot().getDomainAxis().setRange(preferredX);
 			chart.getXYPlot().getRangeAxis().setRange(preferredY);
 			}
 		}
 		
 		ChartFrame frame = new ChartFrame(title, chart);
		frame.pack() ;
		frame.setVisible(true) ;
		return "2D Scatter plot drawn" ;
	}
	
	/**
	 * Creates an XYSeriesCollection of the data for plotting in XYSeriesCollection
	 * @param data data points
	 * @param labels data labels
	 * @param nr_class number of classes in the data
	 * @return XYSeriesCollection
	 */
	private XYSeriesCollection createCollection(double[][] data, int[] labels, int nr_class) {	
		XYSeries[] dataSet = new XYSeries[nr_class] ;
		for (int c=0;c< dataSet.length;c++)
			dataSet[c] = new XYSeries("Class"+(c+1)) ;
		
		for (int i=0;i<data.length;i++)
			dataSet[labels[i]].add(data[i][0],data[i][1]) ;
		
		XYSeriesCollection collection = new XYSeriesCollection() ;
		for (int c=0;c<dataSet.length;c++)
			collection.addSeries(dataSet[c]) ;
		
		return collection ;
	}
	
	/**
	 * Draws a 3D Scatter Plot using JMathPlot
	 * @param plot identifies data to plot
	 * @return outcome of the method
	 */
	private String XYChart3D(int plot) {
		String title = null ;
		double[][][] array = new double[0][0][0] ;
		switch(plot) {
			case TRAINING_SET :
				if (!app.getGen().getGenerated()) {
					return "Load or generate some data first.";
				} else {
					array = createArray(app.getGen().getTS(),app.getGen().getLabels(),
											app.getGenParam().nr_class) ;
					title = "Training Set" ;
				}
				preferred3D = get3DRange(app.getGen().getTS()) ;
				break ;
			case VALIDATION_SET:
				if (!app.getGen().getValGenerated()) {
					return "Generate some validation data first." ;
				} else {
					array = createArray(app.getGen().getVS(),app.getGen().getVsLabels(),
											app.getGenParam().nr_class) ;
					title = "Validation Set" ;
				}
				break;
			case SUPPORT_VECTORS:
				if (!app.getTrain().getTrained()) {
					return "Train some data first." ;
				} else {
					array = createArray(app.getTrain().getSVs(),app.getTrain().getSVLabels(),
											app.getTrain().getModel().nr_class) ;
					title = "Support Vectors" ;
				break ;
			}
		}
		
		// Build the 3D scatterplot of the datas in a Panel
		Plot3DPanel plot3d = new Plot3DPanel() ;
		for (int c=0;c<array.length;c++)
			plot3d.addPlot(array[c],String.valueOf(c+1),"SCATTER",classColours[c%classColours.length]) ;
		if (preferred3D != null) {
	 			plot3d.setFixedBounds(preferred3D[0],preferred3D[1]) ;
	 		}
	 	plot3d.setAxesLabels("x_0","x_1","x_2") ;
		
		// Display a Frame containing the plot panel
		new FrameView(title, plot3d);
		return "3D Scatter plot drawn." ;
	}
	
	
	/**
	 * Creates a 3D array of the data for plotting in JMathPlot
	 * @param data data points
	 * @param labels data labels
	 * @param nr_class number of classes in the data
	 * @return 3D array of data
	 */
	public static double[][][] createArray(double[][] data, int[] labels, int nr_class) {
		double[][][] array = new double[nr_class][][] ;
		for (int c=0;c < array.length;c++) {
			int N = 0 ;
			for (int n=0;n<data.length;n++) {
				if (labels[n] == c) N++ ;
			}
			
			double[][] aClass = new double[N][3] ;
			int i = 0 ;
			for (int n=0;n<data.length;n++) {
				if (labels[n] == c) {
					aClass[i][0] = data[n][0] ;
					aClass[i][1] = data[n][1] ;
					aClass[i][2] = data[n][2] ;
					i++ ;
				}
			}
			array[c] = aClass ;
		}
		return array ;
	}
	
	/**
	 * Calculates the minimum and maximum values of the data set
	 * in each dimension
	 * @param data data set
	 * @return array of minimum/maximum values
	 */
	public static double[][] get3DRange(double[][] data) {
		
		double[][] range = new double[2][data[0].length] ;
		for (int d=0;d<data[0].length;d++) {
			double min = data[0][d];
			double max = data[0][d];
			for (int i=0;i<data.length;i++) {
				if (data[i][d] < min) min = data[i][d] ;
				if (data[i][d] > max) max = data[i][d] ;
			}
			range[0][d] = min ;
			range[1][d] = max ;
		}
		return range ;	
	}
	
	
	/**
	 * Convenience method for constructing a ContourDataset from arrays.
	 * @param x x values
	 * @param y y values 
	 * @param z z values
	 */
	private void setContourDataset(String title,double[] x, double[] y, double[] z) {
		contourData = new DefaultContourDataset(title,
							DefaultContourDataset.formObjectArray(x),
							DefaultContourDataset.formObjectArray(y),
							DefaultContourDataset.formObjectArray(z)) ;
	}
	
	
	/**
	 * Draws a ContourPlot of decision regions using JFreeChart.
	 * @param title title of chart
	 * @param ptSize gap between drawn points
	 */
	 public void ContourChart(String title, double ptSize) {
		NumberAxis domainAxis = new NumberAxis() ;
		NumberAxis rangeAxis = new NumberAxis() ;
 		JFreeChart chart = new JFreeChart(title,
 			new ContourPlot(contourData,new NumberAxis("x_0"),new NumberAxis("x_1"),new ColorBar("Class")));
 		((ContourPlot)chart.getPlot()).setPtSizePct(ptSize) ;
 		if (preferredX != null & preferredY != null) {
 			((ContourPlot)chart.getPlot()).getDomainAxis().setRange(preferredX);
 			((ContourPlot)chart.getPlot()).getRangeAxis().setRange(preferredY);
 		}
 		ChartFrame frame = new ChartFrame(title, chart);
		frame.pack() ;
		frame.setVisible(true) ;
	}
	
	/**
	 * Draws decision regions; first generates them using a grid search
	 * of input space then calls ContourPlot().
	 * @return String result of method
	 */
	public String drawRegions() {
		if (!app.getTrain().getTrained())
			return "Train an SVM first." ;
		
		svm_model model = app.getTrain().getModel() ;
		svm_node[][] sv = model.SV ;
		int max_index = 0;
		
		for (int s=0; s<sv.length;s++) if(sv[s].length>max_index)
			max_index = sv[s].length ;
		
		if(max_index==2) {
			double[][] ts = app.getGen().getTS() ;
			double[] min = {0d,0d} ;
			double[] max = {0d,0d} ;
			
			if (preferredX == null && preferredY == null) {
				for (int t=0;t<app.getGenParam().size;t++) {
					if (ts[t][0] < min[0]) min[0] = ts[t][0];
					if (ts[t][0] > max[0]) max[0] = ts[t][0];
					if (ts[t][1] < min[1]) min[1] = ts[t][1];
					if (ts[t][1] > max[1]) max[1] = ts[t][1] ;	
				}
			} else {
				min[0] = preferredX.getLowerBound() ;
				max[0] = preferredX.getUpperBound() ;
				min[1] = preferredY.getLowerBound() ;
				max[1] = preferredY.getUpperBound() ;
			}
			
			/* grid search step size */
			int grid = 100 ;
			double step0 = (max[0]-min[0])/grid ;
			double step1 = (max[1]-min[1])/grid ;
			double x0 = min[0] ;
			
		 	double[] contour0 = new double[grid*grid];
		 	double[] contour1 = new double[grid*grid] ;
		 	double[] shade = new double[grid*grid] ;
		 	
		 	//XYSeries borderSeries = new XYSeries("Border") ;
		 	
			/* scans input space enclosed by support vectors to find decision regions */
			for(int a=0;a<grid;a++){
				double x1 = min[1] ;
				
				//double target = 0;
				//double lowestMargin = 10;
				for(int b=0;b<grid;b++) {
					svm_node[] point = new svm_node[2] ;
					point[0]=new svm_node();point[0].index = 0;point[0].value = x0;
					point[1]=new svm_node();point[1].index = 1;point[1].value = x1;
					
					/*double margin = svmapp.tests.ROC.margin(point,model) ;
					margin = Math.max(margin,-1*margin) ;
					if(margin<lowestMargin) {
						lowestMargin=margin ;
						target=x1;
					}*/
					
					contour0[a*grid+b] = x0 ;
					contour1[a*grid+b] = x1 ;
					shade[a*grid+b] = svm.svm_predict(model,point) + 1d;

					x1 += step1 ;
				}
				//borderSeries.add(x0,target) ;
				x0 += step0 ;
			}
			contourData = new DefaultContourDataset("Classes",
							DefaultContourDataset.formObjectArray(contour0),
							DefaultContourDataset.formObjectArray(contour1),
							DefaultContourDataset.formObjectArray(shade)) ;
			ContourChart("Decision Regions",Math.max(step0,step1)) ;
						
			/*XYItemRenderer borderRenderer = new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES) ;
			XYPlot plot = new XYPlot() ;
			XYDataset border = new XYSeriesCollection(borderSeries) ;
			plot.setDataset(border) ;
			plot.setRenderer(borderRenderer) ;
			
			ValueAxis domainAxis = new NumberAxis("x_0") ;
			plot.setDomainAxis(domainAxis) ;
			ValueAxis rangeAxis = new NumberAxis("x_1") ;
			plot.setRangeAxis(rangeAxis) ;
			
			JFreeChart chart = new JFreeChart("Decision Border",plot) ;
			ChartFrame frame = new ChartFrame("Decision Border", chart) ;
			frame.pack() ;
			frame.setVisible(true) ;*/
			return "Drawn." ;
		} else {
			return "Can only draw 2 dimensional data." ;
		}
	}
	

}