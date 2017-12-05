package com.machine.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;

public class EmailApplication {

    @Test
    public void email() throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("smtp.jzktsports.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("postmaster@jzktsports.com", "19800219gaO"));
        email.setSSLOnConnect(true);
        email.setFrom("postmaster@jzktsports.com");
        email.setSubject("TestMail");
        email.setMsg("This is a test mail ... :-)");
        email.addTo("383414454@qq.com");
        email.send();
    }

}
