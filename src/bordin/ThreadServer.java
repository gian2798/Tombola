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
	private int random;
	static ServerSocket ss;
	static ArrayList<PrintWriter> clientlist=new ArrayList<PrintWriter>();
	static Socket socketClient;
	ArrayList<Integer> numeriTabellona = new ArrayList<Integer>();
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
			ss=new ServerSocket(9999);
			while(true){
				socketClient=ss.accept();
				PrintWriter out =new PrintWriter(socketClient.getOutputStream(),true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
				clientlist.add(out);
				ThreadConnessioni tc = new ThreadConnessioni(socketClient);
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getRandom(int r){
		random=r;
		System.out.println("r:"+random);
		for (PrintWriter printWriter : clientlist) {
			printWriter.println(random);
		}
	}
}
