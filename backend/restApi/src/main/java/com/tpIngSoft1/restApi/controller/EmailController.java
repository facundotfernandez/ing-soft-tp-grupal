package com.tpIngSoft1.restApi.controller;

import com.sendgrid.Response;
import com.tpIngSoft1.restApi.domain.EmailRequest;
import com.tpIngSoft1.restApi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sendemail")
@CrossOrigin(origins = {"http://localhost:3000", "https://ing-soft-tp-grupal.vercel.app"})
public class EmailController {


    @Autowired
    private EmailService emailservice;

    @PostMapping
    public ResponseEntity<String> sendemail(@RequestBody EmailRequest emailrequest) {

        Response response = emailservice.sendemail(emailrequest);
        if (response.getStatusCode() == 200 || response.getStatusCode() == 202)
            return new ResponseEntity<>("sent successfully", HttpStatus.OK);
        System.out.println(emailrequest);
        System.out.println(response);
        System.out.println(response.getBody());
        return new ResponseEntity<>("failed to sent", HttpStatus.NOT_FOUND);

    }

}