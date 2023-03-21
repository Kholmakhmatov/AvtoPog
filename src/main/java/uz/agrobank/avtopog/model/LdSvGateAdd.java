package uz.agrobank.avtopog.model;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Kholmakhmatov_A on 2/20/2023
 *
 * @Author : Kholmakhmatov_A
 * @Date : 2/20/2023
 * @Project : AvtoPog
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LD_SV_GATE_ADD")
public class LdSvGateAdd {

    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "BRANCH")
    private String branch;
    @Column(name = "CARD_NUMBER")
    private String cardNumber;
    @Column(name = "EXPIRY_DATE")
    private String expiryDate;
    @Column(name = "SIGN_CLIENT")
    private Integer signClient;
    @Column(name = "SIGN_CARD")
    private Integer signCard;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "STATE")
    private Integer state;
    @Column(name = "SMS")
    private String sms;
    @Column(name = "SMS_RECV")
    private String smsRecv;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "RG_USER")
    private Long rg_user;

    public LdSvGateAdd(Long id, String branch, String cardNumber, String expiryDate, Long rg_user) {
        this.id = id;
        this.branch = branch;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.rg_user = rg_user;
        name = "test";
        signClient = 2;
        signCard = 1;
        phone = "+998";
        state = 1;
        sms = "sms";
        smsRecv = "sms";
        status = 0;
    }
}
