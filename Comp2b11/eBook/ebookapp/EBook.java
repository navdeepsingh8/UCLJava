/**
 * Title:        EBook Application
 * Description:  Class used to initialise the application. Contains the
 * application main method.
 * Copyright:    Copyright (c) 2001
 * Company:      Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 1.0
 */

package ebookapp ;  
  

import javax.swing.UIManager ;
import java.awt.* ;
import bookmanager.* ;
import ebookapp.* ;

public class EBook
{
  boolean packFrame = false;

  /**Construct the application.
   * This class has the responsibility for creating the key concrete objects,
   * such as the parser, to be used by the rest of the application.
   * This is the only class that should name concrete classes. All other
   * classes should make use of the interfaces implemented by the classes.
   * This allows different concrete implementations to be used.
   */
  public EBook()
  {
    // First create core gui and objects.
    AppFrame frame = new AppFrame("EBook") ;
    BookParserIF parser = null ;
    
    BookDataStructure dataStructure = new BookDataStructure() ;
    try
    {
      parser = new BookParser(dataStructure) ;
    }
    catch (Exception e)
    {
      initialisationError("Unable to create an XML parser") ;
    }

    Model model = new BookModel(parser,dataStructure,new JTextAreaWriter(frame.getTextArea())) ;
    frame.setModel(model) ;

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    frame.setSize(600,400) ;
    if (packFrame)
    {
      frame.pack() ;
    }
    else
    {
      frame.validate() ;
    }
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height)
    {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width)
    {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2,
      (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }

  /**
   * Main method
   */
  public static void main(String[] args)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()) ;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    new EBook();
  }

  /**
   * Called to report a fatal error due to the initialisation of a core
   * object failing. As there may be no GUI, the error is reported to
   * System.err. The application is terminated.
   * @param msg An error message to be reported back to the user.
   */
  private void initialisationError(String msg)
  {
    System.err.print("Initialisation error:") ;
    System.err.println(msg) ;
    System.err.println("The application has been terminated") ;
    System.exit(0) ;
  }
}