package com.inn.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "PURCHASE_ITEM_DETAIL")
public class PurchaseItemDetail {

  public enum ModeOfPayment {
    CASH, ONLINE, CARD
  }

  public enum PaymentStatus {
    DONE, WAITING
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Column(name = "IDENTIFIER")
  private String identifier;

  @Column(name = "USER_NAME")
  private String userName;

  @Column(name = "EMAIL_ID")
  private String emailId;

  @Column(name = "USER_IDENTIFIER")
  private String userIdentifier;

  @Column(name = "CREATED_DATE")
  private String createdDate;

  @Column(name = "CREATED_TIME")
  private String createdTime;

  @Column(name = "MODIFIED_DATE")
  private String modifiedDate;

  @Column(name = "MODIFIED_TIME")
  private String modifiedTime;

  @Column(name = "MONTH")
  private String month;

  @Column(name = "DAY")
  private String day;

  @Column(name = "MODE_OF_PAYMENT")
  @Enumerated(EnumType.STRING)
  private ModeOfPayment modeOfPayment;

  @Column(name = "PAYMENT_STATUS")
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Column(name = "STATUS")
  private String status;

  @Column(name = "GROUP_NAME")
  private String groupName;

  @Column(name = "PURCHASE_DATE")
  private LocalDate purchaseDate;

  @OneToMany(mappedBy = "purchaseItemDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<ItemDetail> itemDetail;

}
