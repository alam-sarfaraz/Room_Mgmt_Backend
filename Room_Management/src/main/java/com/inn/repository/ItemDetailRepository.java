package com.inn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.model.ItemDetail;

@Repository
public interface ItemDetailRepository extends JpaRepository<ItemDetail, Integer>{

}
