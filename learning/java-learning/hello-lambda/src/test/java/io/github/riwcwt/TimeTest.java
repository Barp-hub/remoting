package io.github.riwcwt;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Created by michael on 2017-03-24.
 */
public class TimeTest {

    @Test
    public void date() {
        LocalDate today = LocalDate.now();
        System.out.println("Today's Local date : " + today);

        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        System.out.printf("Year : %d Month : %d day : %d \t %n", year, month, day);

        LocalDate date = LocalDate.of(2017, 3, 24);
        if (date.equals(today)) {
            System.out.printf("Today %s and date %s are same date %n", today, date);
        }

        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("Today is : " + today);
        System.out.println("Date after 1 week : " + nextWeek);

        LocalDate previousYear = today.minus(1, ChronoUnit.YEARS);
        System.out.println("Date before 1 year : " + previousYear);
        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
        System.out.println("Date after 1 year : " + nextYear);

    }

    @Test
    public void time() {
        LocalTime time = LocalTime.now();
        System.out.println("local time now : " + time);

        LocalTime newTime = time.plusHours(2); // adding two hours
        System.out.println("Time after 2 hours : " + newTime);

    }

    @Test
    public void clock() {
        // Returns the current time based on your system clock and set to UTC.
        Clock clock = Clock.systemUTC();
        System.out.println("Clock : " + clock);

        // Returns time based on system clock zone Clock defaultClock =
        Clock.systemDefaultZone();
        System.out.println("Clock : " + clock);

    }

    @Test
    public void instant() {
        Instant timestamp = Instant.now();
        System.out.println("What is value of this instant " + timestamp);

        System.out.println("What is value of this instant " + timestamp.plus(10, ChronoUnit.MINUTES));
    }

    @Test
    public void formatter() {
        String dayAfterTommorrow = "20140116";
        LocalDate formatted = LocalDate.parse(dayAfterTommorrow,
                DateTimeFormatter.BASIC_ISO_DATE);
        System.out.printf("Date generated from String %s is %s %n", dayAfterTommorrow, formatted);

        String goodFriday = "03 18 2014";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy");
            LocalDate holiday = LocalDate.parse(goodFriday, formatter);
            System.out.printf("Successfully parsed String %s, date is %s%n", goodFriday, holiday);
        } catch (DateTimeParseException ex) {
            System.out.printf("%s is not parsable!%n", goodFriday);
            ex.printStackTrace();
        }

        LocalDateTime arrivalDate = LocalDateTime.now();
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            String landing = arrivalDate.format(format);
            System.out.printf("Arriving at : %s %n", landing);
        } catch (DateTimeException ex) {
            System.out.printf("%s can't be formatted!%n", arrivalDate);
            ex.printStackTrace();
        }

    }

}
