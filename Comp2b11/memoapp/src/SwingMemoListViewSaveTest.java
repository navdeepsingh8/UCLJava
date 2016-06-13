/**
 * Title:  class SwingMemoListViewSaveTest
 * Description:  Test Save button works.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JFileChooserOperator;
import junit.framework.TestCase;
import java.io.File;

public class SwingMemoListViewSaveTest extends TestCase
{
  private JFrameOperator mainWindow;
  private JButtonOperator saveButton;
  private FakeMemoListEditor fakeEditor;

  private static class FakeMemoListEditor extends MemoListEditor
  {
    private boolean writeCalled = false;

    public boolean isWriteCalled()
    {
      return writeCalled;
    }

    public FakeMemoListEditor(MemoList list, MemoListView view)
    {
      super(list,view);
    }

    public void writeToFile(File outputFile)
    {
      writeCalled = true;
    }
  }

  protected void setUp()
  {
    SwingMemoListView.start();
    MemoList memoList = new MemoList();

    mainWindow = new JFrameOperator("Memo List");
    fakeEditor =
      new FakeMemoListEditor(memoList,
                         (SwingMemoListView)mainWindow.getWindow());
  }

  protected void tearDown()
  {
    mainWindow.dispose();
  }

  public void testSave() throws Exception
  {
    File outputFile = File.createTempFile("MemoApp",".xml");

    saveButton = new JButtonOperator(mainWindow,"Save...");
    saveButton.pushNoBlock();

    JFileChooserOperator fileChooser = new JFileChooserOperator();
    fileChooser.setSelectedFile(outputFile);
    JButtonOperator chooserSaveButton = new JButtonOperator(fileChooser,"Save");
    chooserSaveButton.push();

    assertTrue("Save not performed",fakeEditor.isWriteCalled());
  }

  public void testSaveCancel() throws Exception
  {
    File outputFile = File.createTempFile("MemoApp",".xml");

    saveButton = new JButtonOperator(mainWindow,"Save...");
    saveButton.pushNoBlock();

    JFileChooserOperator fileChooser = new JFileChooserOperator();
    fileChooser.setSelectedFile(outputFile);
    JButtonOperator chooserSaveButton = new JButtonOperator(fileChooser,"Cancel");
    chooserSaveButton.push();

    assertFalse("Write to file should not happened",fakeEditor.isWriteCalled());
  }
}