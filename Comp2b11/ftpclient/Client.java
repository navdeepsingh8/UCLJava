package ftpclient ;

import java.net.* ;
import java.io.* ;
import java.util.* ;

public class Client {
	
	private Socket clientSock = null ;
	private DataInputStream in = null ;
	private BufferedReader buffIn = null ;
	private PrintStream out = null ;
	
	private Socket dataSock = null ;
	private BufferedInputStream dataIn = null ;
	private BufferedReader dataBuffIn = null ;
	
	private boolean textFlag = false ;
	private String response = "" ;
	private int inputCode = 0 ;
	
	public class ServerResponseException extends Exception {
		public ServerResponseException() {
			super("Unexpected server response") ;
		}
		
		public ServerResponseException(final String s) {
			super("Server Response Exception: ".concat(s)) ;
		}
	}
	
	public Client(String name, int port)
	{
		try {
			clientSock = new Socket(name, port) ;
			in = new DataInputStream(clientSock.getInputStream());
			buffIn = new BufferedReader(new InputStreamReader(in));
   			out = new PrintStream(clientSock.getOutputStream());
		} catch(IOException e) {
			System.out.println(e) ;
		}
	}
	
	public void finalize()
	{
		try {
			clientSock.close() ;
			dataSock.close() ;
		} catch (IOException i) {}
	}
	
	public String getResponse()
	{
		try {
			response = buffIn.readLine() ;				
		} catch(IOException i) {
			System.out.println("Error reading server reponse" + i) ;
		}
		return response ;
	}
	
	public String passResponse()
	{
		return response ;
	}
	
	public void getCode(String response) throws ServerResponseException
	{
		try {
			try {
				inputCode = Integer.parseInt(response.substring(0,3)) ;
			} catch (StringIndexOutOfBoundsException s) {
				response = getResponse() ;
				System.out.println(response) ;
				getCode(response) ;
				return ;
			}
		} catch (NumberFormatException n) {
			if (textFlag) {
				response = getResponse() ;
				System.out.println(response) ;
				getCode(response) ;
				return ;
			} else {
				throw new ServerResponseException(response) ;
			}
		}
		if (response.startsWith(" ",3)) {
			textFlag = false ;
			return ;
		} else {
			textFlag = true ;
			response = getResponse() ;
			System.out.println(response) ;
			getCode(response) ;
			return ;
		}
	}
	
	public void logIn(String user, String pass) throws ServerResponseException
	{
		if (user.equals("")) user = "anonymous" ;
		if (pass.equals("")) pass = "no@email.add" ;
		while(inputCode != 999) {
			getResponse() ;
			System.out.println(response) ;
			getCode(response) ;
			switch (inputCode) {
				case 220: out.println("USER " + user) ; break ;
				case 331: 
				case 332: out.println("PASS " + pass) ; break ;
				case 530: 
				case 503: throw new ServerResponseException(response) ;
				case 230: inputCode = 999 ; break ;
			}
		}
	}
	
	public void abort() throws ServerResponseException
	{
		out.println("ABOR") ;
		getResponse() ;
		getCode(response) ;
		if (inputCode != 225 && inputCode != 226) {
			throw new ServerResponseException(response) ;
		}
	}
	
	public void quit() throws ServerResponseException
	{
		abort() ;
		out.println("QUIT") ;
		getResponse() ;
		getCode(response) ;
		if (inputCode != 221) {
			throw new ServerResponseException(response) ;
		}	
	}
	
	public void setBinary(boolean binary) throws ServerResponseException
	{
		if (binary) {
			out.println("TYPE I") ;
		} else {
			out.println("TYPE A") ;
		}
		
		getResponse() ;
		System.out.println(response) ;
		getCode(response) ;
		if (inputCode != 200) {
			throw new ServerResponseException(response) ;
		}
	}
	
	public void changeDir(String dir) throws ServerResponseException
	{
		out.println("CWD /" + dir) ;
		response = getResponse() ;
		System.out.println(response) ;
		getCode(response) ;
		if (inputCode != 200 && inputCode != 250) {
			throw new ServerResponseException(response) ;
		}
	}
	
	public String currentDir() throws ServerResponseException
	{
		out.println("PWD") ;
		response = getResponse() ;
		System.out.println(response) ;
		getCode(response) ;
		if (inputCode == 257) {
			return response.substring(4,response.length()) ;
		} else {
			throw new ServerResponseException(response) ;
		}		
	}
	
	public String changeDirUp() throws ServerResponseException
	{
		out.println("CDUP") ;
		response = getResponse() ;
		System.out.println(response) ;
		getCode(response) ;
		if (inputCode != 200 || inputCode != 250) {
			throw new ServerResponseException(response) ;
		}
		return currentDir() ;
	}
	
