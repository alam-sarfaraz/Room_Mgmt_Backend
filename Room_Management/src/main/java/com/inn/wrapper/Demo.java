package com.inn.wrapper;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Demo {


  private Map<String, Date> getPaymentDateForEmailAndNotification() {
    Map<String, Date> resultMap = new HashMap<>();
    Date curDate = new Date();
    Calendar cal = Calendar.getInstance();
      cal.setTime(curDate);
      Date startDate = cal.getTime();
      for (int i = 0; i < 5; i++) {
        if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
          cal.add(Calendar.DATE, 3);
          startDate = cal.getTime();
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
          cal.add(Calendar.DATE, 2);
          startDate = cal.getTime();
        } else {
          cal.add(Calendar.DATE, 1);
          startDate = cal.getTime();
        }
      }
      Date endDate = startDate;
      for (int i = 0; i < 3; i++) {
        if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
          cal.add(Calendar.DATE, 3);
          endDate = cal.getTime();
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
          cal.add(Calendar.DATE, 2);
          endDate = cal.getTime();
        } else {
          cal.add(Calendar.DATE, 1);
          endDate = cal.getTime();
        }
      }
      Date notificationDateFrom = endDate;
      if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
        cal.add(Calendar.DATE, 2);
        endDate = cal.getTime();
      }
      Date notificationDateTo = endDate;
      resultMap.put("EMAILSTARTDATE", startDate);
      resultMap.put("EMAILENDDATE", endDate);
      resultMap.put("NOTIFICATIONSTARTDATE", notificationDateFrom);
      resultMap.put("NOTIFICATIONENDDATE", notificationDateTo);
      return resultMap;

  }


  public static void main(String[] args) {
    Map<String, Date> resultMap = new HashMap<>();
    Date curDate = new Date();
    Calendar cal = Calendar.getInstance();
      cal.setTime(curDate);
      Date startDate = cal.getTime();
      for (int i = 0; i < 5; i++) {
        if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
          cal.add(Calendar.DATE, 3);
          startDate = cal.getTime();
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
          cal.add(Calendar.DATE, 2);
          startDate = cal.getTime();
        } else {
          cal.add(Calendar.DATE, 1);
          startDate = cal.getTime();
        }
      }
      Date endDate = startDate;
      for (int i = 0; i < 3; i++) {
        if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
          cal.add(Calendar.DATE, 3);
          endDate = cal.getTime();
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
          cal.add(Calendar.DATE, 2);
          endDate = cal.getTime();
        } else {
          cal.add(Calendar.DATE, 1);
          endDate = cal.getTime();
        }
      }
      Date notificationDateFrom = endDate;
      if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
        cal.add(Calendar.DATE, 2);
        endDate = cal.getTime();
      }
      Date notificationDateTo = endDate;
      resultMap.put("EMAILSTARTDATE", startDate);
      resultMap.put("EMAILENDDATE", endDate);
      resultMap.put("NOTIFICATIONSTARTDATE", notificationDateFrom);
      resultMap.put("NOTIFICATIONENDDATE", notificationDateTo);
      System.out.println(resultMap);


  }

}
