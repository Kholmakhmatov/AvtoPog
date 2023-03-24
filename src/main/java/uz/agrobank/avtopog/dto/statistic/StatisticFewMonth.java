package uz.agrobank.avtopog.dto.statistic;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StatisticFewMonth {
    private Long amount;
    private String label;
    private String indexLabel="";

}
