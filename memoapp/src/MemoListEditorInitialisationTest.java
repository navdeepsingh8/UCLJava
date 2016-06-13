/**
 * Title:  class MemoListEditorInitialisationTest
 * Description:  Test the creation of a MemoListEditor with a non-empty
 * MemoList does the correct initialisation.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import org.easymock.MockControl;
import junit.framework.TestCase;

public class MemoListEditorInitialisationTest extends TestCase
{
  private MockControl control;
  private MemoListView mockView;
  private MemoList memoList;
  private Memo memo1;

  protected void setUp()
  {
    memoList = new MemoList();
    memo1 = new Memo();
    memo1.setTitle("Title1");
    memoList.add(memo1);

    control = MockControl.createControl(MemoListView.class);
    mockView = (MemoListView)control.getMock();
  }

  public void testInitialisation()
  {
    mockView.setEditor(null);
    control.setDefaultVoidCallable();
    mockView.setMemoTitle(memo1.getTitle());
    control.setVoidCallable();
    control.replay();
    new MemoListEditor(memoList,mockView);
    control.verify();
  }
}
