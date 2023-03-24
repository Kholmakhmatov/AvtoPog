package uz.agrobank.avtopog.dto.statistic;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StatisticFewYear {
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate localDate;
    private Long amount;
    private String indexLabel="";
   private String markerColor="green";

}
