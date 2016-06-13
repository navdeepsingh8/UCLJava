import java.io.*;
import java.net.*;
import java.util.*;

/**
Ftp class:  by Joseph Szabo<p>

<pre>
$Id: Ftp.java,v 1.1 1996/08/22 02:55:48 jszabo Exp jszabo $
$Log: Ftp.java,v $
Revision 1.1  1996/08/22 02:55:48  jszabo
Initial revision

</pre>

<script>
<!--
document.writeln("Last Modified: " + document.lastModified); 
// --->
</script>

<p>
Here is the source (people don't find the link sometimes): <a href =
http://remus.rutgers.edu/~jszabo/myjava/Ftp.java>http://remus.rutgers.edu/~jszabo/myjava/Ftp.java</a>.<br>

A very-beta device-independent bare-bones implementation of an ftp
client, capable of regets, or resuming an interrupted download,
something no web browser can do (and http can't do).  Runs as a
text-based standalone application.  All ftpd commands that don't use a
data port work (quit is a little awkward, pressing return a few times
after quit exceptions you out of there).  Here are some useful
commands:

<pre>
user - send username
pass - send password (visibly)
cwd  - change working directory
stat - current status
rnfr - rename from
rnto - rename to
dele - delete file
mkd  - make directory
rmd  - remove directory
help - cryptic online help
pwd  - print working directory
cdup - change directory one up tree
syst - print system type
type - set type to i (image/binary) or a (ascii/text)
mdtm - show last modification time of a file
size - show size of a file<p>

site chmod - change mode on file permissions
site umask - user mask, what mode not to make new files (should be 22
    at least)
site help  - cryptic help for site commands
</pre>

Hit the end of file key to quit gracefully (control z in dos, control
d in unix).  There's no abort yet.<p>

Also these commands are supported, they all involve using an extra
data port:

<pre>
list - long listing of files
nlst - short listing
retr - retrieve file (restart supported)
stor - upload and store a file (restart in ftpd doesn't work)
stou - upload and store a file with a unique name, don't overwrite
rest - restart at byte offset to resume an interrupted download with retr
</pre>

So basically you can start like this:
<pre>
java Ftp ftp.uu.net
</pre>
login with user and pass (password is echoed),
use cwd to change directories, use list or nlst to list files, and
retr to retrieve or download a file (binary mode only).  You can use
rest right before retr to resume an interrupted download:<br>
<pre>
rest numberbytesihavealready
retr filename
</pre>

You can now upload with stor or stou.  Haven't done appe (append
localfile remotefile).<p>

Here's my email: <a href =
mailto:jszabo@remus.rutgers.edu>jszabo@remus.rutgers.edu</a>.  Here's
my homepage: <a href =
http://remus.rutgers.edu/~jszabo>http://remus.rutgers.edu/~jszabo</a>.<br>

Here is the class: <a href =
http://remus.rutgers.edu/~jszabo/myjava/Ftp.class>http://remus.rutgers.edu/~jszabo/myjava/Ftp.class</a>.<p>

The author tag  doesn't show up in javadoc 1.0.  It's right here:

@author    Joseph Szabo
*/


public class Ftp {

    // at what offset to resume a file transfer
    static long restartpoint = 0L;

    // ftpd result code prefixes
    static final int PRELIM    = 1;	 // positive preliminary
    static final int COMPLETE  = 2;	 // positive completion
    static final int CONTINUE  = 3;	 // positive intermediate
    static final int TRANSIENT = 4;	 // transient negative completion
    static final int ERROR     = 5;	 // permanent negative completion

    /** 
     * Main loop that parses commands and sends them to the
     * appropriate function.
     *
     * @param args    args[0] is the name of the host to ftp to  
     */

    public static void main(String[] args) {
	
	if(args.length != 1){
	    System.out.println("Usage: java Ftp host");
	    System.exit(1);
	}

        Socket echoSocket = null;
        PrintStream os = null;
        DataInputStream is = null;
        DataInputStream stdIn = new DataInputStream(System.in);

        try {
            echoSocket = new Socket(args[0], 21);  // ftp port
            os = new PrintStream(echoSocket.getOutputStream());
            is = new DataInputStream(echoSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: "+args[0]);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "
		+args[0]);
        }

        if (echoSocket != null && os != null && is != null) {
            try {
                String userInput;

		// get intro
		getreply(is);

		// read and write loop
                while ((userInput = stdIn.readLine()) != null) {
		    if(userInput.startsWith("list") || 
		      userInput.startsWith("nlst"))
			doDataPort(userInput, false, is, os);
		    else if(userInput.startsWith("retr"))
			doDataPort(userInput, true, is, os);
		    else if(userInput.startsWith("rest"))
			rest(userInput, is, os);
		    else if(userInput.startsWith("stor") ||
			    userInput.startsWith("stou"))
			upload(userInput, is, os);
		    else {
			os.println(userInput);

			// get server response
			getreply(is);

		    } // end else

		}  // end while userInput

		os.close();
		is.close();
                echoSocket.close();
            } catch (IOException e) {
                System.err.println("I/O failed on the connection to: "+
		    args[0]);
            }
        } // if echosocket
    } // main


/** 
 * Gets server text reply from the control port after an
 * ftp command has been entered.  It knows the last line of the
 * response because it begins with a 3 digit number and a space, (a
 * dash instead of a space would be a continuation).  Note, no special
 * IAC telnet commands are processed here.  Seems to work anyway.
 *
 * @param is    input data stream on the ftp control port socket  
 * @return      returns the first character of the last line as an
 * integer (ftpd return code)
 */

