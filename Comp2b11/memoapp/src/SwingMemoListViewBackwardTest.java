/**
 * Title:  class SwingMemoListViewBackwardTest
 * Description:  Test backward button works.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

import junit.framework.TestCase;
public class SwingMemoListViewBackwardTest extends TestCase
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

  public void testBack()
  {
    Memo memo2 = new Memo();
    memo2.setTitle("Title2");
    memoList.add(memo2);
    mainWindow = new JFrameOperator("Memo List");
    MemoListEditor editor =
      new MemoListEditor(memoList,
                         (SwingMemoListView)mainWindow.getWindow());
    JButtonOperator forwardButton = new JButtonOperator(mainWindow,"->");
    forwardButton.doClick();
    JButtonOperator backButton = new JButtonOperator(mainWindow,"<-");
    backButton.doClick();
    JTextFieldOperator textField = new JTextFieldOperator(mainWindow);
    String text = textField.getText();
    assertEquals("Wrong memo title displayed",memo1.getTitle(),text);
  }
}
