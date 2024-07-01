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
@Table(name = "USER_GROUP_MAPPING")
public class UserGroupMapping {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Column(name = "GROUP_NAME")
  private String groupName;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "USER_NAME")
  private String username;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID_FK", referencedColumnName = "ID")
  @Getter(onMethod_ = @JsonIgnore)
  private UserRegistration user;
}
