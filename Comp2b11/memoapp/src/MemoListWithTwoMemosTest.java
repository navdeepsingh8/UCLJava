/**
 * Title: class MemoListWithTwoMemosTest
 * Description:  Test MemoList containing two memos.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import java.util.ArrayList;

public class MemoListWithTwoMemosTest extends MemoListTestCase
{
  private Memo memo2;
  private Memo memo3;

  protected void setUp()
  {
    super.setUp();
    memo2 = new Memo();
    memo3 = new Memo();
    list.add(memo2);
  }

  public void testSizeAfterAddTwo()
  {
    assertEquals("List should have size 2", 2, list.size());
  }
  
  public void testListContainsMemo1()
  {
    assertTrue("Should contain memo1", list.contains(memo1));
  }
  
  public void testListContainsMemo2()
  {
    assertTrue("Should contain memo2", list.contains(memo2));
  }

  public void testDoesNotContainMemo3()
  {
    assertFalse("Should not contain memo3", list.contains(memo3));
  }
  
  public void testValidGet()
  {
    assertGet(0,memo1);
    assertGet(1,memo2);
  }
}
