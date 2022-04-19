package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {

	private String userName = "sendmailgmai@gmail.com";
	private String senha = "";

	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	public ObjetoEnviaEmail(String listaDestinatario, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatario;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;

	}

	public void enviarEmail(boolean envioHtml) throws Exception {
		try {
			Properties properties = new Properties();

			properties.put("mail.smtp.ssl.trust", "*");
			properties.put("mail.smtp.auth", "true");/* Autorização */
			properties.put("mail.smtp.starttls", "true");/* Autenticação */
			properties.put("mail.smtp.host", "smtp.gmail.com");/* Servidor SMTP gmail google */
			properties.put("mail.smtp.port", "465");/* Porta do servidor */
			properties.put("mail.smtp.socketFactory.port", "465"); /* Especifica a porta a ser conectada pelo socket */
			properties.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");/* Classe socket de conexão ao SMTP */

			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, senha);
				}
			});

			Address[] toUser = InternetAddress.parse(listaDestinatarios);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName, nomeRemetente));/* Quem envia */
			message.setRecipients(Message.RecipientType.TO, toUser);/* Email de destino */
			message.setSubject(assuntoEmail);/* Assunto do E-mail */

			if (envioHtml) {
				message.setContent(textoEmail, "text/html; charset=utf-8");
			} else {
				message.setText(textoEmail);
			}

			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void enviarEmailAnexo(boolean envioHtml) throws Exception {
		try {
			Properties properties = new Properties();

			properties.put("mail.smtp.ssl.trust", "*");
			properties.put("mail.smtp.auth", "true");/* Autorização */
			properties.put("mail.smtp.starttls", "true");/* Autenticação */
			properties.put("mail.smtp.host", "smtp.gmail.com");/* Servidor SMTP gmail google */
			properties.put("mail.smtp.port", "465");/* Porta do servidor */
			properties.put("mail.smtp.socketFactory.port", "465"); /* Especifica a porta a ser conectada pelo socket */
			properties.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");/* Classe socket de conexão ao SMTP */

			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, senha);
				}
			});

			Address[] toUser = InternetAddress.parse(listaDestinatarios);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName, nomeRemetente));/* Quem envia */
			message.setRecipients(Message.RecipientType.TO, toUser);/* Email de destino */
			message.setSubject(assuntoEmail);/* Assunto do E-mail */

			/*
			 * Parte 1 do e-mail que é texto e a descrição do e-mail
			 */

			MimeBodyPart corpoEmail = new MimeBodyPart();

			if (envioHtml) {
				corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
			} else {
				corpoEmail.setText(textoEmail);
			}

			List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
			arquivos.add(simuladorDePDF());
			arquivos.add(simuladorDePDF());
			arquivos.add(simuladorDePDF());
			arquivos.add(simuladorDePDF());

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(corpoEmail);

			int index = 0;
			for (FileInputStream fileInputStream : arquivos) {

				/*
				 * Parte 2 do e-mail que são os anexos em PDF
				 */
				MimeBodyPart anexoEmail = new MimeBodyPart();

				/*
				 * Onde é passado o simuladorDePDF você passa seu arquivo gravado no seu banco
				 * de dados
				 */

				anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
				anexoEmail.setFileName("anexoemail" + index + ".pdf");

				multipart.addBodyPart(anexoEmail);

				index++;
			}

			message.setContent(multipart);

			Transport.send(message);

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * Esse metodo simula o PDF ou qualquer arquivo que possa ser enviado por anexo
	 * no email Pode pegar arquivo no seu banco de dados base64, byte[], Stream de
	 * Arquivos Pode estar em um bancode dados ou em uma pasta Retorna um PDF em
	 * branco com o texto do paragrafo de exemplo
	 */
	private FileInputStream simuladorDePDF() throws Exception {

		Document document = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo do PDF anexo com Java Mail"));
		document.close();

		return new FileInputStream(file);
	}
}
