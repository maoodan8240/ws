package ws.common.utils.date;

/**
 * <pre>
 * G Era 标志符 Text AD 
 * y 年 Year 1996; 96 
 * M 年中的月份 Month July; Jul; 07 
 * w 年中的周数 Number 27 
 * W 月份中的周数 Number 2 搜索
 * D 年中的天数 Number 189 
 * d 月份中的天数 Number 10 
 * F 月份中的星期 Number 2 
 * E 星期中的天数 Text Tuesday; Tue 
 * a Am/pm 标记 Text PM 
 * H 一天中的小时数（0-23） Number 0 
 * k 一天中的小时数（1-24） Number 24 
 * K am/pm 中的小时数（0-11） Number 0 
 * h am/pm 中的小时数（1-12） Number 12 
 * m 小时中的分钟数 Number 30 
 * s 分钟中的秒数 Number 55 
 * S 毫秒数 Number 978 
 * z 时区 General time zone Pacific Standard Time; PST; GMT-08:00 
 * Z 时区 RFC 822 time zone -0800
 * 
 * </pre>
 */
public enum WsDateFormatEnum {
    /**
     * 年
     */
    yyyy("yyyy"), //
    /**
     * 年中的月份
     */
    MM("MM"),
    /**
     * 月份中的天数
     */
    dd("dd"), //
    /**
     * 一天中的小时数（0-23）
     */
    HH("HH"), //
    /**
     * 小时中的分钟数
     */
    mm("mm"), //
    /**
     * 分钟中的秒数
     */
    ss("ss"), //

    /**
     * 返回星期一...
     */
    EEEE("EEEE"),
    /**
     * 返回一月...
     */
    MMMM("MMMM"),
    /**
     * 返回下午
     */
    aaaa("aaaa"),

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    yyyy_MM_dd$HH_mm_ss("yyyy-MM-dd HH:mm:ss"),
    /**
     * 记录log所用 yyyy-MM-dd HH:mm:ss.SSS
     */
    LOGGER_TIME("yyyy-MM-dd HH:mm:ss.SSS"), //

    yyyyMMdd("yyyyMMdd"), //
    yyyyMM("yyyyMM"), //
    MMdd("MMdd"), //

    HHmmss("HHmmss"), //
    HHmm("HHmm"), //
    mmss("mmss"), //

    yyyyMMddHHmmss("yyyyMMddHHmmss"), //
    yyyyMMddHHmm("yyyyMMddHHmm"), //
    yyyyMMddHH("yyyyMMddHH"), //
    ;

    private String format;

    private WsDateFormatEnum(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
