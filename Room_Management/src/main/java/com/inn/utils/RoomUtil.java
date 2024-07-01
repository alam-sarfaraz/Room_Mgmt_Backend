package com.inn.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import java.awt.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.inn.constant.RoomConstant;
import com.inn.customException.CustomException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoomUtil {

  public static String simpleDateFormatter(String pattern) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    return simpleDateFormat.format(new Date());

  }

  public static String dateFormatter() {
    return simpleDateFormatter(RoomConstant.DATE_PATTERN);

  }

  public static String timeFormatter() {
    return simpleDateFormatter(RoomConstant.TIME_PATTERN);
  }

  public static String modifiedDateTimeFormatter() {
    return simpleDateFormatter(RoomConstant.MODIFIED_DATE_TIME);
  }

  public static String dateTime() {
    return simpleDateFormatter(RoomConstant.DATE_TIME);
  }

  public static String generateIdentifier(String pattern) {
    return pattern + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
  }

  public static void validatePasswordAndConfirmPassword(String password, String confirmpassword)
      throws CustomException {
    if (!password.equals(confirmpassword)) {
      throw new CustomException("Password and Confirm password are not same");
    }
  }

  public static void validateEmailAndConfirmEmail(String email, String confirmEmail) throws CustomException {
    if (!email.equals(confirmEmail)) {
      throw new CustomException("Email id and Confirm Email id are not same");
    }
  }

//Validation for mobile Number
  public static void validationForMobileNumber(String mobileNo) throws CustomException {
    log.info(RoomConstant.INSIDE_THE_METHOD + "validationForMobileNumber :{}", mobileNo);
    String regex = RoomConstant.REGEX_FOR_MOBILE_NO;
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(mobileNo);
    boolean matchFound = matcher.find();
    if (!matchFound) {
      throw new CustomException("Invalid mobile number");
    }
  }

  // Validation for Alternate mobile Number
  public static void validationForAlternateMobileNumber(String altMobileNo) throws CustomException {
    log.info(RoomConstant.INSIDE_THE_METHOD + "validationForAlternateMobileNumber :{} ", altMobileNo);
    if (altMobileNo != null) {
      String regex = RoomConstant.REGEX_FOR_MOBILE_NO;
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(altMobileNo);
      boolean matchFound = matcher.find();
      if (!matchFound) {
        throw new CustomException("Invalid Alternate Mobile number");
      }
    }
  }

  public static void genderValidate(String gender) throws CustomException {
    if (!gender.equalsIgnoreCase("MALE") && !gender.equalsIgnoreCase("FEMALE") && !gender.equalsIgnoreCase("OTHER")) {
      throw new CustomException("Invalid gender value");
    }
  }

  public static ResponseEntity<byte[]> downloadFile(String filePath) {
    log.info("Inside the downloadFile  :{}", filePath);
    try {
      File file = new File(filePath);
      String fileName = file.getName();
      log.info("downloadFile fileName : {}", fileName);
      byte[] fileByteArray = org.apache.commons.io.IOUtils.toByteArray(new FileInputStream(file));
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
      return new ResponseEntity<>(fileByteArray, headers, HttpStatus.OK);
    } catch (Exception e) {
      log.error("Error inside downloadFile : {}", e.getMessage());
      return null;
    }
  }

  public static String getUserFullName(String fname, String mname, String lname) {
    String fullname = "";
    if (!StringUtils.isEmpty(fname)) {
      fullname += fname + " ";
    }
    if (!StringUtils.isEmpty(mname)) {
      fullname += mname + " ";
    }
    if (!StringUtils.isEmpty(lname)) {
      fullname += lname;
    }
    return fullname.toUpperCase();
  }

  public static void cellRender(List<String> cellValueList, Row row) {
    try {
      Integer columnIndex = 0;
      for (String cellResult : cellValueList) {
        Cell cell = row.createCell(columnIndex++);
        cell.setCellValue(cellResult);
      }
    } catch (Exception ex) {
      log.error(RoomConstant.ERROR_DUE_TO, "paymentCellRender {}", ex.getMessage());
    }
  }

  public static String getZipFileFromMultipleFiles(String zipFilePath, List<String> sourceFiles) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "getZipFileFromMultipleFiles zipname::{}, sourceFiles::{},", zipFilePath,
        sourceFiles.size());
    try (FileOutputStream fout = new FileOutputStream(zipFilePath); ZipOutputStream zout = new ZipOutputStream(fout)) {
      byte[] buffer = new byte[1024];

      for (String sourceFile : sourceFiles) {
        try (FileInputStream fin = new FileInputStream(sourceFile)) {
          log.info("Full path of :{}", sourceFile);
          String substringAfter = StringUtils.substringAfterLast(sourceFile, "\\");
          log.info("filename :{}", substringAfter);
          zout.putNextEntry(new ZipEntry(substringAfter));
          int length;
          while ((length = fin.read(buffer)) > 0) {
            zout.write(buffer, 0, length);
          }
          zout.closeEntry();
        } catch (FileNotFoundException fnfe) {
          log.info(RoomConstant.INSIDE_THE_METHOD + "getZipFileFromMultipleFiles filenotfoundException.");
          throw new FileNotFoundException(fnfe.getMessage());
        } catch (ZipException ze) {
          log.info(RoomConstant.INSIDE_THE_METHOD + "getZipFileFromMultipleFiles zipexception.");
          throw new ZipException(ze.getMessage());
        }
      }
      log.info(RoomConstant.INSIDE_THE_METHOD + "getZipFileFromMultipleFiles zip files has been created ");
    } catch (Exception ex) {
      log.error(RoomConstant.INSIDE_THE_METHOD + "getZipFileFromMultipleFiles exception is");
      throw new CustomException(ex.getMessage());
    }
    return zipFilePath;
  }

  public static List<String> getHeaderList() {
    List<String> headerList = new ArrayList<>();
    headerList.add("PURCHASE ID");
    headerList.add("USERNAME");
    headerList.add("STATUS");
    headerList.add("COMFIRMED");
    return headerList;
  }
  @SuppressWarnings("deprecation")
  public static XSSFCellStyle getCheckListCellStyle(XSSFWorkbook workbook) {
    log.info("Inside the XSSFCellStyle method");
    XSSFCellStyle style = workbook.createCellStyle();
    XSSFColor myColor = new XSSFColor(Color.orange);
    style.setFillPattern(FillPatternType.LESS_DOTS);
    style.setFillBackgroundColor(myColor);

    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setBorderBottom(BorderStyle.THIN);
    style.setBottomBorderColor(new XSSFColor(Color.BLACK));
    style.setBorderRight(BorderStyle.THIN);
    style.setRightBorderColor(new XSSFColor(Color.BLACK));
    style.setBorderTop(BorderStyle.THIN);
    style.setTopBorderColor(new XSSFColor(Color.BLACK));
    style.setAlignment(HorizontalAlignment.CENTER);


    Font font = workbook.createFont();
    font.setBold(true);
    font.setFontHeightInPoints((short) 12);
    font.setFontName("Meiryo UI");
    style.setFont(font);
    log.info("After set All the value in sheet method :");
    return style;
  }

}
