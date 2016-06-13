/**
 * Title:        EBook Application
 * Description:  Class responsible for constructing the basic application.
 * Copyright:    Copyright (c) 2001
 * @author Navdeep Daheley
 * @version 1.0 13/12/2001
 */
package ebookapp ; 

import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.filechooser.* ;
import javax.swing.tree.* ;
import javax.swing.event.* ;
import java.util.* ;
import java.io.* ;

public class AppFrame extends JFrame
{
  
  /**
   * Panels and panes.
   */
  JPanel contentPane ;
  BorderLayout borderLayout1 = new BorderLayout() ;
  JSplitPane splitPane ;
  JScrollPane navScrollPane = new JScrollPane() ;
  JScrollPane textScrollPane = new JScrollPane() ;
  
  /**
   * Menu components.
   */
  JMenuBar menuBar = new JMenuBar() ;
  JMenu fileMenu = new JMenu() ;
  JMenuItem fileOpen = new JMenuItem();
  JMenuItem fileExit = new JMenuItem() ;
  JMenu editMenu = new JMenu() ;
  JMenuItem editCopy = new JMenuItem() ;
  JMenu bookMenu = new JMenu() ;
  JMenuItem bookPrevChap = new JMenuItem() ;
  JMenuItem bookNextChap = new JMenuItem() ;
  JMenu helpMenu = new JMenu() ;
  JMenuItem helpAbout = new JMenuItem() ;
  
  /**
   * Content pane components.
   */
  JLabel statusBar = new JLabel() ;
  JTextArea textOutput = new JTextArea();
  JToolBar toolBar = new JToolBar() ;
  DefaultMutableTreeNode nodeBook ;
  JTree treeBook ;
    
  /**
   * Model the GUI should communicate with.
   */
  private Model model = null ;

