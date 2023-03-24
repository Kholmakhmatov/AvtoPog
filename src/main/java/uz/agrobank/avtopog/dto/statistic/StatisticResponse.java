package uz.agrobank.avtopog.dto.statistic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class StatisticResponse <T> {
    private LocalDate maximum;

    private LocalDate minimum;
    private List<T>list;
}
