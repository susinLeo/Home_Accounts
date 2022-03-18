package homeaccounts;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Banco.UsuariosDB;
import Modelo.Usuarios;
import homeaccounts.Lembretes;

public class JavaMailApp
{
	
	//private static Usuarios usuario;
	private static String emailaddress;

	public static void main(String[] args) {
		JavaMailApp email = new JavaMailApp();
		email.init(emailaddress);
	}
	
	public void init(String emailaddress) {
		
		Properties props = new Properties();
		
		/** Parâmetros de conexao com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getDefaultInstance(
				props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication(){
						return new PasswordAuthentication("trabfaculvane@gmail.com","tr4b4lh0sd4ucs");
					}
				});

		/** Ativa Debug para sessão */
		session.setDebug(true);
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("trabfaculvane@gmail.com"));

			//Remetente
			Address[] toUser = InternetAddress.parse(emailaddress);

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Home Accounts - Lembretes");//Assunto
			message.setText("Enviei este email utilizando JavaMail com minha conta GMail!");
			/**Método para enviar a mensagem criada*/
			Transport.send(message);

			System.out.println("Feito!!!");

		} catch (MessagingException e) {
			System.err.println("Uncaught exception - " + e.getMessage());
	        e.printStackTrace(System.err);
		}
	}
}
