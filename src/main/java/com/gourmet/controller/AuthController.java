package com.gourmet.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gourmet.config.JwtProvider;
import com.gourmet.model.Cart;
import com.gourmet.model.USER_Role;
import com.gourmet.model.User;
import com.gourmet.repositories.CartRepository;
import com.gourmet.repositories.UserRepository;
import com.gourmet.request.LoginRequest;
import com.gourmet.response.AuthResponse;
import com.gourmet.services.CustomerUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        // Check if the email already exists
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new Exception("This email is already connected to another account");
        }

        // Encode the password before saving to the database
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        // Create a new user with encoded password
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFullname(user.getFullname());
        newUser.setRole(user.getRole());
        newUser.setPassword(encodedPassword);

        // Save the user to the database
        User savedUser = userRepository.save(newUser);

        // Create a new cart for the user
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        // Authenticate the new user
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(savedUser.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token for the authenticated user
        String jwt = jwtProvider.generateToken(authentication);

        // Create response with JWT token and user role
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req) {

        // Retrieve email and password from request body
        String username = req.getEmail();
        String password = req.getPassword();

        // Authenticate the user
        Authentication authentication = authenticate(username, password);

        // Generate JWT token for the authenticated user
        String jwt = jwtProvider.generateToken(authentication);

        // Retrieve user role from authentication
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        // Create response with JWT token and user role
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success");
        authResponse.setRole(USER_Role.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        // Load user details by username (email)
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        // Check if user exists and password matches
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        // Create authentication token for the user
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
