package com.inn.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_REGISTRATION")
public class UserRegistration {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Column(name = "IDENTIFIER")
  private String identifier;

  @Column(name = "USER_NAME")
  private String userName;

  @Column(name = "FIRST_NAME")
  @NotBlank(message = "First Name is mandatory")
  private String firstName;

  @Column(name = "MIDDLE_NAME")
  private String middleName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "EMAIL_ID")
  @NotBlank(message = "Email id is mandatory")
  private String emailId;

  @Column(name = "CONFIRM_EMAIL_ID")
  @NotBlank(message = "confirm email id is mandatory")
  private String confirmEmailId;

  @Column(name = "PASSWORD")
  @NotBlank(message = "Password is mandatory")
  @Size(min = 8, max = 100, message = "Password must be in between 8 to 100 character")
  private String password;

  @NotBlank(message = "Confirm Password is mandatory")
  @Size(min = 8, max = 100, message = "Confirm Password must be in between 8 to 100 character")
  @Column(name = "CONFIRM_PASSWORD")
  private String confirmPassword;

  @NotBlank(message = "Mobile number is mandatory")
  @Column(name = "MOBILE_NUMBER")
  private String mobileNo;

  @Column(name = "ALTERNATE_MOBILE_NUMBER")
  private String alternateMobileNo;

  @Column(name = "DOB")
  private Date dob;

  @NotBlank(message = "Gender is mandatory")
  @Column(name = "GENDER")
  private String gender;

  @Column(name = "ROLE")
  private String role;

  @Column(name = "IS_ACTIVE")
  private Boolean isActive;

  @Column(name = "CREATED_DATE")
  private String createdDate;

  @Column(name = "CREATED_TIME")
  private String createdTime;

  @Column(name = "MODIFIED_DATE_TIME")
  private String modifiedDateTime;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<UserGroupMapping> groupMappings ;

}
