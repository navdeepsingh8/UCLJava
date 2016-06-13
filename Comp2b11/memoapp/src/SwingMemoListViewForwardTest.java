/**
 * Title:  class SwingMemoListViewBackwardTest
 * Description:  Test backward forward works.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import junit.framework.TestCase;

public class SwingMemoListViewForwardTest extends TestCase
{
  private JFrameOperator mainWindow;
  private MemoList memoList;
  private Memo memo1;
  private Memo memo2;
  private JButtonOperator forwardButton;

  protected void setUp()
  {
    SwingMemoListView.start();
    memoList = new MemoList();
    memo1 = new Memo();
    memo1.setTitle("Title1");
    memoList.add(memo1);
    memo2 = new Memo();
    memo2.setTitle("Title2");
    memoList.add(memo2);

    mainWindow = new JFrameOperator("Memo List");
    MemoListEditor editor =
      new MemoListEditor(memoList,
                         (SwingMemoListView)mainWindow.getWindow());
    forwardButton = new JButtonOperator(mainWindow,"->");
    forwardButton.doClick();
  }
  
  protected void tearDown()
  {
    mainWindow.dispose();
  }

  public void testForward()
  {
    JTextFieldOperator textField = new JTextFieldOperator(mainWindow);
    String text = textField.getText();
    assertEquals("Wrong memo title displayed",memo2.getTitle(),text);    
  }

  public void testForwardAtEndOfList()
  {
    forwardButton.doClick();
    JTextFieldOperator textField = new JTextFieldOperator(mainWindow);
    String text = textField.getText();
    assertEquals("Wrong memo title displayed",memo2.getTitle(),text);
  }
}
