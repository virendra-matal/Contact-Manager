package com.spring.boot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CONTACT")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ContactId;
	private String Name;
	private String Email;
	private String Nickname;
	@Column(length = 10)
	private String MobileNo;
	private String Image;
	private String Work;
	@Column(length = 1000)
	private String Discription;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Contact(int contactId, String name, String email, String nickname, String mobileNo, String image,
			String work, String discription) {
		super();
		ContactId = contactId;
		Name = name;
		Email = email;
		Nickname = nickname;
		MobileNo = mobileNo;
		Image = image;
		Work = work;
		Discription = discription;
	}


	public int getContactId() {
		return ContactId;
	}

	public String getName() {
		return Name;
	}

	public String getEmail() {
		return Email;
	}

	public String getNickname() {
		return Nickname;
	}

	public String getMobileNo() {
		return MobileNo;
	}

	public String getImage() {
		return Image;
	}

	public String getWork() {
		return Work;
	}

	public String getDiscription() {
		return Discription;
	}

	public User getUser() {
		return user;
	}

	public void setContactId(int contactId) {
		ContactId = contactId;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public void setNickname(String nickname) {
		Nickname = nickname;
	}

	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}

	public void setImage(String image) {
		Image = image;
	}

	public void setWork(String work) {
		Work = work;
	}

	public void setDiscription(String discription) {
		Discription = discription;
	}

	public void setUser(User user) {
		this.user = user;
	}


	
	
	
	

	
	
}
