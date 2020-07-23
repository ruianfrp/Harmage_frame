package com.harmonycloud.util.date;

import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void insertUserIdMap(JSONObject jsonObject) {
        Calendar c_begin = new GregorianCalendar();
        Calendar c_end = new GregorianCalendar();
        Calendar calendar = Calendar.getInstance();
        c_end.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, -178);
        System.out.println(simpleDateFormat.format(calendar.getTime()));
        System.out.println(calendar.get(Calendar.YEAR));
        c_begin.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        c_end.add(Calendar.DAY_OF_YEAR, 1);  //结束日期下滚一天是为了包含最后一天
        while (c_begin.before(c_end)) {
            if (c_end.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                String monday = simpleDateFormat.format(c_end.getTime().getTime());
                Calendar c_friday = new GregorianCalendar();
                c_friday.setTimeInMillis(c_end.getTime().getTime());
//                System.out.println("时间：" + simpleDateFormat.format(c_friday.getTime().getTime()));
                c_friday.add(Calendar.DAY_OF_MONTH, 5);
//                System.out.println("时间：" + simpleDateFormat.format(c_friday.getTime().getTime()));
                String friday = simpleDateFormat.format(c_friday.getTime().getTime());
                jsonObject.put(monday + "~" + friday, "There is no data");
            }
            c_end.add(Calendar.DAY_OF_MONTH, -1);
        }
    }

    public static List<String> getProjectDate(Date projectStartTime, Date projectEndTime) {
        Calendar c_begin = new GregorianCalendar();
        Calendar c_end = new GregorianCalendar();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(projectEndTime);
        c_end.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.setTime(projectStartTime);
        System.out.println(simpleDateFormat.format(calendar.getTime()));
        System.out.println(calendar.get(Calendar.YEAR));
        c_begin.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        c_begin.add(Calendar.DAY_OF_YEAR, -6);  //结束日期下滚一天是为了包含最后一天
        c_end.add(Calendar.DAY_OF_YEAR, -6);  //结束日期下滚一天是为了包含最后一天
        List<String> dateList = new ArrayList<>();
        while (c_begin.before(c_end)) {
            if (c_end.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                String monday = simpleDateFormat.format(c_end.getTime().getTime());
                dateList.add(monday);
            }
            c_end.add(Calendar.DAY_OF_MONTH, -1);
        }
        return dateList;
    }

    public static Date format(Date source){
        String sourceString = simpleDateFormat.format(source);
        Date desDate = java.sql.Date.valueOf(sourceString);
        return desDate;
    }
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        insertUserIdMap(jsonObject);
        System.out.println(jsonObject);

        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, -179);
        String date = simpleDateFormat.format(calendar.getTime());
        System.out.println(date);
    }
}
