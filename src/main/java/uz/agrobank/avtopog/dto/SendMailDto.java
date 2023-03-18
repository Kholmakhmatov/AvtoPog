package uz.agrobank.avtopog.dto;

import lombok.Data;

@Data
public class SendMailDto {
    private String fullName;
    private String subject;
    private String email;
    private String message;
}
