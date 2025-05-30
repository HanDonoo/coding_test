package util;



import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String DEFAULT_PATTERN = "MM/dd/yyyy";

    private static final ThreadLocal<SimpleDateFormat> threadLocalSdf = ThreadLocal.withInitial(() -> new SimpleDateFormat(DEFAULT_PATTERN));

    /**
     * parse date
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateStr) throws ParseException {
        if(StringUtils.isEmpty(dateStr)){
            return null;
        }
        return threadLocalSdf.get().parse(dateStr);
    }

    public static String formatDate(Date date){
        if (date == null) {
            return "N/A";
        }
        return threadLocalSdf.get().format(date);
    }

}
