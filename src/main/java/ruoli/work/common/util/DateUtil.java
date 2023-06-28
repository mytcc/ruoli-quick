package ruoli.work.common.util;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DAY_PATTERN = "yyyy-MM-dd";
    public static final String DAY_PATTERN_E = "yyyy-MM-dd E";
    public static final DateTimeFormatter TIME_FORMATTER =DateTimeFormatter.ofPattern(TIME_PATTERN);
    public static final DateTimeFormatter DATE_FORMATTER =DateTimeFormatter.ofPattern(DAY_PATTERN);
    public static final DateTimeFormatter DATE_FORMATTER_E =DateTimeFormatter.ofPattern(DAY_PATTERN_E);

    // localDate 转 字符串
    public static String toDateString(LocalDate localDate){
        if(localDate==null){
            return null;
        }
        return localDate.format(DATE_FORMATTER);
    }
    public static String toDateStringE(LocalDate localDate){
        if(localDate==null){
            return null;
        }
        return localDate.format(DATE_FORMATTER_E);
    }
    // localDateTime 转 字符串
    public static String toTimeString(LocalDateTime localDateTime){
        if(localDateTime==null){
            return null;
        }
        return localDateTime.format(TIME_FORMATTER);
    }
    public static String toDateString(Date date){
        if(date == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DAY_PATTERN);
        return sdf.format(date);
    }
    public static String toTimeString(Date date){
        if(date == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN);
        return sdf.format(date);
    }
    public static String toDateString(long ms){
        Date date = new Date();
        date.setTime(ms);
        return toDateString(date);
    }
    public static String toTimeString(long ms){
        Date date = new Date();
        date.setTime(ms);
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN);
        return sdf.format(date);
    }

    // 字符串 转 LocalDate
    public static LocalDate toLocalDate(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        // 异常数据处理， 2014-04-1 -> 2014-04-01
        //             2014-4-01 -> 2014-04-01
        String[] array = str.split("-");
        if(array.length==3){
            if(array[1].length()==1){
                array[1]="0"+array[1];
            }
            if(array[2].length()==1){
                array[2]="0"+array[2];
            }
        }
        String result = StringUtils.join(array, "-");
        return LocalDate.parse(result,DATE_FORMATTER);
    }

    public static Date toDate(LocalDate localDate){
        ZoneId zone= ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }
    @SneakyThrows
    public static Date toDate(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat(DAY_PATTERN);
        return sdf.parse(string);
    }

    public static String convertToDuration(long duration){
        return null;
    }
}
