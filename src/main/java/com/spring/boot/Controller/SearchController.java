package com.spring.boot.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.Dao.ContactRepository;
import com.spring.boot.Dao.UserRepository;
import com.spring.boot.model.Contact;
import com.spring.boot.model.User;

@RestController
public class SearchController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String name,Principal principal){
		
		System.out.println(name);
		User user = this.userRepository.GetUserByName(principal.getName());
		System.out.println(user);
		
		List<Contact> contacts = this.contactRepository.findByNameforsearch(name,user);
		System.out.println("Search"+contacts);
		
		return ResponseEntity.ok(contacts);
	}
}
