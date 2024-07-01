package com.inn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ATTACHMENT")
public class Attachment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Column(name = "IDENTIFIER")
  private String identifier;

  @Column(name = "USER_NAME")
  private String userName;

  @Column(name = "FILE_NAME")
  private String fileName;

  @Column(name = "FILE_PATH")
  private String filePath;

  @Column(name = "FILE_SIZE")
  private Long fileSize;

  @Column(name = "USER_IDENTIFIER")
  private String userIdentifier;

  @Column(name = "PID_IDENTIFIER")
  private String pidIdentifier;

  @Column(name = "EXTENSION")
  private String extension;

  @Column(name = "CREATED_DATE")
  private String createdDate;

  @Column(name = "CREATED_TIME")
  private String createdTime;

  @Column(name = "MODIFIED_DATE_TIME")
  private String modifiedDateTime;

}
