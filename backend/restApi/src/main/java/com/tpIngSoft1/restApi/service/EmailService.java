package com.tpIngSoft1.restApi.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.tpIngSoft1.restApi.domain.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {


    @Autowired
    SendGrid sendGrid;

    public Response sendemail(EmailRequest emailrequest) {

        Mail mail = new Mail(new Email("ing.soft.tp.grupal@gmail.com"), emailrequest.getSubject(), new Email(emailrequest.getTo()), new Content("text/plain", emailrequest.getBody()));
        mail.setReplyTo(new Email(emailrequest.getTo()));
        Request request = new Request();

        Response response = null;

        try {

            request.setMethod(Method.POST);

            request.setEndpoint("mail/send");

            request.setBody(mail.build());

            response = this.sendGrid.api(request);

        } catch (IOException ex) {

            System.out.println(ex.getMessage());

        }

        return response;
    }
}
