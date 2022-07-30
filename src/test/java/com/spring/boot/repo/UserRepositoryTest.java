package com.spring.boot.repo;

import com.spring.boot.Dao.UserRepository;
import com.spring.boot.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepositiry;

    @BeforeEach
    void setUp() {
        System.out.println("TEst Has Started..........");
    }

    @AfterEach
    void tearDown() {

        System.out.println("Test Has done.................");
    }

    @Test

    void getUserByName() {

        User ActualUser = this.userRepositiry.GetUserByName("veermatal100@gmail.com");
        int AuserId = ActualUser.getUserId();
        System.out.println("Actual REsult : "+ActualUser);
//        User user= new User (1,"Virendra Matal", "veermatal100@gmail.com", "$2a$10$zzsk.7Q2Jfz/OAbLzCeOKOH5wjke8S4LApjRbgwz6PK.xYQuSnbOi","defualt.png", "ROLE_USER",true, "I am a Developer...");
        int EuserId= 1;

        assertEquals(EuserId,AuserId);
    }
}