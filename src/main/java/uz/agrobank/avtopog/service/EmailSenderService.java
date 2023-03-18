package uz.agrobank.avtopog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.config.SecretKeys;
import uz.agrobank.avtopog.dto.SendMailDto;
import uz.agrobank.avtopog.response.ResponseDto;

@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public ResponseDto<String> sendSimpleEmail(SendMailDto sendMailDto) {
        ResponseDto<String> responseDto=new ResponseDto<>();
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sendMailDto.getEmail());
            message.setTo(SecretKeys.TO_EMAIL);
            message.setText("Sender: "+sendMailDto.getEmail()+"\n"+sendMailDto.getMessage());
            message.setSubject(sendMailDto.getSubject());
            mailSender.send(message);
            responseDto.setMessage("Send your message");
            responseDto.setSuccess(true);
            message = new SimpleMailMessage();
            message.setTo(sendMailDto.getEmail());
            message.setFrom(SecretKeys.TO_EMAIL);
            message.setText("We have received your question and will respond shortly\nYour question : "+sendMailDto.getMessage());
            message.setSubject(sendMailDto.getSubject());
            mailSender.send(message);
        }catch (Exception e){
            responseDto.setMessage("Server error try again or your email address not found");
        }
        return responseDto;
    }

}