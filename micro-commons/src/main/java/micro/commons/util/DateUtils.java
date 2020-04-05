package micro.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Seconds;
import org.joda.time.Years;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Period;

/**
 * 日期工具类
 * 
 * @author gewx
 **/
public final class DateUtils {

	@SuppressWarnings({ "static-access", "rawtypes" })
	private static final ThreadLocal<SimpleDateFormat> FORMAT = new ThreadLocal().withInitial(() -> {
		SimpleDateFormat format = new SimpleDateFormat();
		format.setLenient(false);
		return format;
	});

	/**
	 * 日期格式校验
	 * 
	 * @author gewx
	 * @param strDate   日期字符串
	 * @param strFormat 格式化字符
	 * @return true-验证通过, false-验证失败
	 **/
	public static boolean validDate(String strDate, String strFormat) {
		SimpleDateFormat format = FORMAT.get();
		try {
			format.applyPattern(strFormat);
			format.parse(strDate);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 日期解析
	 * 
	 * @author gewx
	 * @param strDate   日期字符串
	 * @param strFormat 格式化字符
	 * @throws ParseException
	 * @return 日期对象
	 **/
	public static Date parseDate(String strDate, String strFormat) throws ParseException {
		SimpleDateFormat format = FORMAT.get();
		format.applyPattern(strFormat);
		return format.parse(strDate);
	}

	/**
	 * 计算日期差,毫秒
	 * 
	 * @author gewx
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return 日期差值
	 **/
	public static long timeDiffMillis(DateTime start, DateTime end) {
		return end.getMillis() - start.getMillis();
	}

	/**
	 * 计算日期差,秒
	 * 
	 * @author gewx
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return 日期差值
	 **/
	public static int timeDiffSeconds(DateTime start, DateTime end) {
		return Seconds.secondsBetween(start, end).getSeconds();
	}

	/**
	 * 计算日期差,分钟
	 * 
	 * @author gewx
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return 日期差值
	 **/
	public static int timeDiffMinutes(DateTime start, DateTime end) {
		return Minutes.minutesBetween(start, end).getMinutes();
	}

	/**
	 * 计算日期差,小时
	 * 
	 * @author gewx
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return 日期差值
	 **/
	public static int timeDiffHours(DateTime start, DateTime end) {
		return Hours.hoursBetween(start, end).getHours();
	}

	/**
	 * 计算日期差,天
	 * 
	 * @author gewx
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return 日期差值
	 **/
	public static int timeDiffDays(DateTime start, DateTime end) {
		return Days.daysBetween(start, end).getDays();
	}

	/**
	 * 计算日期差,月
	 * 
	 * @author gewx
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return 日期差值
	 **/
	public static int timeDiffMonths(DateTime start, DateTime end) {
		return Months.monthsBetween(start, end).getMonths();
	}

	/**
	 * 计算日期差,年
	 * 
	 * @author gewx
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return 日期差值
	 **/
	public static int timeDiffYears(DateTime start, DateTime end) {
		return Years.yearsBetween(start, end).getYears();
	}

	/**
	 * 计算日期差,年/月/日/时/分/秒
	 * 
	 * @author gewx
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return 日期差值Period对象
	 **/
	public static Period timeDiffPeriod(DateTime start, DateTime end) {
		Interval time = new Interval(start, end);
		return time.toPeriod();
	}
}
