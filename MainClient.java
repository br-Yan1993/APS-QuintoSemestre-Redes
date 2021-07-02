package program_client;

import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import conex.ClientMsg;

/***
 * Classe que inicia um Cliente para efetuar uma conexão com o Servidor
 * @throws IOException retorna IO Exception caso dê algum erro.
 */

public class MainClient {
	
	public static void main(String[] args) throws IOException {
		
		JLabel title = new JLabel("<html>Esta aplicacao atua atraves de uma conexao por JavaSocket,<br>"
				+ " tanto para envio de mensagens, quanto para envio de arquivos .txt<br>"
				+ "Por isto, quando necessario a troca de arquivos,<br>"
				+ "sugerimos informar previamente a 'Porta' que sera feita o envio,<br>"
				+ "Pois desta forma, quem enviara o arquivo,<br>tera todas as informacoes disponiveis!</html>");
	    Object[] texts = {title};
	    JOptionPane.showMessageDialog(null, texts);
		
		ClientMsg clnew = new ClientMsg();
		clnew.conectClient();
		clnew.listeningServer();
	}

}
