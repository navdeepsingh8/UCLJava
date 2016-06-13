
package svmapp ;

import libsvm.svm_parameter ;
import java.io.*;
import java.util.* ;
import java.text.NumberFormat ;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.* ;

/**
 * This class is responsible for constructing and handling the GUI.
 *
 * @author Navdeep Singh Daheley
 */
public class GUI extends JFrame {

	private SVMApp app ;
	/** Identifies the uniformly distributed, polynomially
	 * separated data dependency type */
	private final int UNIFORM = 0 ;
	/** Identifies a mixture of gaussians data dependency 
	 * type */
	private final int GAUSSIAN = 1 ;
	/** Data dependency list */
	private String[] dists = {"Polynomially Separated","Mixture of Gaussians"} ;
	/** SVM type list */
	private String[] svms = { "C-SVC","nu-SVC" } ;
	/** Kernels list */
	private String[] kernels = { "Linear","Polynomial","RBF","Sigmoid" } ;
	/** Cross validation thread */
	private Thread XRunner ;
	/** Training thread */
	private Thread TRunner ;
	private NumberFormat nf = NumberFormat.getInstance() ;
	
	//General GUI components
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	private Border empty = BorderFactory.createEmptyBorder(5,5,5,5);
	private JLabel statusBar = new JLabel("Ready.") ;
	private JLabel dataBar = new JLabel("No data.") ;
	private JFileChooser fileChooser = new JFileChooser() ;
	private JProgressBar progressBar = new JProgressBar();
	
	//Generate tab components
	private JPanel distPanel = new JPanel(false) ;
	private JPanel uniformPanel = new JPanel(false) ;
	private JPanel gaussPanel = new JPanel(false) ;
	private JTextField dimField = new JTextField() ;
	private JTextField noField = new JTextField() ;
	private JTextField aField = new JTextField() ;
	private JTextField bField = new JTextField() ;
	private JSlider uNoiseSlider = new JSlider(0,50) ;
	private JTextField degField = new JTextField() ;
	private JTextField nr_classField = new JTextField() ;
	private JTextField hyperField = new JTextField() ;
	private JTextField weightsField = new JTextField() ;
	private JSlider varSlider = new JSlider(0,50) ;
	private JSlider proxSlider = new JSlider(0,50) ;
	private JComboBox distCombo = new JComboBox(dists) ;
	private JButton generateButton = new JButton("Generate") ;
	private JButton viewGenButton = new JButton("View") ;
	private JButton saveGenButton = new JButton("Save") ;
	private JButton loadGenButton = new JButton("Load") ;
	
	//Train tab components
	private JComboBox svmCombo = new JComboBox(svms) ;
	private JComboBox kernelCombo = new JComboBox(kernels) ;
	private JTextField svmCField = new JTextField() ;
	private JTextField svmNuField = new JTextField() ;
	private JTextField svmEField = new JTextField() ;
	private JTextField xValField = new JTextField() ;
	private JTextField kernelDegField = new JTextField() ;
	private JTextField kernelGField = new JTextField() ;
	private JTextField kernelCoField = new JTextField() ;
	private JTextArea outputArea = new JTextArea(6,25) ;
	private JButton xValButton = new JButton("X-Validate") ;
	private JButton fromMemButton = new JButton("Train") ;
	private JButton fromFileButton = new JButton("From file") ;
	private JButton viewSVButton = new JButton("View SVs") ;
	private JButton viewRegionsButton = new JButton("View Regions") ;
	private JButton saveTrainButton = new JButton("Save") ;
	
	//Validate tab components
	private JTextField vSizeField = new JTextField() ;
	private JSlider vNoiseSlider = new JSlider(0,50) ;
	private JTextArea vOutputArea = new JTextArea(2,25) ;
	private JButton vGenButton = new JButton("Generate") ;
	private JButton vLoadButton = new JButton("Load") ;
	private JButton vViewButton = new JButton("View") ;
	private JButton vValidateButton = new JButton("Validate") ;
	
	public GUI(SVMApp app) {
    	this.app = app ;
    	setGenInitials(app.getGenParam()) ;
    	setSVMInitials(app.getSVMParam()) ;
    	setUpThreads() ;
    	createGUI();
  	}
	
