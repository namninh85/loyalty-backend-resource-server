package com.nin.xloyalty.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class ArrayUtil {
	static DateTimeFormatter formatter =
		    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.LONG )
		                     .withLocale( Locale.UK )
		                     .withZone( ZoneId.systemDefault() );
	
	public static String instantToString(Instant instant) {
		String output = formatter.format( instant );
		return output;
	}
}
