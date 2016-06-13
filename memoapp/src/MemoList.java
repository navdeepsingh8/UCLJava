/**
 * Title: Class MemoList
 * Description: Manage a list of Memos.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import java.util.ArrayList;

public class MemoList
{
  private ArrayList memos = new ArrayList();

  public int size()
  {
    return memos.size();
  }

  public void add(Memo memo)
  {
    memos.add(memo);
  }

  public boolean contains(Memo memo)
  {
    return memos.contains(memo);
  }

  public Memo get(int n) throws MemoListException
  {
    if (!isValidMemo(n))
    {
      throw new MemoListException("Invalid index " + n + " size " + size());
    }
    return (Memo)memos.get(n);
  }

  private boolean isValidMemo(int n)
  {
    return (n >= 0) && (n < size());
  }
}
