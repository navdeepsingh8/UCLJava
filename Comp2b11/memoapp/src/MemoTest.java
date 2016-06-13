/**
 * Title: class MemoTest
 * Description:  Test Memo.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import junit.framework.TestCase;

public class MemoTest extends TestCase
{
  private Memo memo;
  
  public void setUp()
  {
    memo = new Memo();
    memo.setTitle("title");
  }
  
  public void testGetTitle()
  {
    assertEquals("Memo has wrong title","title",memo.getTitle());
  }
}
