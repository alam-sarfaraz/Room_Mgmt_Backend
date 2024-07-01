package com.inn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inn.model.Attachment;

@Repository
public interface IAttachmentRepository extends JpaRepository<Attachment, Integer> {

  @Query("select ur from Attachment ur where ur.userName =:un")
  List<Attachment> findByUsername(@Param("un") String username);

  @Query("select ur from Attachment ur where ur.identifier =:idti")
  Attachment findByIdentifier(@Param("idti") String identifier);

  @Query("select ur from Attachment ur where ur.userIdentifier =:uridti")
  List<Attachment> findByUserIdentifier(@Param("uridti") String userIdentifier);

}
