package nhom3.Utils;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class MailUtil {

    private static final String EMAIL =
            "khangtmhth08478@gmail.com";

    private static final String APP_PASSWORD =
            "vhho eeco huiv yyof";

    public static void sendOTP(
            String toEmail,
            String otp) throws Exception {

        Properties props = new Properties();

        props.put(
                "mail.smtp.auth",
                "true");

        props.put(
                "mail.smtp.starttls.enable",
                "true");

        props.put(
                "mail.smtp.host",
                "smtp.gmail.com");

        props.put(
                "mail.smtp.port",
                "587");

        Session session =
                Session.getInstance(
                        props,
                        new Authenticator() {

                    protected PasswordAuthentication
                    getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                EMAIL,
                                APP_PASSWORD);
                    }
                });

        Message message =
                new MimeMessage(session);

        message.setFrom(
                new InternetAddress(EMAIL));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail));

        message.setSubject(
                "OTP đặt lại mật khẩu");

        message.setText(
                "Mã OTP của bạn là: "
                + otp);

        Transport.send(message);
    }
    
    public static void sendRegisterOTP(
            String toEmail,
            String otp) throws Exception {

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session =
                Session.getInstance(
                        props,
                        new Authenticator() {

            protected PasswordAuthentication
            getPasswordAuthentication() {

                return new PasswordAuthentication(
                        EMAIL,
                        APP_PASSWORD);
            }
        });

        Message message =
                new MimeMessage(session);

        message.setFrom(
                new InternetAddress(EMAIL));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail));

        message.setSubject(
                "Xác thực đăng ký tài khoản HAKY TECH");

        message.setText(
                "Xin chào,\n\n"
              + "Mã OTP để đăng ký tài khoản của bạn là: "
              + otp
              + "\n\nMã OTP có hiệu lực trong 5 phút."
              + "\n\nNếu bạn không thực hiện đăng ký, hãy bỏ qua email này.");

        Transport.send(message);
    }
}