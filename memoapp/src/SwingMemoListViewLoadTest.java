/**
 * Title:  class SwingMemoListViewLoadTest
 * Description:  Test Load button works.
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

public class SwingMemoListViewLoadTest extends TestCase
{
  private JFrameOperator mainWindow;
  private JButtonOperator loadButton;
  private FakeMemoListEditor fakeEditor;
  private File inputFile;

  private static class FakeMemoListEditor extends MemoListEditor
  {
    private boolean loadCalled = false;

    public boolean isLoadCalled()
    {
      return loadCalled;
    }

    public FakeMemoListEditor(MemoList list, MemoListView view)
    {
      super(list,view);
    }

    public void readFromFile(File inputFile)
    {
      loadCalled = true;
    }
  }

  protected void setUp() throws Exception
  {
    SwingMemoListView.start();
    MemoList memoList = new MemoList();
    inputFile = File.createTempFile("MemoApp",".xml");

    mainWindow = new JFrameOperator("Memo List");
    fakeEditor =
      new FakeMemoListEditor(memoList,
                         (SwingMemoListView)mainWindow.getWindow());
  }

  protected void tearDown()
  {
    mainWindow.dispose();
    inputFile.delete();
  }

  public void testLoad() throws Exception
  {
    loadButton = new JButtonOperator(mainWindow,"Load...");
    loadButton.pushNoBlock();

    JFileChooserOperator fileChooser = new JFileChooserOperator();
    fileChooser.setCurrentDirectory(inputFile.getAbsoluteFile());
    fileChooser.setSelectedFile(inputFile);
    JButtonOperator chooserLoadButton = new JButtonOperator(fileChooser,"Open");
    chooserLoadButton.push();

    assertTrue("Load not performed",fakeEditor.isLoadCalled());
  }

  public void testLoadCancel() throws Exception
  {


    loadButton = new JButtonOperator(mainWindow,"Load...");
    loadButton.pushNoBlock();

    JFileChooserOperator fileChooser = new JFileChooserOperator();
    fileChooser.setCurrentDirectory(inputFile.getAbsoluteFile());
    fileChooser.setSelectedFile(inputFile);
    JButtonOperator chooserLoadButton = new JButtonOperator(fileChooser,"Cancel");
    chooserLoadButton.push();

    assertFalse("Load from file should not happened",fakeEditor.isLoadCalled());
  }
}
