package uz.agrobank.avtopog.dto.statistic;

import lombok.Data;

@Data
public class StatisticMQ {
    private Long amount;
    private String label;
    private String indexLabel="";

}
