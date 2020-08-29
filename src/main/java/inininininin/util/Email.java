package inininininin.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	
	public static void main(String[] args) throws Exception {
		Email j = new Email().start("smtp.qq.com", 25, "smtp", "true", "413038044@qq.com", "euhiglgjaxribjch",
				"413038044@qq.com");

		j.send("413038044@qq.com", "aq123123d", "qqqq");
	}

	private String host;
	private Integer port;
	private String protocol;
	private String smtpAuth;
	private String user;
	private String pwd;
	private String sender;
	
	public Email start(String host, Integer port, String protocol, String smtpAuth, String user, String pwd,
			String sender) {
		this.host = host;
		this.port = port;
		this.protocol = protocol;
		this.smtpAuth = smtpAuth;
		this.user = user;
		this.pwd = pwd;
		this.sender = sender;
		return this;
	}

	public Message send(String receiver, String subject, String content) throws Exception {
		Properties prop = new Properties();
		prop.setProperty("mail.host", host);
		prop.setProperty("mail.transport.protocol", protocol);
		prop.setProperty("mail.smtp.auth", smtpAuth);
		prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// 使用JavaMail发送邮件的5个步骤
		// 1、创建session
		Session session = Session.getInstance(prop);
		// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(false);
		// 2、通过session得到transport对象
		Transport ts = session.getTransport();
		// 3、连上邮件服务器
		if (port == null)
			ts.connect(host, user, pwd);
		else
			ts.connect(host, port, user, pwd);

		// 4、创建邮件
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(sender));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
		// 邮件的标题
		message.setSubject(subject);
		// 邮件的文本内容
		message.setContent(content, "text/html;charset=UTF-8");
		// 返回创建好的邮件对象

		// 5、发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
		return message;
	}

}
