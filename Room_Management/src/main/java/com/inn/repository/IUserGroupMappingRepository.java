package com.inn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inn.model.UserGroupMapping;

@Repository
public interface IUserGroupMappingRepository extends JpaRepository<UserGroupMapping, Integer> {

  @Query("select ug from UserGroupMapping ug where ug.groupName =:gn")
  List<UserGroupMapping> findByGroupName(@Param("gn") String groupName);

  @Query("select ug from UserGroupMapping ug where ug.email =:em")
  List<UserGroupMapping> findByEmail(@Param("em") String email);

}
