package qq;


import java.net.*;
import java.io.*;

public class b82_proxy {
        @SuppressWarnings("resource")
		public static void main(String args[]) throws Exception {
        	
        	System.out.println("****** Local Proxy (8080) started listening *******");
        	
    		
			Socket socket = (new ServerSocket(8080)).accept();
    		DataInputStream get_in = new DataInputStream(socket
    				.getInputStream());    		
    		
    		//while (true){
    			StringBuffer sb = new StringBuffer();
    		
    			Socket js = new Socket("goodgame.ru", 8080);
          		BufferedOutputStream bos = new BufferedOutputStream(js
                        .getOutputStream());
      
         		
    		while (!sb.toString().contains("\r\n\r\n")) {
    			char cc=(char)get_in.read();
                 bos.write(cc);
                 sb.append(cc);
                 
   ///////////// comment
                 
    			System.out.print(cc);
   
    		}		
    		
    		PrintStream outs = new PrintStream(js.getOutputStream());
    		outs.println(sb.toString());
    		outs.flush();
    		
    		System.out.println("===  read response  ===");
    		
    		// read response
    		
    		DataInputStream ins = new DataInputStream(js.getInputStream());
    		StringBuffer stb = new StringBuffer();
    		
    	
    		int j=9;
    		//while(!ss.contains("\r\n0\r\n")){
    		while(!stb.toString().contains("\r\n0\r\n")){
    		int i = ins.read();
    		stb.append((char)i);
    
    		//comment 
    		System.out.print((char)i);
    	}	
    		//ss=ss.substring(ss.indexOf("\r\n4f"));
    		//System.out.println("\r\n*********"+ss+"***********\r\n");
    		//System.out.println("ServerSocket(8080) is waiting ...");
    		
    		
    		
    		System.out.println("77777777777777777");
    		
    		sb = new StringBuffer();
    		int i=111111;
    		while (i-- >0) {
    			char cc=(char)get_in.read();
    			sb.append(cc);	
    			System.out.println((int)cc);
    		}		
        		
    		System.out.println("88888888888");


    		//}
        }
}