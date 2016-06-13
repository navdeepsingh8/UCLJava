/**
 * Title:  class SwingMemoListViewInitialisationTest
 * Description:  Test Swing view is correctly initialised.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

import junit.framework.TestCase;

public class SwingMemoListViewInitialisationTest extends TestCase
{
  private JFrameOperator mainWindow;
  private MemoList memoList;
  private Memo memo1;

  protected void setUp()
  {
    SwingMemoListView.start();
    memoList = new MemoList();
    memo1 = new Memo();
    memo1.setTitle("title");
    memoList.add(memo1);
  }

  protected void tearDown()
  {
    mainWindow.dispose();
  }

  public void testInitialiasation()
  {
    mainWindow = new JFrameOperator("Memo List");
    MemoListEditor editor =
      new MemoListEditor(memoList,
                         (SwingMemoListView)mainWindow.getWindow());
    JTextFieldOperator textField = new JTextFieldOperator(mainWindow);
    String text = textField.getText();
    assertEquals("Wrong memo title displayed",memo1.getTitle(),text);
  }
}
