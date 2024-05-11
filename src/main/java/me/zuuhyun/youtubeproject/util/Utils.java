package me.zuuhyun.youtubeproject.util;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;

public class Utils {

    public static long generateRandomNum(int start, int end) {
        SecureRandom secureRandom = new SecureRandom();
        return start + secureRandom.nextInt(end);
    }

    public static Date getStartDate(Date endDate, Period period){
        Date startDate;
        if (period == Period.WEEK){
            startDate = java.sql.Date.valueOf(LocalDate.now().minusWeeks(1));
        }else if (period == Period.MONTH){
            startDate = java.sql.Date.valueOf(LocalDate.now().minusMonths(1));
        }else {/*Period.DAY*/
            startDate = endDate;
        }
        return startDate;
    }
}
