/**
 * Title:  class MemoApp
 * Description:  Provide a main method to run MemoApp from the command line.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

public class MemoApp
{
  public static void main(String[] args)
  {
    MemoList memoList = new MemoList();
    SwingMemoListView view = new SwingMemoListView();
    MemoListEditor editor = new MemoListEditor(memoList,view);
    view.show();
  }
}
