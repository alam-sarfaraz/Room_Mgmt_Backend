package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.inn.constant.RoomConstant;
import com.inn.customException.CustomException;
import com.inn.model.Attachment;
import com.inn.model.EmailDetails;
import com.inn.model.UserGroupMapping;
import com.inn.model.UserRegistration;
import com.inn.repository.IUserGroupMappingRepository;
import com.inn.repository.IUserRegistrationRepository;
import com.inn.service.IAttachmentService;
import com.inn.service.IEmailDetailService;
import com.inn.service.IGroupDetailService;
import com.inn.service.IUserRegistrationService;
import com.inn.utils.RoomUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperRunManager;

@Service
@Slf4j
public class UserRegistrationServiceImpl implements IUserRegistrationService {

  @Autowired
  IUserRegistrationRepository userRegistrationRepository;

  @Autowired
  IAttachmentService attachmentService;

  @Autowired
  IEmailDetailService emailDetailService;

  @Autowired
  IUserGroupMappingRepository groupMappingRepository;

  @Autowired
  IGroupDetailService groupDetailService;

  @Override
  @Transactional
  public UserRegistration create(UserRegistration userRegistration) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "create {}", kv("UserRegistration", userRegistration));
      /* validation for username */
      UserRegistration findByUsername = userRegistrationRepository.findByUsername(userRegistration.getUserName());
      UserRegistration findByEmail = userRegistrationRepository.findByEmail(userRegistration.getEmailId());
      if (findByUsername == null) {
        if (findByEmail == null) {
          /* validation over group detail */
          List<UserGroupMapping> groupMappingDetail = userRegistration.getGroupMappings();
          if (groupMappingDetail != null && !groupMappingDetail.isEmpty()) {
            log.info("Group Mappings detail :{}", kv("GroupMappings", groupMappingDetail),
                kv("groupMappings size ", groupMappingDetail.size()));
            for (UserGroupMapping gm : groupMappingDetail) {
              String groupName = gm.getGroupName();
              log.info("Group name  :{}", kv("groupName", groupName));
              groupDetailService.findByGroupName(groupName);
            }
            String identifier = RoomUtil.generateIdentifier("USER");
            userRegistration.setIdentifier(identifier);
            userRegistration.setCreatedDate(RoomUtil.dateFormatter());
            userRegistration.setCreatedTime(RoomUtil.timeFormatter());
            userRegistration.setIsActive(true);
            userRegistration.setRole(userRegistration.getRole().toUpperCase());
            /* validation for Email ID */
            RoomUtil.validateEmailAndConfirmEmail(userRegistration.getEmailId(), userRegistration.getConfirmEmailId());
            /* validation for password */
            RoomUtil.validatePasswordAndConfirmPassword(userRegistration.getPassword(),
                userRegistration.getConfirmPassword());
            /* validation for Mobile number */
            RoomUtil.validationForMobileNumber(userRegistration.getMobileNo());
            RoomUtil.validationForMobileNumber(userRegistration.getAlternateMobileNo());
            /* validation for Gender */
            RoomUtil.genderValidate(userRegistration.getGender());
            UserRegistration userDetail = userRegistrationRepository.save(userRegistration);
            /* Mapping with User Group Mapping table */
            List<UserGroupMapping> groupMappings = userDetail.getGroupMappings();
            for (UserGroupMapping gp : groupMappings) {
              gp.setUser(userDetail);
              gp.setEmail(userDetail.getEmailId());
              gp.setUsername(userDetail.getUserName());
              groupMappingRepository.save(gp);
            }
            /* Generate Report */
            String filepath = this.generateUserRegistrationReport(userDetail.getUserName());
            /* Create Attachment */
            createAttachment(filepath, identifier, userRegistration.getUserName());
            /* Trigger Email to user */
            sendEmailToUser(userDetail.getEmailId(), filepath, userDetail.getUserName(), userDetail.getPassword());
            return userDetail;
          } else {
            throw new CustomException("Please select at list one group or create a new group ....");
          }
        } else {
          throw new CustomException("Email Id already exist. Please choose another email id ....");
        }
      } else {
        throw new CustomException("Username already exist. Please choose another username ....");
      }
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  private void sendEmailToUser(String email, String filePath, String username, String password) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "sendEmailToUser {} {}", kv("Email", email), kv("FilePath", filePath));
    EmailDetails emailDetails = new EmailDetails();
    emailDetails.setRecipient(email);
    emailDetails.setSubject("Regarding New User Registration.");
    emailDetails
        .setMsgBody("Your user creation over Room Management application has been successfully.\n" + "Username :" + " "
            + username + "\n" + "Password :" + " " + password + "\n" + "\n" + "Regards.\n" + "Room Managemant");
    emailDetails.setAttachment(filePath);
    emailDetailService.sendMailWithAttachment(emailDetails);
  }

  private Attachment createAttachment(String filepath, String identifier, String username) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "CreateAttachment {} {}", kv("filepath", filepath),
        kv("Identifier", identifier));
    File file = new File(filepath);
    String fileName = file.getName();
    String extension = StringUtils.substringAfterLast(fileName, ".");
    String filePath = StringUtils.substringBeforeLast(file.getPath(), "\\") + File.separator;
    long fileSize = file.getTotalSpace();
    log.info("Detail of files  {} {} {}", kv("fileName", fileName), kv("filePath", filePath), kv("fileSize", fileSize));
    Attachment attachment = new Attachment();
    attachment.setIdentifier(RoomUtil.generateIdentifier("ATT"));
    attachment.setUserIdentifier(identifier);
    attachment.setUserName(username);
    attachment.setFileName(fileName);
    attachment.setFilePath(filePath);
    attachment.setExtension(extension);
    attachment.setFileSize(fileSize);
    return attachmentService.create(attachment);
  }

  @Override
  public UserRegistration findByUsername(String username) throws CustomException {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByUsername {}", kv("Username", username));
      UserRegistration userDetail = userRegistrationRepository.findByUsername(username);
      if (userDetail != null) {
        return userDetail;
      } else {
        throw new CustomException("User not found ....");
      }
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;

    }
  }

  @Override
  public UserRegistration findByEmail(String email) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByEmail {}", kv("Email", email));
      UserRegistration userDetail = userRegistrationRepository.findByEmail(email);
      if (userDetail != null) {
        return userDetail;
      } else {
        throw new CustomException("Invalid Email Id ........");
      }
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public UserRegistration findByIdentifier(String identifier) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findByIdentifier {}", kv("Identifier", identifier));
      UserRegistration userDetail = userRegistrationRepository.findByIdentifier(identifier);
      if (userDetail != null) {
        return userDetail;
      } else {
        throw new CustomException("Invalid Identifier........");
      }
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String deleteByUsername(String username) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "deleteByUsername {}", kv("username", username));
      UserRegistration userRegistrationDetail = this.findByUsername(username);
      log.info("Detail of userRegistrationDetail {}", kv("username", username));
      if (userRegistrationDetail != null) {
        Integer id = userRegistrationDetail.getId();
        userRegistrationRepository.deleteById(id);
        attachmentService.deleteByIdentifier(username);
      } else {
        throw new CustomException("Invalid Username ........");
      }
      return "User Delete successfully " + username + ".....";
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public String generateUserRegistrationReport(String username) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "generateUserRegistrationReport {}", kv("username", username));
      return generateReport(username);
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

