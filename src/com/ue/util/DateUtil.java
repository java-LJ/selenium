package com.ue.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

   /**
    * 把指定日期对象转成指定格式的日期字符串
    */
   public static String formatDate(Date date, String format){
      String result="";
      SimpleDateFormat sdf=new SimpleDateFormat(format);
      if(date!=null){
         result=sdf.format(date);
      }
      return result;
   }

   /**
    * 把指定日期字符串转成指定格式的日期对象
    */
   public static Date formatString(String str,String format) throws Exception{
      if(StringUtil.isEmpty(str)){
         return null;
      }
      SimpleDateFormat sdf=new SimpleDateFormat(format);
      return sdf.parse(str);
   }

   /**
    * 获取当前日期的字符串
    */
   public static String getCurrentDateStr(){
      Date date=new Date();
      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return sdf.format(date);
   }
}