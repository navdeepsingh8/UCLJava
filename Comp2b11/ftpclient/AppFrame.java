package ftpclient ; 

import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.filechooser.* ;
import javax.swing.tree.* ;
import javax.swing.event.* ;
import java.util.* ;
import java.io.* ;
import Client.ServerResponseException ;

public class AppFrame extends JFrame {
	
		private Client client ;
		private ArrayList dir ;
		
		/**
		 * Panels and panes.
		 */
		JPanel contentPane ;
		BorderLayout borderLayout1 = new BorderLayout() ;
		JSplitPane splitPane ;
		JScrollPane localScrollPane = new JScrollPane() ;
		JScrollPane remoteScrollPane = new JScrollPane() ;
		
		/**
		 * Menu components.
		 */
		JMenuBar menuBar = new JMenuBar() ;
		JMenu fileMenu = new JMenu() ;
		JMenu editMenu = new JMenu() ;
		JMenu helpMenu = new JMenu() ;
		JMenuItem helpAbout = new JMenuItem() ;
		
		/**
		 * Content pane components.
		 */
		JLabel statusBar = new JLabel() ;
		JPanel toolPane = new JPanel() ;
		JToolBar toolBar = new JToolBar() ;
		JToolBar addressBar = new JToolBar() ;
		DefaultMutableTreeNode nodeRemoteRoot, nodeLocalRoot ;
		JTree treeLocal, treeRemote ;
		
		public AppFrame(String title)
		{
			enableEvents(AWTEvent.WINDOW_EVENT_MASK) ;
		    try {
		    	jbInit() ;
		    	this.setTitle(title);
		    } catch(Exception e) {
		    	e.printStackTrace();
		  	}
		}
		
