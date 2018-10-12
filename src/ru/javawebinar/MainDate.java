package ru.javawebinar;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MainDate {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        System.out.println(System.currentTimeMillis() - date.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("YY/MM/dd");
        System.out.println(sdf.format(date));

        LocalDate ld = LocalDate.of(2018, 10, 11);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YY/MM/dd");
        System.out.println(dtf.format(ld));
    }
}
