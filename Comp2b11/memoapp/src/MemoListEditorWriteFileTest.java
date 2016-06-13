/**
 * Title:  class MemoListEditorWriteFileTest
 * Description:  Test that the MemoListEditor write method works correctly.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */
import org.custommonkey.xmlunit.*;
import org.easymock.MockControl;

import java.io.StringReader;
import java.io.File;
import java.io.FileReader;

public class MemoListEditorWriteFileTest extends XMLTestCase
{
  private MemoList memoList;
  private MemoListEditor editor;
  private MockControl control;
  private MemoListView mockView;
  private File outputFile;

  protected void setUp() throws Exception
  {
    outputFile = File.createTempFile("testSave",".xml");
    memoList = new MemoList();
    Memo memo1 = new Memo();
    memo1.setTitle("Title1");
    memoList.add(memo1);
    Memo memo2 = new Memo();
    memo2.setTitle("Title2");
    memoList.add(memo2);

    control = MockControl.createControl(MemoListView.class);
    mockView = (MemoListView)control.getMock();
    mockView.setEditor(null);
    control.setDefaultVoidCallable();
    mockView.setMemoTitle(memo1.getTitle());
    control.setVoidCallable();
    mockView.setMemoTitle(memo2.getTitle());
    control.setVoidCallable();
    mockView.getFileToSave();
    control.setReturnValue(outputFile);
    control.replay();
    editor = new MemoListEditor(memoList,mockView);
  }

  public void testWriteToFile() throws Exception
  {
    String expected =
      "<MemoList>" +
        "<Memo>" +
          "<Title>" +
             "Title1" +
          "</Title>" +
        "</Memo>" +
        "<Memo>" +
          "<Title>" +
             "Title2" +
          "</Title>" +
        "</Memo>" +
      "</MemoList>";

    editor.save();
    assertXMLEqual("File content not correct",
      new StringReader(expected),
      new FileReader(outputFile));
  }
}