	/**
	 * Sets initial generation parameter component values.
	 * @param genParam gen_parameter from which values are taken
	 */
	private void setGenInitials(gen_parameter genParam) {
		dimField = new JTextField(String.valueOf(genParam.dimension),3) ;
		noField = new JTextField(String.valueOf(genParam.size),3) ;
		degField = new JTextField(String.valueOf(genParam.degree),3) ;
		aField = new JTextField(String.valueOf(genParam.a),3) ;
		bField = new JTextField(String.valueOf(genParam.b),3) ;
		uNoiseSlider.setValue((int)(genParam.noise*100)) ;
		distCombo.setSelectedIndex(genParam.distribution) ;
		nr_classField = new JTextField(String.valueOf(genParam.nr_class)) ;
		hyperField = new JTextField(String.valueOf(genParam.meanScale)) ;
		varSlider.setValue((int)((genParam.varScale/genParam.meanScale)*100)) ;
		proxSlider.setValue((int)((genParam.proximity/genParam.meanScale)*100)) ;
		
		StringBuffer weights = new StringBuffer() ;
		nf.setMaximumFractionDigits(1) ;
		for (int c = 0; c < genParam.nr_class;c++) 	
			weights.append(nf.format(genParam.weights[c])+",") ;
		weightsField = new JTextField(weights.toString(),15) ;
		
		vSizeField = new JTextField(String.valueOf(genParam.size),3) ;
		vNoiseSlider.setValue(20) ;
	}
	
	/**
	 * Sets initial SVM parameter component values.
	 * @param svmParam svm_parameter from which values are taken
	 */
	private void setSVMInitials(svm_parameter svmParam) {
		svmCField = new JTextField(String.valueOf(svmParam.C),3) ;
 		svmNuField = new JTextField(String.valueOf(svmParam.nu),3) ;
		svmEField = new JTextField(String.valueOf(svmParam.p),3) ;
		xValField = new JTextField(String.valueOf(svmParam.nr_fold),3);
		switch(svmParam.svm_type) {
			case svm_parameter.C_SVC : 		svmCombo.setSelectedIndex(0) ; break ;
			case svm_parameter.NU_SVC: 		svmCombo.setSelectedIndex(1) ; break ;
			case svm_parameter.EPSILON_SVR:	svmCombo.setSelectedIndex(2) ; break ;
			case svm_parameter.NU_SVR:		svmCombo.setSelectedIndex(3) ; break ;
		}
 		kernelDegField = new JTextField(String.valueOf(svmParam.degree),3) ;
		kernelGField = new JTextField(String.valueOf(svmParam.gamma),3) ;
		kernelCoField = new JTextField(String.valueOf(svmParam.coef0),3) ;
		kernelCombo.setSelectedIndex(svmParam.kernel_type) ;
	}
	
	/**
	 * Constructs the GUI including all JPanels, components and
	 * their associated event handler methods.
	 */
	private void createGUI() {
	    this.setTitle("SVM App");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setResizable(false) ;
	    
	    Container content = getContentPane();
	    content.setLayout(new BorderLayout());
	    	JTabbedPane tabbedPane = new JTabbedPane();
	    	tabbedPane.addTab("Generate", makeGeneratePanel());
	    	tabbedPane.addTab("Train", makeTrainPanel());
	    	tabbedPane.addTab("Validate",makeValidatePanel()) ;
	    content.add(tabbedPane,BorderLayout.CENTER) ;
	    	JPanel bars = new JPanel(false) ;
	    	bars.setLayout(new BoxLayout(bars, BoxLayout.PAGE_AXIS)) ;
	    	bars.add(dataBar) ;
	    	bars.add(statusBar) ;
	    content.add(bars,BorderLayout.PAGE_END) ;
	    pack() ;
	    show() ;
  	}	
	
	private JComponent makeGeneratePanel() {
        JPanel panel = new JPanel(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        setBorder(panel) ;
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        
        	JPanel generalPanel = new JPanel(false) ;
        	generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.PAGE_AXIS)) ;
        	setBorder(generalPanel,"General") ;
        
			generalPanel.add(addInput("Data dimension: ",dimField)) ;
	        generalPanel.add(Box.createRigidArea(new Dimension(0,5)));
	        generalPanel.add(addInput("Number of examples: ",noField));
	        generalPanel.add(Box.createRigidArea(new Dimension(0,5)));
		
