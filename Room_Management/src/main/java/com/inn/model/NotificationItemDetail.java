package com.inn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NOTIFICATION_ITEM_DETAIL")
public class NotificationItemDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Column(name = "ITEM_NAME")
  private String itemName;

  @Column(name = "PRICE")
  private Double price;

  @Column(name = "QUANTITY")
  private Double quantity;

  @Column(name = "UNIT_PRICE")
  private Double unitPrice;

  @Column(name = "UNIT_OF_MEASUREMENT")
  private String unitOfMeasurement;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "NOTIFICATION_DETAIL_ID_FK", referencedColumnName = "ID")
  @Getter(onMethod_ = @JsonIgnore)
  private NotificationDetail notificationDetail;
}
