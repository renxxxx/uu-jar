package cc.renx.uu;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MMail {

	public static void main(String[] args) throws Exception {
		MMail j = new MMail();
		j.host = "smtp.qq.com";
		j.user = "413038044@qq.com";
		j.password = "";
		j.sender = "413038044@qq.com";

		j.send("413038044@qq.com", "aq123123d", "qqqq");
	}

	public String host;
	public String user;
	public String password;
	public String sender;

	public void send(String[] receivers, String subject, String content) throws Exception {
		for (int i = 0; receivers != null && i < receivers.length; i++) {
			String receiver = receivers[i];
			send(receiver, subject, content);
		}
	}

	public Message send(String receiver, String subject, String content) throws Exception {
		try {
			if (receiver == null || receiver.isEmpty())
				return null;
			subject = subject == null ? "" : subject;
			content = content == null ? "" : content;
			content = content.replaceAll("\n", "</br>");

			Properties prop = new Properties();
			prop.setProperty("mail.transport.protocol", "smtp");
			prop.setProperty("mail.smtp.host", host);
			prop.setProperty("mail.smtp.auth", "true");
			prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			prop.setProperty("mail.smtp.socketFactory.port", "465");
			prop.setProperty("mail.smtp.port", "465");

			// 使用JavaMail发送邮件的5个步骤
			// 1、创建session
			Session session = Session.getInstance(prop);
			// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
			session.setDebug(false);
			// 2、通过session得到transport对象

			Transport ts = session.getTransport();
			// 3、连上邮件服务器
			ts.connect(host, 465, user, password);

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
		} catch (Exception e) {
			throw new RuntimeException(MMap.build().put("error", e.getMessage()).put("host", host).put("user", user)
					.put("password", password).put("sender", sender).put("receiver", receiver).put("subject", subject)
					.toJSONString(), e);
		}

	}

}
