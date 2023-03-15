package uz.agrobank.avtopog.dto;

//import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * Created by Kholmakhmatov_A on 2/20/2023
 *
 * @Author : Kholmakhmatov_A
 * @Date : 2/20/2023
 * @Project : AvtoPog
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LdSvGateDto {
    @NotNull(message = "Branch should not bu null! Please enter a branch!")
    private String branch;

    @NotNull(message = "Anketa number shouldn't be null! Please enter a anketa number!")
    private Long id;

    @NotNull(message = "Card number should't be null! Please enter a card number!")
    private String cardNumber;

    @NotNull(message = "Expiry date  should't be null! Please enter a expiry date!")
    private String expiryDate;

    private String name = "test";

    private String phone = "+9989";

    private String sms = "sms";

    private String smsRecv = "sms";

    public LdSvGateDto(String branch, String cardNumber) {
        this.branch = branch;
        this.cardNumber = cardNumber;
    }

    public LdSvGateDto(String branch, Long id) {
        this.branch = branch;
        this.id = id;
    }

    private Integer status = 0;


    public LdSvGateDto(String branch, Long id, String cardNumber) {
        this.branch = branch;
        this.id = id;
        this.cardNumber = cardNumber;
    }



}
