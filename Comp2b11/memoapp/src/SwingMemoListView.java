/**
 * Title: class SwingMemoListView
 * Description: Swing based implementation of a MemoListView. This class
 * constructs a real GUI, connect events (e.g., button clicks) to a
 * MemoListEditor.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */
 
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.*;

public class SwingMemoListView extends JFrame implements MemoListView
{
  private JTextField titleField;
  private JPanel buttonPanel;
  private MemoListEditor editor;
  private JFileChooser fileChooser;

  public SwingMemoListView()
  {
    createGUI();
  }

  public void setEditor(MemoListEditor editor)
  {
    this.editor = editor;
  }
  
  public void setMemoTitle(String title)
  {
    titleField.setText(title);    
  }

  public File getFileToSave()
  {
    int returnValue = fileChooser.showSaveDialog(this);
    if (returnValue == JFileChooser.APPROVE_OPTION)
    {
      return fileChooser.getSelectedFile();
    }
    else
    {
      return null;
    }
  }

  public File getFileToLoad()
  {
    int returnValue = fileChooser.showOpenDialog(this);
    if (returnValue == JFileChooser.APPROVE_OPTION)
    {
      return fileChooser.getSelectedFile();
    }
    else
    {
      return null;
    }
  }

  private void createGUI()
  {
    Container content = getContentPane();
    this.setTitle("Memo List");
    content.setLayout(new BorderLayout());
    addTitleField();
    createButtonPanel();
    fileChooser = new JFileChooser();
    content.add(buttonPanel,BorderLayout.SOUTH);
    pack();
  }

  private void addTitleField()
  {
    titleField = new JTextField();
    getContentPane().add(titleField);
    setMemoTitle("");
  }

  private void createButtonPanel()
  {
    buttonPanel = new JPanel(new FlowLayout());
    addBackButton(buttonPanel);
    addLoadButton(buttonPanel);
    addSaveButton(buttonPanel);
    addForwardButton(buttonPanel);
  }

  private void addForwardButton(JPanel panel)
  {
    JButton forwardButton = new JButton("->");
    forwardButton.addActionListener(
      new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          editor.forward();
        }
      });
    panel.add(forwardButton);
  }

  private void addLoadButton(JPanel panel)
  {
    JButton loadButton = new JButton("Load...");
    loadButton.addActionListener(
      new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          editor.load();
        }
      });
    panel.add(loadButton);
  }

  private void addSaveButton(JPanel panel)
  {
    JButton saveButton = new JButton("Save...");
    saveButton.addActionListener(
      new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          editor.save();
        }
      });
    panel.add(saveButton);
  }

  private void addBackButton(JPanel panel)
  {
    JButton backButton = new JButton("<-");
    backButton.addActionListener(
      new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          editor.backward();
        }
      });
    panel.add(backButton);
  }

  public static void start()
  {
    SwingMemoListView window = new SwingMemoListView();
    window.show();
  }
}
