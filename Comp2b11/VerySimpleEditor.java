// Example Swing program to illustrate a very simple editor
// Need to import classes from all these packages.
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.io.* ;
public class VerySimpleEditor extends JFrame
{
  // Instance variable used to construct the user
  // interface.
  JPanel topPanel = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel buttonPanel = new JPanel();
  JButton loadButton = new JButton();
  JButton saveButton = new JButton();
  JPanel editorPanel = new JPanel();
  JScrollPane scroller = new JScrollPane();
  JButton cutButton = new JButton();
  JButton copyButton = new JButton();
  JButton pasteButton = new JButton();
  BorderLayout borderLayout2 = new BorderLayout();
  JTextArea editor = new JTextArea();
  GridLayout gridLayout1 = new GridLayout();
  TitledBorder titledBorder1;
  // The constructor build the window using a utility
  // method and displays it.
  public VerySimpleEditor()
  {
    try
    {
      jbInit() ;
      pack() ;
      setVisible(true) ;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  public static void main(String[] args)
  {
    VerySimpleEditor editor = new VerySimpleEditor();
  }
  // Private helper method to create GUI.
  // The interface was built using the JBuilder 3 visual
  // GUI builder (I'm allowed to :-).
  private void jbInit() throws Exception
  {
    titledBorder1 = new TitledBorder("");
    topPanel.setLayout(borderLayout1);
    this.addWindowListener(new java.awt.event.WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        this_windowClosing(e);
      }
    });
    loadButton.setText("Load...");
    loadButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        loadButton_actionPerformed(e);
      }
    });
    buttonPanel.setLayout(gridLayout1);
    saveButton.setText("Save...");
    saveButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        saveButton_actionPerformed(e);
      }
    });
    cutButton.setText("Cut");
    cutButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        cutButton_actionPerformed(e);
      }
    });
    copyButton.setText("Copy");
    copyButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        copyButton_actionPerformed(e);
      }
    });
    pasteButton.setText("Paste");
    pasteButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        pasteButton_actionPerformed(e);
      }
    });
    editorPanel.setLayout(borderLayout2);
    editor.setColumns(40);
    editor.setText("Type here");
    gridLayout1.setColumns(1);
    gridLayout1.setHgap(10);
    gridLayout1.setRows(5);
    gridLayout1.setVgap(3);
    editorPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    buttonPanel.setBorder(BorderFactory.createEtchedBorder());
    this.setTitle("Very simple editor");
    this.getContentPane().add(topPanel, BorderLayout.CENTER);
    topPanel.add(buttonPanel, BorderLayout.WEST);
    buttonPanel.add(loadButton, null);
    buttonPanel.add(saveButton, null);
    buttonPanel.add(cutButton, null);
    buttonPanel.add(copyButton, null);
    buttonPanel.add(pasteButton, null);
    topPanel.add(editorPanel, BorderLayout.CENTER);
    editorPanel.add(scroller, BorderLayout.CENTER);
    scroller.getViewport().add(editor, null);
  }
  void this_windowClosing(WindowEvent e)
  {
    System.exit(0) ;
  }
  // Display a file dialog and load a file
  void loadButton_actionPerformed(ActionEvent e)
  {
    //Create a file chooser
    JFileChooser fc = new JFileChooser() ;
    //In response to a button click:
    int returnVal = fc.showOpenDialog(this) ;
    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      File file = fc.getSelectedFile() ;
      try
      {
        editor.read(new FileReader(file),null) ;
      }
      catch (IOException exp)
      {}
    }
  }
  void saveButton_actionPerformed(ActionEvent e)
  {
    JFileChooser fc = new JFileChooser() ;
    int returnVal = fc.showSaveDialog(this) ;
    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      File file = fc.getSelectedFile() ;
      try
      {
        editor.write(new FileWriter(file)) ;
      }
      catch (IOException exp)
      {}
    }
  }
  // Cut, copy and paste are implemented using methods provided
  // by a superclass of the JTextArea class, and work with
  // the system clipboard.
  // Very useful and makes it very easy to implement cut/copy/paste!!
  //
  // The requestFocus method is used to make the JTextArea the active
  // component after a button is clicked. The component with the focus
  // receives the input events. If focus is not returned to the JTextArea
  // it remains with the button, preventing text being entered into
  // the text area until it is clicked on to regain focus.
  // Try commenting out the calls to requestFocus() to see the
  // differene in behaviour.
  void copyButton_actionPerformed(ActionEvent e)
  {
    editor.copy() ;
    editor.requestFocus() ;
  }
  void pasteButton_actionPerformed(ActionEvent e)
  {
    editor.paste() ;
    editor.requestFocus() ;
  }
  void cutButton_actionPerformed(ActionEvent e)
  {
    editor.cut() ;
    editor.requestFocus() ;
  }
}