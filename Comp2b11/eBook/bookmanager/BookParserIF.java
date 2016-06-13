/**
 * Title:        EBook Application
 * Description:  Interface for providing access to a book parser.
 * Copyright:    Copyright (c) 2001
 * Company:      Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 1.0 01/10/01
 */
package bookmanager ;  

public interface BookParserIF
{
  /**
   * Parse given book file, storing result into model.
   * @param file File object referencing file to parse.
   * @ return name of book parsed, or null if parsing failed.
   */
  public String parse(String file) ;
}