    public static int getreply(DataInputStream is){

	String sockoutput;

	// get reply (intro)
	try {
	    do {
		sockoutput = is.readLine();
		System.out.println(sockoutput);
	    } while(!(Character.isDigit(sockoutput.charAt(0)) &&
		      Character.isDigit(sockoutput.charAt(1)) &&
		      Character.isDigit(sockoutput.charAt(2)) && 
		      sockoutput.charAt(3) == ' '));
	    
        } catch (IOException e) {
	    System.out.println("Error getting reply from controlport");
	    return(0);
	}
	return(Integer.parseInt(sockoutput.substring(0, 1)));

    } // end getreply


/** 

Performs downloading commands that need a data port.  Tells server
which data port it will listen on, then sends command on control port,
then listens on data port for server response.

@param command           list, nlist, or retr plus arguments
@param saveToFile        whether to save to file or print to screen
                             from data port
@param incontrolport     input stream on the control port socket
@param outcontrolport    output stream on the control port socket.  
 */

    public static void doDataPort(String command, boolean
		     saveToFile, DataInputStream incontrolport,
		     PrintStream outcontrolport){

	ServerSocket serverSocket = null;

	try {
	    serverSocket = new ServerSocket(0);
	}
	catch (IOException e) {
	    System.out.println("Could not get port for listening:  " + 
			       serverSocket.getLocalPort() + ", " + e);
	    return;
	}

	port(serverSocket, incontrolport, outcontrolport);

	// set binary type transfer
	if(saveToFile){
	    outcontrolport.println("type i");
	    System.out.println("type i");
	    getreply(incontrolport);
	}

	// ok, send command

	
	if(restartpoint != 0){
	    // have to do right before retr
	    outcontrolport.println("rest " + restartpoint);
	    System.out.println("rest " + restartpoint);
	    getreply(incontrolport);
	}

	outcontrolport.println(command);
	System.out.println(command);
	
	int result = getreply(incontrolport);

	// guess this should be an exception if false
	if(result == PRELIM){

	    // connect to data port
	    Socket clientSocket = null;
	    try {
		clientSocket = serverSocket.accept();
	    } catch (IOException e) {
		System.out.println("Accept failed: " +
				   serverSocket.getLocalPort() + ", " + e);
	    }

	    try {
		InputStream is = clientSocket.getInputStream();

		byte b[] = new byte[1024];  // 1K blocks I guess
		int amount;

		if(saveToFile){
		    // get filename argument
		    StringTokenizer stringtokens = new
			StringTokenizer(command);
		    stringtokens.nextToken();
		    String filename = stringtokens.nextToken();

		    // open file
		    RandomAccessFile outfile = new
			RandomAccessFile(filename, "rw");

		    // do restart if desired
		    if(restartpoint != 0){

			System.out.println("seeking to " + restartpoint);
			outfile.seek(restartpoint);
			restartpoint = 0;
		    }

		    while((amount = is.read(b)) != -1){
			outfile.write(b, 0, amount);
			System.out.print("#");
		    }
		    System.out.print("\n");
		    outfile.close();
		} // end if savetofile

		else
		    while((amount = is.read(b)) != -1)
			System.out.write(b, 0, amount);  // write to screen

		getreply(incontrolport);

		is.close();

		// clean up when done
		clientSocket.close();

	    } catch (IOException e) {
		e.printStackTrace();
	    }


	} // end if PRELIM

	else{
	    System.out.println("Error calling for download");
	    try {
		serverSocket.close();
	    }
	    catch (IOException e) {
		System.out.println("Error closing server socket.");
	    }
	}
    } // end list()


/**
 * Set the restart point for the next retr.  This way you can
 * resume an interrupted upload or download just like with zmodem.
 * Actually it doesn't send the rest command until right before the
 * retr (it has to be that way), but it will remember to do it.
 *
 * @param command          The command line that begins with rest.
 * @param incontrolport    For reading from the ftp control port.
 * @param outcontrolport   For writing to the ftp control port.
 */

