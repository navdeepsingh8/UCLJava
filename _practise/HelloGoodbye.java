import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
public class HelloGoodbye extends JFrame implements ActionListener
{
	private String message = "Number of Clicks : " ;
	private JPanel counterPanel ;
	private JButton counterButton ;
	private JLabel counterLabel ;
	private JSlider counterSlider ;
	private int numClicks = 0 ;
	
	public HelloGoodbye()
	{
		super("HelloGoodbye") ;
		
		counterSlider = new JSlider(SwingConstants.VERTICAL, 0, 100, 100) ;
		//counterButton.addActionListener(this) ;
		
		counterLabel  = new JLabel(message + "0   ", SwingConstants.CENTER) ;
		
		counterButton = new JButton("Shock!") ;
		counterButton.setMnemonic(KeyEvent.VK_S) ;
		counterButton.addActionListener(this) ;
		
		counterPanel = new JPanel() ;
		counterPanel.setBorder(BorderFactory.createEmptyBorder(20,60,20,60)) ;
		counterPanel.setLayout(new BorderLayout()) ;
		counterPanel.add(counterLabel, BorderLayout.CENTER) ;
		counterPanel.add(counterButton, BorderLayout.SOUTH) ;
		counterPanel.add(counterSlider, BorderLayout.EAST) ;
		getContentPane().add(counterPanel) ;
		addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent e) {
								System.exit(0) ;
							}
						}) ;
		pack() ;
		setVisible(true) ;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		numClicks++ ;
		counterLabel.setText(message + numClicks) ;
		counterSlider.setValue((int)(counterSlider.getValue()*0.9)) ;
	}
	
	public static void main(String[] args)
	{
		try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }
		final HelloGoodbye app = new HelloGoodbye() ;
	}

}	
				
		