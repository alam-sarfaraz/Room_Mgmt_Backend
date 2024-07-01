package com.inn.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inn.model.PurchaseItemDetail;

@Repository
public interface IPurchaseItemDetailRepository extends JpaRepository<PurchaseItemDetail, Integer> {

  @Query("select pd from PurchaseItemDetail pd where pd.userName =:un")
  List<PurchaseItemDetail> findByUsername(@Param("un") String username);

  @Query("select pd from PurchaseItemDetail pd where pd.emailId =:em")
  List<PurchaseItemDetail> findByEmail(@Param("em") String email);

  @Query("select pd from PurchaseItemDetail pd where pd.identifier =:idet")
  PurchaseItemDetail findByIdentifier(@Param("idet") String identifier);

  @Query("select pd from PurchaseItemDetail pd where pd.userIdentifier =:ui")
  List<PurchaseItemDetail> findByUserIdentifier(@Param("ui") String userIdentifier);

  @Query("select pd from PurchaseItemDetail pd where pd.groupName =:gn")
  List<PurchaseItemDetail> findByGroupName(@Param("gn") String groupName);

  @Query("select pd from PurchaseItemDetail pd where pd.purchaseDate =:pd")
  List<PurchaseItemDetail> findByPurchaseDate(@Param("pd") LocalDate purchaseDate);

  @Query("select pd from PurchaseItemDetail pd where pd.status =:st")
  List<PurchaseItemDetail> findByStatus(@Param("st") String status);

  @Query("select pd from PurchaseItemDetail pd where pd.userName=:un AND pd.status =:st")
  List<PurchaseItemDetail> findByUsernameAndStatus(@Param("un") String username, @Param("st") String status);

  @Query("select pd from PurchaseItemDetail pd where pd.emailId=:em AND pd.status =:st")
  List<PurchaseItemDetail> findByEmailAndStatus(@Param("em") String email, @Param("st") String status);

  @Query("select pd from PurchaseItemDetail pd where pd.userIdentifier=:unid AND pd.status =:st")
  List<PurchaseItemDetail> findByUserIdentifierAndStatus(@Param("unid") String userIdentifier,
      @Param("st") String status);

  @Query("select pd from PurchaseItemDetail pd where pd.userName=:un AND pd.purchaseDate =:pd")
  List<PurchaseItemDetail> findByUsernameAndpurchaseDate(@Param("un") String username,
      @Param("pd") LocalDate purchaseDate);

  @Query("select pd from PurchaseItemDetail pd where pd.emailId=:em AND pd.purchaseDate =:pd")
  List<PurchaseItemDetail> findByEmailAndpurchaseDate(@Param("em") String email, @Param("pd") LocalDate purchaseDate);

  @Query("select pd from PurchaseItemDetail pd where pd.userIdentifier=:uid AND pd.purchaseDate =:pd")
  List<PurchaseItemDetail> findByUserIdentifierAndPurchaseDate(@Param("uid") String userIdentifier,
      @Param("pd") LocalDate purchaseDate);

  @Query("select pd from PurchaseItemDetail pd where pd.groupName=:gn AND pd.month =:mnt")
  List<PurchaseItemDetail> findPoDetailListByGroupWiseAndMonthWise(@Param("gn") String groupName,
      @Param("mnt") String month);

  @Query("select pd from PurchaseItemDetail pd where pd.userName=:un AND pd.groupName =:gn AND pd.month=:mnth")
  List<PurchaseItemDetail> findPoDetailListByUsernameGroupAndMonthWise(@Param("un") String username,
      @Param("gn") String groupName, @Param("mnth") String month);

}
