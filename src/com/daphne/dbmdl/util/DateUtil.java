package com.daphne.dbmdl.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtil {
	private  static DateUtil  instance= null;
	Calendar calendar = new GregorianCalendar();
	public static DateUtil getInstance() {
		if (instance == null) {
			instance = new DateUtil();
			
		}
		return instance;
	}
	/**
	 * 此方法描述的是： 将当前日期时间 输出格式 yyyy-MM-dd hh:mm:ss 的字符.
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:27:03
	 */

	public  String formatCurrentDateToString() {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return df.format(calendar.getTime());
	}
}
