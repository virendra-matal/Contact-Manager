package com.spring.boot.Controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.boot.Dao.UserRepository;
import com.spring.boot.helper.Message;
import com.spring.boot.model.User;

@Controller
public class Mycontroller {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/home")
	public String Home() {

		return "Home";
	}

	@GetMapping("/signup")
	public String Signup(Model model) {

		model.addAttribute("user", new User());
		return "SignUp";
	}

	@GetMapping("/login")
	public String Login() {

		return "Login";
	}

	@PostMapping("/do-register")
	public String Register(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam(value = "agreed", defaultValue = "false") boolean agreed, Model model, HttpSession session) {

		try {

			if (!agreed) {
				System.out.println(agreed);
				throw new Exception("you have not accept our terms and conditions.");

			}
			if (result.hasErrors()) {

				model.addAttribute("user", user);
				System.out.println(result);
				return "SignUp";
			}

			user.setEnable(true);
			user.setImageUrl("defualt.png");
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			User result1 = userRepository.save(user);

			System.out.println(result1);
			model.addAttribute("user", new User());

			session.setAttribute("Message", new Message("Registration Successfull !!!", "alert-success"));
			session.setAttribute("Register", "yes");
			return "Home";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("Message", new Message("Somethig went wrong!!" + e.getMessage(), "alert-danger"));
			return "SignUp";
		}

	}

	@PostMapping("/logout")
	public String Logout(HttpSession session) {

		session.invalidate();
		return "Login";
	}

	@GetMapping("/about")
	public String about() {

		return "user/About";
	}
	
	
	
	@GetMapping("/admin/about1")
	public String about2() {

		return "user/About";
	}

}
