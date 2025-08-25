package com.example.tlstool.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtils {

    public static LocalDateTime convertToLocalDateTime(String isoString) {
        // 方法1: 使用 Instant 直接转换
        Instant instant = Instant.parse(isoString);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // 方法2: 使用 DateTimeFormatter
        // DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        // return LocalDateTime.parse(isoString, formatter);
    }

    public static String localDateTimetoCron(LocalDateTime dateTime) {
        return String.format("%d %d %d %d %d ? %d",
                dateTime.getSecond(),   // 秒（0-59）
                dateTime.getMinute(),   // 分（0-59）
                dateTime.getHour(),     // 时（0-23）
                dateTime.getDayOfMonth(), // 日（1-31）
                dateTime.getMonthValue(), // 月（1-12）
                dateTime.getYear()      // 年（如 2023）
        );
    }
}
