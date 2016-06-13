/**
 * Title: class MemoListWithOneMemoTest
 * Description:  Test MemoList containing one memo.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */


import java.util.ArrayList;

public class MemoListWithOneMemoTest extends MemoListTestCase
{
  protected void setUp()
  {
    super.setUp();
  }

  public void testSize()
  {
    assertEquals("List should have size 1", 1, list.size());
  }
  
  public void testValidGet()
  {
    assertGet(0,memo1);
  }
}