//  @Override
//  public ResponseEntity<byte[]> downloadUserRegistrationReportByUsername(String username) throws Exception {
//    try {
//      log.info(RoomConstant.INSIDE_THE_METHOD + "downloadUserRegistrationReportByUsername {}",
//          kv("username", username));
//      ResponseEntity<byte[]> downloadFile = null;
//      Attachment userRegisterationAttachment = attachmentService.findByUsername(username);
//      log.info("userRegisterationAttachment Detail  {}",
//          kv("UserRegisterationAttachment", userRegisterationAttachment));
//      if (userRegisterationAttachment != null) {
//        String filepath = userRegisterationAttachment.getFilePath() + userRegisterationAttachment.getFileName();
//        downloadFile = RoomUtil.downloadFile(filepath);
//      }
//      return downloadFile;
//    } catch (Exception e) {
//      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
//      throw e;
//    }
//  }

  Map<String, Object> setParameter(UserRegistration userDetail) {
    Map<String, Object> parameter = new HashedMap();
    parameter.put("username", userDetail.getUserName());
    parameter.put("email", userDetail.getEmailId());
    parameter.put("firstName", userDetail.getFirstName());
    parameter.put("middleName", userDetail.getMiddleName() != null ? userDetail.getMiddleName() : "-");
    parameter.put("lastName", userDetail.getLastName() != null ? userDetail.getLastName() : "-");
    parameter.put("gender", userDetail.getGender());
    parameter.put("role", userDetail.getRole());
    parameter.put("identifier", userDetail.getIdentifier());
    SimpleDateFormat sdf = new SimpleDateFormat(RoomConstant.DATE_PATTERN);
    String dob = sdf.format(userDetail.getDob());
    parameter.put("dob", dob);
    parameter.put("password", userDetail.getPassword());
    parameter.put("mobileNo", userDetail.getMobileNo());
    parameter.put("altMobileNo", userDetail.getAlternateMobileNo());
    String isActive = "No";
    if (Boolean.TRUE.equals(userDetail.getIsActive())) {
      isActive = "Yes";
    }
    parameter.put("active", isActive);
    List<String> groupNameList = userDetail.getGroupMappings().stream().map(e -> e.getGroupName())
        .collect(Collectors.toList());
    parameter.put("groupName", StringUtils.join(groupNameList, ","));
    return parameter;
  }

  String generateReport(String username) throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "generateReport {}", kv("username", username));
    try {
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat(RoomConstant.DATE_TIME);
      String folder = sdf.format(date);
      String downloadPath = RoomConstant.DOWNLOAD_FILE_PATH + folder + File.separator;
      File createFolder = new File(downloadPath);
      if (!createFolder.exists()) {
        createFolder.mkdirs();
      }
      log.info("Download Path {}", kv("DownloadPath", downloadPath));
      File file = ResourceUtils.getFile("classpath:Registration.jasper");
      String sampleFilePath = file.getAbsolutePath();
      log.info("sampleFilePath Detail  {}", kv("SampleFilePath", sampleFilePath));
      UserRegistration userDetail = this.findByUsername(username);
      String downloadedFileName = username + ".pdf";
      if (userDetail != null) {
        Map<String, Object> parameter = setParameter(userDetail);
        log.info("After set values in parameter  {}", kv("Parameter", parameter));
        JasperRunManager.runReportToPdfFile(sampleFilePath, downloadPath + downloadedFileName, parameter,
            new net.sf.jasperreports.engine.JREmptyDataSource());
      }
      log.info("Full file path and file name {}", kv("Full path ", downloadPath + downloadedFileName));
      return downloadPath + downloadedFileName;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  @Override
  public UserRegistration update(String username, UserRegistration userRegistration) throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "update {} {} ", kv("Username", username),
          kv("UserRegistration", userRegistration));
      UserRegistration userRegistrationDetail = this.findByUsername(username);

      /* validation for Email ID */
      if (userRegistration.getEmailId() != null) {
        if (userRegistration.getConfirmEmailId() != null) {
          RoomUtil.validateEmailAndConfirmEmail(userRegistration.getEmailId(), userRegistration.getConfirmEmailId());
          userRegistrationDetail.setEmailId(userRegistration.getEmailId());
          userRegistrationDetail.setConfirmEmailId(userRegistration.getConfirmEmailId());
        } else {
          throw new CustomException("Please enter Confirm email id ...");
        }
      }
      /* validation for Password */
      if (userRegistration.getPassword() != null) {
        if (userRegistration.getConfirmPassword() != null) {
          RoomUtil.validatePasswordAndConfirmPassword(userRegistration.getPassword(),
              userRegistration.getConfirmPassword());
          userRegistrationDetail.setPassword(userRegistration.getPassword());
          userRegistrationDetail.setConfirmPassword(userRegistration.getConfirmPassword());
        } else {
          throw new CustomException("Please enter Confirm password  ...");
        }
      }
      /* validation for Mobile number */
      if (userRegistration.getMobileNo() != null) {
        RoomUtil.validationForMobileNumber(userRegistration.getMobileNo());
        userRegistrationDetail.setMobileNo(userRegistration.getMobileNo());
      }
      if (userRegistration.getAlternateMobileNo() != null) {
        RoomUtil.validationForMobileNumber(userRegistration.getAlternateMobileNo());
        userRegistrationDetail.setAlternateMobileNo(userRegistration.getAlternateMobileNo());
      }
      /* validation for first name */
      if (userRegistration.getFirstName() != null) {
        userRegistrationDetail.setFirstName(userRegistration.getFirstName());
      }
      /* validation for middle name */
      if (userRegistration.getMiddleName() != null) {
        userRegistrationDetail.setMiddleName(userRegistration.getMiddleName());
      }
      /* validation for last name */
      if (userRegistration.getLastName() != null) {
        userRegistrationDetail.setLastName(userRegistration.getLastName());
      }
      /* validation for gender */
      if (userRegistration.getGender() != null) {
        userRegistrationDetail.setGender(userRegistration.getGender());
      }

      /* validation for Role name */
      if (userRegistration.getRole() != null) {
        userRegistrationDetail.setRole(userRegistration.getRole().toUpperCase());
      }
      /* validation for Role name */
      if (userRegistration.getDob() != null) {
        userRegistrationDetail.setDob(userRegistration.getDob());
      }
      if (userRegistration.getGroupMappings() != null && !userRegistration.getGroupMappings().isEmpty()) {
        for (UserGroupMapping gm : userRegistration.getGroupMappings()) {
          /* validation for Group name */
          groupDetailService.findByGroupName(gm.getGroupName());
          gm.setUser(userRegistrationDetail);
          gm.setEmail(userRegistrationDetail.getEmailId());
          groupMappingRepository.save(gm);
        }
      }
      userRegistrationDetail.setModifiedDateTime(RoomUtil.modifiedDateTimeFormatter());
      UserRegistration afterUpdate = userRegistrationRepository.save(userRegistrationDetail);
      /* Generate Report */
      String filepath = this.generateUserRegistrationReport(afterUpdate.getUserName());
      /* Create Attachment */
      createAttachment(filepath, afterUpdate.getIdentifier(), userRegistration.getUserName());
      /* Trigger Email to user */
      sendEmailToUserAfterUpdate(afterUpdate.getEmailId(), filepath, afterUpdate.getUserName(),
          afterUpdate.getPassword());
      return afterUpdate;
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

  private void sendEmailToUserAfterUpdate(String email, String filePath, String username, String password)
      throws Exception {
    log.info(RoomConstant.INSIDE_THE_METHOD + "sendEmailToUser {} {}", kv("Email", email), kv("FilePath", filePath));
    EmailDetails emailDetails = new EmailDetails();
    emailDetails.setRecipient(email);
    emailDetails.setSubject("Regarding User updatation in Room Management");
    emailDetails.setMsgBody("Your user updateion in Room Management application has been successfully.\n" + "Username :"
        + " " + username + "\n" + "Password :" + " " + password + "\n" + "\n" + "Regards.\n" + "Room Managemant");
    emailDetails.setAttachment(filePath);
    emailDetailService.sendMailWithAttachment(emailDetails);
  }

  @Override
  public List<UserRegistration> findAll() throws Exception {
    try {
      log.info(RoomConstant.INSIDE_THE_METHOD + "findAll {}");
      return userRegistrationRepository.findAll();
    } catch (Exception e) {
      log.error(RoomConstant.ERROR_DUE_TO, kv(RoomConstant.MESSAGE, e.getMessage()));
      throw e;
    }
  }

}
