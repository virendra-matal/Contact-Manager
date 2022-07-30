package com.spring.boot.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.boot.model.Contact;
import com.spring.boot.model.User;

public interface ContactRepository extends JpaRepository<Contact, Integer>{
	@Query("from Contact as c where c.user.UserId=:userId order by ContactId desc")
	public Page<Contact> GetContsctsByUser(@Param("userId")int UserId, Pageable pageable);
	
	@Query("FROM  Contact p WHERE p.Name LIKE %:Name% And p.user=:user")
	public List<Contact> findByNameforsearch(@Param("Name")String Name,@Param("user") User user);

}
