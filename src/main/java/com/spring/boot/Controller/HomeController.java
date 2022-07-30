package com.spring.boot.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.Dao.ContactRepository;
import com.spring.boot.Dao.UserRepository;
import com.spring.boot.helper.Message;
import com.spring.boot.model.Contact;
import com.spring.boot.model.User;


@Controller
@RequestMapping("/user")
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;

	
	@RequestMapping(path  = "/test" , method = RequestMethod.GET)
	public String  Test() {
		
		
		return "user/Test";
	}
	
	
	@RequestMapping(path  = "/test2" , method = RequestMethod.GET)
	public String  Test2() {
		
		
		return "user/Testing";
	}
	
	
	@GetMapping("/dashboard")
	public String  DashBoard(Model model, Principal principal,HttpSession session) {
		
		String Username = principal.getName();
		
		System.out.println("Username is : "+Username);
		User user = userRepository.GetUserByName(Username);
		session.setAttribute("Username", user.getName());
		session.setAttribute("Login", "yes");
		System.out.println("User Data is: "+user);
		model.addAttribute("user", user);
		
		return "user/Dashboard";
	}
	
	@GetMapping("/add-contact")
	public String ShowAddContactPage(Model model) {
		
		model.addAttribute("contact", new Contact());
		return "user/Add_Contact";
	}
	
	
	@PostMapping("/process-contact")
	public String ProcessAddContact(@ModelAttribute("contact") Contact contact,@RequestParam("Image1") MultipartFile file,Principal principal,Model model,HttpSession session) throws IOException{
		
		try {
			
			if(contact.getContactId() != 0) {
				
				Contact OldContact = this.contactRepository.findById(contact.getContactId()).get();
				if(!file.isEmpty()) {
					File file2 = new ClassPathResource("static/images").getFile();
					File file3=new File(file2, OldContact.getImage());
					file3.delete();
				}
			}
			
			String name = principal.getName();
			User user = userRepository.GetUserByName(name);
			if(file.isEmpty()) {
				System.out.println("File is Empty...");
				contact.setImage("profile.png");
				/* throw new Exception("Please upload the Image!!!!"); */
			}else {
				contact.setImage(file.getOriginalFilename());
				File savedFile = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(savedFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
			}
			
			contact.setUser(user);
			user.getContacts().add(contact);
			userRepository.save(user);
			System.out.println("Contact : "+contact);
			System.out.println("data has Inserted is : ");
			model.addAttribute("contact", new Contact());
			session.setAttribute("message", new Message("Contact added successfully!!!", "alert-success"));
			return "user/Add_Contact";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong... Please try Again!!!", "alert-danger"));
			return "user/Add_Contact";
		}
	}

	
	@GetMapping("/show-contact/{page}")
	public String ShowAllContactPage(@PathVariable("page") Integer page ,Model model,Principal principal) {
		String username = principal.getName();
		User userByName = userRepository.GetUserByName(username);
		
		Pageable pageable = PageRequest.of(page, 2);
		Page<Contact> contacts = contactRepository.GetContsctsByUser(userByName.getUserId(),pageable);
		
		model.addAttribute("contacts", contacts);
		model.addAttribute("curruntPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());
		return "user/View_Contact";
	}
	
	
	
	@GetMapping("/{cid}/contact")
	public String ShowSingleContact(@PathVariable("cid") Integer contactid, Model model,Principal principal) {
		
		System.out.println("cid : "+contactid);
		Optional<Contact> optional = contactRepository.findById(contactid);
		Contact contact = optional.get();
		
		String username = principal.getName();
		User user = userRepository.GetUserByName(username);
		if(user.getUserId()==contact.getUser().getUserId()) {
			
			model.addAttribute("contact", contact);
		}
		
		return "user/SHow_Single_Contact";
	}

	
	
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cid) throws IOException {

		Optional<Contact> optional = contactRepository.findById(cid);
		Contact contact = optional.get();
		/* Deleting image from the server when deleting the contact */
		String image = contact.getImage();
		File savedFile = new ClassPathResource("static/images").getFile();
		Path path = Paths.get(savedFile.getAbsolutePath()+File.separator+image);
		Files.delete(path);
		System.out.println("Image Deleted...");
		
		/* Images deleted Succefully */
		
		/* contact.setUser(null); */
		
		this.contactRepository.delete(contact);

		return "redirect:/user/show-contact/0";
	}
	
	
	@PostMapping("/updateContact/{cid}")
	public String UpdateContact(@PathVariable("cid") Integer cid, Model model) throws IOException {
		
		System.out.println("Contact id is : "+cid);
		Contact contact = this.contactRepository.findById(cid).get();
		
		model.addAttribute("contact", contact);
		
		
		return "user/Add_Contact";
	}

	@GetMapping("/settings")
	public String ShowSettingsPage(){

		return "user/Settings";
	}
	

	@PostMapping("/chagePassword")
	public String ProcessChagePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, Principal principal,Model model,HttpSession session){

		System.out.println("Old and new Password in sequence : "+oldPassword +"  "+ newPassword);
		String username = principal.getName();
		User user = this.userRepository.GetUserByName(username);
		System.out.println("oldPssword enter by user in bcrypt : "+this.bCryptPasswordEncoder.encode(oldPassword));
		System.out.println("old Password in bcrypt : "+user.getPassword());

		if (this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
			System.out.println("MATCH SUCCESSFULL!!!");
			user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			userRepository.save(user);
			// model.addAttribute("user", user);
			session.setAttribute("result", new Message("Password Changed Succefull!!!", "alert-success"));
			Object attribute = session.getAttribute("result");
			System.out.println("session value is: "+attribute);
			return "redirect:/user/settings";
			
		} else {
			// model.addAttribute("user", user);
			session.setAttribute("result", new Message("Oops something went wrong!!! Pleasee enter your correct current password!!!", "alert-danger"));
			Object attribute = session.getAttribute("result");
			System.out.println("session value is: "+attribute);
			return "redirect:/user/settings";
		}
		
	}
}
