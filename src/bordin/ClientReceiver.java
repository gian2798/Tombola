package bordin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceiver extends Thread{
	
	private Client cs=new Client();
	private Socket s;
	
	public ClientReceiver(Client cs,Socket s) {
		// TODO Auto-generated constructor stub
		this.cs=cs;
		this.s=s;
	}
	
	public void run(){
		/*
		 * 
		 * aspetta il numero e lo manda ai client
		 * 
		 */
		while(true){
			try {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String numeroRicevuto = in.readLine();
				System.out.println("cr, ra:"+numeroRicevuto);
				cs.addNumero(numeroRicevuto);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
