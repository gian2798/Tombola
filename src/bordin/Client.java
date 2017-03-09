package bordin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class Client {
	static ArrayList<Integer> numeriTabella = new ArrayList<Integer>();
	static ArrayList<TableItem> controllo = new ArrayList<TableItem>();
	int numeri[];
	static Socket client;
	final private boolean[] segnati=new boolean [15]; 
	protected Shell shell;
	private Table table;
	String tipo;
	/**
	 * Launch the application.
	 * @param args
	 */
	
	private void inizializzaCartella() {
		numeri = new int[15]; 
		// Riempio il vettore con 15 numeri casuali che rispettino le regole:
		// 1. no numeri ripetuti
		// 2. max 2 numeri con la stessa decina
		final int[] decine = new int[10]; //indica quanti numeri per ogni decina
		for (int i=0; i<15; i++) {
			// Genero un numero casuale tra 1 e 90
			final int n = Utility.generaCasuale(1, 90);
			// Decina attuale
			final int d = n==90? 8 : n/10; //il 90 va nella colonna degli 80

			// Se il numero casuale generato è già presente oppure se ci sono già due
			// numeri con la stessa decina, ripeto il calcolo dell'elemento i-esimo
			if (decine[d] >= 2 || Utility.indexOf(n, numeri, i) >= 0) {
				i--;
				continue;
			} else {
				numeri[i] = n;
				decine[d]++;
			}
		}

		// Ordina il vettore finale
		Arrays.sort(numeri);

		// Permuta per ottenere le righe finali (un elemento ogni tre nel vettore ordinato)
		int tmp = numeri[1];
		numeri[1] = numeri[3];
		numeri[3] = numeri[9];
		numeri[9] = numeri[13];
		numeri[13] = numeri[11];
		numeri[11] = numeri[5];
		numeri[5] = numeri[2];
		numeri[2] = numeri[6];
		numeri[6] = numeri[4];
		numeri[4] = numeri[12];
		numeri[12] = numeri[8];
		numeri[8] = numeri[10];
		numeri[10] = numeri[5];
		numeri[5] = tmp;
		
		// Scambia (in verticale) i numeri della stessa colonna se non sono in ordine tra loro
		for (int i=0; i<15; i++) {
			final int n = numeri[i];
			final int d = n/10;
			for (int j=i; j<15; j++) {
				final int n2 = numeri[j];
				final int d2 = n2/10;
				
				if (d == d2 && n>n2) { // d==d2: stessa colonna, n>n2 ordine invertito
					final int temp = numeri[i];
					numeri[i] = numeri[j];
					numeri[j] = temp;
				}
			}
		}
		stampa();
	}
	
	// Rappresentazione testuale
	public void stampa() {
		final String spacer = "   ";
		for (int r=0; r<3; r++) {
			String output = "";
			int d = 0;
			for (int c=0; c<5; c++) {
				int index = r*5+c;
				int num = numeri[index];

				// spazi per i numeri mancanti (per incolonnare i numeri nella giusta decina)
				int _d = (int)((double)num/10.0);
				if (num == 90) // il 90 va nella colonna degli 80
					_d = 8;
				for (int i=1; i<_d-d+(c==0?1:0); i++)
					output += spacer+"   ";
				d = _d;

				
				// stampa numero
				int ind=0,contatore=0;
				for (int i : numeri) {
					if(ind>4){
						ind=0;
						contatore++;
					}
					if(i<10){
						ind++;
						controllo.get(contatore).setText(0,i+"");
					}
					if(i>=10 && i<20){
						ind++;
						controllo.get(contatore).setText(1,i+"");
					
					}
					if(i>=20 &&i<30){
						ind++;
						controllo.get(contatore).setText(2,i+"");
					}
					if(i>=30 &&i<40){
						ind++;
						controllo.get(contatore).setText(3,i+"");
					
					}
					if(i>=40 && i<50){
						ind++;
						controllo.get(contatore).setText(4,i+"");
					
					}
					if(i>=50 && i<60){
						ind++;
						controllo.get(contatore).setText(5,i+"");
					
					}
					if(i>=60 && i<70){
						ind++;
						controllo.get(contatore).setText(6,i+"");
					
					}
					if(i>=70 && i<80){
						ind++;
						controllo.get(contatore).setText(7,i+"");
					
					}
					if(i>=80){
						ind++;
						controllo.get(contatore).setText(8,i+"");
					
					}
					
				}
				
				numeriTabella.add(num);
				output += String.format("%s%02d%c", spacer, num, (segnati[index]?'#':' '));
			}
			Collections.sort(numeriTabella);

			Utility.info(output);
		}
	}
	
	public static void main(String[] args) {
		try {
			Client window = new Client();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(0, 206, 209));
		shell.setSize(450, 324);
		shell.setText("Client");
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(33, 76, 365, 92);
		for(int i = 0; i<9 ; i++){
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setWidth(40);
			column.setText("");
			column.setResizable(false);
			table.setHeaderVisible(false);
		}
		
		TableItem item;
		
		for(int i = 0; i<3 ;i++){
			item=new TableItem(table,SWT.NONE);
			controllo.add(item);
		}
		inizializzaCartella();
		
		Button btnConnessione = new Button(shell, SWT.NONE);
		btnConnessione.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					
					client = new Socket("localhost",9999);
					ClientReceiver cr = new ClientReceiver(Client.this,client);
					cr.start();
					System.out.println("fatto!");
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnConnessione.setBounds(135, 21, 155, 25);
		btnConnessione.setText("Connessione");
		
		
		Button btnAmbo = new Button(shell, SWT.RADIO);
		btnAmbo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tipo="a";
			}
		});
		btnAmbo.setBounds(33, 193, 62, 16);
		btnAmbo.setText("Ambo");
		
		Button btnTerna = new Button(shell, SWT.RADIO);
		btnTerna.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tipo="t";
			}
		});
		btnTerna.setBounds(120, 193, 62, 16);
		btnTerna.setText("Terna");
		
		Button btnQuaterna = new Button(shell, SWT.RADIO);
		btnQuaterna.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tipo="t";
			}
		});
		btnQuaterna.setBounds(220, 193, 70, 16);
		btnQuaterna.setText("Quaterna");
		
		Button btnCinquina = new Button(shell, SWT.RADIO);
		btnCinquina.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tipo="t";
			}
		});
		btnCinquina.setBounds(308, 193, 90, 16);
		btnCinquina.setText("Cinquina");
		
		Button btnTombola = new Button(shell, SWT.RADIO);
		btnTombola.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tipo="tombola";
			}
		});
		btnTombola.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		btnTombola.setAlignment(SWT.CENTER);
		btnTombola.setBounds(135, 220, 155, 16);
		btnTombola.setText("Tombola");
		
		Button btnAvvisa = new Button(shell, SWT.NONE);
		btnAvvisa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switch(tipo){
				case "a":
					vincitaA();
				break;
				case "t":
					vincitaT();
					break;
				case "q":
					vincitaQ();
					break;
				case "c":
					vincitaC();
					break;
				case "tombola":
					vincitaTombo();
					break;
				}
			}

			private void vincitaTombo() {
				// TODO Auto-generated method stub
				
			}

			private void vincitaC() {
				// TODO Auto-generated method stub
				
			}

			private void vincitaQ() {
				// TODO Auto-generated method stub
				
			}

			private void vincitaT() {
				// TODO Auto-generated method stub
				
			}

			private void vincitaA() {
				// TODO Auto-generated method stub
				int controllo=0;
				for(int j=0;j<9;j++){
					System.out.println(segnati[j]);
					if(segnati[j]==true){
						controllo++;
					}
				}
			}
		});
		btnAvvisa.setBounds(175, 242, 75, 25);
		btnAvvisa.setText("Avvisa");

	}

	public void addNumero(String numeroRicevuto) {
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				Display d = Display.getDefault();
				for(int i=0;i<numeriTabella.size();i++){
					if(Integer.parseInt(numeroRicevuto)==numeriTabella.get(i)){
						segnati[i]=true;
						for(int j=0;j<3;j++){
							for(int k=0;k<9;k++){
								if(table.getItem(j).getText(k).equalsIgnoreCase(numeroRicevuto)){
									table.getItem(j).setBackground(k, d.getSystemColor(SWT.COLOR_RED));
									
								}
							}
						}
					}
				}
				
			}
		});
		
	}
}
