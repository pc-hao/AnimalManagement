package com.animalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MailService {

    public static final String SENDER_MAIL = "BH_ANI_2@outlook.com";
    public static final String SUBJECT = "验证码";
    // JavaMailSender 在Mail 自动配置类 MailSenderAutoConfiguration 中已经导入，这里直接注入使用即可
    @Autowired
    JavaMailSender javaMailSender;

    //方法5个参数分别表示：邮件发送者、收件人、抄送人、邮件主题以及邮件内容
    public void sendSimpleMail(String from, String to, String cc, String subject, String content) {
        // 简单邮件直接构建一个 SimpleMailMessage 对象进行配置并发送即可
        SimpleMailMessage simpMsg = new SimpleMailMessage();
        simpMsg.setFrom(from);
        simpMsg.setTo(to);
        if (cc!=null) {
            simpMsg.setCc(cc);
        }
        simpMsg.setSubject(subject);
        simpMsg.setText(content);
        System.out.println("准备好了simMsg，即将发送");
        javaMailSender.send(simpMsg);
    }
    /* 调用实例：
        mailService.sendSimpleMail(
                "123@QQ.com",
                "1421548723@qq.com",
                "123456@qq.com",
                "我是邮件的标题",
                "我是邮件的内容");
     */

}