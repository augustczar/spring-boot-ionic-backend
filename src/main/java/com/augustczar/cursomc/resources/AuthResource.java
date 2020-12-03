package com.augustczar.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.augustczar.cursomc.dto.EmailDTO;
import com.augustczar.cursomc.security.JWTUtil;
import com.augustczar.cursomc.security.UserSpringSecurity;
import com.augustczar.cursomc.services.AuthService;
import com.augustczar.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response){
		UserSpringSecurity userSS = UserService.authenticated();
		String token = jwtUtil.generateToken(userSS.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO){
		authService.sendNewPassword(emailDTO.getEmail());
		return ResponseEntity.noContent().build();
	}
}
