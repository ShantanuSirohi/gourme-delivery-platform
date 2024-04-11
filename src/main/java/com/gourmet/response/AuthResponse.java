package com.gourmet.response;

import com.gourmet.model.USER_Role;

import lombok.Data;

@Data
public class AuthResponse {

	private String jwt;
	private String message;
	private USER_Role role;
}
