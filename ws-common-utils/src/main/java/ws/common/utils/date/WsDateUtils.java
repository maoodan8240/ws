package ws.common.utils.date;

import ws.common.utils.exception.WsDateParseException;

import java.util.Locale;

public class WsDateUtils {
    /**
     * 以固定格式{@link WsDateFormatEnum}转换一个date对象为另一个date对象
     *
     * @param date
     * @param wsDateFormatEnum {@link WsDateFormatEnum}
     * @return
     */
    public static java.util.Date dateToFormatDate(java.util.Date date, WsDateFormatEnum wsDateFormatEnum) {
        return dateToFormatDate(dateToFormatStr(date, wsDateFormatEnum), wsDateFormatEnum);
    }

    /**
     * 将字符串以固定格式{@link WsDateFormatEnum}转换为一个date
     *
     * @param dateStr
     * @param wsDateFormatEnum {@link WsDateFormatEnum}
     * @return
     */
    public static java.util.Date dateToFormatDate(String dateStr, WsDateFormatEnum wsDateFormatEnum) {
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, Locale.CHINESE, wsDateFormatEnum.getFormat());
        } catch (Exception e) {
            throw new WsDateParseException("", e);
        }
    }

    /**
     * 以固定格式{@link WsDateFormatEnum}转换一个date对象为字符串
     *
     * @param date
     * @param wsDateFormatEnum {@link WsDateFormatEnum}
     * @return
     */
    public static String dateToFormatStr(java.util.Date date, WsDateFormatEnum wsDateFormatEnum) {
        return org.apache.commons.lang3.time.DateFormatUtils.format(date, wsDateFormatEnum.getFormat(), Locale.CHINESE);
    }

    /**
     * <pre>
     * 调用 dateToFormatStr(new Date(), WsDateFormatEnums.EEEE)
     * 获取当前日期的 星期一、星期二、星期三、星期四、星期五、星期六、星期日
     * 将星期转为数字
     * </pre>
     *
     * @param dayOfWeekStr 星期一、星期二、星期三、星期四、星期五、星期六、星期日
     * @return {@link WsWeekEnum}
     */
    public static int formatDayOfWeekStrToNum(String dayOfWeekStr) {
        WsWeekEnum enums = WsWeekEnum.parse(dayOfWeekStr);
        if (enums == null) {
            return -1;
        }
        return enums.getValue();
    }

    /**
     * 获取日期所属周中星期几对应的日期
     *
     * @param date
     * @param weekStrEnum
     * @return 日期格式为：yyyyMMdd
     */
    public static String getweekOfDay(java.util.Date date, WsWeekEnum weekStrEnum) {
        String dateWeekStr = dateToFormatStr(date, WsDateFormatEnum.EEEE);
        int dateWeekNum = formatDayOfWeekStrToNum(dateWeekStr);
        java.util.Date targetDate = org.apache.commons.lang3.time.DateUtils.addDays(date, weekStrEnum.getValue() - dateWeekNum);
        return dateToFormatStr(targetDate, WsDateFormatEnum.yyyyMMdd);
    }
}
