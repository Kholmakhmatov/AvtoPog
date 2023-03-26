package uz.agrobank.avtopog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.dto.statistic.StatisticFewDays;
import uz.agrobank.avtopog.dto.statistic.StatisticFewMonth;
import uz.agrobank.avtopog.dto.statistic.StatisticFewYear;
import uz.agrobank.avtopog.dto.statistic.StatisticResponse;
import uz.agrobank.avtopog.exceptions.UniversalException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService {
    public String getFewDaysHumo() {
        int period=30;
        StatisticResponse<StatisticFewDays>response=new StatisticResponse<>();
        LocalDate now = LocalDate.now();
        LocalDate minDate = LocalDate.now().minusDays(period);
        response.setMaximum(now);
        response.setMinimum(minDate);
        long max=0L;
        long min=0L;
        List<StatisticFewDays>list=new ArrayList<>();
        for (int i = 0; i <= period; i++) {
            StatisticFewDays statisticFewDays=new StatisticFewDays();
            LocalDate localDate = minDate.plusDays(i);
            statisticFewDays.setLocalDate(localDate);
            long amount= (long) (12000+Math.random()*6000-1000);
            if(amount>max) max=amount;
            if (i==0) min=amount;
            else if(amount<min) min=amount;
            statisticFewDays.setAmount(amount);
            list.add(statisticFewDays);
        }
        for (StatisticFewDays statisticFewDays : list) {
            if (statisticFewDays.getAmount().equals(max)){
                statisticFewDays.setIndexLabel("★ Max (humo)");
                statisticFewDays.setMarkerColor("red");
            }
            if (statisticFewDays.getAmount().equals(min)){
                statisticFewDays.setIndexLabel("⚑ Min (humo)");
                statisticFewDays.setMarkerColor("blue");
            }
        }
        response.setList(list);
          ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String s = null;
        try {
            s = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new UniversalException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  s;

    }

    public String getFewDaysUzcard() {
        int period=30;
        StatisticResponse<StatisticFewDays>response=new StatisticResponse<>();
        LocalDate now = LocalDate.now();
        LocalDate minDate = LocalDate.now().minusDays(period);
        response.setMaximum(now);
        response.setMinimum(minDate);
        long max=0L;
        long min=0L;
        List<StatisticFewDays>list=new ArrayList<>();
        for (int i = 0; i <= period; i++) {
            StatisticFewDays statisticFewDays=new StatisticFewDays();
            LocalDate localDate = minDate.plusDays(i);
            statisticFewDays.setLocalDate(localDate);
            long amount= (long) (12000+Math.random()*6000-1000);
            if(amount>max) max=amount;
            if (i==0) min=amount;
            else if(amount<min) min=amount;
            statisticFewDays.setAmount(amount);
            list.add(statisticFewDays);
        }
        for (StatisticFewDays statisticFewDays : list) {
            if (statisticFewDays.getAmount().equals(max)){
                statisticFewDays.setIndexLabel("★ Max (uzCard)");
                statisticFewDays.setMarkerColor("red");
            }
            if (statisticFewDays.getAmount().equals(min)){
                statisticFewDays.setIndexLabel("⚑ Min (uzCard)");
                statisticFewDays.setMarkerColor("blue");
            }
        }
        response.setList(list);
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String s = null;
        try {
            s = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new UniversalException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  s;

    }

    public String getFewMonthHumo() {
        int monthValue = LocalDate.now().getMonthValue();
        long max=0;
        long min=0;
        List<StatisticFewMonth>list=new ArrayList<>();
        for (int i = monthValue-1; i >=0 ; i--) {
            LocalDate localDate = LocalDate.now().minusMonths(i);
            StatisticFewMonth statisticFewMonth=new StatisticFewMonth();
            long amount= (long) (12000+Math.random()*5000-5000);
            if(amount>max) max=amount;
            if (i==monthValue-1) min=amount;
            else if(amount<min) min=amount;
            statisticFewMonth.setAmount(amount);
            statisticFewMonth.setLabel(localDate.getMonth().name());
            list.add(statisticFewMonth);
        }
        for (StatisticFewMonth statisticFewMonth : list) {
            if (statisticFewMonth.getAmount().equals(max)) statisticFewMonth.setIndexLabel("★ Highest");
            if (statisticFewMonth.getAmount().equals(min)) statisticFewMonth.setIndexLabel("⚑ Lowest");
        }
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new UniversalException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
    public String getFewMonthUzCard() {
        int monthValue = LocalDate.now().getMonthValue();
        long max=0;
        long min=0;
        List<StatisticFewMonth>list=new ArrayList<>();
        for (int i = monthValue-1; i >=0 ; i--) {
            LocalDate localDate = LocalDate.now().minusMonths(i);
            StatisticFewMonth statisticFewMonth=new StatisticFewMonth();
            long amount= (long) (12000+Math.random()*5000-5000);
            if(amount>max) max=amount;
            if (i==monthValue-1) min=amount;
            else if(amount<min) min=amount;
            statisticFewMonth.setAmount(amount);
            statisticFewMonth.setLabel(localDate.getMonth().name());
            list.add(statisticFewMonth);
        }
        for (StatisticFewMonth statisticFewMonth : list) {
            if (statisticFewMonth.getAmount().equals(max)) statisticFewMonth.setIndexLabel("★ Highest");
            if (statisticFewMonth.getAmount().equals(min)) statisticFewMonth.setIndexLabel("⚑ Lowest");
        }
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new UniversalException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
