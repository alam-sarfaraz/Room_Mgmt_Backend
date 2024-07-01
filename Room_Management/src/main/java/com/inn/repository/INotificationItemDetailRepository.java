package com.inn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.model.NotificationItemDetail;

@Repository
public interface INotificationItemDetailRepository extends JpaRepository<NotificationItemDetail, Integer>
{

}
