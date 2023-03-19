package uz.agrobank.avtopog.model;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "LD_SV_GATE")
public class LdSvGate {
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
}
