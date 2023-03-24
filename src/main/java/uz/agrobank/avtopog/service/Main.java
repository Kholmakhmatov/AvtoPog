package uz.agrobank.avtopog.service;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        long period=10;
        LocalDate now = LocalDate.now();
        LocalDate start = now.minusYears(period);
        for (long i = 0; i <= period; i++) {
            LocalDate localDate = start.plusYears(i);
            System.out.println(localDate);
        }
    }
}
