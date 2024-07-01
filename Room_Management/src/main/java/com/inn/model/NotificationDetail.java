package com.inn.model;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NOTIFICATION_DETAIL")
public class NotificationDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Column(name = "IDENTIFIER")
  private String identifier;

  @Column(name = "BUYER_USER_NAME")
  private String buyerUserName;

  @Column(name = "BUYER_EMAIL_ID")
  private String buyerEmailId;

  @Column(name = "BUYER_USER_IDENTIFIER")
  private String buyerUserIdentifier;

  @Column(name = "PO_IDENTIFIER")
  private String poIdentifier;

  @Column(name = "NOTIFICATION_RECEIVER_USER_NAME")
  private String notificationReceiverUsername;

  @Column(name = "NOTIFICATION_RECEIVER_EMAIL_ID")
  private String notificationReceiverEmailId;

  @Column(name = "NOTIFICATION_RECEIVER_USER_IDENTIFIER")
  private String notificationReceiverUserIdentifier;

  @Column(name = "GROUP_NAME")
  private String groupName;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "CREATED_DATE")
  private String createdDate;

  @Column(name = "CREATED_TIME")
  private String createdTime;

  @Column(name = "MODIFIED_DATE")
  private String modifiedDate;

  @Column(name = "MODIFIED_TIME")
  private String modifiedTime;

  @OneToMany(mappedBy = "notificationDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<NotificationItemDetail> notificationItemDetail;
}
