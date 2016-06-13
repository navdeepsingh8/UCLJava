/**
 * Title: class MemoListEmptyTest
 * Description:  Test that getting a memo from an empty memo list throws
 * an exception correctly.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import junit.framework.TestCase;

public class MemoListEmptyTest extends TestCase
{
  private MemoList list;

  protected void setUp()
  {
    list = new MemoList();
  }

  public void testSize()
  {
    assertEquals("Empty list should have size 0", 0, list.size());
  }
  
  public void testGet()
  {
    try
    {
      list.get(0);
    }
    catch (MemoListException e)
    {
      return;
    }
    fail("Attempt to get non-existant memo worked");
  }
}
