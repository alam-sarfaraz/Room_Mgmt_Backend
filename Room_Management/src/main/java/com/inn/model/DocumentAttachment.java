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
@Table(name = "DOCUMENT_ATTACHMENT")
public class DocumentAttachment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Column(name = "IDENTIFIER")
  private String identifier;

  @Column(name = "FILE_NAME")
  private String fileName;

  @Column(name = "FILE_PATH")
  private String filePath;

  @Column(name = "FILE_SIZE")
  private Long fileSize;

  @Column(name = "EXTENSION")
  private String extension;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "RESPONSE_FILE_PATH")
  private String responseFilePath;

  @Column(name = "RESPONSE_FILE_NAME")
  private String responseFileName;

  @Column(name = "IS_HEADER_MATCHED")
  private Boolean isHeaderMatched;

  @Column(name = "PROCESSED")
  private Boolean isProcessed;

  @Column(name = "CREATED_DATE")
  private String createdDate;

  @Column(name = "CREATED_TIME")
  private String createdTime;

  @Column(name = "MODIFIED_DATE")
  private String modifiedDate;

  @Column(name = "MODIFIED_TIME")
  private String modifiedTime;
}
