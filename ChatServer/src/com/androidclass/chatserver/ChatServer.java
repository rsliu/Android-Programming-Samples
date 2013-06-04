package com.androidclass.chatserver;

//
// Source: http://homepages.ius.edu/RWISMAN/C490/html/chatserver4chatserver5.htm
//

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer {
	public static void main(String args[]) throws Exception {
		// Construct a ServerSocket object that listens on port 8888
		// Note: Mac OS only accepts ports higher than 1023
		ServerSocket conn = new ServerSocket(8888);
		
		while(true) {
			new Server(conn.accept());
		}
	}
}

class Server implements Runnable {
	Socket s;
	static Vector<PrintStream> outVector = new Vector<PrintStream>();
	
	Server(Socket s) {
		this.s = s;
		new Thread(this).start(); // Start() invokes run
	}
	
	public void run() {
		String from;
		BufferedReader in = null;
		PrintStream out = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintStream(s.getOutputStream());
			
			// Serialize access to outVector object, allowing only one thread
			// to execute any protected code at a time.
			synchronized(outVector) {
				outVector.addElement(out);
			}
			
			System.out.println("Connected");
			while ((from = in.readLine()) != null && !from.equals("")) {
				System.out.println(from);
				
				// Send to all connections
				for(int i = 0; i < outVector.size(); i++) {
					((PrintStream)outVector.elementAt(i)).print(from + "\r\n");
				}
			}
			
			s.close();
		} catch (IOException e) {
			
		}
		System.out.println("Disconnected");
		
		synchronized(outVector) {
			outVector.removeElement(out);
		}
	}
}