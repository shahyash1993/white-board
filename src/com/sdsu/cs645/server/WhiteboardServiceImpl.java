package com.sdsu.cs645.server;

import com.sdsu.cs645.client.WhiteboardService;
import com.sdsu.cs645.shared.FieldVerifier;
import com.sun.xml.internal.stream.util.BufferAllocator;

import jdk.jfr.events.FileWriteEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.tools.ant.taskdefs.BUnzip2;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class WhiteboardServiceImpl extends RemoteServiceServlet implements WhiteboardService {

	public String validateLogin(String name) throws IllegalArgumentException {
		if (name.equals("sp2018"))
			return "OK";
		else if (name.equals("q"))
			return "OK";
		else
			return "ERROR, Wrong Password!";
	}

	public String save(String content) throws IllegalArgumentException {
			
		String path = getServletContext().getRealPath("/");
		String fileName = path + "/data.txt";
		try {
			PrintWriter out = new PrintWriter(
			new FileWriter(fileName));
			out.println(content);
			out.close();
			return "OK";
		}
		catch(Exception  e){
			return "Error! "+e;
		}
	}

	public String load() throws IllegalArgumentException {
		
		String path = getServletContext().getRealPath("/");
		String fileName = path + "/data.txt";
		
		StringBuffer content = new StringBuffer();
		String line;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
				
			while((line= in.readLine()) != null)
				content.append(line);
			
			in.close();
		}
		catch(Exception e) {
			return "Error! "+e;
		}
		
		return content.toString();
	}
}









