package com.gourmet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gourmet.config.JwtProvider;
import com.gourmet.model.User;
import com.gourmet.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserByJwtToken(String jwt) throws Exception {

		String email=jwtProvider.getEmailFromJwtToken(jwt);
		User user=findUserByEmail(email);
		
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		
		User user=userRepository.findByEmail(email);
		if(user==null) {
			throw new Exception("User Not Found");
		}
		return user;
	}

}
