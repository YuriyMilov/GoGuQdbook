package qq;


import java.net.*;
import java.io.*;

public class socket {

	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception {
		String s="GET /chat/info HTTP/1.1\r\nHost: goodgame.ru:8080\r\nConnection: keep-alive\r\nUser-Agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36\r\nOrigin: null\r\nAccept: */*\r\nAccept-Language: en-US,en;q=0.8,ru;q=0.6\r\n\r\n";
		Socket js = new Socket("goodgame.ru", 8080);
		PrintStream outs = new PrintStream(js.getOutputStream());
		DataInputStream ins = new DataInputStream(js.getInputStream());
		outs.println(s);
		outs.flush();

		// read response
		
		StringBuffer stb = new StringBuffer();
		
		//for (char ch = (char)inStream.read(); (int)ch > 0; ch =(char) inStream.read()) {
		int j=9;String ss="qq";
		//while(!ss.contains("\r\n0\r\n")){
		while(!ss.contains("\r\n0\r\n")){
		int i = ins.read();
		stb.append((char)i);
		System.out.print((char)i);
		ss=stb.toString();			
	}	
		ss=ss.substring(ss.indexOf("\r\n4f"));
		System.out.println("\r\n*********"+ss+"***********\r\n");
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
