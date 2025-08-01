package com.example.tlstool.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtils {

    public static LocalDateTime convertToLocalDateTime(String isoString) {
        // 方法1: 使用 Instant 直接转换
        Instant instant = Instant.parse(isoString);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // 方法2: 使用 DateTimeFormatter
        // DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        // return LocalDateTime.parse(isoString, formatter);
    }
}
