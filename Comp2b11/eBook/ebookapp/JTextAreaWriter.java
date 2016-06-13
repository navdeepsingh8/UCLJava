/**
 * Title:        EBook Application
 * Description:  Provide a Writer onto a JTextArea by subclassing Writer.
 * Copyright:    Copyright (c) 2001
 * @author Navdeep Daheley
 * @version 1.0  17/12/2001
 */
package ebookapp ;

import java.io.*;
import javax.swing.JTextArea ;

public class JTextAreaWriter extends Writer
{
  private JTextArea textArea = null ;

  public JTextAreaWriter(JTextArea t)
  {
    textArea = t ;
  }

  public void close()
  {

  }

  public void flush()
  {

  }

  public void writePara(String para) throws IOException
  {
  	textArea.append(para) ;
  }
  
  public void writeHeader(String header) throws IOException
  {
  	textArea.setText(header + "\n") ;
  }
  
  public void write(char[] buf, int offset, int count)
  {
    
  }
}