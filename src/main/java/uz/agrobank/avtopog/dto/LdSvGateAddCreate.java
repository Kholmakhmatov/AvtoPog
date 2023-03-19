package uz.agrobank.avtopog.dto;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

public class LdSvGateAddCreate {
    private Long id;

    private String branch;

    private String cardNumber;

    private String expiryMonth;

    private String expiryYear;

}
