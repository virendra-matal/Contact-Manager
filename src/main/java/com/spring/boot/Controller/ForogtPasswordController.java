package com.spring.boot.Controller;

import java.util.Random;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.boot.Dao.UserRepository;
import com.spring.boot.model.User;
import com.spring.boot.services.EmailService;

@Controller
public class ForogtPasswordController {
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    Random randomotp=new Random(1000);

    @GetMapping("/forgot")
    public String ForgotPassword(){

        return "ForgotPassword";
    }
    
    @PostMapping("/sendOTP")
    public String GetOTP(@RequestParam("email") String email, HttpSession session) throws MessagingException{

        System.out.println("email is : "+email);

        User user = this.userRepository.GetUserByName(email);

        if (user==null) {
            session.setAttribute("wrongemail", "Wrong Email ID, Please provide registered Email ID.... Thank-You!!! ");
            return "ForgotPassword";
        }

        
        int otp = randomotp.nextInt(999999);
        System.out.println("otp is : "+otp);

        String message="Your OTP is "+otp+". Please Verify this otp with Smart Contact Manager... Thank-You!!!!!";
        String subject="OTP from Smart Contact Manager";
        
        boolean flag = this.emailService.sendEmail(email, message, subject);

        if (flag) {
            session.setAttribute("email",email);
            session.setAttribute("firstotp", otp);
            return "Verify_OTP";
        }else{
            session.setAttribute("Status", "failed to send OTP to your email, Please check your Email Id... Thank You!!!");
            return "ForgotPassword";
        }
        
    }

    @PostMapping("/verify_otp")
    public String matchOTP(@RequestParam("otp") int otp,HttpSession session){

        
        int firstotp=(int)session.getAttribute("firstotp");

        if (firstotp==otp) {
            
           

            return "ChangePasswordByForgotPw";
        } else {
            
            session.setAttribute("wrongotp", "You have enter wrong OTP...");
            return "Verify_OTP";
        }

    }

    @PostMapping("/setnewpassword")
    public String SetNewPassword(@RequestParam("newPassword") String newPassword, HttpSession session){

        String email = (String)session.getAttribute("email");
        User user = this.userRepository.GetUserByName(email);
        // boolean match = newPassword.matches(user.getPassword());
        boolean match = this.bCryptPasswordEncoder.matches(newPassword, user.getPassword());
        System.out.println("Password Match  : "+match   );
        // newPassword.length();
        String PasswordPattern="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

        if (match==false) {           
            if (newPassword.matches(PasswordPattern)) {
                user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
                userRepository.save(user);
                System.out.println("Password changed Successfully !!!");
                session.setAttribute("password", "Congrates! You have Successfully Changed your Passsword.");
                return "Login";
            }else{

                session.setAttribute("password", "Passsword should be minimum eight characters, at least one letter, one number and one special character:.");
                return "ChangePasswordByForgotPw";

            }
        }else{
            session.setAttribute("password", "Oops! You have entered your previous password, Please Provide new Password... "+newPassword);
            return "ChangePasswordByForgotPw";
        }
       

        
    }
}
