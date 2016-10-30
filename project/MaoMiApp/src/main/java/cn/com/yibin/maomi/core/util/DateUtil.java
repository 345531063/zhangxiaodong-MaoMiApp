package cn.com.yibin.maomi.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtil
{
	public final static String FULL_TIME_FORMAT      		   		 		= "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String DATE_TIME_FORMAT      		   		 		= "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_FORMAT           		  			 		= "yyyy-MM-dd";
	public final static String DATE_NOTLINE_FORMAT                		= "yyyyMMdd";
	public final static String DATEMONTH_NOTLINE_FORMAT          = "yyyyMM";
	public final static String DATE_TIME_HOUR_MINUTE_FORMAT     = "yyyy-MM-dd HH:mm";
	public final static String HOUR_MINUTE_FORMAT    		   			 = "HH:mm";
	public final static String HOUR_FORMAT    		   		   				 = "HH";
	public final static String YEAR_MONTH_FORMAT			   			 = "yyyy-MM";
	public final static String DATENOMARL			   							 = "yyyyMMddHHmmss";
	public final static String HALFYEAR_MONTH_FORMAT					 = "yyMMddHHmm";
	public  static boolean isSameDay(Date d1,Date d2,TimeZone timeZone){
		Calendar  c1  = Calendar.getInstance(timeZone);
		c1.setTime(d1);
		Calendar  c2  = Calendar.getInstance(timeZone);
		c2.setTime(d2);
		return c1.get(Calendar.DAY_OF_MONTH)  == c2.get(Calendar.DAY_OF_MONTH) ;
	}
	public static int getIntervalDays(Date startDate, Date endDate)
	{
		if ((null == startDate) || (null == endDate))
		{
			return -1;
		}
		long intervalMilli = (endDate.getTime() - startDate.getTime());
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}
	public static long getIntervalMilliSeconds(Date startDate, Date endDate)
	{
		if ((null == startDate) || (null == endDate))
		{
			return -1;
		}
		long intervalMilli = (endDate.getTime() - startDate.getTime());
		return intervalMilli;
	}
	public static int getIntervalSeconds(Date startDate, Date endDate)
	{
		if ((null == startDate) || (null == endDate))
		{
			return -1;
		}
		long intervalMilli = (endDate.getTime() - startDate.getTime());
		return (int) (intervalMilli / (1000));
	}
	public static int getIntervalMinutes(Date startDate, Date endDate)
	{
		if ((null == startDate) || (null == endDate))
		{
			return -1;
		}
		long intervalMilli = (endDate.getTime() - startDate.getTime());
		return (int) (intervalMilli / ( 60 * 1000));
	}
	public static int getIntervalHours(Date startDate, Date endDate)
	{
		if ((null == startDate) || (null == endDate))
		{
			return -1;
		}
		long intervalMilli = (endDate.getTime() - startDate.getTime());
		return (int) (intervalMilli / (60 * 60 * 1000));
	}
	public static Integer getSystemFullYear()
	{
		Calendar aCalendar = Calendar.getInstance();
		Integer year       = aCalendar.get(Calendar.YEAR);
		return year;
	}

	public static String getSystemDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	public static String getSystemDateHour()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		return sdf.format(new Date());
	}
	public static String getSystemDateHourMinute()
	{
		SimpleDateFormat sdf = new SimpleDateFormat(HOUR_MINUTE_FORMAT);
		return sdf.format(new Date());
	}
	public static String getSystemDateMinute()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("mm");
		return sdf.format(new Date());
	}
	public static String getSystemDateTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	public static String getSystemDateTime(TimeZone timeZone)
	{
		return getDateTime(new Date(),timeZone);
	}
	public static String getSystemDateTime(String timeZoneId)
	{
		return getDateTime(new Date(),TimeZone.getTimeZone(timeZoneId));
	}
    
	public static String getDateTime(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null == date) {
			date = new Date();
		}
		return sdf.format(date);
	}
	public static String getDateTime(Date date,TimeZone timeZone)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (null == date) {
			date = new Date();
		}
		sdf.setTimeZone(timeZone);
		return sdf.format(date);
	}
	public static String getDateTime(Date date,String timeZoneId)
	{
		return getDateTime(date,TimeZone.getTimeZone(timeZoneId));
	}
	public static String getDateTime(String dateStr,TimeZone timeZone)
	{
		Date date  = DateUtil.getDateTimeByDateStr(dateStr);
		return getDateTime(date,timeZone);
	}
	
    public static Date  getDateTimeByDateStr(String dateStr) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return new Date();
    }
	/**
	 * @param dateFormat
	 *            输出日期格式，形如：yyyy-MM-dd HH:mm:ss
	 * @return 格式化后的日期字符串
	 */
	public static String getDateTimeByFormat(String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}

	public static String getDateTimeByFormat(Date date, String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}
	public static String getDateTimeByFormat(Date date, String dateFormat,TimeZone timeZone)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setTimeZone(timeZone);
		return sdf.format(date);
	}

	/**
	 * @param dateStr
	 *            日期字符串，形如：yyyy-MM-dd HH:mm:ss
	 * @param dateFormat
	 *            输出日期格式，形如：yyyy-MM-dd HH:mm:ss
	 * @return 格式化后的日期字符串
	 */
	public static Date getDateByFormat(String dateStr, String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static Date getDateTimeByFormat(String dateStr, String dateFormat,TimeZone timeZone)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setTimeZone(timeZone);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static Date getDateTimeByFormat(String dateStr, String dateFormat)
	{
		return getDateTimeByFormat(dateStr,dateFormat,TimeZone.getDefault());
	}
	
	/**
	 * 获取 yyyy-MM-dd 日期
	 * @param dateStr 日期时间字符串
	 * @param dateFormat dateStr的字符串格式
	 * @return ' yyyy-MM-dd '格式日期
	 */
	public static String getFormatedDate(String dateStr, String dateFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
		
	}
	
	 /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int    w = getWeekOfDateIndex(dt,TimeZone.getDefault());
        return weekDays[w];
    }
    public static String getWeekOfDate(String dateStr) {
    	Date  dt  = DateUtil.getDateByFormat(dateStr, "yyyy-MM-dd");
        return getWeekOfDate(dt);
    }
    public static int   getWeekOfDateIndex(Date dt,TimeZone timeZone){
    	 Calendar cal = Calendar.getInstance();
         cal.setTime(dt);
         cal.setTimeZone(timeZone);
         
         int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
         if (w < 0){
             w = 0;
         }
         return w;
    }
    public static int   getSystemWeekOfDateIndex(){
    	return getWeekOfDateIndex(new Date(),TimeZone.getDefault());
    }
    
    public static int   getSystemWeekOfDateIndex(String timeZone){
    	return getWeekOfDateIndex(new Date(),TimeZone.getTimeZone(timeZone));
    }
    public static Date getBeforeDate(Date currentDate , int intervalDays){
    	long beforeDateTimeMillis = currentDate.getTime() - (intervalDays * 24 * 60 * 60 *1000);
    	return new Date(beforeDateTimeMillis);
    }
    public static Date getAfterDate(Date currentDate , int intervalDays){
    	long afterDateTimeMillis = currentDate.getTime() + (intervalDays * 24 * 60 * 60 *1000);
    	return new Date(afterDateTimeMillis);
    }
    /***获取上一个小时的数据**/
    public static Date getBeforeDateHour(Date currentDate , int intervalHours){
    	long afterDateTimeMillis = currentDate.getTime() - (intervalHours * 60 * 60 *1000);
    	return new Date(afterDateTimeMillis);
    }
    public static String getDateTimeByTimeZone(String timeZone){
		// 当前系统默认时区的时间：
		Calendar calendar = new GregorianCalendar();
		// 美国洛杉矶时区
		//TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
		TimeZone tz = TimeZone.getTimeZone(timeZone);
		// 时区转换
		calendar.setTimeZone(tz);
		return DateUtil.getDateTime(calendar.getTime(),tz);
    }
    public static Calendar getCalendarByTimeZone(Date date,TimeZone timeZone){
    	// 当前系统默认时区的时间：
    	Calendar calendar = Calendar.getInstance(timeZone);
    	calendar.setTime(date);
    	return calendar;
    }
    public static Calendar getCalendarByTimeZone(String timeZone,long timeStamp){
    	// 当前系统默认时区的时间：
    	return getCalendarByTimeZone(new Date(timeStamp),TimeZone.getTimeZone(timeZone));
    }
  //获取上一个月
    public static String getCalendarBeforeMonthByTimeZone(int inversDay,String timeZoneId,String formate){
    	  TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
    	  Calendar c = Calendar.getInstance(timeZone);
          c.add(Calendar.MONTH, -inversDay);
    	return new SimpleDateFormat(formate).format(c.getTime());
    }
    public static String getMonthSpace(String date1, int imonth)
			throws ParseException {
		SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd");
		Calendar 				c1 	= Calendar.getInstance();
		c1.setTime(sdf.parse(date1));
		c1.add(Calendar.MONTH, imonth);
        // System.out.println("上个月是："+new SimpleDateFormat("yyyy-MM").format(c1.getTime()));
        String dateTime  = new SimpleDateFormat("yyyy-MM").format(c1.getTime());
        return dateTime;
	}
    public static void main(String[] args) {
//    	String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
//    	System.out.println(DateUtil.getDateTime(new Date(new Date().getTime() - 2  * 60 * 60 *1000 )));
//        int    w = getWeekOfDateIndex(new Date(new Date().getTime() - 2  * 60 *60*1000 ),TimeZone.getTimeZone("America/Los_Angeles"));
//		System.out.println(weekDays[w]);
    	System.out.println("=========0000000000========="+DateUtil.getDateTimeByFormat(DateUtil.getBeforeDateHour(new Date(),1 ),DateUtil.DATE_TIME_FORMAT));
    	String dateTime = "201612041212";
    	Date 					date 					= DateUtil.getDateByFormat(dateTime, "yyMMddHHmm");
		dateTime 				= DateUtil.getDateTime(date);
		System.out.println("==============dateTime============"+dateTime);
    	//获取上一个月
    	Date  startDate   =  DateUtil.getDateTimeByDateStr("2016-05-04 12:33:33");
    	Date  endDate   =  DateUtil.getDateTimeByDateStr("2016-04-27 08:01:15.000");
    	System.out.println("========DateUtil.getIntervalMilliSeconds(startDate, endDate)======"+DateUtil.getIntervalMilliSeconds(startDate, endDate));
    	  Calendar c = Calendar.getInstance();
          c.add(Calendar.MONTH, -1);
          try {
			System.out.println("上个月是3333："+getMonthSpace(DateUtil.getDateTimeByFormat(DateUtil.DATE_FORMAT),-3));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
			System.out.println("---------------"+getMonthSpace("2015-01-12",-1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
