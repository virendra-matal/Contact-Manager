
package com.spring.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.spring.boot.Dao.UserRepository;
import com.spring.boot.model.User;

public class UserdetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.GetUserByName(username);
		/*
		 * user.getUserId(); System.out.println(user);
		 */
		if (user == null) {
			throw new UsernameNotFoundException("Could not found User by UserName!!!");
		}

		UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);
		return userDetailsImpl;
	}

}
