package com.noone.skins.common;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class MailService {
    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;


    public void sendMail(String title, String text, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); // 发送人的邮箱
        message.setTo(to); //发给谁对方邮箱
        message.setSubject(title); //标题
        message.setText(text); //内容
        javaMailSender.send(message); //发送
        System.out.println("时间: " + LocalDateTime.now() + "邮件发送成功！");
    }
}