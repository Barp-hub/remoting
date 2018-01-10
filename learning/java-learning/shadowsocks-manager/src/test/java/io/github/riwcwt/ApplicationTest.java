package io.github.riwcwt;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

//import javax.mail.Message;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
//import java.util.Properties;

/**
 * Unit test for simple Application.
 */
public class ApplicationTest {

    @Test
    public void json() throws IOException {
        String config = FileUtils.readFileToString(new File(Application.CONFIG), Charset.defaultCharset());
        System.out.println(config);
        ShadowsocksConfig shadowsocksConfig = JSON.parseObject(config, ShadowsocksConfig.class);
        System.out.println(JSON.toJSONString(shadowsocksConfig, true));
    }

    @Test
    public void delete() {
        ShadowsocksOperator operator = new ShadowsocksOperator();
        operator.delete("40001");
    }

    @Test
    public void format() {
        System.out.println(String.format("%-10s %-24s %-36s", "端口", "用户", "密码"));
        ShadowsocksOperator operator = new ShadowsocksOperator();
        operator.list();
    }

    //    @Test
    //    public void email() {
    //        Properties properties = new Properties();
    //        properties.put("mail.smtp.auth", "true");
    //        properties.put("mail.smtp.starttls.enable", "true");
    //        properties.put("mail.smtp.host", "smtp.exmail.qq.com");
    //        properties.put("mail.smtp.port", "465");
    //
    //        Session session = Session.getInstance(properties,
    //                new javax.mail.Authenticator() {
    //                    protected PasswordAuthentication getPasswordAuthentication() {
    //                        return new PasswordAuthentication("dev@qbb6.com", "**********");
    //                    }
    //                });
    //
    //        try {
    //
    //            Message message = new MimeMessage(session);
    //            message.setFrom(new InternetAddress("dev@qbb6.com"));
    //            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("zhujun@qbb6.com"));
    //            message.setRecipients(Message.RecipientType.TO,
    //                    InternetAddress.parse("zhujun@qbb6.com"));
    //            message.setSubject("翻墙代理相关信息");
    //            message.setText("IP : 47.91.150.194\n" +
    //                    "method : aes-256-cfb\n" +
    //                    "端口 ： " + 1 + "\n" +
    //                    "密码 ： " + 1 + "\n" +
    //                    "\n" +
    //                    "配置参考 : http://47.96.254.39:8090/x/PQAB");
    //
    //            Transport.send(message);
    //
    //            System.out.println("邮件发送成功");
    //
    //        } catch (Exception e) {
    //            System.out.println("邮件发送失败!!!");
    //            e.printStackTrace();
    //        }
    //
    //    }

}
