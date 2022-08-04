package com.zane.test_ums.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

/**
 * 时间处理工具类
 * @author Zanezeng
 */
@Component
public class DateTimeUtil {
    private DateTimeUtil() {
        super();
    }

    public static LocalDateTime toZone(final LocalDateTime time, final ZoneId fromZone, final ZoneId toZone) {
        final ZonedDateTime zonedtime = time.atZone(fromZone);
        final ZonedDateTime converted = zonedtime.withZoneSameInstant(toZone);
        return converted.toLocalDateTime();
    }

    public static LocalDateTime toZone(final LocalDateTime time, final ZoneId toZone) {
        return DateTimeUtil.toZone(time, ZoneId.systemDefault(), toZone);
    }

    public static LocalDateTime toUtc(final LocalDateTime time, final ZoneId fromZone) {
        return DateTimeUtil.toZone(time, fromZone, ZoneOffset.UTC);
    }

    public static LocalDateTime toUtc(final LocalDateTime time) {
        return DateTimeUtil.toUtc(time, ZoneId.systemDefault());
    }

    public static LocalDateTime getUtc() {
        LocalDateTime now = LocalDateTime.now();
        return toUtc(now);
    }
}
