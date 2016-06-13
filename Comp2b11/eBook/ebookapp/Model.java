/**
 * Title:        EBook Application
 * Description: Generic interface to the application model.
 * Copyright:    Copyright (c) 2001
 * Company:      Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 1.0  01/11/01
 */

package ebookapp ;  

import java.io.* ;
import java.util.List ;

public interface Model
{
  public String loadFile(File file) ;
  
  public String writeNext() ;
  
  public String writePrev() ;
  
  public String writeChap(String title, String part, String chap) ;
  
  public List treeParts(String title) ;
  
  public List treeChapters(String title, String part) ;
  
}