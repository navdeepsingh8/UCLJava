/**
 * Title:  class MemoListEditorReadEmptyFileTest
 * Description:  Test that the MemoListEditor read method works correctly
 * with an empty memo list.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import org.easymock.MockControl;
import java.io.File;
import java.io.FileWriter;

import junit.framework.TestCase;

public class MemoListEditorReadEmptyFileTest extends TestCase
{
  private MemoList memoList;
  private MemoListEditor editor;
  private MockControl control;
  private MemoListView mockView;
  private File inputFile;

  protected void setUp() throws Exception
  {
    inputFile = File.createTempFile("testLoad",".xml");
    FileWriter writer = new FileWriter(inputFile);
    writer.write("<MemoList></MemoList>");
    writer.close();

    memoList = new MemoList();
    Memo memo1 = new Memo();
    memo1.setTitle("OldTitle");
    memoList.add(memo1);

    control = MockControl.createControl(MemoListView.class);
    mockView = (MemoListView)control.getMock();
    mockView.setEditor(null);
    control.setDefaultVoidCallable();
    mockView.setMemoTitle(memo1.getTitle());
    control.setVoidCallable();
    mockView.getFileToLoad();
    control.setReturnValue(inputFile);
    mockView.setMemoTitle("");
    control.setVoidCallable();
    control.replay();

    editor = new MemoListEditor(memoList,mockView);
  }

  public void testReadEmptyList()
  {
    editor.load();
    control.verify();
  }
}