  /**
   * Construct the frame
   */
  public AppFrame(String title)
  {
    this.model = model ;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK) ;
    try
    {
      jbInit() ;
      this.setTitle(title);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public void setModel(Model model)
  {
    this.model = model ;
  }

  public JTextArea getTextArea()
  {
    return textOutput ;
  }

  /**
   * Component initialization
   */
  private void jbInit() throws Exception
  {
    setIconImage(Toolkit.getDefaultToolkit().createImage(AppFrame.class.getResource("images/ebook.gif")));
    contentPane = (JPanel)this.getContentPane();
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(400, 300));
    
    fileMenu.setText("File") ;
    
    fileOpen.setText("Open...") ;
    fileOpen.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        fileOpen_actionPerformed(e);
      }
    });
    fileMenu.add(fileOpen);
    fileMenu.addSeparator();
        
    fileExit.setText("Exit") ;
    fileExit.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        fileExit_actionPerformed(e);
      }
    }) ;
    fileMenu.add(fileExit);
    
    editMenu.setText("Edit") ;
    editCopy.setText("Copy") ;
    editCopy.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			editCopy_actionPerformed(e) ;
		}
	}) ;
    editMenu.add(editCopy) ;
    
    bookMenu.setText("Book") ;
    bookNextChap.setText("Next Chapter") ;
    bookNextChap.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			nextChap_actionPerformed(e) ;
		}
	}) ;
    bookMenu.add(bookNextChap) ;
    bookPrevChap.setText("Previous Chapter") ;
    bookPrevChap.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			prevChap_actionPerformed(e) ;
		}
	}) ;
    bookMenu.add(bookPrevChap) ;
    
    helpMenu.setText("Help");
    helpAbout.setText("About");
    helpAbout.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        helpAbout_actionPerformed(e);
      }
    });
    helpMenu.add(helpAbout);
    
    menuBar.add(fileMenu) ;
    menuBar.add(editMenu) ;
    menuBar.add(bookMenu) ;
    menuBar.add(helpMenu) ;
    this.setJMenuBar(menuBar);
    
    textOutput.setColumns(0);
    textOutput.setRows(0);
    textOutput.setEditable(false) ;
    textOutput.setMargin(new Insets(10, 10, 10, 10)) ;
    textOutput.setFont(new Font("Serif", Font.PLAIN, 16));
    statusBar.setText(" ");
    addButtons(toolBar) ;
    
    textScrollPane.getViewport().add(textOutput, null);
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navScrollPane, textScrollPane) ;
    splitPane.setOneTouchExpandable(true);
	splitPane.setDividerLocation(0);
    contentPane.add(toolBar, BorderLayout.NORTH) ;
    contentPane.add(splitPane, BorderLayout.CENTER) ;
    contentPane.add(statusBar, BorderLayout.SOUTH) ;
  }

  /**
   * Add buttons to the toolbar
   */
  protected void addButtons(JToolBar toolBar)
  {
        JButton button = null;

        //Open Button
        button = new JButton(new ImageIcon("ebookapp/images/open.gif"));
        button.setToolTipText("Open...");
        button.setBorderPainted(false) ;
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
      		{
        		fileOpen_actionPerformed(e);
      		}
        });
        toolBar.add(button);
        toolBar.addSeparator() ;
        
        //Back Button
        button = new JButton(new ImageIcon("ebookapp/images/back.gif"));
        button.setToolTipText("Previous Chapter");
        button.setEnabled(false) ;
        button.setBorderPainted(false) ;
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                prevChap_actionPerformed(e) ;
            }
        });
        toolBar.add(button, 1);

        //Forward Button
        button = new JButton(new ImageIcon("ebookapp/images/forward.gif"));
        button.setToolTipText("Next Chapter");
        button.setEnabled(false) ;
        button.setBorderPainted(false) ;
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	nextChap_actionPerformed(e) ;
            }
        });
        toolBar.add(button, 2);
    }
    
  /**
   * Build the navigation tree.
   */
  protected void createTree(String title)
  {
  	nodeBook = new DefaultMutableTreeNode(title) ;
  	
  	DefaultMutableTreeNode part = null ;
  	DefaultMutableTreeNode chapter = null ;
  	
  	java.util.List parts = model.treeParts(title) ;
      
   	//Add part and chapter nodes.
   	for (Iterator partIterator = parts.iterator(); partIterator.hasNext(); )
    {
    	String nextPart = (String)partIterator.next() ;
		part = new DefaultMutableTreeNode(nextPart) ;
		nodeBook.add(part) ;
      	java.util.List chapters = model.treeChapters(title, nextPart) ;
      	
      	for (Iterator chapIterator = chapters.iterator(); chapIterator.hasNext(); )
      	{
      		String nextChap = (String)chapIterator.next() ;
      		chapter = new DefaultMutableTreeNode(nextChap) ;
      		part.add(chapter) ;
      	}
  	}
  	
  	treeBook = new JTree(nodeBook) ;
  	
  	//Add listener for node selection.
  	treeBook.addTreeSelectionListener(new TreeSelectionListener() {
    	public void valueChanged(TreeSelectionEvent e)
    	{
        	treeBook_valueChanged(e) ;
		}
	}) ;
	
	//Add rendering changes
	DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	renderer.setLeafIcon(new ImageIcon("ebookapp/images/chaptree.gif"));
	renderer.setClosedIcon(new ImageIcon("ebookapp/images/booktree.gif")) ;
	renderer.setOpenIcon(new ImageIcon("ebookapp/images/booktree.gif")) ;
	treeBook.setCellRenderer(renderer);
	treeBook.putClientProperty("JTree.lineStyle", "Angled");

  	navScrollPane.getViewport().add(treeBook) ;
  	splitPane.setDividerLocation(200);
  }
  
  /**
   * Return file extension
   * @return String
   */
  protected String getExtension(File f)
  {
  	String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');

    if (i > 0 &&  i < s.length() - 1) {
    	ext = s.substring(i+1).toLowerCase();
    }
    return ext ;
  }
  
  /**
   * Common component changes on new chapter.
   */
  protected void refreshChap(String status)
  {
  	if (status != null)
  	{
  		statusBar.setText(status) ;
  		getTextArea().getCaret().setDot(0) ;
  	}
  }
     
  /**
   * File|Exit action performed
  **/
  public void fileExit_actionPerformed(ActionEvent e)
  {
    System.exit(0);
  }

  /**
   * Help|About action performed
  **/
  public void helpAbout_actionPerformed(ActionEvent e)
  {
    AppFrame_AboutBox dlg = new AppFrame_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }

  /**
   * Overridden so we can exit when window is closed
  **/
  protected void processWindowEvent(WindowEvent e)
  {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      fileExit_actionPerformed(null);
    }
  }
  
  /**
   * Next Chapter menu or toolbar action performed.
   */
  void nextChap_actionPerformed(ActionEvent e)
  {
  	refreshChap(model.writeNext()) ;
  }
  
  /**
   * Prevous Chapter menu or toolbar action performed.
   */
  void prevChap_actionPerformed(ActionEvent e)
  {
  	refreshChap(model.writePrev()) ;
  }
  
  /**
   * Tree node selection changed.
   */
  void treeBook_valueChanged(TreeSelectionEvent e)
  {
  	DefaultMutableTreeNode node = (DefaultMutableTreeNode)treeBook.getLastSelectedPathComponent();
	if (node == null || !node.isLeaf()) return ;
	DefaultMutableTreeNode nodeTitle = (DefaultMutableTreeNode)node.getRoot() ;
	DefaultMutableTreeNode nodePart = (DefaultMutableTreeNode)node.getParent() ;
	refreshChap(model.writeChap((String)nodeTitle.getUserObject(), (String)nodePart.getUserObject(), (String)node.getUserObject())) ;
  }

  /**
   * File|Open menu or Open toolbar button action performed.
   */
  void fileOpen_actionPerformed(ActionEvent e)
  {
    JFileChooser fc = new JFileChooser() ;
    fc.setFileFilter(new javax.swing.filechooser.FileFilter()
		{
    		// Accept all directories and XML files
    		public boolean accept(File f) {
        		if (f.isDirectory()) {
            	return true;
        	}
    		String extension = getExtension(f);
			if (extension != null) {
            		if (extension.equals("xml")) {
              			return true ;
            		} else {
                		return false;
            		}
    			}
    			return false;
    		}
    
    		// The description of this filter
    		public String getDescription() {
        	return "XML eBooks";
    		}
		}) ;
	
    int returnVal = fc.showOpenDialog(this) ;

    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      File file = fc.getSelectedFile() ;
      String title = model.loadFile(file) ;
      createTree(title) ;
      statusBar.setText(title) ;
      toolBar.getComponent(1).setEnabled(true) ;
      toolBar.getComponent(2).setEnabled(true) ;
    }
  }
  
  /**
   * Edit|Copy menu action performed.
   */
  void editCopy_actionPerformed(ActionEvent e)
  {
  	getTextArea().copy() ;
  }
  
}