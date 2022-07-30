package com.spring.boot.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.spring.boot.model.User;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {

	
	@Query("select u from User u where u.Email=:email")
	public User GetUserByName(@Param("email") String Email);
	
	/* public User findByName(String Name); */
}
