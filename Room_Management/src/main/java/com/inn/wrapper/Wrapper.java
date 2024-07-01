package com.inn.wrapper;

import java.text.DecimalFormat;

public class Wrapper {

  public static void main(String[] args) {

    DecimalFormat df = new DecimalFormat("0.00");

    Double paymentAmountWithTax =   123.9346543567;
    String currency = Wrapper.getFormatedAmountCellValueByCurrency(paymentAmountWithTax,"JPY");
    System.out.println(currency);

  }

  public static String getFormatedAmountCellValue(Double payAmount) {
    String result = "-";
    DecimalFormat decimalFormatter = new DecimalFormat("#,###.00");
    if (payAmount != null) {
      if (Double.compare(payAmount, 0) > 0 && Double.compare(payAmount, 1) < 0) {
        result = String.format("%.2f", payAmount);
      } else {
        result = Double.compare(payAmount, 0) == 0 ? "0.00" : decimalFormatter.format(payAmount);
      }
    }
    return result;
  }

  public static String getFormatedAmountCellValueByCurrency(Double payAmount, String currency) {
    String result = "-";
    if (currency.equalsIgnoreCase("JPY")) {
      DecimalFormat decimalFormatter = new DecimalFormat("#,###");
      if (payAmount != null) {
        result = Double.compare(payAmount, 0) == 0 ? "0" : decimalFormatter.format(payAmount);
      }
    } else {
      result = getFormatedAmountCellValue(payAmount);
    }
    return result;
  }


}
