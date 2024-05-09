package com.ql.www.utils;

import cn.hutool.core.date.DateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author chocoh
 */
public class TimeUtil {
    private static final String TODAY_FORMAT = "HH:mm";
    private static final String YESTERDAY_FORMAT = "昨天";
    private static final String DATE_FORMAT = "yyyy/MM/dd";

    public static String formatConversationTime(LocalDateTime targetDateTime) {
        LocalDate targetDate = targetDateTime.toLocalDate();
        if (targetDate.isEqual(LocalDate.now())) {
            return DateUtil.format(targetDateTime, TODAY_FORMAT);
        } else if (targetDate.isEqual(LocalDate.now().minusDays(1))) {
            return DateUtil.format(targetDateTime, YESTERDAY_FORMAT);
        } else {
            return DateUtil.format(targetDateTime, DATE_FORMAT);
        }
    }
}
