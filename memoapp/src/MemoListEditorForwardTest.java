/**
 * Title:  class MemoListEditorForwardTest
 * Description:  Test the MemoListEditor forward method.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */
 
import junit.framework.TestCase;
import org.easymock.MockControl;

public class MemoListEditorForwardTest extends TestCase
{
  private MockControl control;
  private MemoListView mockView;
  private MemoList memoList;
  private Memo memo1;
  private Memo memo2;
  private MemoListEditor editor;

  protected void setUp()
  {
    memoList = new MemoList();
    memo1 = new Memo();
    memo2 = new Memo();
    memo1.setTitle("Title1");
    memo2.setTitle("Title2");
    memoList.add(memo1);
    memoList.add(memo2);

    control = MockControl.createControl(MemoListView.class);
    mockView = (MemoListView)control.getMock();
    mockView.setEditor(null);
    control.setDefaultVoidCallable();
    mockView.setMemoTitle(memo1.getTitle());
    control.setVoidCallable();
    mockView.setMemoTitle(memo2.getTitle());
    control.setVoidCallable();
    control.replay();
    editor = new MemoListEditor(memoList,mockView);
  }

  public void testForward()
  {
    editor.forward();
    editor.forward();
    control.verify();
  }
}
