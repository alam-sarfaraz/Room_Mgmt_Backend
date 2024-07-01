package com.inn.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inn.model.DocumentAttachment;

@Repository
public interface IDocumentAttachmentRepository extends JpaRepository<DocumentAttachment, Integer> {

  @Query("select  da from DocumentAttachment da where da.status =:st AND da.isHeaderMatched =:hm AND da.isProcessed=:proc ORDER BY da.id ASC")
  List<DocumentAttachment> getDocumentAttachmentListByStatus(@Param("st") String status,
      @Param("hm") Boolean isHeaderMatched, @Param("proc") Boolean isProcessed,Pageable pageable);

}