		private void jbInit() throws Exception
		{
			//setIconImage(Toolkit.getDefaultToolkit().createImage(AppFrame.class.getResource("images/ebook.gif")));
    		contentPane = (JPanel)this.getContentPane();
    		contentPane.setLayout(borderLayout1);
    		contentPane.setBackground(new Color(240, 240, 230)) ;
    		//splitPane.setBackground(new Color(240, 240, 230)) ;
    		localScrollPane.setBackground(new Color(240, 240, 230)) ;
    		remoteScrollPane.setBackground(new Color(240, 240, 230)) ;
    		//this.setSize(new Dimension(580, 400));

    		fileMenu.setText("File") ;
    		fileMenu.setBackground(new Color(240, 240, 230)) ;
			editMenu.setText("Edit") ;
			editMenu.setBackground(new Color(240, 240, 230)) ;
			helpMenu.setText("Help");
			helpMenu.setBackground(new Color(240, 240, 230)) ;
			helpAbout.setText("About");
			helpAbout.setBackground(new Color(240, 240, 230)) ;
			helpAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					about_actionPerformed(e);
				}
			});
			helpMenu.add(helpAbout);
    
			menuBar.add(fileMenu) ;
			menuBar.add(editMenu) ;
			menuBar.add(helpMenu) ;
			menuBar.setBackground(new Color(240, 240, 230)) ;
			this.setJMenuBar(menuBar);
    
			statusBar.setText(" ");
			statusBar.setBackground(new Color(240, 240, 230)) ;
    		addButtons() ;
    		addAddress() ;
    		toolBar.setBackground(new Color(240, 240, 230)) ;
    		addressBar.setBackground(new Color(240, 240, 230)) ;
    		toolPane.add(toolBar, BorderLayout.NORTH) ;
    		toolPane.add(addressBar, BorderLayout.SOUTH) ;
    		toolPane.setBackground(new Color(240, 240, 230)) ;
    		
			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, localScrollPane, remoteScrollPane) ;
			splitPane.setOneTouchExpandable(true);
			splitPane.setSize(new Dimension(580, 600)) ;
			splitPane.setDividerLocation(290);
			splitPane.setBackground(new Color(240, 240, 230)) ;
			contentPane.add(toolPane, BorderLayout.NORTH) ;
			contentPane.add(splitPane, BorderLayout.CENTER) ;
			contentPane.add(statusBar, BorderLayout.SOUTH) ;
			
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {System.exit(0);}
			});

		}
		
		protected void addButtons()
  		{
        	JButton button = null;
        	
        	//Connect Button
	        button = new JButton(new ImageIcon("ftpclient/connect.gif"));
	        button.setToolTipText("Connect");
	        button.setBorderPainted(false) ;
	        button.setBackground(new Color(240, 240, 230)) ;
	        button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e)
	      		{
	        		try {
	        			connect_actionPerformed(e);
	        		} catch (Exception i) {
	        			System.out.println(i) ;
	        		}
	      		}
	        });
	        toolBar.add(button);
	        
	        //Disconnect Button
	        button = new JButton(new ImageIcon("ftpclient/dc.gif"));
	        button.setToolTipText("Disconnect");
	        button.setBorderPainted(false) ;
	        button.setBackground(new Color(240, 240, 230)) ;
	        button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e)
	      		{
	        		disconnect_actionPerformed(e);
	      		}
	        });
	        toolBar.add(button, 1);
	        toolBar.addSeparator() ;
	        
	        //Refresh Button
	        button = new JButton(new ImageIcon("ftpclient/refresh.gif"));
	        button.setToolTipText("Refresh");
	        button.setBorderPainted(false) ;
	        button.setBackground(new Color(240, 240, 230)) ;
	        button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e)
	      		{
	        		refresh_actionPerformed(e);
	      		}
	        });
	        toolBar.add(button, 2);
	        
	        //Abort Button
	        button = new JButton(new ImageIcon("ftpclient/abort.gif"));
	        button.setToolTipText("Abort");
	        button.setBorderPainted(false) ;
	        button.setBackground(new Color(240, 240, 230)) ;
	        button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e)
	      		{
	        		abort_actionPerformed(e);
	      		}
	        });
	        toolBar.add(button, 3);
	     }
	     
	     public void addAddress() 
	     {
	     	
	     	JTextField text = null ;
	     	text = new JTextField(10) ;
	     	text.setToolTipText("Hostname") ;
	     	addressBar.add(text) ;
	     	
	     	text = new JTextField(10) ;
	     	text.setToolTipText("Username") ;
	     	addressBar.add(text, 1) ;
	     	
	     	JPasswordField password = new JPasswordField(10) ;
	     	password.setToolTipText("Password") ;
	     	addressBar.add(password, 2) ;
	     	
	     	text = new JTextField(3) ;
	     	text.setToolTipText("Port") ;
	     	text.setText("21") ;
	     	addressBar.add(text, 3) ;
	     }
	     
		public static void main(String[] args)
		{
			AppFrame testFrame = new AppFrame("FTP Client Window") ;
			testFrame.pack() ;
			testFrame.setBackground(new Color(240, 240, 230)) ;
			testFrame.setVisible(true) ;
			JTextField tempField = null ;
			for (int argIndex = 0; argIndex < 4; argIndex++) {
				if (args.length > argIndex) {
					tempField = (JTextField)testFrame.addressBar.getComponentAtIndex(argIndex) ;
					tempField.setText(args[argIndex]) ;
				}
			}
					
		}
		
	     
	     public void abort_actionPerformed(ActionEvent e)
	   	 {
	   	 }
	   	 
	   	 public void refresh_actionPerformed(ActionEvent e)
	   	 {
	   	 }
	   	 
	   	 public void disconnect_actionPerformed(ActionEvent e)
	   	 {
	   	 	if (client != null) {
	   	 		try {
	   	 			client.quit() ;
	   	 		} catch (ServerResponseException s) {}
	   	 		client = null ;
	   	 	}
	   	 	treeRemote = null ;
	   	 	remoteScrollPane.getViewport().add(treeRemote) ;
	   	 }
	   	 
	   	 public void connect_actionPerformed(ActionEvent e) throws Exception
	   	 {
	   	 	String[] details = new String[4] ;
	   	 	for (int n = 0; n < 4; n++) {
	   	 		JTextField tempField = (JTextField)addressBar.getComponent(n) ;
	   	 		if ((n == 0 || n == 3) && tempField.getText().equals("")) {
	   	 			throw new Exception("No server specified") ;
	   	 		}
	   	 		details[n] = tempField.getText() ;
	   	 	}
	   	 	client = new Client(details[0], Integer.parseInt(details[3])) ;
	   	 	statusBar.setText(client.passResponse()) ;
	   	 	client.logIn(details[1], details[2]) ;
	   	 	
	   	 	nodeRemoteRoot = new DefaultMutableTreeNode(client.currentDir()) ;
	   	 	System.out.println("node root " + nodeRemoteRoot) ;
	   	 	statusBar.setText(client.passResponse()) ;
	   	 	contentPane.add(statusBar, BorderLayout.SOUTH) ;
	   	 	client.dataConnection() ;
	   	 	statusBar.setText(client.passResponse()) ;
	   	 	contentPane.add(statusBar, BorderLayout.SOUTH) ;
	   	 	dir = client.listDir() ;
	   	 	statusBar.setText(client.passResponse()) ;
	   	 	contentPane.add(statusBar, BorderLayout.SOUTH) ;
	   	 	DefaultMutableTreeNode top = new DefaultMutableTreeNode(nodeRemoteRoot);
	   	 	createDir(top) ;
	   	 	treeRemote = new JTree(top) ;
	   	 	remoteScrollPane.getViewport().add(treeRemote) ;
	   	 }
	   	 
	   	 public void createDir(DefaultMutableTreeNode top)
	   	 {
	   	 	DefaultMutableTreeNode node = null ;
	   	 	
	   	 	for (int n = 0; n < dir.size(); n++) {
	   	 		String nodeString = (String)dir.get(n) ;
	   	 		System.out.println(nodeString) ;
	   	 		node = new DefaultMutableTreeNode(nodeString) ;
	   	 		top.add(node) ;
	   	 	}
	   	 }
	   	 
	   	 public void about_actionPerformed(ActionEvent e)
	   	 {
	   	 }
		
		
}