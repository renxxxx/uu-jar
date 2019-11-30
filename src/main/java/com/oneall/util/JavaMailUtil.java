package com.oneall.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtil {
	public static void main(String[] args) throws Exception {
		JavaMailUtil.Heart hert = new JavaMailUtil.Heart();
		hert.setHost("smtp.qq.com");
		hert.setProtocol("smtp");
		hert.setPwd("jthdzmjaheysbiga");
		hert.setSender("413038044@qq.com");
		hert.setSmtpAuth("true");
		hert.setUser("413038044@qq.com");

		JavaMailUtil.send(hert, "413038044@qq.com", "ad", "asdf");
	}

	public static class Heart {
		String host;
		String protocol;
		String smtpAuth;
		String user;
		String pwd;
		String sender;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public String getProtocol() {
			return protocol;
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

		public String getSmtpAuth() {
			return smtpAuth;
		}

		public void setSmtpAuth(String smtpAuth) {
			this.smtpAuth = smtpAuth;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPwd() {
			return pwd;
		}

		public void setPwd(String pwd) {
			this.pwd = pwd;
		}

		public String getSender() {
			return sender;
		}

		public void setSender(String sender) {
			this.sender = sender;
		}

	}

	public static Message send(Heart heart, String receiver, String subject, String content) throws Exception {
		Properties prop = new Properties();
		prop.setProperty("mail.host", heart.host);
		prop.setProperty("mail.transport.protocol", heart.protocol);
		prop.setProperty("mail.smtp.auth", heart.smtpAuth);
		// 使用JavaMail发送邮件的5个步骤
		// 1、创建session
		Session session = Session.getInstance(prop);
		// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(true);
		// 2、通过session得到transport对象
		Transport ts = session.getTransport();
		// 3、连上邮件服务器
		ts.connect(heart.host, heart.user, heart.pwd);

		// 4、创建邮件
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(heart.sender));
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
