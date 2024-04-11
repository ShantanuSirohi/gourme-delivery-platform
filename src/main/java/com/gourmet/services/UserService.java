package com.gourmet.services;

import com.gourmet.model.User;

public interface UserService {
	
	public User findUserByJwtToken(String jwt) throws Exception;

	public User findUserByEmail(String email) throws Exception;
	
}
