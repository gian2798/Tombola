package bordin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadServer extends Thread{
	static Server s;
	static ServerSocket ss;
	static ArrayList<PrintWriter> clientlist=new ArrayList<PrintWriter>();
	static Socket socketClient;
	public ThreadServer(Server ser){
		s=ser;
	}
	
	private static class ThreadConnessioni extends Thread{
		
		private Socket client;
		
		public ThreadConnessioni(Socket socketClient) {
			// TODO Auto-generated constructor stub
			client = socketClient;
		}

		public void run(){
			super.run();
			/*
			 * resta in attesa dei messaggi client
			 * riceve il messaggio
			 * manda messaggio a tutti i client
			 * 
			 */
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream(),true);
				
				while(true){
					String numero = in.readLine();
					//manda il messaggio a tutti
					for (PrintWriter printWriter : clientlist) {
						printWriter.println(numero);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void run(){
		
		try {
			ss=new ServerSocket();
			while(true){
				
				socketClient=ss.accept();
				PrintWriter out =new PrintWriter(socketClient.getOutputStream(),true);
				clientlist.add(out);
				ThreadConnessioni tc = new ThreadConnessioni(socketClient);
				//thread resta in attesa
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
