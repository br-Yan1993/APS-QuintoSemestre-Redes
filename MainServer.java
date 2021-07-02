package program_server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import conex.FileToSend;

/***
 * Classe que lança o Servidor
 */

public class MainServer extends Thread {
	
	private static ArrayList<BufferedWriter>clientes = new ArrayList<BufferedWriter>();// Lista com os clientes conectados
	private String clientName;
	private Socket conex;
	private InputStream inpStm;
	private InputStreamReader inpStmRdr;
	private BufferedReader buffRdr;

	byte[] objectAsByte;
	BufferedInputStream bf;
	FileToSend arquivo;
	FileOutputStream fos;
	Object obj;
    ByteArrayInputStream bis;
    ObjectInputStream ois;
    
    public MainServer(Socket con) {
    	
    	this.conex = con;
    	try {
    	inpStm  = con.getInputStream();
    	inpStmRdr = new InputStreamReader(inpStm);
    	buffRdr = new BufferedReader(inpStmRdr);
    	}
    	catch (IOException e) {
    		JOptionPane.showMessageDialog(null, "Problema com a conexão\nRevise os Dados de Ip e Porta!",
    			"Atenção...", JOptionPane.INFORMATION_MESSAGE);
    	}
    }
    
    
    /***
     * Metodo run() que ativa a Tread (o Servidor no caso)
     */
    public void run(){
        
    	try {
    	String msg;
	    OutputStream ou =  this.conex.getOutputStream();
	    Writer ouw = new OutputStreamWriter(ou);
	    BufferedWriter bfw = new BufferedWriter(ouw);
	    clientes.add(bfw);
	    clientName = msg = buffRdr.readLine();

	    while(msg != null)
	      {
	       msg = buffRdr.readLine();
	       sendToAll(bfw, msg);
	       }
    	
	    
    	}
  	catch (IOException e) {
				// TODO Auto-generated catch block
    		JOptionPane.showMessageDialog(null, "Problema com a conexão!\nProvavelmente esta conexão foi perdida.\n"
    				+ "Abra uma nova conexão!",
	    			"Atenção...", JOptionPane.INFORMATION_MESSAGE);
			}
		
    }
    
    /***
     * Método que efetua o envio das mensagens para todos os clientes devidamente conectados
     */
    
    public void sendToAll(BufferedWriter bwSaida, String msg) throws  IOException
	{
	  BufferedWriter bwS;
	  for(BufferedWriter bw : clientes){
	   bwS = (BufferedWriter)bw;
	   if(!(bwSaida == bwS)){
	     bw.write(clientName + " -> " + msg+"\r\n");
	     bw.flush();
	   }
	  }
	}
    
    
    
    public static void main(String[] args)  {
    	JFrame frameServer = new JFrame("Tela Servidor");
		frameServer.setSize(190, 120);
		frameServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameServer.dispose();
		frameServer.setLayout(null);
		frameServer.setResizable(false);
		frameServer.setLocationRelativeTo(null);
    	try {
    		
    		
    		JLabel ipl;JLabel ipp;JLabel port;
    		
    		JLabel title = new JLabel("Servidor para Envio de Mensagens!");
    		JTextField txtFldIpLocal = new JTextField();
    		JTextField txtFldIpPublic = new JTextField();
    		JTextField txtFldIpPort = new JTextField("12345");
    	    Object[] texts = {title, txtFldIpLocal, txtFldIpPublic, txtFldIpPort};
    	    JOptionPane.showMessageDialog(null, texts);
    		
    	byte[] byteIpLocal = InetAddress.getByName(txtFldIpLocal.getText()).getAddress();
		ServerSocket srvSocket = new ServerSocket(Integer.parseInt(txtFldIpPort.getText()), 0,
				InetAddress.getByAddress(txtFldIpPublic.getText(), byteIpLocal));
		
		ipl = new JLabel("IP Local:  " + txtFldIpLocal.getText());ipp = new JLabel("IP Publico:  " + txtFldIpPublic.getText());
		port = new JLabel("IP Porta:  " + txtFldIpPort.getText());
		ipl.setBounds(15, 15, 150, 25);ipp.setBounds(15, 30, 160, 25);
		port.setBounds(15, 44, 100, 25);
		
	while(true){
	       Socket conex = srvSocket.accept();
	       Thread t = new MainServer(conex);
	       t.start();
	       frameServer.add(ipl);frameServer.add(ipp);frameServer.add(port);
	       frameServer.setVisible(true);
		}
    }
    	catch (IOException e) {
			// TODO: handle exception
	    	  JOptionPane.showMessageDialog(null, "NÂO CONECTADO!\nREINICIE A OPERAÇÂO!\nProblemas ao acessar o Servidor!\nVerifique os campos de IP e Porta!",
		    			"Atenção...", JOptionPane.INFORMATION_MESSAGE);	
		}
	  catch (NullPointerException e) {
			// TODO: handle exception
	    	  JOptionPane.showMessageDialog(null, "NÂO CONECTADO!\nREINICIE A OPERAÇÂO!\nInsira corretamente os dados nos campos IP's e Porta!",
		    			"Atenção...", JOptionPane.INFORMATION_MESSAGE);	
		}
    	
    	catch (NumberFormatException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "NÂO CONECTADO!\nREINICIE A OPERAÇÂO!\nInsira apenas numeros (IP' e Porta) e pontos nos campos IP's!",
	    			"Atenção...", JOptionPane.INFORMATION_MESSAGE);	
		}
    	
    }
}