		panel.add(generalPanel) ;
		
	        
	    	distPanel.setLayout(new BorderLayout()) ;
	    	setBorder(distPanel,"Data Distribution") ;
	    	
	    		distCombo.setSelectedIndex(1) ;
	    		distCombo.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent e) {
	    				distPanel.remove(1) ;
	    				if (distCombo.getSelectedIndex()==UNIFORM)
	    					distPanel.add(uniformPanel,1) ;
	    				if (distCombo.getSelectedIndex()==GAUSSIAN)
	    					distPanel.add(gaussPanel,1) ;
	    				repaint() ;
	    			}	    			
	    		}) ;
	    		
	    	distPanel.add(distCombo, BorderLayout.PAGE_START) ;
	    				
				uniformPanel.setLayout(new BoxLayout(uniformPanel, BoxLayout.PAGE_AXIS)) ;
				setBorder(uniformPanel,"Polynomially Separated data parameters") ;			
				uniformPanel.add(addInput("Degree: ", 40,degField)) ;
				uniformPanel.add(Box.createRigidArea(new Dimension(0,5)));
				uniformPanel.add(addInput("a: ",aField));
        		uniformPanel.add(Box.createRigidArea(new Dimension(0,5)));
        		uniformPanel.add(addInput("b: ",bField));
        		uniformPanel.add(Box.createRigidArea(new Dimension(0,5)));
				uniformPanel.add(addSlider("Noise: ",uNoiseSlider,10)) ;
				uniformPanel.add(Box.createRigidArea(new Dimension(0,5)));
			
			//distPanel.add(uniformPanel, BorderLayout.CENTER) ;
				
				gaussPanel.setLayout(new BoxLayout(gaussPanel, BoxLayout.PAGE_AXIS)) ;
				setBorder(gaussPanel, "Mixture of Gaussians data parameters") ;
				gaussPanel.add(addInput("# Classes: ",nr_classField)) ;
				gaussPanel.add(Box.createRigidArea(new Dimension(0,5)));
				gaussPanel.add(addInput("Weights: ",weightsField)) ;
				gaussPanel.add(Box.createRigidArea(new Dimension(0,5)));
				gaussPanel.add(addInput("Maximum Mean: ",hyperField)) ;
				gaussPanel.add(Box.createRigidArea(new Dimension(0,5)));
				gaussPanel.add(addSlider("Variance Factor",varSlider,10)) ;
				gaussPanel.add(Box.createRigidArea(new Dimension(0,5)));
				gaussPanel.add(addSlider("Proximity Factor",proxSlider,10)) ;
			
			distPanel.add(gaussPanel, BorderLayout.CENTER) ;
		
        panel.add(distPanel) ;
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        
        	JPanel buttonPanel = new JPanel(false) ;
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS)) ;
			
	    	generateButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		int dim = (int)Double.parseDouble(dimField.getText()) ;
	        		int no = (int)Double.parseDouble(noField.getText()) ;
	        		int dist = distCombo.getSelectedIndex();
		    		if (dist==UNIFORM) {
		    			double a = Double.parseDouble(aField.getText()) ;
	        			double b = Double.parseDouble(bField.getText()) ;
		    			int degree = (int)Double.parseDouble(degField.getText()) ;
		    			double noise = (double)uNoiseSlider.getValue()/100 ;
		    			setData(app.getGen().tsGen(no,dim,a,b,degree,noise)) ;
		       		} else if(dist==GAUSSIAN) {
		       			int nr_class = (int)Double.parseDouble(nr_classField.getText()) ;
		       			double meanScale = Double.parseDouble(hyperField.getText()) ;
		       			double varScale = (double)varSlider.getValue()/100 ;
		       			double proximity = (double)proxSlider.getValue()/100 ; 
		       			StringTokenizer	st = new StringTokenizer(weightsField.getText()," \t\n\r\f,;");
		       			double[] weights = new double[st.countTokens()] ;
		       			for (int i=0;i<weights.length;i++)
		       				weights[i] = Double.parseDouble(st.nextToken()) ;
		       			if (weights.length==0) weights = new double[nr_class] ;
		       			try {
		       				setData(app.getGen().tsGen(no,dim,nr_class,weights,meanScale,varScale,proximity)) ;
		       			} catch (Exception i) {
		       				setStatus(i.getMessage()) ;
		       			}
		       		}
		    	}
		       	
	      	});
	    	buttonPanel.add(generateButton);
	    	buttonPanel.add(Box.createRigidArea(new Dimension(10,0))) ;
	    	
	    	viewGenButton.addActionListener(new ActionListener() {
	        		public void actionPerformed(ActionEvent e) {
	        			setStatus(app.getChart().XYChart(Chart.TRAINING_SET));
	        		}
	      	});
	    	buttonPanel.add(viewGenButton);
	    	buttonPanel.add(Box.createRigidArea(new Dimension(10,0))) ;
	    	
	    	saveGenButton.addActionListener(new ActionListener() {
	        		public void actionPerformed(ActionEvent e) {
	        			try { setStatus(app.getIO().tsWrite()) ; }
	        			catch (Exception i) { setStatus(i.getMessage());}        				
	        		}
	        });
	    	buttonPanel.add(saveGenButton);
	    	buttonPanel.add(Box.createRigidArea(new Dimension(10,0))) ;
	    	
	    	loadGenButton.addActionListener(new ActionListener() {
	        		public void actionPerformed(ActionEvent e) {
	        			try { 
	        				setData(app.getIO().tsLoad()) ;
	        				setStatus("Loaded data from file.") ;
	        			}
	        			catch (Exception i) { 
	        				setStatus(i.getMessage());
	        			}        				
	        		}
	        });
	    	buttonPanel.add(loadGenButton);
	    	
        panel.add(buttonPanel) ;
        panel.add(Box.createRigidArea(new Dimension(0,5)));        
        return panel ;
    }
    
    private JComponent makeTrainPanel() {
        JPanel panel = new JPanel(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        setBorder(panel) ;
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        
        	JPanel paramPanel = new JPanel(false) ;
        	paramPanel.setLayout(new BoxLayout(paramPanel, BoxLayout.LINE_AXIS)) ;
        	setBorder(paramPanel,"SVM parameters") ;
        		
        		JPanel svmPanel = new JPanel(false) ;
        		svmPanel.setLayout(new BoxLayout(svmPanel, BoxLayout.PAGE_AXIS)) ;
        		setBorder(svmPanel,"SVM type") ;
        		svmPanel.add(svmCombo) ;
        		svmPanel.add(Box.createRigidArea(new Dimension(0,5)));
        		svmPanel.add(addInput("C: ",svmCField)) ;
        		svmPanel.add(Box.createRigidArea(new Dimension(0,5)));
        		svmPanel.add(addInput("nu: ",svmNuField)) ;
        		svmPanel.add(Box.createRigidArea(new Dimension(0,5)));
        		//svmPanel.add(addInput("e: ",svmEField)) ;
        		//svmPanel.add(Box.createRigidArea(new Dimension(0,5)));
        		svmPanel.add(addInput("X-Validation N-fold: ",xValField)) ;
        		svmPanel.add(Box.createRigidArea(new Dimension(0,5)));
        	
        	paramPanel.add(svmPanel) ;
        	paramPanel.add(Box.createRigidArea(new Dimension(10,0)));
        	
        		JPanel kernelPanel = new JPanel(false) ;
        		kernelPanel.setLayout(new BoxLayout(kernelPanel, BoxLayout.PAGE_AXIS)) ;
        		setBorder(kernelPanel,"Kernel") ;
        		kernelPanel.add(kernelCombo) ;
        		kernelPanel.add(Box.createRigidArea(new Dimension(0,5)));
        		kernelPanel.add(addInput("Degree: ",kernelDegField)) ;
        		kernelPanel.add(Box.createRigidArea(new Dimension(0,5)));
        		kernelPanel.add(addInput("Gamma: ",kernelGField)) ;
        		kernelPanel.add(Box.createRigidArea(new Dimension(0,5)));
        		kernelPanel.add(addInput("Coefficient: ",kernelCoField)) ;
        		kernelPanel.add(Box.createRigidArea(new Dimension(0,5)));
        	
        	paramPanel.add(kernelPanel) ;
        	
        panel.add(paramPanel) ;
        panel.add(Box.createRigidArea(new Dimension(0,5)));
				
			JPanel outputPanel = new JPanel(false) ;
			outputPanel.setLayout(new BoxLayout(outputPanel,BoxLayout.PAGE_AXIS)) ;
			setBorder(outputPanel,"Output") ;
			outputPanel.add(Box.createRigidArea(new Dimension(0,5)));
			outputArea.setEditable(false) ;
			outputPanel.add(new JScrollPane(outputArea)) ;
			outputPanel.add(Box.createRigidArea(new Dimension(0,5)));
			outputPanel.add(progressBar) ;
			outputPanel.add(Box.createRigidArea(new Dimension(0,5)));
			
		panel.add(outputPanel) ;
		panel.add(Box.createRigidArea(new Dimension(0,5)));
		
			JPanel buttonPanel = new JPanel(false) ;
			buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS)) ;
			setBorder(buttonPanel,"Train") ;
			
			xValButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
					if (!XRunner.isAlive() && !TRunner.isAlive()) {
						setUpThreads() ;
						//XRunner.setPriority(Thread.MAX_PRIORITY) ;
						XRunner.start() ;
					} else if (XRunner.isAlive()) {
						/* Compile with -depreciation to allow this */
						XRunner.stop() ;
						System.gc();
						outputArea.setText("") ;
						setStatus("Cross Validation stopped.");
						setButtonLabel(xValButton, "X-Validate");
						progressBar.setValue(0) ;						
					}			
		       	}
	      	});
			buttonPanel.add(xValButton) ;
			buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
			
			
			fromMemButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
					if(!XRunner.isAlive() && !TRunner.isAlive()) {
						setUpThreads() ;
						//TRunner.setPriority(Thread.MAX_PRIORITY) ;
						TRunner.start() ;
					} else if (TRunner.isAlive()) {
						/* Compile with -depreciation to allow this */
						TRunner.stop() ;
						System.gc();
						setStatus("Training stopped.");
						setButtonLabel(fromMemButton, "Train");
						progressBar.setIndeterminate(false) ;
					}
				}
	      	});
	      	
			buttonPanel.add(fromMemButton) ;
			buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
			
			//buttonPanel.add(fromFileButton) ;
			//buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
			
			viewRegionsButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
					setStatus(app.getChart().drawRegions()) ;
		       	}
	      	});
			buttonPanel.add(viewRegionsButton) ;
			buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
						
			viewSVButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
					setStatus(app.getChart().XYChart(Chart.SUPPORT_VECTORS)) ;
		       	}
	      	});
			buttonPanel.add(viewSVButton) ;
			buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
			
			saveTrainButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
					try { 
						setStatus(app.getIO().svmWrite()) ;
					}
					catch(Exception i) { 
						setStatus(i.getMessage()) ;
					}
				}
	      	});
			buttonPanel.add(saveTrainButton) ;
			
		panel.add(buttonPanel) ;       	
        return panel ;
    }
    
    private JComponent makeValidatePanel() {
    	JPanel panel = new JPanel(false) ;
    	panel.setLayout(new BorderLayout());
        setBorder(panel) ;
                	
        	JPanel paramPanel = new JPanel(false) ;
        	paramPanel.setLayout(new FlowLayout()) ;
        	setBorder(paramPanel,"Validation Set") ;
        	paramPanel.add(addSlider("Noise",vNoiseSlider,10)) ;
        	paramPanel.add(addInput("Size",vSizeField)); 
        
        panel.add(paramPanel,BorderLayout.PAGE_START) ;
        	
        	JPanel vOutputPanel = new JPanel(false) ;
        	
        	setBorder(vOutputPanel,"Output") ;
        	vOutputPanel.setLayout(new BoxLayout(vOutputPanel,BoxLayout.PAGE_AXIS)) ;
        	vOutputPanel.add(vOutputArea) ;
        	vOutputArea.setEditable(false) ;
			vOutputPanel.add(new JScrollPane(vOutputArea)) ;
        
        panel.add(vOutputPanel,BorderLayout.CENTER) ;
        
        	JPanel buttonPanel = new JPanel(false) ;
        	setBorder(buttonPanel,"Validate") ;
        	
        	vGenButton.addActionListener(new ActionListener() {
		       	public void actionPerformed(ActionEvent e) {
					int size = (int)Double.parseDouble(vSizeField.getText()) ;
					double noise = (double)vNoiseSlider.getValue()/100 ;
					setStatus(app.getGen().vsGen(size,noise)) ;
			   	}
	      	});
			buttonPanel.add(vGenButton) ;
        	
        	vLoadButton.addActionListener(new ActionListener() {
		       	public void actionPerformed(ActionEvent e) {
		       		try { 
		       			setStatus(app.getIO().vsLoad()) ; 
		       		}
	        		catch (Exception i) { 
	        			setStatus(i.getMessage()) ;
	        		} 
			   	}
	      	});
			buttonPanel.add(vLoadButton) ;
			
			vViewButton.addActionListener(new ActionListener() {
		       	public void actionPerformed(ActionEvent e) {
					setStatus(app.getChart().XYChart(Chart.VALIDATION_SET)) ;
			   	}
	      	});
			buttonPanel.add(vViewButton) ;
			
			vValidateButton.addActionListener(new ActionListener() {
		       	public void actionPerformed(ActionEvent e) {
		       		setStatus("Validating...") ;
		       		try {
		       			vOutputArea.setText(app.getTrain().validate()) ;
		       			setStatus("Validated.") ;
		       		} catch (Exception i) {
		       			setStatus(i.getMessage()) ;
		       		}
			   	}
	      	});
			buttonPanel.add(vValidateButton) ;
        	
        panel.add(buttonPanel,BorderLayout.PAGE_END) ;        	
        return panel ;    	
    }
    
    
    /** 
     * Changes the text of the statusBar component.
     * @param status String to change text to
     */
    public void setStatus(String status) {
		statusBar.setText(status) ;
		repaint() ;
	}
	
	
	/** Changes the text of the dataBar component, when
	 * training set is generated or loaded.
	 * @param data String to change text to
	 */
	public void setData(String data) {
		dataBar.setText(data) ;
		setStatus("Training Set generated/loaded.") ;
		repaint() ;
	}
	
	/**
	 * Sets the SVM parameters of the system by calling
	 * the method SVMTrain.setSVMParams(...).
	 * @see SVMTrain#setSVMParams()
	 */
	private void setSVMParams() {
		int svm = svm_parameter.C_SVC ;
		switch(svmCombo.getSelectedIndex()) {
			case 0: { svm = svm_parameter.C_SVC ; break ; }
			case 1: { svm = svm_parameter.NU_SVC ; break ; }
			case 2: { svm = svm_parameter.EPSILON_SVR ; break ;}
			case 3: { svm = svm_parameter.NU_SVR ; break ; }
		}
		int kernel = kernelCombo.getSelectedIndex() ;
		double degree = Double.parseDouble(kernelDegField.getText()) ;
		double gamma = Double.parseDouble(kernelGField.getText()) ;
		double co = Double.parseDouble(kernelCoField.getText()) ;
		double c = Double.parseDouble(svmCField.getText()) ;
		double nu = Double.parseDouble(svmNuField.getText()) ;
		double e = Double.parseDouble(svmEField.getText()) ;
		int n = (int)Double.parseDouble(xValField.getText()) ;
		app.getTrain().setSVMParams(svm,kernel,degree,gamma,co,c,nu,e,n) ;
	}
	
	private void setButtonLabel(JButton button, String label) {
		button.setText(label) ;
	}
  	
  	private void setBorder(JPanel panel) {
  		setBorder(panel,"",5,15,5,15) ;
  	}
  	
  	private void setBorder(JPanel panel, String title) {
  		setBorder(panel,title,5,15,5,15) ;
  	}
  	
  	private void setBorder(JPanel panel, int top, int left, int bottom, int right) {
  		setBorder(panel,"",top,left,bottom,right) ;
  	}
  	
  	private void setBorder(JPanel panel, String title, int top, int left, int bottom, int right) {
  		if (title.equals("")) {
  			panel.setBorder(BorderFactory.createEmptyBorder(top,left,bottom,right)) ;
  		} else if (!title.equals("")) {
  			panel.setBorder(BorderFactory.createCompoundBorder(
		  					BorderFactory.createTitledBorder(title),
		                    BorderFactory.createEmptyBorder(top,left,bottom,right))) ;
		}
  	}    
    
    private JComponent addInput(String input, JTextField inputField) {
    	return addInput(input, 20, inputField) ;
    }
   
    private JComponent addInput(String input, int preflength, JTextField inputField) {
    	JPanel inputPanel = new JPanel(false) ;
    	inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS)) ;
    	JLabel l = new JLabel(input) ;
    	inputPanel.add(l) ;
    	inputPanel.add(Box.createRigidArea(new Dimension(10,0)));
    		//inputField.setPreferredSize(new Dimension(preflength,20));
    		//inputField.setMaximumSize(new Dimension(40,20)) ;
    	l.setLabelFor(inputField) ;
    	inputPanel.add(inputField) ;
    	return inputPanel ;
    }
    
    private JComponent addSlider(String label,JSlider slider,int increment) {
    	
    	Hashtable labelTable = new Hashtable();
		for(int i=0;i<=slider.getMaximum();i+=increment)
			labelTable.put(new Integer(i), new JLabel(""+(double)i/100)) ;
		JPanel sliderPanel = new JPanel(false) ;
    	sliderPanel.setLayout(new BoxLayout(sliderPanel,BoxLayout.PAGE_AXIS)) ;
    		JLabel l = new JLabel(label,JLabel.LEFT) ;
    		l.setAlignmentX(Component.CENTER_ALIGNMENT);
    	sliderPanel.add(l) ;
    	sliderPanel.add(Box.createRigidArea(new Dimension(0,2))) ;
    		l.setLabelFor(slider) ;
    		slider.setMajorTickSpacing(increment) ;
    		slider.setPaintTicks(true) ;
    		slider.setLabelTable(labelTable) ;
    		slider.setPaintLabels(true) ;
    	sliderPanel.add(slider) ;
    	return sliderPanel ;
   	}
   	
   	private void setUpThreads() {
   		TRunner = new Thread("Training Thread") {
			public void run() {
					setSVMParams() ;
					setButtonLabel(fromMemButton, "Stop");
					setStatus("Training...");							
					long startTime= System.currentTimeMillis();
					try {
						app.getTrain().train(SVMTrain.TRAIN) ;
					} catch (Exception i){
						setStatus(i.getMessage()) ;
						setButtonLabel(fromMemButton, "Train") ;
						return ;
					}
					long endTime= System.currentTimeMillis();
					long runTime= endTime-startTime;
					setStatus("Trained: " + (runTime/1000) + " seconds");
					setButtonLabel(fromMemButton, "Train");
					System.gc();
			}
		};
		
		XRunner = new Thread("Cross Validation Thread") {
			public void run() {
				setSVMParams() ;
				setButtonLabel(xValButton, "Stop");
				setStatus("Cross Validating...") ;
				long startTime= System.currentTimeMillis();
				try {
					app.getTrain().train(SVMTrain.X_VALIDATE) ;
				} catch (Exception i){
					setStatus(i.getMessage()) ;
					setButtonLabel(xValButton, "X-Validate") ;
					return ;
				}
				long endTime= System.currentTimeMillis();
				long runTime= endTime-startTime;
				setStatus("Cross Validated: " + (runTime/1000) + " seconds");
				setButtonLabel(xValButton, "X-Validate");
				System.gc();
			}
		};
		
   	}
    
    
    /** 
     * Opens JFileChooser dialog for selecting a file to
     * save to.
     * @return File returned File from JFileChooser
     */
    public File getFileToSave() {
		int returnValue = fileChooser.showSaveDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION)
	  		return fileChooser.getSelectedFile();
		else
	  		return null;
	}
	
	
	/** 
     * Opens JFileChooser dialog for selecting a file to
     * load.
     * @return File returned File from JFileChooser
     */
	public File getFileToLoad() {
		int returnValue = fileChooser.showOpenDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) 
	  		return fileChooser.getSelectedFile();
	  	else
	  		return null;
	}
	
	
	/**
	 * Returns progress bar component.
	 * @return JProgressBar progress bar
	 */
	public JProgressBar getProgressBar() {
		return progressBar ;
	}
	
	
	/**
	 * Returns ouput area component.
	 * @return JTextArea output area
	 */
	public JTextArea getOutputArea() {
		return outputArea ;
	}
}