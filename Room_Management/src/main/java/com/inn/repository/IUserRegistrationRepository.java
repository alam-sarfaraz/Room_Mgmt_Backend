package com.inn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inn.model.UserRegistration;

@Repository
public interface IUserRegistrationRepository extends JpaRepository<UserRegistration, Integer> {

  @Query("select ur from UserRegistration ur where ur.userName =:un")
  UserRegistration findByUsername(@Param("un") String username);

  @Query("select ur from UserRegistration ur where ur.emailId =:em")
  UserRegistration findByEmail(@Param("em") String email);

  @Query("select ur from UserRegistration ur where ur.identifier =:idti")
  UserRegistration findByIdentifier(@Param("idti") String identifier);

}
