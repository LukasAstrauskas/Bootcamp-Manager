package com.bootcamp.bootcampmanager.mail;

import com.bootcamp.bootcampmanager.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService
{
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(Mail mail)
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmailForNewUser(User user) {
        Mail mail = new Mail();
        mail.setMailFrom("bootcamp.manager.2022@gmail.com");
        mail.setMailTo(user.getEmail());
        mail.setMailSubject("Invitation to join Bootcamp Manager");
        mail.setMailContent("Hello, " + user.getFirstName() + " " + user.getLastName() + ",\n\nYou are invited to join Bootcamp Manager. Please login to application with your email and password. Change your password after you login on profile page. \n\nYour password: " + user.getPassword() + "\n\n" + "Link to login page: https://bootcamp-manager.herokuapp.com/login\n\nBest regards,\nBootcamp Manager team");
        sendEmail(mail);
    }

}