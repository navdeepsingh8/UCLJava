/**
 * Title: interface MemoListView
 * Description: Methods that must be supported by any view on to a MemoList.
 * Note that a view should have no direct connection with a MemoList. Instead
 * it interacts with a MemoListEditor.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import java.io.File;

public interface MemoListView
{
  public void setEditor(MemoListEditor editor);
  public void setMemoTitle(String title);
  public File getFileToSave();
  public File getFileToLoad();
}
