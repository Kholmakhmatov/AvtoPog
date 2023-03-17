package uz.agrobank.avtopog.model;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Long id;

    private String branch;

    private String cardNumber;

    private String expiryDate;

    private Integer signClient;

    private Integer signCard;

    private String name;

    private String phone;

    private Integer state;

    private String sms;

    private String smsRecv;

    private Integer status;
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
