/**
 * Title:        EBook Application
 * Description: The About dialog class.
 * Copyright:    Copyright (c) 2001
 * @author Navdeep Daheley
 * @version 1.0 17/12/01
 */

package ebookapp ;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class AppFrame_AboutBox extends JDialog implements ActionListener
{

  JPanel mainPanel = new JPanel() ;
  JPanel imagePanel = new JPanel();
  JPanel textPanel = new JPanel();
  JPanel buttonPanel = new JPanel() ;
  
  JButton buttonOK = new JButton();
  JLabel imageLabel = new JLabel();
  JLabel labelProduct = new JLabel();
  JLabel labelVersion = new JLabel();
  JLabel labelCopyright = new JLabel();
  JLabel labelComments = new JLabel();
  
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  GridLayout gridLayout1 = new GridLayout();
  
  String product = "EBook Application";
  String version = "1.0";
  String copyright = "Copyright (c) 2001";
  String comments = "";

  public AppFrame_AboutBox(Frame parent)
  {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    pack();
  }

  /**
   * Component initialization
   */
  private void jbInit() throws Exception
  {
    imageLabel.setIcon(new ImageIcon(AppFrame_AboutBox.class.getResource("images/ebook.gif")));
    this.setTitle("About");
    setResizable(false);
    
    labelProduct.setText(product);
    labelVersion.setText(version);
    labelCopyright.setText(copyright);
    labelComments.setText(comments);
        
    buttonOK.setText("Ok");
    buttonOK.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        buttonOK_actionPerformed(e);
      }
    });
    buttonOK.addActionListener(this);
        
    imagePanel.setLayout(new BorderLayout());
    imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    imagePanel.add(imageLabel, BorderLayout.CENTER);
	
	gridLayout1.setRows(4);
    gridLayout1.setColumns(1);
	textPanel.setLayout(gridLayout1);
    textPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));         
    textPanel.add(labelProduct, null);
    textPanel.add(labelVersion, null);
    textPanel.add(labelCopyright, null);
    textPanel.add(labelComments, null);
    
    buttonPanel.setLayout(new BorderLayout()) ;
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 80)) ;
    buttonPanel.add(buttonOK, BorderLayout.CENTER) ;
    
    mainPanel.setLayout(new BorderLayout());
    mainPanel.add(textPanel, BorderLayout.EAST) ;
	mainPanel.add(imagePanel, BorderLayout.CENTER) ;
	mainPanel.add(buttonPanel, BorderLayout.SOUTH);    	
    this.getContentPane().add(mainPanel, null);
    
  }

  /**
   * Overridden so we can exit when window is closed
   */
  protected void processWindowEvent(WindowEvent e)
  {
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      cancel();
    }
    super.processWindowEvent(e);
  }

  /**
   * Close the dialog
   */
  void cancel()
  {
    dispose();
  }

  /**
   * Close the dialog on a button event
   */
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == buttonOK)
    {
      cancel();
    }
  }

  void buttonOK_actionPerformed(ActionEvent e)
  {

  }
}