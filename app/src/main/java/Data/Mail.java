package Data;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail
{
    private final String User;
    private String Pass;
    private String Destinatario;
    private String Asunto;
    private String Mensaje;

    public Mail(String User, String Pass, String Destinatario, String Asunto, String Mensaje)
    {
        this.User = User;
        this.Pass = Pass;
        this.Destinatario = Destinatario;
        this.Asunto = Asunto;
        this.Mensaje = Mensaje;
    }

    public String sendMensaje()
    {
        String msgError;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(User, Pass);
            }
        });

        try
        {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(Destinatario));
            message.setSubject(Asunto);
            message.setText(Mensaje);

            Transport.send(message);
            msgError = "";
        }
        catch (MessagingException e)
        {
            msgError = "Error al enviar mail";
            e.printStackTrace();
        }

        return msgError;
    }
}