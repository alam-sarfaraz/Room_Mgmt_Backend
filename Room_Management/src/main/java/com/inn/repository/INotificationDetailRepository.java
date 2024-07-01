package com.inn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inn.model.NotificationDetail;

@Repository
public interface INotificationDetailRepository extends JpaRepository<NotificationDetail, Integer> {

  @Query("select nd from NotificationDetail nd where nd.notificationReceiverUsername=:un AND nd.identifier =:nd")
  NotificationDetail findByUsernameAndNotificationIdentifier(@Param("un") String username,
      @Param("nd") String niIdentifier);

  @Query("select nd from NotificationDetail nd where nd.notificationReceiverUsername=:un AND nd.poIdentifier =:po")
  NotificationDetail findByUsernameAndPoIdentifier(@Param("un") String username, @Param("po") String poIdentifier);

  @Query("select nd from NotificationDetail nd where nd.notificationReceiverEmailId=:em AND nd.identifier =:nd")
  NotificationDetail findByEmailAndNotificationIdentifier(@Param("em") String email, @Param("nd") String niIdentifier);

  @Query("select nd from NotificationDetail nd where nd.notificationReceiverEmailId=:em AND nd.poIdentifier =:po")
  NotificationDetail findByEmailAndPoIdentifier(@Param("em") String email, @Param("po") String poIdentifier);

  @Query("select nd from NotificationDetail nd where  nd.poIdentifier =:po")
  List<NotificationDetail> findByPoIdentifier(@Param("po") String poIdentifier);

}
