package com.inn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inn.model.GroupDetail;

@Repository
public interface IGroupDetailRepository extends JpaRepository<GroupDetail, Integer> {

  @Query("select gd from GroupDetail gd where gd.identifier =:idti")
  GroupDetail findByIdentifier(@Param("idti") String identifier);

  @Query("select gd from GroupDetail gd where gd.groupName =:gn")
  GroupDetail findByGroupName(@Param("gn") String groupName);

}
