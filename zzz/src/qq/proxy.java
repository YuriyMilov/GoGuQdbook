package qq;


import java.net.*;
import java.io.*;

public class proxy {

	public static void main(String args[]) throws Exception {

		System.out.println("ServerSocket(8080) is waiting ...");
		Socket socket = (new ServerSocket(8080)).accept();
		DataInputStream get_in = new DataInputStream(socket
				.getInputStream());
		
		StringBuffer sb = new StringBuffer();
		int i=11111;
		while (i-- >0) {
			char cc=(char)get_in.read();
			sb.append(cc);	
			System.out.println((int)cc);
		}		
		System.out.println(sb.toString());
	}
}
