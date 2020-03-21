package com.nin.xloyalty.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.util.StringUtils;

public class DateUtil {
	static String DATE_FORMAT = "dd/MM/yyyy";
	static DateTimeFormatter formatter =
			DateTimeFormatter.ofPattern(DATE_FORMAT)
		                     .withLocale( Locale.UK )
		                     .withZone( ZoneId.systemDefault() );
	
	public static String instantToString(Instant instant) {
		String output = formatter.format( instant );
		return output;
	}
	
	public static String longDateToString(long longDate) {
		if(longDate < 10000000000L) {
			longDate = longDate * 1000;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(longDate);
		return instantToString(calendar.toInstant());
	}
	
	public static Long strindDateToLong(String strDate) {
		Long longDate = null;
		if(StringUtils.hasText(strDate)) return null;
		try {
			Date date1= new SimpleDateFormat(DATE_FORMAT).parse(strDate);
			return date1.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return longDate;
	}
	
	public static Date stringToDate(String sDate) {
		
		 SimpleDateFormat formatter1=new SimpleDateFormat(DATE_FORMAT);  
		 try {
			return formatter1.parse(sDate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String dateToString(Date date) {
		
		 SimpleDateFormat formatter1=new SimpleDateFormat(DATE_FORMAT);  
		 try {
			return formatter1.format(date);  
		} catch (Exception e) {
			return null;
		}
	}

}
