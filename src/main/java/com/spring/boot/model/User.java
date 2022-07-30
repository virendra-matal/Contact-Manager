package com.spring.boot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity(name = "User")
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int UserId;
	
	@NotBlank(message = "Please enter your Name...")
	@Size(min = 5, max = 20)
	private String Name;
	@javax.validation.constraints.Email(regexp =  "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")
	@Column(unique = true)
	private String Email;
	@NotBlank(message = "Please enter your Password...")
	@Size(min = 8, max = 200)
	private String Password;
	private String ImageUrl;
	private String Role;
	private boolean Enable;
	@NotBlank(message = "Please Write Information here...")
	@Column(length = 500)
	private String About;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY , mappedBy = "user")
	private List<Contact> contacts=new ArrayList<>();
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return UserId;
	}

	public String getName() {
		return Name;
	}

	public String getEmail() {
		return Email;
	}

	public String getPassword() {
		return Password;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public String getRole() {
		return Role;
	}

	public boolean isEnable() {
		return Enable;
	}

	public String getAbout() {
		return About;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

	public void setRole(String role) {
		Role = role;
	}

	public void setEnable(boolean enable) {
		Enable = enable;
	}

	public void setAbout(String about) {
		About = about;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "User [UserId=" + UserId + ", Name=" + Name + ", Email=" + Email + ", Password=" + Password
				+ ", ImageUrl=" + ImageUrl + ", Role=" + Role + ", Enable=" + Enable + ", About=" + About
				;
	}
	
	
	
	
	
	
	
	

}
