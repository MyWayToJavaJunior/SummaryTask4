package ua.nure.norkin.SummaryTask4.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import ua.nure.norkin.SummaryTask4.command.AdminRegistrationCommand;
import ua.nure.norkin.SummaryTask4.entity.User;

public class MailUtils {
	private static final Logger LOG = Logger
			.getLogger(AdminRegistrationCommand.class);
	private static final Session SESSION = init();
	private static final String confirmSubjectRu = "Подтвердите регистрацию";
	private static final String confirmSubjectEng = "Confirm Registration";
	private final static String confirmationURL = "http://localhost:8080/SummaryTask4/controller?command=confirmRegistration&ID=";

	private static Session init() {
		Session session = null;
		try {
			Context initialContext = new InitialContext();
			session = (Session) initialContext
					.lookup("java:comp/env/mail/Session");
		} catch (Exception ex) {
			LOG.error("mail session lookup error", ex);
		}
		return session;
	}

	public static void sendConfirmationEmail(User user) {
		try {
			Message msg = new MimeMessage(SESSION);
			msg.setFrom(new InternetAddress(
					"mail.university.admission@gmail.com"));
			msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress(user.getEmail()));

			if (user.getLang().equals("ru")) {
				setContentToConfirmationEmailRu(msg, user);
			} else {
				setContentToConfirmationEmailEng(msg, user);
			}

			msg.setSentDate(new Date());

			Transport.send(msg);
		} catch (AddressException e) {
			LOG.error(e);
		} catch (MessagingException e) {
			LOG.error(e);
		} catch (UnsupportedEncodingException e) {
			LOG.error(e);
		}
	}

	private static void setContentToConfirmationEmailRu(Message msg, User user)
			throws MessagingException, UnsupportedEncodingException {
		msg.setSubject(confirmSubjectRu);

		Multipart multipart = new MimeMultipart();

		String encodedEmail = new String(Base64.getEncoder().encode(
				user.getEmail().getBytes(StandardCharsets.UTF_8)));

		InternetHeaders emailAndPass = new InternetHeaders();
		emailAndPass.addHeader("Content-type", "text/plain; charset=UTF-8");
		String hello = "Здравствуйте, " + user.getFirstName() + " "
				+ user.getLastName() + " !\n"
				+ "Вы успешно прошли регистрацию на нашем сайте.\n\n\n";

		String data = "\nВаш логин: " + user.getEmail() + "\nВаш пароль: "
				+ user.getPassword() + "\n\n";

		MimeBodyPart greetingAndData = new MimeBodyPart(emailAndPass,
				(hello + data).getBytes("UTF-8"));

		InternetHeaders headers = new InternetHeaders();
		headers.addHeader("Content-type", "text/html; charset=UTF-8");
		String confirmLink = "Подтвердите регистрацию кликнув по этой"
				+ "<a href='" + confirmationURL + encodedEmail + "'>ссылке</a>";
		MimeBodyPart link = new MimeBodyPart(headers,
				confirmLink.getBytes(StandardCharsets.UTF_8));

		multipart.addBodyPart(greetingAndData);
		multipart.addBodyPart(link);

		msg.setContent(multipart);
	}

	private static void setContentToConfirmationEmailEng(Message msg, User user)
			throws MessagingException, UnsupportedEncodingException {
		msg.setSubject(confirmSubjectEng);

		Multipart multipart = new MimeMultipart();

		String encodedEmail = new String(Base64.getEncoder().encode(
				user.getEmail().getBytes(StandardCharsets.UTF_8)));

		InternetHeaders emailAndPass = new InternetHeaders();
		emailAndPass.addHeader("Content-type", "text/plain; charset=UTF-8");
		String hello = "Hello, " + user.getFirstName() + " "
				+ user.getLastName() + " !\n"
				+ " You successfuly registered on our web-site!\n\n\n";

		String data = "\nLogin: " + user.getEmail() + "\nPassword: "
				+ user.getPassword() + "\n\n";

		MimeBodyPart greetingAndData = new MimeBodyPart(emailAndPass,
				(hello + data).getBytes("UTF-8"));

		InternetHeaders headers = new InternetHeaders();
		headers.addHeader("Content-type", "text/html; charset=UTF-8");
		String confirmLink = "Complete your registration by clicking on following"
				+ "<a href='" + confirmationURL + encodedEmail + "'>link</a>";
		MimeBodyPart link = new MimeBodyPart(headers,
				confirmLink.getBytes("UTF-8"));

		multipart.addBodyPart(greetingAndData);
		multipart.addBodyPart(link);

		msg.setContent(multipart);
	}
}
