package com.es.iesmz.transita3.Transita.Utils;

import com.es.iesmz.transita3.Transita.exception.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSetPasswordEmail(String email) throws MessagingException {
        MimeMesafe
    }
}
