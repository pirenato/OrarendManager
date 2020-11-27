package usermanagement;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {
    public static void send(String emailFrom,String password,String emailTo,String subject,String message){
        //Properties objektum beallitasa
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailFrom,password);
                    }
                });

        //uzenet letrehozasa
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(emailTo));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            //uzenet kuldese
            Transport.send(mimeMessage);
        } catch (MessagingException e) {throw new RuntimeException(e);}

    }
}
