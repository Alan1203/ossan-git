package _07_email;
import java.io.IOException;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class MailServlet
 */
@WebServlet("/_07_email/SendEmail")
public class SendEmail extends HttpServlet {
    private static final long serialVersionUID = 1L;
    String username = "alan841203@gmail.com";
    String password = "0911076028";
    public SendEmail() {
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
        	//取得連線
            Properties props = new Properties();
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            //邮箱的发送服务器地址
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            //邮箱发送服务器端口,这里设置为465端口
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            //获取到邮箱会话,利用匿名内部类的方式,将发送者邮箱用户名和密码授权给jvm
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                       protected PasswordAuthentication getPasswordAuthentication() {
                          return new PasswordAuthentication(username, password);
           	   }
                    });
            
//            Session session = Session.getDefaultInstance(props); //設置對話對象窗口


            //新建一个邮件
            Message message = new MimeMessage(session);
            //设置发件人
            message.setFrom(new InternetAddress(username));
            //设置多个收件人
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("123456@qq.com"));
            //設置收信人
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("alanisyoung@yahoo.com.tw"));
            //设置主题
            message.setSubject("ha");
            //设置文本
            message.setText("I am iron man");
            
            //邮件传送对象
            Transport transport = session.getTransport();
            
//            message.setContent(msgBody, "text/plain;charset=big5");要傳中文時用，String msgBody
            //设置，邮件服务器地址、用户名、密码
//            //发送邮件（邮件，地址）
//            transport.sendMessage(message,  new Address[] {new InternetAddress("alan841203@gmail.com")} );
            Transport.send(message);
            //关闭连接
            System.out.println("ok");
            transport.close();
            
            response.sendRedirect("emailTest.jsp");
            
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

}