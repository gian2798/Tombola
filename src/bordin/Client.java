package bordin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class Client {

	protected Shell shell;
	private Table table;
	/**
	 * Launch the application.
	 * @param args
	 */
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
		shell.setSize(450, 300);
		shell.setText("Client");
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(32, 65, 370, 123);
		for(int i = 0;i<10;i++){
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setWidth(40);
			column.setText("");
			column.setResizable(false);
		}
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableItem item;
		ArrayList<TableItem> controllo = new ArrayList<TableItem>();
		ArrayList numeri = new ArrayList();
		for(int i=0;i<15;i++){
			int random= (int) (Math.random()*90)+1;
			numeri.add(random);
		}
		
		for(int i=0;i<9;i++){
			
		}
		
		
		for(int i = 0; i<3 ;i++){
			item=new TableItem(table,SWT.NONE);
			
			controllo.add(item);
		}
		
		Button btnChiedi = new Button(shell, SWT.NONE);
		btnChiedi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		btnChiedi.setBounds(175, 215, 75, 25);
		btnChiedi.setText("Chiedi");
		
		Button btnConnessione = new Button(shell, SWT.NONE);
		btnConnessione.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					
					Socket client = new Socket("localhost",9999);
					ClientReceiver cr = new ClientReceiver(Client.this,client);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnConnessione.setBounds(135, 21, 155, 25);
		btnConnessione.setText("Connessione");
		

	}

	public void addNumero(String numeroRicevuto) {
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				//list.add(numeroRicevuto);
			}
		});
		
	}
}
