package top.suyuanshine.store.utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.Properties;

public class MailUtils {
    public static void main(String[] args) throws MessagingException {
        sendMail("1121775686@qq.com", "sss");
    }

    public static void sendMail(String email, String emailMsg)
            throws AddressException, MessagingException {
        // 1.创建一个程序与邮件服务器会话对象 Session

        Properties props = new Properties();
        //设置发送的协议
        props.setProperty("mail.transport.protocol", "SMTP");

        //设置发送邮件的服务器
        String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        props.setProperty("mail.host", "smtp.163.com");
        props.setProperty("mail.smtp.auth", "true");// 指定验证为true

        // 创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //设置发送人的帐号和密码
                return new PasswordAuthentication("y_starrysky@163.com", "LCIBZHDJIHPNTBCN");
            }
        };

        Session session = Session.getInstance(props, auth);

        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);

        //设置发送者
        message.setFrom(new InternetAddress("y_starrysky@163.com"));

        //设置发送方式与接收者
        message.setRecipient(RecipientType.TO, new InternetAddress(email));

        //设置邮件主题
        message.setSubject("用户激活");
        // message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

        String url = "http://suyuanshine.top/store/UserServlet?method=active&code=" + emailMsg;
        String content = "<h1>来自购物天堂的激活邮件!激活请点击以下链接!(点击无效请复制链接到浏览器打开即可)</h1><h3><a href='" + url + "'>" + url + "</a></h3>";
        //设置邮件内容
        message.setContent(content, "text/html;charset=utf-8");

        // 3.创建 Transport用于将邮件发送
        Transport.send(message);
    }

}
