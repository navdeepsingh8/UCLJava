/**
 * Title: class MemoListException
 * Description: Exception class thrown by MemoList when invalid list
 * operations are attempted.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

public class MemoListException extends Exception
{
  public MemoListException()
  {
    super("MemoList exception");
  }
  
  public MemoListException(String s)
  {
    super(s);
  }

}
