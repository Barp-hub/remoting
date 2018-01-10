package io.github.riwcwt;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

//import javax.mail.Address;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
//import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShadowsocksOperator {

    private ShadowsocksConfig config() {
        ShadowsocksConfig config;
        try {
            config = JSON.parseObject(FileUtils.readFileToString(new File(Application.CONFIG), Charset.defaultCharset()), ShadowsocksConfig.class);
        } catch (IOException e) {
            return null;
        }
        return config;
    }

    public void delete(String port) {
        ShadowsocksConfig config = this.config();
        Optional.ofNullable(config).ifPresent(shadowsocksConfig -> {
            shadowsocksConfig.get_comment()
                    .entrySet().stream().filter(entry -> entry.getKey().equalsIgnoreCase(port))
                    .forEach(entry -> System.out.println(String.format("移除用户：%s - %s", entry.getKey(), entry.getValue())));

            shadowsocksConfig.getPort_password().remove(port);
            shadowsocksConfig.get_comment().remove(port);

            this.flush(shadowsocksConfig);
        });

    }

    public void add(String username) {
        ShadowsocksConfig config = this.config();
        AtomicBoolean exist = new AtomicBoolean(false);
        Optional.ofNullable(config).ifPresent(shadowsocksConfig -> exist.set(shadowsocksConfig.get_comment().values().stream().anyMatch(value -> username.equalsIgnoreCase(value))));
        if (exist.get()) {
            System.out.println("用户已存在！");
            return;
        }
        Optional.ofNullable(config).ifPresent(shadowsocksConfig -> {
            int port = shadowsocksConfig.getPort_password().keySet().stream().mapToInt(Integer::valueOf).max().getAsInt();
            port++;
            String password = this.randomPassword(28);
            shadowsocksConfig.getPort_password().put(String.valueOf(port), password);
            shadowsocksConfig.get_comment().put(String.valueOf(port), username);

            this.flush(shadowsocksConfig);

            this.print(password, String.valueOf(port));
            //            this.sendEmail(username, password, String.valueOf(port));
        });

    }

    public void reboot() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("reboot now");

            try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                while (input.readLine() != null) {
                }
            }
            int exit = process.waitFor();

            if (exit != 0) {
                System.out.println("重新加载失败！！！");
            }

        } catch (IOException e) {
            System.out.println("重新加载失败！！！");
        } catch (InterruptedException e) {
            System.out.println("重新加载失败！！！");
        }

    }

    private void print(String password, String port) {
        System.out.println(new StringBuilder()
                .append("IP : 47.91.150.194\n")
                .append("method : aes-256-cfb\n")
                .append("端口 ： ").append(port).append("\n")
                .append("密码 ： ").append(password).append("\n")
                .append("\n")
                .append("配置参考 : http://47.96.254.39:8090/x/PQAB")
                .toString());
    }

    //    private void sendEmail(String username, String password, String port) {
    //        Properties properties = new Properties();
    //        properties.put("mail.smtp.auth", "true");
    //        properties.put("mail.smtp.starttls.enable", "true");
    //        properties.put("mail.smtp.host", "smtp.exmail.qq.com");
    //        properties.put("mail.smtp.port", "465");
    //
    //        Session session = Session.getInstance(properties,
    //                new javax.mail.Authenticator() {
    //                    protected PasswordAuthentication getPasswordAuthentication() {
    //                        return new PasswordAuthentication("dev@qbb6.com", "*********");
    //                    }
    //                });
    //
    //        try {
    //
    //            Message message = new MimeMessage(session);
    //            message.setFrom(new InternetAddress("dev@qbb6.com"));
    //            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("zhujun@qbb6.com"));
    //            message.setRecipients(Message.RecipientType.TO,
    //                    InternetAddress.parse(username + "@qbb6.com"));
    //            message.setSubject("翻墙代理相关信息");
    //            message.setText("IP : 47.91.150.194\n" +
    //                    "method : aes-256-cfb\n" +
    //                    "端口 ： " + port + "\n" +
    //                    "密码 ： " + password + "\n" +
    //                    "\n" +
    //                    "配置参考 : http://47.96.254.39:8090/x/PQAB");
    //
    //            Transport.send(message);
    //
    //            System.out.println("邮件发送成功");
    //
    //        } catch (Exception e) {
    //            System.out.println("邮件发送失败!!!");
    //        }
    //
    //    }

    public void list() {
        ShadowsocksConfig config = this.config();
        System.out.println(String.format("%-10s %-24s %-36s", "端口", "用户", "密码"));
        Optional.ofNullable(config).ifPresent(shadowsocksConfig -> shadowsocksConfig.getPort_password()
                .keySet().stream().mapToInt(Integer::valueOf).sorted().forEach(port -> {
                    System.out.println(String.format("%-10s %-24s %-36s", port, shadowsocksConfig.get_comment().get(String.valueOf(port)), shadowsocksConfig.getPort_password().get(String.valueOf(port))));
                }));
    }

    private String randomPassword(int length) {
        int i;  //生成的随机数
        int count = 0; //生成的密码的长度
        // 密码字典
        char[] str = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };
        StringBuffer stringBuffer = new StringBuffer("");
        List<String> list = new ArrayList<>();
        Random r = new Random();
        for (i = 0; i < str.length; i++) {
            list.add(str[i] + "");
        }
        Collections.shuffle(list);
        while (count < length) {
            //生成 0 ~ 密码字典-1之间的随机数
            i = r.nextInt(list.size());
            stringBuffer.append(list.get(i));
            count++;
        }
        return stringBuffer.toString();
    }

    private void flush(ShadowsocksConfig config) {
        try {
            FileUtils.writeStringToFile(new File(Application.CONFIG), JSON.toJSONString(config, true), Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println("写入配置文件错误！！！");
        }
    }
}
