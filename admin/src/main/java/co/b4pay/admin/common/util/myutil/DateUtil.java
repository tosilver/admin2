package co.b4pay.admin.common.util.myutil;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 日期工具类
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2018-02-08
 * Time: 9:18
 */
public class DateUtil {

    /**
     * 根据日期字符串格式获取该时间的开始时间
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Long getBeginTimeOfDateStr(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return getBeginTime(localDate);
    }

    /**
     * 根据年份获取该年开始时间
     *
     * @param year
     * @return
     */
    public static Long getBeginTimeOfYear(Integer year) {
        return getBeginTimeOfYearMonth(year, 1);
    }


    /**
     * 根据年月获取该月份的开始时间
     *
     * @param year
     * @param month
     * @return
     */
    public static Long getBeginTimeOfYearMonth(Integer year, Integer month) {
        if (year == null || year < 0) {
            year = LocalDate.now().getYear();
        }
        if (month == null || month < 0) {
            month = LocalDate.now().getMonthValue();
        }
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        return getBeginTime(localDate);
    }

    /**
     * 获取开始时间
     *
     * @param localDate
     * @return
     */
    private static Long getBeginTime(LocalDate localDate) {
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant()).getTime();
    }

    /**
     * 根据日期字符串格式获取该时间的结束时间
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Long getEndTimeOfDateStr(String date) {
        LocalDate endOfMonth = LocalDate.parse(date);
        return getEndTime(endOfMonth);
    }

    /**
     * 根据年份获取该年结束时间时间
     *
     * @param year
     * @return
     */
    public static Long getEndTimeOfYear(Integer year) {
        return getEndTimeOfYearMonth(year, 12);
    }

    /**
     * 根据年月获取该月份的结束时间
     *
     * @param year
     * @param month
     * @return
     */
    public static Long getEndTimeOfYearMonth(Integer year, Integer month) {
        if (year == null || year < 0) {
            year = LocalDate.now().getYear();
        }
        if (month == null || month < 0) {
            month = LocalDate.now().getMonthValue();
        }
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        return getEndTime(endOfMonth);
    }

    /**
     * 获取结束时间
     *
     * @param endOfMonth
     * @return
     */
    private static Long getEndTime(LocalDate endOfMonth) {
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant()).getTime();
    }

    /**
     * 获取某个日期向上推N天的时间集合
     *
     * @param date
     * @param minusDays
     * @return
     */
    public static Map<String, Map<String, Long>> minusDayOfDate(String date, int minusDays, Boolean isInclude) {
        Map<String, Map<String, Long>> timeMap = new LinkedHashMap<>();
        LocalDate day = LocalDate.parse(date);
        if (minusDays > 0) {
            int beginIndex = 1;
            int endIndex = minusDays;
            if (isInclude != null && isInclude) {
                beginIndex = 0;
                endIndex = minusDays - 1;
            }
            for (int i = beginIndex; i <= endIndex; i++) {
                Map<String, Long> startAndEndTime = new HashMap<>();
                LocalDate localDate = day.minusDays(i);
                startAndEndTime.put("startLongTime", getBeginTime(localDate));
                startAndEndTime.put("endLongTime", getEndTime(localDate));
                timeMap.put(localDate.toString(), startAndEndTime);
            }
        }
        return timeMap;
    }

    /**
     * 获取某个日期向上推N周的时间集合
     *
     * @param date
     * @param minusWeeks
     * @return
     */
    public static Map<String, Map<String, Long>> minushWeekOfDate(String date, int minusWeeks, Boolean isInclude) {
        Map<String, Map<String, Long>> timeMap = new LinkedHashMap<>();
        LocalDate day = LocalDate.parse(date);
        if (minusWeeks > 0) {
            int beginIndex = 1;
            int endIndex = minusWeeks;
            if (isInclude != null && isInclude) {
                beginIndex = 0;
                endIndex = minusWeeks - 1;
            }
            for (int i = beginIndex; i <= endIndex; i++) {
                Map<String, Long> startAndEndTime = new HashMap<>();
                LocalDate startLocalDate = day.minusWeeks(i).with(DayOfWeek.MONDAY);
                LocalDate endLocalDate = day.minusWeeks(i).with(DayOfWeek.SUNDAY);
                startAndEndTime.put("startLongTime", getBeginTime(startLocalDate));
                startAndEndTime.put("endLongTime", getEndTime(endLocalDate));
                timeMap.put(startLocalDate.toString() + "/" + endLocalDate.toString(), startAndEndTime);
            }
        }
        return timeMap;
    }

    /**
     * 获取某个日期向上推N月的时间集合
     *
     * @param date
     * @param minusMonths
     * @return
     */
    public static Map<String, Map<String, Long>> minushMonthOfDate(String date, int minusMonths, Boolean isInclude) {
        Map<String, Map<String, Long>> timeMap = new LinkedHashMap<>();
        LocalDate day = LocalDate.parse(date);
        if (minusMonths > 0) {
            int beginIndex = 1;
            int endIndex = minusMonths;
            if (isInclude != null && isInclude) {
                beginIndex = 0;
                endIndex = minusMonths - 1;
            }
            for (int i = beginIndex; i <= endIndex; i++) {
                Map<String, Long> startAndEndTime = new HashMap<>();
                LocalDate startLocalDate = day.minusMonths(i).with(TemporalAdjusters.firstDayOfMonth());
                LocalDate endLocalDate = day.minusMonths(i).with(TemporalAdjusters.lastDayOfMonth());
                startAndEndTime.put("startLongTime", getBeginTime(startLocalDate));
                startAndEndTime.put("endLongTime", getEndTime(endLocalDate));
                timeMap.put(startLocalDate.getYear() + "-" + startLocalDate.getMonthValue(), startAndEndTime);
            }
        }
        return timeMap;
    }


    /**
     * 获取某年的十二个月的时间集合
     *
     * @param year
     * @return
     */
    public static Map<Integer, Map<String, Long>> getMonthsOfYear(Integer year) {
        Map<Integer, Map<String, Long>> timeMap = new LinkedHashMap<>();
        for (int i = 1; i <= 12; i++) {
            Map<String, Long> startAndEndTime = new HashMap<>();
            startAndEndTime.put("startLongTime", getBeginTimeOfYearMonth(year, i));
            startAndEndTime.put("endLongTime", getEndTimeOfYearMonth(year, i));
            timeMap.put(i, startAndEndTime);
        }
        return timeMap;
    }

    /**
     * 长整型的时间类型转换成LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate longToLocalDate(Long date) {
        return new Date(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * LocalDate转换成长整型的时间类型
     *
     * @param localDate
     * @return
     */
    public static Long localDateToLong(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime();
    }
}
