package com.example.springredditclone.service;

import com.example.springredditclone.exception.SpringRedditException;
import com.example.springredditclone.model.NotificaionEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
class MailService
{
    private final MailContentBuilder mailContentBuilder;
    private final JavaMailSender mailSender;

    @Async
    void sendEmail(NotificaionEmail notificaionEmail)
    {

        MimeMessagePreparator messagePreparator= mimeMessage -> {
            MimeMessageHelper messageHelper= new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@gmail.com");
            messageHelper.setTo(notificaionEmail.getRecipient());
            messageHelper.setSubject(notificaionEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificaionEmail.getBody()));
        };
        try
        {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        }
        catch (MailException e)
        {
            log.error("Exception occured while sending mail to user: "+e);
            throw new SpringRedditException("Exception occured while sending mail to "+notificaionEmail.getRecipient());
        }
    }
}