	public void dataConnection() throws ServerResponseException
	{
		out.println("PASV") ;
		response = getResponse() ;
		System.out.println(response) ;
		getCode(response) ;
		if (inputCode == 227) {
			
			int startPos = 0 ;
			int endPos = 0 ;
			int pointer = 4 ;
			while (endPos == 0) {
				while (startPos == 0) {
					if (Character.isDigit(response.charAt(pointer))) {
						startPos = pointer ;
					} else {
						pointer++ ;
					}
				}
				if (!Character.isDigit(response.charAt(pointer)) && response.charAt(pointer) != ',') {
					endPos = pointer ;
				} else {
					pointer++ ;
				}
			}
			
			StringTokenizer st = new StringTokenizer(response.substring(startPos, endPos), ",") ;
			String hostname = "" ;
			int port = 0 ;
			if (st.countTokens() != 6) {
				throw new ServerResponseException(response) ;
			} else {
				for (int i = 0; i < 3; i++) {
					hostname = hostname.concat(st.nextToken()).concat(".") ;
				}
				hostname = hostname.concat(st.nextToken()) ;
				port = Integer.parseInt(st.nextToken())*256 + Integer.parseInt(st.nextToken()) ;
			}
			
			try {
				dataSock = new Socket(hostname, port) ;
				dataIn = new BufferedInputStream(dataSock.getInputStream()) ;
				dataBuffIn = new BufferedReader(new InputStreamReader(dataIn)) ;
			} catch (IOException e) {
				System.out.println(e) ;
			}
			
		} else {
			throw new ServerResponseException(response) ;
		}
	}
	
	public ArrayList listDir() throws ServerResponseException
	{
		out.println("LIST") ;
		response = getResponse() ;
		System.out.println(response) ;
		getCode(response) ;
		switch (inputCode) {
			case 450 :
			case 550 : throw new ServerResponseException(response) ;
		}
		if (inputCode == 150 || inputCode == 125) {
			response = getResponse() ;
			System.out.println(response) ;
			int inputCode = Integer.parseInt(response.substring(0,3)) ;
			switch (inputCode) {
				case 425:
				case 426:
				case 451: throw new ServerResponseException(response) ;
				case 226: {
					ArrayList dir = new ArrayList() ;
					try {
						String dirLine = dataBuffIn.readLine() ;
						while (dataBuffIn.ready() && dirLine != null) {
							dir.add(dirLine) ;
							dirLine = dataBuffIn.readLine() ;
						}
					} catch (IOException e) {}
					getCode(response) ;					
					return dir ;
				}
			}
		}
		throw new ServerResponseException(response) ;					
	}
	
	public void retrieve(String file) throws ServerResponseException
	{
		out.println("RETR "+file) ;
		String response = getResponse() ;
		System.out.println(response) ;
		int inputCode = Integer.parseInt(response.substring(0,3)) ;
		switch (inputCode) {
			case 450 :
			case 550 : throw new ServerResponseException(response) ;
		}
		if (inputCode == 150 || inputCode == 125) {
			response = getResponse() ;
			System.out.println(response) ;
			inputCode = Integer.parseInt(response.substring(0,3)) ;
			switch (inputCode) {
				case 425: throw new ServerResponseException() ;
				case 426:
				case 451: throw new ServerResponseException(response) ;
				case 226: {
					while (!response.startsWith(" ", 3)) {
						response = getResponse() ;
						System.out.println(response) ;
						inputCode = Integer.parseInt(response.substring(0,3)) ;
					}
				}
			}
		}		
	}
	public static void main(String[] args)
	{
		if (args.length > 1) {
			String name = args[0] ;
			String user = null ;
			String pass = null ;
			if (args.length > 2) {
				user = args[1] ;
				pass = args[2] ;
			} else {
				user = "anonymous" ;
				pass = "no@email.add" ;
			}
			Integer port = new Integer(21) ;
			if (args.length > 3) {
				port = new Integer(args[3]) ;
			}
			Client client = new Client(name, port.intValue()) ;
			try {
				client.logIn(user, pass) ;
				client.currentDir() ;
				client.dataConnection() ;
				ArrayList dir = client.listDir() ;
				ListIterator dirIterator = dir.listIterator() ;
				while (dirIterator.hasNext()) {
					System.out.println(dirIterator.next()) ;
				}
			} catch (ServerResponseException e) {
				System.out.println(e.getMessage()) ;
			}
		} else {
			System.out.println("Incorrect parameters. Correct usage: Client hostname <user> <password> <port>") ;
		}
	}
}