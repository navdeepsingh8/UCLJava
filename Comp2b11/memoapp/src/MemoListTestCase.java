/**
 * Title: class MemoListTestCase
 * Description:  Common tests for subclasses that test MemoList.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import junit.framework.TestCase;

public abstract class MemoListTestCase extends TestCase
{
  protected MemoList list;
  protected Memo memo1;  
  
  protected void setUp()
  {
    list = new MemoList();
    memo1 = new Memo();
    list.add(memo1);
  }

  public void testGetLessThanZero()
  {
    try
    {
      list.get(-1);
    }
    catch (MemoListException e)
    {
      return;
    }
    fail("Attempt to get non-existant memo worked");
  }

  public void testGetGreaterThanSize()
  {
    try
    {
      list.get(list.size());
    }
    catch (MemoListException e)
    {
      return;
    }
    fail("Attempt to get non-existant memo worked");
  }

  protected void assertGet(int position, Memo value)
  {
    Memo memo = null;
    try
    {
      memo = list.get(position);
    }
    catch (MemoListException e)
    {
      fail("Attempt to get non-existant memo worked");
    }
    assertEquals("Wrong Memo",value,memo);
  }
}