    public static void rest(String command, DataInputStream
			    incontrolport, PrintStream
			    outcontrolport){

	StringTokenizer stringtokens = new StringTokenizer(command);
	stringtokens.nextToken();

	// put second argument here
        restartpoint = Integer.parseInt(stringtokens.nextToken());
	System.out.println("restart noted");
    }


/** 

Upload a file in binary mode using either stor or stou.  Looks like
restart doesn't work with stores, unfortunately.  Ftpd at least on my
solaris account zeros out the old half of the file already
transferred.

@param command           stor or stou plus arguments
@param incontrolport     input stream on the control port socket
@param outcontrolport    output stream on the control port socket.  
     */

    public static boolean upload(String command, DataInputStream
		     incontrolport, PrintStream outcontrolport){

	ServerSocket serverSocket = null;

	try {
	    serverSocket = new ServerSocket(0);
	}
	catch (IOException e) {
	    System.out.println("Could not get port for listening:  " + 
			       serverSocket.getLocalPort() + ", " + e);
	    return(false);
	}

	port(serverSocket, incontrolport, outcontrolport);

	// set binary type transfer
	outcontrolport.println("type i");
	System.out.println("type i");
	getreply(incontrolport);

	// send restart if desired
	if(restartpoint != 0){
	    // have to do right before retr?
	    outcontrolport.println("rest " + restartpoint);
	    System.out.println("rest " + restartpoint);
	    getreply(incontrolport);
	}

	// ok, send command	
	outcontrolport.println(command);
	System.out.println(command);
	int result = getreply(incontrolport);

	// guess this should be an exception if false
	if(result == PRELIM){

	    // listen on data port
	    Socket clientSocket = null;
	    try {
		clientSocket = serverSocket.accept();
	    } catch (IOException e) {
		System.out.println("Accept failed: " +
				   serverSocket.getLocalPort() + ", " + e);
	    }

	    try {
		OutputStream outdataport = clientSocket.getOutputStream();

		byte b[] = new byte[1024];  // 1K blocks I guess

		// get filename argument
		StringTokenizer stringtokens = new
		    StringTokenizer(command);
		stringtokens.nextToken();
		String filename = stringtokens.nextToken();

		// open file
		RandomAccessFile infile = new
		    RandomAccessFile(filename, "r");

		// do restart if desired
		if(restartpoint != 0){

		    System.out.println("seeking to " + restartpoint);
		    infile.seek(restartpoint);
		    restartpoint = 0;
		}

		// do actual upload
		int amount;

		// *** read returns 0 at end of file, not -1 as in api

//		while((amount = infile.read(b)) != -1){

//		while((amount = infile.read(b)) != 0){

		while ((amount = infile.read(b)) > 0) {

		    outdataport.write(b, 0, amount);
		    System.out.print("#");
		}
		System.out.print("\n");

		infile.close();
		outdataport.close();

		// clean up when done
		clientSocket.close();
		serverSocket.close();

		result = getreply(incontrolport);


	    } catch (IOException e) {
		e.printStackTrace();
	    }

	    return(result == COMPLETE);
	    
	} // end if PRELIM

	else{
	    System.out.println("Error calling for download");
	    try {
		serverSocket.close();
	    }
	    catch (IOException e) {
		System.out.println("Error closing server socket.");
	    }
	    return(false);
	}

    }// end upload


    /** Get ip address and port number from serverSocket and send them
        via the port command to the ftp server, and getting a valid
        response.

@param  serverSocket     Socket to get info from.
@return                  true or false depending on success */

    public static boolean port(ServerSocket serverSocket,
			       DataInputStream incontrolport,
			       PrintStream outcontrolport)
    {
	int localport = serverSocket.getLocalPort();
	System.out.println("Will listen on port, " + localport);

	// get local ip address
	InetAddress inetaddress = serverSocket.getInetAddress();
	InetAddress localip;
	try {
	    localip = inetaddress.getLocalHost();
	} catch(UnknownHostException e) {
	    System.out.println("can't get local host");
	    return(false);
	}

	// get ip address in high byte order
	byte[] addrbytes = localip.getAddress();

	// tell server what port we are listening on
	short addrshorts[] = new short[4];

	// problem:  bytes greater than 127 are printed as negative numbers
	for(int i = 0; i <= 3; i++){
	    addrshorts[i] = addrbytes[i];
	    if(addrshorts[i] < 0)
		addrshorts[i] += 256;
	}

	outcontrolport.println("port " + addrshorts[0] + "," +
			   addrshorts[1] + "," + addrshorts[2] + "," +
			   addrshorts[3] + "," + ((localport & 0xff00) >>
			   8) + "," + (localport & 0x00ff));

	// echo for myself
	System.out.println("port " + addrshorts[0] + "," + addrshorts[1]
			   + "," + addrshorts[2] + "," + addrshorts[3] + ","
			   + ((localport & 0xff00) >> 8) + "," +
			   (localport & 0x00ff));

	int result = getreply(incontrolport);

	return(result == COMPLETE);

    }// end port


} // Class Ftp
